package future.legends.pancake.model;

import future.legends.pancake.logger.Logger;

import java.util.*;

public class QueueProvider {
    Map<TraineeCourse, Queue<Trainee>> pausedTrainees;

    Map<TraineeCourse, Queue<Trainee>> newTrainees;

    Map<TraineeCourse, Queue<Trainee>> benchTrainees;

    public QueueProvider()
    {
        pausedTrainees = new HashMap<>();
        newTrainees = new HashMap<>();
        benchTrainees = new HashMap<>();
        for(TraineeCourse tc : TraineeCourse.values())
        {
            pausedTrainees.put(tc,new LinkedList<>());
            newTrainees.put(tc,new LinkedList<>());
            benchTrainees.put(tc,new LinkedList<>());
        }
    }
    @Deprecated
    public void addTrainees(Collection<Trainee> trainees, boolean arePaused)
    {
        if(trainees ==null)
        {
            Logger.warn("Null pointer provided for QueueProvider addTrainees(), but issue handled");
            return;
        }
        Map<TraineeCourse, Queue<Trainee>> traineeQueue = arePaused?pausedTrainees:newTrainees;
            for(Trainee tr : trainees)
        {
            addTrainee(traineeQueue,tr);
        }
    }
    public void addTrainees(Collection<Trainee> trainees, TraineeState tstate)
    {
        if(trainees ==null || tstate==null)
        {
            Logger.warn("Null pointer provided for QueueProvider" +
                    " addTrainees(Collection<Trainee> trainees, TraineeState tstate)," +
                    " but issue handled");
            return;
        }
        Map<TraineeCourse, Queue<Trainee>> traineeQueue = switch(tstate)
        {
            case NEW -> newTrainees;
            case PAUSED -> pausedTrainees;
            case BENCHED -> benchTrainees;
        };
        for(Trainee tr : trainees)
        {
            addTrainee(traineeQueue,tr);
        }
    }

    private void addTrainee(Map<TraineeCourse, Queue<Trainee>> queueMap, Trainee tr)
    {
        if(tr ==null)
        {
            Logger.warn("Null pointer provided for QueueProvider" +
                    " addTrainee(Map<TraineeCourse, Queue<Trainee>> queueMap, Trainee tr)," +
                    " but issue handled");
            return;
        }
        queueMap.get(tr.getCourseType()).add(tr);
    }

    public boolean isAvailable()
    {
        return !isEmpty(pausedTrainees) || !isEmpty(newTrainees);
    }

    private boolean isEmpty(Map<TraineeCourse, Queue<Trainee>> queueMap)
    {
        for(Queue<Trainee> qt : queueMap.values())
        {
            if(!qt.isEmpty())
                return false;
        }
        return true;
    }
    public boolean isAvailable(TraineeCourse tc)
    {
        return (!pausedTrainees.get(tc).isEmpty()) || (!newTrainees.get(tc).isEmpty());
    }

    public Trainee getTrainee()
    {
        if(!isEmpty(pausedTrainees))
        {
            return getTraineeFromMap(pausedTrainees);
        }
        return getTraineeFromMap(newTrainees);
    }

    public Trainee getBenchedTrainee(TraineeCourse tc) throws NoSuchElementException
    {
        try { // FIXME <Queue>.remove() --> The method throws an NoSuchElementException when the Queue is empty.
            return benchTrainees.get(tc).remove();
        }
        catch (NullPointerException e)
        {
            throw new NoSuchElementException(e);
        }
    }

    private Trainee getTraineeFromMap(Map<TraineeCourse, Queue<Trainee>> queueMap) {
            for(Queue<Trainee> qt : queueMap.values())
            {
                if(!qt.isEmpty())
                    return qt.remove();
            }
            throw new NoSuchElementException("No trainees were found in the queue");
    }

    public int getAvailableBenchedCount()
    {
        int count = 0;
        for(TraineeCourse tc : TraineeCourse.values())
        {
            count += getAvailableBenchedCount(tc);
        }
        return count;
    }
    public int getAvailableBenchedCount(TraineeCourse tc)
    {
        return getAvailableCountHelper(tc, benchTrainees);
    }

    /**
     * Tries to pick a paused trainee; if there are none, picks a new trainee
     * @param tc course of the trainee
     * @return Trainee to be enrolled in a new centre
     */
    public Trainee getTrainee(TraineeCourse tc) throws NoSuchElementException
    {
        try { // FIXME <Queue>.remove() --> The method throws an NoSuchElementException when the Queue is empty.
            if (pausedTrainees.get(tc).isEmpty()) return newTrainees.get(tc).remove();
            else return pausedTrainees.get(tc).remove();
        }
        catch (NullPointerException e)
        {
            Logger.error("Paused trainee queue and new trainee queue are both empty " + e.getMessage());
            throw new NoSuchElementException(e);
        }
    }
    public int getAvailableTraineeCount()
    {
        int count = 0;
        for(TraineeCourse tc : TraineeCourse.values())
        {
            count += getAvailableTraineeCount(tc);
        }
        return count;
    }
    public int getAvailableTraineeCount(TraineeCourse tc)
    {
        return getAvailableCountHelper(tc,newTrainees,pausedTrainees);
    }
    @SafeVarargs
    private int getAvailableCountHelper(TraineeCourse tc, Map<TraineeCourse, Queue<Trainee>>... queueMaps)
    {
        int count=0;
        for(Map<TraineeCourse, Queue<Trainee>> queue : queueMaps)
        {
            count+=queue.get(tc).size();
        }
        return count;
    }

    // Returns a copy of the Map data for the simulator view to access detailed information.
    public Map<TraineeCourse, Queue<Trainee>> getMap(TraineeState tstate){
        Map<TraineeCourse, Queue<Trainee>> traineeQueue = switch(tstate)
        {
            case NEW -> newTrainees;
            case PAUSED -> pausedTrainees;
            case BENCHED -> benchTrainees;
        };
        return Map.copyOf(traineeQueue);
    }
}

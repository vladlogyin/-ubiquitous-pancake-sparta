package future.legends.pancake.model;

import java.util.*;

public class QueueProvider {
    Map<TraineeCourse, Queue<Trainee>> pausedTrainees;

    Map<TraineeCourse, Queue<Trainee>> newTrainees;

    public QueueProvider()
    {
        pausedTrainees = new HashMap<>();
        newTrainees = new HashMap<>();
        for(TraineeCourse tc : TraineeCourse.values())
        {
            pausedTrainees.put(tc,new LinkedList<>());
            newTrainees.put(tc,new LinkedList<>());
        }
    }

    public void addTrainees(Collection<Trainee> trainees, boolean arePaused)
    {
        Map<TraineeCourse, Queue<Trainee>> traineeQueue = arePaused?pausedTrainees:newTrainees;
            for(Trainee tr : trainees)
        {
            addTrainee(traineeQueue,tr);
        }
    }

    private void addTrainee(Map<TraineeCourse, Queue<Trainee>> queueMap, Trainee tr)
    {
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

    private Trainee getTraineeFromMap(Map<TraineeCourse, Queue<Trainee>> queueMap) {
            for(Queue<Trainee> qt : queueMap.values())
            {
                if(!qt.isEmpty())
                    return qt.remove();
            }
            throw new NoSuchElementException("No trainees were found in the queue");
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
            throw new NoSuchElementException(e);
        }
    }
    public int getAvailableCount()
    {
        int count = 0;
        for(TraineeCourse tc : TraineeCourse.values())
        {
            count += getAvailableCount(tc);
        }
        return count;
    }
    public int getAvailableCount(TraineeCourse tc)
    {
        return pausedTrainees.get(tc).size()+newTrainees.get(tc).size();
    }
}

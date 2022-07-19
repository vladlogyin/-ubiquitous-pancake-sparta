package future.legends.pancake.model;

import java.util.*;

public class QueueProvider {
    Map<TraineeCourse, Queue<Trainee>> pausedTrainees;

    Map<TraineeCourse, Queue<Trainee>> newTrainees;

    public QueueProvider()
    {
        pausedTrainees = new HashMap<>();
        newTrainees = new HashMap<>();
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
        if(!queueMap.containsKey(tr.getCourseType()))
            queueMap.put(tr.getCourseType(),new LinkedList<>());
        queueMap.get(tr.getCourseType()).add(tr);
    }

    public boolean isAvailable(TraineeCourse ts)
    {
        return (!pausedTrainees.isEmpty()) || (!newTrainees.isEmpty());
    }

    /**
     * Tries to pick a paused trainee; if there are none, picks a new trainee
     * @param tc course of the trainee
     * @return Trainee to be enrolled in a new centre
     */
    public Trainee getTrainee(TraineeCourse tc) throws NoSuchElementException
    {
        try {
            if (pausedTrainees.isEmpty()) return newTrainees.get(tc).remove();
            else return pausedTrainees.get(tc).remove();
        }
        catch (NullPointerException e)
        {
            throw new NoSuchElementException(e);
        }
    }
}

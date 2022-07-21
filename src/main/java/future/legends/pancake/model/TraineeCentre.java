package future.legends.pancake.model;

import future.legends.pancake.logger.Logger;

import java.util.*;

public abstract class TraineeCentre {
    int capacity;
    int monthsAlive;
    Collection<Trainee> enrolledTrainees = new ArrayList<>();

    public int getNumberOfEnrolledTrainees(){
        return enrolledTrainees.size();
    }

    public Collection<Trainee> getEnrolledTrainees(){
        return enrolledTrainees;
    }
    abstract void monthPassed();
    boolean shouldClose()
    {
        return enrolledTrainees.size()<25;
    }

    void enrollTrainees(Queue<Trainee> waitingList){
        Random r = new Random();
        int amountToEnroll = r.nextInt((50)) + 1;
        if(waitingList.isEmpty()) return; // No one to enroll.
        if(waitingList.size() < amountToEnroll) amountToEnroll = waitingList.size(); // waitingList smaller than amountToEnroll
        if(amountToEnroll > getAvailableSpots()) amountToEnroll = getAvailableSpots(); // amountToEnroll greater than availableSlots

        enrollTrainees(waitingList, amountToEnroll);
    }

    protected void enrollTrainees(Queue<Trainee> waitingList, int toEnroll){
        for (int i = 0; i < toEnroll; i++) {
            enrolledTrainees.add(waitingList.remove());
        }
    }

    void enrollTrainees(QueueProvider qp) {
        // TODO "DRY-B-GONE"
        Random r = new Random();
        int amountToEnroll = r.nextInt(50) + 1;

        int traineesAvailable = qp.getAvailableCount();
        if(traineesAvailable<=0) return; // No one to enroll.
        if(traineesAvailable < amountToEnroll) amountToEnroll = traineesAvailable; // qp smaller than amountToEnroll
        if(amountToEnroll > getAvailableSpots()) amountToEnroll = getAvailableSpots(); // qp greater than availableSlots
        for (int i = 0; i < amountToEnroll; i++) {
            enrolledTrainees.add(qp.getTrainee());
        }
        Logger.info(amountToEnroll + " trainees enrolled at TrainingHub");
    }

    public int getAvailableSpots(){
        return capacity - getNumberOfEnrolledTrainees();
    }

    public int getCapacity(){
        return capacity;
    }
}

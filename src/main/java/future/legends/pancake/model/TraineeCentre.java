package future.legends.pancake.model;

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

    abstract void close();

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

    public int getAvailableSpots(){
        return capacity - getNumberOfEnrolledTrainees();
    }

    public int getCapacity(){
        return capacity;
    }
}

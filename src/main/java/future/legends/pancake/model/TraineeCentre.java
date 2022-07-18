package future.legends.pancake.model;

import java.util.*;

public class TraineeCentre {
    private final int CAPACITY = 100;
    private Collection<Trainee> enrolledTrainees = new ArrayList<>();

    public int getNumberOfEnrolledTrainees(){
        return enrolledTrainees.size();
    }

    public void enrollTrainees(Queue<Trainee> waitingList){
        Random r = new Random();
        int amountToEnroll = r.nextInt((50)) + 1;

        if(waitingList.isEmpty()) return; // No one to enroll.
        if(waitingList.size() < amountToEnroll) amountToEnroll = waitingList.size(); // waitingList smaller than amountToEnroll
        if(amountToEnroll > getAvailableSpots()) amountToEnroll = getAvailableSpots(); // amountToEnroll greater than availableSlots

        enrollTrainees(waitingList, amountToEnroll);
    }

    private void enrollTrainees(Queue<Trainee> waitingList, int toEnroll){
        for (int i = 0; i < toEnroll; i++) {
            enrolledTrainees.add(waitingList.remove());
        }
    }

    private int getAvailableSpots(){
        return CAPACITY - getNumberOfEnrolledTrainees();
    }
}

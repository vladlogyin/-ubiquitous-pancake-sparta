package future.legends.pancake.model;

import java.util.Collection;
import java.util.Queue;

public class Bootcamp extends TraineeCentre{
    Bootcamp() {
        capacity = 500;
    }
    int numberOfLowAttendanceMonths = 0;

    @Override
    public Collection<Trainee> getEnrolledTrainees() {
        return super.getEnrolledTrainees();
    }

    @Override
    void monthPassed() {
        monthsAlive++;

        if (getNumberOfEnrolledTrainees() < 25) {
            numberOfLowAttendanceMonths++;
        } else {
            numberOfLowAttendanceMonths = 0;
        }

        /* Moved into shouldClose();
         *
        if (numberOfLowAttendanceMonths >= 3) {
            //close
        }
        */
    }

    @Override
    boolean shouldClose() {
        return numberOfLowAttendanceMonths >= 3;
    }

    void enrollTrainees(QueueProvider qp) {
        // TODO "DRY-B-GONE
        int traineesToEnroll = qp.getAvailableCount(); // get available trainees
        if(traineesToEnroll<=0) return; // No one to enroll.
        if(traineesToEnroll > getAvailableSpots()) traineesToEnroll = getAvailableSpots(); // qp greater than availableSlots
        for (int i = 0; i < traineesToEnroll; i++) {
            enrolledTrainees.add(qp.getTrainee());
        }
    }
    @Override
    void enrollTrainees(Queue<Trainee> waitingList) {
        // TODO maybe instead of accepting 1-50 random employees implement a method that
        //      takes on as many trainees as possible.
        int amountToEnroll = getAvailableSpots();
        if (waitingList.isEmpty()) {
            return;
        }
        if (waitingList.size() < amountToEnroll) {
            amountToEnroll = waitingList.size();
        }
        enrollTrainees(waitingList, amountToEnroll);
    }
}

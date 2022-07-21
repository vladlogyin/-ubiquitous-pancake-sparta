package future.legends.pancake.model;

import java.util.Random;

public class TechCentre extends TraineeCentre{

    private TraineeCourse course;

    public TraineeCourse getCourseType() {
        return course;
    }

    TechCentre(){
        Random r = new Random();
        course = TraineeCourse.values()[r.nextInt(TraineeCourse.values().length)];
        capacity = 200;
    }

    void enrollTrainees(QueueProvider qp) {
        // TODO "DRY-B-GONE"
        Random r = new Random();
        int amountToEnroll = r.nextInt(50) + 1;

        int traineesAvailable = qp.getAvailableTraineeCount(course);
        if(traineesAvailable<=0) return; // No one to enroll.
        if(traineesAvailable < amountToEnroll) amountToEnroll = traineesAvailable; // qp smaller than amountToEnroll
        if(amountToEnroll > getAvailableSpots()) amountToEnroll = getAvailableSpots(); // qp greater than availableSlots
        for (int i = 0; i < amountToEnroll; i++) {
            enrolledTrainees.add(qp.getTrainee(course));
        }
    }

    @Override
    void monthPassed() {
        monthsAlive++;
    }

}

package future.legends.pancake.model;

import future.legends.pancake.logger.Logger;

import java.util.Random;

public class TechCentre extends TraineeCentre{

    private TraineeCourse course;

    public TraineeCourse getCourseType() {
        return course;
    }

    public TechCentre(){
        Random r = new Random();
        course = TraineeCourse.values()[r.nextInt(TraineeCourse.values().length)];
        capacity = 200;
    }

    public void enrollTrainees(QueueProvider qp) {
        // TODO "DRY-B-GONE"
        Random r = new Random();
        int amountToEnroll = r.nextInt(50) + 1;

        int traineesAvailable = qp.getAvailableTraineeCount(course);
        if(traineesAvailable<=0) {
            Logger.warn("No employees enrolled this month for one of the Tech Centres");
            return;
        }
        if(traineesAvailable < amountToEnroll) amountToEnroll = traineesAvailable; // qp smaller than amountToEnroll
        if(amountToEnroll > getAvailableSpots()) amountToEnroll = getAvailableSpots(); // qp greater than availableSlots
        for (int i = 0; i < amountToEnroll; i++) {
            enrolledTrainees.add(qp.getTrainee(course));
        }
        Logger.info(amountToEnroll + " trainees enrolled at TechCentre");
    }

    @Override
    public void monthPassed() {
        monthsAlive++;
    }

}

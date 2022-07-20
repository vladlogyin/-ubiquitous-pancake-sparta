package future.legends.pancake.model;

import java.util.Random;

public class TechCentre extends TraineeCentre{

    private TraineeCourse course;

    public TraineeCourse getCourseType() {
        return course;
    }

    TechCentre(){
        Random r = new Random();
        course = TraineeCourse.values()[r.nextInt(TraineeCourse.values().length)];// TODO - assign course on creation.
        capacity = 200;
    }

    @Override
    void monthPassed() {
        monthsAlive++;
    }

    @Override
    boolean shouldClose() {
        return false;
    }
}

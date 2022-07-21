package future.legends.pancake.model;

public class Trainee {
    private TraineeCourse courseType;
    private int monthsPassed;


    public Trainee(TraineeCourse courseType) {
        this.courseType = courseType;
        monthsPassed=0;
    }

    public TraineeCourse getCourseType() {
        return courseType;
    }
    public void setCourseType(TraineeCourse courseType) {
        this.courseType = courseType;
    }

    public void monthPassed()
    {
        monthsPassed++;
    }
    public boolean hasGraduated()
    {
        return monthsPassed>3;
    }

}

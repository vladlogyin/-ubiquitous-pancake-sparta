package future.legends.pancake.model;

public class Trainee {

    private static final int MONTHS_GRADUATION=3;

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
        return monthsPassed>MONTHS_GRADUATION;
    }

    public int getMonthsBenched()
    {
        return hasGraduated()?(monthsPassed-MONTHS_GRADUATION):0;
    }

}

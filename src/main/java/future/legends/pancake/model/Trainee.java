package future.legends.pancake.model;

public class Trainee {
    private TraineeCourse courseType;

    public Trainee(TraineeCourse courseType) {
        this.courseType = courseType;
    }

    public Trainee() {

    }


    public TraineeCourse getCourseType() {
        return courseType;
    }
    public void setCourseType(TraineeCourse courseType) {
        this.courseType = courseType;
    }
}

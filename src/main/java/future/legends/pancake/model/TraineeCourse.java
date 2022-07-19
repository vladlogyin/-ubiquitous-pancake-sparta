package future.legends.pancake.model;

public enum TraineeCourse {
    JAVA ("Java"),
    C ("C#"),
    DEVOPS("DevOps"),
    DATA("Data"),
    BUSINESS("Business");

    private final String courseName;
    TraineeCourse(String courseName) {

        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }
}

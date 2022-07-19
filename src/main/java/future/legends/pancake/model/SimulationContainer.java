package future.legends.pancake.model;

import java.util.*;
import java.util.stream.Stream;

public class SimulationContainer {

    private List<Trainee> enrolledStudents;
    private Queue<Trainee> waitingStudents;
    private List<TraineeCentre> centres;


    public SimulationContainer() {
        waitingStudents = new LinkedList<>();
        enrolledStudents = new ArrayList<Trainee>();
        centres = new ArrayList<TraineeCentre>();
    }

    @Override
    public String toString(){
        //TODO add properties here VVVVVVVVVVV
        return "Number of open centres: " +
                "\nNumber of full centres: " +
                "\nNumber of trainees currently training: " +
                "\nNumber of trainees on the waiting list: ";
    }

    public List<Trainee> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Queue<Trainee> getWaitingStudents() {
        return waitingStudents;
    }

    public List<TraineeCentre> getCentres() {
        return centres;
    }

}

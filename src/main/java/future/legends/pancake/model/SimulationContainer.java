package future.legends.pancake.model;

import java.util.*;
import java.util.stream.Stream;

public class SimulationContainer {

    private List<Trainee> enrolledStudents;
    private Deque<Trainee> waitingStudents;
    private List<TraineeCentre> centres;


    public SimulationContainer() {
        waitingStudents = new LinkedList<>();
        enrolledStudents = new ArrayList<Trainee>();
        centres = new ArrayList<TraineeCentre>();
    }

    @Override
    public String toString(){
        //TODO add properties here VVVVVVVVVVV
        return "Number of open centres: " + centres.size() +
                "\nNumber of full centres: " + centres.stream().filter((c)->{return c.getAvailableSpots()<=0;}).count() +
                "\nNumber of trainees currently training: " + enrolledStudents.size() +
                "\nNumber of trainees on the waiting list: " + waitingStudents.size();
    }

    public List<Trainee> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Deque<Trainee> getWaitingStudents() {
        return waitingStudents;
    }

    public void moveTraineesFromClosedCentre(Collection<Trainee> traineesToBeMoved) {
        for (Trainee t: traineesToBeMoved) {
            waitingStudents.addFirst(t);
        }
    }

    public List<TraineeCentre> getCentres() {
        return centres;
    }

}

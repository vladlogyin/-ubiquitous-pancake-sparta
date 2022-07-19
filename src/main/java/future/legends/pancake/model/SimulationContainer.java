package future.legends.pancake.model;

import java.util.*;
import java.util.stream.Stream;

public class SimulationContainer {

    private Deque<Trainee> waitingStudents;
    private List<TraineeCentre> centres;


    public SimulationContainer() {
        waitingStudents = new LinkedList<>();
        centres = new ArrayList<TraineeCentre>();
    }

    @Override
    public String toString(){
        //TODO add properties here VVVVVVVVVVV
        return "Number of open centres: " + centres.size() +
                "\nNumber of full centres: " + centres.stream().filter((c)->{return c.getAvailableSpots()<=0;}).count() +
                "\nNumber of trainees currently training: " + countEnrolledStudents() +
                "\nNumber of trainees on the waiting list: " + waitingStudents.size();
    }

    public List<Trainee> generateEnrolledStudents() {
        ArrayList<Trainee> enrolledTrainees= new ArrayList<>();
        for(TraineeCentre tc : centres)
        {
            enrolledTrainees.addAll(tc.getEnrolledTrainees());
        }
        return enrolledTrainees;
    }

    public int countEnrolledStudents() {
        int count = 0;
        for(TraineeCentre tc : centres)
        {
            count+=tc.getEnrolledTrainees().size();
        }
        return count;
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

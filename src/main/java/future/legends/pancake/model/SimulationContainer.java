package future.legends.pancake.model;

import java.util.*;
import java.util.stream.Stream;

public class SimulationContainer {

    private int graduatedCount=0;

    private Deque<Trainee> waitingStudents;
    private List<TraineeCentre> centres;
    private List<TraineeCentre> closedCentres;

    private CentreFactory centreFactory;
    private QueueProvider queueProvider;

    public SimulationContainer() {
        waitingStudents = new LinkedList<>();
        centres = new ArrayList<TraineeCentre>();
        closedCentres = new ArrayList<TraineeCentre>();
        queueProvider = new QueueProvider();
        centreFactory = new CentreFactory();
    }

    @Override
    public String toString(){
        //TODO add properties here VVVVVVVVVVV
        return "Number of open centres: " + centres.size() +
                "\nNumber of full centres: " + centres.stream().filter((c)->{return c.getAvailableSpots()<=0;}).count() +
                "\nNumber of trainees currently training: " + countEnrolledStudents() +
                "\nNumber of trainees on the waiting list: " + queueProvider.getAvailableCount() +
                "\nNumber of trainees who have graduated: " + graduatedCount;
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

    public void incrementGraduateCount()
    {
        graduatedCount++;
    }

    public int getGraduateCount()
    {
        return graduatedCount;
    }

    public List<TraineeCentre> getCentres() {
        return centres;
    }

    public List<TraineeCentre> getClosedCentres() {
        return closedCentres;
    }

    public QueueProvider getQueueProvider()
    {
        return queueProvider;
    }

    public CentreFactory getCentreFactory()
    {
        return centreFactory;
    }

}

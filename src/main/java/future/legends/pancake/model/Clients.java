package future.legends.pancake.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Clients {

    private int assignedTrainees;
    private TraineeCourse traineeType;

    public Clients(int assignedTrainees, TraineeCourse traineeType) {
        this.assignedTrainees = assignedTrainees;
        this.traineeType = traineeType;
    }

    public Clients() {
    }

    public int getAssignedTrainees() {
        return assignedTrainees;
    }

    public TraineeCourse getTraineeType() {
        return traineeType;
    }

    public static Collection<Clients> createClients(){
        Random rand = new Random();
        int traineesWanted = rand.nextInt(15,30);
     return createClients(traineesWanted);
    }

    public static Collection<Clients> createClients(int assignedTrainees){
        Collection<Clients> clientsCollection = new ArrayList<Clients>();
        TraineeCourse[] courses = TraineeCourse.values();
        for (int i = 0; i < clientsCollection.size(); i++) {
            int selectEnum = new Random().nextInt(courses.length);
            clientsCollection.add(new Clients(assignedTrainees, courses[selectEnum]));
        }
        return null;
    }
}

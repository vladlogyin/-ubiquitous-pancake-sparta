package future.legends.pancake.model;

import java.util.*;

public class Client {

    private static int happyClients = 0;
    private static int unhappyClients = 0;
    private List<Trainee> assignedTrainees;
    private int traineesRequirement;
    private TraineeCourse traineeType;
    private int monthsPassed;

    public Client(int traineesRequirement, TraineeCourse traineeType) {
        this.traineesRequirement = traineesRequirement;
        this.traineeType = traineeType;
        assignedTrainees = new ArrayList<>();
        monthsPassed = 0;
    }

    public List<Trainee> getAssignedTrainees() {
        return assignedTrainees;
    }

    public TraineeCourse getTraineeType() {
        return traineeType;
    }

    public int getMonthsPassed() {
        return monthsPassed;
    }

    /**
     * This method takes each of the existing trainees,
     * but uses only a random amount of them 1-5
     * @param trainees  All trainees that exist
     * */
    public void takeTraineesThisMonth(Queue<Trainee> trainees) {
        this.monthsPassed++;
        checkIfRequirementMet();
        Random random = new Random();
        int numberOfTrainees = random.nextInt(1,6);

        for (int i = 0; i < numberOfTrainees; i++) {
            this.assignedTrainees.add(trainees.remove());
        }
    }

    private void checkIfRequirementMet() {
        if(this.monthsPassed % 12 == 0) {
            if(assignedTrainees.size() >= traineesRequirement) {
                happyClients++;
                // move temporarily - when it comes back it should
                ClientFactory.pauseHappyClient(this);
                // have same course type and months passed, but new trainees
            } else {
                unhappyClients++;
                ClientFactory.getAllClients().remove(this);
            }
        }
    }

}

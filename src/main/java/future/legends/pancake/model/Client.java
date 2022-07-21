package future.legends.pancake.model;

import future.legends.pancake.logger.Logger;

import java.util.*;

public class Client {

    private static int happyClients = 0;
    private static int unhappyClients = 0;
    private List<Trainee> assignedTrainees;
    private int newTraineesRequirement;
    private int traineesRequirement;
    private TraineeCourse traineeType;

    public Client(int traineesRequirement, TraineeCourse traineeType) {
        this.newTraineesRequirement = traineesRequirement;
        this.traineesRequirement = traineesRequirement;
        this.traineeType = traineeType;
        assignedTrainees = new ArrayList<>();
    }

    public List<Trainee> getAssignedTrainees() {
        return assignedTrainees;
    }

    public TraineeCourse getTraineeType() {
        return traineeType;
    }

    /**
     * This method takes each of the existing trainees,
     * but uses only a random amount of them 1-max requirement
     *
     * @param qp All trainees that exist
     */
    public void takeTraineesThisMonth(QueueProvider qp) {
        Random random = new Random();

        int numberOfTrainees = 0;
        if (traineesRequirement > 0) {
            numberOfTrainees = traineesRequirement>1?random.nextInt(1, traineesRequirement):1;
            int availableTrainees = qp.getAvailableBenchedCount(getTraineeType());
            numberOfTrainees = availableTrainees >= numberOfTrainees ? numberOfTrainees : availableTrainees;
            traineesRequirement -= numberOfTrainees;
            for (int i = 0; i < numberOfTrainees; i++) {
                this.assignedTrainees.add(qp.getBenchedTrainee(getTraineeType()));
            }
            Logger.info("A " + getTraineeType().toString() + " client took " + numberOfTrainees + " trainees");
        }
    }

    /**
     * Only call this at the end of the year
     */
    public boolean isSatisfied() {
        return traineesRequirement == 0;
        /*if(assignedTrainees.size() >= traineesRequirement) {
            happyClients++;
            // move temporarily - when it comes back it should
            ClientFactory.pauseHappyClient(this);
            // have same course type and months passed, but new trainees
        } else {
            unhappyClients++;
            ClientFactory.getAllClients().remove(this);
        }*/
    }

    /**
     * Call this at the beginning of the year to reset the trainee requirement
     */
    public void resetClient() {
        traineesRequirement = newTraineesRequirement;
    }

}

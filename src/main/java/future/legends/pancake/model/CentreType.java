package future.legends.pancake.model;

public enum CentreType {
    TRAINING_HUB(TrainingHub.class,-1);
    Class<? extends TraineeCentre> type;
    /**
     * Limit of how many centres of this type can be opened
     * set to -1 for an unlimited amount
     */
    int limit;

    CentreType(Class<TrainingHub> t, int limit) {
        this.type = t;
        this.limit=limit;
    }
}

package future.legends.pancake.model;

public enum CentreType {
    TECH_CENTRE(TechCentre.class,-1),
    BOOTCAMP(Bootcamp.class,2),
    TRAINING_HUB(TrainingHub.class,-1);
    Class<? extends TraineeCentre> type;
    /**
     * Limit of how many centres of this type can be opened
     * set to -1 for an unlimited amount
     */
    int limit;

    CentreType(Class<? extends TraineeCentre> t, int limit) {
        this.type = t;
        this.limit=limit;
    }
}

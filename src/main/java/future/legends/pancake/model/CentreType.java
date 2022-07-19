package future.legends.pancake.model;

public enum CentreType {
    TRAINING_HUB(TrainingHub.class);
    Class<? extends TraineeCentre> type;
    CentreType(Class<TrainingHub> t) {
        this.type = t;
    }
}

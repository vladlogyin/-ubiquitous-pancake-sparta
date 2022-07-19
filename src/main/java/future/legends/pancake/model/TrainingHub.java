package future.legends.pancake.model;

public class TrainingHub extends TraineeCentre{
    TrainingHub(){
        capacity = 100;
    }

    @Override
    void monthPassed() {
        monthsAlive++;
    }

    @Override
    boolean shouldClose() {
        return false;
    }
}

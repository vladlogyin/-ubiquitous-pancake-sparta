package future.legends.pancake.model;

public class TechCentre extends TraineeCentre{
    TechCentre(){
        capacity = 200;
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

package future.legends.pancake.model;

public class SimulationContainer {
    private int months_left;

    public SimulationContainer(int months_left) {
        this.months_left = months_left;
    }

    private void monthPassed() {
        months_left--;
        System.out.println("A month passed, " + months_left + " months remaining");
    }

    public String start() {
        while(months_left > 0) {
            try {
                Thread.sleep(100); // tenth of a second per simulated month
                monthPassed();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return stop();
    }

    private String stop(){
        return "Number of open centres: " +
            "\nNumber of full centres: " +
            "\nNumber of trainees currently training: " +
            "\nNumber of trainees on the waiting list: ";
    }
}

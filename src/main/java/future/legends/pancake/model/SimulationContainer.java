package future.legends.pancake.model;

public class SimulationContainer {
    private static int months_left = 0;

    private void start() {
        while(months_left > 0) {
            try {
                Thread.sleep(250); // quarter of a second per month
                monthPassed();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void monthPassed() {
        months_left--;
        System.out.println("A month passed, " + months_left + " months remaining");
    }

    private void stop(){
        // output:
        // number of open centres
        // number of full centres
        // number of trainees currently training
        // number of trainees on the waiting list
    }
}

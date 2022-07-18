package future.legends.pancake.model;

import java.util.Random;

public class Simulator {

    Random rnd;

    private int monthsPassed=0;

    //TODO review if this needs to be public
    public SimulationContainer simData;


    public Simulator()
    {
        rnd = new Random();
    }

    public Simulator(int seed)
    {
        rnd = new Random(seed);
    }

    /**
     * Advances the simulation by multiple months
     * @param months number of months to advance
     */
    public void simulateMonths(int months)
    {
        for(int i=0;i<months;i++) {
            simulateMonth();
        }
    }

    /**
     * Advances the simulation by one month
     */
    public void simulateMonth()
    {
        try {
            Thread.sleep(100); // tenth of a second per simulated month
        } catch (InterruptedException e) {
            // month took less than it should have taken to simulate - 100ms
            System.err.println("Timing was corrupted for this simulation");
            throw new RuntimeException(e);
        }

        monthsPassed++;
        if (monthsPassed > 0 && monthsPassed % 2 == 0 ) {
            // TODO create a new centre
        }
        // TODO create a random num of trainees
    }

    public int getMonthsPassed()
    {
        return monthsPassed;
    }

    @Override
    public String toString(){
        return simData.toString();
    }

}

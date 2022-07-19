package future.legends.pancake.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class Simulator {

    Random rnd;

    private int monthsPassed=0;

    //TODO review if this needs to be public
    public SimulationContainer simData;


    public Simulator()
    {
        rnd = new Random();
        simData = new SimulationContainer();
    }

    public Simulator(int seed)
    {
        rnd = new Random(seed);
        simData = new SimulationContainer();
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
        everyMonthActivity();
        if (monthsPassed > 0 && monthsPassed % 2 == 0 ) everyTwoMonthsActivity();
        if (monthsPassed > 0 && monthsPassed % 3 == 0 ) everyThreeMonthsActivity();
    }

    private void everyMonthActivity() {
        simData.getWaitingStudents().addAll(TraineeFactory.generateTrainees());
        for(TraineeCentre tc : simData.getCentres())
        {
            if(simData.getWaitingStudents().size() == 0 ) break;
            tc.enrollTrainees(simData.getWaitingStudents());
        }
        checkCentresForInactivity();
    }
    private void everyTwoMonthsActivity() {
        simData.getCentres().add(new TraineeCentre());
    }
    private void everyThreeMonthsActivity() {

    }
    private void checkCentresForInactivity() {
        Iterator<TraineeCentre> i = simData.getCentres().iterator();
        while(i.hasNext())
        {
            var tc = i.next();
            if(tc.getNumberOfEnrolledTrainees() < 25) {
                var traineesToBeMoved = i.getEnrolledTrainees();
                simData.moveTraineesFromClosedCentre(traineesToBeMoved);
                i.remove(); // close centre
            }
        }
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

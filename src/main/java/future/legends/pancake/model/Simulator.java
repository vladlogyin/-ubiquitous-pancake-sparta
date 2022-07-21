package future.legends.pancake.model;

import future.legends.pancake.viewer.SimulatorView;
import org.apache.logging.log4j.core.util.KeyValuePair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

        // graduate trainees

        graduateTrainees();

        // open centres

        if(monthsPassed%2==0 && monthsPassed>0){
            simData.getCentres().add(simData.getCentreFactory().create());
        }

        // generate trainees
        simData.getQueueProvider().addTrainees(TraineeFactory.generateTrainees(),false);
        // run through each training centre to assign trainees or close the centre
        Iterator<TraineeCentre> i = simData.getCentres().iterator();
        while(i.hasNext())
        {
            var tc = i.next();
            boolean shouldCloseBefore = tc.shouldClose();
            //assign trainees
            tc.enrollTrainees(simData.getQueueProvider());
            if(tc.shouldClose()) {
                var traineesToBeMoved = tc.getEnrolledTrainees();
                // put trainees on pause
                simData.getQueueProvider().addTrainees(traineesToBeMoved,true);
                i.remove(); // close centre
                simData.getCentreFactory().delete(tc);
                simData.getClosedCentres().add(tc); // keep track of closed centres
            }
        }
        // **training**
        for(TraineeCentre tc : simData.getCentres())
        {
            for(Trainee tr : tc.getEnrolledTrainees())
            {
                tr.monthPassed(); // payslip
            }
        }

        monthsPassed++;
    }

    private void graduateTrainees() {
        for(TraineeCentre tc : simData.getCentres())
        {

            Iterator<Trainee> i = tc.getEnrolledTrainees().iterator();
            while(i.hasNext())
            {
                var tr = i.next();
                if(tr.hasGraduated())
                {
                    //TODO move trainee to a client or to the bench in phase 3
                    i.remove(); // remove trainee from training centre
                    simData.incrementGraduateCount();

                }
            }
        }
    }

    public int getMonthsPassed()
    {
        return monthsPassed;
    }

    @Override
    public String toString() {
        return SimulatorView.getReport(simData,
                simData.getQueueProvider().pausedTrainees,
                simData.getQueueProvider().newTrainees);
    }
}

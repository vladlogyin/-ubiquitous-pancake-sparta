package future.legends.pancake.model;

import future.legends.pancake.logger.Logger;
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
        Logger.info("Simulation started");
        for(int i=0;i<months;i++) {
            simulateMonth();
            Logger.info("Simulated month " + (i+1));
        }
        Logger.info("Simulation finished");
    }

    /**
     * Advances the simulation by one month
     */
    public void simulateMonth()
    {

        // graduate trainees

        graduateTrainees();

        if(monthsPassed%12==0 && monthsPassed>0) {
            manageClients();
        }

        // assign benched trainees
        for(Client c : simData.getClients())
        {
            c.takeTraineesThisMonth(simData.getQueueProvider());
        }

        // open centres every 2 months
        if(monthsPassed%2==0 && monthsPassed>0){
            simData.getCentres().add(simData.getCentreFactory().create());
            Logger.info("Opened a " + simData.getCentres().get(simData.getCentres().size()-1).getClass().getSimpleName());
        }

        generateNewTrainees();

        manageCentres();

        notifyMonthHasPassed();
    }

    private void notifyMonthHasPassed(){
        for(TraineeCentre tc : simData.getCentres())
        {
            tc.monthPassed();
            for(Trainee tr : tc.getEnrolledTrainees())
            {
                tr.monthPassed(); // payslip
            }
        }
        monthsPassed++;
    }

    private void generateNewTrainees(){
        simData.getQueueProvider().addTrainees(TraineeFactory.generateTrainees(),TraineeState.PAUSED);
    }

    private void manageCentres(){
        // run through each training centre to assign trainees or close the centre
        Iterator<TraineeCentre> i = simData.getCentres().iterator();
        while(i.hasNext())
        {
            var tc = i.next();
            //assign trainees
            tc.enrollTrainees(simData.getQueueProvider());
            if(tc.shouldClose()) {
                var traineesToBeMoved = tc.getEnrolledTrainees();
                // put trainees on pause
                simData.getQueueProvider().addTrainees(traineesToBeMoved,TraineeState.PAUSED);
                i.remove(); // close centre
                Logger.info(tc.getClass().getSimpleName() + " closed and " + tc.getEnrolledTrainees().size() + " trainees added to queue");
                simData.getCentreFactory().delete(tc);
                simData.getClosedCentres().add(tc); // keep track of closed centres
            }
        }
    }

    private void manageClients()
    {
        Iterator<Client> i = simData.getClients().iterator();
        while(i.hasNext())
        {
            Client c = i.next();
            if(c.isSatisfied())
            {
                c.resetClient();
                Logger.info("A client has become happy after having their requirement met");
            }
            else
            {
                i.remove();
                simData.incrementUnhappyClientCount();
                Logger.warn("A client has become unhappy after not having their requirement met");
            }
        }
        // Create a new client
        simData.getClients().add(ClientFactory.create());
    }

    private void graduateTrainees() {
        for(TraineeCentre tc : simData.getCentres())
        {
            int preGraduationCount = simData.getGraduateCount();
            Iterator<Trainee> i = tc.getEnrolledTrainees().iterator();
            while(i.hasNext())
            {
                var tr = i.next();
                if(tr.hasGraduated())
                {
                    i.remove();
                    Collection<Trainee> ae = new ArrayList(1);
                    ae.add(tr);
                    simData.getQueueProvider().addTrainees(ae, TraineeState.BENCHED);
                    simData.incrementGraduateCount();

                }
            }
            if (simData.getGraduateCount() - preGraduationCount == 0){
                Logger.warn("No employees graduated this month from " + tc.getClass().getSimpleName());
            }
            else{
                Logger.info((simData.getGraduateCount() - preGraduationCount) + " trainees graduated " + tc.getClass().getSimpleName());
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

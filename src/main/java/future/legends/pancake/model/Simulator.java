package future.legends.pancake.model;

import future.legends.pancake.logger.Logger;
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

        // open centres

        if(monthsPassed%2==0 && monthsPassed>0){
            simData.getCentres().add(simData.getCentreFactory().create());
            Logger.info("Opened a " + simData.getCentres().get(simData.getCentres().size()-1).getClass().getSimpleName());
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
                Logger.info(tc.getClass().getSimpleName() + " closed and " + tc.getEnrolledTrainees().size() + " trainees added to queue");
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
            int preGraduationCount = simData.getGraduateCount();
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
            Logger.info((simData.getGraduateCount() - preGraduationCount) + " trainees graduated " + tc.getClass().getSimpleName());
        }
    }

    public int getMonthsPassed()
    {
        return monthsPassed;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(1000);

        appendOpenCentres(str);
        appendClosedCentres(str);
        appendFullCentres(str);
        appendCurrentlyTraining(str);
        appendWaitingTrainees(str);
        // TODO implement empty messages.
        return str.toString();
    }

    private void appendOpenCentres(StringBuilder str){
        str.append("\n>> Open Centers\n");
        var openCenters = simData.getCentres().stream()
                .collect(Collectors.groupingBy(e -> e.getClass().getSimpleName(),
                        Collectors.counting()
                ));
        openCenters.forEach((key, value) -> str.append(key).append(" : ").append(value).append(" open centres.").append("\n"));
        // TODO add check to see if empty and append("None open");
    }

    private void appendClosedCentres(StringBuilder str){
        str.append("\n>> Closed Centres\n");
        var closedCenters = simData.getClosedCentres().stream()
                .collect(Collectors.groupingBy(e -> e.getClass().getSimpleName(),
                        Collectors.counting()
                ));
        closedCenters.forEach((key, value) -> str.append(key).append(" : ").append(value).append(" closed centres.").append("\n"));
        // TODO add check to see if empty and append("None closed");
    }

    private void appendFullCentres(StringBuilder str){
        str.append("\n>> Full Centres\n");
        HashMap<String,Integer> fullCenters = new HashMap<>();

        for (var s : simData.getCentres()){
            var name = s.getClass().getSimpleName();
            if(s.getAvailableSpots() == 0) {
                if(fullCenters.containsKey(name)){
                    fullCenters.put(name, fullCenters.get(name) + 1);
                } else fullCenters.put(name, 1);
            }
        }

        if(fullCenters.isEmpty()){
            str.append("No full centres.\n");
            return;
        }

        for (var trainingCount : fullCenters.entrySet()) {
            str.append(trainingCount.getKey()).append(" : ").append(trainingCount.getValue()).append("\n");
        }
    }

    private void appendCurrentlyTraining(StringBuilder str){
        str.append("\n>> Currently Training\n");
        HashMap<String,Integer> trainingMap = new HashMap<>();

        for (var s : simData.getCentres()){
            var name = s.getClass().getSimpleName();
            var cohortSize = s.enrolledTrainees.size();
            if(trainingMap.containsKey(name)){
                trainingMap.put(name, trainingMap.get(name) + cohortSize);
            } else trainingMap.put(name, cohortSize);
        }

        if(trainingMap.isEmpty()){
            str.append("None training.\n");
            return;
        }

        for (var trainingCount : trainingMap.entrySet()) {
            str.append(trainingCount.getKey()).append(" : ").append(trainingCount.getValue()).append("\n");
        }
    }

    private void appendWaitingTrainees(StringBuilder str){
        str.append("\n>> Waiting for enroll.\n");
        var waiting = new HashMap<TraineeCourse, AtomicInteger>();//simData.getQueueProvider().newTrainees;
        for(var kvp : simData.getQueueProvider().newTrainees.entrySet())
        {
            if(!waiting.containsKey(kvp.getKey()))
            {
                waiting.put(kvp.getKey(),new AtomicInteger(0));
            }
            waiting.get(kvp.getKey()).incrementAndGet();
        }
        for(var kvp : simData.getQueueProvider().pausedTrainees.entrySet())
        {
            if(!waiting.containsKey(kvp.getKey()))
            {
                waiting.put(kvp.getKey(),new AtomicInteger(0));
            }
            waiting.get(kvp.getKey()).incrementAndGet();
        }

        for(var entry : waiting.entrySet()){

            int aint = entry.getValue().get();
            str.append(entry.getKey().getCourseName()).append(" : ");
            if(aint>0) {
                str.append(aint).append(" waiting\n");
            }
            else {
                str.append("None waiting\n");
            }
        }
    }
}

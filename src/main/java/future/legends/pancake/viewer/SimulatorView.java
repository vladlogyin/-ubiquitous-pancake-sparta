package future.legends.pancake.viewer;

import future.legends.pancake.model.SimulationContainer;
import future.legends.pancake.model.Trainee;
import future.legends.pancake.model.TraineeCentre;
import future.legends.pancake.model.TraineeCourse;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SimulatorView {

    private static SimulationContainer simData;
    private static Set<Map.Entry<TraineeCourse, Queue<Trainee>>> pausedTrainees;
    private static Set<Map.Entry<TraineeCourse, Queue<Trainee>>> newTrainees;

    public static String getReport(SimulationContainer sim,
                                   Set<Map.Entry<TraineeCourse, Queue<Trainee>>> pausedTrainees,
                                   Set<Map.Entry<TraineeCourse, Queue<Trainee>>> newTrainees)
    {
        if(sim == null) return "Unable to load simulation data!";
        SimulatorView.simData = sim;
        SimulatorView.pausedTrainees = pausedTrainees;
        SimulatorView.newTrainees = newTrainees;

        StringBuilder stringBuilder = new StringBuilder(1000);

        appendOpenCentres(stringBuilder);
        appendClosedCentres(stringBuilder);
        appendFullCentres(stringBuilder);
        appendCurrentlyTraining(stringBuilder);
        //appendTraineeCourseDetails(stringBuilder);

        return stringBuilder.toString();
    }

    private static void appendTraineeCourseDetails(StringBuilder stringBuilder) {
        HashMap<TraineeCourse,Integer> courseTrainees = new HashMap<>();
        for (var tc: simData.getCentres())
        {
            getIndividualCourseDetails(stringBuilder, tc);
        }
        stringBuilder.append(Arrays.toString(new Set[]{courseTrainees.entrySet()}));
    }

    private static void getIndividualCourseDetails(StringBuilder stringBuilder, TraineeCentre tc){
        HashMap<TraineeCourse, Integer> traineeCourses = new HashMap<>();
        for(Trainee t : tc.getEnrolledTrainees()){
            if(!traineeCourses.containsKey(t.getCourseType()))
            {
                traineeCourses.put(t.getCourseType(), 1);
            }
            else
            {
                traineeCourses.put(t.getCourseType(), traineeCourses.get(t.getCourseType()) + 1);
            }
        }
        for (var courseDetail : traineeCourses.entrySet()) {
            stringBuilder.append(" - ").append(courseDetail.getValue().intValue()).append(" : ")
                    .append(courseDetail.getKey().getCourseName()).append(" trainees.\n");
        }
    }

    private static void appendOpenCentres(StringBuilder str){
        str.append("\n>> Open Centers\n");
        var openCenters = simData.getCentres().stream()
                .collect(Collectors.groupingBy(e -> e.getClass().getSimpleName(),
                        Collectors.counting()
                ));
        openCenters.forEach((key, value) -> str.append(key).append(" : ").append(value).append(" open centres.").append("\n"));
        // TODO add check to see if empty and append("None open");
    }

    private static void appendClosedCentres(StringBuilder str){
        str.append("\n>> Closed Centres\n");
        var closedCenters = simData.getClosedCentres().stream()
                .collect(Collectors.groupingBy(e -> e.getClass().getSimpleName(),
                        Collectors.counting()
                ));
        closedCenters.forEach((key, value) -> str.append(key).append(" : ").append(value).append(" closed centres.").append("\n"));
        // TODO add check to see if empty and append("None closed");
    }

    private static void appendFullCentres(StringBuilder str){
        str.append("\n>> Full Centres\n");
        HashMap<TraineeCentre,Integer> fullCenters = new HashMap<>();

        for (var s : simData.getCentres()){
            var name = s;
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
            str.append(trainingCount.getKey().getClass().getSimpleName()).append(" : ").append(trainingCount.getValue()).append("\n");
        }
    }

    private static void appendCurrentlyTraining(StringBuilder str){
        str.append("\n>> Currently Training\n");
        HashMap<TraineeCentre,Integer> trainingMap = new HashMap<>();

        for (var s : simData.getCentres()){
            var name = s;
            var cohortSize = s.getNumberOfEnrolledTrainees();
            if(trainingMap.containsKey(name)){
                trainingMap.put(name, trainingMap.get(name) + cohortSize);
            } else trainingMap.put(name, cohortSize);
        }

        if(trainingMap.isEmpty()){
            str.append("None training.\n");
            return;
        }

        for (var trainingCount : trainingMap.entrySet()) {
            str.append(trainingCount.getKey().getClass().getSimpleName()).append(" : ").append(trainingCount.getValue()).append("/")
                    .append(trainingCount.getKey().getCapacity()).append("\n");
            getIndividualCourseDetails(str, trainingCount.getKey());
        }
    }

    private static void appendWaitingTrainees(StringBuilder str){
        str.append("\n>> Waiting for enroll.\n");
        var waiting = new HashMap<TraineeCourse, AtomicInteger>();//simData.getQueueProvider().newTrainees;
        for(var kvp : newTrainees)
        {
            if(!waiting.containsKey(kvp.getKey()))
            {
                waiting.put(kvp.getKey(),new AtomicInteger(0));
            }
            waiting.get(kvp.getKey()).incrementAndGet();
        }
        for(var kvp : pausedTrainees)
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

package future.legends.pancake.viewer;

import future.legends.pancake.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class SimulatorView {

    private static SimulationContainer simData;
    private static Map<TraineeCourse, Queue<Trainee>> pausedTrainees;
    private static Map<TraineeCourse, Queue<Trainee>> newTrainees;

    public static String getReport(SimulationContainer sim,
                                   Map<TraineeCourse, Queue<Trainee>> pausedTrainees,
                                   Map<TraineeCourse, Queue<Trainee>> newTrainees)
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
        appendWaitingTrainees(stringBuilder);
        appendClients(stringBuilder);
        appendGraduatedTrainees(stringBuilder);

        return stringBuilder.toString();
    }

    private static void appendGraduatedTrainees(StringBuilder stringBuilder){
        stringBuilder.append("\n\n").append(simData.getGraduateCount()).append(" Spartans have graduated!");
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
            stringBuilder.append(" - ").append(courseDetail.getValue().intValue()).append(" x ")
                    .append(courseDetail.getKey().getCourseName()).append(" trainees.\n");
        }
    }

    private static void appendOpenCentres(StringBuilder str){
        str.append("\n>> Open Centers\n");
        var openCenters = simData.getCentres().stream()
                .collect(Collectors.groupingBy(e -> e.getClass().getSimpleName(),
                        Collectors.counting()
                ));
        if(openCenters.isEmpty()){
            str.append("None open.\n");
            return;
        }
        openCenters.forEach((key, value) -> str.append(key).append("\t: ").append(value).append(" open centres.").append("\n"));
    }

    private static void appendClosedCentres(StringBuilder str){
        str.append("\n>> Closed Centres\n");
        var closedCenters = simData.getClosedCentres().stream()
                .collect(Collectors.groupingBy(e -> e.getClass().getSimpleName(),
                        Collectors.counting()
                ));
        if(closedCenters.isEmpty()){
            str.append("None closed.\n");
            return;
        }
        closedCenters.forEach((key, value) -> str.append(key).append("\t: ").append(value).append(" closed centres.").append("\n"));

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

    private static void appendWaitingTrainees(StringBuilder stringBuilder){
        stringBuilder.append("\n>> Waiting for enroll.\n");
        HashMap<TraineeCourse, Integer> traineeCourses = new HashMap<>();
        for (var entry : pausedTrainees.entrySet()) {
            countWaiting(stringBuilder, entry.getValue(), traineeCourses);
        }

        for (var entry : newTrainees.entrySet()) {
            countWaiting(stringBuilder, entry.getValue(), traineeCourses);
        }

        if(traineeCourses.isEmpty()){
            stringBuilder.append("None waiting.\n");
            return;
        }

        for (var courseDetail : traineeCourses.entrySet()) {
            stringBuilder.append(courseDetail.getValue().intValue()).append(" : ")
                    .append(courseDetail.getKey().getCourseName()).append(" waiting.\n");
        }
    }

    private static void countWaiting(StringBuilder stringBuilder, Queue<Trainee> queue, HashMap<TraineeCourse, Integer> counts){
        for (var trainee: queue) {
            if(!counts.containsKey(trainee.getCourseType()))
            {
                counts.put(trainee.getCourseType(), 1);
            }
            else
            {
                counts.put(trainee.getCourseType(), counts.get(trainee.getCourseType()) + 1);
            }
        }
    }

    private static void appendClients(StringBuilder stringBuilder){
        stringBuilder.append("\n>> Clients");
        stringBuilder.append("\nTotal clients\t--> ").append(simData.getHappyClientCount()+simData.getUnhappyClientCount());
        stringBuilder.append("\nHappy Clients\t--> ").append(simData.getHappyClientCount());
        stringBuilder.append("\nUnhappy Clients\t--> ").append(simData.getUnhappyClientCount());
    }
}

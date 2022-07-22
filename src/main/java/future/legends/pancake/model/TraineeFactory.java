package future.legends.pancake.model;

import future.legends.pancake.logger.Logger;

import java.util.*;

public class TraineeFactory {

    public static Collection<Trainee> generateTrainees(){
        Random random = new Random();
        int newTrainees = random.nextInt(50,100);
        return generateTrainees(newTrainees);
    }

    public static Collection <Trainee> generateTrainees(int numOfTrainees) {
        Collection<Trainee> traineeCollection = new ArrayList<Trainee>(numOfTrainees>0?numOfTrainees:0);
        for (int i = 0; i < numOfTrainees; i++) {
            TraineeCourse[] courses = TraineeCourse.values();
            int selectEnum = new Random().nextInt(courses.length);
            traineeCollection.add(new Trainee(courses[selectEnum]));
        }
        Logger.info("Generated " + numOfTrainees + " new trainees");
        return traineeCollection;
    }



}

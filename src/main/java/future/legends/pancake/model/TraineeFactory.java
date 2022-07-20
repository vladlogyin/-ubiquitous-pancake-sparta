package future.legends.pancake.model;

import java.util.*;

public class TraineeFactory {

    public static Collection<Trainee> generateTrainees(){
        Random random = new Random();
        int newTrainees = random.nextInt(50,100);
        return generateTrainees(newTrainees);
    }

    public static Collection <Trainee> generateTrainees(int numOfTrainees) {
        Collection<Trainee> traineeCollection = new ArrayList<Trainee>(numOfTrainees);
        TraineeCourse[] courses = TraineeCourse.values();
        for (int i = 0; i < numOfTrainees; i++) {
            int selectEnum = new Random().nextInt(courses.length);
            traineeCollection.add(new Trainee(courses[selectEnum]));
        }
        return traineeCollection;
    }



}

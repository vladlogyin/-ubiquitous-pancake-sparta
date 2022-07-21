package future.legends.pancake.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class ClientFactory {

    public static Client create(){
        Random random = new Random();
        int randomRequirement = new Random().nextInt(15,31);
        TraineeCourse[] courses = TraineeCourse.values();
        int selectEnum = random.nextInt(courses.length);
        return new Client(randomRequirement, courses[selectEnum]);
    }
}

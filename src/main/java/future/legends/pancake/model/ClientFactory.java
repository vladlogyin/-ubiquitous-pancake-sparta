package future.legends.pancake.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class ClientFactory {

    private static Collection<Client> allClients;
    private static Collection<Client> happyClients;

    public ClientFactory() {
        allClients = new ArrayList<>();
    }

    public void create(){
        Random random = new Random();
        int randomRequirement = new Random().nextInt(15,31);
        TraineeCourse[] courses = TraineeCourse.values();
        int selectEnum = random.nextInt(courses.length);
        allClients.add(new Client(randomRequirement, courses[selectEnum]));
    }

    public static Collection<Client> getAllClients() {
        return allClients;
    }

    public static void pauseHappyClient(Client c){
        happyClients.add(c);
    }

    public void checkIfHappyClientIsBack() {
        Iterator<Client> i = happyClients.iterator();
        while(i.hasNext()){
            Client c = i.next();
            if(c.getMonthsPassed() % 12 == 0) {
                allClients.add(c); // add client to original list
                i.remove(); // remove client from paused happy clients
            }
        }
    }

}

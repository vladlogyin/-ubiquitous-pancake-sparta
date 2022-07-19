package future.legends.pancake.model;

import java.awt.*;
import java.util.Random;

public class CentreFactory {

    public static TraineeCentre create(){
        Random r = new Random();
        TraineeCentre tc = null;
        switch (r.nextInt(3)){
            case 0 -> {
                tc = new TrainingHub();
            }
            case 1 -> {
                tc = new Bootcamp();
            }
            case 2 -> {
                tc = new TechCentre();
            }
        }
        return tc;
    }

}

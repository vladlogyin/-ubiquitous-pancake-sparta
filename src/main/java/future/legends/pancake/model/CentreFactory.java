package future.legends.pancake.model;

import java.awt.*;
import java.util.Random;

public class CentreFactory {
    public static TraineeCentre create() {
        Random r = new Random();
        TraineeCentre tc = null;
        try {
            return CentreType.values()[r.nextInt(CentreType.values().length)].type
                    .getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            new RuntimeException(e);
        }
        return tc;
    }
}

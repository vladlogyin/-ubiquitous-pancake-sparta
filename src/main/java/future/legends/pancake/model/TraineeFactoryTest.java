package future.legends.pancake.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraineeFactoryTest {

    @Test
    @DisplayName("Not Null Test")
    void generateTraineesNullTest() {
        Assertions.assertNotNull(TraineeFactory.generateTrainees());
    }

}
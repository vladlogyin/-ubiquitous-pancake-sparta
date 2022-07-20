package test;

import future.legends.pancake.model.Trainee;
import future.legends.pancake.model.TraineeFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TraineeFactoryTest {

    @Test
    void testGenerateNegativeNum() {
        Collection<Trainee> trainees = TraineeFactory.generateTrainees(-1);
        // explanation: return an empty collection if parameter is invalid to avoid null pointers
        assertEquals(0, trainees.size());
    }

    @Test
    void testGenerateZero() {
        Collection<Trainee> trainees = TraineeFactory.generateTrainees(0);
        // explanation: return an empty collection if parameter is invalid to avoid null pointers
        assertEquals(0, trainees.size());
    }

    @Test
    void testGenerate5() {
        Collection<Trainee> trainees = TraineeFactory.generateTrainees(5);
        assertEquals(5, trainees.size());
    }

}
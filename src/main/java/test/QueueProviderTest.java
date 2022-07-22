package test;

import future.legends.pancake.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class QueueProviderTest {

    QueueProvider qp;

    @BeforeEach
    void setUp() {
        qp = new QueueProvider();
    }

    @Test
    void addNullTrainees() {
        Collection<Trainee> collection = null;
        assertDoesNotThrow(()->qp.addTrainees(collection, true));
    }

    @Test
    void normalFunction() {
        QueueProvider qp = new QueueProvider();
        qp.addTrainees(TraineeFactory.generateTrainees(1), TraineeState.NEW);
        Assertions.assertEquals(1, qp.getAvailableTraineeCount());
        Assertions.assertEquals(0, qp.getAvailableBenchedCount());
        qp.addTrainees(TraineeFactory.generateTrainees(2),TraineeState.BENCHED);
        Assertions.assertEquals(2, qp.getAvailableBenchedCount());
        qp.addTrainees(TraineeFactory.generateTrainees(5),TraineeState.PAUSED);
        Assertions.assertEquals(6, qp.getAvailableTraineeCount());
        Assertions.assertEquals(2, qp.getAvailableBenchedCount());
        qp.getTrainee();
        Assertions.assertEquals(5, qp.getAvailableTraineeCount());
        qp.getTrainee();
        Assertions.assertEquals(4, qp.getAvailableTraineeCount());
        Assertions.assertEquals(2, qp.getAvailableBenchedCount());
    }
    @Test
    void isAvailable() {
        assertFalse(qp.isAvailable());
        addOneTrainee(TraineeCourse.BUSINESS);
        assertTrue(qp.isAvailable());
    }

    @Test
    void getTraineeBeforeAdding() {
        assertThrows(NoSuchElementException.class,()->qp.getTrainee()); // get before add
        addOneTrainee(TraineeCourse.BUSINESS);
        assertDoesNotThrow(()->qp.getTrainee()); // get after add
    }

    @Test
    void getAvailableTraineeCount() {
        assertEquals(0, qp.getAvailableTraineeCount());
        addOneTrainee(TraineeCourse.BUSINESS);
        assertEquals(1, qp.getAvailableTraineeCount());
        assertEquals(1, qp.getAvailableTraineeCount(TraineeCourse.BUSINESS));
        addOneTrainee(TraineeCourse.C);
        assertEquals(2, qp.getAvailableTraineeCount());
        assertEquals(1, qp.getAvailableTraineeCount(TraineeCourse.BUSINESS));
        assertEquals(1, qp.getAvailableTraineeCount(TraineeCourse.C));
        assertEquals(0, qp.getAvailableTraineeCount(TraineeCourse.JAVA));
        assertEquals(0, qp.getAvailableTraineeCount(TraineeCourse.DEVOPS));
    }

    private void addOneTrainee(TraineeCourse tc) {
        Collection<Trainee> collection = new ArrayList<>();
        collection.add(new Trainee(tc));
        qp.addTrainees(collection,false);
    }
}
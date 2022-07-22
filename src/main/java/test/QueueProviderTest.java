package test;

import future.legends.pancake.model.QueueProvider;
import future.legends.pancake.model.Trainee;
import future.legends.pancake.model.TraineeCourse;
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
    void getAvailableCount() {
        assertEquals(0, qp.getAvailableCount());
        addOneTrainee(TraineeCourse.BUSINESS);
        assertEquals(1, qp.getAvailableCount());
        assertEquals(1, qp.getAvailableCount(TraineeCourse.BUSINESS));
        addOneTrainee(TraineeCourse.C);
        assertEquals(2, qp.getAvailableCount());
        assertEquals(1, qp.getAvailableCount(TraineeCourse.BUSINESS));
        assertEquals(1, qp.getAvailableCount(TraineeCourse.C));
        assertEquals(0, qp.getAvailableCount(TraineeCourse.JAVA));
        assertEquals(0, qp.getAvailableCount(TraineeCourse.DEVOPS));
    }

    private void addOneTrainee(TraineeCourse tc) {
        Collection<Trainee> collection = new ArrayList<>();
        collection.add(new Trainee(tc));
        qp.addTrainees(collection,false);
    }
}
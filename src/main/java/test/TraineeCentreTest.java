package test;

import future.legends.pancake.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Queue;

class TraineeCentreTest {

    TraineeCentre tc;
    CentreFactory cf;
    @BeforeEach
    void setUp() {
        cf = new CentreFactory();
        try {
            tc = cf.create();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    @Test
    void enrollNullTrainees() {
        Queue<Trainee> q = null;
        Assertions.assertThrows(NullPointerException.class, ()->{tc.enrollTrainees(q, 0);});
    }

    @Test
    void deleteNullCentre() {
        // exmplanation: if an empty queue is provided, please do nothing instead of crashing
        Assertions.assertDoesNotThrow(()->{cf.delete(null);});
    }


}
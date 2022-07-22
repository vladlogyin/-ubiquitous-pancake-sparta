package test;

import future.legends.pancake.model.Bootcamp;
import future.legends.pancake.model.QueueProvider;
import future.legends.pancake.model.Simulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BootcampTest {

    Bootcamp bootcamp;

    @BeforeEach
    void setUp() {
        bootcamp = new Bootcamp();
    }

    @Test
    void closeInactiveBootcampTest() {
        assertFalse(bootcamp.shouldClose()); // 0 inactive months
        bootcamp.monthPassed();
        assertFalse(bootcamp.shouldClose()); // 1 inactive month
        bootcamp.monthPassed();
        assertFalse(bootcamp.shouldClose()); // 2 inactive months
        bootcamp.monthPassed();
        assertTrue(bootcamp.shouldClose()); // 3 inactive months
    }

}
package test;

import future.legends.pancake.model.Simulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorTest {

    Simulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new Simulator();
    }

    @Test
    void simulateMonth() {
        Assertions.assertEquals(0, simulator.getMonthsPassed());
        simulator.simulateMonth();
        Assertions.assertEquals(1, simulator.getMonthsPassed());
    }

    @Test
    void simulateMonths() {
        Assertions.assertEquals(0, simulator.getMonthsPassed());
        simulator.simulateMonths(13);
        Assertions.assertEquals(13, simulator.getMonthsPassed());
    }

    @Test
    void simulateNegativeMonths() {
        Assertions.assertEquals(0, simulator.getMonthsPassed());
        simulator.simulateMonths(2);
        simulator.simulateMonths(-10);
        Assertions.assertEquals(2, simulator.getMonthsPassed());
    }



}
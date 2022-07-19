package future.legends.pancake.controller;

import future.legends.pancake.model.Simulator;
import future.legends.pancake.viewer.MainViewer;

public class Controller {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        MainViewer mainViewer = new MainViewer();

        simulator.simulateMonths(mainViewer.getSimulationDuration());
        //mainViewer.displaySimulationResults();
    }
}

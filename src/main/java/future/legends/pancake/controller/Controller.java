package future.legends.pancake.controller;

import future.legends.pancake.model.Simulator;
import future.legends.pancake.viewer.MainViewer;
import future.legends.pancake.viewer.SimulatorView;

public class Controller {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        MainViewer mainViewer = new MainViewer();

        int totalMonths = mainViewer.getSimulationDuration();
        if(mainViewer.getSimulationReportEveryMonth())
        {
            for(int month=1;month<=totalMonths;month++)
            {
                simulator.simulateMonth();
                mainViewer.displaySimulationReport(SimulatorView.getMonthlyReport(month,simulator.simData));
            }
        }
        else
        {
            simulator.simulateMonths(totalMonths);
            mainViewer.displaySimulationReport(simulator.toString());
        }
    }
}

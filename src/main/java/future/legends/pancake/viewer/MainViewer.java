package future.legends.pancake.viewer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainViewer {

    static Scanner scanner = new Scanner(System.in);

    public int getSimulationDuration(){

        boolean validInput = false;
        int months = 0;

        while (!validInput){

            System.out.println("How many months would you like the simulation to run for?");

            try{
                months = scanner.nextInt();
                if (months > 0){
                    validInput = true;
                }
                else{
                    throw new InputMismatchException();
                }
            }
            catch(InputMismatchException e){
                System.out.println("\nInvalid Input: Please enter a positive integer that is greater than 0\n");
                scanner.nextLine();
            }
        }

        return months;

    }

    public int getSimulationOutputFrequency(){

        boolean validInput = false;
        int frequencyChoice = 0;

        while (!validInput){

            System.out.println("When would you like to receive simulation information? Select a number: ");
            System.out.println("1. End of simulation");
            System.out.println("2. End of every month");

            try{
                frequencyChoice = scanner.nextInt();
                if (frequencyChoice == 1 || frequencyChoice == 2){
                    validInput = true;
                }
                else{
                    throw new InputMismatchException();
                }
            }
            catch(InputMismatchException e){
                System.out.println("\nInvalid input: Please enter 1 or 2\n");
                scanner.nextLine();
            }

        }

        return frequencyChoice;

    }

    public void displaySimulationReport(int openCentres, int fullCentres, int activeTrainees, int waitingTrainees){

        System.out.println("Number of open centres: " + openCentres);
        System.out.println("Number of full centres: " + fullCentres);
        System.out.println("Number of trainees currently training: " + activeTrainees);
        System.out.println("Number of trainees on waiting list: " + waitingTrainees);

    }

}

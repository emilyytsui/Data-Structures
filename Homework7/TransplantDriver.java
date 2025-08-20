/**
 * This class acts as the main driver for the application.
 *
 * @author Emily Tsui
 */

import java.io.*;
import java.util.Scanner;

public class TransplantDriver {
    public static final String DONOR_FILE = "donors.txt";
    public static final String RECIPIENT_FILE = "recipients.txt";

    /**
     * The main method runs a menu-driven application. It will first attempt to
     * load any previously serialized TransplantGraph object located in
     * 'transplant.obj'. If object is not found, a new TransplantGraph object
     * is created using DONOR_FILE and RECIPIENT_FILE.
     * <p>
     * The program prompts the user for a command to execute an operation.
     * Once a command has been chosen, the program may ask the user
     * for additional information if necessary and performs the operation.
     *
     * <p>
     * The program serializes its TransplantGraph object on program
     * termination, so that it may be loaded at a later time.
     *
     * @param args An array of command-line arguments passed to the program
     */
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        boolean done = false;
        boolean continueLoop = true;
        String name, organ, choice;
        int age;
        BloodType bloodType;
        TransplantGraph transplantGraph;

        try {
            FileInputStream file = new FileInputStream("transplant.obj");
            ObjectInputStream fin = new ObjectInputStream(file);
            transplantGraph = (TransplantGraph) fin.readObject();
            fin.close();
            System.out.println("Loading data from transplant.obj...\n");
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("transplant.obj not found. " +
                    "Creating new TransplantGraph object...");
            System.out.println("Loading data from 'donors.txt'...");
            System.out.println("Loading data from 'recipients.txt'...\n");
            transplantGraph = TransplantGraph.buildFromFiles(DONOR_FILE,
                    RECIPIENT_FILE);
        }

        while (!done) {
            System.out.println("Menu :");
            System.out.print(
                    "\t(LR) - List all recipients\n" +
                    "\t(LO) - List all donors\n" +
                    "\t(AO) - Add new donor\n" +
                    "\t(AR) - Add new recipient\n" +
                    "\t(RO) - Remove donor\n" +
                    "\t(RR) - Remove recipient\n" +
                    "\t(SR) - Sort recipients\n" +
                    "\t(SO) - Sort donors\n" +
                    "\t(Q) - Quit\n\n" +
                    "Please select an option: ");

            String selection = stdin.nextLine().toUpperCase();

            switch (selection) {
                case "LR": //List all recipients
                    System.out.println();
                    transplantGraph.printAllRecipients();
                    System.out.println();
                    break;
                case "LO": //List all donors
                    System.out.println();
                    transplantGraph.printAllDonors();
                    System.out.println();
                    break;
                case "AO": //Add new donor
                    System.out.print("\nPlease enter the organ donor name: ");
                    name = stdin.nextLine();
                    System.out.print("Please enter the blood type of " +
                            name + ": ");
                    bloodType = new BloodType(stdin.nextLine());
                    System.out.print("Please enter the age of " + name + ": ");
                    age = stdin.nextInt();
                    stdin.nextLine();
                    System.out.print("Please enter the organs " + name +
                            " is donating: ");
                    organ = stdin.nextLine();

                    Patient donor = new Patient(name, bloodType, age, organ,
                            true);

                    transplantGraph.addDonor(donor);
                    System.out.println("\nThe organ donor, " + name +
                            ", has been added to the donor list with ID " +
                            donor.getID() + ".\n");
                    break;
                case "AR": //Add new recipient
                    System.out.print("\nPlease enter new recipient's name: ");
                    name = stdin.nextLine();
                    System.out.print("Please enter the recipient's " +
                            "blood type: ");
                    bloodType = new BloodType(stdin.nextLine());
                    System.out.print("Please enter the recipient's age: ");
                    age = stdin.nextInt();
                    stdin.nextLine();
                    System.out.print("Please enter the organ needed: ");
                    organ = stdin.nextLine();

                    Patient recipient = new Patient(name, bloodType, age, organ,
                            false);

                    transplantGraph.addRecipient(recipient);
                    System.out.println("\nThe organ recipient, " + name +
                            ", has been added to the recipient list with ID " +
                            recipient.getID() + ".\n");
                    break;
                case "RO": //Remove donor
                    System.out.print("\nPlease enter the name of the " +
                            "organ donor to remove: ");
                    name = stdin.nextLine();

                    try {
                        transplantGraph.removeDonor(name);
                        System.out.println("\n" + name +
                                " was removed from the organ donor list.\n");
                    } catch (PatientDoesNotExistException ex) {
                        System.out.println("\n" + ex + "\n");
                    }

                    break;
                case "RR": //Remove recipient
                    System.out.print("\nPlease enter the name of the " +
                            "recipient to remove: ");
                    name = stdin.nextLine();

                    try {
                        transplantGraph.removeRecipient(name);
                        System.out.println("\n" + name + " was removed from the " +
                                "organ transplant waitlist.\n");
                    } catch (PatientDoesNotExistException ex) {
                        System.out.println("\n" + ex + "\n");
                    }
                    break;
                case "SR": //Sort recipients
                    while (continueLoop) {
                        System.out.print(
                                "\n\t(I) Sort by ID\n" +
                                "\t(N) Sort by Number of Donors\n" +
                                "\t(B) Sort by Blood Type\n" +
                                "\t(O) Sort by Organ Needed\n" +
                                "\t(Q) Back to Main Menu\n\n" +
                                "Please select an option: ");

                        choice = stdin.nextLine().toUpperCase();

                        switch (choice) {
                            case "I":
                            case "N":
                            case "B":
                            case "O":
                                transplantGraph.sortListAndPrint(
                                        choice, false);
                                break;
                            case "Q":
                                System.out.println("\nReturning to " +
                                        "main menu.\n");
                                continueLoop = false;
                                break;
                            default:
                                System.out.println("\nPlease enter a choice" +
                                        " from the options.");
                        }
                    }

                    continueLoop = true;

                    break;
                case "SO": //Sort donors
                    while (continueLoop) {
                        System.out.print(
                                "\n\t(I) Sort by ID\n" +
                                "\t(N) Sort by Number of Recipients\n" +
                                "\t(B) Sort by Blood Type\n" +
                                "\t(O) Sort by Organ Donated\n" +
                                "\t(Q) Back to Main Menu\n\n" +
                                "Please select an option: ");

                        choice = stdin.nextLine().toUpperCase();

                        switch (choice) {
                            case "I":
                            case "N":
                            case "B":
                            case "O":
                                transplantGraph.sortListAndPrint(
                                        choice, true);
                                break;
                            case "Q":
                                System.out.println("\nReturning to " +
                                        "main menu.\n");
                                continueLoop = false;
                                break;
                            default:
                                System.out.println("\nPlease enter a choice" +
                                        " from the options.");
                        }
                    }

                    continueLoop = true;

                    break;
                case "Q": //Quit the application
                    try {
                        FileOutputStream file =
                                new FileOutputStream("transplant.obj");
                        ObjectOutputStream fout = new ObjectOutputStream(file);

                        fout.writeObject(transplantGraph);
                        fout.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }

                    System.out.println("\nWriting data to transplant.obj...");
                    System.out.println("\nProgram terminating normally...");

                    done = true;
                    break;
                default:
                    System.out.println("\nPlease enter a selection " +
                            "from the menu.\n");
            }
        }
        stdin.close();
    }
}

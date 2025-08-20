/**
 * This class acts as the main driver for the inventory management system.
 *
 * @author Emily Tsui
 */

import java.io.*;
import java.util.Scanner;

public class GroceryDriver {
    /**
     * The main method runs a menu-driven application.
     * The program prompts the user for a command to execute an operation.
     * Once a command has been chosen, the program may ask the user
     * for additional information if necessary and performs the operation.
     *
     * @param args An array of command-line arguments passed to the program
     */
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        boolean done = false;
        String itemCode = "", name, filename;
        int qtyInStore, averageSalesPerDay, onOrder, arrivalDay;
        double price;
        HashedGrocery hashedGrocery;

        try {
            FileInputStream file = new FileInputStream("grocery.obj");
            ObjectInputStream fin = new ObjectInputStream(file);
            hashedGrocery = (HashedGrocery) fin.readObject();
            fin.close();
            System.out.println("HashedGrocery is loaded from grocery.obj\n");
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("Grocery.obj does not exist. " +
                    "Creating new HashedGrocery object...\n");
            hashedGrocery = new HashedGrocery();
        }

        System.out.println("Business Day " + hashedGrocery.getBusinessDay() +
                ".\n");

        while (!done) {
            System.out.println("Menu :\n");
            System.out.print(
                    "(L) Load item catalog\n" +
                    "(A) Add items\n" +
                    "(B) Process Sales\n" +
                    "(C) Display all items\n" +
                    "(N) Move to next business day\n" +
                    "(Q) Quit\n\n" +
                    "Enter option: ");

            String selection = stdin.nextLine().toUpperCase();

            switch (selection) {
                case "L": //Load item catalog
                    System.out.print("\nEnter file to load: ");
                    filename = stdin.nextLine();

                    try {
                        System.out.println();
                        hashedGrocery.addItemCatalog(filename);
                        System.out.println();
                    } catch (FileNotFoundException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("Could not find file\n");
                    }

                    break;
                case "A": //Add item
                    try {
                        System.out.print("\nEnter item code: ");
                        itemCode = stdin.nextLine();

                        System.out.print("Enter item name: ");
                        name = stdin.nextLine();

                        System.out.print("Enter Quantity in store: ");
                        qtyInStore = stdin.nextInt();

                        System.out.print("Enter Average sales per day: ");
                        averageSalesPerDay = stdin.nextInt();

                        System.out.print("Enter price: ");
                        price = stdin.nextDouble();

                        stdin.nextLine();
                        Item newItem = new Item(itemCode, name, qtyInStore,
                                averageSalesPerDay, price);
                        hashedGrocery.addItem(newItem);
                        System.out.println("\n" + itemCode + ": " + name +
                                " added to inventory\n");
                    } catch (ItemCodeAlreadyExistsException ex) {
                        System.out.println("\n" + ex);
                        System.out.println(itemCode + ": Cannot add item " +
                                "as item code already exists\n");
                    }
                    break;
                case "B": //Process Sales
                    System.out.print("\nEnter file to load: ");
                    filename = stdin.nextLine();

                    try {
                        hashedGrocery.processSales(filename);
                        System.out.println();
                    } catch (FileNotFoundException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("Could not find file\n");
                    }
                    break;
                case "C": //Display all items
                    System.out.println("\n" + hashedGrocery.toString());
                    break;
                case "N": //Move to next business day
                    hashedGrocery.nextBusinessDay();
                    System.out.println();
                    break;
                case "Q": //Quit the application
                    try {
                        FileOutputStream file =
                                new FileOutputStream("grocery.obj");
                        ObjectOutputStream fout = new ObjectOutputStream(file);

                        fout.writeObject(hashedGrocery);
                        fout.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }

                    System.out.println("\nHashedGrocery is saved " +
                            "in grocery.obj");
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

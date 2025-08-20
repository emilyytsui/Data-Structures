/**
 * This class contains the main method which is used to run the simulation.
 *
 * @author Emily Tsui
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class DiningSimulator {
    ArrayList<Restaurant> restaurants = new ArrayList<>();
    private int chefs, duration, maxCustomerSize, numRestaurants,
            customersLost, totalServiceTime, customersServed, profit;
    private double arrivalProb;

    /**
     * This is a Constructor used to create a new DiningSimulator object with
     * the specified parameters.
     *
     * @param numRestaurants
     * The number of restaurants to simulate.
     * @param maxCustomerSize
     * The maximum number of customers that can be seated at each restaurant.
     * @param arrivalProb
     * The probability ([0.0, 1.0]) of a new customer arriving.
     * @param chefs
     * The number of chefs preset at each restaurant.
     * @param duration
     * The number of simulation units to perform.
     */
    public DiningSimulator(int numRestaurants, int maxCustomerSize,
                           double arrivalProb, int chefs, int duration) {
        Customer.setTotalCustomers(0);
        this.numRestaurants = numRestaurants;
        this.maxCustomerSize = maxCustomerSize;
        this.arrivalProb = arrivalProb;
        this.chefs = chefs;
        this.duration = duration;
    }

    /**
     * Randomly chooses the food that the Customer orders.
     *
     * @return The food that the Customer orders as a String.
     */
    public String chooseFood() {
        String[] foodOptions = {"Cheeseburger", "Steak", "Grilled Cheese",
                "Chicken Tenders", "Chicken Wings"};
        return foodOptions[randInt(0, foodOptions.length - 1)];
    }

    /**
     * Runs the simulator.
     *
     * @return The average time the customer spent at the restaurant.
     */
    public double simulate() {
        if (numRestaurants < 1 || maxCustomerSize < 1 || arrivalProb < 0 ||
                arrivalProb > 1 || chefs < 1 || duration < 1) {
            System.out.println("\nNo simulation.");
            return -1;
        } else {
            BooleanSource arrival = new BooleanSource(arrivalProb);

            for (int i = 0; i < numRestaurants; i++) {
                restaurants.add(new Restaurant());
            }

            for (int currentSimulationUnit = 1;
                 currentSimulationUnit <= duration; currentSimulationUnit++) {

                System.out.println("\nTime: " + currentSimulationUnit);

                for (int i = 0; i < numRestaurants; i++) {
                    Restaurant currRestaurant = restaurants.get(i);

                    for (int c = 0; c < currRestaurant.size(); c++) {
                        Customer customer = currRestaurant.get(c);
                        customer.setTimeToServe(customer.getTimeToServe() - 5);

                        if (customer.getTimeToServe() == 0) {
                            currRestaurant.remove(customer);
                            c--;

                            totalServiceTime += (currentSimulationUnit -
                                    customer.getTimeArrived()) * 5;
                            profit += customer.getPriceOfFood();
                            customersServed++;
                            System.out.println("Customer #" +
                                    customer.getOrderNumber() +
                                    " has enjoyed their food! $" +
                                    customer.getPriceOfFood() + " profit.");
                        }
                    }
                }

                for (int j = 0; j < numRestaurants; j++) {
                    Restaurant currRestaurant = restaurants.get(j);

                    for (int p = 0; p < 4; p++) {
                        if (arrival.occurs()) {
                            Customer customer = new Customer();
                            customer.setTimeArrived(currentSimulationUnit);

                            System.out.println("Customer #" +
                                    customer.getOrderNumber() +
                                    " has entered Restaurant " + (j + 1) + ".");

                            currRestaurant.enqueue(customer);
                        }
                    }
                }

                for (int k = 0; k < numRestaurants; k++) {
                    Restaurant currRestaurant = restaurants.get(k);

                    for (int c = 0; c < currRestaurant.size(); c++) {
                        Customer customer = currRestaurant.get(c);

                        if (customer.getTimeArrived() == currentSimulationUnit){
                            if (c + 1 > maxCustomerSize) {
                                currRestaurant.remove(customer);
                                System.out.println("Customer #" +
                                        customer.getOrderNumber() +
                                        " cannot be seated! " +
                                        "They have left the restaurant.");
                                customersLost++;
                                c--;
                            } else {
                                customer.setFood(chooseFood());
                                customer.setTimeToServe(
                                        customer.calculateTimeToMake(chefs)
                                        + 15);
                                System.out.println("Customer #" +
                                        customer.getOrderNumber() +
                                        " has been seated with order " +
                                        "\"" + customer.getFood() + "\".");
                            }
                        }
                    }
                }

                for (int r = 0; r < numRestaurants; r++) {
                    System.out.println("R" + (r + 1) + ": " +
                            restaurants.get(r).toString());
                }

            }

            if (customersServed == 0) {
                return 0;
            }

            return (double) totalServiceTime / customersServed;
        }
    }

    /**
     * A helper method that can be used to generate a random number
     * between minVal and maxVal, inclusively.
     *
     * @return The randomly generated number.
     */
    private int randInt(int minVal, int maxVal) {
        return minVal + (int) (Math.random() * (maxVal - minVal + 1));
    }

    /**
     * The main method that allows the user to use the simulation.
     * It prompts the user for the input required to perform the simulation.
     * The simulator runs and then output the results.
     * It prompts the user whether another simulation should be performed.
     *
     * @param args An array of command-line arguments passed to the program
     */
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        boolean done = false;
        String input = "";
        int numRestaurants, maxCustomerSize, chefs, duration;
        double arrivalProb;

        while (!done) {
            System.out.println("Starting simulator...\n");

            try {
                System.out.print("Enter the number of restaurants: ");
                numRestaurants = stdin.nextInt();

                System.out.print("Enter the maximum number of customers a " +
                        "restaurant can serve: ");
                maxCustomerSize = stdin.nextInt();

                System.out.print("Enter the arrival probability " +
                        "of a customer: ");
                arrivalProb = stdin.nextDouble();

                System.out.print("Enter the number of chefs: ");
                chefs = stdin.nextInt();

                System.out.print("Enter the number of simulation units: ");
                duration = stdin.nextInt();

                if (numRestaurants < 1) {
                    System.out.println("\nThere must be at least one " +
                            "restaurant during a simulation unit.");
                }
                if (maxCustomerSize < 1) {
                    System.out.println("\nThe maximum number of customers " +
                            "must be at least one.");
                }
                if (arrivalProb < 0 || arrivalProb > 1) {
                    System.out.println("\nThe arrival probability of a " +
                            "customer must be between 0 and 1.");
                }
                if (chefs < 1) {
                    System.out.println("\nThere should always be at least one" +
                            " chef in the kitchen.");
                }
                if (duration < 1) {
                    System.out.println("\nThere should be at least one " +
                            "simulation unit for the simulator to run.");
                }

                DiningSimulator diningSimulator = new DiningSimulator
                        (numRestaurants, maxCustomerSize, arrivalProb,
                                chefs, duration);
                double avgCustomerTime = diningSimulator.simulate();

                if (avgCustomerTime != -1) {
                    System.out.println("\nSimulation ending...\n");

                    System.out.println("Total customer time: " +
                            diningSimulator.totalServiceTime + " minutes");
                    System.out.println("Total customers served: " +
                            diningSimulator.customersServed);
                    System.out.println("Average customer time lapse: " +
                            String.format("%.2f", avgCustomerTime) +
                            " minutes per order");
                    System.out.println("Total Profit: $" +
                            diningSimulator.profit);
                    System.out.println("Customers that left: " +
                            diningSimulator.customersLost);
                }
            } catch (InputMismatchException ex) {
                System.out.println("\nPlease enter a valid number.");
                stdin.nextLine();
            }

            System.out.print("\nDo you want to try another simulation? (y/n):" +
                    " ");
            input = stdin.next().toLowerCase();

            System.out.println();

            boolean continueLoop = true;

            while (continueLoop) {
                if (input.equals("n")) {
                    continueLoop = false;
                    done = true;
                    System.out.println("Program terminating normally...");
                } else if (input.equals("y")) {
                    continueLoop = false;
                } else {
                    System.out.println("Please enter y or n.");
                    input = stdin.next().toLowerCase();
                    System.out.println();
                }
            }
        }
    }
}

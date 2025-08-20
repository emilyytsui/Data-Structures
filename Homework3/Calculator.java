/**
 * This class acts as a driver for the company's new calculator.
 *
 * @author Emily Tsui
 */

import java.util.EmptyStackException;
import java.util.Scanner; //For user input.
import java.util.InputMismatchException; //If there's a wrong data type input.

public class Calculator {
    /**
     * The main method that allows the user to use the calculator.
     * It runs a menu-driven application.
     * The program prompts the user for a command to execute an operation.
     * Once a command has been chosen,
     * the program may ask the user
     * for additional information if necessary and performs the operation.
     *
     * @param args An array of command-line arguments passed to the program
     */
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        boolean done = false;
        boolean continueEditing = true;
        Equation equation = new Equation();
        HistoryStack historyStack = new HistoryStack();
        String input;
        int position;
        char chToAddOrReplace;

        System.out.println("Welcome to calculat0r.\n");

        while (!done) {
            System.out.print(
                    "[A] Add new equation\n" +
                    "[F] Change equation from history\n" +
                    "[B] Print previous equation\n" +
                    "[P] Print full history\n" +
                    "[U] Undo\n" +
                    "[R] Redo\n" +
                    "[C] Clear history\n" +
                    "[Q] Quit\n\n" +
                    "Select an option: ");

            String selection = stdin.nextLine().toUpperCase();

            switch (selection) {
                case "A": //Add a new equation, create and solve (infix).
                    System.out.print("\nPlease enter an equation (in-fix " +
                            "notation): ");
                    input = stdin.nextLine();
                    equation = new Equation(input);
                    historyStack.push(equation);

                    if (equation.getBalanced()) {
                        System.out.println("The equation is balanced and the " +
                                "answer is " + String.format("%.3f",
                                equation.getAnswer()) + "\n");
                    } else {
                        System.out.println("The equation is not balanced " +
                                "but saved.\n");
                    }
                    break;
                case "F": //Change equation in history.
                    try {
                        System.out.print("\nWhich equation would you like to " +
                                "change? ");
                        position = stdin.nextInt();
                        stdin.nextLine();

                        equation = historyStack.getEquation(position);

                        System.out.println("\nEquation at position " +
                                position + ": " + equation.getEquation());

                        StringBuilder equationBeingModified =
                                new StringBuilder(equation.getEquation());

                        while (continueEditing) {
                            System.out.print("What would you like to do to " +
                                    "the equation (Replace / remove / add)? ");
                            input = stdin.nextLine().toLowerCase();

                            switch (input) {
                                case "replace":
                                    System.out.print("What position would " +
                                            "you like to change? ");
                                    position = stdin.nextInt();
                                    stdin.nextLine();
                                    System.out.print("What would you like to " +
                                            "replace it with? ");
                                    chToAddOrReplace =
                                            stdin.nextLine().charAt(0);
                                    equationBeingModified.setCharAt(
                                            position - 1,
                                            chToAddOrReplace);
                                    break;
                                case "remove":
                                    System.out.print("What position would " +
                                            "you like to remove? ");
                                    position = stdin.nextInt();
                                    stdin.nextLine();
                                    equationBeingModified.deleteCharAt(
                                            position - 1);
                                    break;
                                case "add":
                                    System.out.print("What position would " +
                                            "you like to add something? ");
                                    position = stdin.nextInt();
                                    stdin.nextLine();
                                    System.out.print("What would you like " +
                                            "to add? ");
                                    chToAddOrReplace =
                                            stdin.nextLine().charAt(0);
                                    equationBeingModified.insert(
                                            position - 1,
                                            chToAddOrReplace);
                                    break;
                                default:
                                    System.out.println("Please enter a choice" +
                                            " from the options.");
                            }

                            System.out.println("\nEquation: " +
                                    equationBeingModified.toString());
                            System.out.print("Would you like to make any " +
                                    "more changes? ");

                            input = stdin.nextLine().toLowerCase();

                            if (input.equals("n") || input.equals("no")) {
                                equation = new Equation(
                                        equationBeingModified.toString());
                                historyStack.push(equation);

                                if (equation.getBalanced()) {
                                    System.out.println("The equation is " +
                                            "balanced and the answer is: " +
                                            String.format("%" + ".3f",
                                            equation.getAnswer()) + "\n");
                                } else {
                                    System.out.println("Equation is not " +
                                            "balanced.\n");
                                }
                                continueEditing = false;
                            }
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("\nPlease enter a valid " +
                                "number.\n");
                        stdin.nextLine();
                    } catch (InvalidPositionException e) {
                        System.out.println("\nNo equation at this position.\n");
                    }
                    break;
                case "B": //Print previous equation.
                    if (historyStack.empty()) {
                        System.out.println("Calculator History is empty.\n");
                    } else {
                        System.out.println();
                        historyStack.printPreviousEquation();
                    }
                    break;
                case "P": //Print full history.
                    System.out.println("\n" + historyStack.toString());
                    break;
                case "U": //Undo last equation.
                    try {
                        equation = historyStack.peek();
                        historyStack.undo();
                        System.out.println("Equation '" +
                                equation.getEquation() + "' undone.\n");
                    } catch (EmptyStackException ex) {
                        System.out.println("\nNo equation to undo.\n");
                    }
                    break;
                case "R": //Redo last equation which was undone.
                    try {
                        historyStack.redo();
                        equation = historyStack.peek();
                        System.out.println("Redoing equation '" +
                                equation.getEquation() + "'.\n");
                    } catch (NoLastUndoneEquationException ex) {
                        System.out.println("\nNo equation to redo.\n");
                    }
                    break;
                case "C": //Clear history.
                    historyStack = new HistoryStack();
                    System.out.println("\nResetting calculator.\n");
                    break;
                case "Q": //Terminates the program.
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

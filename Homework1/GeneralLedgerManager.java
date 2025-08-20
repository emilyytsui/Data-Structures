/**
 * This class acts as a driver for the ledger.
 *
 * @author Emily Tsui
 */

import java.util.Scanner; //For user input.
import java.util.InputMismatchException; //If there's a wrong data type input.

public class GeneralLedgerManager {
    /**
     * The main method runs a menu driven application.
     * The program prompts the user for a command to execute an operation.
     * Once a command has been chosen,
     * the program may ask the user
     * for additional information if necessary and performs the operation.
     *
     * @param args
     * An array of command-line arguments passed to the program
     */
    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);
        GeneralLedger ledger = new GeneralLedger();
        GeneralLedger backupCopy = new GeneralLedger();
        boolean done = false;
        String date = "";
        double amount = 0;
        String description = "";
        int position = 0;

        while(!done){
            System.out.print(
                    "(A) Add Transaction\n" +
                    "(G) Get Transaction\n" +
                    "(R) Remove Transaction\n" +
                    "(P) Print Transactions in General Ledger\n" +
                    "(F) Filter by Date\n" +
                    "(L) Look for Transaction\n" +
                    "(S) Size\n" +
                    "(B) Backup\n" +
                    "(PB) Print Transactions in Backup\n" +
                    "(RB) Revert to Backup\n" +
                    "(CB) Compare Backup with Current\n" +
                    "(PF) Print Financial Information\n" +
                    "(Q) Quit\n\n" +
                    "Enter a selection: ");

            String selection = stdin.nextLine().toUpperCase();

            switch(selection){
                case "A": //Adds a new transaction into the ledger.
                    try{
                        System.out.print("\nEnter Date: ");
                        date = stdin.nextLine();

                        System.out.print("Enter Amount ($): ");
                        amount = stdin.nextDouble();

                        stdin.nextLine();
                        System.out.print("Enter Description: ");
                        description = stdin.nextLine();

                        Transaction newTransaction = new Transaction(
                                date, amount, description);
                        ledger.addTransaction(newTransaction);

                        System.out.println("\nTransaction successfully added " +
                                "to the general ledger.\n");
                    }catch(InputMismatchException ex){
                        System.out.println("\nPlease enter a valid " +
                                "number for amount.\n");
                        stdin.nextLine();
                    }catch(TransactionAlreadyExistsException ex){
                        System.out.println("\n" + ex);
                        System.out.println("Transaction not added: " +
                                "Transaction already exists " +
                                "in the general ledger.\n");
                    }catch(FullGeneralLedgerException |
                           InvalidTransactionException ex){
                        System.out.println("\n" + ex);
                        System.out.println("Transaction was not successfully " +
                                "added to the general ledger.\n");
                    }
                    break;
                case "G": //Displays information of Transaction at position.
                    try{
                        System.out.print("\nEnter Position: ");
                        position = stdin.nextInt();
                        stdin.nextLine();

                        ledger.getTransaction(position);

                        System.out.println();
                        System.out.printf("%-8s%-10s%12s%12s%-5s%-100s",
                                "No.", "Date", "Debit", "Credit",
                                "", "Description");
                        System.out.println("\n-------------------------------" +
                                "------------------------" +
                                "--------------------------------------------");
                        System.out.printf("%-8s", position);
                        System.out.println(
                                ledger.getTransaction(position).toString());
                        System.out.println("\nSuccessfully got " +
                                "and printed transaction " +
                                "at position " + position + ".\n");
                    }catch(InputMismatchException ex){
                        System.out.println("\nPlease enter a valid " +
                                "position number.\n");
                        stdin.nextLine();
                    }catch (InvalidLedgerPositionException ex){
                        System.out.println("\n" + ex);
                        System.out.println("No such transaction.\n");
                    }
                    break;
                case "R": //Removes the transaction at the given position.
                    try{
                        System.out.print("\nEnter Position: ");
                        position = stdin.nextInt();
                        stdin.nextLine();

                        ledger.removeTransaction(position);
                        System.out.println("\nTransaction has been " +
                                "successfully removed from the general ledger" +
                                ".\n");
                    }catch(InputMismatchException ex){
                        System.out.println("\nPlease enter a valid " +
                                "position number.\n");
                        stdin.nextLine();
                    }catch (InvalidLedgerPositionException ex){
                        System.out.println("\n" + ex);
                        System.out.println("Transaction not removed: " +
                                "No such transaction in the general ledger.\n");
                    }
                    break;
                case "P": //Displays all transaction in the General Ledger.
                    if(ledger.size() > 0){
                        System.out.println();
                        ledger.printAllTransactions();
                        System.out.println("\nSuccessfully printed all " +
                                "transactions in the General Ledger.\n");
                    }else{
                        System.out.println("\nNo transactions currently " +
                                "in the general ledger.\n");
                    }
                    break;
                case "F": //Displays all transactions on the specified date.
                    System.out.print("\nEnter Date: ");
                    date = stdin.nextLine();

                    if(ledger.transactionWithDateExists(date)){
                        System.out.println();
                        GeneralLedger.filter(ledger, date);
                        System.out.println("\nSuccessfully printed all " +
                                "transactions in the General Ledger " +
                                "that were posted on " + date + ".\n");
                    }else{
                        System.out.println("\nNo transactions found for "
                                + date + ".\n");
                    }
                    break;
                case "L": //Determines if a transaction is in current ledger.
                    try{
                        System.out.print("\nEnter Date: ");
                        date = stdin.nextLine();

                        System.out.print("Enter Amount ($): ");
                        amount = stdin.nextDouble();

                        stdin.nextLine();
                        System.out.print("Enter Description: ");
                        description = stdin.nextLine();

                        Transaction newTransaction =
                                new Transaction(date, amount, description);
                        boolean transactionExists =
                                ledger.exists(newTransaction);

                        if(transactionExists){
                            System.out.println();
                            System.out.printf("%-8s%-10s%12s%12s%-5s%-100s",
                                    "No.", "Date",
                                    "Debit", "Credit", "", "Description");
                            System.out.println("\n---------------------------" +
                                    "--------------------------------" +
                                    "----------------------------------------");
                            position = ledger.getPositionOfTransaction(
                                    newTransaction);
                            System.out.printf("%-8s", position);
                            System.out.println(
                                    ledger.getTransaction(position).toString());

                            System.out.println("\nTransaction successfully " +
                                    "found in the general ledger.\n");
                        }else{
                            System.out.println("\nTransaction not found " +
                                    "in the general ledger.\n");
                        }
                    }catch(InputMismatchException ex){
                        System.out.println("\nPlease enter a valid " +
                                "number for amount.\n");
                        stdin.nextLine();
                    }catch (IllegalArgumentException |
                            InvalidTransactionException |
                            InvalidLedgerPositionException ex){
                        System.out.println("\n" + ex);
                        System.out.println("Transaction not found " +
                                "in the general ledger.\n");
                    }
                    break;
                case "S": //Determines the number of transactions in the ledger.
                    System.out.println("\nThere are " + ledger.size() +
                            " transactions currently in the general ledger.\n");
                    break;
                case "B": //Creates a copy of the given General Ledger.
                    if(ledger.size() != 0){
                        backupCopy = (GeneralLedger) ledger.clone();
                        System.out.println("\nCreated a backup of the " +
                                "current general ledger.\n");
                    }else{
                        System.out.println("Copy of General Ledger not " +
                                "successfully created: ledger size is 0.\n");
                    }
                    break;
                case "PB": //Displays all transactions in the backed-up copy.
                    if(backupCopy.size() != 0){
                        System.out.println();
                        backupCopy.printAllTransactions();
                        System.out.println("\nSuccessfully printed all " +
                                "transactions in the backed-up copy of " +
                                "General Ledger.\n");
                    }else{
                        System.out.println("\nNo backup exists.\n");
                    }
                    break;
                case "RB": //Reverts the current ledger to the backed-up copy.
                    if(backupCopy.size() != 0){
                        ledger = (GeneralLedger) backupCopy.clone();
                        System.out.println("\nGeneral ledger successfully " +
                                "reverted to the backup copy.\n");
                    }else{
                        System.out.println("General Ledger not " +
                                "successfully reverted: no backup exists.\n");
                    }
                    break;
                case "CB": //Compares backed-up ledger with the current ledger.
                    if(ledger.equals(backupCopy)){
                        System.out.println("\nThe current general ledger is " +
                                "the same as the backup copy.\n");
                    }else{
                        System.out.println("\nThe current general ledger is " +
                                "NOT the same as the backup copy.\n");
                    }
                    break;
                case "PF": //Displays financial information of this transaction.
                    System.out.println();
                    ledger.printFinancialInformation();
                    System.out.println("\nSuccessfully printed the " +
                            "financial information of General Ledger.\n");
                    break;
                case "Q": //Terminates the program.
                    System.out.println("\nProgram terminating successfully...");
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

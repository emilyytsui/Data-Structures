/**
 * This class implements an abstract data type for a list of Transactions
 * supporting some common operations on such lists.
 *
 * @author Emily Tsui
 */

public class GeneralLedger {
    public static final int MAX_TRANSACTIONS = 50;
    private Transaction[] ledger;
    private double totalDebitAmount;
    private double totalCreditAmount;
    private int sizeCounter; //Number of Transactions currently in this ledger.

    /**
     * Constructs an instance of the GeneralLedger
     * with no Transaction objects in it.
     *
     * Postcondition:
     * This GeneralLedger has been initialized to an empty list of Transactions.
     */
    public GeneralLedger(){
        ledger = new Transaction[MAX_TRANSACTIONS];
        totalDebitAmount = 0;
        totalCreditAmount = 0;
        sizeCounter = 0;
    }

    /**
     * Adds newTransaction into this GeneralLedger if it does not already exist.
     *
     * @param newTransaction
     * The new Transaction to add to the ledger.
     *
     * Precondition:
     * The Transaction object has been instantiated.
     * The number of Transaction objects in this GeneralLedger
     * is less than MAX_TRANSACTIONS.
     *
     * Postcondition:
     * New transaction is now listed in the correct order in the list.
     * All Transactions whose date is newer than or equal
     * to newTransaction are moved back one position.
     *
     * @throws FullGeneralLedgerException
     * Thrown if there is no more room
     * in this GeneralLedger to record additional transactions.
     * @throws InvalidTransactionException
     * Thrown if the transaction amount is 0 or if the date is invalid.
     * @throws TransactionAlreadyExistsException
     * Thrown if there is already a transaction
     * in this GeneralLedger which is equivalent to newTransaction.
     */
    public void addTransaction(Transaction newTransaction)
            throws FullGeneralLedgerException, InvalidTransactionException,
            TransactionAlreadyExistsException{
        if(this.size() == MAX_TRANSACTIONS){
            throw new FullGeneralLedgerException("General ledger is full.");
        }
        if(newTransaction.getAmount() == 0){
            throw new InvalidTransactionException(
                    "Transaction amount cannot be 0.");
        }
        if(!Transaction.validDate(newTransaction.getDate())){
            throw new InvalidTransactionException(
                    "Transaction date is invalid.");
        }
        if(this.exists(newTransaction)){
            throw new TransactionAlreadyExistsException(
                    "Transaction already exists in ledger.");
        }
        else{
            int insertPosition = sizeCounter;

            while(insertPosition > 0 &&
                    newTransaction.getDate().compareTo(
                            ledger[insertPosition - 1].getDate()) < 0){
                ledger[insertPosition] = ledger[insertPosition - 1];
                insertPosition--;
            }

            this.ledger[insertPosition] = newTransaction;

            double amount = newTransaction.getAmount();

            if(amount > 0){
                totalDebitAmount += amount;
            }else{
                totalCreditAmount += amount;
            }

            sizeCounter++;
        }
    }

    /**
     * Removes the transaction located at position from this GeneralLedger.
     *
     * @param position
     * The 1-based index of the Transaction to remove.
     *
     * Precondition:
     * This generalLedger has been instantiated.
     * 1 <= position <= items_currently_in_list.
     *
     * Postcondition:
     * The Transaction at the desired position has been removed.
     * All transactions that were originally greater than or equal
     * to position are moved backward one position.
     *
     * @throws InvalidLedgerPositionException
     * Thrown if position is not valid.
     */
    public void removeTransaction(int position)
            throws InvalidLedgerPositionException{
        if(position < 1 || position > this.size()){
            throw new InvalidLedgerPositionException("Position is not valid.");
        }else{
            Transaction remove = ledger[position - 1];

            for(int i = position - 1; i < this.size(); i++){
                ledger[i] = ledger[i + 1];
            }

            this.ledger[sizeCounter - 1] = null;

            double amount = remove.getAmount();

            if(amount > 0){
                totalDebitAmount -= amount;
            }else{
                totalCreditAmount -= amount;
            }

            sizeCounter--;
        }
    }

    /**
     * Returns a reference to the Transaction object located at position.
     *
     * @param position
     * The position in this GeneralLedger to retrieve.
     *
     * Precondition:
     * The GeneralLedger has been instantiated.
     * 1 <= position <= items_currently_in_list.
     *
     * @return
     * The Transaction at the specified position in this GeneralLedger object.
     *
     * @throws InvalidLedgerPositionException
     * Indicates that position is not within the valid range.
     */
    public Transaction getTransaction(int position)
            throws InvalidLedgerPositionException{
        if(position < 1 || position > this.size()){
            throw new InvalidLedgerPositionException("Position is not valid.");
        }else{
            return this.ledger[position - 1];
        }
    }

    /**
     * Gets the position of where Transaction is in this GeneralLedger.
     *
     * @param transaction
     * The Transaction to be found in this GeneralLedger.
     *
     * @return
     * The position of the Transaction, -1 if not found.
     */
    public int getPositionOfTransaction(Transaction transaction){
        for(int i = 0; i < this.size(); i++){
            if(ledger[i].equals(transaction)){
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Prints all transactions that were posted on the specified date.
     *
     * @param generalLedger
     * The ledger of transactions to search in.
     * @param date
     * The date of the transaction(s) to search for.
     *
     * Precondition:
     * This GeneralLedger object has been instantiated.
     *
     * Postcondition:
     * Displays a neatly formatted table of all Transactions
     * that have taken place on the specified date.
     */
    public static void filter(GeneralLedger generalLedger, String date){
        System.out.printf("%-8s%-10s%12s%12s%-5s%-100s",
                "No.", "Date", "Debit", "Credit", "", "Description");
        System.out.println("\n-----------------------------------------------" +
                "----------------------------------------------------");

        for(int i = 0; i < generalLedger.size(); i++){
            if(generalLedger.ledger[i].getDate().equals(date)){
                System.out.printf("%-8d", i + 1);
                System.out.println(generalLedger.ledger[i].toString());
            }
        }
    }

    /**
     * Checks whether any transaction with a specific date exists.
     *
     * @param date
     * The date of the transaction.
     *
     * @return
     * True if there's a transaction in the ledger that happened on date.
     */
    public boolean transactionWithDateExists(String date){
        for(int i = 0; i < this.size(); i++){
            if(this.ledger[i].getDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a deep copy of this GeneralLedger.
     * Subsequent changes to the copy will not affect
     * the original and vice versa.
     *
     * Precondition:
     * This GeneralLedger object has been instantiated.
     *
     * @return
     * A copy (backup) of this GeneralLedger object.
     */
    @Override
    public Object clone(){
        GeneralLedger ledgerCopy = new GeneralLedger();

        for(int i = 0; i < this.size(); i++){
            ledgerCopy.ledger[i] = (Transaction) this.ledger[i].clone();
        }

        ledgerCopy.totalDebitAmount = this.totalDebitAmount;
        ledgerCopy.totalCreditAmount = this.totalCreditAmount;
        ledgerCopy.sizeCounter = this.sizeCounter;

        return ledgerCopy;
    }


    /**
     * Checks whether a certain transaction is contained in the ledger.
     *
     * @param transaction
     * The Transaction to check for.
     *
     * Precondition:
     * This GeneralLedger and transaction have been instantiated.
     *
     * @return
     * True if this GeneralLedger contains transaction, false otherwise.
     *
     * @throws IllegalArgumentException
     * Thrown if transaction is not a valid Transaction object.
     */
    public boolean exists(Transaction transaction)
            throws IllegalArgumentException{
        if(transaction == null || !(transaction instanceof Transaction)){
            throw new IllegalArgumentException("Transaction is not a valid " +
                    "Transaction object.");
        }else{
            for(int i = 0; i < this.size(); i++){
                if(ledger[i].equals(transaction)){
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns the number of Transactions currently in this ledger.
     *
     * Precondition:
     * This GeneralLedger has been instantiated.
     *
     * @return
     * The number of Transactions in this ledger.
     */
    public int size(){
        return sizeCounter;
    }

    /**
     * Prints a neatly formatted table of each item
     * in the list with its position number.
     *
     * Precondition:
     * This GeneralLedger has been instantiated.
     *
     * Postcondition:
     * All transactions contained within this GeneralLedger
     * are printed in a neatly formatted table.
     */
    public void printAllTransactions(){
        System.out.println(this.toString());
    }

    /**
     * Prints the sum of all debit dollar amounts for all transactions
     * side-by-side with the sum of all credit dollar amount of all
     * transactions, and the net worth value of the account.
     */
    public void printFinancialInformation(){
        System.out.println("Financial Data for Jack's Account");
        System.out.println("-------------------------------------------------" +
                "--------------------------------------------------");
        System.out.printf("%12s: $%-8.2f%n", "Assets", totalDebitAmount);
        System.out.printf("%12s: $%-8.2f%n", "Liabilities",
                Math.abs(totalCreditAmount));
        System.out.printf("%12s: $%-8.2f%n", "Net Worth",
                totalDebitAmount - Math.abs(totalCreditAmount));
    }

    /**
     * Returns a String representation of this GeneralLedger object,
     * which is a neatly formatted table of each Transaction contained
     * in this ledger on its own line with its position number.
     *
     * @return
     * A String representation of this GeneralLedger object.
     */
    @Override
    public String toString(){
        String header = String.format("%-8s%-10s%12s%12s%-5s%-100s",
                "No.", "Date", "Debit", "Credit", "", "Description");
        String line = "------------------------------------------------------" +
                "---------------------------------------------";
        StringBuilder formatted = new StringBuilder(header + "\n" + line);

        for(int i = 0; i < this.size(); i++){
            formatted.append("\n");
            formatted.append(String.format("%-8d%s", i + 1,
                    this.ledger[i].toString()));
        }

        return formatted.toString();
    }

    /**
     * Compares this GeneralLedger with another object
     * to check if they are equal.
     *
     * @param obj
     * The object being compared to this GeneralLedger.
     *
     * @return
     * True if that obj refers to a GeneralLedger object with
     * the same attributes as this GeneralLedger, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GeneralLedger){
            GeneralLedger generalLedger = (GeneralLedger) obj;

            if(this.size() != generalLedger.size()){
                return false;
            }

            for(int i = 0; i < this.size(); i++){
                if (!this.ledger[i].equals(generalLedger.ledger[i])){
                    return false;
                }
            }

            return this.totalDebitAmount == generalLedger.totalDebitAmount &&
                    this.totalCreditAmount == generalLedger.totalCreditAmount;
        }else{
            return false;
        }
    }
}

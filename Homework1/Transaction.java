/**
 * This class represents a financial transaction,
 * containing details like the date, amount, and description of the transaction.
 *
 * @author Emily Tsui
 */
public class Transaction {
    private String date;
    private double amount;
    private String description;

    /**
     * This is a Constructor used to create a new Transaction object.
     */
    public Transaction(){
        date = "";
        amount = 0;
        description = "";
    }

    /**
     * This is a Constructor used to create
     * a new Transaction object with the specified parameters.
     *
     * @param date
     * The date of the transaction, inputted in yyyyy/mm/dd format.
     * @param amount
     * The change in cash as a result of this transaction.
     * @param description
     * The description of what this transaction is.
     *
     * @throws InvalidTransactionException
     * Thrown if the date is invalid or amount is 0.
     * The date must be inputted  in yyyy/mm/dd format,
     * year must be between 1900 and 2050 inclusive,
     * the month must be between 1 and 12 inclusive,
     * and the day must be between 1 and 30 inclusive.
     */
    public Transaction(String date, double amount, String description)
            throws InvalidTransactionException {
        if(!validDate(date)){
            throw new InvalidTransactionException("Date is invalid.");
        }
        if(amount == 0){
            throw new InvalidTransactionException(
                    "Transaction amount cannot be 0.");
        }
        else{
            this.date = date;
            this.amount = amount;
            this.description = description;
        }
    }

    /**
     * Checks to see if the is valid.
     * The date must be inputted in yyyy/mm/dd format,
     * year must be between 1900 and 2050 inclusive,
     * the month must be between 1 and 12 inclusive,
     * and the day must be between 1 and 30 inclusive.
     *
     * @param date
     * The date of the transaction.
     *
     * @return
     * True if the date is valid, false otherwise.
     */
    public static boolean validDate(String date){
        String[] dateArr = date.split("/");

        if(dateArr.length != 3){
            return false;
        }else{
            int year = Integer.parseInt(dateArr[0]);
            int month = Integer.parseInt(dateArr[1]);
            int day = Integer.parseInt(dateArr[2]);

            return (year >= 1900 && year <= 2050)
                    && (month >= 1 &&  month <= 12) && (day >= 1 && day <= 30);
        }

    }

    /**
     * Gets the date of this transaction.
     *
     * @return
     * The date of the transaction as a String in yyyyy/mm/dd format.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the amount (change in cash as a result of this transaction).
     *
     * @return
     * The amount of the transaction as a double.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the description of this transaction.
     *
     * @return
     * The description of what this transaction is as a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Creates a deep copy of this Transaction.
     *
     * @return
     * A new Transaction object with the same
     * date, amount, and description as this Transaction.
     */
    @Override
    public Object clone() {
        try{
            Transaction copy = new Transaction(
                    this.date, this.amount, this.description);
            return copy;
        }catch (InvalidTransactionException ex){
            System.out.println(ex + "Can't clone Transaction.");
            return null;
        }
    }

    /**
     * Compares this Transaction with another object to check if they are equal.
     *
     * @param obj
     * The object being compared to this Transaction.
     *
     * @return
     * True if that obj refers to a Transaction object with
     * the same attributes as this Transaction, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transaction){
            Transaction t = (Transaction) obj;
            return this.date.equals(t.date) && this.amount == t.amount
                    && this.description.equals(t.description);
        }else{
            return false;
        }
    }

    /**
     * Returns a String representation of this Transaction object,
     * which includes it's information of date, debit/credit, and description.
     *
     * @return
     * A String representation of this GeneralLedger object.
     */
    @Override
    public String toString(){
        double debit = 0;
        if(this.amount > 0){
            debit = this.amount;
        }

        double credit = 0;
        if (this.getAmount() < 0) {
            credit = Math.abs(this.amount);
        }

        return String.format("%-8s%12s%12s%-5s%-20s",
                this.date,
                (debit > 0) ? String.format("%.2f", debit) : "",
                (credit > 0) ? String.format("%.2f", credit) : "",
                "", this.description);
    }
}
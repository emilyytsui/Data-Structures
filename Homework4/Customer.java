/**
 * This class represents a customer that will dine at the restaurant.
 *
 * @author Emily Tsui
 */
public class Customer {
    private static int totalCustomers;
    private int orderNumber, priceOfFood, timeArrived, timeToServe;
    private String food;

    /**
     * This is a Constructor used to create a new Customer object.
     */
    public Customer() {
        totalCustomers++;
        orderNumber = totalCustomers;
    }

    /**
     * Gets the food that a customer has ordered.
     *
     * @return The food that this Customer has ordered as a String.
     */
    public String getFood() {
        return food;
    }

    /**
     * Gets the time it will take to serve the customer before the customer
     * leaves the restaurant in minutes
     *
     * @return The time to serve this Customer as an int.
     */
    public int getTimeToServe() {
        return timeToServe;
    }

    /**
     * Gets the order number of the customer that is determined using
     * totalCustomers.
     *
     * @return The order number of this Customer as an int.
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Gets the price of the food that will be added to the restaurant's
     * profit once the customer pays the bill.
     *
     * @return The price of the food as an int.
     */
    public int getPriceOfFood() {
        return priceOfFood;
    }

    /**
     * Gets the simulation unit when this Customer arrived at the restaurant.
     *
     * @return The simulation unit this Customer arrived at as an int.
     */
    public int getTimeArrived() {
        return timeArrived;
    }

    /**
     * Sets the food that this Customer has ordered.
     * It also updates the priceOfFood and timeToServe to accurately
     * represent the food that this Customer has ordered.
     *
     * @param food the food
     */
    public void setFood(String food) {
        this.food = food;
        priceOfFood = this.foodPrice();
        timeToServe = this.avgTimeToMake();
    }

    /**
     * Sets the time the Customer arrived at the Restaurant.
     *
     * @param timeArrived
     * The simulation unit when this Customer arrived at the restaurant.
     */
    public void setTimeArrived(int timeArrived) {
        this.timeArrived = timeArrived;
    }

    /**
     * Sets the time it will take to serve the customer before the customer
     * leaves the restaurant in minute.
     *
     * @param timeToServe The time left to serve this Customer.
     */
    public void setTimeToServe(int timeToServe) {
        this.timeToServe = timeToServe;
    }

    /**
     * Sets the total customers in this chain.
     *
     * @param totalCustomers
     * The total number of customers that have showed up to the chain.
     */
    public static void setTotalCustomers(int totalCustomers) {
        Customer.totalCustomers = totalCustomers;
    }

    /**
     * Returns the abbreviation of the food.
     *
     * @return
     * The abbreviation of the food that this Customer chooses as a String.
     */
    public String foodAbbreviation() {
        switch (food.toLowerCase()) {
            case "steak":
                return "S";
            case "chicken wings":
                return "CW";
            case "cheeseburger":
                return "C";
            case "chicken tenders":
                return "CT";
            case "grilled cheese":
                return "GC";
            default:
                return "";
        }
    }

    /**
     * Returns the price of the food that this Customer chooses.
     *
     * @return The price of the food as an int.
     */
    public int foodPrice() {
        switch (food.toLowerCase()) {
            case "steak":
                return 25;
            case "chicken wings":
                return 20;
            case "cheeseburger":
                return 15;
            case "chicken tenders", "grilled cheese":
                return 10;
            default:
                return 0;
        }
    }

    /**
     * Returns the average time a menu option takes to make. Average is a
     * restaurant that has 3 chefs.
     *
     * @return The average time it takes to make the food as an int.
     */
    public int avgTimeToMake() {
        switch (food.toLowerCase()) {
            case "steak", "chicken wings":
                return 30;
            case "cheeseburger", "chicken tenders":
                return 25;
            case "grilled cheese":
                return 15;
            default:
                return 0;
        }
    }

    /**
     * Calculates time to make the food the Customer chooses.
     * It's calculated based on the number of chefs in the Restaurant.
     *
     * @param numChefs The number of chefs in the Restaurant where this
     *                 Customer is at.
     * @return The time it takes to make the food as an int.
     */
    public int calculateTimeToMake(int numChefs) {
        if (numChefs > 3) {
            return avgTimeToMake() - Math.min((5 * (numChefs - 3)), 10);
        } else if (numChefs < 3) {
            return avgTimeToMake() + (5 * (3 - numChefs));
        } else {
            return avgTimeToMake();
        }
    }

    /**
     * Returns a String representation of this Customer object.
     * It's in the format [#orderNumber, food, timeToServe min.] with food
     * being abbreviated by initial.
     *
     * @return A textual representation of all the information for
     * this Customer object.
     */
    public String toString() {
        return "[#" + orderNumber + ", " + foodAbbreviation() + ", " +
                timeToServe + " min.]";
    }
}

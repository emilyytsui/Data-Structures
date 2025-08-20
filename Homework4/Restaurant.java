/**
 * This class functions as a Queue.
 * The queue will take Customer objects and seat them.
 *
 * @author Emily Tsui
 */

import java.util.LinkedList;

public class Restaurant extends LinkedList<Customer> {

    /**
     * This is a Constructor used to create a new Restaurant object.
     */
    public Restaurant() {
    }

    /**
     * Adds a new customer c to the restaurant.
     *
     * @param c The customer to add to the restaurant.
     */
    public void enqueue(Customer c) {
        super.addLast(c);
    }

    /**
     * Removes and returns the first Customer in the restaurant.
     *
     * @return The first Customer that was in the restaurant
     */
    public Customer dequeue() {
        return super.removeFirst();
    }

    /**
     * Gets the first Customer in the restaurant.
     *
     * @return The first Customer in the restaurant.
     */
    public Customer peek() {
        return super.getFirst();
    }

    /**
     * Returns the number of Customers in the restaurant.
     *
     * @return The size of the Restaurant as an int.
     */
    public int size() {
        return super.size();
    }

    /**
     * Checks to see if this Restaurant is empty.
     *
     * @return True if there are no Customers in the restaurant,
     * false otherwise.
     */
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Returns a String representation of this Restaurant object,
     * which includes it's information of each customer in the Restaurant.
     *
     * @return
     * A textual representation of all the information for
     * this Restaurant object.
     */
    public String toString() {
        if (this.size() == 0) {
            return "{}";
        } else {
            StringBuilder formatted = new StringBuilder("{");

            for (int i = 0; i < this.size(); i++) {
                formatted.append(this.get(i).toString() + ", ");
            }

            formatted.replace(formatted.length() - 2,
                    formatted.length(), "}");

            return formatted.toString();
        }
    }
}

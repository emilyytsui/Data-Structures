/**
 * This class represents an item in a grocery store, containing details like
 * item code, name of the item, quantity in store, average sales per day,
 * restocking order, business arrival day, and price.
 *
 * @author Emily Tsui
 */

import java.io.Serializable;

public class Item implements Serializable {
    private String itemCode;
    private String name;
    private int qtyInStore;
    private int averageSalesPerDay;
    private int onOrder;
    private int arrivalDay;
    private double price;

    /**
     * This is a Constructor used to create a new Item object.
     */
    public Item() {
    }

    /**
     * This is a Constructor used to create a new Item object with the
     * specified parameters.
     *
     * @param itemCode           the item code
     * @param name               the name
     * @param qtyInStore         the quantity in store
     * @param averageSalesPerDay the average sales per day
     * @param price              the price
     */
    public Item(String itemCode, String name, int qtyInStore,
                int averageSalesPerDay, double price) {
        this.itemCode = itemCode;
        this.name = name;
        this.qtyInStore = qtyInStore;
        this.averageSalesPerDay = averageSalesPerDay;
        this.price = price;
    }

    /**
     * Gets item code of this item.
     *
     * @return the item code of this item as String
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Gets name of this item
     *
     * @return the name of this item as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the quantity of this item in store.
     *
     * @return the qty in store as an int
     */
    public int getQtyInStore() {
        return qtyInStore;
    }

    /**
     * Gets the average sales per day for this item.
     *
     * @return the average sales per day as an int
     */
    public int getAverageSalesPerDay() {
        return averageSalesPerDay;
    }

    /**
     * Gets how many have already been ordered for restocking.
     *
     * @return the number of items on order as an int
     */
    public int getOnOrder() {
        return onOrder;
    }

    /**
     * Gets arrival day (the business day at which the order for this item
     * will arrive). If there is nothing currently on order, then it will be 0.
     *
     * @return the arrival day as an int
     */
    public int getArrivalDay() {
        return arrivalDay;
    }

    /**
     * Sets quantity of this item in store.
     *
     * @param qtyInStore the updated quantity of this item in store
     */
    public void setQtyInStore(int qtyInStore) {
        this.qtyInStore = qtyInStore;
    }

    /**
     * Sets how many have been ordered for restocking.
     *
     * @param onOrder the number to set onOrder to
     */
    public void setOnOrder(int onOrder) {
        this.onOrder = onOrder;
    }

    /**
     * Sets arrival day (the business day at which the order for this item will
     * arrive).
     *
     * @param arrivalDay the number to set the arrival day to
     */
    public void setArrivalDay(int arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    /**
     * Returns a String representation of this Item object,
     * which includes it's information of item code, name, quantity in store,
     * average sales per day, price, orders for restocking, and arrival day.
     *
     * @return A String representation of this Item.
     */
    public String toString() {
        return String.format("%-12s%-19s%8d%11d%9s%10d%13d%n",
                itemCode, name, qtyInStore, averageSalesPerDay,
                String.format("%.2f", price), onOrder, arrivalDay);
    }
}

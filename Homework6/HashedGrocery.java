/**
 * This class contains all the methods that will allow employees to perform
 * different functions on the inventory of the grocery store.
 *
 * @author Emily Tsui
 */

import java.io.*;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HashedGrocery implements Serializable {
    private int businessDay;
    private HashMap<String, Item> hashMap = new HashMap<>();

    /**
     * This is a Constructor used to create a new HashedGrocery object.
     */
    public HashedGrocery() {
        businessDay = 1;
    }

    /**
     * Gets the current business day (which starts from 1).
     *
     * @return the current business day as an int.
     */
    public int getBusinessDay() {
        return businessDay;
    }

    /**
     * Adds item to the hash table.
     *
     * @param item the item to add
     * @throws ItemCodeAlreadyExistsException
     * Thrown if an item with the same item code
     * already exists in the hash table.
     */
    public void addItem(Item item) throws ItemCodeAlreadyExistsException {
        if (hashMap.containsKey(item.getItemCode())) {
            throw new ItemCodeAlreadyExistsException("An item with the same " +
                    "item code already exists in the hash table.");
        } else {
            hashMap.put(item.getItemCode(), item);
        }
    }

    /**
     * Changes the qtyInStore amount of item by adjustByQty.
     *
     * @param item        the item
     * @param adjustByQty the quantity to adjust by
     */
    public void updateItem(Item item, int adjustByQty) {
        item.setQtyInStore(item.getQtyInStore() + adjustByQty);
    }

    /**
     * Adds all the items present in the text file.
     * Any duplicate items within the file is ignored.
     *
     * @param filename the filename of the supplied file (contains JSON
     *                 information for one or more Item objects)
     * @throws FileNotFoundException Thrown if filename does not exist.
     */
    public void addItemCatalog(String filename) throws FileNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            JSONParser parser = new JSONParser();
            JSONArray objs = (JSONArray) parser.parse(isr);
            String itemCode, name;
            int averageSalesPerDay, qtyInStore, onOrder;
            double price;

            for (int i = 0; i < objs.size(); i++) {
                JSONObject obj = (JSONObject) objs.get(i);
                itemCode = (String) obj.get("itemCode");
                name = (String) obj.get("itemName");
                averageSalesPerDay =
                        Integer.parseInt((String) obj.get("avgSales"));
                qtyInStore = Integer.parseInt((String) obj.get("qtyInStore"));
                price = Double.parseDouble((String) obj.get("price"));
                onOrder =
                        Integer.parseInt((String) obj.get("amtOnOrder"));

                Item itemToAdd = new Item(itemCode, name, qtyInStore,
                        averageSalesPerDay, price);

                if (onOrder > 0) {
                    itemToAdd.setOnOrder(onOrder);
                    itemToAdd.setArrivalDay(businessDay + 3);
                }

                try {
                    addItem(itemToAdd);
                    System.out.println(itemCode + ": " + name +
                            " added to inventory");
                } catch (ItemCodeAlreadyExistsException ex) {
                    System.out.println(itemCode + ": Cannot add item as item " +
                            "code already exists");
                }
            }
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("Filename does not exist.");
        } catch (ParseException | IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Processes filename to see which items have been sold that day.
     * If the item exists in inventory, its quantity in store is updated.
     * If there is not enough inventory for the next day, an order is placed.
     * Sales are not processed if quantity sold is greater than the quantity
     * in the store.
     *
     * @param filename the filename of the supplied file (contains JSON
     *                 information for one or more sales)
     * @throws FileNotFoundException Thrown if the file does not exist.
     */
    public void processSales(String filename) throws FileNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            JSONParser parser = new JSONParser();
            JSONArray objs = (JSONArray) parser.parse(isr);
            String itemCode;
            int qtySold, amtForRestocking, minimumQuantity;

            System.out.println();

            for (int i = 0; i < objs.size(); i++) {
                JSONObject obj = (JSONObject) objs.get(i);
                itemCode = (String) obj.get("itemCode");
                qtySold = Integer.parseInt((String) obj.get("qtySold"));
                Item item = hashMap.get(itemCode);

                if (item == null) {
                    System.out.println(itemCode + ": Cannot buy as it is not " +
                            "in the grocery store");
                } else if (qtySold > item.getQtyInStore()) {
                    System.out.println(itemCode +
                            ": Not enough stock for sale. Not updated.");
                } else {
                    updateItem(item, -qtySold);
                    System.out.print(itemCode + ": " + qtySold + " units of" +
                            " " + item.getName() + " are sold.");
                    amtForRestocking = 2 * item.getAverageSalesPerDay();
                    minimumQuantity = 3 * item.getAverageSalesPerDay();

                    if ((item.getQtyInStore() < minimumQuantity) &&
                            (item.getOnOrder() == 0)) {
                        item.setOnOrder(amtForRestocking);
                        item.setArrivalDay(businessDay + 3);
                        System.out.print("Order has been placed for " +
                                amtForRestocking + " units ");
                    }

                    System.out.println();
                }
            }
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("Filename does not exist.");
        } catch (ParseException | IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Increases businessDay by 1, and checks to see if any orders have arrived.
     * If so, it updates the quantities of items in the store for those orders
     * which have arrived, then sets the onOrder and the arrivalDay
     * variables for those items to 0.
     */
    public void nextBusinessDay() {
        System.out.println("\nAdvancing business day...");

        businessDay++;

        System.out.println("Business Day " + businessDay + ".\n");

        ArrayList<String> ordersArrived = new ArrayList<>();

        for (String key : hashMap.keySet()) {
            Item item = hashMap.get(key);
            if (item.getArrivalDay() == businessDay) {
                ordersArrived.add(item.getItemCode());
            }
        }

        if (!ordersArrived.isEmpty()) {
            System.out.println("Orders have arrived for: \n");

            Collections.sort(ordersArrived);

            for (String itemCode : ordersArrived) {
                Item item = hashMap.get(itemCode);
                System.out.println(item.getItemCode() + ": " +
                        item.getOnOrder() + " units of " + item.getName());
                updateItem(item, item.getOnOrder());
                item.setOnOrder(0);
                item.setArrivalDay(0);
            }
        } else {
            System.out.println("No orders have arrived.");
        }
    }

    /**
     * Prints all items in a neatly formatted table, each item includes it's
     * information of item code, name, quantity in store, average sales per
     * day, price, orders for restocking, and arrival day.
     *
     * @return A table representation of this HashedGrocery as a String.
     */
    public String toString() {
        String header = String.format("%-12s%-12s%15s%11s%9s%10s%13s",
                "Item code", "Name", "Qty", "AvgSales", "Price",
                "OnOrder", "ArrOnBusDay");
        String line = "------------------------------------------------------" +
                "-----------------------------\n";
        StringBuilder formatted = new StringBuilder(header + "\n" + line);

        ArrayList<String> sortedItemCodes = new ArrayList<>(hashMap.keySet());
        Collections.sort(sortedItemCodes);

        for (int i = 0; i < sortedItemCodes.size(); i++) {
            formatted.append((hashMap.get(sortedItemCodes.get(i))).toString());
        }

        return formatted.toString();
    }
}

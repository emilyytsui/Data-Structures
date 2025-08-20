/**
 * This class represents an active organ donor or recipient in the database.
 *
 * @author Emily Tsui
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Patient implements Comparable, Serializable {
    private String name;
    private String organ;
    private int age;
    private BloodType bloodType;
    private int ID;
    private boolean isDonor;
    private ArrayList<String> connectionsList = new ArrayList<>();

    /**
     * This is a Constructor used to create a new Patient object.
     */
    public Patient() {
    }

    /**
     * This is a Constructor used to create a new Patient object with the
     * specified parameters.
     *
     * @param ID        The ID number of the patient
     * @param name      The name of the donor or recipient
     * @param age       The age of the patient
     * @param organ     The organ the patient is donating or receiving
     * @param bloodType The blood type of the patient
     * @param isDonor   If this Patient is a donor or not
     */
    public Patient(int ID, String name, int age, String organ,
                   BloodType bloodType, boolean isDonor) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.organ = organ;
        this.bloodType = bloodType;
        this.isDonor = isDonor;
    }

    /**
     * This is a Constructor used to create a new Patient object with the
     * specified parameters.
     *
     * @param name      The name of the donor or recipient
     * @param bloodType The blood type of the patient
     * @param age       The age of the patient
     * @param organ     The organ the patient is donating or receiving
     * @param isDonor   If this Patient is a donor or not
     */
    public Patient(String name, BloodType bloodType,
                   int age, String organ, boolean isDonor) {
        this.name = name;
        this.bloodType = bloodType;
        this.age = age;
        this.organ = organ;
        this.isDonor = isDonor;
    }

    /**
     * Gets the name of the donor or recipient.
     *
     * @return the name of this patient as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the organ this patient is donating or receiving.
     *
     * @return the organ to be donated or received as a String
     */
    public String getOrgan() {
        return organ;
    }

    /**
     * Gets the blood type of this patient.
     *
     * @return the blood type as a BloodType object
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Gets the ID number of this patient.
     *
     * @return the ID number as an int
     */
    public int getID() {
        return ID;
    }

    /**
     * Gets connections list of this patient.
     *
     * @return an ArrayList of Strings representing the connections (the ID
     * numbers)
     */
    public ArrayList<String> getConnectionsList() {
        return connectionsList;
    }

    /**
     * Sets the ID number of the patient.
     *
     * @param ID the ID number
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Compares this Patient object with another object based on their ID
     * values (sorted in ascending order).
     *
     * @return An int representing the relationship. Zero if they are equal,
     * 1 if this patient's ID is greater, otherwise -1.
     */
    public int compareTo(Object o) {
        Patient patient = (Patient) o;

        if (this.ID == patient.ID) {
            return 0;
        } else if (this.ID > patient.ID) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Returns a String representation of this Patient object,
     * which includes it's information of patient ID number, name, age, organ
     * to be donated or received, blood type, and connections.
     *
     * @return A String representation of this Patient.
     */
    public String toString() {
        return String.format("%3d %1s | %-18s | %-3d | %-13s | %4s %-5s | %s%n",
                ID, "", name, age, organ, "", bloodType.getBloodType(),
                String.join(", ", connectionsList));
    }
}

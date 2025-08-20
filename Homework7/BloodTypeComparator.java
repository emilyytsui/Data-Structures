/**
 * This class implements the Comparator interface to compare two Patient
 * objects based on their blood type.
 *
 * @author Emily Tsui
 */

import java.util.Comparator;

public class BloodTypeComparator implements Comparator<Patient> {
    /**
     * Compares two patients' blood types lexicographically by comparing the
     * blood type strings of the two patients.
     *
     * @param p1 the first patient to be compared
     * @param p2 the second patient to be compared
     * @return
     * A negative integer if the blood type of the first patient is less than
     * that of the second patient, zero if they are equal, or a positive
     * integer if the blood type of the first patient is greater.
     */
    public int compare(Patient p1, Patient p2) {
        return (p1.getBloodType().getBloodType().compareTo(
                p2.getBloodType().getBloodType()));
    }
}

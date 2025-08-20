/**
 * This class implements the Comparator interface to compare two Patient
 * objects based on the organ needed/donating.
 *
 * @author Emily Tsui
 */

import java.util.Comparator;

public class OrganComparator implements Comparator<Patient> {
    /**
     * Compares two patients' blood types lexicographically by comparing the
     * organ strings of the two patients.
     *
     * @param p1 the first patient to be compared
     * @param p2 the second patient to be compared
     * @return
     * A negative integer if the organ of the first patient is less than
     * that of the second patient, zero if they are equal, or a positive
     * integer if the organ of the first patient is greater.
     */
    public int compare(Patient p1, Patient p2) {
        return (p1.getOrgan().compareToIgnoreCase(p2.getOrgan()));
    }
}

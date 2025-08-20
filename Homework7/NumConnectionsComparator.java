/**
 * This class implements the Comparator interface to compare two Patient
 * objects based on their number of connections.
 *
 * @author Emily Tsui
 */

import java.util.Comparator;

public class NumConnectionsComparator implements Comparator<Patient> {
    /**
     * Compares two patients based on the size of their connections list.
     *
     * @param p1 the first patient to be compared
     * @param p2 the second patient to be compared
     * @return
     * A negative integer if the first patient has fewer connections than the
     * second patient, zero if both patients have the same number of
     * connections, a positive integer if the first patient has more
     * connections than the second patient.
     */
    public int compare(Patient p1, Patient p2) {
        if (p1.getConnectionsList().size() == p2.getConnectionsList().size()) {
            return 0;
        } else if (p1.getConnectionsList().size() >
                p2.getConnectionsList().size()) {
            return 1;
        } else {
            return -1;
        }
    }
}

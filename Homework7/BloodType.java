/**
 * This class represents a human blood type and provides functionality to
 * determine blood compatibility between a donor and a recipient.
 *
 * @author Emily Tsui
 */

import java.io.Serializable;
import java.util.HashMap;

public class BloodType implements Serializable {
    private String bloodType;

    /**
     * This is a Constructor used to create a new BloodType object.
     */
    public BloodType() {
    }

    /**
     * This is a Constructor used to create a new Item object with the
     * specified parameters.
     *
     * @param bloodType the blood type
     */
    public BloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Gets blood type.
     *
     * @return the blood type as a String.
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Determines whether two blood types are compatible.
     *
     * @param recipient the recipient's blood type
     * @param donor     the donor's blood type
     * @return True if the two blood types are compatible, false otherwise.
     */
    public static boolean isCompatible(BloodType recipient, BloodType donor) {
        HashMap<String, Integer> bloodTypeMap = new HashMap<>() {{
            put("O", 0);
            put("A", 1);
            put("B", 2);
            put("AB", 3);
        }};

        boolean[][] compatibilityMatrix = {
                {true, false, false, false},
                {true, true, false, false},
                {true, false, true, false},
                {true, true, true, true}
        };

        return compatibilityMatrix[bloodTypeMap.get(recipient.bloodType)]
                [bloodTypeMap.get(donor.bloodType)];
    }
}

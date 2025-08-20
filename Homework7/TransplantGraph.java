/**
 * This class contains an adjacency matrix for the organ donors and recipients.
 *
 * @author Emily Tsui
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class TransplantGraph implements Serializable {
    private ArrayList<Patient> donors;
    private ArrayList<Patient> recipients;
    public static final int MAX_PATIENTS = 100;
    private boolean[][] connections;

    /**
     * This is a Constructor used to create a new TransplantGraph object.
     */
    public TransplantGraph() {
        donors = new ArrayList<>();
        recipients = new ArrayList<>();
        connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];
    }

    /**
     * A helper method that creates a new Patient object from parsing a String
     * line of data about the Patient.
     *
     * @param data
     * the line from the text file in the format:
     * "ID, name, age, organ, bloodType".
     * @param isDonor if this Patient is a donor or not
     * @return the newly created Patient object from the information
     */
    public static Patient patientFromString(String data, boolean isDonor) {
        String[] dataParts = data.split(", ");
        int ID = Integer.parseInt(dataParts[0]);
        int age = Integer.parseInt(dataParts[2]);
        BloodType bloodType = new BloodType(dataParts[4]);
        return new Patient(ID, dataParts[1], age,
                dataParts[3], bloodType, isDonor);
    }

    /**
     * Creates and returns a new TransplantGraph object, initialized with the
     * donor information found in donorFile and the recipient
     * information found in recipientFile.
     *
     * @param donorFile     the donor file
     * @param recipientFile the recipient file
     * @return the newly created TransplantGraph object
     */
    public static TransplantGraph buildFromFiles(String donorFile,
                                                 String recipientFile) {
        TransplantGraph transplantGraph = new TransplantGraph();

        try {
            FileInputStream fis = new FileInputStream(donorFile);
            InputStreamReader instream = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(instream);

            String data = reader.readLine();

            while (data != null) {
                transplantGraph.addDonor(patientFromString(data, true));
                data = reader.readLine();
            }

            fis = new FileInputStream(recipientFile);
            instream = new InputStreamReader(fis);
            reader = new BufferedReader(instream);
            data = reader.readLine();

            while (data != null) {
                transplantGraph.addRecipient(
                        patientFromString(data, false));
                data = reader.readLine();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return transplantGraph;
    }

    /**
     * Adds the specified Patient to the recipients list and updates the
     * connections adjacency matrix.
     *
     * @param patient the recipient to be added
     */
    public void addRecipient(Patient patient) {
        int j = recipients.size();
        patient.setID(j);
        recipients.add(patient);

        for (int i = 0; i < donors.size(); i++) {
            Patient donor = donors.get(i);
            if (BloodType.isCompatible(patient.getBloodType(),
                    donor.getBloodType()) &&
                    donor.getOrgan().equalsIgnoreCase(patient.getOrgan())) {
                connections[i][j] = true;
                patient.getConnectionsList().add(String.valueOf(i));
                donor.getConnectionsList().add(String.valueOf(j));
            }
        }
    }

    /**
     * Adds the specified Patient to the donors list and updates the
     * connections adjacency matrix.
     *
     * @param patient the donor to be added
     */
    public void addDonor(Patient patient) {
        int i = donors.size();
        patient.setID(i);
        donors.add(patient);

        for (int j = 0; j < recipients.size(); j++) {
            Patient recipient = recipients.get(j);
            if (BloodType.isCompatible(recipient.getBloodType(),
                    patient.getBloodType()) &&
                    patient.getOrgan().equalsIgnoreCase(recipient.getOrgan())) {
                connections[i][j] = true;
                patient.getConnectionsList().add(String.valueOf(j));
                recipient.getConnectionsList().add(String.valueOf(i));
            }
        }
    }

    /**
     * A helper method that updates the connections between
     * donors and recipients. It first clears any existing connections from
     * the donor and recipient connection lists. Then, it loops through all
     * donors and recipients, checking for compatibility based on blood type
     * and organ type. If a donor and recipient are compatible, the
     * connection adjacency matrix is updated, and the donor and recipient's
     * connection lists are updated to reflect their matching pair.
     */
    public void updateConnections() {
        for (int i = 0; i < donors.size(); i++) {
            donors.get(i).getConnectionsList().clear();
        }

        for (int j = 0; j < recipients.size(); j++) {
            recipients.get(j).getConnectionsList().clear();
        }

        connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];

        for (int i = 0; i < donors.size(); i++) {
            Patient donor = donors.get(i);
            for (int j = 0; j < recipients.size(); j++) {
                Patient recipient = recipients.get(j);
                if (BloodType.isCompatible(recipient.getBloodType(),
                        donor.getBloodType()) &&
                        donor.getOrgan().equalsIgnoreCase(
                                recipient.getOrgan())) {
                    connections[i][j] = true;
                    donor.getConnectionsList().add(String.valueOf(j));
                    recipient.getConnectionsList().add(String.valueOf(i));
                }
            }
        }
    }

    /**
     * Removes the specified Patient from the recipients list and updates the
     * connections adjacency matrix.
     *
     * @param name the name of the patient to be removed
     * @throws PatientDoesNotExistException
     * Thrown if there is no donor with the given name
     */
    public void removeRecipient(String name)
            throws PatientDoesNotExistException {
        int index = -1;

        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new PatientDoesNotExistException("Failed to remove " +
                    "recipient: No such patient named " + name + " in list of" +
                    " recipients.");
        } else {
            recipients.remove(index);

            for (int j = index; j < recipients.size(); j++) {
                recipients.get(j).setID(j);
            }

            updateConnections();
        }
    }

    /**
     * Removes the specified Patient from the donors list and updates the
     * connections adjacency matrix.
     *
     * @param name the name of the patient to be removed
     * @throws PatientDoesNotExistException
     * Thrown if there is no donor with the given name
     */
    public void removeDonor(String name) throws PatientDoesNotExistException {
        int index = -1;

        for (int i = 0; i < donors.size(); i++) {
            if (donors.get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new PatientDoesNotExistException("Failed to remove donor: " +
                    "No such patient named " + name + " in list of donors.");
        } else {
            donors.remove(index);

            for (int i = index; i < donors.size(); i++) {
                donors.get(i).setID(i);
            }

            updateConnections();
        }
    }

    /**
     * Prints all organ recipients' information in a neatly formatted table.
     */
    public void printAllRecipients() {
        System.out.printf("%-5s | %-18s | %-3s | %-13s | %-10s | %s%n",
                "Index", "Recipient Name", "Age", "Organ Needed",
                "Blood Type", "Donor IDs");
        System.out.println("=================================================" +
                "========================");

        for (int i = 0; i < recipients.size(); i++) {
            System.out.print(recipients.get(i));
        }
    }

    /**
     * Prints all organ donors' information in a neatly formatted table.
     */
    public void printAllDonors() {
        System.out.printf("%-5s | %-18s | %-3s | %-13s | %-10s | %s%n",
                "Index", "Donor Name", "Age", "Organ Donated",
                "Blood Type", "Recipient IDs");
        System.out.println("=================================================" +
                "============================");

        for (int i = 0; i < donors.size(); i++) {
            System.out.print(donors.get(i));
        }
    }

    /**
     * A helper method for sorting recipients or donors. It sorts based on the
     * user's choice. After sorting, it prints the sorted list and reverts it
     * back to the list's initial state.
     *
     * @param choice  the choice chosen by the user (in the main method)
     * @param isDonor if the donors list is to be sorted or not
     */
    public void sortListAndPrint(String choice, boolean isDonor) {
        ArrayList<Patient> listToSort = (isDonor ? donors : recipients);
        ArrayList<Patient> listCopy = (ArrayList<Patient>) listToSort.clone();

        switch (choice) {
            case "I": //Sort by ID
                Collections.sort(listToSort);
                break;
            case "N": //Sort by Number of Recipients
                Collections.sort(listToSort, new NumConnectionsComparator());
                break;
            case "B": //Sort by Blood Type
                Collections.sort(listToSort, new BloodTypeComparator());
                break;
            case "O": //Sort by Organ Donated
                Collections.sort(listToSort, new OrganComparator());
                break;
        }

        if (isDonor) {
            System.out.println();
            printAllDonors();
            donors = listCopy;
        } else {
            System.out.println();
            printAllRecipients();
            recipients = listCopy;
        }
    }
}

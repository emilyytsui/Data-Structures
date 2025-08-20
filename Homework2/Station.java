/**
 * This class contain a list of Track objects (which are themselves lists of
 * Train objects) that the user can switch between in the menu.
 * It also contains the main method that allows the user to interact with the
 * Trains in the Tracks.
 *
 * @author Emily Tsui
 */
import java.util.Scanner; //For user input.
import java.util.InputMismatchException; //If there's a wrong data type input.

public class Station {
    private Track head, tail, cursor;
    private int numTracks;

    /**
     * This is a Constructor used to create a new Station object.
     */
    public Station() {
        head = null;
        tail = null;
        cursor = null;
    }

    /**
     * Gets the number of tracks in this station.
     *
     * @return The number of tracks in this station as an int.
     */
    public int getNumTracks() {
        return numTracks;
    }

    /**
     * Adds newTrack to this Station.
     *
     * @param newTrack
     * The new Track to add to the station.
     *
     * <p>
     * Precondition:
     * The new Track object does not already exist.
     *
     * <p>
     * Postcondition:
     * The new Track object is inserted into the list such that the list
     * is sorted by track numbers.
     * The currently selected Track is set to the newly inserted Track.
     *
     * @throws TrackAlreadyExistsException
     * Thrown if there is already a Track object in this Station with
     * the same track number as newTrack.
     */
    public void addTrack(Track newTrack) throws TrackAlreadyExistsException {
        if (this.exists(newTrack)) {
            throw new TrackAlreadyExistsException("Track already exists in " +
                    "station.");
        } else {
            if (cursor == null) {
                head = newTrack;
                tail = newTrack;
            } else {
                Track curr = tail;

                while (curr != null && newTrack.getTrackNumber() <
                        curr.getTrackNumber()) {
                    curr = curr.getPrev();
                }

                if (curr == null) {
                    newTrack.setNext(head);
                    head.setPrev(newTrack);
                    head = newTrack;
                } else if (curr == tail) {
                    newTrack.setPrev(tail);
                    tail.setNext(newTrack);
                    tail = newTrack;
                } else {
                    Track currNext = curr.getNext();

                    newTrack.setPrev(curr);
                    newTrack.setNext(currNext);

                    if (currNext != null) {
                        currNext.setPrev(newTrack);
                    }

                    curr.setNext(newTrack);
                }
            }
            cursor = newTrack;
            numTracks++;
        }
    }

    /**
     * Removes the selected Track object from this Station and returns it.
     * If there is no Track selected, returns null.
     *
     * <p>
     * Postcondition:
     * The selected Track is now the Track node directly after the one that
     * was removed. If there is no Track node after the one that was
     * removed, then selected Track is the node directly before the one that
     * was removed. If the Track that was removed was the only one in
     * Station list, then the selected Track is now null.
     *
     * @return
     * A reference to the removed train. If there is no Train selected,
     * returns null.
     */
    public Track removeSelectedTrack() {
        if (cursor == null) {
            return null;
        } else {
            Track trackToRemove = cursor;
            Track currNext = trackToRemove.getNext();
            Track currPrev = trackToRemove.getPrev();

            if (head == tail) {
                head = null;
                tail = null;
                cursor = null;
            } else if (trackToRemove == head) {
                head = currNext;
                head.setPrev(null);
                cursor = head;
            } else if (currNext == null) {
                tail = currPrev;
                cursor = currPrev;
            } else {
                currPrev.setNext(currNext);
                currNext.setPrev(currPrev);
                cursor = currNext;
            }

            numTracks--;

            return trackToRemove;
        }
    }

    /**
     * Prints the selected Track list.
     */
    public void printSelectedTrack() {
        System.out.println("Track " + cursor.getTrackNumber() + " (" +
                String.format("%.2f", cursor.getUtilizationRate()) +
                "% Utilization Rate):");
        System.out.printf("%-11s%-17s%-25s%-17s%-18s%n",
                "Selected", "Train Number", "Train Destination",
                "Arrival Time", "Departure Time");
        System.out.println("-------------------------------------------------" +
                "------------------------------------");

        System.out.println(cursor.toString());
    }

    /**
     * Prints all Track lists in this Station.
     */
    public void printAllTracks() {
        System.out.print(this.toString());
    }

    /**
     * Prints station information including number of tracks and details
     * about each track including track number, number of trains arriving,
     * and utilization rate of that track.
     */
    public void printStationInformation() {
        Track curr = head;

        System.out.println("Station (" + numTracks + " tracks):");

        for (int i = 0; i < numTracks; i++) {
            System.out.println("    Track " + curr.getTrackNumber() + ": " +
                    curr.getNumTrains() + " trains arriving (" +
                    String.format("%.2f", curr.getUtilizationRate()) +
                    "% Utilization Rate)");
            curr = curr.getNext();
        }
    }

    /**
     * Moves the reference of the selected Track node to the node which has the
     * same trackNumber as the given trackToSelect parameter if it exists and
     * returns true. If there is no Track with a trackNumber which matches
     * trackToSelect, then the selected Track object remains the same
     * and returns false.
     *
     * @return True if the reference of the selected Track node is moved to the
     * node which has the same trackNumber, false otherwise.
     *
     */
    public boolean selectTrack(int trackToSelect) {
        Track curr = head;
        while (curr != null) {
            if (curr.getTrackNumber() == trackToSelect) {
                cursor = curr;
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    /**
     * Checks whether a certain track is contained in this station.
     *
     * @param track
     * The Train to check for.
     *
     * <p>
     * Precondition:
     * This Station and track have been instantiated.
     *
     * @return
     * True if this Station contains track, false otherwise.
     *
     * @throws IllegalArgumentException
     * Thrown if track is not a valid Track object.
     */
    public boolean exists(Track track) throws IllegalArgumentException {
        if (track == null || !(track instanceof Track)) {
            throw new IllegalArgumentException("Track is not a valid Track " +
                    "object.");
        } else {
            Track curr = head;
            while (curr != null) {
                if (curr.getTrackNumber() == track.getTrackNumber()) {
                    return true;
                }
                curr = curr.getNext();
            }
            return false;
        }
    }

    /**
     * Returns a String representation of this Station object,
     * which includes information on each track and all trains in each track.
     *
     * @return
     * A neatly formatted representation of this Station.
     */
    public String toString() {
        Track curr = head;
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < numTracks; i++) {
            if (curr.getTrackNumber() == cursor.getTrackNumber()) {
                formatted.append("Track " + curr.getTrackNumber() + "* (" +
                        String.format("%.2f", curr.getUtilizationRate()) +
                        "% Utilization Rate):\n");
            } else {
                formatted.append("Track " + curr.getTrackNumber() + " (" +
                        String.format("%.2f", curr.getUtilizationRate()) +
                        "% Utilization Rate):\n");
            }

            formatted.append(String.format("%-11s%-17s%-25s%-17s%-18s%n",
                    "Selected", "Train Number", "Train Destination",
                    "Arrival Time", "Departure Time"));
            formatted.append("---------------------------------------------" +
                    "----------------------------------------\n");
            formatted.append(curr.toString() + "\n");

            curr = curr.getNext();
        }

        return formatted.toString();
    }

    /**
     * The main method runs a menu-driven application.
     * The program prompts the user for a command to execute an operation.
     * Once a command has been chosen, the program may ask the user
     * for additional information if necessary and performs the operation.
     *
     * @param args
     * An array of command-line arguments passed to the program
     */
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        boolean done = false;
        int trainNumber, arrivalTime, transferTime, trackNumber = 0;
        String destination = "";
        Station station = new Station();

        while (!done) {
            System.out.println("|--------------------------------------------" +
                    "---------------------------------|");
            System.out.printf("| %-35s | %-37s | %n",
                    "Train Options", "Track Options");
            System.out.printf("| %5s %-29s | %6s %-30s | %n",
                    "A.", "Add new Train", "TA.", "Add Track");
            System.out.printf("| %5s %-29s | %6s %-30s | %n",
                    "N.", "Select next Train", "TR.", "Remove selected Track");
            System.out.printf("| %5s %-29s | %6s %-30s | %n",
                    "V.", "Select previous Train", "TS.", "Switch Track");
            System.out.printf("| %5s %-29s | %6s %-30s | %n",
                    "R.", "Remove selected Train",
                    "TPS.", "Print selected Track");
            System.out.printf("| %5s %-29s | %6s %-30s | %n",
                    "P.", "Print selected Train", "TPA.", "Print all Tracks");
            System.out.println("|--------------------------------------------" +
                    "---------------------------------|");
            System.out.printf("| %-75s |%n", "Station Options");
            System.out.printf("| %5s %-69s |%n",
                    "SI.", "Print Station Information");
            System.out.printf("| %5s %-69s |%n", "Q.", "Quit");
            System.out.println("|--------------------------------------------" +
                    "---------------------------------|\n");

            System.out.print("Choose an operation: ");

            String selection = stdin.nextLine().toUpperCase();

            switch (selection) {
                case "A": //Add new Train
                    try {
                        System.out.print("\nEnter train number: ");
                        trainNumber = stdin.nextInt();
                        stdin.nextLine();

                        System.out.print("Enter train destination: ");
                        destination = stdin.nextLine();

                        System.out.print("Enter train arrival time: ");
                        arrivalTime = stdin.nextInt();
                        stdin.nextLine();

                        System.out.print("Enter train transfer time: ");
                        transferTime = stdin.nextInt();
                        stdin.nextLine();

                        Train newTrain = new Train(trainNumber, destination,
                                arrivalTime, transferTime);

                        if (station.cursor != null) {
                            station.cursor.addTrain(newTrain);
                            System.out.println("\nTrain No. " + trainNumber +
                                    " to " + destination + " added to Track " +
                                    station.cursor.getTrackNumber() + ".\n");
                        } else {
                            System.out.println("\nTrain not added: " +
                                    "There is no Track to add the Train to!\n");
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("\nPlease enter a valid " +
                                "number.\n");
                        stdin.nextLine();
                    } catch (TrainAlreadyExistsException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("Train not added: " +
                                "There is already a Train with that number!\n");
                    } catch (InvalidTrainException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("Train not added: " +
                                "Invalid arrival time.\n");
                    } catch (TrainTimeConflictException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("Train not added: " +
                                "There is a Train already scheduled on Track " +
                                station.cursor.getTrackNumber() +
                                " at that time!\n");
                    }
                    break;
                case "N": //Select next Train
                    try {
                        boolean cursorMoved = station.cursor.selectNextTrain();

                        if (cursorMoved) {
                            System.out.println("\nCursor has been moved to " +
                                    "next train.\n");
                        } else {
                            System.out.println("\nSelected train not updated:" +
                                    " Already at end of Track list.\n");
                        }
                    } catch (NoSelectedTrainException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("Cursor not moved: " +
                                "There is no selected Train.\n");
                    }
                    break;
                case "V": //Select previous Train
                    try {
                        boolean cursorMoved = station.cursor.selectPrevTrain();

                        if (cursorMoved) {
                            System.out.println("\nCursor has been moved to " +
                                    "previous train.\n");
                        } else {
                            System.out.println("\nSelected train not updated:" +
                                    " Already at beginning of Track list.\n");
                        }
                    } catch (NoSelectedTrainException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("Cursor not moved: " +
                                "There is no selected Train.\n");
                    }
                    break;
                case "R": //Remove selected Train
                    Train removeTrain = station.cursor.removeSelectedTrain();
                    if (removeTrain != null) {
                        System.out.println("\nTrain No. " +
                                removeTrain.getTrainNumber() +
                                " to " + removeTrain.getDestination() +
                                " has been removed from Track " +
                                station.cursor.getTrackNumber() + ".\n");
                    } else {
                        System.out.println("\nTrack is empty: " +
                                "no trains to remove.\n");
                    }
                    break;
                case "P": //Print selected Train
                    try {
                        if (station.cursor != null) {
                            station.cursor.printSelectedTrain();
                            System.out.println("Successfully printed " +
                                    "selected train.\n");
                        } else {
                            System.out.println("\nNo selected track.\n");
                        }
                    } catch (NoSelectedTrainException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("Track is empty: " +
                                "No trains to print.\n");
                    }
                    break;
                case "TPS": //Print selected Track
                    if (station.cursor != null) {
                        System.out.println();
                        station.printSelectedTrack();
                        System.out.println("Successfully printed " +
                                "selected track.\n");
                    } else {
                        System.out.println("\nTrack is empty: " +
                                "No trains to print.\n");
                    }
                    break;
                case "TPA": //Print all Tracks
                    if (station.getNumTracks() > 0) {
                        System.out.println();
                        station.printAllTracks();
                        System.out.println("Successfully printed " +
                                "all tracks in station.\n");
                    } else {
                        System.out.println("\nNo tracks in station.\n");
                    }
                    break;
                case "TS": //Switch Track
                    try {
                        System.out.print("\nEnter the Track number: ");
                        trackNumber = stdin.nextInt();
                        stdin.nextLine();

                        boolean cursorMoved = station.selectTrack(trackNumber);

                        if (cursorMoved) {
                            System.out.println("\nSwitched to Track " +
                                    trackNumber + ".\n");
                        } else {
                            System.out.println("\nCould not switch tracks: " +
                                    "Track " + trackNumber +
                                    " does not exist.\n");
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("\nPlease enter a valid " +
                                "number.\n");
                        stdin.nextLine();
                    }
                    break;
                case "TA": //Add Track
                    try {
                        System.out.print("\nEnter the Track number: ");
                        trackNumber = stdin.nextInt();
                        stdin.nextLine();

                        station.addTrack(new Track(trackNumber));

                        System.out.println("\nTrack " + trackNumber +
                                " added to the Station.\n");
                    } catch (InputMismatchException ex) {
                        System.out.println("\nPlease enter a valid " +
                                "number.\n");
                        stdin.nextLine();
                    } catch (TrackAlreadyExistsException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("Track not added: " +
                                "Track " + trackNumber + " already exists.\n");
                    }
                    break;
                case "TR": //Remove selected Track
                    Track removeTrack = station.removeSelectedTrack();
                    if (removeTrack != null) {
                        System.out.println("\nClosed Track " +
                                removeTrack.getTrackNumber() + ".\n");
                    } else {
                        System.out.println("\nTrack not removed: " +
                                "No track exists.\n");
                    }
                    break;
                case "SI": //Print Station information
                    if (station.getNumTracks() > 0) {
                        System.out.println();
                        station.printStationInformation();
                        System.out.println("\nSuccessfully printed " +
                                "station information.\n");
                    } else {
                        System.out.println("\nNo tracks in station.\n");
                    }
                    break;
                case "Q": //Terminates the program.
                    System.out.println("\nProgram terminating normally...");
                    done = true;
                    break;
                default:
                    System.out.println("\nPlease enter a selection " +
                            "from the menu.\n");
            }
        }
        stdin.close();
    }
}

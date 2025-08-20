/**
 * This class contains basic information for a train approaching a station.
 * It includes the train number, the train's destination, the arrival time of
 * the train to its track (in 24-hour format between 0000 - 2359), and the
 * transfer time for how long the train waits at the station (in minutes).
 *
 * @author Emily Tsui
 */
public class Train {
    private Train next, prev;
    private int trainNumber, arrivalTime, transferTime;
    private String destination;

    /**
     * This is a Constructor used to create a new Train object.
     */
    public Train() {
        next = null;
        prev = null;
        trainNumber = 0;
        arrivalTime = 0;
        transferTime = 0;
        destination = "";
    }

    /**
     * This is a Constructor used to create
     * a new Train object with the specified parameters.
     *
     * @param trainNumber  the train number
     * @param destination  the destination
     * @param arrivalTime  the arrival time
     * @param transferTime the transfer time
     *
     * @throws InvalidTrainException
     * Thrown if the arrival time is invalid.
     * The time must be in 24-hour format between 0000 - 2359.
     */
    public Train(int trainNumber, String destination, int arrivalTime,
                 int transferTime) throws InvalidTrainException {
        if (!validTime(arrivalTime)) {
            throw new InvalidTrainException("Invalid arrival time.");
        } else {
            this.trainNumber = trainNumber;
            this.arrivalTime = arrivalTime;
            this.transferTime = transferTime;
            this.destination = destination;
        }
    }

    /**
     * Sets the next train in the linked list.
     * <p>
     * Precondition: The next Train should point to the train scheduled to
     * arrive after this one.
     *
     * @param nextTrain The train that follows this one in the list.
     */
    public void setNext(Train nextTrain) {
        this.next = nextTrain;
    }

    /**
     * Sets the previous train in the linked list.
     * <p>
     * Precondition: The previous train should be the train arriving before
     * this train.
     *
     * @param prevTrain The train that precedes this one in the list.
     */
    public void setPrev(Train prevTrain) {
        this.prev = prevTrain;
    }

    /**
     * Gets the next train in the linked list.
     *
     * @return The next Train object, or null if there is no next train.
     */
    public Train getNext() {
        return next;
    }

    /**
     * Gets the previous train in the linked list.
     *
     * @return The previous Train object, or null if there is no previous train.
     */
    public Train getPrev() {
        return prev;
    }

    /**
     * Gets the train number of this train.
     *
     * @return The train number of this train as an int.
     */
    public int getTrainNumber() {
        return trainNumber;
    }

    /**
     * Gets the arrival time of this train.
     *
     * @return The arrival time of this train as an int.
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Gets the transfer time of this train.
     *
     * @return The transfer time of this train as an int.
     */
    public int getTransferTime() {
        return transferTime;
    }

    /**
     * Gets the destination of this train.
     *
     * @return
     * The destination of this train as a String.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Checks to see if the time is valid.
     * The time must be in 24-hour format between 0000 - 2359.
     *
     * @param time
     * The time of the train.
     *
     * @return
     * True if the time is valid, false otherwise.
     */
    public static boolean validTime(int time) {
        if (!(time >= 0000 && time <= 2359)) {
            return false;
        }
        int hours = time / 100;
        int minutes = time % 100;

        return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
    }

    /**
     * Calculates the departure time of the train by adding the transfer time
     * to the arrival time.
     *
     * @return
     * An int representing the departure time in a 24-hour format between
     * 0000 - 2359.
     */
    public int departureTime() {
        int arrivalHours = arrivalTime / 100;
        int arrivalMinutes = arrivalTime % 100;

        int departureTime = (arrivalHours * 60) + arrivalMinutes + transferTime;

        return (departureTime / 60 * 100) + departureTime % 60;
    }

    /**
     * Compares this Train with another object
     * to check if they are equal.
     *
     * @param o
     * The object being compared to this Train.
     *
     * @return
     * True if that o refers to a Train object with
     * the same train number as this Train, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Train) {
            Train t = (Train) o;
            return this.trainNumber == t.trainNumber;
        } else {
            return false;
        }
    }

    /**
     * Returns a String representation of this Train object,
     * which includes it's information of train number, destination, arrival
     * time, and departure time.
     *
     * @return
     * A textual representation of all the information for this Train object.
     */
    @Override
    public String toString() {
        return "Selected Train:\n" +
                "    Train Number: " + trainNumber + "\n" +
                "    Train Destination: " + destination + "\n" +
                String.format("    Arrival Time: %04d\n", arrivalTime) +
                String.format("    Departure Time: %04d\n", departureTime());
    }
}

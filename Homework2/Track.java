/**
 * This class implements an abstract data type for a list of Trains. It
 * contains references to the head and tail of a list of Train nodes,
 * as well as a cursor representing the selected Train node. Each track has a
 * unique track number and utilization rate.
 *
 * @author Emily Tsui
 */
public class Track {
    private Train head, tail, cursor;
    private Track next, prev;
    private double utilizationRate;
    private int trackNumber, numTrains, utilizationTime;

    /**
     * This is a Constructor used to create a new Track object.
     */
    public Track() {
        head = null;
        tail = null;
        cursor = null;
        next = null;
        prev = null;
        utilizationRate = 0;
        trackNumber = 0;
    }

    /**
     * This is a Constructor used to create
     * a new Train object with the specified parameters.
     *
     * @param trackNumber the track number
     */
    public Track(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    /**
     * Gets the next track in the linked list.
     *
     * @return The next Track object, or null if there is no next track.
     */
    public Track getNext() {
        return next;
    }

    /**
     * Gets the previous track in the linked list.
     *
     * @return The previous Track object, or null if there is no previous track.
     */
    public Track getPrev() {
        return prev;
    }

    /**
     * Gets the utilization rate of this track. It represents the percentage of
     * the day the track is being used (total time of trains waiting at the
     * track / total minutes in a day).
     *
     * @return The utilization rate of this track as a double.
     */
    public double getUtilizationRate() {
        return utilizationRate;
    }

    /**
     * Gets the track number of this track.
     *
     * @return The track number of this track as an int.
     */
    public int getTrackNumber() {
        return trackNumber;
    }

    /**
     * Gets the number of trains in this track.
     *
     * @return The number of trains in this track as an int.
     */
    public int getNumTrains() {
        return numTrains;
    }

    /**
     * Sets the next track in the linked list.
     * <p>
     * Precondition: The next Track should point to the track scheduled to
     * arrive after this one.
     *
     * @param nextTrack The track that follows this one in the list.
     */
    public void setNext(Track nextTrack) {
        this.next = nextTrack;
    }

    /**
     * Sets the previous track in the linked list.
     * <p>
     * Precondition: The previous track should be the track arriving before
     * this track.
     *
     * @param prevTrack The track that precedes this one in the list.
     */
    public void setPrev(Track prevTrack) {
        this.prev = prevTrack;
    }

    /**
     * Checks to see if two trains are scheduled for the same track
     * at the same time. The arrival and departure time of train conflicts
     * with an already scheduled train.
     *
     * @param train
     * The train object to be added.
     *
     * @return
     * True if the train does not conflict with another train in this track,
     * false otherwise.
     */
    public boolean timeConflict(Train train) {
        Train curr = head;

        while (curr != null) {
            if (train.getArrivalTime() < curr.departureTime() &&
                    curr.getArrivalTime() < train.departureTime()) {
                return true;
            }
            curr = curr.getNext();
        }

        return false;
    }

    /**
     * Adds newTrain into to the specified Track list
     *
     * @param newTrain
     * The new Train to add to the track.
     *
     * <p>
     * Precondition:
     * The new Train object does not already exist and has a valid arrival time
     * that does not conflict with another train in this track.
     *
     * <p>
     * Postcondition:
     * The currently selected train for this Track object is
     * updated to be the newly inserted Train.
     * New train is now in the correct order in the list (sorted according to
     * arrival time).
     *
     * @throws InvalidTrainException
     * Thrown if the train's arrival time is invalid.
     * @throws TrainAlreadyExistsException
     * Thrown if there is already a train
     * in this track which is equivalent to newTrain.
     * @throws TrainTimeConflictException
     * Thrown if newTrain is scheduled for the same time as another train in
     * this track.
     */
    public void addTrain(Train newTrain) throws InvalidTrainException,
            TrainAlreadyExistsException, TrainTimeConflictException {
        if (!Train.validTime(newTrain.getArrivalTime())) {
            throw new InvalidTrainException("Invalid arrival time.");
        }
        if (this.exists(newTrain)) {
            throw new TrainAlreadyExistsException("Train already exists in " +
                    "track.");
        }
        if (this.timeConflict(newTrain)) {
            throw new TrainTimeConflictException("Train time conflicts with " +
                    "another train in the track.");
        } else {
            if (cursor == null) {
                head = newTrain;
                tail = newTrain;
            } else {
                Train curr = tail;

                while (curr != null && newTrain.getArrivalTime() <
                        curr.getArrivalTime()) {
                    curr = curr.getPrev();
                }

                if (curr == null) {
                    newTrain.setNext(head);
                    head.setPrev(newTrain);
                    head = newTrain;
                } else if (curr == tail) {
                    newTrain.setPrev(tail);
                    tail.setNext(newTrain);
                    tail = newTrain;
                } else {
                    Train currNext = curr.getNext();

                    newTrain.setPrev(curr);
                    newTrain.setNext(currNext);

                    if (currNext != null) {
                        currNext.setPrev(newTrain);
                    }

                    curr.setNext(newTrain);
                }
            }

            cursor = newTrain;

            utilizationTime += cursor.getTransferTime();
            utilizationRate = utilizationTime / 1440.0 * 100;

            numTrains++;
        }
    }

    /**
     * Prints the data of the selected Train.
     */
    public void printSelectedTrain() throws NoSelectedTrainException{
        if(cursor == null){
            throw new NoSelectedTrainException("Selected train is null");
        }else{
            System.out.println("\n" + cursor.toString());
        }
    }

    /**
     * Removes the selected Train from the track and returns a reference to it.
     *
     * <p>
     * Postcondition:
     * The selected Train is the Train node after the one which was just
     * removed. If there is no Train after the one which was just removed,
     * the selected Train is now the node before the one which was just
     * removed. If the Train removed was the only one in the Track list, then
     * the selected Train is now null.
     *
     * @return
     * A reference to the removed train. If there is no Train selected,
     * returns null.
     */
    public Train removeSelectedTrain() {
        if (cursor == null) {
            return null;
        } else {
            Train trainToRemove = cursor;
            Train currNext = trainToRemove.getNext();
            Train currPrev = trainToRemove.getPrev();

            if (head == tail) { //Only Train in the Track list
                head = null;
                tail = null;
                cursor = null;
            } else if (trainToRemove == head) { //Removed the first Train in the Track
                head = currNext;
                head.setPrev(null);
                cursor = head;
            } else if (trainToRemove == tail) { //No Train after removed one
                tail = currPrev;
                cursor = currPrev;
            } else {
                currPrev.setNext(currNext);
                currNext.setPrev(currPrev);
                cursor = currNext;
            }

            utilizationTime -= trainToRemove.getTransferTime();
            utilizationRate = utilizationTime / 1440.0 * 100;

            numTrains--;

            return trainToRemove;
        }
    }

    /**
     * Moves the reference of the selected Train node forwards to the next Train
     * if it exists and returns true. If there is no next Train, then the
     * selected Train remains the same and returns false.
     *
     * @return True if the reference of the selected Train node is moved
     * forward, false otherwise.
     *
     * @throws NoSelectedTrainException
     * Thrown if there is no selected Train.
     */
    public boolean selectNextTrain() throws NoSelectedTrainException {
        if (cursor == null) {
            throw new NoSelectedTrainException("There is no selected Train.");
        }
        if (cursor.getNext() != null) {
            cursor = cursor.getNext();
            return true;
        }
        return false;
    }

    /**
     * Moves the reference of the selected Train node backwards to the previous
     * Train if it exists and returns true. If there is no previous Train, then
     * the selected Train remains the same and returns false.
     *
     * @return True if the reference of the selected Train node is moved
     * backwards, false otherwise.
     *
     * @throws NoSelectedTrainException
     * Thrown if there is no selected Train.
     */
    public boolean selectPrevTrain() throws NoSelectedTrainException {
        if (cursor == null) {
            throw new NoSelectedTrainException("There is no selected Train.");
        }
        if (cursor.getPrev() != null) {
            cursor = cursor.getPrev();
            return true;
        }
        return false;
    }

    /**
     * Checks whether a certain train is contained in this track.
     *
     * @param train
     * The Train to check for.
     *
     * <p>
     * Precondition:
     * This Track and train have been instantiated.
     *
     * @return
     * True if this Track contains train, false otherwise.
     *
     * @throws IllegalArgumentException
     * Thrown if train is not a valid Train object.
     */
    public boolean exists(Train train) throws IllegalArgumentException {
        if (train == null || !(train instanceof Train)) {
            throw new IllegalArgumentException("Train is not a valid Train " +
                    "object.");
        } else {
            Train curr = head;

            while (curr != null) {
                if (curr.equals(train)) {
                    return true;
                }
                curr = curr.getNext();
            }

            return false;
        }
    }

    /**
     * Returns a String representation of this Track object,
     * which includes it's information of selected train and each train
     * including train number, destination, arrival time, and departure time.
     *
     * @return
     * A neatly formatted list of all the trains scheduled on this Track.
     */
    @Override
    public String toString() {
        Train curr = head;
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < numTrains; i++) {
            formatted.append(String.format("%-9s%10d%10s%-20s%12s%18s%n",
                    curr.equals(cursor) ? "    *   " : "",
                    curr.getTrainNumber(),
                    "",
                    curr.getDestination(),
                    String.format("%04d", curr.getArrivalTime()),
                    String.format("%04d", curr.departureTime())
            ));
            curr = curr.getNext();
        }

        return formatted.toString();
    }
}

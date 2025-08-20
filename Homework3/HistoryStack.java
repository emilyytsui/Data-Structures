/**
 * This class contains the user's history of equations.
 *
 * @author Emily Tsui
 */

import java.util.Stack;

public class HistoryStack extends Stack {
    private int size;
    private Stack<Equation> undoRedoStack = new Stack<>();

    /**
     * This is a Constructor used to create a new HistoryStack object.
     */
    public HistoryStack() {
        size = 0;
    }

    /**
     * Adds newEquation to the top of the stack.
     *
     * @param newEquation The newEquation to add to the stack.
     */
    public void push(Equation newEquation) {
        super.push(newEquation);
        size++;
    }

    /**
     * Removes the Equation at the top of the stack.
     *
     * @return The removed Equation that was at the top of the stack.
     */
    public Equation pop() {
        Equation poppedEquation = (Equation) super.pop();
        size--;
        return poppedEquation;
    }

    /**
     * Returns the Equation at the top of the stack.
     *
     * @return The Equation that is at the top of the stack.
     */
    public Equation peek() {
        return (Equation) super.peek();
    }

    /**
     * Removes the last Equation from the top of the stack,
     * preserving it for later (it's stored in undoRedoStack).
     */
    public void undo() {
        undoRedoStack.push(this.pop());
    }

    /**
     * Replaces the last undone Equation back onto the stack.
     *
     * @throws NoLastUndoneEquationException
     * Thrown if there is no last undone Equation.
     */
    public void redo() throws NoLastUndoneEquationException {
        if (undoRedoStack.empty()) {
            throw new NoLastUndoneEquationException("No last undone Equation.");
        } else {
            super.push(undoRedoStack.pop());
            size++;
        }
    }

    /**
     * Returns the current size of the stack.
     *
     * @return The size of HistoryStack as an int.
     */
    public int size() {
        return size;
    }

    /**
     * Searches through this HistoryStack and returns the
     * Equation located at the specified position.
     *
     * @param position The position the Equation is at.
     * @return The equation at the position.
     * @throws InvalidPositionException
     * Thrown if the position is out of range or otherwise invalid (it's less
     * than 1 or greater than the size of the stack).
     */
    public Equation getEquation(int position) throws InvalidPositionException {
        if (position < 1 || position > this.size()) {
            throw new InvalidPositionException("Position is out of range or " +
                    "otherwise invalid.");
        } else {
            HistoryStack copy = (HistoryStack) super.clone();

            for (int i = 0; i < position - 1; i++) {
                copy.pop();
            }

            return copy.peek();
        }
    }

    /**
     * Prints a neatly formatted table of the last equation entered (the
     * equation on the top of this HistoryStack).
     */
    public void printPreviousEquation() {
        System.out.printf("%-4s%-35s%-35s%-34s%-18s%-12s%-12s%n",
                "#", "Equation", "Pre-Fix", "Post-Fix",
                "Answer", "Binary", "Hexadecimal");
        System.out.println("-------------------------------------------------" +
                "------------------------------------------------------------" +
                "----------------------------------------");
        System.out.printf("%-4d", size);
        System.out.println(this.peek().toString());
    }

    /**
     * Returns a String representation of this HistoryStack object,
     * which includes it's information about each equation including position,
     * equation, prefix, postfix, answer, binary, and departure hex.
     *
     * @return
     * A neatly formatted table of this HistoryStack as a String.
     */
    public String toString() {
        String header = String.format("%-4s%-35s%-35s%-34s%-18s%-12s%-12s%n",
                "#", "Equation", "Pre-Fix", "Post-Fix",
                "Answer", "Binary", "Hexadecimal");
        String line = "------------------------------------------------------" +
                "------------------------------------------------------------" +
                "-----------------------------------";
        StringBuilder formatted = new StringBuilder(header + line + "\n");

        HistoryStack copy = (HistoryStack) super.clone();

        for (int i = size; i > 0; i--) {
            formatted.append(String.format("%-4d", i));
            formatted.append(copy.pop().toString());
        }

        return formatted.toString();
    }
}

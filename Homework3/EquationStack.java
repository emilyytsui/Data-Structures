/**
 * This class functions like a stack and is used to evaluate an Equation.
 *
 * @author Emily Tsui
 */

import java.util.Stack;

public class EquationStack extends Stack {
    private int size;

    /**
     * This is a Constructor used to create a new EquationStack object.
     */
    public EquationStack() {
        size = 0;
    }

    /**
     * Pushes s (a new operator or operand) to the top of the stack.
     *
     * @param s The new operator or operand.
     */
    public void push(String s) {
        super.push(s);
        size++;
    }

    /**
     * Removes the operator or operand stored at the top of the stack.
     *
     * @return The removed operator or operand that was at the top of the
     * stack as a String.
     */
    public String pop() {
        String poppedString = (String) super.pop();
        size--;
        return poppedString;
    }

    /**
     * Returns the operator or operand stored at the top of the stack.
     *
     * @return The operator or operand that is at the top of the
     * stack as a String.
     */
    public String peek(){
        return (String) super.peek();
    }

    /**
     * Determines if the EquationStack is empty or not.
     *
     * @return True if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return super.empty();
    }

    /**
     * Returns the current size of the stack.
     *
     * @return The size of EquationStack as an int.
     */
    public int size() {
        return size;
    }
}

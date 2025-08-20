/**
 * This class store the actual equation (in in-fix notation),
 * pre- and post-fix notations, the answer in decimal, binary, and hex, and a
 * balanced flag to denote whether the equation is balanced or not.
 *
 * @author Emily Tsui
 */

import java.util.Stack;

public class Equation {
    private String equation, prefix, postfix;
    private double answer;
    private String binary, hex;
    private boolean balanced;

    /**
     * This is a Constructor used to create a new Equation object.
     */
    public Equation() {
        equation = "";
        prefix = postfix = "";
        answer = 0;
        binary = hex = "";
        balanced = false;
    }

    /**
     * This is a Constructor used to create a new Equation object with the
     * specified parameter.
     *
     * @param equation The equation (in in-fix notation).
     */
    public Equation(String equation) {
        this.equation = equation;
        this.balanced = isBalanced();

        try {
            postfix = infixToPostfix();
            prefix = infixToPrefix();
            answer = evaluatePostfix();
            binary = decToBin((int) Math.round(answer));
            hex = decToHex((int) Math.round(answer));
        } catch (EquationNotBalancedException ex) {
            prefix = postfix = "N/A";
            answer = 0;
            binary = hex = "0";
            System.out.println(ex);
        }
    }

    /**
     * Gets the answer in decimal (base 10).
     *
     * @return the answer of this equation as a double.
     */
    public double getAnswer() {
        return answer;
    }

    /**
     * Gets the equation (in in-fix notation).
     *
     * @return the equation as a String.
     */
    public String getEquation() {
        return equation;
    }

    /**
     * Gets the balanced flag.
     *
     * @return true if equation is balanced, otherwise false.
     */
    public boolean getBalanced() {
        return balanced;
    }

    /**
     * Returns the precedence of operators.
     *
     * @param ch The character to check.
     * @return An int representing the precedence of the operator, -1 if ch
     * is not an operator.
     */
    public static int operatorPrecedence(char ch) {
        if (ch == '^') {
            return 3;
        } else if (ch == '*' || ch == '/' || ch == '%') {
            return 2;
        } else if (ch == '+' || ch == '-') {
            return 1;
        } else if (ch == '(' || ch == ')') {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Converts this equation from in-fix notation to pre-fix notation.
     *
     * @return The equation in pre-fix notation as a String.
     * @throws EquationNotBalancedException
     * Thrown if the equation is not valid (not balanced)
     */
    public String infixToPrefix() throws EquationNotBalancedException {
        if (!balanced) {
            throw new EquationNotBalancedException("Equation is not valid " +
                    "(not balanced).");
        } else {
            EquationStack stack = new EquationStack();
            StringBuilder prefix = new StringBuilder();

            for (int i = equation.length() - 1; i >= 0; i--) {
                char ch = equation.charAt(i);

                if (Character.isDigit(ch)) {
                    while (i >= 0 && Character.isDigit(equation.charAt(i))) {
                        prefix.append(equation.charAt(i));
                        i--;
                    }
                    prefix.append(" ");
                    i++;
                } else if (ch == ')') {
                    stack.push(String.valueOf(ch));
                } else if (ch == '(') {
                    while (!stack.isEmpty() && !stack.peek().equals(")")) {
                        prefix.append(stack.pop());
                        prefix.append(" ");
                    }
                    stack.pop();
                } else if (operatorPrecedence(ch) > -1) {
                    while (!stack.isEmpty() &&
                            operatorPrecedence(stack.peek().charAt(0)) >=
                            operatorPrecedence(ch)) {
                        prefix.append(stack.pop());
                        prefix.append(" ");
                    }
                    stack.push(String.valueOf(ch));
                }
            }

            while (!stack.isEmpty()) {
                prefix.append(stack.pop());
                prefix.append(" ");
            }

            return prefix.reverse().toString().trim();
        }
    }

    /**
     * Converts this equation from in-fix notation to post-fix notation.
     *
     * @return The equation in post-fix notation as a String.
     * @throws EquationNotBalancedException
     * Thrown if the equation is not valid (not balanced)
     */
    public String infixToPostfix() throws EquationNotBalancedException {
        if (!balanced) {
            throw new EquationNotBalancedException("Equation is not valid " +
                    "(not balanced).");
        } else {
            EquationStack stack = new EquationStack();
            StringBuilder postfix = new StringBuilder();

            for (int i = 0; i < equation.length(); i++) {
                char ch = equation.charAt(i);

                if (Character.isDigit(ch)) {
                    while (i < equation.length() &&
                            Character.isDigit(equation.charAt(i))) {
                        postfix.append(equation.charAt(i));
                        i++;
                    }
                    postfix.append(" ");
                    i--;
                } else if (ch == '(') {
                    stack.push(String.valueOf(ch));
                } else if (ch == ')') {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        postfix.append(stack.pop());
                        postfix.append(" ");
                    }
                    stack.pop();
                } else if (operatorPrecedence(ch) > -1) {
                    while (!stack.isEmpty() &&
                            (operatorPrecedence(stack.peek().charAt(0)) >
                            operatorPrecedence(ch) ||
                            (operatorPrecedence(stack.peek().charAt(0)) ==
                            operatorPrecedence(ch) && ch != '^'))) {
                        postfix.append(stack.pop());
                        postfix.append(" ");
                    }
                    stack.push(String.valueOf(ch));
                }
            }

            while (!stack.isEmpty()) {
                postfix.append(stack.pop());
                postfix.append(" ");
            }

            return postfix.toString();
        }
    }

    /**
     * Checks to see if s is an operator.
     *
     * @param s The String to check.
     * @return True if s is one of the expected operator symbols, otherwise
     * false.
     */
    public static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*")
                || s.equals("%") || s.equals("^");
    }

    /**
     * Calculates the operation based on the given operator and operands.
     *
     * @param operand2 the operand 2
     * @param operand1 the operand 1
     * @param operator the operator
     * @return The result of the calculation as a String.
     */
    public String calculateOperation(double operand2, double operand1,
                                     String operator) {
        switch (operator) {
            case "+":
                return String.valueOf(operand1 + operand2);
            case "-":
                return String.valueOf(operand1 - operand2);
            case "*":
                return String.valueOf(operand1 * operand2);
            case "/":
                return String.valueOf(operand1 / operand2);
            case "%":
                return String.valueOf(operand1 % operand2);
            case "^":
                return String.valueOf(Math.pow(operand1, operand2));
        }
        return "";
    }

    /**
     * Evaluates an equation in post-fix notation.
     *
     * @return The answer of the post-fix expression as a double.
     * @throws EquationNotBalancedException
     * Thrown if the equation is not valid (not balanced) or if equation is
     * unable to be calculated.
     */
    public double evaluatePostfix() throws EquationNotBalancedException {
        if (!balanced) {
            throw new EquationNotBalancedException("Equation is not valid " +
                    "(not balanced).");
        } else {
            EquationStack stack = new EquationStack();
            String[] tokens = postfix.split(" ");
            double operand2, operand1;

            for (int i = 0; i < tokens.length; i++) {
                String curr = tokens[i];

                if (isOperator(curr)) {
                    if(stack.size() < 2){
                        balanced = false;
                        throw new EquationNotBalancedException(
                                "Not enough operands.");
                    }

                    try{
                        operand2 = Double.parseDouble(stack.pop());
                        operand1 = Double.parseDouble(stack.pop());
                    }catch(NumberFormatException ex){
                        balanced = false;
                        throw new EquationNotBalancedException(
                                "Not enough numbers.");
                    }

                    if (operand2 == 0 && curr.equals("/")) {
                        balanced = false;
                        throw new EquationNotBalancedException(
                                "Divisor can't be zero.");
                    }

                    stack.push(calculateOperation(operand2, operand1, curr));
                } else {
                    stack.push(curr);
                }
            }

            return Double.parseDouble(stack.pop());
        }
    }

    /**
     * Determines if the equation is balanced or not.
     * This means that the equation has properly matched parentheses.
     *
     * @return True if the equation is balanced, false otherwise.
     */
    public boolean isBalanced() {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < equation.length(); i++) {
            if (equation.charAt(i) == '(') {
                stack.push('(');
            } else {
                if (equation.charAt(i) == ')') {
                    if (!stack.empty() && stack.peek() == '(') {
                        stack.pop();
                    } else {
                        return false;
                    }
                }
            }
        }
        return stack.empty();
    }

    /**
     * Converts a number from decimal (base 10) to the given base.
     *
     * @param number
     * The number to convert.
     * @param base
     * The base to convert to.
     * @return The number in given base as a String.
     */
    public String decimalToBase(int number, int base) {
        String[] ch = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F"};
        if (number == 0) {
            return "0";
        } else {
            StringBuilder result = new StringBuilder();

            do {
                result.append(ch[Math.abs(number % base)]);
                number /= base;
            } while (number > 0);

            return result.reverse().toString();
        }
    }

    /**
     * Converts number from decimal (base 10) to binary (base 2).
     *
     * @param number The number to convert.
     * @return The number in base 2 as a String.
     * @throws EquationNotBalancedException
     * Thrown if the equation is not valid (not balanced),
     */
    public String decToBin(int number) throws EquationNotBalancedException {
        if (!balanced) {
            throw new EquationNotBalancedException("Equation is not valid " +
                    "(not balanced).");
        } else {
            return decimalToBase(number, 2);
        }
    }

    /**
     * Converts number from decimal (base 10) to hexadecimal (base 16).
     *
     * @param number The number to convert.
     * @return The number in base 16 as a String.
     * @throws EquationNotBalancedException
     * Thrown if the equation is not valid (not balanced),
     */
    public String decToHex(int number) throws EquationNotBalancedException {
        if (!balanced) {
            throw new EquationNotBalancedException("Equation is not valid " +
                    "(not balanced).");
        } else {
            return decimalToBase(number, 16);
        }
    }

    /**
     * Returns a String representation of this Equation object,
     * which includes it's information of equation, prefix, postfix,
     * answer, binary, and departure hex.
     *
     * @return
     * A textual representation of all the information for this Equation object.
     */
    public String toString() {
        return String.format("%-35s%-35s%-24s%16.3f%18s%17s%n",
                equation, prefix, postfix, answer, binary, hex);
    }
}

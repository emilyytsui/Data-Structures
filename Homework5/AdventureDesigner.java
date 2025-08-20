/**
 * This class contains the main method which is used to
 * guide the user through making and possibly playing the game.
 *
 * @author Emily Tsui
 */

import java.util.Scanner;

public class AdventureDesigner {
    private static SceneTree sceneTree = new SceneTree();
    private static Scanner stdin = new Scanner(System.in);

    /**
     * The main driver for the program, which initializes a SceneTree object
     * and guides users through the menu to help them create their own
     * adventures.
     *
     * @param args An array of command-line arguments passed to the program
     */
    public static void main(String[] args) {
        boolean done = false;
        String title, sceneDescription, option;
        int sceneId;

        System.out.println("Creating a story...\n");

        System.out.print("Please enter a title: ");
        title = stdin.nextLine();
        System.out.print("Please enter a scene: ");
        sceneDescription = stdin.nextLine();

        try {
            sceneTree.addNewNode(title, sceneDescription);
            System.out.println("\nScene #" +
                    sceneTree.getCursor().getSceneID() + " added.");
        } catch (FullSceneException ex) {
            System.out.println(ex);
        }

        while (!done) {
            System.out.print(
                    "\nA) Add Scene\n" +
                    "R) Remove Scene\n" +
                    "S) Show Current Scene\n" +
                    "P) Print Adventure Tree\n" +
                    "B) Go Back A Scene\n" +
                    "F) Go Forward A Scene\n" +
                    "G) Play Game\n" +
                    "N) Print Path To Cursor\n" +
                    "M) Move Scene\n" +
                    "Q) Quit\n\n" +
                    "Please enter a selection: ");

            String selection = stdin.nextLine().toUpperCase();

            switch (selection) {
                case "A": //Add Scene
                    try {
                        System.out.print("\nPlease enter a title: ");
                        title = stdin.nextLine();
                        System.out.print("Please enter a scene: ");
                        sceneDescription = stdin.nextLine();

                        sceneTree.addNewNode(title, sceneDescription);
                        System.out.println("\nScene #" +
                                SceneNode.getNumScenes() + " added.");
                    } catch (FullSceneException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("You cannot add another scene!");
                    }
                    break;
                case "R": //Remove Scene
                    try{
                        System.out.print("\nPlease enter an option: ");
                        option = stdin.nextLine();
                        SceneNode nodeToRemove = null;

                        if(option.equals("A")){
                            nodeToRemove = sceneTree.getCursor().getLeft();
                        }else if(option.equals("B")){
                            nodeToRemove = sceneTree.getCursor().getMiddle();
                        }else if(option.equals("C")){
                            nodeToRemove = sceneTree.getCursor().getRight();
                        }

                        sceneTree.removeScene(option);
                        System.out.println("\n" + nodeToRemove.getTitle() +
                                " removed.");
                    } catch (NoSuchNodeException ex) {
                        System.out.println("\n" +ex);
                        System.out.println("There is no scene to be removed!");
                    }
                    break;
                case "S": //Show Current Scene
                    System.out.println();
                    sceneTree.getCursor().displayFullScene();
                    break;
                case "P": //Print Adventure Tree
                    System.out.print("\n" + sceneTree.toString());
                    break;
                case "B": //Go Back A Scene
                    try{
                        sceneTree.moveCursorBackwards();
                        System.out.println("\nSuccessfully moved back to " +
                                sceneTree.getCursor().getTitle() + ".");
                    } catch (NoSuchNodeException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("There is no scene " +
                                "to move back to!");
                    }
                    break;
                case "F": //Go Forward A Scene
                    try{
                        System.out.print("\nWhich option do you wish to go " +
                                "to: ");
                        option = stdin.nextLine();

                        sceneTree.moveCursorForward(option);
                        System.out.println("\nSuccessfully moved to " +
                                sceneTree.getCursor().getTitle() + ".");
                    } catch (NoSuchNodeException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("That option does not exist.");
                    }
                    break;
                case "G": //Play Game
                    playGame();
                    break;
                case "N": //Print Path To Cursor
                    System.out.println("\n" + sceneTree.getPathFromRoot());
                    break;
                case "M": //Move scene
                    try {
                        System.out.print("\nMove current scene to: ");
                        sceneId = stdin.nextInt();
                        stdin.nextLine();
                        sceneTree.moveScene(sceneId);
                        System.out.println("\nSuccessfully moved scene.");
                    } catch (NoSuchNodeException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("That option does not exist.");
                    } catch (FullSceneException ex) {
                        System.out.println("\n" + ex);
                        System.out.println("The scene is full!");
                    }
                    break;
                case "Q": //Terminates the program.
                    System.out.println("\nProgram terminating normally...");
                    done = true;
                    break;
                default:
                    System.out.println("\nPlease enter a selection " +
                            "from the menu.");
            }
        }
        stdin.close();
    }

    /**
     * This method start at the root of the tree and displays the scene.
     * It asks the user to select an option
     * and prints the scene for that option.
     * This continues until a leaf node has been
     * displayed, at which point the adventure is concluded
     * and the program returns to the main method.
     */
    public static void playGame(){
        String option;
        SceneNode curr = sceneTree.getRoot();

        System.out.println("\nNow beginning game...");

        while(!curr.isEnding()){
            System.out.println();
            curr.displayScene();

            System.out.print("\nPlease enter an option: ");
            option = stdin.next();

            if (option.equals("A")) {
                curr = curr.getLeft();
            } else if (option.equals("B")) {
                curr = curr.getMiddle();
            } else if (option.equals("C")) {
                curr = curr.getRight();
            }
        }

        System.out.println();
        curr.displayScene();

        System.out.println("The End\n");
        System.out.println("Returning back to creation mode...");
    }
}

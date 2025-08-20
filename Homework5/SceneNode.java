/**
 * This class represents a specific scene. scene.
 * A scene has a title, a sceneDescription, a sceneID, and
 * up to three possible child SceneNode references (left, middle, and right).
 *
 * @author Emily Tsui
 */
public class SceneNode {
    private String title, sceneDescription;
    private int sceneID;
    private SceneNode left, middle, right, parent;
    private static int numScenes;

    /**
     * This is a Constructor used to create a new SceneNode object.
     */
    public SceneNode() {
        title = "";
        sceneDescription = "";
        sceneID = ++numScenes;
        left = null;
        middle = null;
        right = null;
        parent = null;
    }

    /**
     * This is a Constructor used to create a new SceneNode object with the
     * specified parameters.
     *
     * @param title            the title of this SceneNode object
     * @param sceneDescription the scene description of this SceneNode object
     */
    public SceneNode(String title, String sceneDescription) {
        this.title = title;
        this.sceneDescription = sceneDescription;
        sceneID = ++numScenes;
        left = null;
        middle = null;
        right = null;
        parent = null;
    }

    /**
     * Gets the title of this SceneNode object.
     *
     * @return the title as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the left child of this SceneNode object..
     *
     * @return the left child as a SceneNode object
     */
    public SceneNode getLeft() {
        return left;
    }

    /**
     * Gets the middle child of this SceneNode object.
     *
     * @return the middle child as a SceneNode object
     */
    public SceneNode getMiddle() {
        return middle;
    }

    /**
     * Gets the right child of this SceneNode object.
     *
     * @return the right child as a SceneNode object
     */
    public SceneNode getRight() {
        return right;
    }

    /**
     * Gets the parent of this SceneNode object.
     *
     * @return the parent node as a SceneNode object
     */
    public SceneNode getParent() {
        return parent;
    }

    /**
     * Gets the scene id of this SceneNode object.
     *
     * @return the scene id as an int
     */
    public int getSceneID() {
        return sceneID;
    }

    /**
     * Gets the total number of how many scenes have been created so far.
     *
     * @return the number of scenes as int
     */
    public static int getNumScenes() {
        return numScenes;
    }

    /**
     * Sets the left child of SceneNode object.
     *
     * @param left the left child reference
     */
    public void setLeft(SceneNode left) {
        this.left = left;
    }

    /**
     * Sets the middle child of SceneNode object.
     *
     * @param middle the middle child reference
     */
    public void setMiddle(SceneNode middle) {
        this.middle = middle;
    }

    /**
     * Sets the right child of SceneNode object.
     *
     * @param right the right child reference
     */
    public void setRight(SceneNode right) {
        this.right = right;
    }

    /**
     * Sets the parent of SceneNode object.
     *
     * @param parent the parent reference
     */
    public void setParent(SceneNode parent) {
        this.parent = parent;
    }

    /**
     * Sets the count of how many scenes have been created.
     *
     * @param numScenes how many scenes have been created so far
     */
    public static void setNumScenes(int numScenes){
        SceneNode.numScenes = numScenes;
    }

    /**
     * Sets the scene to the first available slot in the child nodes.
     * Children are be added in the left-most node.
     *
     * @param scene the scene to be added
     * @throws FullSceneException
     * thrown if the current node does not have any empty child nodes
     */
    public void addSceneNode(SceneNode scene) throws FullSceneException {
        if (left == null) {
            left = scene;
        } else if (middle == null) {
            middle = scene;
        } else if (right == null) {
            right = scene;
        } else {
            throw new FullSceneException("The current node does not have any " +
                    "empty child nodes.");
        }
    }

    /**
     * Determines if this scene is an ending for a storyline.
     * If this node has no children (a leaf), then the user is deemed to have
     * reached the end of that particular story path.
     *
     * @return true if this scene is the ending of a storyline, false otherwise.
     */
    public boolean isEnding() {
        return left == null && middle == null && right == null;
    }

    /**
     * Outputs the scene information,
     * as would be shown during gameplay (option G).
     */
    public void displayScene() {
        System.out.println(title);
        System.out.println(sceneDescription + "\n");

        if (left != null) {
            System.out.println("A) " + left.title);
        }
        if (middle != null) {
            System.out.println("B) " + middle.title);
        }
        if (right != null) {
            System.out.println("C) " + right.title);
        }
    }

    /**
     * Outputs all information about a scene,
     * as would be shown in creation mode (option S).
     */
    public void displayFullScene() {
        System.out.println("Scene ID #" + sceneID);
        System.out.println("Title: " + title);
        System.out.println("Scene: " + sceneDescription);
        System.out.print("Leads to: ");

        if (isEnding()) {
            System.out.print("NONE");
        } else {
            if (left != null) {
                System.out.print("'" + left.title +
                        "' (#" + left.sceneID + ")");
            }
            if (middle != null) {
                System.out.print(", '" + middle.title +
                        "' (#" + middle.sceneID + ")");
            }
            if (right != null) {
                System.out.print(", '" + right.title +
                        "' (#" + right.sceneID + ")");
            }
        }
        System.out.println();
    }

    /**
     * Returns a String representation of this SceneNode object,
     * which includes it's information of title and sceneID.
     *
     * @return
     * A String representing a brief summary of this node.
     */
    public String toString() {
        return title + " (#" + sceneID + ")";
    }
}

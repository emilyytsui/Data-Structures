/**
 * This class represents a collection of SceneNode objects arranged as a
 * ternary tree.
 *
 * @author Emily Tsui
 */
public class SceneTree {
    private SceneNode root, cursor;

    /**
     * This is a Constructor used to create a new SceneTree object.
     */
    public SceneTree() {
        root = null;
        cursor = null;
    }

    /**
     * Gets root node of this SceneTree.
     *
     * @return the root node as a SceneNode object.
     */
    public SceneNode getRoot() {
        return root;
    }

    /**
     * Gets the cursor reference.
     *
     * @return the cursor as a SceneNode object.
     */
    public SceneNode getCursor() {
        return cursor;
    }

    /**
     * Moves the cursor to the parent node.
     *
     * @throws NoSuchNodeException
     * Thrown if the current node does not have a parent.
     */
    public void moveCursorBackwards() throws NoSuchNodeException {
        if (cursor == null || cursor.getParent() == null) {
            throw new NoSuchNodeException("The current node does not have a " +
                    "parent.");
        } else {
            cursor = cursor.getParent();
        }
    }

    /**
     * Moves the cursor to the appropriate child node.
     *
     * @param option
     * A String ("A", "B", or "C") dictating whether we should select the
     * left, middle, or right child, respectively.
     *
     * @throws NoSuchNodeException
     * Thrown if the current node does not have any such child.
     */
    public void moveCursorForward(String option) throws NoSuchNodeException {
        if (option.equals("A") && cursor.getLeft() != null) {
            cursor = cursor.getLeft();
        } else if (option.equals("B") && cursor.getMiddle() != null) {
            cursor = cursor.getMiddle();
        } else if (option.equals("C") && cursor.getRight() != null) {
            cursor = cursor.getRight();
        } else {
            throw new NoSuchNodeException("The current node does not have" +
                    " any such child.");
        }
    }

    /**
     * Creates a new SceneNode object as a child of the current node
     * with the supplied values.
     * It will be set in the first empty left, middle, or right node.
     *
     * @param title            the title of the scene
     * @param sceneDescription the scene description
     * @throws FullSceneException
     * Thrown if the current node does not have any available child positions.
     */
    public void addNewNode(String title, String sceneDescription)
            throws FullSceneException {
        SceneNode newSceneNode = new SceneNode(title, sceneDescription);
        if (root == null) {
            root = newSceneNode;
            cursor = root;
        } else {
            try {
                newSceneNode.setParent(cursor);
                cursor.addSceneNode(newSceneNode);
            } catch (FullSceneException ex) {
                SceneNode.setNumScenes(SceneNode.getNumScenes() - 1);
                throw new FullSceneException("The current node does not have " +
                        "any available child positions.");
            }
        }
    }

    /**
     * Removes the specified child node from the tree.
     *
     * @param option
     * A String ("A", "B", or "C") dictating whether we should select the
     * left, middle, or right child, respectively.
     * @throws NoSuchNodeException
     * Thrown if the current node does not have any such child.
     */
    public void removeScene(String option) throws NoSuchNodeException {
        if (option.equals("A") && cursor.getLeft() != null) {
            cursor.setLeft(cursor.getMiddle());
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
        } else if (option.equals("B") && cursor.getMiddle() != null) {
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
        } else if (option.equals("C") && cursor.getRight() != null) {
            cursor.setRight(null);
        } else {
            throw new NoSuchNodeException("The current node does not have" +
                    " any such child.");
        }
    }

    /**
     * A helper method for the moveScene method. This method traverses the
     * tree and searches to see if a SceneNode exists with the specified
     * sceneID.
     *
     * @param node    The current node/SceneNode of the tree
     * @param sceneID the Scene ID# that needs to be located.
     * @return A reference to the SceneNode if found, otherwise null.
     */
    public SceneNode findSceneNode(SceneNode node, int sceneID) {
        if (node == null) {
            return null;
        }

        if (node.getSceneID() == sceneID) {
            return node;
        }

        SceneNode leftResult = findSceneNode(node.getLeft(), sceneID);
        if (leftResult != null) {
            return leftResult;
        }

        SceneNode middleResult = findSceneNode(node.getMiddle(), sceneID);
        if (middleResult != null) {
            return middleResult;
        }

        SceneNode rightResult = findSceneNode(node.getRight(), sceneID);
        if (rightResult != null) {
            return rightResult;
        }

        return null;
    }

    /**
     * Moves the node at cursor to be a child of the node with the specified ID.
     *
     * @param sceneIDToMoveTo
     * An integer which specifies which node this SceneNode object
     * should be moved under.
     * @throws NoSuchNodeException
     * Thrown if there does not exist a SceneNode with the supplied Scene ID.
     * @throws FullSceneException
     * Thrown if the SceneNode specified by the supplied Scene ID does not
     * have any available child positions.
     */
    public void moveScene(int sceneIDToMoveTo)
            throws NoSuchNodeException, FullSceneException {
        SceneNode nodeToMoveTo = findSceneNode(root, sceneIDToMoveTo);

        if (nodeToMoveTo == null) {
            throw new NoSuchNodeException("There does not exist a SceneNode " +
                    "with the supplied Scene ID.");
        } else if (nodeToMoveTo.getLeft() != null &&
                nodeToMoveTo.getMiddle() != null &&
                nodeToMoveTo.getRight() != null) {
            throw new FullSceneException("The current node does not have " +
                    "any available child positions.");
        } else {
            try {
                SceneNode cursorCopy = cursor;
                if (cursor.getParent().getLeft() == cursor) {
                    cursor = cursorCopy.getParent();
                    removeScene("A");
                } else if (cursor.getParent().getMiddle() == cursor) {
                    cursor = cursorCopy.getParent();
                    removeScene("B");
                } else if (cursor.getParent().getRight() == cursor) {
                    cursor = cursorCopy.getParent();
                    removeScene("C");
                }
                cursor = cursorCopy;
                cursor.setParent(nodeToMoveTo);
                nodeToMoveTo.addSceneNode(cursor);
            } catch (NoSuchNodeException ex) {
                throw new NoSuchNodeException("The current node does not have" +
                        " any such child.");
            } catch (FullSceneException ex) {
                throw new FullSceneException("The current node does not have " +
                        "any available child positions.");
            }
        }
    }

    /**
     * Constructs a String showing the path from the root of the tree
     * to the currently selected SceneNode, illustrating the decisions
     * which would have to be made to arrive at the selected node (option N).
     *
     * @return
     * A String representing the path between the root of the tree
     * and the currently selected SceneNode.
     */
    public String getPathFromRoot() {
        SceneNode curr = cursor;
        StringBuilder path = new StringBuilder();

        while (curr != root) {
            path.insert(0, ", " + curr.getTitle());
            curr = curr.getParent();
        }

        path.insert(0, curr.getTitle());

        return path.toString();
    }

    /**
     * Constructs a String representation of the tree.
     *
     * @return
     * A String representation of the tree.
     */
    public String toString() {
        if (root == null) {
            return "";
        } else {
            return printTreePreorder(root, 0, "");
        }
    }

    /**
     * A helper method for the toString method. This method does a
     * preorder traversal of this SceneTree. It traverses the current, left
     * child, middle child, and right child, respectively. Each level of the
     * tree represents how many indentations are added.
     *
     * @param node     the current node in the SceneTree
     * @param level
     * the level of the current node in the tree, used for indentation
     * @param location
     * A String that indicates the location of the node relative to
     * its parent, where "A" represents the left child, "B" represents the
     * middle child, "C" represents the right child, and "" represents none.
     *
     * @return the preorder traversal of this SceneTree as a String.
     */
    public String printTreePreorder(SceneNode node, int level, String location) {
        if (node == null) {
            return "";
        }

        StringBuilder tree = new StringBuilder();

        for (int i = 0; i < level; i++) {
            tree.append("\t");
        }

        tree.append(location + node.toString() +
                        (node.equals(cursor) ? " *" : "") + "\n");

        tree.append(printTreePreorder(node.getLeft(),
                level + 1, "A) "));
        tree.append(printTreePreorder(node.getMiddle(),
                level + 1, "B) "));
        tree.append(printTreePreorder(node.getRight(),
                level + 1, "C) "));

        return tree.toString();
    }
}

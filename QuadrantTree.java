import java.util.ArrayList;
import java.util.List;

public class QuadrantTree {
    private QTreeNode root;

    // Constructor: Builds the quadrant tree using the given pixel data.
    public QuadrantTree(int[][] thePixels) {
        root = buildTree(thePixels, 0, 0, thePixels.length);
    }

    // Getter for the root of the tree.
    public QTreeNode getRoot() {
        return root;
    }

    // Collects and returns pixels at a specific tree level as a linked list.
    public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
        List<QTreeNode> result = new ArrayList<>();
        getPixelsHelper(r, theLevel, 0, result);
        return listToLinkedList(result);
    }

    // Converts a List of nodes into a linked list structure.
    private ListNode<QTreeNode> listToLinkedList(List<QTreeNode> nodes) {
        ListNode<QTreeNode> head = null;
        ListNode<QTreeNode> prev = null;
        for (QTreeNode node : nodes) {
            ListNode<QTreeNode> current = new ListNode<>(node);
            if (head == null) {
                head = current;
            } else {
                prev.setNext(current);
            }
            prev = current;
        }
        return head;
    }

    // Helper method to recursively gather pixels from the specified level.
    private void getPixelsHelper(QTreeNode node, int targetLevel, int currentLevel, List<QTreeNode> result) {
        if (node == null || currentLevel > targetLevel) {
            return;
        }

        if (currentLevel == targetLevel || node.isLeaf()) {
            result.add(node);
        } else {
            for (QTreeNode child : node.getChildren()) {
                getPixelsHelper(child, targetLevel, currentLevel + 1, result);
            }
        }
    }

    // Finds and returns matching pixels by color at a certain level.
    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
        List<QTreeNode> matchingNodes = new ArrayList<>();
        int count = findMatchingHelper(r, theColor, theLevel, 0, matchingNodes);
        ListNode<QTreeNode> front = createLinkedList(matchingNodes); // Convert list to linked list
        return new Duple(front, count);
    }

    // Helper method to create a linked list from a list of nodes.
    private ListNode<QTreeNode> createLinkedList(List<QTreeNode> nodes) {
        ListNode<QTreeNode> head = null;
        ListNode<QTreeNode> current = null;

        for (QTreeNode node : nodes) {
            ListNode<QTreeNode> newNode = new ListNode<>(node);
            if (head == null) {
                head = newNode;
                current = newNode;
            } else {
                current.setNext(newNode);
                current = newNode;
            }
        }

        return head;
    }

    // Recursively searches for nodes matching a specific color and level, returning the count of such nodes.
    private int findMatchingHelper(QTreeNode node, int targetColor, int targetLevel, int currentLevel, List<QTreeNode> result) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        if (currentLevel == targetLevel || node.isLeaf()) {
            if (Gui.similarColor(node.getColor(), targetColor)) {
                result.add(node);
                count++;
            }
        } else {
            for (QTreeNode child : node.getChildren()) {
                count += findMatchingHelper(child, targetColor, targetLevel, currentLevel + 1, result);
            }
        }

        return count;
    }

    // Finds a specific node based on level and coordinates.
    public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
        if (r == null || theLevel < 0) {
            return null;
        }

        if (theLevel == 0 || r.isLeaf()) {
            return r;
        }

        int size = r.getSize();
        int halfSize = size / 2;

        if (x < 0 || x > size - 1 || y < 0 || y > size - 1) {
            return null;
        }

        // Determine which quadrant the coordinates fall into and recursively search that quadrant.
        if (x < halfSize && y < halfSize) {
            return findNode(r.getChild(0), theLevel - 1, x, y);
        } else if (x >= halfSize && y < halfSize) {
            return findNode(r.getChild(1), theLevel - 1, x - halfSize, y);
        } else if (x < halfSize && y >= halfSize) {
            return findNode(r.getChild(2), theLevel - 1, x, y - halfSize);
        } else {
            return findNode(r.getChild(3), theLevel - 1, x - halfSize, y - halfSize);
        }
    }

    // Builds the tree recursively based on the provided pixel matrix.
    private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
        // Base cases for leaf nodes and empty nodes.
        if (size == 1) {
            return new QTreeNode(x, y, size, pixels[y][x], null);
        } else if (size == 0) {
            return null;
        } else {
            // Recursively build child nodes for each quadrant.
            int halfSize = size / 2;
            QTreeNode[] children = new QTreeNode[4];
            children[0] = buildTree(pixels, x, y, halfSize);
            children[1] = buildTree(pixels, x + halfSize, y, halfSize);
            children[2] = buildTree(pixels, x, y + halfSize, halfSize);
            children[3] = buildTree(pixels, x + halfSize, y + halfSize, halfSize); // Last quadrant uses the same size for simplicity.
            // Create the parent node with the average color of its quadrant.
            QTreeNode node = new QTreeNode(x, y, size, averageColor(pixels, x, y, size), children);
            return node;
        }
    }

    // Computes the average color of a given quadrant.
    private int averageColor(int[][] pixels, int x, int y, int size) {
        if (pixels.length == 0) {
            return 0; // Default color for empty array
        }
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        // Sum up the color components for all pixels in the quadrant.
        for (int i = y; i < y + size && i < pixels.length; i++) {
            for (int j = x; j < x + size && j < pixels[i].length; j++) {
                int color = pixels[i][j];
                redSum += (color >> 16) & 0xFF;
                greenSum += (color >> 8) & 0xFF;
                blueSum += color & 0xFF;
            }
        }

        // Calculate the average color components.
        int numPixels = size * size;
        int averageRed = redSum / numPixels;
        int averageGreen = greenSum / numPixels;
        int averageBlue = blueSum / numPixels;

        // Combine the average components back into a single integer.
        return (0xFF << 24) | (averageRed << 16) | (averageGreen << 8) | averageBlue;
    }
}

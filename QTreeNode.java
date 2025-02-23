// Import the Math class for mathematical operations
import java.lang.Math;

// Define the QTreeNode class representing a node in a quadtree structure
public class QTreeNode {
    // Coordinates of the node
    private int x;
    private int y;
    // Size of the node's area
    private int size;
    // Color value of the node, typically used for image compression
    private int color;
    // Reference to the parent node, null for the root node
    private QTreeNode parent;
    // Array to hold references to the node's children, supports up to 4 as per quadtree structure
    private QTreeNode[] children;

    // Default constructor to create a node with initialized values and no children
    public QTreeNode() {
        // Set parent to null and initialize children array with null values
        parent = null;
        children = new QTreeNode[4];
        // Initialize position, size, and color to default values (0)
        x = 0;
        y = 0;
        size = 0;
        color = 0;
    }

    // Constructor that initializes a node with specific values, intended for internal use or testing
    public QTreeNode(Object object, int j, int k, int l, int m) {
        // Initialize node with specified values; the Object parameter is unused
        this(); // Calls the default constructor to initialize parent and children
        x = j;
        y = k;
        size = l;
        color = m;
    }

    // Primary constructor to create a node with specific attributes and a parent reference
    public QTreeNode(int xcoord, int ycoord, int theSize, int theColor, QTreeNode theParent) {
        // Set the node's position, size, color, and parent based on parameters
        this(); // Ensures children array is initialized
        x = xcoord;
        y = ycoord;
        size = theSize;
        color = theColor;
        parent = theParent;
    }

    // Getter methods for accessing the node's properties
    public int getx() { return x; }
    public int gety() { return y; }
    public int getSize() { return size; }
    public int getColor() { return color; }
    public QTreeNode getParent() { return parent; }

    // Method to retrieve a specific child node by index
    public QTreeNode getChild(int index) throws QTreeException {
        // Validate index and ensure children array is not null before returning the child
        if (children == null || index < 0 || index > 3) {
            throw new QTreeException("Invalid index or null children array");
        }
        return children[index];
    }

    // Setter methods for updating the node's properties
    public void setx(int newx) { x = newx; }
    public void sety(int newy) { y = newy; }
    public void setSize(int newSize) { size = newSize; }
    public void setColor(int newColor) { color = newColor; }
    public void setParent(QTreeNode newParent) { parent = newParent; }

    // Method to assign a specific child node to a given index
    public void setChild(QTreeNode newChild, int index) throws QTreeException {
        // Validate index and ensure children array is not null before setting the child
        if (children == null || index < 0 || index > 3) {
            throw new QTreeException("Invalid index or null children array");
        }
        children[index] = newChild;
    }

    // Getter for the children array
    public QTreeNode[] getChildren() { return children; }
    // Setter for the children array
    public void setChildren(QTreeNode[] newChildren) { children = newChildren; }

    // Method to check if the node contains a given point (xcoord, ycoord)
    public boolean contains(int xcoord, int ycoord) {
        // Round the input coordinates to the nearest integers for consistency
        xcoord = Math.round(xcoord);
        ycoord = Math.round(ycoord);

        // Check if the coordinates fall within the node's area
        return xcoord >= x && xcoord < x + size && ycoord >= y && ycoord < y + size;
    }

    // Method to check if the node is a leaf, i.e., has no children
    public boolean isLeaf() {
        // Iterate through the children array; if any child exists, the node is not a leaf
        for (int i = 0; i < 4; i++) {
            if (children[i] != null) {
                return false;
            }
        }
        return true; // Returns true if all children are null
    }
}

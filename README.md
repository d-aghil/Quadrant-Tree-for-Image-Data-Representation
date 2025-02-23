# Quadrant Tree for Image Data Representation

Quadrant Tree Implementation

This repository contains a Java implementation of a Quadtree data structure, commonly used in image processing, spatial indexing, and compression algorithms. The implementation includes two primary classes:

Files
	- QTreeNode.java:
Defines the QTreeNode class, representing individual nodes in the quadtree. Each node contains:
	- Coordinates (x, y)
	- Size of the node’s area
	- Color value (useful in image compression)
	- References to child nodes (quadrants) and the parent node
	- QuadrantTree.java:
Manages the construction and traversal of the quadtree using pixel data:
	- Builds the tree from a 2D pixel array.
	- Provides methods to retrieve nodes at specific levels.
	- Handles spatial subdivision for efficient data representation. 

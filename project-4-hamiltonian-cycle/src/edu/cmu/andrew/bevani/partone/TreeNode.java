/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 1
*/
package edu.cmu.andrew.bevani.partone;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* This class is used to create a node of MST
*
* Class invariants:
* 
* vertex -> Vertex Number of graph
* children -> The children of this node
* 
*/
public class TreeNode {
	
	// Class Invariants
	private int vertex;
	
	private SinglyLinkedList children;
	
	// Parameterized Constructor
	public TreeNode(int vertex) {
		this.vertex = vertex;
		children = new SinglyLinkedList(); // Initially no children are present
	}

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}

	public SinglyLinkedList getChildren() {
		return children;
	}

	public void addChild(TreeNode node) {
		this.children.addAtEndNode(node);;
	}
}

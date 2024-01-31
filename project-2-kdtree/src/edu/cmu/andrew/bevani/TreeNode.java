/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

/**
 * Class used to initialize node used to TwoDTree
 *
 * Class Invariants:
 * left - left pointer to TreeNode
 * right - right pointer to TreeNode
 * entry - value to be store in a node
 */
public class TreeNode {
	
	// class invariants
	private TreeNode left;
	
	private Entry entry;
	
	private TreeNode right;

	/**
	 * Constructor
	 * 
	 * @param entry
	 * @postcondition
	 * 	Initializes the node with value entry and
	 * 	left = right is set as null
	 */
	public TreeNode(Entry entry) {
		this.entry = entry;
		this.left = this.right = null;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}
}

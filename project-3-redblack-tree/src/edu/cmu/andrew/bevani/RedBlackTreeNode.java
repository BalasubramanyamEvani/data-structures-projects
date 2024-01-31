/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 3
 */
package edu.cmu.andrew.bevani;

import java.util.Objects;

/*
* RedBlack Tree Node
* 
* Class invariants:
* 
* left -> to store the pointer to left node
* right -> to store the pointer to right node
* parent -> to track the parent of the node
* subject -> to store the subject associated with the node
* index -> this is used to store the index associated with the subject
* color -> this is used to store the color of node [1 -> red, 0 -> black]
* 
*/
public class RedBlackTreeNode {
	
	// Class Invariants
	private RedBlackTreeNode left;
	
	private RedBlackTreeNode right;
	
	private RedBlackTreeNode parent;
	
	private String subject;
	
	private int index;
	
	private int color;

	// Constructor using subject and index[will be fed from RedBlackTree insert method]
	public RedBlackTreeNode(String subject, int index) {
		this.subject = subject;
		this.index = index;
		color = 1;
		left = right = parent = null;
	}

	public RedBlackTreeNode getLeft() {
		return left;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setLeft(RedBlackTreeNode left) {
		this.left = left;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public RedBlackTreeNode getRight() {
		return right;
	}

	public void setRight(RedBlackTreeNode right) {
		this.right = right;
	}

	public RedBlackTreeNode getParent() {
		return parent;
	}

	public void setParent(RedBlackTreeNode parent) {
		this.parent = parent;
	}
	
	/**
	 * Override the hashCode() and equals()
	 * for easier comparison of nodes based on 
	 * subjects since only unique subjects will be 
	 * stored
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RedBlackTreeNode other = (RedBlackTreeNode) obj;
		return Objects.equals(subject, other.subject);
	}

	@Override
	public String toString() {
		return "RedBlackTreeNode [left=" + left + ", subject=" + subject + ", index=" + index + ", right=" + right
				+ "]";
	}
}

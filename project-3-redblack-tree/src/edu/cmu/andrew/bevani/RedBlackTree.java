/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 3
 */
package edu.cmu.andrew.bevani;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* RedBlack Tree Class
* 
* Class invariants:
* 
* root -> to store the root of the RB Tree
* subjectColors -> When we add new subjects to the tree
* a new node will be added to this list as well with no color, this
* will be helpful when graph accesses this data
* size -> to store the size of the tree
* 
*/
public class RedBlackTree {
	
	// Class Invariants
	private RedBlackTreeNode root;
	
	private SinglyLinkedList subjectColors;
	
	private int size;
	
	// Class level Constants to map RED and BLACK color
	private static final int RED = 1;
	private static final int BLACK = 0;
	
	// Constructor to build the tree using studentInfo list
	// that is passed
	public RedBlackTree(SinglyLinkedList list) {
		root = null;
		size = 0;
		subjectColors = new SinglyLinkedList();
		buildTree(list);
	}
	
	/**
	 * @precondition
	 *  1. the list is not empty
	 *  2. The list contains values which are valid and not null
	 * 
	 * @param list
	 * @postcondition
	 * 	The RB Tree is built using the student info list
	 */
	private void buildTree(SinglyLinkedList list) {
		list.reset();
		while (list.hasNext()) {
			StudentInfo student = (StudentInfo) list.next();
			for (String subject: student.getSubjects()) {
				insert(subject);
			}
		}
	}
	
	/**
	 * @return
	 * @postcondition
	 * 	Returns the subjectColors which contains info
	 *  on the order in which the subjects were inserted
	 */
	public SinglyLinkedList getSubjectColors() {
		return subjectColors;
	}
	
	/**
	 * @precondition: 
	 *  1. memory is available for insertion
	 *  2. Valid String is inserted not empty of null
	 * 
	 * @postcondition
	 *  method places a data item (subject) into the tree
	 *  
	 * @param subject
	 */
	public void insert(String subject) {
		if (root == null) {
			root = new RedBlackTreeNode(subject, size);
			root.setColor(BLACK);
			subjectColors.addAtEndNode(new SubjectColor(subject, -1));
			++size;
			return;
		}
		RedBlackTreeNode ptr = root;
		RedBlackTreeNode parent = null;
		
		while (ptr != null) {
			parent = ptr;
			int comp = subject.compareTo(ptr.getSubject());
			if (comp < 0) {
				ptr = ptr.getLeft();
			} else if (comp > 0) {
				ptr = ptr.getRight();
			} else {
				return;
			}
		}
		int comp = subject.compareTo(parent.getSubject());
		RedBlackTreeNode newNode = new RedBlackTreeNode(subject, size);
		subjectColors.addAtEndNode(new SubjectColor(subject, -1));
		if (comp < 0) {
			parent.setLeft(newNode);
		} else {
			parent.setRight(newNode);
		}
		rbInsertFixup(newNode);
		++size;
	}
	
	/**
	 * @precondition
	 * 	1. Left child of node != NIL
	 *  2. Root's parent is NIL
	 * @postcondition
	 * 	performs a single right rotation
	 * @param node
	 */
	private void rightRotate(RedBlackTreeNode node) {
		RedBlackTreeNode parent = node.getParent();
		RedBlackTreeNode leftChild = node.getLeft();
		node.setLeft(leftChild.getRight());
		if (leftChild.getRight() != null) {
			leftChild.getRight().setParent(node);
		}
		leftChild.setRight(node);
		node.setParent(leftChild);
		leftChild.setParent(parent);
		fixChildRelationship(parent, node, leftChild);
	}
	
	/**
	 * @precondition
	 *  1. node right child != NIL
	 *  2. Root's parent is NIL
	 * 
	 * @postcondition
	 *  performs a single left rotation. 
	 * 
	 * @param node
	 */
	private void leftRotate(RedBlackTreeNode node) {
		RedBlackTreeNode parent = node.getParent();
		RedBlackTreeNode rightChild = node.getRight();
		node.setRight(rightChild.getLeft());
		if (rightChild.getLeft() != null) {
			rightChild.getLeft().setParent(node);
		}
		rightChild.setLeft(node);
		node.setParent(rightChild);
		rightChild.setParent(parent);
		fixChildRelationship(parent, node, rightChild);
	}
	
	/**
	 * @precondition
	 *  1. The rotations have taken place during fixup
	 * @postcondition
	 *  1. The parent to child relationships are fixed in this method
	 *  
	 *  When rotations take place we also need to make sure the parent of node (about which
	 *  rotation took place) are also pointing to the updated child and not the old one
	 * 
	 * @param parent
	 * @param prevChild
	 * @param currChild
	 */
	private void fixChildRelationship(RedBlackTreeNode parent, RedBlackTreeNode prevChild, RedBlackTreeNode currChild) {
		if (parent == null) {
			root = currChild;
		} else if (parent.getLeft().equals(prevChild)) {
			parent.setLeft(currChild);
		} else if (parent.getRight().equals(prevChild)) {
			parent.setRight(currChild);
		}
		currChild.setParent(parent);
	}
	
	/**
	 * @precondition
	 *  1. The tree is constructed and new ndoe added like we do 
	 *     in normal BST
	 * 
	 * @param node
	 * 
	 * @postcondition
	 *  The RB tree is fixed using recoloring and rotations
	 */
	private void rbInsertFixup(RedBlackTreeNode node) {
		RedBlackTreeNode parent = node.getParent();
		if (parent == null) {
			node.setColor(BLACK);
			return;
		}
		if (parent.getColor() == 0) {
			return;
		}
		RedBlackTreeNode grandparent = parent.getParent();
		RedBlackTreeNode uncle = getUncleOf(node);
		if (uncle != null && uncle.getColor() == 1) {
			parent.setColor(BLACK);
			uncle.setColor(BLACK);
			grandparent.setColor(RED);
			rbInsertFixup(grandparent);
			return;
		}
		if (parent.equals(grandparent.getLeft())) {
			if (node.equals(parent.getRight())) {
				leftRotate(parent);
				parent = node;
			}
			rightRotate(grandparent);
			parent.setColor(BLACK);
			grandparent.setColor(RED);
		} else {
			if (node.equals(parent.getRight())) {
				rightRotate(parent);
				parent = node;
			}
			leftRotate(grandparent);
			parent.setColor(BLACK);
			grandparent.setColor(RED);
		}
	}
	
	/**
	 * @precondition
	 *  1. The tree is constructed
	 * 	2. The node is present in the tree
	 * 
	 * @param node
	 * 
	 * @return
	 * @postcondition
	 * 	Returns the uncle (parent's sibling) of the node
	 */
	private RedBlackTreeNode getUncleOf(RedBlackTreeNode node) {
		if (node == null || node.equals(root)) {
			return null;
		}
		RedBlackTreeNode grandParent = node.getParent();
		if (grandParent.getLeft() != null && grandParent.getLeft().equals(node)) {
			return grandParent.getRight();
		}
		return grandParent.getLeft();
	}
	
	/**
	 * @precondition
	 *  1. The tree is constructed
	 *  2. The subject was present in the student info list
	 *     provided while constructing the list
	 *  3. Valid subject name not null or empty
	 * 
	 * @param subject
	 * 
	 * @return
	 * @postcondition
	 * 	Returns the subject index if found, if not founds returns -1
	 */
	public int searchSubject(String subject) {
		RedBlackTreeNode node = searchRec(root, subject);
		return node != null ? node.getIndex(): -1;
	}
	
	/**
	 * 
	 * @note Helper Utility to search by recursion
	 * 
	 * @param node
	 * @param subject
	 * 
	 * @return
	 * @postcondition
	 * 	Search Result (Tree Node) after querying the RB Tree
	 */
	private RedBlackTreeNode searchRec(RedBlackTreeNode node, String subject) {
		if (node == null) {
			return null;
		}
		int comp = subject.compareTo(node.getSubject());
		if (comp > 0) {
			return searchRec(node.getRight(), subject);
		} else if (comp < 0) {
			return searchRec(node.getLeft(), subject);
		}
		return node;
	}
	
	/**
	 * @precondition: The tree has been constructed.
	 * @postcondition: The tree is displayed with an in-order
	 *	traversal
	 */
	public void inOrderPrint() {
		inOrderRec(root);
	}
	
	/**
	 * helper utility to print inorder traversal
	 * used to check if tree insertions are correct
	 * @param node
	 */
	private void inOrderRec(RedBlackTreeNode node) {
		if (node == null) {
			return;
		}
		inOrderRec(node.getLeft());
		System.out.println(node.getSubject() + " " + node.getIndex());
		inOrderRec(node.getRight());
	}
	
	/**
	 * @precondition
	 * the tree has been constructed
	 * 
	 * @return
	 * @postcondition
	 *  Returns the size of the tree (number of subjects)
	 */
	public int size() {
		return size;
	}
}

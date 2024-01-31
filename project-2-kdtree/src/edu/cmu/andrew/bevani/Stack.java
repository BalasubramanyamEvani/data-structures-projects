/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

/**
 * Class used to initialize a stack using
 * singly linked list
 * 
 * class invariants:
 * list - Singly Linked List
 *
 */
public class Stack {
	
	// class invariant
	private SinglyLinkedList list;

	/**
	 * Constructor
	 * Initializes list to an empty list
	 */
	public Stack() {
		list = new SinglyLinkedList();
	}
	
	/**
	 * @precondition
	 * 	1. Entry not null
	 * @param entry
	 * 
	 * @postcondition
	 * 	Entry is pushed to stack (at the front of the list)
	 */
	public void push(Object entry) {
		list.addAtFrontNode(entry);
	}
	
	/**
	 * @precondition
	 * 	1. Stack is not empty
	 * @return
	 * @postcondition
	 * 	First entry of list is removed (LIFO)
	 */
	public Object pop() {
		SQNode res = list.getHead();
		list.removeFirst();
		return res.getEntry();
	}
	
	/**
	 * @precondition
	 * 	1. Stack not empty
	 * @return
	 * @postcondition
	 * 	Returns the top of the stack but doesn't pop
	 */
	public Object peek() {
		return list.getHead().getEntry();
	}
	
	/**
	 * 
	 * @return
	 * @postcondition
	 * 	Returns true if stack is empty or else false
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/**
	 * 
	 * @return
	 * @postcondition
	 * 	Returns size of stack
	 */
	public int size() {
		return list.countNodes();
	}
}

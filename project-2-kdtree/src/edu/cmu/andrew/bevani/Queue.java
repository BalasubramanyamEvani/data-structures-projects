/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

/**
 * Class used to initialize a queue using
 * singly linked list
 * 
 * class invariants:
 * list - Singly Linked List
 *
 */
public class Queue {
	
	// class invariant
	private SinglyLinkedList list;

	/**
	 * Constructor
	 * Initializes list to an empty list
	 */
	public Queue() {
		list = new SinglyLinkedList();
	}
	
	/**
	 * @precondition
	 * 	1. Entry not null
	 * @param entry
	 * 
	 * @postcondition
	 * 	Entry is enqueued to queue (added at the end of the list)
	 */
	public void enqueue(Object entry) {
		list.addAtEndNode(entry);
	}
	
	/**
	 * @precondition
	 * 	1. Queue is not empty
	 * @return
	 * @postcondition
	 * 	First entry of list is removed (FIFO)
	 */
	public SQNode dequeue() {
		SQNode res = list.getHead();
		list.removeFirst();
		return res;
	}
	
	/**
	 * 
	 * @return
	 * @postcondition
	 * 	Returns true if queue is empty or else false
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/**
	 * 
	 * @return
	 * @postcondition
	 * 	Returns the size of the queue
	 */
	public int size() {
		return list.countNodes();
	}
}

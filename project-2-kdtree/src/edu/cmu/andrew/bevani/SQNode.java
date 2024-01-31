/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

/**
 * Class to store node used by SinglyLinkedList
 * 
 * Class invariants:
 * entry - Object type
 * next - pointer to next node (linked to the current)
 */
public class SQNode {
	
	// class invariants
	private Object entry;
	
	private SQNode next;

	/**
	 * Constructor to initialize SQNode
	 * @param entry
	 */
	public SQNode(Object entry) {
		this.entry = entry;
		this.next = null;
	}

	public Object getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public SQNode getNext() {
		return next;
	}

	public void setNext(SQNode next) {
		this.next = next;
	}
}

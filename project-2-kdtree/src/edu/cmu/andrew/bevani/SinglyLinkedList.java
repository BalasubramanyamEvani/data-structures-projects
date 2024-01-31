/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2 (Similar to HW-1 implementation only changed Node type and
 *  added methods to remove nodes)
 */
package edu.cmu.andrew.bevani;

/*
*
* Class invariants:
* 
* The head pointer -> points to the first node. 
* The tail pointer -> points to the last node. 
* current -> points to the current while using iterator
* size -> tracks the size of the list
*/
public class SinglyLinkedList {
	
	// Invariant of the SinglyLinkedList class:
	private SQNode head;
	
	private SQNode tail;
	
	private SQNode current;
	
	private int size;
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * Constructor
	 * 
	 * Initializes values of current, head, tail and size
	 */
	public SinglyLinkedList() {
		current = head = tail = null;
		size = 0;
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * @postcondition
	 * 	1. Returns head of the list- might be null if empty
	 * 
	 * @return
	 * 	Returns the head of the list
	 */
	public SQNode getHead() {
		return head;
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @precondition
	 * 	1. item should not be null or else
	 * 	   NPE might occur while traversing,
	 * 	   fetching data
	 * 
	 * @postcondition
	 * 	A new node tmp is added to the list at the front
	 * 	and the size of the list is incremented
	 
	 * @param item
	 * 
	 * Adds Entry type item at the front of the list
	 */
	public void addAtFrontNode(Object item) {
		SQNode tmp = new SQNode(item);
		if (isEmpty()) {
			head = tail = tmp;
		} else {
			tmp.setNext(head);
			head = tmp;
		}
		++size;
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * Remove the first item of the list
	 * 
	 * @postcondition the first item of the list is removed
	 * @param 
	 */
	public void removeFirst() {
		if (head != null) {
			head = head.getNext();
			--size;
		}
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @precondition
	 * 	1. item should not be null or else
	 * 	   NPE might occur while traversing,
	 * 	   fetching data
	 * 
	 * @postcondition
	 * 	A new node tmp is added to the list at the end
	 * 	and the size of the list is incremented
	 * 
	 * @param item
	 * 
	 * Adds Entry type item at the end of the list
	 */
	public void addAtEndNode(Object item) {
		SQNode tmp = new SQNode(item);
		if (isEmpty()) {
			head = tail = tmp;
		} else {
			tail.setNext(tmp);
			tail = tmp;
		}
		++size;
	}
	
	/**
	 * Routine Complexity: Θ(n)
	 * 
	 * @return
	 * @postcondition
	 * Returns and removes the last element from the list 
	 */
	public Object removeLast() {
		if (isEmpty()) {
			return null;
		}
		SQNode tmp = head;
		while(tmp.getNext().getNext() != null) {
			tmp = tmp.getNext();
		}
		SQNode res = tmp.getNext();
		tmp.setNext(null);
		--size;
		return res.getEntry();
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @postcondition
	 * 	Returns the size of the list (number of nodes)
	 * 
	 * @return
	 * 	Size of the list
	 */
	public int countNodes() {
		return size;
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * @note
	 * 	Might give null if data is null so the
	 *  caller would need to handle that appropriately
	 *  
	 * @postcondition
	 * 	Return the data of the last node in the list
	 * 
	 * @return
	 * 	Data of the last node in the list
	 */
	public Object getLast() {
		return isEmpty() ? null : tail.getEntry();
	}
	
	/**
	 * Routine Complexity: Θ(n)
	 * 
	 * @param i
	 * 
	 * @precondition
	 * 	1. Make sure the list doesn't have a cycle
	 * 	   or for an invalid length too the data might 
	 * 	   be returned
	 * 
	 * @postcondition
	 * 	Returns the Entry at the ith position (0 based)
	 * 
	 * @return
	 * 	Entry at the ith position (0 based)
	 */
	public Object getEntryAt(int i) {
		if (i < 0 || isEmpty() || i >= countNodes()) {
			throw new IndexOutOfBoundsException(i);
		}
		int ctr = 0;
		SQNode cur = head;
		while (ctr != i) {
			cur = cur.getNext();
			++ctr;
		}
		return cur.getEntry();
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @postcondition
	 * 	Returns true if current itr pointer not equal to null
	 * 
	 * @return
	 * 	true if current is not null or else false
	 */
	public boolean hasNext() {
		return current != null;
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @postcondition
	 * 	Returns Entry data of the current itr pointer
	 * 	if hasNext() method true else null
	 * 
	 * @return
	 * 	Data of the next node while iterating
	 */
	public Object next() {
		if (hasNext()) {
			Object data = current.getEntry();
			current = current.getNext();
			return data;
		}
		return null;
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @postcondition
	 * 	Sets current itr pointer back to head
	 */
	public void reset() {
		current = head;
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @postcondition
	 * 	Returns true if empty (size is 0)
	 * 
	 * @return
	 * 	true if list is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public String toString() {
		SQNode cur = head;
		StringBuilder sb = new StringBuilder();
		
		while (cur != null) {
			sb.append(cur.getEntry());
			cur = cur.getNext();
		}
		return sb.toString();
	}
}

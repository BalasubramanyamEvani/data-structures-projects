/**
 * Previous Homework Reused
 * Changes done for HW 5:
 * Added support of generic type
 * Added remove method to remove from head
 * 
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 1, part - 1
 */
package edu.cmu.andrew.bevani.prevhw;

/*
 *
 * Class invariants:
 * 
 * The head pointer -> points to the first node. 
 * The tail pointer -> points to the last node. 
 * current -> points to the current while using iterator
 * size -> tracks the size of the list
 */
public class SinglyLinkedList<T> {
	
	// Invariant of the SinglyLinkedList class:
	private ObjectNode head;
	
	private ObjectNode tail;
	
	private ObjectNode current;
	
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
	 * Adds Object type item at the front of the list
	 */
	public void addAtFrontNode(T item) {
		ObjectNode tmp = new ObjectNode(item, null);
		if (isEmpty()) {
			head = tail = tmp;
		} else {
			tmp.setLink(head);
			head = tmp;
		}
		++size;
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
	 * Adds Object type item at the end of the list
	 */
	public void addAtEndNode(T item) {
		ObjectNode tmp = new ObjectNode(item, null);
		if (isEmpty()) {
			head = tail = tmp;
		} else {
			tail.setLink(tmp);
			tail = tmp;
		}
		++size;
	}
	
	/**
	 * 
	 * This method would delete the node at the head and
	 * return it. 
	 * 
	 * @pre
	 * 	The list is not empty and the data
	 *  is not null
	 * 
	 * @return
	 * 
	 * Returns the node at the head
	 */
	@SuppressWarnings("unchecked")
	public T removeFirst() {
		if (head == null) {
			return null;
		}
		T res = (T) head.getData();
		head = head.getLink();
		if (head == null) {
			tail = null;
		}
		--size;
		return res;
	}
	
	/**
	 * Routine Complexity: Θ(n)
	 * 
	 * @return
	 * @postcondition
	 * Returns and removes the last element from the list 
	 */
	@SuppressWarnings("unchecked")
	public T removeLast() {
		if (isEmpty()) {
			return null;
		}
		ObjectNode tmp = head;
		if (size == 1) {
			head = tail = null;
			--size;
			return (T) tmp;
		}
		while(tmp.getLink().getLink() != null) {
			tmp = tmp.getLink();
		}
		ObjectNode res = tmp.getLink();
		tail = tmp;
		tmp.setLink(null);
		--size;
		return (T) res.getData();
	}
	
	/**
	 * @pre
	 * the current list is not null
	 * 
	 * @return
	 * Returns a copy of the list
	 */
	public SinglyLinkedList<T> copy() {
		if (head == null) {
			return null;
		}
		SinglyLinkedList<T> newList = new SinglyLinkedList<>();
		this.reset();
		while (this.hasNext()) {
			newList.addAtEndNode(this.next());
		}
		return newList;
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
	@SuppressWarnings("unchecked")
	public Object getLast() {
		return isEmpty() ? null : (T) tail.getData();
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
	 * 	Returns the Object at the ith position (0 based)
	 * 
	 * @return
	 * 	Object at the ith position (0 based)
	 */
	@SuppressWarnings("unchecked")
	public T getObjectAt(int i) {
		if (i < 0 || isEmpty() || i >= countNodes()) {
			throw new IndexOutOfBoundsException(i);
		}
		int ctr = 0;
		ObjectNode cur = head;
		while (ctr != i) {
			cur = cur.getLink();
			++ctr;
		}
		return (T) cur.getData();
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
	 * 	Returns Object data of the current itr pointer
	 * 	if hasNext() method true else null
	 * 
	 * @return
	 * 	Data of the next node while iterating
	 */
	@SuppressWarnings("unchecked")
	public T next() {
		if (hasNext()) {
			Object data = current.getData();
			current = current.getLink();
			return (T) data;
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
	private boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public String toString() {
		ObjectNode cur = head;
		StringBuilder sb = new StringBuilder();
		
		while (cur != null) {
			sb.append(cur.getData() + " ");
			cur = cur.getLink();
		}
		
		return sb.toString();
	}
}

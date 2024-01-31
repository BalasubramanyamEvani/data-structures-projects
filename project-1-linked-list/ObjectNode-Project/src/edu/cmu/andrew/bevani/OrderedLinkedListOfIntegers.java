/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 1, part - 1
 */
package edu.cmu.andrew.bevani;

import edu.colorado.nodes.ObjectNode;

/*
*
* Class invariants:
* 
* The head pointer -> points to the first node. 
* The tail pointer -> points to the last node. 
* current -> points to the current while using iterator
* size -> tracks the size of the list
*/
public class OrderedLinkedListOfIntegers {
	
	// Invariant of the OrderedLinkedListOfIntegers class:
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
	public OrderedLinkedListOfIntegers() {
		current = head = tail = null;
		size = 0;
	}

	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @precondition
	 * 	1. item should be >= the current last element in the list or
	 *     the list should be empty or else the list will not be in
	 *     sorted order
	 *  2. item should not be null or else
	 * 	   NPE might occur while traversing,
	 * 	   fetching data
	 * 
	 * @postcondition
	 * 	A new node tmp is added to the list at the end
	 * 	and the size of the list is incremented likewise
	 * 
	 * @param item
	 * 
	 * Adds int type item at the end of the list
	 */
	public void addAtEndNode(int item) {
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
	 * Routine Complexity: Θ(n)
	 * 
	 * @param item
	 * 
	 * Adds int type item to the list in a sorted (increasing) fashion
	 */
	public void sortedAdd(int item) {
		ObjectNode tmp = new ObjectNode(item, null);
		if (isEmpty()) {
			head = tail = tmp;
		} else {
			ObjectNode curr = head;
			ObjectNode prev = null;
			
			while (curr != null && item > (int) curr.getData()) {
				prev = curr;
				curr = curr.getLink();
			}
			
			if (curr == head) {
				tmp.setLink(head);
				head = tmp;
			} else if (curr == null) {
				tail.setLink(tmp);
				tail = tmp;
			} else {
				ObjectNode prev_next = prev.getLink();
				prev.setLink(tmp);
				tmp.setLink(prev_next);
			}
		}
		++size;
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
	 * 
	 * @postcondition
	 * 	Return the data of the last node in the list
	 * 
	 * @return
	 * 	Data of the last node in the list
	 */
	public Object getLast() {
		return isEmpty() ? null : tail.getData();
	}
	
	/**
	 * Routine Complexity: Θ(n)
	 * 
	 * @param i
	 * 
	 * @postcondition
	 * 	Returns the int at the ith position (0 based)
	 * 
	 * @return
	 * 	List's int data at the ith position (0 based)
	 */
	public int getIntAt(int i) {
		if (i < 0 || isEmpty() || i >= countNodes()) {
			throw new IndexOutOfBoundsException(i);
		}
		int ctr = 0;
		ObjectNode cur = head;
		while (ctr != i) {
			cur = cur.getLink();
			++ctr;
		}
		return (int) cur.getData();
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @postcondition
	 * 	Returns true if current itr pointer not equal to null
	 * 
	 * @return
	 * true if current is not null
	 */
	public boolean hasNext() {
		return current != null;
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @postcondition
	 * 	Returns Object data (in this case would be int) of the current itr pointer
	 * 	if hasNext() method true else null
	 * 
	 * @return
	 */
	public Object next() {
		if (hasNext()) {
			Object data = current.getData();
			current = current.getLink();
			return data;
		}
		return null;
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @postcondition
	 * 	Sets current itr pointer back to head
	 * 
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
	 */
	private boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Routine Complexity: Θ(m + n)
	 * where m, n are lengths of lists src1, src2 respectively
	 * 
	 * @precondition
	 * 	1. Both lists should not have loops
	 * 
	 * @param src1
	 * @param src2
	 * @return
	 * 	A list which a list of merging lists src1 and src2 in a sorted manner
	 */
	public static OrderedLinkedListOfIntegers merge(OrderedLinkedListOfIntegers src1, OrderedLinkedListOfIntegers src2) {
		ObjectNode ptr1 = src1.head;
		ObjectNode ptr2 = src2.head;
		
		OrderedLinkedListOfIntegers res = new OrderedLinkedListOfIntegers();
		
		while (ptr1 != null && ptr2 != null) {
			int ptr1Data = (int) ptr1.getData();
			int ptr2Data = (int) ptr2.getData();
			if (ptr1Data < ptr2Data) {
				res.addAtEndNode(ptr1Data);
				ptr1 = ptr1.getLink();
			} else {
				res.addAtEndNode(ptr2Data);
				ptr2 = ptr2.getLink();
			}
		}
		
		ObjectNode ptr = ptr1 == null? ptr2: ptr1;
		while (ptr != null) {
			res.addAtEndNode((int) ptr.getData());
			ptr = ptr.getLink();
		}
		
		return res;
	}
	
	@Override
	public String toString() {
		ObjectNode cur = head;
		StringBuilder sb = new StringBuilder();
		
		while (cur != null) {
			sb.append(cur.getData()).append(" ");
			cur = cur.getLink();
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		OrderedLinkedListOfIntegers list = new OrderedLinkedListOfIntegers();
		list.sortedAdd(1);
		list.sortedAdd(10);
		list.sortedAdd(5);
		list.sortedAdd(20);
		list.sortedAdd(-1);
		list.sortedAdd(0);
		list.sortedAdd(6);
		
		System.out.println("List Contents: " + list.toString());
		System.out.println("List Size: " + list.countNodes());
		System.out.println("Last Element: " + list.getLast());
		System.out.println("Element at '0' pos: " + list.getIntAt(0));
		System.out.println("Element at '1' pos: " + list.getIntAt(1));
		
		int notValidIndex = 7;
		try {
			list.getIntAt(notValidIndex);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("NPE, Element not found at pos: " + notValidIndex);
		}

		list.reset();
		while(list.hasNext()) {
			System.out.println("Itr Next: " + list.next());
		}
		System.out.println("Reached end of iterator, no more values left: " + list.next());
		System.out.println("Resetting iterator");
		list.reset();
		while(list.hasNext()) {
			System.out.println("Itr Next: " + list.next());
		}
		
		OrderedLinkedListOfIntegers oli1 = new OrderedLinkedListOfIntegers();
		OrderedLinkedListOfIntegers oli2 = new OrderedLinkedListOfIntegers();
		
		for(int i = 0; i < 20; ++i) {
			oli1.sortedAdd(1 + (int) (Math.random() * 9)); // Range = [1, 10]
		}

		for(int i = 0; i < 20; ++i) {
			oli2.sortedAdd(2 + (int) (Math.random() * 19)); // Range = [2, 21]
		}
		
		System.out.println("-----------------");
		
		System.out.println("Ordered List Contents oli1: " + oli1.toString());
		System.out.println("Size of List 1: " + oli1.countNodes());
		System.out.println("Ordered List Contents oli2: " + oli2.toString());
		System.out.println("Size of List 2: " + oli2.countNodes());
		
		OrderedLinkedListOfIntegers mergedList = merge(oli1, oli2);
		System.out.println("Merged List: " + mergedList.toString());
		System.out.println("Size of Merged List: " + mergedList.countNodes());
	}
}

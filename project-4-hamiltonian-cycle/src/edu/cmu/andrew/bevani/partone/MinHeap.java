/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 1
*/
package edu.cmu.andrew.bevani.partone;

/*
*
* Class invariants:
* 
* size -> the current size of the min heap
* nodes -> an array of max number of HeapNodes it can store
* 
*/
public class MinHeap {
	
	// Class Invariants
	private int size;
	
	private HeapNode[] nodes;
	
	// Parameterized Constructor
	public MinHeap(int numVertices) {
		nodes = new HeapNode[numVertices];
		size = 0; // Initially size is 0
	}
	
	/**
	 * This function inserts a node to min heap
	 * initially the node is insert at the end
	 * 
	 * Afterward, the heapifyUp method is called
	 * from the current index. The heapifyUp method
	 * is a recursive function which compares the 
	 * current inserted node with the parent node
	 * to maintain the correctness of the min heap
	 * structure
	 * 
	 * Finally once heapifyUp is finished the current size is 
	 * incremented
	 * 
	 * @param node
	 * The node to be inserted
	 */
	public void insert(HeapNode node) {
		if (size == nodes.length) {
			throw new RuntimeException("No more insertions possible in heap");
		}
		nodes[size] = node;
		heapifyUp(size);
		++size;
	}
	
	/**
	 * This method updates the value of node at the 
	 * passed in index and then calls the heapifyUp method
	 * which recursively compares the node with it's parent's 
	 * to maintain the integrity of the min heap
	 * 
	 * @param index
	 * The index of the node whose value must be updated
	 * 
	 * @param val
	 * The new value of the node
	 */
	public void decreaseKey(int index, double val) {
		nodes[index].setVal(val);
		heapifyUp(index);
	}
	
	/**
	 * This methods extracts the minimum which is always
	 * at the root i.e first element of the array of heap nodes.
	 * Once extracted the last element takes it's place.
	 * Subsequently the heapifyDown method is called from the 
	 * root to maintain the integrity of the min heap
	 * 
	 * @return
	 * Returns the minimum HeapNode compared on the basis of
	 * val of HeapNode
	 */
	public HeapNode extractMin() {
		if (isEmpty()) {
			throw new RuntimeException("Heap is Empty");
		}
		HeapNode min = nodes[0];
		nodes[0] = nodes[size - 1];
		nodes[size - 1] = null;
		--size;
		heapifyDown(0);
		return min;
	}
	
	/**
	 * To calculate the index of the left child
	 * 
	 * @pre
	 * Valid index -> index >= 0 and index < size
	 * 
	 * @param index
	 * 
	 * @return
	 * Index of left child from the node's index
	 */
	private int leftChildPos(int index) {
		return (2 * index) + 1;
	}
	
	/**
	 * To calculate the index of the right child
	 * 
	 * @pre
	 * Valid index -> index >= 0 and index < size
	 * 
	 * @param index
	 * 
	 * @return
	 * Index of right child from the node's index
	 */
	private int rightChildPos(int index) {
		return (2 * index) + 2;
	}
	
	/**
	 * To calculate the index of the parent
	 * 
	 * @pre
	 * Valid index -> index >= 0 and index < size
	 * 
	 * @param index
	 * 
	 * @return
	 * parent from the node's index
	 */
	private int parentPos(int index) {
		return index / 2;
	}
	
	/**
	 * Swaps two nodes given position of two node's
	 * position
	 * 
	 * @pre
	 * Valid index -> index >= 0 and index < size
	 * 
	 * @param pos1
	 * @param pos2
	 */
	private void swap(int pos1, int pos2) {
		HeapNode tmp = nodes[pos1];
		nodes[pos1] = nodes[pos2];
		nodes[pos2] = tmp;
	}
	
	/**
	 * The heapifyUp method compares child with parent starting
	 * from the passed in index. Swap happens if the parent
	 * is greater than child
	 * this process is continued until it reaches the root or
	 * the parent is less than the child
	 * 
	 * @pre
	 * Valid index -> index >= 0 and index < size
	 * 
	 * @param index
	 * The start index from where this routine needs to be called
	 */
	private void heapifyUp(int index) {
		int parent = parentPos(index);
		int currIndex = index;
		while (currIndex > 0 && (nodes[parent].getVal() > nodes[currIndex].getVal())) {
			swap(parent, currIndex);
			currIndex = parent;
			parent = parentPos(currIndex);
		}
	}
	
	/**
	 * The heapifyDown method compares the parent with left
	 * and right child to check if any of its children can it's
	 * place. This is called recursively
	 * 
	 * @pre
	 * Valid index -> index >= 0 and index < size
	 * 
	 * @param index
	 * The start index from this routine needs to be called
	 */
	private void heapifyDown(int index) {
		int currSmallest = index;
		int leftChildPos = leftChildPos(index);
		int rightChildPos = rightChildPos(index);
		
		if (leftChildPos < size && nodes[leftChildPos].getVal() < nodes[currSmallest].getVal()) {
			currSmallest = leftChildPos;
		}
		
		if (rightChildPos < size && nodes[rightChildPos].getVal() < nodes[currSmallest].getVal()) {
			currSmallest = rightChildPos;
		}
		
		if (currSmallest != index) {
			swap(index, currSmallest);
			heapifyDown(currSmallest);
		}
	}
	
	/**
	 * @pre
	 * Valid index -> index >= 0 and index < size
	 * 
	 * @param index
	 * 
	 * @return
	 * Returns the node at given index
	 * 
	 */
	public HeapNode get(int index) {
		return nodes[index];
	}
	
	/**
	 * 
	 * @return
	 * Returns the size of the min heap
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * 
	 * @return
	 * Returns true if the min heap is not empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
}

/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 5
*/
package edu.cmu.andrew.bevani;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* This class is used to initialize a single chain in the HashMap,
* the Map currently used is based on Separate Chaining.
* 
* The idea is to make each key of the Map to point to a linked
* list of records that have the same hash function value
*
* Class invariants:
* 
* list -> The linked list of records with same hash function
* 
*/
public class Chain<K, V> {
	
	// Class Invariants
	private SinglyLinkedList<MyEntry<K, V>> list;
	
	/**
	 * Constructor to initialize the chain
	 */
	public Chain() {
		list = new SinglyLinkedList<>();
	}
	
	/**
	 * This method would add the passed in entry to the
	 * map
	 * 
	 * @pre
	 * The entry value is valid, does not contain NULL or else
	 * irregularities may occur during runtime
	 * 
	 * @param entry
	 * This is of type MyEntry
	 */
	public void addToChain(MyEntry<K, V> entry) {
		list.addAtEndNode(entry);
	}
	
	/**
	 * This method would return the chain, list of records
	 * with same hash function value
	 * 
	 * @return
	 * A singly linked list of MyEntry
	 */
	public SinglyLinkedList<MyEntry<K, V>> getList() {
		return list;
	}
}

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
public class MyMap<K, V> {
	
	// Class Invariants
	private int MAX_CAPACITY = 1 << 8;
	
	// Mao with MAX_CAPACITY is built -> 2^8 entries
	@SuppressWarnings("unchecked")
	private Chain<K, V>[] chains = new Chain[MAX_CAPACITY];
	
	// curr size of the Map (number of entries)
	private int size;
	
	/**
	 * Non-parameterized constructor to initialize size
	 */
	public MyMap() {
		size = 0;
	}
	
	/**
	 * This method puts a key value pair to the Map
	 * It first checks whether the key is already present
	 * if it is then the value pertaining to the key is updated
	 * 
	 * Else, a new hash is created based on the key which would be
	 * used as the index to store in the chains
	 * 
	 * @param key
	 * @param value
	 * 
	 * Key Value pair to store in the Map
	 * 
	 */
	public void put(K key, V value) {
		if (containsKey(key)) {
			MyEntry<K, V> entry = getEntry(key);
			entry.setValue(value);
		} else {
			int hash = getHash(key);
			if (chains[hash] == null) {
				 chains[hash] = new Chain<>();
			}
			chains[hash].addToChain(new MyEntry<>(key, value));
			++size;
		}
	}
	
	/**
	 * This method would get the value  pertaining to the specific
	 * key passed as an argument to it.
	 * 
	 * @param key
	 * The key whose value is requested
	 * 
	 * @return
	 * NULL if not found in the map
	 * else valid value is returned
	 */
	public V getValue(K key) {
		if (containsKey(key)) {
			return getEntry(key).getValue();
		}
		return null;
	}
	
	/**
	 * This method would get the whole entry, both key and value pair
	 * The search is based on the passed key value.
	 * 
	 * Based on the passed key, the index of the chain is calculated
	 * and then it's respective list is scanned to find the entry
	 * whose key value matches the passed key value
	 * 
	 * @param key
	 * Entry returned on the basis of the passed in key value
	 * 
	 * @return
	 * The value pertaining to the key
	 * 
	 */
	public MyEntry<K, V> getEntry(K key) {
		int hash = getHash(key);
		SinglyLinkedList<MyEntry<K, V>> entries = chains[hash].getList();
		entries.reset();
		while (entries.hasNext()) {
			MyEntry<K, V> curr = entries.next();
			if (curr.getkey().equals(key)) {
				return curr;
			}
		}
		return null;
	}
	
	/**
	 * This method checks if the passed in key 
	 * corresponds to a valid index in the chain
	 * 
	 * If it is, it then checks if the key is present 
	 * in the respective chain.
	 * 
	 * If present true is returned else false
	 * 
	 * @param key
	 * The key value which needs to be checked
	 * if its present in the Map
	 * 
	 * @return
	 * true -> if present, false -> otherwise
	 */
	public boolean containsKey(K key) {
		int hash = getHash(key);
		if (chains[hash] == null || getEntry(key) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * The current size of the Map
	 * 
	 * @return
	 * Returns the number of entries inserted in the Map
	 */
	public int size() {
		return size;
	}
	
	/**
	 * This is used to generate the hashcode
	 * hashcode is generated using the builtin method
	 * of an object and then a modulus is taken
	 * so that a valid index of the chain can be
	 * assigned
	 * 
	 * @param obj
	 * Key value
	 * 
	 * @return
	 * index of the chain
	 */
	private int getHash(K obj) {
		return (obj.hashCode() & (MAX_CAPACITY - 1));
	}
}

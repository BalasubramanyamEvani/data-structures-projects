/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 5
*/
package edu.cmu.andrew.bevani;

import java.util.Objects;

/*
* This class is used to initialize an entry pertaining to the MyMap class
* Generics are used for flexible data type assignment
* 
* Class invariants:
* 
* key -> The Map entry's key value of type K
* value -> The Map entry's value w.r.t to the Key of type V
* 
*/
public class MyEntry<K, V> {
	
	// Class Invariants
	private K key;
	
	private V value;

	/**
	 * Parameterized Constructor
	 * to initialize key and value pair
	 * 
	 * @param key
	 * @param value
	 * Both have generic data type
	 * 
	 */
	public MyEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * This method returns the key value
	 * 
	 * @return
	 * Key value
	 */
	public K getkey() { 
		return key;
	}
	
	/**
	 * This method sets the key value
	 * 
	 * @param key
	 */
	public void setkey(K key) {
		this.key = key;
	}
	
	/**
	 * This method gets the value variable
	 * 
	 * @return
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * This method sets the value variable
	 * 
	 * @param value
	 */
	public void setValue(V value) {
		this.value = value;
	}
	
	/**
	 * The below is required to generate hashcode and
	 * for comparison purposes.
	 * 
	 * Although deleting entries from the Map is out
	 * of scope for this HW, the equals would help us
	 * to delete an entry from the Map
	 * 
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		MyEntry<K, V> other = (MyEntry<K, V>) obj;
		return Objects.equals(key, other.key) && Objects.equals(value, other.value);
	}
}

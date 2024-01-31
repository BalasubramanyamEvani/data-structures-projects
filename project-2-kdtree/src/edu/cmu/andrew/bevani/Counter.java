/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

/*
* This class is used by TwoDTree.java's method
* range search and nearest neighbor 
* to store counts (traversed nodes while searching)
* 
* Class invariants:
* 
* count -> To Store count
* 
*/
public class Counter {
	
	private int count;
	
	/**
	 * Constructor to initialize count
	 */
	public Counter() {
		count = 0;
	}
	
	/**
	 * Increments the count
	 */
	public void increment() {
		count += 1;
	}
	
	/**
	 * 
	 * @return
	 * Returns the stored count
	 */
	public int getCount() {
		return count;
	}
}

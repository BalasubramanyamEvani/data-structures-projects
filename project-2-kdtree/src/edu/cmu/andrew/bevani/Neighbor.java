/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

/**
 * Class to initialize an object to store
 * nearest neighbor result
 *
 * Class Invariants:
 * 
 * distance - nearest neighbot distance
 * ref - nearest neighbor
 */
public class Neighbor {
	
	// class invariants
	private double distance;
	
	private TreeNode ref;

	/**
	 * Constructor to initialize a neighbor
	 * 
	 * @param distance
	 * @param ref
	 */
	public Neighbor(double distance, TreeNode ref) {
		this.distance = distance;
		this.ref = ref;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public TreeNode getRef() {
		return ref;
	}

	public void setRef(TreeNode ref) {
		this.ref = ref;
	}
}

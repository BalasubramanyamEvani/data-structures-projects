/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 1, part - 2
 */
package edu.cmu.andrew.bevani;

public class PublicKey {
	
	// Invariant of the PublicKey class:
	private SinglyLinkedList b;

	public PublicKey(SinglyLinkedList b) {
		this.b = b;
	}

	public SinglyLinkedList getB() {
		return b;
	}

	@Override
	public String toString() {
		return "PublicKey [b=" + b + "]";
	}
}

/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 1, part - 2
 */
package edu.cmu.andrew.bevani;

import java.math.BigInteger;

public class PrivateKey {
	
	// Invariant of the PrivateKey class:
	private SinglyLinkedList w;
	
	private BigInteger q;
	
	private BigInteger r;

	public PrivateKey(SinglyLinkedList w, BigInteger q, BigInteger r) {
		this.w = w;
		this.q = q;
		this.r = r;
	}

	public SinglyLinkedList getW() {
		return w;
	}

	public BigInteger getQ() {
		return q;
	}

	public BigInteger getR() {
		return r;
	}
}

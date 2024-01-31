/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 1
*/
package edu.cmu.andrew.bevani.partone;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* This class holds the results of Part One
*
* Class invariants:
* 
* hamiltonianCycle -> The list which will contain the approx tour
* totalDistance -> the total distance of the tour
* 
*/
public class Result {
	
	// Class Invariants
	private SinglyLinkedList hamiltonianCycle;
	
	private double totalDistance;
	
	// Parameterized Constructor
	public Result(SinglyLinkedList hamiltonianCycle, double totalDistance) {
		this.hamiltonianCycle = hamiltonianCycle;
		this.totalDistance = totalDistance;
	}

	public SinglyLinkedList getHamiltonianCycle() {
		return hamiltonianCycle;
	}

	public void setHamiltonianCycle(SinglyLinkedList hamiltonianCycle) {
		this.hamiltonianCycle = hamiltonianCycle;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}
}

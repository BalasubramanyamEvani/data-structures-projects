/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 2
*/
package edu.cmu.andrew.bevani.parttwo;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* This class holds the results of Part Two
*
* Class invariants:
* 
* tour -> Singly Linked List store the optimal tour
* distance -> The total distance of the tour
*/
public class ResultOpt {
	
	// Class Invariants
	private SinglyLinkedList tour;
	
	private double distance;

	// Parameterized Constructor
	public ResultOpt(SinglyLinkedList tour, double distance) {
		this.tour = tour;
		this.distance = distance;
	}

	public SinglyLinkedList getTour() {
		return tour;
	}

	public void setTour(SinglyLinkedList tour) {
		this.tour = tour;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}

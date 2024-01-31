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
* vertex -> vertex number of a graph
* val -> the distance [weight] associated with this vertex
* 
* This is used by MinHeap class to build a heap
* 
*/
public class HeapNode {
	
	// Class Invariants
	private int vertex;
	
	private double val;

	// Parameterized Constructor
	public HeapNode(int vertex, double val) {
		this.vertex = vertex;
		this.val = val;
	}

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}
}

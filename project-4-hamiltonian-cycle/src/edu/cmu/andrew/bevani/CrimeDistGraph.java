/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4
*/
package edu.cmu.andrew.bevani;

import edu.cmu.andrew.bevani.partone.CrimeEntry;
import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* This class is used to create 2D matrix Graph representation
* where each element stores the distance from src to dst node
*
* Class invariants:
* 
* adjacencyMatrix -> The adjacency matrix which would be generated
* after the graph is built
* 
*/
public class CrimeDistGraph {
	
	// Class Invariants
	private double adjacencyMatrix[][];
	
	// Parameterized Constructor
	// Calls the buildGraph method which builds the graph using the 
	// passed in list of crimes
	public CrimeDistGraph(SinglyLinkedList crimes) {
		buildGraph(crimes);
	}
	
	/**
	 * 
	 * @return
	 * 
	 * Returns the adjacency matrix representation
	 */
	public double[][] getAdjMat() {
		return adjacencyMatrix;
	}
	
	/**
	 * This method builds the graph by calculating distance
	 * between two nodes. The graph is 2D matrix of dimensions
	 * #crimes x #crimes
	 * 
	 * @param crimes
	 * Singly linked list of crimes
	 */
	private void buildGraph(SinglyLinkedList crimes) {
		int numberOfVertices = crimes.countNodes();
		adjacencyMatrix = new double[numberOfVertices][numberOfVertices];
		for (int i = 0; i < numberOfVertices; ++i) {
			CrimeEntry src = (CrimeEntry)crimes.getObjectAt(i);
			for (int j = 0; j < numberOfVertices; ++j) {
				CrimeEntry dst = (CrimeEntry)crimes.getObjectAt(j);
				double distance = calcDistance(src.getxCordinate(), src.getyCordinate(), 
						dst.getxCordinate(), dst.getyCordinate());
				adjacencyMatrix[i][j] = distance;
			}
		}
	}
	
	/**
	 * This method calculates the euclidean distance between two points
	 * (x1, y1) and (x2, y2) 
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * 
	 * @return
	 * Returns the euclidean distance
	 */
	private double calcDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
}

/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 2
*/
package edu.cmu.andrew.bevani.parttwo;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
*
* Class invariants:
* 
* adjacencyMatrix -> The 2D matrix representation of the crime records graph
* tourDist -> the total distance of the optimal tour, initially infinte [MAX double value]
* 
*/
public class OptimumTour {
	
	// Class Invariants
	private double[][] adjacencyMatrix;
	
	private double tourDist = Double.MAX_VALUE;
	
	// Parameterized Constructor
	public OptimumTour(double[][] adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	/**
	 * This method calls the backtrack method to perform
	 * an exhaustive search to find the optimal tour
	 * 
	 * Once found it adds the first node to the end to complete
	 * the tour
	 * 
	 * @return
	 * ResultOpt instance which contains the result of the exhaustive search,
	 * the optimal tour and distance
	 */
	public ResultOpt build() {
		int[] vertices = new int[adjacencyMatrix.length];
		for (int i = 0; i < vertices.length; ++i) {
			vertices[i] = i;
		}
		boolean[] used = new boolean[adjacencyMatrix.length];
		ResultOpt res = new ResultOpt(null, 0);
		backtrack(res, vertices, new SinglyLinkedList(), used);
		res.getTour().addAtEndNode(res.getTour().getObjectAt(0)); // to complete the tour
		return res;
	}
	
	/**
	 * @note all permutations are considered which has a starting index from
	 * vertex 0 as mentioned in this piazza post
	 * https://piazza.com/class/kyn5hq66yai3m7?cid=104
	 * 
	 * This method is used to generate all permutations of the passed in array
	 * of vertices and then check the total distance for each. [with starting vertex 0]
	 * 
	 * The best result res is updated when a permutation of vertices is better than the current
	 * result at that point of recursion
	 * 
	 * @param res Stores the best result
	 * Optimal tour list and distance
	 * 
	 * @param vertices
	 * An array of vertices of the graph whose permutations will be generated and
	 * investigated
	 * 
	 * @param permutation
	 * A list which stores the permutation result as it's built using 
	 * backtracking
	 * 
	 * @param used
	 * A boolean array of marked vertices at each level of recursion
	 * this is helpful for backtracking purposes. It helps us decide which
	 * vertex needs to be inserted in our permutation list
	 * 
	 */
	private void backtrack(ResultOpt res, int[] vertices, SinglyLinkedList permutation, boolean[] used) {
		if (permutation.countNodes() == vertices.length) {
			
			if((int)permutation.getObjectAt(0) != 0) {
				return;	
			}
			
			double dist = calcTourDistance(permutation);
			if (dist < tourDist) {
				res.setDistance(dist);
				res.setTour(permutation.copy());
				tourDist = dist;
			}
			return;
		}
		
		for (int i = 0; i < vertices.length; ++i) {
			if (!used[i]) {
				used[i] = true;
				permutation.addAtEndNode(vertices[i]);
				backtrack(res, vertices, permutation, used);
				used[i] = false;
				permutation.removeLast();
			}
		}
	}
	
	/**
	 * This methods calculates the total distance
	 * traveled in the optimal tour
	 * 
	 * @param tour
	 * List representation of the optimal tour
	 * 
	 * @return
	 * The total distance covered
	 */
	private double calcTourDistance(SinglyLinkedList tour) {
		int[] tourArr = covertToArray(tour);
		double res = 0;
		for (int i = 1; i < tourArr.length; ++i) {
			res += adjacencyMatrix[tourArr[i - 1]][tourArr[i]];
		}
		res += adjacencyMatrix[tourArr[tourArr.length - 1]][tourArr[0]]; // to complete the tour
		return res * 0.00018939;
	}
	
	/**
	 * 
	 * this method converts a list to an array
	 * representation. 
	 * 
	 * This is done for easier access of data values
	 * 
	 * @param tour
	 * The tour which is stored as a singly linked list
	 * 
	 * @return
	 * An array representation of the tour
	 */
	private int[] covertToArray(SinglyLinkedList tour) {
		int[] res = new int[tour.countNodes()];
		
		int i = 0;
		tour.reset();
		while (tour.hasNext()) {
			res[i] = (int) tour.next();
			++i;
		}
		
		return res;
	}
}

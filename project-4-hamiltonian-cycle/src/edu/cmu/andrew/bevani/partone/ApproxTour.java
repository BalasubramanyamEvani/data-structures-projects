/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 1
*/
package edu.cmu.andrew.bevani.partone;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
*
* Class invariants:
* 
* adjacencyMatrix -> 2D matrix representation of graph of crimes 
* tour -> The resultant approximate tour. [Hamiltonian cycle]
* 
*/
public class ApproxTour {
	
	// Class Invariants
	private double adjacencyMatrix[][];
	
	private SinglyLinkedList tour;
	
	/**
	 * Constructor
	 * 
	 * Initializes the tour list -> initially empty
	 * Stores reference to the 2D matrix [Graph]
	 * 
	 * @param graph
	 */
	public ApproxTour(double[][] graph) {
		this.adjacencyMatrix = graph;
		tour = new SinglyLinkedList();
	}
	
	/**
	 * Build routine to the perform prim routine
	 * 
	 * This method calls the prim routine which
	 * builds the MST and performs the preOrder walk
	 * 
	 * @return
	 * 
	 * This method returns approx tour with the 
	 * total distance - Result
	 */
	public Result build() {
		primRoutine();
		return new Result(tour, totDistance(tour));
	}
	
	/**
	 * Given a tour this function traverses it and then
	 * calculates the total distance
	 * 
	 * @param tour
	 * Singly linked list which has the tour and needs
	 * to be traversed
	 * 
	 * @pre
	 * expects the adjacency matrix to have distances in
	 * feet and the tour is not empty
	 * 
	 * @return
	 * total distance -> double value [in miles]
	 */
	private double totDistance(SinglyLinkedList tour) {
		double res = 0;
		for (int i = 1; i < tour.countNodes(); ++i) {
			int src = (int) tour.getObjectAt(i - 1);
			int dst = (int) tour.getObjectAt(i);
			res += adjacencyMatrix[src][dst];
		}
		return res * 0.00018939; // convert to miles
	}
	
	/**
	 * This function is where the Prim algorithm is performed
	 * using the Min Heap data structure. 
	 * 
	 * Initially all heap nodes [vertices] except the first one,
	 * which is the starting point are given MAX value (infinity)
	 * and their parents are set to be none (-1)
	 * 
	 * Once the nodes are created, they are inserted to the MinHeap
	 * Until the minheap is not empty
	 *  1. node with min value [u] is extracted from the help
	 *  2. it's adjacent nodes [v] are visited and updated
	 *     if the distance from current node u -> v is less than
	 *     the value of v present in heap
	 *     
	 *     Then decreaseKey operation is called on heap to update the
	 *     vertex value
	 * 
	 * We are maintaining the traversed vertices in parents and in
	 * every iteration when we traverse the min heap we check for
	 * for all nodes currently reachable from the parents, this helps
	 * us to extract the min [greedily]
	 * 
	 * Once the above Prim algorithm is run, then we create a
	 * new Tree Node with root as starting vertex, same thing we did
	 * in Prim. Then we build the tree connecting parents with their
	 * children nodes
	 * 
	 * Once the MST is built then we perform a pre order walk
	 * equivalent to DFS
	 * 
	 * Finally to create a hamiltonian cycle we end the tour
	 * with the start point 0
	 * 
	 */
	private void primRoutine() {
		int vertices = adjacencyMatrix.length;
		
		int[] parents = new int[vertices];
		double[] distances = new double[vertices];
		
		HeapNode[] heapNodes = new HeapNode[vertices];
		for (int i = 0; i < vertices; ++i) {
			heapNodes[i] = new HeapNode(i, Double.MAX_VALUE);
            parents[i] = -1;
            distances[i] = Double.MAX_VALUE;
		}
		
		heapNodes[0].setVal(0.0);
		
		MinHeap minHeap = new MinHeap(vertices);
		for (int i = 0; i < vertices; ++i) {
			minHeap.insert(heapNodes[i]);
		}
		
		while (!minHeap.isEmpty()) {
			HeapNode src = minHeap.extractMin();
			for (int i = 0; i < minHeap.getSize(); ++i) {
				HeapNode dst = minHeap.get(i);
				double edge = adjacencyMatrix[src.getVertex()][dst.getVertex()];
				double heapVal = dst.getVal();
				if (edge < heapVal) {
					minHeap.decreaseKey(i, edge);
					parents[dst.getVertex()] = src.getVertex();
					distances[dst.getVertex()] = edge;
				}
			}
		}
		
		TreeNode root = new TreeNode(0);
		buildTree(root, parents);
		preOrderWalk(root);
		tour.addAtEndNode(0);
	}
	
	/**
	 * This functions builds the tree starting from the root
	 * which will be vertex 0
	 * 
	 * One by one the children of the node is read and then
	 * recursively this routine is called [DFS manner]
	 * 
	 * @param node
	 * The current parent node whose children needs to be
	 * attached
	 * 
	 * @param parents
	 * Parents array generated from Prim Algorithm
	 */
	private void buildTree(TreeNode node, int[] parents) {
		for (int i = 0; i < parents.length; ++i) {
			if (node.getVertex() == parents[i]) {
				TreeNode child = new TreeNode(i);
				node.addChild(child);
			}
		}
		if (node.getChildren().countNodes() != 0) {
			for (int i = 0; i < node.getChildren().countNodes(); ++i) {
				buildTree((TreeNode) node.getChildren().getObjectAt(i), parents);
			}
		}
	}
	
	/**
	 * Given the root of a tree this function will perform
	 * a pre-order walk
	 * 
	 * @param root
	 * The src usually the root of the tree from where the
	 * pre-order walk needs to be performed
	 */
	private void preOrderWalk(TreeNode root) {
		tour.addAtEndNode(root.getVertex());
		for (int i = 0; i < root.getChildren().countNodes(); ++i) {
			preOrderWalk((TreeNode) root.getChildren().getObjectAt(i));
		}
	}
}

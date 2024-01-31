/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Class to initialize a TwoDTree
 * 
 * Class Invariants:
 * root - root of the 2d tree
 * size - size of the 2d tree
 * crimeDataLocation - the csv file with which the tree was built
 */
public class TwoDTree {
	
	// class invariants
	private TreeNode root;
	
	private int size;
	
	// Static and constant
	private static final int k = 2;
	
	private String crimeDataLocation;
	
	/**
	 * Constructor to initialize the 2d Tree
	 * @precondition: 
	 *  The String crimeDataLocation contains the path to
	 *	a file formatted in the exact same way as CrimeLatLonXY.csv
	 *
	 * @postcondition: 
	 * 	The 2d tree is constructed and may be printed or
	 *	queried.
	 * @param crimeDataLocation
	 * 
	 * @throws IOException
	 * if error while reading the file (e.g. incorrect file path)
	 * 
	 */
	public TwoDTree(String crimeDataLocation) {
		root = null;
		size = 0;
		this.crimeDataLocation = crimeDataLocation;
		try {
			buildTree();
		} catch (IOException e) {
			System.out.println("Exception Occurred while building tree: " + e.getMessage());
			root = null;
			size = 0;
			this.crimeDataLocation = null;
		}
	}
	
	/**
	 * Builds the tree using the stored crimeDataLocation
	 * 
	 * @throws IOException
	 */
	private void buildTree() throws IOException {
		Path pathToFile = Paths.get(crimeDataLocation); 
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) { 
			Stream<String> lines = br.lines().skip(1);
			lines.forEachOrdered(line -> {
				String[] attributes = line.split(",");
				Entry entry = new Entry();
				entry.setxCordinate(Double.parseDouble(attributes[0]))
					.setyCordinate(Double.parseDouble(attributes[1]))
					.setTime(Integer.parseInt(attributes[2]))
					.setStreet(attributes[3])
					.setOffense(attributes[4])
					.setDate(attributes[5])
					.setTract(Integer.parseInt(attributes[6]))
					.setLatitude(Double.parseDouble(attributes[7]))
					.setLongitutde(Double.parseDouble(attributes[8]));
				insert(entry);
			});
		}
	}
	
	/**
	 * @precondition
	 * 	1. Entry not null, valid entry similar to row in crimeDataLocation
	 * @postcondition
	 * 	Entry inserted in the 2D tree
	 * @param entry
	 */
	public void insert(Entry entry) {
		root = insertRecursive(root, entry, 0);
		++size;
	}
	
	/**
	 * Recursive function to insert the entry
	 * the axis is changed with respect to depth
	 * 
	 * if depth is even then x axis is compared
	 * if depth is odd then y axis is compared
	 * 
	 * @param node
	 * @param entry
	 * @param depth
	 * @return
	 * @postcondition
	 *  Root of the updated tree after insertion
	 */
	private TreeNode insertRecursive(TreeNode node, Entry entry, int depth) {
		if (node == null) {
			return new TreeNode(entry);
		}
		boolean dim = depth % k == 0;
		if (dim) {
			if (entry.getxCordinate() < node.getEntry().getxCordinate()) {
				node.setLeft(insertRecursive(node.getLeft(), entry, ++depth));
			} else {
				node.setRight(insertRecursive(node.getRight(), entry, ++depth));
			}
		} else {
			if (entry.getyCordinate() < node.getEntry().getyCordinate()) {
				node.setLeft(insertRecursive(node.getLeft(), entry, ++depth));
			} else {
				node.setRight(insertRecursive(node.getRight(), entry, ++depth));
			}
		}
		return node;
	}
	/**
	 * @precondition: The 2d tree has been constructed.
	 * @postcondition: The 2d tree is displayed with a pre-order
	 *  traversal.
	 */
	public void preOrderPrint() {
		preOrderRec(root);
	}
	
	/**
	 * Recursive call to print tree in preOrder format starting from the 
	 * given node
	 * 
	 * @param node
	 */
	private void preOrderRec(TreeNode node) {
		if (node == null) {
			return;
		}
		System.out.println(node.getEntry());
		preOrderRec(node.getLeft());
		preOrderRec(node.getRight());
	}
	
	/**
	 * Runtime Complexity:  Θ(n)
	 * 
	 * @precondition: The 2d tree has been constructed.
	 * @postcondition: The 2d tree is displayed with an in-order
	 *	traversal
	 */
	public void inOrderPrint() {
		inOrderRec(root);
	}
	
	/**
	 * Recursive call to print tree in inOrder format starting from the 
	 * given node
	 * 
	 * @param node
	 */
	private void inOrderRec(TreeNode node) {
		if (node == null) {
			return;
		}
		inOrderRec(node.getLeft());
		System.out.println(node.getEntry());
		inOrderRec(node.getRight());	
	}
	
	/**
	 * @precondition: The 2d tree has been constructed.
	 * @postcondition: The 2d tree is displayed with a post-order
	 *	traversal.
	 */
	public void postOrderPrint() {
		postOrderRec(root);
	}
	
	/**
	 * Recursive call to print tree in preOrder format starting from the 
	 * given node
	 * 
	 * @param node
	 */
	private void postOrderRec(TreeNode node) {
		if (node == null) {
			return;
		}
		postOrderRec(node.getLeft());
		postOrderRec(node.getRight());
		System.out.println(node.getEntry());	
	}
	
	/**
	 * Runtime Complexity:  Θ(n)
	 * 
	 * @precondition: The 2d tree has been constructed.
	 * @postcondition: The 2d tree is displayed with a level-order
	 * traversal.
	 * 
	 */
	public void levelOrderPrint() {
		if (root == null) {
			return;
		}		
		
		Queue queue = new Queue();
		queue.enqueue(root);
		
		while (!queue.isEmpty()) {
			SQNode node = queue.dequeue();
			TreeNode tmpNode = (TreeNode) node.getEntry();
			System.out.println(tmpNode.getEntry());
			if (tmpNode.getLeft() != null) {
				queue.enqueue(tmpNode.getLeft());
			}
			if (tmpNode.getRight() != null) {
				queue.enqueue(tmpNode.getRight());
			}
		}
	}
	
	/**
	 * @precondition: The 2d tree has been constructed.
	 * @postcondition: The 2d tree is displayed with a reverse levelorder traversal.
	 */
	public void reverseLevelOrderPrint() {
		if (root == null) {
			return;
		}
		
		Queue queue = new Queue();
		Stack stack = new Stack();
		queue.enqueue(root);
		
		while (!queue.isEmpty()) {
			TreeNode tmpNode = (TreeNode) queue.dequeue().getEntry();
			stack.push(tmpNode);
			if (tmpNode.getLeft() != null) {
				queue.enqueue(tmpNode.getLeft());
			}
			if (tmpNode.getRight() != null) {
				queue.enqueue(tmpNode.getRight());
			}
		}
		
		while (!stack.isEmpty()) {
			TreeNode tmpNode = (TreeNode) stack.pop();
			System.out.println(tmpNode.getEntry());
		}
	}
	
	/**
	 * 
	 * @precondition: The 2d tree has been constructed
	 * @postcondition: A list of 0 or more crimes is returned. These
	 * crimes occurred within the rectangular range specified by the
	 * four parameters. The pair (x1, y1) is the left bottom of the
	 * rectangle. The pair (x2, y2) is the top right of the rectangle. 
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public ListOfCrimes findPointsInRange(double x1, double y1, double x2, double y2) {
		ListOfCrimes res = new ListOfCrimes();
		Counter counter = new Counter();
		searchForPoints(root, res, new Rectangle(x1, y1, x2, y2), 0, counter);
		System.out.println("Examined " + (counter.getCount() + 1) + " nodes during search"); // plus one for root
		return res;
	}
	
	/**
	 * Recursive range search using depth 
	 * 
	 * @precondition
	 *  Will work if the rectangle is x2 > x1 and y2 > y1
	 *  For cases like a rectangle which is diagonally aligned towards left
	 *  it will not work
	 * 
	 * @note
	 * If depth is even then:
	 * 	if entry x cordinate < rect's x1 - search right of the subtree
	 *  if entry x cordinate > rect's x2 - search left of the subtree
	 *  if x cordinate in range of x1, x2 then:
	 *  	check if entry y range in y1, y2: 
	 *  		add to result (if above true)
	 *  	search left subtree
	 *  	search right sub tree because axis intersects the rectangle
	 *  
	 *  If depth is off then:
	 * 	if entry y cordinate < rect's y1 - search right of the subtree
	 *  if entry y cordinate > rect's y2 - search left of the subtree
	 *  if y cordinate in range of y1, y2 then:
	 *  	check if entry x range in x1, x2: 
	 *  		add to result (if above true)
	 *  	search left subtree
	 *  	search right sub tree because axis intersects the rectangle
	 * 
	 * @param node
	 * @param res
	 * @param rect
	 * @param depth
	 * @param counter
	 */
	private void searchForPoints(TreeNode node, ListOfCrimes res, Rectangle rect, int depth, Counter counter) {
		if (node == null)  {
			return;
		}
		
		counter.increment();
		
		if (depth % k == 0) {
			if (node.getEntry().getxCordinate() > rect.getX2()) {
				searchForPoints(node.getLeft(), res, rect, ++depth, counter);
			}
			else if (node.getEntry().getxCordinate() < rect.getX1()) {
				searchForPoints(node.getRight(), res, rect, ++depth, counter);
			} else {
				if (node.getEntry().getyCordinate() >= rect.getY1() && node.getEntry().getyCordinate() <= rect.getY2()) {
					res.add(node.getEntry());
				}
				searchForPoints(node.getLeft(), res, rect, ++depth, counter);
				searchForPoints(node.getRight(), res, rect, ++depth, counter);
			}
		} else {
			if (node.getEntry().getyCordinate() > rect.getY2()) {
				searchForPoints(node.getLeft(), res, rect, ++depth, counter);
			}
			else if (node.getEntry().getyCordinate() < rect.getY1()) {
				searchForPoints(node.getRight(), res, rect, ++depth, counter);
			} else {
				if (node.getEntry().getxCordinate() >= rect.getX1() && node.getEntry().getxCordinate() <= rect.getX2()) {
					res.add(node.getEntry());
				}
				searchForPoints(node.getLeft(), res, rect, ++depth, counter);
				searchForPoints(node.getRight(), res, rect, ++depth, counter);
			}
		}
	}
	
	/**
	 * @precondition: the 2d tree has been constructed.
	 * The (x1,y1) pair represents a point in space near Pittsburgh and
	 * in the state plane coordinate system.
	 * @postcondition: the distance in feet to the nearest node is
	 * returned in Neighbor. In addition, the Neighbor object contains a
	 * reference to the nearest neighbor in the tree. 
	 * 
	 * @param x1
	 * @param y1
	 * @return
	 */
	public Neighbor nearestNeighbor(double x1, double y1) {
		double currBestDist = distance(root.getEntry().getxCordinate(), root.getEntry().getyCordinate(), x1, y1);
		Neighbor neighbor = new Neighbor(currBestDist, root);
		Counter counter = new Counter();
		searchNearest(root, neighbor, x1, y1, 0, counter);
		System.out.println("Looked at " + counter.getCount() + " nodes in tree. Found the nearest crime at:");
		System.out.println(neighbor.getRef().getEntry());
		return neighbor;
	}
	
	/**
	 * Recursive Search to find nearest
	 * 
	 * Neighbor here is already initialized when compared with root
	 * Based on depth we alternate between x and y axis
	 * 
	 * if depth is even:
	 * 	we check if node's x cordinate > x1, if it is then check left subtree
	 * 	after recursive left subtree call, we check if the neighbor's distance
	 *  is beyond the abs value of difference of nodes x cordinate and x1 then
	 *  we check right subtree too
	 *  
	 *  we check if node's x cordinate < x1, if it is then check right subtree
	 * 	after recursive right subtree call, we check if the neighbor's distance
	 *  is beyond the abs value of difference of nodes x cordinate and x1 then
	 *  we check left subtree too
	 * 
	 * We do the same as above if depth is odd, difference is we calculate for y
	 * cordinate 
	 * 
	 * @param node
	 * @param neighbor
	 * @param x1
	 * @param y1
	 * @param depth
	 * @param counter
	 */
	private void searchNearest(TreeNode node, Neighbor neighbor, double x1, double y1, int depth, Counter counter) {
		if (node == null) {
			return;
		}
		
		counter.increment();
		
		double currDistance = distance(node.getEntry().getxCordinate(), node.getEntry().getyCordinate(), x1, y1);
		if (currDistance < neighbor.getDistance()) {
			neighbor.setDistance(currDistance);
			neighbor.setRef(node);
		}
		
		if (depth % k == 0) {
			if (x1 <= node.getEntry().getxCordinate()) {
				searchNearest(node.getLeft(), neighbor, x1, y1, ++depth, counter);
				if (neighbor.getDistance() > Math.abs(node.getEntry().getxCordinate() - x1)) {
					searchNearest(node.getRight(), neighbor, x1, y1, depth, counter);
				}
			} else {
				searchNearest(node.getRight(), neighbor, x1, y1, ++depth, counter);
				if (neighbor.getDistance() > Math.abs(node.getEntry().getxCordinate() - x1)) {
					searchNearest(node.getLeft(), neighbor, x1, y1, depth, counter);
				}
			}
		} else { 
			if(y1 <= node.getEntry().getyCordinate()) {
				searchNearest(node.getLeft(), neighbor, x1, y1, ++depth, counter);
				if (neighbor.getDistance() > Math.abs(node.getEntry().getyCordinate() - y1)) {
					searchNearest(node.getRight(), neighbor, x1, y1, depth, counter);
				}
			} else {
				searchNearest(node.getRight(), neighbor, x1, y1, ++depth, counter);
				if (neighbor.getDistance() > Math.abs(node.getEntry().getyCordinate() - y1)) {
					searchNearest(node.getLeft(), neighbor, x1, y1, depth, counter);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 * @postcondition
	 * Euclidean distance between (x1, y1) and (x2, y2)
	 */
	private double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}
	
	/**
	 * @postcondition
	 * @return
	 * 
	 * Returns the size of the 2D Tree
	 */
	public int size() {
		return size;
	}
	
	/**
	 * @postcondition
	 * @return
	 * 
	 * Returns true if 2D Tree is empty
	 */
	public boolean isEmpty() { 
		return root == null; 
	}
}

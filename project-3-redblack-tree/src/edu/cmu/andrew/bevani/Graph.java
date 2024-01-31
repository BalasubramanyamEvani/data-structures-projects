/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 3
 */
package edu.cmu.andrew.bevani;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* Graph Class
* 
* Class invariants:
* 
* adjacencyMatrix -> Graph's Adjacency Matrix [VxV] where V is the 
* number of vertices
* 
* vertices -> number of vertices in this graph
* rbTree -> The adjacencyMatrix will be build using this passed tree
* to search for indices. Also, this tree will be used to get the subjectColors list
* 
*/
public class Graph {
	
	// Class Invariants
	private int[][] adjacencyMatrix;
	
	private int vertices;
	
	private RedBlackTree rbTree;
	
	// Constructor using passed students info(read from file)
	// and the RedBlack Tree for querying and fetch subjectColors
	// list
	public Graph(SinglyLinkedList students, RedBlackTree rbTree) {
		this.rbTree = rbTree;
		vertices = rbTree.size();
		adjacencyMatrix = new int[vertices][vertices];
		buildGraph(students);
	}
	
	/**
	 * @precondition
	 * 	1. The passed students list is valid doesn't contain null
	 *     and is similar to the format provided
	 * 
	 * @postcondition    
	 * 	1. Builds the graph:
	 * 	  For each student info the subjects are read and
	 *    then edge is created by update the 2D adjacency matrix
	 * 
	 * @param students
	 */
	private void buildGraph(SinglyLinkedList students) {
		students.reset();
		while (students.hasNext()) {
			StudentInfo student = (StudentInfo) students.next();
			String[] subjects = student.getSubjects();
			for (int i = 0; i < subjects.length - 1; ++i) {
				String src = student.getSubjects()[i];
				for (int j = i + 1; j < subjects.length; ++j) {
					addEdge(src, subjects[j]);
				}
			}
		}
	}
	
	/**
	 * @precondition
	 *  2. Both are valid subjects which are present in the RedBlack Tree
	 *  
	 * @postcondition
	 * 	1. Edge is created between the nodes(undirected fashion) by
	 *     updating the 2D adjacency matrix
	 * 
	 * @param subOne
	 * @param subTwo
	 */
	private void addEdge(String subOne, String subTwo) {
		int indexOne = rbTree.searchSubject(subOne);
		int indexTwo = rbTree.searchSubject(subTwo);
		adjacencyMatrix[indexOne][indexTwo] = 1;
		adjacencyMatrix[indexTwo][indexOne] = 1;
	}
	
	/**
	 * @return
	 * @postcondition
	 *  Returns the 2D adjacency matrix created
	 *  as a string
	 */
	public String printAdjacencyMatrix() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < vertices; ++i) {
			for (int j = 0; j < vertices; ++j) {
				sb.append(adjacencyMatrix[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * @precondition
	 * 	1. The graph is created correctly
	 * 
	 * @note
	 * 	1. First we fetch the subjectColors list from rbTree and color the first vertice
	 *  2. Second we initialize an array of booleans to store the available colors
	 *  3. populate the available colors to all true
	 *  4. The for each vertex we find adjacent vertices and mark off those
	 *     colors which have been used
	 *  5. Finally we loop the available colors array
	 *     to find the first vertex (color) which is available for us to use
	 *     
	 *     If no vertex find the we add a new color
	 *  6. Reset available colors array for new vertex
	 * 
	 * @return
	 * @postcondition
	 * 	Graph coloring is done and
	 *  an updated list of subjectColors is returned
	 */
	private SinglyLinkedList color() {
		SinglyLinkedList subjectColors = rbTree.getSubjectColors();
		((SubjectColor) subjectColors.getObjectAt(0)).setColor(0);
		boolean[] availableColors = new boolean[vertices];
		fillBoolean(availableColors, true);
		for (int v = 1; v < vertices; ++v) {
			for (int j = 0; j < vertices; ++j) {
				int verticeColor = ((SubjectColor) subjectColors.getObjectAt(j)).getColor();
				if (adjacencyMatrix[v][j] == 1 && verticeColor != -1) {
					availableColors[verticeColor] = false;
				}
			}
			int vcolor;
			for (vcolor = 0; vcolor < vertices; ++vcolor) {
				if (availableColors[vcolor]) {
					break;
				}
			}
			((SubjectColor) subjectColors.getObjectAt(v)).setColor(vcolor);
			fillBoolean(availableColors, true);
		}
		return subjectColors;
	}
	
	/**
	 * @precondition
	 *  The list is not empty
	 * 
	 * @param list
	 * @return
	 * @postcondition
	 * 	We find the count of colors used given out subjectColors list
	 */
	private int maxColors(SinglyLinkedList list) {
		list.reset();
		int max = -1;
		while (list.hasNext()) {
			int num = ((SubjectColor) list.next()).getColor();
			if (num > max) {
				max = num;
			}
		}
		return max + 1;
	}
	
	/**
	 * @precondition
	 * 	The graph is build and coloring is successful
	 * 
	 * @note
	 * 	1. Find the total colors used (unique)
	 *  2. Create that number of final exam periods
	 *  3. For each period now populate subjects w.r.t to 
	 *     period index
	 * 
	 * @return
	 * @postcondition
	 * 	Returns the array of Final Exam Periods
	 */
	public Period[] getSchedule() {
		SinglyLinkedList subjectColors = color();
		int maxColors = maxColors(subjectColors);
		Period[] periods = new Period[maxColors];
		for (int i = 0; i < maxColors; ++i) {
			periods[i] = new Period(i + 1);
		}
		subjectColors.reset();
		while (subjectColors.hasNext()) {
			SubjectColor subColor  = (SubjectColor) subjectColors.next();
			int index = subColor.getColor();
			String subject = subColor.getSubject();
			periods[index].insertSubject(subject);
		}
		return periods;
	}
	
	/**
	 * Helper utility to fill all values in a
	 * boolean array
	 * 
	 * @param arr
	 * @param val
	 * 
	 * @postcondition
	 *  1. The boolean array is filled with values val
	 */
	private void fillBoolean(boolean[] arr, boolean val) {
		for(int i = 0; i < arr.length; ++i) {
			arr[i] = val;
		}
	}
}

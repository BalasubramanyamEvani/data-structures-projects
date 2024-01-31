/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 3
 */
package edu.cmu.andrew.bevani;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

public class Driver {
	
	// Driver Method to run the program for HW 3
	public static void main(String... args) {
		StringBuilder sb = new StringBuilder("bevani\n");
		String filenameOne = args[0];
		String filenameTwo = args[1];
		process(filenameOne, sb);
		sb.append("\n");
		process(filenameTwo, sb);
		sb.append("\n");
		System.out.println(sb.toString());
		writeResult(sb.toString());
	}
	
	/**
	 * @precondition
	 * 	1. The file should be present i.e a valid needs to be provided
	 *  2. The format should be the same as the one provided in HW 3 writeup 
	 * 
	 * Process includes
	 * 1. Reading the file with name filename
	 * 2. Building a RedBlack Tree
	 * 3. Building the graph out of read students and RedBlack Tree
	 * 4. Printing the adjacency Matrix
	 * 5. Printing the exam periods
	 * @param filename
	 * @param sb: StringBuider instance
	 * 
	 * @return
	 * @postcondition
	 * 	Returns back the StringBuilder instance with update contents to printed
	 *  to console and result.txt
	 */
	private static StringBuilder process(String filename, StringBuilder sb) {
		try {
			SinglyLinkedList students = readStudentInfoFile(filename);
			RedBlackTree rbTree = new RedBlackTree(students);
			Graph graph = new Graph(students, rbTree);
			sb.append(graph.printAdjacencyMatrix());
			sb.append("\n");
			Period[] periods = graph.getSchedule();
			for (Period period: periods) {
				sb.append(period.toString() + "\n");
			}
		} catch (IOException ex) {
			System.out.println("Unable to read file");
		}
		return sb;
	}
	
	/**
	 * @precondition
	 * 	1. The file should be present i.e a valid needs to be provided
	 *  2. The format should be the same as the one provided in HW 3 writeup 
	 * 
	 * @param fileName
	 * 
	 * @return
	 * @postcondition
	 * 	Returns a student info list read from file
	 * 
	 * @throws IOException
	 *  If error during IO Read
	 */
	private static SinglyLinkedList readStudentInfoFile(String fileName) throws IOException {
		Path pathToFile = Paths.get(fileName);
		SinglyLinkedList students = new SinglyLinkedList();
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) { 
			Stream<String> lines = br.lines();
			lines.forEachOrdered(line -> {
				String[] attributes = line.split(" ");
				StudentInfo student = new StudentInfo(attributes[0], Integer.parseInt(attributes[1]));
				for (int i = 2; i < attributes.length; ++i) {
					student.insertSubject(attributes[i]);
				}
				students.addAtEndNode(student);
			});
		}
		return students;
	}
	
	/**
	 * 
	 * @param content
	 * 	Content to be written to the result.txt
	 * 
	 * @postcondition
	 * 	The String content gets written to result.txt
	 */
	private static void writeResult(String content) {
		try(PrintWriter printWriter = new PrintWriter(new FileWriter("./result.txt"))) {
			printWriter.print(content);
		} catch (IOException e) {
			System.out.println("Error while writing result");
		}
	}
}

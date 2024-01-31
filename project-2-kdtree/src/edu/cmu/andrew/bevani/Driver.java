/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	
	private static final String prompt = "What would you like to do\n" 
					+ "1: inorder\n"
					+ "2: preorder\n"
					+ "3: levelorder\n"
					+ "4: postorder\n"
					+ "5: reverseLevelOrder\n"
					+ "6: search for points within rectangle\n"
					+ "7: search for nearest neighbor\n"
					+ "8: quit\n";

	private static final String optionSixPrompt = "Enter a rectangle bottom left (X1,Y1) and top "
			+ "right (X2, Y2) as four doubles each separated by a space";
	
	private static final String exitPrompt = "Thank you for exploring Pittsburgh crimes in the 1990's.";
	
	/**
	 * Driver to run the option oriented 
	 * Tree queries
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TwoDTree tree = new TwoDTree("C:\\Users\\BALA\\Downloads\\CrimeLatLonXY.csv");
		System.out.println("Crime file loaded into 2D tree with " + tree.size() + " records");
		try(Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.println(prompt);
				int option = sc.nextInt();
				switch (option) {
				case 1:
					tree.inOrderPrint();
					break;
				case 2:
					tree.preOrderPrint();
					break;
				case 3:
					tree.levelOrderPrint();
					break;
				case 4:
					tree.postOrderPrint();
					break;
				case 5:
					tree.reverseLevelOrderPrint();
					break;
				case 6:
					System.out.println(optionSixPrompt);
					double x1 = sc.nextDouble();
					double y1 = sc.nextDouble();
					double x2 = sc.nextDouble();
					double y2 = sc.nextDouble();
					System.out.println("Searching for points within" + " (" + x1 + ", " + y1 + ") and " + "(" + x2 + ", " + y2 + ")");
					ListOfCrimes res = tree.findPointsInRange(x1, y1, x2, y2);
					System.out.println("Found " + res.getSize() + " crimes");
					System.out.println(res);
					writeKMLFile(res.toKML());
					break;
				case 7:
					System.out.println("Enter a point to find nearest crime. Separate with a space.");
					double x71 = sc.nextDouble();
					double y71 = sc.nextDouble();
					tree.nearestNeighbor(x71, y71);
					break;
				case 8:
					System.out.println(exitPrompt);
					System.exit(0);
					break;
				default:
					System.out.println("Invalid Option Selected");
					break;
				}
			}
		}
	}
	
	/**
	 * @precondition
	 *  1. content is not null
	 *  2. content should KML type supported by Google Earth
	 *  
	 * @param content
	 * @throws IOException
	 * 	if error while writing KML content to file
	 * 
	 * Writes KML string to file named "PGHCrimes.KML"
	 */
	public static void writeKMLFile(String content) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("./PGHCrimes.KML"))) {
			writer.write(content);
			System.out.println("The crime data has been written to PGHCrimes.KML. It is viewable in Google Earth Pro.");
		} catch (IOException e) {
			System.out.println("Unable to write KML String to file: " + e.getMessage());
		}
	}
}

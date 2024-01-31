/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4
*/
package edu.cmu.andrew.bevani;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Scanner;

import edu.cmu.andrew.bevani.partone.ApproxTour;
import edu.cmu.andrew.bevani.partone.Result;
import edu.cmu.andrew.bevani.parttwo.OptimumTour;
import edu.cmu.andrew.bevani.parttwo.ResultOpt;
import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* Main Driver Class for HW 4
*/
public class MainDriver {
	
	// Class Constant: file which needs to be processed
	private static final String file = "./CrimeLatLonXY1990.csv";
	
	// Class Constant: file which will store the result
	private static final String resultFile = "./result.txt";
	
	// Main driver program
	// User input is read for start and end date
	// in an infinite loop and
	// approx tour and optimal tour is calculated for each set of
	// passed in input
	// After each result, it's written to result.txt file path
	public static void main(String[] args) throws IOException, ParseException {
		System.out.println("bevani");
		String startDate = ""; 
		String endDate = "";
		
		// Checking if result file exists, if it does
		// then delete to store new results
		if (Paths.get(resultFile).toFile().exists()) {
			try {
				Files.delete(Paths.get(resultFile));
			} catch(IOException ioe) {
				System.out.println("Unable to delete prev results file.");
				System.out.println("Program exiting");
				return;
			}
		}
		
		// Read crime records
		CrimeFileReader creader = new CrimeFileReader(file);
		
		writeResult("bevani\n\n");
		
		int i = 1;
		
		// Infinitely read user inputs
		try(Scanner sc = new Scanner(System.in)) {
			while(true) {
				// Read use input start and end date
				System.out.println();
				System.out.println("Enter start date");
				startDate = sc.nextLine();
				
				System.out.println("Enter end date");
				endDate = sc.nextLine();
				
				SinglyLinkedList crimes = creader.filter(startDate, endDate);
				CrimeDistGraph graph = new CrimeDistGraph(crimes);
				
				// Calculate approx tour using Prim
				ApproxTour tour = new ApproxTour(graph.getAdjMat());
				Result result = tour.build();
				
				System.out.println();
				System.out.println("Crime Records Between " + startDate + " and " + endDate);
				crimes.reset();
				while(crimes.hasNext()) {
					System.out.println(crimes.next());
				}
				
				System.out.println();
				System.out.println("Hamiltonian Cycle (not necessarily optimum):");
				System.out.println(result.getHamiltonianCycle());
				System.out.println("Length Of cycle: " + result.getTotalDistance() + " miles");
				System.out.println();
				
				System.out.println("Looking at every permutation to find the optimal solution");
				
				// Calculate optimal tour using all permutations
				OptimumTour optTour = new OptimumTour(graph.getAdjMat());
				ResultOpt optTourResult = optTour.build();
				System.out.println("The best permutation");
				System.out.println(optTourResult.getTour());
				System.out.println();
				System.out.println("Optimal Cycle length = " + optTourResult.getDistance() + " miles");
				
				StringBuilder sb = new StringBuilder();
				sb.append("TestCase" + i + "\n");
				sb.append("Hamiltonian Cycle\n");
				sb.append(result.getHamiltonianCycle().toString() + "\n");
				sb.append("Length\n");
				sb.append(result.getTotalDistance() + "\n");
				sb.append("Optimum Path\n");
				sb.append(optTourResult.getTour().toString() + "\n");
				sb.append("Length\n");
				sb.append(String.valueOf(optTourResult.getDistance() + "\n"));
				sb.append("\n");
				
				writeResult(sb.toString());
				
				++i;
			}
		}
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
		try(PrintWriter printWriter = new PrintWriter(new FileWriter(resultFile, true))) {
			printWriter.print(content);
		} catch (IOException e) {
			System.out.println("Error while writing result");
		}
	}
	
}

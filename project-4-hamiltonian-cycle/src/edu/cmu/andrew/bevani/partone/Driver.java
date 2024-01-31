/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 1
*/
package edu.cmu.andrew.bevani.partone;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import edu.cmu.andrew.bevani.CrimeDistGraph;
import edu.cmu.andrew.bevani.CrimeFileReader;
import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* Driver Class for part one
*/
public class Driver {
	
	// Class Constant: file which needs to be processed
	private static final String file = "./CrimeLatLonXY1990.csv";
	
	// Main Driver Program for Part One
	/**
	 * Reads the crimes and filters the crimes
	 * based on start date and end date which are given by user
	 * 
	 * On the filtered crimes, Approximate Optimal Tour is 
	 * calculated using Prim
	 * 
	 * @param args
	 * 
	 * @throws ParseException
	 * @throws IOException
	 * 
	 * Throws an exception if there is a problem reading the 
	 * crime file
	 */
	public static void main(String[] args) throws ParseException, IOException {
		String startDate = ""; 
		String endDate = "";
		
		CrimeFileReader creader = new CrimeFileReader(file);
		
		try(Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter start date");
			startDate = sc.nextLine();
			
			System.out.println("Enter end date");
			endDate = sc.nextLine();
			
			SinglyLinkedList crimes = creader.filter(startDate, endDate);
			CrimeDistGraph graph = new CrimeDistGraph(crimes);
			
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
		}
	}
}

/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 2
*/
package edu.cmu.andrew.bevani.parttwo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import edu.cmu.andrew.bevani.CrimeDistGraph;
import edu.cmu.andrew.bevani.CrimeFileReader;
import edu.cmu.andrew.bevani.partone.ApproxTour;
import edu.cmu.andrew.bevani.partone.Result;
import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* Driver class for part two
* 
*/
public class Driver {
	
	// Constant: path of file which needs to be processed
	private static final String file = "./CrimeLatLonXY1990.csv";
	
	// Driver program of Part two
	public static void main(String[] args) throws ParseException, IOException {
		String startDate = ""; 
		String endDate = "";
		
		// reads the crime file
		CrimeFileReader creader = new CrimeFileReader(file);
		
		try(Scanner sc = new Scanner(System.in)) {
			// Gets input from user
			System.out.println("Enter start date");
			startDate = sc.nextLine();
			
			System.out.println("Enter end date");
			endDate = sc.nextLine();
			
			// filters the crime list
			SinglyLinkedList crimes = creader.filter(startDate, endDate);
			CrimeDistGraph graph = new CrimeDistGraph(crimes);
			
			// Approximate tour
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
			
			// Optimal Tour
			OptimumTour optTour = new OptimumTour(graph.getAdjMat());
			ResultOpt optTourResult = optTour.build();
			System.out.println("The best permutation");
			System.out.println(optTourResult.getTour());
			System.out.println();
			System.out.println("Optimal Cycle length = " + optTourResult.getDistance() + " miles");
		}
	}
}

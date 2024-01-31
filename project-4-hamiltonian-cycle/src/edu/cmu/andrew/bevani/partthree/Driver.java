/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 3
*/
package edu.cmu.andrew.bevani.partthree;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import edu.cmu.andrew.bevani.CrimeDistGraph;
import edu.cmu.andrew.bevani.CrimeFileReader;
import edu.cmu.andrew.bevani.partone.ApproxTour;
import edu.cmu.andrew.bevani.partone.Result;
import edu.cmu.andrew.bevani.parttwo.OptimumTour;
import edu.cmu.andrew.bevani.parttwo.ResultOpt;
import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* Part three driver class
*/
public class Driver {
	
	// Constant: path of the file which needs to be processed
	private static final String file = "./CrimeLatLonXY1990.csv";
	
	// driver program of part three
	public static void main(String[] args) throws ParseException, IOException {
		String startDate = ""; 
		String endDate = "";
		
		CrimeFileReader creader = new CrimeFileReader(file);
		
		try(Scanner sc = new Scanner(System.in)) {
			// Read date inputs from user
			System.out.println("Enter start date");
			startDate = sc.nextLine();
			
			System.out.println("Enter end date");
			endDate = sc.nextLine();
			
			SinglyLinkedList crimes = creader.filter(startDate, endDate);
			CrimeDistGraph graph = new CrimeDistGraph(crimes);
			
			// Approximate tour from part one
			ApproxTour tour = new ApproxTour(graph.getAdjMat());
			Result result = tour.build();

			// Optimal tour from part two
			OptimumTour optTour = new OptimumTour(graph.getAdjMat());
			ResultOpt optTourResult = optTour.build();
			KMLUtil.write(result.getHamiltonianCycle(), optTourResult.getTour(), crimes); // Writes the result to a KML file
			
			System.out.println("KML Generated Successfully");
		}
	}
}

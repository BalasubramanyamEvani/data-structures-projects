/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4
*/
package edu.cmu.andrew.bevani;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import edu.cmu.andrew.bevani.partone.CrimeEntry;
import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
*
* Class invariants:
* 
* crimes: Singly Linked List of all crimes
* 
*/
public class CrimeFileReader {
	
	// Constant: required for date parsing - static across all instances
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
	
	// Class invariants
	private SinglyLinkedList crimes;
	
	// Parameterized constructor
	// calls the readFile method to read the contents
	// of the passed in crime records file
	public CrimeFileReader(String fileName) throws IOException {
		crimes = new SinglyLinkedList();
		readFile(fileName);
	}
	
	/**
	 * 
	 * This method filters the read crime entries
	 * based on the start and end date passed to it
	 * 
	 * @param startDate
	 * @param endDate
	 * 
	 * @return
	 * Returns a filtered list of crimes which has a date
	 * between the start and end date [inclusive]
	 * 
	 * @throws ParseException
	 * Thrown if invalid date present [not in the form of MM/dd/yy]
	 */
	public SinglyLinkedList filter(String startDate, String endDate) throws ParseException {
		SinglyLinkedList res = new SinglyLinkedList();
		
		Date start = DATE_FORMAT.parse(startDate);
		Date end = DATE_FORMAT.parse(endDate);
		
		crimes.reset();
		while(crimes.hasNext()) {
			CrimeEntry entry = (CrimeEntry) crimes.next();
			if (dateInBetween(entry.getDate(), start, end)) {
				res.addAtEndNode(entry);
			}
		}
		
		return res;
	}
	
	/**
	 * This methods reads the crime records data from the 
	 * passed in file path and then stores in the crimes Singly Linked List
	 * 
	 * @param filePath
	 * The path to the crime records file
	 * 
	 * @throws IOException
	 * Thrown if unable to read the file
	 */
	private void readFile(String filePath) throws IOException {
		Path pathToFile = Paths.get(filePath);
		crimes = new SinglyLinkedList();

		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) { 
			Stream<String> lines = br.lines().skip(1);
			lines.forEachOrdered(line -> {
				String[] attributes = line.split(",");
				Date date;
				try {
					date = DATE_FORMAT.parse(attributes[5]);
					CrimeEntry entry = new CrimeEntry();
					entry.setxCordinate(Double.parseDouble(attributes[0]))
						.setyCordinate(Double.parseDouble(attributes[1]))
						.setTime(Integer.parseInt(attributes[2]))
						.setStreet(attributes[3])
						.setOffense(attributes[4])
						.setDate(date)
						.setTract(Integer.parseInt(attributes[6]))
						.setLatitude(Double.parseDouble(attributes[7]))
						.setLongitutde(Double.parseDouble(attributes[8]));
					crimes.addAtEndNode(entry);
				} catch (ParseException e) {
					throw new RuntimeException("Date Parse Exception: line -> " + line);
				}
			});
		}
	}
	
	/**
	 * This method computes whether a date is between
	 * the passed in start and end date [inclusive]
	 * 
	 * @param comp
	 * The date to be compared
	 * 
	 * @param start
	 * The start date
	 * 
	 * @param end
	 * The end date
	 * 
	 * @return
	 * True if it's between start and end [inclusive] or else false
	 */
	private boolean dateInBetween(Date comp, Date start, Date end) {
		return !(comp.after(end) || comp.before(start));
	}
}

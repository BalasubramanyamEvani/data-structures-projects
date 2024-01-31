/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 3
*/
package edu.cmu.andrew.bevani.partthree;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.cmu.andrew.bevani.partone.CrimeEntry;
import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* This is a utility class which has a method to 
* write tour data and generate a KML File, which can
* be imported on Google Earth
*/
public class KMLUtil {
	
	// Constant value: the small eta value, which represents the diff
	// in latitude and longitude of approx tour and optimal tour
	private static double diff = 0.001;
	
	// KML String template
	private static final String template = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
			+ "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n"
			+ "<Document>\n"
			+ "<name>Pittsburgh TSP</name><description>TSP on Crime</description><Style id=\"style6\">\n"
			+ "<LineStyle>\n"
			+ "<color>73FF0000</color>\n"
			+ "<width>5</width>\n"
			+ "</LineStyle>\n"
			+ "95-771 Data Structures and Algorithms for Information Processing Carnegie Mellon University\n"
			+ "4\n"
			+ "</Style>\n"
			+ "<Style id=\"style5\">\n"
			+ "<LineStyle>\n"
			+ "<color>507800F0</color>\n"
			+ "<width>5</width>\n"
			+ "</LineStyle>\n"
			+ "</Style>\n"
			+ "<Placemark>\n"
			+ "<name>TSP Path</name>\n"
			+ "<description>TSP Path</description>\n"
			+ "<styleUrl>#style6</styleUrl>\n"
			+ "<LineString>\n"
			+ "<tessellate>1</tessellate>\n"
			+ "<coordinates>\n"
			+ "%s"
			+ "</coordinates>\n"
			+ "</LineString>\n"
			+ "</Placemark>\n"
			+ "<Placemark>\n"
			+ "<name>Optimal Path</name>\n"
			+ "<description>Optimal Path</description>\n"
			+ "<styleUrl>#style5</styleUrl>\n"
			+ "<LineString>\n"
			+ "<tessellate>1</tessellate>\n"
			+ "<coordinates>\n"
			+ "%s"
			+ "</coordinates>\n"
			+ "</LineString>\n"
			+ "</Placemark>\n"
			+ "</Document>\n"
			+ "</kml>";
	
	/**
	 * This method calls the methods which calculate the approx and optimal tour
	 * co-ordinates.
	 * 
	 * It then uses the fetched co-ordinates to format the KML template
	 * and then finally call the writeResult method to write the contents
	 * to KML file
	 * 
	 * @param approxTour
	 * The list having the approx tour
	 * 
	 * @param optTour
	 * The list having the optimal tour
	 * 
	 * @param crimes
	 * List of Crime Entries
	 */
	public static void write(SinglyLinkedList approxTour, SinglyLinkedList optTour, SinglyLinkedList crimes) {
		CrimeEntry[] crimesArr = toArr(crimes);
		String approxTourCoords = scan(approxTour, crimesArr, true);
		String optTourCoords = scan(optTour, crimesArr, false);
		String res = String.format(template, approxTourCoords, optTourCoords);
		writeResult(res);
	}
	
	/**
	 * 
	 * @param content
	 * 	Content to be written to the PGHCrimes.kml file
	 * 
	 * @postcondition
	 * 	The String content gets written to PGHCrimes.kml file
	 */
	private static void writeResult(String content) {
		try(PrintWriter printWriter = new PrintWriter(new FileWriter("./PGHCrimes.kml"))) {
			printWriter.print(content);
		} catch (IOException e) {
			System.out.println("Error while writing result");
		}
	}
	
	/**
	 * This method converts a list to an array
	 * 
	 * it is done in this class so that it's easier to fetch the 
	 * crime data
	 * 
	 * @param crimes
	 * 
	 * @return
	 * An Array of crime records from it's list representation
	 */
	private static CrimeEntry[] toArr(SinglyLinkedList crimes) {
		CrimeEntry[] crimesArr = new CrimeEntry[crimes.countNodes()];
		int i = 0;
		crimes.reset();
		while (crimes.hasNext()) {
			crimesArr[i++] = (CrimeEntry) crimes.next(); 
		}
		return crimesArr;
	}
	
	/**
	 * This method scans the passed in tour and then use the
	 * crime records array to get the longitude and latitude
	 * 
	 * addDiff acts a flag when a small of diff = 0.001 needs to be added
	 * this is done so that the tours are visible on google earth, it'd done
	 * on the approx tour
	 * 
	 * @param list
	 * @param crimes
	 * @param addDiff
	 * 
	 * @return
	 * Returns the co-ordinates as as string which is used to format
	 * KML template
	 */
	private static String scan(SinglyLinkedList list, CrimeEntry[] crimes, boolean addDiff) {
		StringBuilder sb = new StringBuilder();
		
		list.reset();
		while (list.hasNext()) {
			int vertex = (int) list.next();
			double longitude = addDiff ? (crimes[vertex].getLongitutde() + diff): crimes[vertex].getLongitutde();
			double latitude = addDiff ? (crimes[vertex].getLatitude() + diff): crimes[vertex].getLatitude();
			sb.append(longitude + "," + latitude + ",0.000000\n");
		}
		
		return sb.toString();
	}
}

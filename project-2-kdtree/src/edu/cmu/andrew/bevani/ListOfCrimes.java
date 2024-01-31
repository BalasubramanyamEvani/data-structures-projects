/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

/**
 * Class used to store list of Crimes
 *
 * Class Invariants:
 * list - SinglyLinkedList to store list of crimes
 * 
 */
public class ListOfCrimes {
	
	// class invariant
	private SinglyLinkedList list;
	
	private static final String kmlstartTemplate = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            		+ "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" + "<Document>\n"
            		+ " <Style id=\"style1\">\n"
            		+ " <IconStyle>\n"
            		+ " <Icon>\n"
            		+ "\n"
            		+ "<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue\n"
            		+ "-dot.png</href>\n"
            		+ " </Icon>\n"
            		+ " </IconStyle>\n"
            		+ " </Style>";

	private static final String placemarkTemplate ="\t<Placemark>\n" 
					+ "<name>%s</name>\n"
                	+ "\t<description>%s</description>\n"
                	+ "<styleUrl>#style1</styleUrl>\n"
                	+ "\t<Point>\n"
                	+ "\t\t<coordinates>%s,%s,0.000000</coordinates>\n"
                	+ "\t</Point>\n"
                	+ "\t</Placemark>\n";

	private static final String kmlEndTemplate = "</Document>\n"
					+ "</kml>";
	
	/**
	 * Constructor
	 * Initializes list to an empty list
	 */
	public ListOfCrimes() {
		list = new SinglyLinkedList();
	}
	
	/**
	 * Gets Size of list
	 * 
	 * @postcondition
	 * @return
	 * Returns size of the list
	 */
	public int getSize() {
		return list.countNodes();
	}
	
	/**
	 * @precondition
	 * 	1. Entry not null
	 * @param entry
	 * @postcondition
	 * 	1. Entry is added to the list
	 */
	public void add(Entry entry) {
		list.addAtEndNode(entry);
	}
	
	/**
	 * @precondition
	 * 	1. Index is valid >= 0 and <= size of list
	 * 
	 * @param index
	 * 
	 * @return
	 * @postcondition
	 * 	Returns entry at index 
	 */
	public Entry get(int index) {
		return (Entry) list.getEntryAt(index);
	}
	
	/**
	 * @postcondition
	 * Prints out the list of crimes
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		list.reset();
		while (list.hasNext()) {
			sb.append(list.next()).append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * @precondition
	 *  1. List is not empty
	 * 
	 * @return
	 * @postcondition
	 * 	Prints out the list of crimes in KML format
	 *  compatible with Google Earth
	 */
	public String toKML() {
		StringBuilder sb = new StringBuilder();
		sb.append(kmlstartTemplate);
		list.reset();
		while (list.hasNext()) {
			Entry entry = (Entry) list.next();
			sb.append(String.format(placemarkTemplate, entry.getOffense(), 
								entry.getStreet(), entry.getLongitutde(), entry.getLatitude()));
		}
		sb.append(kmlEndTemplate);
		return sb.toString();
	}
}

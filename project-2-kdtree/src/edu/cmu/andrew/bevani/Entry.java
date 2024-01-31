/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 2
 */
package edu.cmu.andrew.bevani;

/*
* This class is used to store csv entries
* 
* Class invariants:
* xCordinate - X Cordinate Related to Crime Entry
* yCordinate - Y Cordinate Related to Crime Entry
* time - Time Related to Crime Entry
* street - Street Related to Crime Entry
* offense - Offense Related to Crime Entry
* date - Date Related to Crime Entry
* tract - Tract Related to Crime Entry
* latitude - Latitude Related to Crime Entry
* longitutde - Longitude Related to Crime Entry
*/
public class Entry {
	
	// class invariants
	private double xCordinate;
	
	private double yCordinate;
	
	private int time;
	
	private String street;
	
	private String offense;
	
	private String date;
	
	private int tract;
	
	private double latitude;
	
	private double longitutde;

	public Entry() {
	}

	public double getxCordinate() {
		return xCordinate;
	}

	public Entry setxCordinate(double xCordinate) {
		this.xCordinate = xCordinate;
		return this;
	}

	public double getyCordinate() {
		return yCordinate;
	}

	public Entry setyCordinate(double yCordinate) {
		this.yCordinate = yCordinate;
		return this;
	}

	public int getTime() {
		return time;
	}

	public Entry setTime(int time) {
		this.time = time;
		return this;
	}

	public String getStreet() {
		return street;
	}

	public Entry setStreet(String street) {
		this.street = street;
		return this;
	}

	public String getOffense() {
		return offense;
	}

	public Entry setOffense(String offense) {
		this.offense = offense;
		return this;
	}

	public String getDate() {
		return date;
	}

	public Entry setDate(String date) {
		this.date = date;
		return this;
	}

	public int getTract() {
		return tract;
	}

	public Entry setTract(int tract) {
		this.tract = tract;
		return this;
	}

	public double getLatitude() {
		return latitude;
	}

	public Entry setLatitude(double latitude) {
		this.latitude = latitude;
		return this;
	}

	public double getLongitutde() {
		return longitutde;
	}

	public Entry setLongitutde(double longitutde) {
		this.longitutde = longitutde;
		return this;
	}

	@Override
	public String toString() {
		return "Entry [xCordinate=" + xCordinate + ", yCordinate=" + yCordinate + ", time=" + time + ", street=" + street
				+ ", offense=" + offense + ", date=" + date + ", tract=" + tract + ", latitude=" + latitude
				+ ", longitutde=" + longitutde + "]";
	}
}

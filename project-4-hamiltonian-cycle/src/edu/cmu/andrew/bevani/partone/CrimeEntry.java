/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 4, part - 1
*/
package edu.cmu.andrew.bevani.partone;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
* This class is used to store csv entries
* 
* Class invariants:
* xCordinate - X Cordinate Related to Crime CrimeEntry
* yCordinate - Y Cordinate Related to Crime CrimeEntry
* time - Time Related to Crime CrimeEntry
* street - Street Related to Crime CrimeEntry
* offense - Offense Related to Crime CrimeEntry
* date - Date Related to Crime CrimeEntry
* tract - Tract Related to Crime CrimeEntry
* latitude - Latitude Related to Crime CrimeEntry
* longitutde - Longitude Related to Crime CrimeEntry
*/
public class CrimeEntry {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
	
	// class invariants
	private double xCordinate;
	
	private double yCordinate;
	
	private int time;
	
	private String street;
	
	private String offense;
	
	private Date date;
	
	private int tract;
	
	private double latitude;
	
	private double longitutde;

	public CrimeEntry() {
	}

	public double getxCordinate() {
		return xCordinate;
	}

	public CrimeEntry setxCordinate(double xCordinate) {
		this.xCordinate = xCordinate;
		return this;
	}

	public double getyCordinate() {
		return yCordinate;
	}

	public CrimeEntry setyCordinate(double yCordinate) {
		this.yCordinate = yCordinate;
		return this;
	}

	public int getTime() {
		return time;
	}

	public CrimeEntry setTime(int time) {
		this.time = time;
		return this;
	}

	public String getStreet() {
		return street;
	}

	public CrimeEntry setStreet(String street) {
		this.street = street;
		return this;
	}

	public String getOffense() {
		return offense;
	}

	public CrimeEntry setOffense(String offense) {
		this.offense = offense;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public CrimeEntry setDate(Date date) {
		this.date = date;
		return this;
	}

	public int getTract() {
		return tract;
	}

	public CrimeEntry setTract(int tract) {
		this.tract = tract;
		return this;
	}

	public double getLatitude() {
		return latitude;
	}

	public CrimeEntry setLatitude(double latitude) {
		this.latitude = latitude;
		return this;
	}

	public double getLongitutde() {
		return longitutde;
	}

	public CrimeEntry setLongitutde(double longitutde) {
		this.longitutde = longitutde;
		return this;
	}

	@Override
	public String toString() {
		return "CrimeEntry [xCordinate=" + xCordinate + ", yCordinate=" + yCordinate + ", time=" + time + ", street=" + street
				+ ", offense=" + offense + ", date=" + DATE_FORMAT.format(date) + ", tract=" + tract + ", latitude=" + latitude
				+ ", longitutde=" + longitutde + "]";
	}
}

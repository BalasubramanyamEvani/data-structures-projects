/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 3
 */
package edu.cmu.andrew.bevani;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* Period class to store Exam Periods
* 
* Class invariants:
* 
* number -> Final Exam period number
* subjects -> subjects list which will be conducted during that
* period
* 
*/
public class Period {
	
	// Class Invariants
	private int number;
	
	private SinglyLinkedList subjects;

	// Constructor only using number
	// as subjects will be added individually to
	// the list using provided getter
	public Period(int number) {
		this.number = number;
		subjects = new SinglyLinkedList();
	}

	public int getNumber() {
		return number;
	}

	public void setColor(int number) {
		this.number = number;
	}

	public SinglyLinkedList getSubjects() {
		return subjects;
	}

	public void insertSubject(String subject) {
		subjects.addAtEndNode(subject);
	}

	/**
	 * For Appropriate Printing Format
	 */
	@Override
	public String toString() {
		return "Final Exam Period " + number + " => " + subjects.toString();
	}
}

/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 3
 */
package edu.cmu.andrew.bevani;

/*
* This class is used by RedBlackTree class to 
* add subjects (insertion order) with color set to -1
* 
* Then it is called by the Graph class to update the values
* while graph coloring
* 
* Class invariants:
* 
* subject -> Subject Name
* color -> To Store color associated with Subject
* 
*/
public class SubjectColor {
	
	// Class Invariants
	private String subject;
	
	private int color;
	
	// Constructor using fields
	public SubjectColor(String subject, int color) {
		super();
		this.subject = subject;
		this.color = color;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}

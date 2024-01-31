/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 3
 */
package edu.cmu.andrew.bevani;

/*
* This class is used to store the content
* read from file
* 
* Class invariants:
* 
* name -> Student Name (<>,<> format)
* numOfSubjects -> To store number of subjects the student has taken
* subjects[] -> To store the subject names
* index -> used internally by the class object for insertion of subjects
* 
*/
public class StudentInfo {
	
	// class invariants
	private String name;
	
	private int numOfSubjects;
	
	private String[] subjects;
	
	private int index;
	
	// Constructor using fields
	public StudentInfo(String name, int numOfSubjects) {
		this.name = name;
		this.numOfSubjects = numOfSubjects;
		this.subjects = new String[numOfSubjects];
		this.index = 0;
	}

	public String getName() {
		return name;
	}

	public StudentInfo setName(String name) {
		this.name = name;
		return this;
	}

	public int getNumOfSubjects() {
		return numOfSubjects;
	}

	public String[] getSubjects() {
		return subjects;
	}
	
	public StudentInfo insertSubject(String newSubject) {
		if (index >= numOfSubjects) {
			throw new RuntimeException("max subjects reached for this student");
		}
		subjects[index++] = newSubject;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + name + "\n");
		sb.append("Number of Subjects: " + numOfSubjects + "\n");
		for (String subject: subjects) {
			sb.append(subject + "\n");
		}
		return sb.toString();
	}
}

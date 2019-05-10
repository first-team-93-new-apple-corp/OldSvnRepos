package src;

import java.util.*;

public class Student {
	private String name;
	private double GPA;
	private int studentID;
	private String gradDate;
	private boolean memberFIRST;
	
	public Student (String name, double GPA, int studentID, String gradDate, boolean memberFIRST) {
		this.name = name;
		this.GPA = GPA;
		this.studentID = studentID;
		this.gradDate = gradDate;
		this.memberFIRST = memberFIRST;
		
		
	}

	
	public String getname() {
		return name;
	}
	
	public double getGPA() {
		return GPA;
	}
	
	public int getstudentID() {
		return studentID;
	}
	
	public String getgradDate() {
		return gradDate;
	}
	
	public boolean isMemberOfFirst() {
		return memberFIRST;
	}
	
	public int compareTo (Student student) {
		return name.compareTo(student.getname());
	}


	


	
	
	
	
	
	

}


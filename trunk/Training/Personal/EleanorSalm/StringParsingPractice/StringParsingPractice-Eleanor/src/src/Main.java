package src;
import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class Main{
	String aLong; 

	final static int STUDENT_INDEX_NAME = 0; 
	final static int STUDENT_INDEX_GPA = 1;
	final static int STUDENT_INDEX_STUDENT_ID = 2;
	final static int STUDENT_INDEX_GRAD_DATE = 3;
	final static int STUDENT_INDEX_MEMBER_FIRST = 4;



	public static void main(String[] args) throws FileNotFoundException {

		File csv = new File("StudentDatabase.csv");
		Scanner fileScanner = new Scanner("StudentDatabase.csv");

		List<Student> studentList = new ArrayList<Student>();

		fileScanner.nextLine();

		while (fileScanner.hasNextLine()) {
			String currentLine = fileScanner.nextLine();
			String[] currentLineFields = currentLine.split(",");

			String studentName = currentLineFields[STUDENT_INDEX_NAME];
			double studentGPA = Double.parseDouble(currentLineFields[STUDENT_INDEX_GPA]);
			int studentID = Integer.parseInt(currentLineFields[STUDENT_INDEX_STUDENT_ID]);
			String studentGradDate = currentLineFields[STUDENT_INDEX_GRAD_DATE];
			boolean studentMemberFIRST = Boolean.parseBoolean(currentLineFields[STUDENT_INDEX_MEMBER_FIRST]);

			//Create student and add to list
			Student currentStudent = new Student(studentName, studentGPA, studentID, studentGradDate, studentMemberFIRST);
			studentList.add(currentStudent);
		}

		//Sort Student list
		studentList.sort(null);

		//Print header
		System.out.println("Name, Student ID");

		//for(Student currentStudent : studentList) {
		//	if(currentStudent.isMemberOfFirst()) {
		//		System.out.println(currentStudent.getname() + ", " + currentStudent.getstudentID());
		//	}

		while(fileScanner.hasNextLine()) {
			if(currentStudent.isMemberOfFirst()) {
				System.out.println(currentStudent.getname() + ", " + currentStudent.getstudentID());
			}
		}




	}














}


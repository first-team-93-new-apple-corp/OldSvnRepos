import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Example solution for string parsing exercise 
 * 
 * @author mark.tervo
 */
public class StudentCsvParser {

	final static int STUDENT_CSV_NAME_INDEX = 0;
	final static int STUDENT_CSV_GPA_INDEX = 1;
	final static int STUDENT_CSV_STUDENT_ID_INDEX = 2;
	final static int STUDENT_CSV_EXP_GRAD_DATE_INDEX = 3;
	final static int STUDENT_CSV_IS_FIRST_MEMBER_INDEX = 4;
	
	/**
	 * Main method
	 * 
	 * @param args Command line arguments
	 * @throws FileNotFoundException May throw this exception if the student database file is missing
	 */
	public static void main(String[] args) throws FileNotFoundException {
		//Open Student Database CSV file
		File studentDatabaseCsv = new File("StudentDatabase.csv");
		Scanner fileScanner = new Scanner(studentDatabaseCsv);
		
		//Create list to contain the information about the students
		List<Student> studentList = new ArrayList<Student>();
		
		// Pop header off the top of the file and ignore it
		fileScanner.nextLine();

		while(fileScanner.hasNextLine())
		{
			String currentLine = fileScanner.nextLine();
			String[] currentLineFields = currentLine.split(",");
			
			//Extract student information
			String studentName = currentLineFields[STUDENT_CSV_NAME_INDEX];
			float studentGpa = Float.parseFloat(currentLineFields[STUDENT_CSV_GPA_INDEX]);
			int studentId = Integer.parseInt(currentLineFields[STUDENT_CSV_STUDENT_ID_INDEX]);
			String studentExpGradData = currentLineFields[STUDENT_CSV_EXP_GRAD_DATE_INDEX];
			boolean isMemberOfFirst = currentLineFields[STUDENT_CSV_IS_FIRST_MEMBER_INDEX].equals("Yes");
			
			//Create student and add to list
			Student currentStudent = new Student(studentName, studentGpa, studentId, studentExpGradData, isMemberOfFirst);
			studentList.add(currentStudent);
		}
		
		//Sort Student list
		studentList.sort(null);
		
		//Print header
		System.out.println("Name, Student ID");
		
		//Print the name and student ID of the FIRST students
		for(Student currentStudent : studentList) {
			if(currentStudent.isMemberOfFirst()) {
				System.out.println(currentStudent.getName() + ", " + currentStudent.getStudentId());
			}
		}
		
	}

}

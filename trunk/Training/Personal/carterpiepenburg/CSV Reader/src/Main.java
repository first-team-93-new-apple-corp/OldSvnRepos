import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	
	static String students;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner studentData = new Scanner(new File("StudentDatabase.csv")).useDelimiter(",|\r\n");
		int number = 0;
		String one, two, three, four, five;
		student[] studentArray = new student[101];
		
		boolean onlyNamesAndID = false;
		boolean firstMembers = false;
		
	    while (studentData.hasNext()) {
	    	
	        one = studentData.next();
	        two = studentData.next();
            three = studentData.next();
            four = studentData.next();
            five = studentData.next();
            
            studentArray[number] = new student(one, two, three, four, five);
            
            if(firstMembers) {
            	if(studentArray[number].firstMember.equals("Yes")) {
            		if(onlyNamesAndID) {
            			System.out.println(studentArray[number].name + ", " + studentArray[number].studentID);
            		}
            		if(!onlyNamesAndID) {
            			System.out.println(studentArray[number].toString());
            		}
            	}
            }
            
            if(!firstMembers) {
            	if(onlyNamesAndID) {
            		System.out.println(studentArray[number].name + ", " + studentArray[number].studentID);
            	}
            	if(!onlyNamesAndID) {
            		System.out.println(studentArray[number].toString());
            	}
            }
		    
		    number++;
            
	    }
	    
	    studentData.close();

	}

}

package src;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Main{
	String aLong; 
	public static void main(String[] args) throws FileNotFoundException {
		
Scanner data = new Scanner(new File("StudentDatabase.csv"));
while (data.hasNextLine()) {
	String aLong;
     aLong = data.nextLine();
	if (aLong.contains("Yes")) {
		String[] subject = aLong.split(",");
		System.out.println(subject[0]);
		System.out.println(subject[2]);
		
		
	}
	
	
}
	
 
	   
	  }
	



		
		

	}


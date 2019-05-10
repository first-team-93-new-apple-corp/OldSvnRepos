import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class train {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner data = new Scanner(new File("StudentDatabase.csv"));
	      while (data.hasNextLine()) {
	    	  String aLong;
	           aLong = data.nextLine();
	          
	                    
	           Boolean Myboolean = aLong.contains("Yes");
	       if (Myboolean==true) {
	    	   System.out.println(aLong);
	        	    }
	    	}
	      }
	      
	}



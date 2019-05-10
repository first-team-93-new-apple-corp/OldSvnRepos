
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class roboid {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner data = new Scanner(new File("StudentDatabase.csv"));
	      while (data.hasNextLine()) {
	    	  String aLong;
	           aLong = data.nextLine();
	          
	                    
	           Boolean Myboolean = aLong.contains("Yes");
	       if (Myboolean==true) {
	    	   System.out.println(aLong);
	    	   {
	    		   //System.out.println(aLong);
	    	   }
	    	   String currentLine = aLong;
	    	   String[] s = currentLine.split(",");
	    	   
	    	   System.out.println(s[0]+","+s[2]);
	        	    }
	       
	       System.out.println(args[0]+","+args[2]);
	    	}
	      }
	      
	}
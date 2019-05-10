
		import java.io.File;
		import java.io.FileNotFoundException;
		import java.util.Scanner;

		public class robostu {

			public static void main(String[] args) throws FileNotFoundException {
				
				Scanner data = new Scanner(new File("StudentDatabase.csv"));
			      while (data.hasNextLine()) {
			    	  String aLong;
			           aLong = data.nextLine();
			           System.out.println(aLong);
		}
			 
			}
}


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
class main{

	public static void main(String[] args) throws FileNotFoundException {
	
		//problem 1
		Scanner sc = new Scanner(new File("StudentDatabase.csv"));
	
	
		while (sc.hasNextLine()) 
		{
			String sorting = sc.nextLine();
		
			if(sorting.contains("Yes")) 
			{
			String sortingSplit[]  = sorting.split (",");
			
			System.out.println(sortingSplit[0] + sortingSplit[1]);
			
		
	
			}	
		}
	}
}
import java.util.Scanner;

public class diamond {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Number of rows per triangle/diamond: ");
		int numberOfRows = scanner.nextInt();
		
		System.out.println("Triangles or diamonds (tri = 1/dia = 2): ");
		int triOrDia = scanner.nextInt();
		
		System.out.println("Number of triangles/diamonds: ");
		int numberOfTris = scanner.nextInt();
		
		int totalStars = 0;
		int totalRows = numberOfRows * numberOfTris;
		
		for (int g = 0; g < numberOfTris; g++) { //for #4
			
			
			
			
			//Triangle
			if (triOrDia == 1) {
				
				for (int i = 0; i < numberOfRows; i++) { //for #3
					
					for (int j = 0; j < ((numberOfRows - 1) - i); j++) { //for #1
						System.out.print(" ");
				
					} //for #1 end
			
					for (int k = 0; k < (2 * i + 1); k++) { //for #2
						System.out.print("*");
						totalStars++;
					} //for #2 end
					
					System.out.println("");
					
					
				} //for #3 end
			
			}
			
			
			
			
			
			//Diamond
			else if (triOrDia == 2) {
				
				for (int i = 0; i < (numberOfRows/2); i++) { //for #3
					
					for (int j = 0; j < ((numberOfRows - 1) - i); j++) { //for #1 
						
						System.out.print(" ");
				
					} //for #1 end
			
					for (int k = 0; k < (2 * i + 1); k++) { //for #2
						
						System.out.print("*");
						totalStars++;
					} //for #2 end
					
					System.out.println("");
					
					
				} //for #3 end
				
				for (int i = ((numberOfRows - 1)/2); i >= 0; i--) { //for #3
					
					for (int j = 0; j < ((numberOfRows - 1) - i); j++) { //for #1
						
						System.out.print(" ");
				
					} //for #1 end
			
					for (int k = 0; k < (2 * i + 1); k++) { //for #2
						
						System.out.print("*");
						totalStars++;
					} //for #2 end
					
					System.out.println("");
					
					
				} //for #3 end
				
			}
			
		} //for #4 end
		
		System.out.println("");
		System.out.println("");
		
		System.out.println("Total stars: ");
		System.out.println(totalStars);
		
		System.out.println("Total rows: ");
		System.out.println(totalRows);
		
	}
		
}



import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		int numRows = 4;
		
		for (int currentRow = 0; currentRow < numRows; currentRow++) {
			int numSpaces = numRows-currentRow - 1;
			int numStars = 2 * currentRow + 1;
			
			for (int currentSpace = 0; currentSpace < numSpaces; currentSpace++) {
				System.out.print(" ");
			}
			
			for (int currentStar = 0; currentStar < numStars; currentStar++) {
				System.out.print("*");
			}
			System.out.println("");
		}
		*/
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Please enter the number of rows: ");
		int numRows = scanner.nextInt();
		scanner.close();
		
		Diamond diamond = new Diamond(numRows);
		
		diamond.print();
	}

}

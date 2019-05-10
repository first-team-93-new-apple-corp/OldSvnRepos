
public class StarRow {
	private int numStartingSpaces;
	private int numStars;
	
	StarRow(int rowIndex, int numRows) {
		int halfwayRow, rowWidth;
		// If the number of rows is odd...
		if (numRows % 2 == 1) 
		{
			rowWidth = numRows;
			halfwayRow = numRows / 2;
		}
		else // If the number of rows is even...
		{
			rowWidth = numRows - 1;
			halfwayRow = numRows / 2;
			// We need to adjust the halfway row depending on whether we're in the top or the bottom
			if (rowIndex < halfwayRow)
			{
				halfwayRow--;
			}
		}
		
		numStartingSpaces = Math.abs(halfwayRow - rowIndex);
		numStars = rowWidth - (numStartingSpaces * 2);
	}
	
	public void print() {
		for (int i = 0; i < numStartingSpaces; i++)
		{
			System.out.print(" ");
		}
		for (int j = 0; j < numStars; j++)
		{
			System.out.print("*");
		}
		System.out.println("");
	}
}

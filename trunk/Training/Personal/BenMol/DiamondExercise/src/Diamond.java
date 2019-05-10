
public class Diamond {

	private StarRow rows[];
	private int numRows;
	
	Diamond(int myNumRows) {
		numRows = myNumRows;
		rows = new StarRow[numRows];
		
		for (int i = 0; i < numRows; i++)
		{
			rows[i] = new StarRow(i, numRows);
		}
	}
	
	public void print() {
		for (int i = 0; i < numRows; i++)
		{
			rows[i].print();
		}
	}
}

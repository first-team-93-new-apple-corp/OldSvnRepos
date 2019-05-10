package control;

import java.util.concurrent.ThreadLocalRandom;

public class Snowflake {

	public int row;
	public int column;
	
	public String snowflake = "*";
	
	
	Snowflake(int consRow, int consColumn){
		
		this.row = consRow;
		this.column = consColumn;
		
	}
	
	void move() {
		
		this.column += 1;
		int leftRightStraight = ThreadLocalRandom.current().nextInt(0, 3);
		if(leftRightStraight == 2) {
			this.row += 1;
		}
		else if(leftRightStraight == 3) {
			this.row -= 1;
		}
		
	}
	
}

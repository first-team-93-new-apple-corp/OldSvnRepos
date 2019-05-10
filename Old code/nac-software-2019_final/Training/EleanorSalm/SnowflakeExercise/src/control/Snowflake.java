package control;

import java.util.concurrent.ThreadLocalRandom;

import graphics.TextBoard;

public class Snowflake {
	int column;
	int row;
	private TextBoard textBoard;
	
	public Snowflake(int column, int row){
		this.column = column;
		this.row = row;
		
		
		
	}
	public void Move(){
		 row++;
		 int col = ThreadLocalRandom.current().nextInt(1, textBoard.getNumColumns());
		 
	}
	

}

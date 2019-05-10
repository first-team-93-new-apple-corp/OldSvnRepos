package control;


import userInterface.ICharKeyProcessor;
import graphics.TextBoard;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class Control extends Thread implements ICharKeyProcessor{

	private TextBoard textBoard;
	private char lastKeyPressed = ' ';
	private boolean receivedInput = false;
	private ArrayList<Snowflake> mySnowflakeList;
	
	
	public String counter = "*"; // This is just used for the example code, and can be removed
	
	/**
	 * Creates a new Control objects
	 * 
	 */
	public Control(TextBoard myTextBoard) {
		textBoard = myTextBoard;
	}

	/**
	 * Main control loop of the application
	 */
	@Override public void run() {
		while (true) {
			
			doDuringEachLoop();
			
			
			// If we received any input, process it
			if (receivedInput) {
				
				handleKeyPressInLoop(lastKeyPressed);
				
				// Indicate that we have processed the input
				receivedInput = false;
			}
			
			textBoard.repaint(); // At the end of every loop, repaint the board to show any changes made during the loop
			try { // Attempt to wait, but watch for errors
				Thread.sleep(100); // Wait for 100 ms before running the loop again
			}
			catch (InterruptedException e) { // If an error (exception) occurs in the try{} block, then do something to handle it
				e.printStackTrace();
			}
		}
	} // run

	@Override
	public void keyPressed(char key) {
		// TODO React to pressed keys
		// For now, don't do anything
		lastKeyPressed = key;
		receivedInput = true;
	}

	@Override
	public void keyReleased(char key) {
		// TODO React to released keys
		// For now, don't do anything
	}

	@Override
	public void keyTyped(char key) {
		// TODO React to typed keys
		// For now, don't do anything
	}

	/**
	 * This is a private function where code that should execute during each loop of the main
	 * control thread is placed.
	 */
	private void doDuringEachLoop() {
		// THIS IS WHERE YOU SHOULD ADD ANY CODE THAT YOU WANT TO RUN EVERY 100 MS
		int col = ThreadLocalRandom.current().nextInt(1, textBoard.getNumColumns());
		int row = ThreadLocalRandom.current().nextInt(1, textBoard.getNumColumns());
		mySnowflakeList.add("1");
		
		Snowflake mySnowflake = new Snowflake(col, row);
		mySnowflakeList.add(mySnowflake);
		
		for () {
			
			
		}
		
		
		
	}
	
	/**
	 * This is a private function where code that should respond to key presses during each loop
	 * of the main control thread is placed.
	 * @param key
	 */
	private void handleKeyPressInLoop(char key) {
		// If the input was the enter key, quit
		if (lastKeyPressed == '\n') {
			System.exit(0);
		}
		
		// Else, set an arbitrary location on the board to that char
		// This is just here to demonstrate how to draw on the board.
		textBoard.setCharAtLocation(3, 5, lastKeyPressed);
	}
}

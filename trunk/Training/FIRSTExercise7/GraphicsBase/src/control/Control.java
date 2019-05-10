package control;

import userInterface.ICharKeyProcessor;
import graphics.TextBoard;

public class Control extends Thread implements ICharKeyProcessor{

	private TextBoard textBoard;
	private char lastKeyPressed = ' ';
	private boolean receivedInput = false;
	private char savedChar = ' ';
	
	private int counter = 0; // This is just used for the example code, and can be removed
	
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
			textBoard.clearBoard();
			
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
		counter++;
		
		// This is just here to demonstrate how to draw on the board. The savedChar was set by handleKeyPress
		textBoard.setCharAtLocation(3, 5, savedChar);
		textBoard.setStringAtLocation(0, 0, Integer.toString(counter));
	}
	
	/**
	 * This is a private function where code that should respond to key presses during each loop
	 * of the main control thread is placed.
	 * @param key
	 */
	private void handleKeyPressInLoop(char key) {
		// If the input was the enter key, quit
		if (key == '\n') {
			System.exit(0);
		}
		
		// Else, save the char to be displayed at an arbitrary location on the board
		savedChar = key;
	}
}

package control;

import userInterface.Keyboard;
import graphics.TextBoard;

public class Main {

	/** Number of rows in the textBoard */
	private static final int ROWS    = 20;
	/** Number of columns in the textBoard */
	private static final int COLUMNS = 60;
	/** Font size in the textBoard */
	private static final int FONTSIZE = 20;

	/**
	 * Constructs the main objects in the system and starts the two threads
	 * 
	 * @param args - N/A
	 */
	public static void main(String[] args) {
		TextBoard textBoard = new TextBoard(ROWS, COLUMNS, FONTSIZE);
		textBoard.setTitle("Snowflakes");
		Control control = new Control(textBoard); // Create new instance of Control called control
		Keyboard keyboard = new Keyboard(control); // Create a new Keyboard object that will listen for keyboard input
		textBoard.addKeyListener(keyboard); // Creates a new KeyListener
	    textBoard.clearBoard(); // Clears the board
	    
	    /*
	     * Threading is complicated, but the short version is that a "thread" represents an independent piece of code
	     * executing in an application. So a basic program has one thread, and it starts executing in main and runs line-by-line until 
	     * the end of main. But if we create more threads, we can have multiple pieces of code running simultaneously.
	     * In this case, it allows us to do things like draw text, listen for keyboard input, and do control processing at the same time.
	     * For these threads, execution begins in the "run" methods of the "control" and "textBoard" objects.
	     */
		Thread controlThread = new Thread(control);
		Thread boardThread = new Thread(textBoard);
		controlThread.start();
		boardThread.start();
	}

}

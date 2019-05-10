package userInterface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Contains the KeyListener that received and interprets user input.
 */
public class Keyboard implements KeyListener {

	private ICharKeyProcessor m_processor;
	/**
	 * Constructs an instance of the KeyBoard
	 * 
	 */
	public Keyboard(ICharKeyProcessor processor) {
		m_processor = processor;
	}

	/**
	 * Deals with key presses
	 * 
	 * @param e The key input.
	 */
	public void keyPressed(KeyEvent e) {
		char keyPress = e.getKeyChar();
		m_processor.keyPressed(keyPress);
	}

	/**
	 * Deals with key releases
	 * 
	 * @param e The key input.
	 */
	public void keyReleased(KeyEvent e) {
		char keyRelease = e.getKeyChar();
		m_processor.keyReleased(keyRelease);
	}

	/**
	 * Deals with key types
	 * 
	 * @param e The key input.
	 */
	public void keyTyped(KeyEvent e) {
		char keyTyped = e.getKeyChar();
		m_processor.keyReleased(keyTyped);
	}

}

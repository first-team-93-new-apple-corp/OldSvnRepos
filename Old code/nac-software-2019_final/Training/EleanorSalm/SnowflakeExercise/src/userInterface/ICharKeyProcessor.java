package userInterface;

/**
 * This is an interface for classes that want to process KeyListener events by dealing only with chars.
 *
 */
public interface ICharKeyProcessor {
	/**
	 * Handles keys on the down press
	 * @param key
	 */
	public void keyPressed(char key);
	
	/**
	 * Handles keys on the release
	 * @param key
	 */
	public void keyReleased(char key);
	
	/**
	 * Handles keys on a completed press and release
	 * @param key
	 */
	public void keyTyped(char key);
}

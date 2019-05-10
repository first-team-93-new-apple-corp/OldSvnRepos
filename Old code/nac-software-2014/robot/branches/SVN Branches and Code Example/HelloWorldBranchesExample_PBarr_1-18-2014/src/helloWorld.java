import javax.swing.JOptionPane;

/**
 * This class is an example for Developers Guide 2014 for FRC Team 93.
 * @author Patrick Barr
 * @version 2.0
 * @since 2.0 - Added Javadocs.
 * @since 1.1 - Cleaned Errors.
 * @since 1.0 - Created and uploaded to Subversion as example commit.
 */

public class helloWorld {
	
	static String helloWorld = "Hello World!";
	
	/**
	 * Runs {@link helloWorldPrint()} and {@link customMessage(String customText)} for java examples.
	 * @param args
	 * @see {@link helloWorldPrint()}
	 * @see {@link customMessage(String customText)}
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		helloWorldPrint();
		customMessage(JOptionPane.showInputDialog(null,"Input a custom string.","Example"));
	}//main(String[] args)
	
	/**
	 * Displays the message "Hello World".
	 */
	public static void helloWorldPrint(){
		JOptionPane.showMessageDialog(null,helloWorld);
		System.out.println(helloWorld);
	}//helloWorldPrint()
	
	/**
	 * Displays a custom message, {@link customText}.
	 * @param customText - String to be displayed.
	 */
	public static void customMessage(String customText){
		if(customText == null) customText = "You pressed cancel.";
		JOptionPane.showMessageDialog(null,customText);
		System.out.println(customText);
	}//customMessage()
}//helloWorld

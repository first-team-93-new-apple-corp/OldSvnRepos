import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.*;


/*
 * Starting in Scanner Generation 4, this is the main program that runs
 * 
 * It will launch a window with a text box and some basic instructions
 * so that students don't accidentally scan their ID's into the code
 * instead of the console.
 */
public class GUIMain
{
	public static void main(String[] args)
	{
		GUIMain.createWindow();
	}
	static JTextField textBox;
	static JTextArea instructions;
	public static JTextArea printBox;
	static String newLine = "\n";
	
	public static void createWindow()
	{
		textBox = new JTextField(20);
		textBox.addActionListener(new Action());
		
		instructions = new JTextArea(1,3);
		instructions.setEditable(false);
		instructions.append("Have a nice day!" + newLine);
		instructions.append("If you don't have your ID card, you can type in your ID number");
		
		printBox = new JTextArea(5,30);
		printBox.setEditable(false);
		JScrollPane scrollbox = new JScrollPane(printBox);
		
		
		JFrame frame = new JFrame("Sign In / Sign Out");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.add(textBox);
		panel.add(instructions);
		panel.add(scrollbox);
		frame.pack();
	}

	//When the text box detects an action (enter is pressed)
	static class Action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//It will run through the 
			Hub.mainProgram(textBox.getText());
			textBox.selectAll();
			System.out.println(textBox.getText());
		}
	}
}
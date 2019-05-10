import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Game {

	//Creating window
	JFrame window;
	
	//Creating container
	Container con;

	//Title screen
	JPanel titleNamePanel, startButtonPanel, mainTextPanel, choiceButtonPanel;
	JLabel titleNameLabel;
	JButton startButton, choice1, choice2, choice3;
	TitleScreenHandler titleHandler = new TitleScreenHandler();
	BattleScreenHandler battleHandler = new BattleScreenHandler();
	
	//Main text area
	JTextArea mainTextArea;
	
	//Creating fonts
	Font titleFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, 90);
	Font buttonFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, 28);
	Font mainFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, 14);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Game();
		
	}
	
	public Game() {
		
		//Setting up window
		window = new JFrame();
		window.setSize(800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);
		window.setTitle("Test");
		window.setResizable(false);
		
		//Assigning container
		con = window.getContentPane();
		
		//Title panel
		titleNamePanel = new JPanel();
		titleNamePanel.setBounds(100, 100, 600, 150);
		titleNamePanel.setBackground(Color.black);
		titleNameLabel = new JLabel("TEST");
		titleNameLabel.setForeground(Color.white);
		titleNameLabel.setFont(titleFont);
		
		//Start button
		startButtonPanel = new JPanel();
		startButtonPanel.setBounds(300, 400, 200, 100);
		startButtonPanel.setBackground(Color.black);
		
		startButton = new JButton("PLAY");
		startButton.setBackground(Color.black);
		startButton.setForeground(Color.white);
		startButton.setFont(buttonFont);
		startButton.addActionListener(titleHandler);
		
		//Adding components to panels
		titleNamePanel.add(titleNameLabel);
		startButtonPanel.add(startButton);
		
		//Adding panels to containers
		con.add(titleNamePanel);
		con.add(startButtonPanel);
		
		//Setting window to visible
		window.setVisible(true);
		
	}
	
	public void createGameScreen() {
		
		titleNamePanel.setVisible(false);
		startButtonPanel.setVisible(false);
		
		//Main text
		mainTextPanel = new JPanel();
		mainTextPanel.setBounds(100, 100 ,600, 250);
		mainTextPanel.setBackground(Color.black);
		
		con.add(mainTextPanel);
	
		mainTextArea = new JTextArea("Test text area");
		mainTextArea.setBounds(100, 100, 600, 250);
		mainTextArea.setBackground(Color.black);
		mainTextArea.setForeground(Color.white);
		mainTextArea.setFont(mainFont);
		mainTextArea.setLineWrap(true);
		
		mainTextPanel.add(mainTextArea);
		
		choiceButtonPanel = new JPanel();
		choiceButtonPanel.setBounds(300, 400, 200, 100);
		choiceButtonPanel.setBackground(Color.black);
		choiceButtonPanel.setLayout(new GridLayout(3, 1));
		
		con.add(choiceButtonPanel);
		
		choice1 = new JButton("Choice 1");
		choice1.setBackground(Color.black);
		choice1.setForeground(Color.white);
		choice1.setFont(buttonFont);
		choiceButtonPanel.add(choice1);
		
		choice2 = new JButton("Choice 2");
		choice2.setBackground(Color.black);
		choice2.setForeground(Color.white);
		choice2.setFont(buttonFont);
		choiceButtonPanel.add(choice2);
		
		choice3 = new JButton("Choice 3");
		choice3.setBackground(Color.black);
		choice3.setForeground(Color.white);
		choice3.setFont(buttonFont);
		choiceButtonPanel.add(choice3);
		
		//Setting text
		mainTextArea.setText("halsdkjfhalskdjfhlaskdjfhkasjdhflkasjdhflkjashdflahsdfljhasdklfhalsdfhklashdfkljasdfh"+
		"asdfkjhasdlfkjhasldkfhaskldfhklasdhflkasdjfklahsdfklhasldkfhklasdfhlkasdhflkajshflkasdhfljkashdflkjasdhfljk");
		
		mainTextPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		choiceButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
	}
	
	public class TitleScreenHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent event) {
			
			createGameScreen();
			
		}
		
	}
	
	public class BattleScreenHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent event) {
			
			
			
		}
		
	}

}

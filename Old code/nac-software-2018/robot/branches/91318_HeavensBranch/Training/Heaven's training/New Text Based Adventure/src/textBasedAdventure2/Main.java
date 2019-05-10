package textBasedAdventure2;

import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	public static boolean playerType = false;
	
	public static void main (String[] args)
	{
		System.out.println("you wake up and look at the clock, it's 1:07 AM, you try falling asleep but find yourself to be too hungry to do so,\nyou decide to walk downstairs to get some cookies, do you take your flashlight? type '1' for yes and '2' for no");
		playerType = true;
		
		while (playerType==true) {
			ScenarioPicker.pickScenario();
		}
	
	}
}
/*
 * class for words player can type
 * class for response to what player says
 * easter eggs
 * can die
 * fall down stairs without flashlight and need bandages
 * spoiled milk option, random number generator decides if it is
 * poisoned cookies
 * can jump out window and die
 * class keeping track of things like inventory, current room, players state (hurt or not), objects interacted with (use booleans of this)
 */

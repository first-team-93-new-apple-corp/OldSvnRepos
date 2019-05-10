package textBasedAdventure2;

import java.util.Random;

public class Scenario5 {
	public static boolean scenario5done =false;
	public static void milk() {
		Random spoiledMilk = new Random(2);
		if (spoiledMilk.equals(1)) {
			System.out.println("you go to grab the milk and realize it is spoiled, would you like to go to the store and get new milk, get some water, or go to bed thirsty? type '1 for go to the store '2' for get water or '3' for go to bed thirsty");
			scenario5done = true;
		}
		else if (spoiledMilk.equals(2)) {
			System.out.println("you grab the milk and drink it, you then go back to bed");
			scenario5done = true;
			ScenarioPicker.endGame=true;
		}
		else {
			System.out.println("something here is wrong, the code is broken");
		}
	}
	public static void noMilk() {
		System.out.println("you decide not to drink the milk and go to bed, however, you were so dehydrated that you died in your sleep");
		ScenarioPicker.endGame=true;
		scenario5done= true;
	
	}
}

package textBasedAdventure2;

import java.util.Random;

public class Scenario4 {
	public static boolean scenario4done = false;
	public static boolean needToFindAntedote = false;
	public static void magic() {
		Random magicWorks = new Random(2);
		if (magicWorks.equals(1)) {
			System.out.println("the magic works! you eat your cookies along with a glass of milk and go to bed");
			ScenarioPicker.endGame=true;
			scenario4done = true;
		}
		else if (magicWorks.equals(2)) {
			System.out.println("the magic went trerribly wrong, in order to survive you must find the antedote. type '2' '3' or '4'");
			scenario4done = true;
			Scenario2.scenario2done=true;
			needToFindAntedote = true;
		}
		else {
			System.out.println("something here is wrong, the code is broken");
		}
	}
	public static void noMagic() {
		System.out.println("you decided not to use magic, select a new cupboard to open by typing '2' '3' or '4'");
		scenario4done= true;
	
	}
}

package textBasedAdventure2;

public class Scenario4 {
	public static boolean scenario4done = false;
	public static boolean needToFindAntedote = false;
	public static void magic() {
		double magicWorks = Math.random();
		if (magicWorks >= 0.5) {
			System.out.println("the magic works! you eat your cookies along with a glass of milk and go to bed");
			ScenarioPicker.endGame=true;
			scenario4done = true;
		}
		else if (magicWorks < 0.5) {
			System.out.println("the magic went terribly wrong, in order to survive you must find the antedote. \ntype '2' '3' or '4'");
			scenario4done = true;
			Scenario2.scenario2done=true;
			needToFindAntedote = true;
		}
		else {
			System.out.println("something here is wrong, the code is broken");
		}
		Scenario2.cupboard1=false;
	}
	public static void noMagic() {
		System.out.println("you decided not to use magic, select a new cupboard to open by typing '2' '3' or '4'");
		scenario4done= true;
		Scenario2.cupboard1=false;
		Scenario2.scenario2done=false;
	
	}
}

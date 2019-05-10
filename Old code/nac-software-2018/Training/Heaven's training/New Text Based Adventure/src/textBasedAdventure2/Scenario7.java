package textBasedAdventure2;

public class Scenario7 {
	public static boolean scenario7done =false;
	public static void portal() {
		System.out.println("you enter the portal but the air in the other world is poisonous and you die");
		scenario7done=true;
		ScenarioPicker.endGame=true;
		Scenario2.cupboard4=false;
	}
	public static void noPortal() {
		System.out.println("you don't go into the portal, pick a new cupboard to open. type '1' '2' or '3'");
		scenario7done= true;
		Scenario2.scenario2done=false;
		Scenario2.cupboard4=false;
	
	}
}

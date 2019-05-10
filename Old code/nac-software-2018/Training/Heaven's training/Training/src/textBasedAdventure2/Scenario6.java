package textBasedAdventure2;

public class Scenario6 {
	public static boolean scenario6done =false;
	public static void store() {
		System.out.println("you tried to go to the store but got caught stealing the car and get grounded");
		scenario6done=true;
		ScenarioPicker.endGame=true;
	}
	public static void water() {
		System.out.println("you get some water and go to bed");
		scenario6done=true;
		ScenarioPicker.endGame=true;
	
	}
	public static void nothing() {
		System.out.println("you decide to go to bed thirst, however, you were so dehydrated that you died in your sleep");
		ScenarioPicker.endGame=true;
		scenario6done= true;
	
	}
}

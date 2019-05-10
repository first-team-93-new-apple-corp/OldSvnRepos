package textBasedAdventure2;

public class Scenario3 {
	public static boolean scenario3done =false;
	public static void bandaid() {
		System.out.println("you have covered your wound and can now continue on to go get cookies from the kitchen, would you like to open cupboard 1, 2 ,3 or 4? type '1' '2' '3' or '4'");
		scenario3done=true;
		Scenario1.pickFlashlight = true;
	}
	public static void noBandaid() {
		System.out.println("your wound got infected and you died");
		ScenarioPicker.endGame=true;
		Scenario1.scenario1done= false;
	
	}
}

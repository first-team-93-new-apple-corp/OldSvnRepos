package textBasedAdventure2;

public class Scenario1 {
	public static boolean scenario1done =false;
	public static boolean pickFlashlight = false;
	public static void flashlight(){
		System.out.println("you successfully walked down the stairs into the kitchen,\nwhich cupboard would you like to open? type '1' '2' '3' or '4'");
		scenario1done = true;
		Scenario3.scenario3done = true;
		pickFlashlight = true;
	}
	public static void noFlashlight(){
		System.out.println("you tripped on your brother's toy on the steps and fell, you notice you cut yourself,\nwould you like to get a bandaid or leave it be type '1' for a bandaid and '2' for leave it be");
		scenario1done = true;
	}
	public static void jumpOutWindow(){
		System.out.println("you died jumping out the window");
		ScenarioPicker.endGame = true;
		scenario1done = true;
	}
}

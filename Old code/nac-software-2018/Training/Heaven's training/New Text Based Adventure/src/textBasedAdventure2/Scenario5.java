package textBasedAdventure2;


public class Scenario5 {
	public static boolean scenario5done =false;
	public static boolean milkIsSpoiled = false;
	public static void milk() {
		double spoiledMilk = Math.random();
		if (spoiledMilk <= 0.5) {
			System.out.println("you go to grab the milk and realize it is spoiled, would you like to go to the store and get new milk,\nget some water, or go to bed thirsty? type '1 for go to the store '2' for get water or '3' for go to bed thirsty");
			scenario5done = true;
			milkIsSpoiled = true;
		}
		else if (spoiledMilk > 0.5) {
			System.out.println("you grab the milk and drink it, you then go back to bed");
			scenario5done = true;
			ScenarioPicker.endGame=true;
		}
		else {
			System.out.println("something here is wrong, the code is broken");
		}
		Scenario2.cupboard2=false;
	}
	public static void noMilk() {
		System.out.println("you decide not to drink the milk and go to bed, however,\nyou were so dehydrated that you died in your sleep");
		ScenarioPicker.endGame=true;
		scenario5done= true;
		Scenario2.cupboard2=false;
	
	}
}

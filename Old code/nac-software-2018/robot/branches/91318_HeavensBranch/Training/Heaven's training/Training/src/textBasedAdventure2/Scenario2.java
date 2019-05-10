package textBasedAdventure2;

public class Scenario2 {
	public static boolean scenario2done =false;
	public static boolean cupboard1 = false;
	public static boolean cupboard2 = false;
	public static boolean cupboard4 = false;
	
	public static void cupboard1(){
		System.out.println("you open the first cupboard and find a magic spell book, would you like to use it to make your cookies? type '1' for yes and '2' for no");
		scenario2done = true;
		cupboard1=true;
	}
	public static void cupboard2(){
		System.out.println("you open the second cupboard and find the cookies, would you like to eat them with out without milk? type '1' for milk and '2' for no milk");
		scenario2done = true;
		cupboard2=true;
	}
	public static void cupboard3(){
		System.out.println("you open the third cupboard and find an antedote, which cupboard would you like to open next? type '1' '2' or '4'");
	}
	public static void cupboard4(){
		System.out.println("you open the fourth cupboard and find a portal to another world, do you enter? type '1' for yes and '2' for no");
		scenario2done = true;
		cupboard4=true;
	}
	public static void cupboard5(){
		System.out.println("you opened cupboard 5 and now you lost the game, congratulations, open a new cupboard to continue");
	}
	public static void cupboard6(){
		System.out.println("you opened cupboard 6 and find the TARDIS, you travel all of time and space forgetting about cookies");
		scenario2done=true;
		ScenarioPicker.endGame = true;
	}
}


/*
 * This class contains the main function.
 */
public class Main {

	/*
	 * This is the main function. It is the entry point to our code: every program starts
	 * executing at the first line of main.
	 */
	public static void main(String[] args) {
		// This is the usual way we print text and data to the output window
		System.out.println("We have some people here, can you introduce yourselves?");
		
		// Create a Person object for Dave
		Person dave = new Person();
		dave.name = "Dave";
		dave.age = 43;
		dave.runningSpeed = 4.3;
		
		// Create a Person object for Tonilia
		Person tonilia = new Person();
		tonilia.name = "Tonilia";
		tonilia.age = 21;
		tonilia.runningSpeed = 10.5;
		
		// Call their methods so they introduce themselves
		dave.introduceYourself();
		tonilia.introduceYourself();
		
		System.out.println("Excellent, now EVERYONE RACE TO THE FINISH!!!");
		double distance = 40.0;
		
		// Tell them how far they have to run and get the time it takes each of them
		double daveTime = dave.run(distance);
		double toniliaTime = tonilia.run(distance);
		
		// Check to see who finished first
		if (daveTime < toniliaTime)
		{
			System.out.print(dave.name);
			System.out.println(" wins!");
		}
		else if (daveTime > toniliaTime)
		{
			System.out.print(tonilia.name);
			System.out.println(" wins!");
		}
		else
		{
			System.out.println("It's a tie. That's anticlimactic.");
		}
	}

}

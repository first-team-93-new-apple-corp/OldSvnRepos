import java.util.Scanner;

public class Main {
	
	static boolean continueLoop = true;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner input = new Scanner(System.in);
		int optionChoice;
		
		//Creating the dog
		Dog dog = new Dog(35, "Black Lab", "Renold", "Black", 50, 75);
		
		
		System.out.println("Dog attributes:");
		System.out.println(dog.attributeCompiler());
		
		//Printing the new dog's given attributes
		
		while(continueLoop) {
		
		System.out.println(" ");
		System.out.println(" ");
			
		System.out.println("Dog experience: " + dog.dogExperience);
		System.out.println("Dog level: " + dog.dogLevel);
		System.out.println("XP until next level: " + (dog.nextLevelXP - dog.dogExperience));
		System.out.println("XP per train: " + dog.dogXpPerTrain);
		System.out.println("Dog hunger(the lower the better): " + dog.dogHunger);

		System.out.println("Would you like to train(1), speak(2), sit(3), feed(4), or quit(5)?");
		optionChoice = Integer.parseInt(input.nextLine()); 
		
		if(optionChoice == 4) {
			
			dog.feed();
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("You fed your dog. It will now listen to you!");
			
		}
		
		if(dog.dogHunger >= dog.maxDogHunger) {
			
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("You dog is very hungry, and won't listen to you until you feed it.");
			
		}
		
		if(optionChoice == 1 && dog.dogHunger < dog.maxDogHunger) {
			
			System.out.println(" ");
			System.out.println(" ");
			dog.train();
			dog.increaseHunger();
		
		}
		
		if(optionChoice == 2 && dog.dogHunger < dog.maxDogHunger) {
		
			System.out.println(" ");
			System.out.println(" ");
			dog.speak();
			
		}
		
		if(optionChoice == 3 && dog.dogHunger < dog.maxDogHunger) {
			
			System.out.println(" ");
			System.out.println(" ");
			dog.sit();

			}
		
		if(optionChoice == 5) {
			
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("You left your dog at: ");
			System.out.println("Dog experience: " + dog.dogExperience);
			System.out.println("Dog level: " + dog.dogLevel);
			System.out.println("XP until next level: " + (dog.nextLevelXP - dog.dogExperience));
			System.out.println("XP per train: " + dog.dogXpPerTrain);
			input.close();
			quit();
			
		}
		
		}
		
	}

	static void quit() {
		
		continueLoop = false;
		
	}
	
}


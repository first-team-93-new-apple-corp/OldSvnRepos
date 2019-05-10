//Importing java packages
import java.awt.Point;

public class Dog {	
	
	//Declaring variables
	int weight;
	String breed;
	String name;
	String color;
	Point position;
	String dogAttributes; 
	public int dogExperience = 0;
	public int dogLevel = 0;
	public int dogXpPerTrain = 10;
	public int nextLevelXP = 100;
	public int dogHunger = 0;
	public int maxDogHunger = 100;
	
	//Dog constructor
	Dog(int consWeight, String consBreed, String consName, String consColor, int x, int y) {
		
		weight = consWeight;
		breed = consBreed;
		name = consName;
		color = consColor;
		position = new Point(x, y);
	}
	
	//Compiles all dog attributes into one string
	public String attributeCompiler() {
		
		dogAttributes = "Weight: " + new Integer(weight).toString() + ", Breed: " + breed + ", Name: " + name + ", Color: " + color + ", Position X: " + position.x + ", Position Y: " + position.y;
		return dogAttributes;
		
	}
	
	public void speak() {
		if(dogLevel >= 5) {
			if (this.weight <= 8) {
			
				System.out.println("Yip-Yip-Yip");
			}
			if (this.weight > 8 && this.weight <= 20) {
			
				System.out.println("Bark-Bark");
			}
			if (this.weight > 20 && this.weight <= 40) {
			
				System.out.println("Ruff-Ruff");
			}
			if (this.weight > 40) {
			
				System.out.println("Woof-Woof");
			}
			
			System.out.println("Your dog spoke");
			
		}
		
		if(dogLevel < 5) {
			
			System.out.println("Sorry. Your dog must be level 5 to preform this task. Your dog is only level " + dogLevel + ".");
			
		}
		
	}
	
	public void levelUp() {
	
		dogLevel += 1;
		dogExperience -= 100;
		dogXpPerTrain *= 1.3;
		nextLevelXP *= 1.7;
		maxDogHunger *= 1.3;
		System.out.println("  _      ________      ________ _        _    _ _____  _ \r\n" + 
				" | |    |  ____\\ \\    / /  ____| |      | |  | |  __ \\| |\r\n" + 
				" | |    | |__   \\ \\  / /| |__  | |      | |  | | |__) | |\r\n" + 
				" | |    |  __|   \\ \\/ / |  __| | |      | |  | |  ___/| |\r\n" + 
				" | |____| |____   \\  /  | |____| |____  | |__| | |    |_|\r\n" + 
				" |______|______|   \\/   |______|______|  \\____/|_|    (_)\r\n" + 
				"                                                         \r\n" + 
				"                                                         ");
		System.out.println("Your dog leveled up! It is now level " + dogLevel + ". It will take " + nextLevelXP + " experience to level up next!");
		
	}
	
	public void train() {
		
		dogExperience += dogXpPerTrain;
		System.out.println("You trained your dog for " + dogXpPerTrain + " experience. It is now at " + dogExperience + " experience.");

		if (dogExperience >= nextLevelXP) {
			
			levelUp();
			
		}
		
	}
	
	public void sit() {
		
		if(dogLevel >= 10) {
			
			System.out.println("The dog sat. Good job.");
			
		}
		
		if(dogLevel < 10) {
			
			System.out.println("Sorry. Your dog must be level 10 to preform this task. Your dog is only level " + dogLevel + ".");
			
		}
		
	}
	
	public void increaseHunger() {
		
		dogHunger += (0.5*dogXpPerTrain);
		
	}
	
	public void feed() {
		
		dogHunger = 0;
		
	}
	
}


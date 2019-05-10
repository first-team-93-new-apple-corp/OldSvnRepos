import java.awt.Point;

public class Dog {

	public int weight;
	public String breed;
	public String name;
	public String color;
	public Point position;
	public int experience;
	public int hunger;
	
	public Dog(int weight, String breed, String name, String color, Point position) {
		this.weight = weight;
		this.breed = breed;
		this.name= name;
		this. color = color;
		this.position = position;
		experience = 0;
		hunger = 0;
	}
	
	public Dog(int weight, String breed, String name, String color) {
		this.weight = weight;
		this.breed = breed;
		this.name= name;
		this. color = color;
		position = new Point (0,0);
		experience = 0;
		hunger = 0;
	}
	
	public String attributes() {
		String attributes;
		attributes = name + ": " + weight +" lbs, " + breed + ", " +  color;
		return attributes;
	}
	
	public void move(int x, int y) {
		position = new Point (x,y);
	}
	
	public void speak() {
		if(experience >= 50 && hunger <=40) {
			if(weight <= 8) {
				System.out.println(name + " says yip-yip-yip");
			}
			else if(weight > 8 && weight <= 20){
				System.out.println(name + " says bark-bark");
			}
			else if (weight > 20 && weight <= 40) {
				System.out.println(name + " says ruff-ruff");
			}
			else if (weight > 40) {
				System.out.println(name + " says woof-woof");
			}
			hunger += 5;
		}
		else if (experience < 50) {
			System.out.println("sorry, your dog must have 50 experience points to do this");
		}
		else {
			System.out.println("sorry, you must feed your dog to do this");
		}
	}
	
	public void train() {
		if (hunger <= 40) {
			experience = experience + 10;
			hunger += 5;
		}
		else {
			System.out.println("sorry, you must feed your dog to do this");
		}
	}
	
	public void shake() {
		if(experience >= 100 && hunger < 40) {
			System.out.println("your dog shook your hand");
			hunger += 5;
		}
		else if (experience < 100) {
			System.out.println("sorry, your dog must have 100 experience points to do this");
		}
		else {
			System.out.println("sorry, you must feed your dog to do this");
		}
	}
	
	public void lightningBolt() {
		if(experience >= 200 && hunger < 40) {
			System.out.println("             zeeeeee-\r\n" + 
					"            z$$$$$$\"\r\n" + 
					"           d$$$$$$\"\r\n" + 
					"          d$$$$$P\r\n" + 
					"         d$$$$$P\r\n" + 
					"        $$$$$$\"\r\n" + 
					"      .$$$$$$\"\r\n" + 
					"     .$$$$$$\"\r\n" + 
					"    4$$$$$$$$$$$$$\"\r\n" + 
					"   z$$$$$$$$$$$$$\"\r\n" + 
					"   \"\"\"\"\"\"\"3$$$$$\"\r\n" + 
					"         z$$$$P\r\n" + 
					"        d$$$$\"\r\n" + 
					"      .$$$$$\"\r\n" + 
					"     z$$$$$\"\r\n" + 
					"    z$$$$P\r\n" + 
					"   d$$$$$$$$$$\"\r\n" + 
					"  *******$$$\"\r\n" + 
					"       .$$$\"\r\n" + 
					"      .$$\"\r\n" + 
					"     4$P\"\r\n" + 
					"    z$\"\r\n" + 
					"   zP\r\n" + 
					"  z\"");
			hunger += 5;
		}
		else if (experience < 200) {
			System.out.println("sorry, your dog must have 200 experience points to do this");
		}
		else {
			System.out.println("sorry, you must feed your dog to do this");
		}
	}
	
	public void fireball() {
		if(experience >= 300 && hunger < 40) {
			System.out.println("       .\r\n" + 
					"      .M\r\n" + 
					"     ,MM\r\n" + 
					"     MM:\r\n" + 
					" .   YMM,\r\n" + 
					" M   'MMM,     ,\r\n" + 
					" M.   `MMM    .M\r\n" + 
					" MM,  ,MMM   ,MM\r\n" + 
					" \"MM, MMM'  ,MM'\r\n" + 
					" ,MMM.MMMMM.MMM,\r\n" + 
					" MMMMMMMMMMMMMMM\r\n" + 
					" MMMMMMMMMMMMMMM\r\n" + 
					" 'MMMMMMMMMMMMM'\r\n" + 
					"   \"\"\"\"\"\"\"\"\"\"\"");
			hunger += 5;
		}
		else if (experience < 300) {
			System.out.println("sorry, your dog must have 300 experience points to do this");
		}
		else {
			System.out.println("sorry, you must feed your dog to do this");
		}
	}
	
	public void feedDog() {
		hunger = 0;
	}
}
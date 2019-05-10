import java.awt.Point;

public class Dog {

	int weight;
	String breed;
	String name;
	String color;
	Point position; 
	

	public Dog(int weight, String breed, String name, String  color, Point position ) {
		this.weight = weight;
		this.breed = breed;
		this.name = name;
		this.color = color;
		this.position = position;		
	}
	
	public Dog(int weight, String breed, String name, String color) {
		this.weight = weight;
		this.breed = breed;
		this.name = name;
		this.color = color;
		this.position = new Point(0,0);
		
	}
	 
	public String toString() {
		String dogString = "weight: "+weight+" breed: "+breed+" name: "+name+" color: "+color+" position x: "+position.x+" postition y: "+position.y;
		return dogString;
		
	}
	public void Move(int x, int y) {
		position.translate(x,y);
		
	}
	
	public void Speak() {
		if (weight <=8 ) {
			System.out.println("Yip-Yip-Yip");
			
		} else if (8 < weight && weight <= 20) {
			System.out.println("Bark-Bark");
			
		} else if (weight >20 && weight <= 40) {
			System.out.println("Ruff-Ruff");
			
		} else if (weight > 40 ) {
			System.out.println("Woof-Woof");
		}
		}
	

	
}

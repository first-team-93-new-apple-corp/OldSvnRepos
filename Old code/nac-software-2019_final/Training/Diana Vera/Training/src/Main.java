
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Dog Maggie = new Dog(22, "corgi", "Maggie", "brown");
		Maggie.speak();
		
		Dog Spot = new Dog(5, "chihuahua", "Spot", "brown");
		Spot.speak();
		
		Dog Duke = new Dog(45, "pitbull", "Duke", "black");
		Duke.speak();
		
		Dog Pal = new Dog(15, "terrier", "Pal", "yellow");
		Pal.speak();
		
		Pal.move(4, 6);
		System.out.println("Pal moves to " + "(" + Pal.position.x + "," + Pal.position.y + ")");
	}

}

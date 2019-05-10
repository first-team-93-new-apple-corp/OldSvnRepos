import java.awt.Point;
public class Main {

	public static void main(String[] args) {

		Dog Dog1 = new Dog ("Lab",20,"Joe", "Black", new Point (0 , 0));
		
		
		Dog1.Move ( 1, 1 );
		
		System.out.println("My dog says: " + Dog1);
		Dog1.Speak();
	}
}

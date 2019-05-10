import java.awt.Point;

public class Dog {

	private String Dogbreed;
	private int  weight;
	private String Name;
	private String Color;
	private Point position; 
	private int Exp = 25; 

	public Dog(String myBreed, int myweight, String myName, String mycolor,Point dogposition) {
		Dogbreed = myBreed;
		weight = myweight;
		Name = myName;
		Color = mycolor;
		position = dogposition; 
	}

	public String toString() {
		return "My Name is " + Name + " and I am a " + Color + " " + Dogbreed + " and im at " + position.x + "," + position.y;
	}


	public void Speak( ) {


		if (Exp >= 50) {
		
		 else if (Exp < 50)
				System.out.println(" It goes chases a squirrl");
		
			if (weight <= 8 )
			{
				System.out.println("yip-yip-yip " );
			}
			else if  (weight <= 20)
			{
				System.out.println("Bark-Bark");
			}
			else if ( weight <= 40)
			{
				System.out.println("Ruff-Ruff ");
			}
			else if (weight <=40)
			{
				System.out.println("Woof-Woof");
			}
			else if (weight >= 1000)
			{
				System.out.println("Why do you do this to me father?");
			}
		}
	}
	public void Move ( int x , int y ) {
		int newX = position.x + x;
		position.x = newX;
		int newY = position.y + y;
		position.y = newY;
	}

}
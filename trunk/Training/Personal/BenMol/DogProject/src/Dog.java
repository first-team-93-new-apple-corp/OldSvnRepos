import java.awt.Color;
import java.awt.Point;

public class Dog {
	private int weight;
	private String breed;
	private String name;
	private Color color;
	private Point position;
	
	public Dog(int myWeight, String myBreed, String myName, Color myColor, Point myPosition) {
		weight = myWeight;
		breed = myBreed;
		name = myName;
		color = myColor;
		position = myPosition;
	}
	
	public String toString() {
		return "I am a " + color.toString() + " " + breed + " and my name is " + name;
	}
	
	public void Move(int x, int y) {
		position.x += x;
		position.y += y;
	}
	
	public void Speak() {
		if (weight <= 8)
		{
			System.out.println("Yip Yip!");
		}
		else if (weight <= 20)
		{
			System.out.println("Bark Bark!");
		}
		else if (weight <= 40)
		{
			System.out.println("Ruff Ruff!");
		}
		else
		{
			System.out.println("Woof Woof!");
		}
	}
}


/*
 * Classes are the primary thing that all your code will be structured around. They define a collection
 * of data and behavior that together represents an independent actor in the system. It can store information, 
 * control access to that information, perform tasks, or anything else that you want it to do.
 * Classes are like recipes: a recipe contains all the information you need to make a dish, but you
 * can't eat a recipe. You have to use the recipe to make the food. Likewise, a class is a recipe, and
 * it is used to create objects, also called class instances. In this way, we define one class that
 * contains the recipe for a Person, and we can use it to create many Person objects to represent 
 * different people. A class doesn't do anything on its own, you have to make an object of the class
 * before it will do anything.
 */

/*
 * This is the Person class. It contains all the information about a person, and
 * it defines methods that represent that person's possible actions.
 */
public class Person {
	
	/*
	 * Classes can contain variables, which store information. Each variable has a type
	 * that determines what kind of information can be stored in that variable.
	 */
	
	// Note that each of the variables below is initialized with a default value to be overwritten.
	// This is not required, but is often a good idea.
	
	// Strings are for text, so this String contains their name.
	public String name = "";
	
	// Ints are for integers; they can only contain whole numbers with no decimal or fractional part.
	// This int contains their age.
	public int age = 0;
	
	// Doubles are for non-whole numbers; they can contain any number with or without a fractional part.
	// This double contains their top running speed in meters per second.
	public double runningSpeed = 0.0;
	
	/*
	 * Classes can also contain methods, which provide behavior. A method is a way to block off
	 * a small section of code with a name, so that when you want to execute the code in the block, you
	 * just call it by name. The methods of an object can access all the variables and other methods of
	 * the object as well.
	 */
	
	// This method tells the person to introduce themselves using their personal information
	public void introduceYourself()
	{
		System.out.print("Hi, my name is ");
		System.out.print(name);
		System.out.print(" and I am ");
		System.out.print(age);
		System.out.println(" years old!");
	}
	
	/*
	 * This method tells the person to run a certain distance and report their finish time.
	 * The method is given a double containing the distance to run (in meters) and it gives a 
	 * double containing the time it took them (in seconds) back to the code that called this
	 * function. The variables given to this function are called parameters or arguments, and
	 * the value given back to the calling function when it "returns" to it is called a "return value".
	 */
	public double run(double distance)
	{
		// Divide the distance by their running speed to find the time it takes.
		double time = distance / runningSpeed;
		return time;
	}
}

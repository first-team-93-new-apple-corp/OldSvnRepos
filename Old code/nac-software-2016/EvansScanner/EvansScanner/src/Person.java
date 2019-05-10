/**
 * Person
 * The program's representation of each person, signed in or signed out.
 * @author Evans Chen
 * 
 * Stores the data on the person's name, ID, total logged time, last time signed in, and whether they are signed in or not.
 */
public class Person {
	
	/**
	 * The name of the person.
	 */
	public String name = "DEFAULT_NAME";
	
	/**
	 * The total time logged for the person.
	 */
	public long time = 0;
	
	/**
	 * The timestamp of the last time the person signed in.
	 * Used to calculate total time to log for each session the user is signed in.
	 */
	public long last_time = 0;
	
	/**
	 * The person's ID.
	 */
	public int id = 0;
	
	/**
	 * Whether or not the person is signed in.
	 */
	public boolean active = false;
	
	/**
	 * The delimiter to use for the toString method.
	 */
	public String delimiter = ",";
	
	/**
	 * The full constructor of the person.
	 * @param name The person's name.
	 * @param id The person's ID
	 * @param time The person's total time logged
	 * @param last_time The last time the person signed in
	 * @param active Whether the user is signed in or not.
	 */
	public Person(String name, int id, long time, long last_time, boolean active)
	{
		this.name = name;
		this.id = id;
		this.time = time;
		this.last_time = last_time;
		this.active = active;
		
		delimiter = ",";
	}
	
	/**
	 * Constructs a person just from name and ID, initializing all other fields to defaults.
	 * @param name
	 * @param id
	 */
	public Person(String name, int id)
	{
		this(name, id, 0L, System.currentTimeMillis(), false);
	}
	
	/**
	 * Signs the person in.
	 */
	public void signIn()
	{
		//On signing in...
		//Set the person's in time.
		last_time = System.currentTimeMillis();
		//Set active to true.
		active = true;
		//Print output
		System.out.println("Hello, " + name.split(" ")[0] + ".");
		System.out.println("Thank you for coming today.");
		System.out.println("You are now signed in.");
		System.out.println();
	}
	
	/**
	 * Signs the person out.
	 */
	public void signOut()
	{
		//On signing out...
		System.out.println("Hello again, " + name.split(" ")[0] + ".");
		System.out.println("Thank you for your work.");
		
		//Find the amount of time signed in.
		long total = System.currentTimeMillis() - last_time;
		//If the amount of time is valid...
		if (validateTime(total))
		{
			//Add it to the total time.
			time += total;
		}
		else
		{
			//If time is invalid, user must have forgotten to sign out.
			System.out.println("You forgot to sign out last time, so your time was been counted.");
			System.out.println("Please remember to sign out when you leave next time.");
			//That session is not counted.
			time += 0;
		}
		//Set active to false.
		active = false;
		//Print output
		
		System.out.println("You have worked a total of " + TypeConverter.TimeToString(time) + " so far.");
		System.out.println("You are now signed out.");
		System.out.println();
	}
	
	/**
	 * Converts the person to a string using the delimiter.
	 */
	public String toString()
	{
		return toString(delimiter);
	}
	
	/**
	 * Converts the person to a string using a specified delimiter.
	 * @param delimiter The delimiter to use.
	 * @return
	 */
	public String toString(String delimiter)
	{
		return (name + delimiter + id + delimiter + time + delimiter + last_time + delimiter + TypeConverter.BooleanToInteger(active));
	}
	
	public boolean validateTime(long ms)
	{
		long ms_to_hours = 3600000;
		double hours = (double)ms / (double)ms_to_hours;
		return (hours < 8.0);
	}
}

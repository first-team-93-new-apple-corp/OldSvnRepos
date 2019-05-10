import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScannerMain {
	
	/**
	 * The NameLinker links names to ID's.
	 */
	static NameLinker namelinker;
	
	/**
	 * The program's representations of the peoples' information.
	 */
	static Persons persons;
	
	/**
	 * The method that runs.
	 * @param args
	 */
	public static void main(String args[])
	{
		namelinker = new NameLinker("./linker.txt");
		persons = new Persons("./persons.txt");
		
		InputStreamReader r = new InputStreamReader(System.in);  
		BufferedReader br = new BufferedReader(r);
		while(true)
		{
			String id = "";
			try
			{
				id = br.readLine();
			}
			catch (IOException e) 
			{
				System.out.println("Reader failed.");
			}
			
			//If an input has been received...
			//Process it.
			processID(id, namelinker, persons);
		}
	}
	
	/**
	 * Processes a string of input.
	 * @param input The input to process.
	 * @param namelinker The NameLinker object to use.
	 * @param persons The Persons object to use.
	 */
	private static void processID(String input, NameLinker namelinker, Persons persons)
	{
		//If an input has been received...
		String id = null;
		if (validID(input.replaceAll("\\D+",""), namelinker))
		{
			id = input.replaceAll("\\D+","");
		}
		else if (validName(capitalizeAll(input), namelinker))
		{
			id = namelinker.getID(capitalizeAll(input)).toString();
		}
		if (id != null)
		{
			int ID = TypeConverter.StringToInt(id);
			String name = namelinker.getName(ID);
			//if the ID is valid...
			//If the person does not yet exist in the list of persons, then create it.
			if (!persons.contains(ID))
			{
				//Create and add the person
				Person p = new Person(name, ID);
				persons.addPerson(p);
			}
			
			///Process the person.
			Person person = persons.getPerson(ID);
			//If the person is not signed in yet...
			if (!person.active)
			{
				//Sign them in.
				person.signIn();
			}
			//If the person is already signed in...
			else
			{
				//Sign them out.
				person.signOut();
			}
			
			//Save the current state.
			persons.save();
		}
		else
		{
			System.out.println("Invalid Name or ID entered. Please try again.");
			System.out.println();
		}
	}
	
	/**
	 * Checks to see if the input is a valid ID.
	 * @param s The input to check.
	 * @param n The NameLinker object to use.
	 * @return Whether or not the input is valid as an ID.
	 */
	private static boolean validID(String s, NameLinker n)
	{
		//The string cannot be null
		if (s == null)
		{
			return false;
		}
		//The string cannot be empty.
		if (s == "")
		{
			return false;
		}
		//The string must be convertable to an integer.
		try
		{
			Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		//The integer must be an ID number in the NameLinker.
		if (n.getName(TypeConverter.StringToInt(s)) == null)
		{
			return false;
		}
		//If all conditions are met, then it is a valid ID string.
		return true;
	}
	
	/**
	 * Checks to see if the input is valid as a name.
	 * @param s The input to check
	 * @param n The NameLinker to use.
	 * @return Whether or not the input is valid as a name.
	 */
	private static boolean validName(String s, NameLinker n)
	{
		//The name cannot be null or empty.
		if (s == null)
		{
			return false;
		}
		if (s == "")
		{
			return false;
		}
		//The name must exist in the namelinker.
		if (n.getID(s) == null)
		{
			return false;
		}
		//The linked ID must be valid.
		return validID(n.getID(s).toString(), n);
	}
	
	/**
	 * String manipulation function to capitalize the first char of a string.
	 * @param s The String to capitalize the first letter of
	 * @return The String with the first letter capitalized.
	 */
	private static String capitalize(String s)
	{
		if (s == null)
		{
			return null;
		}
		String str = s.toLowerCase();
		if (str.length() < 2)
		{
			return str.toUpperCase();
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	/**
	 * String manipulation function to capitalize the first char of every word of a string.
	 * @param s The String to capitalize the first letter of every word of.
	 * @return The String with the first letter capitalized for every word.
	 */
	private static String capitalizeAll(String s)
	{
		String str[] = s.split(" ");
		for (int i = 0; i < str.length; i++)
		{
			str[i] = capitalize(str[i]);
		}
		return TypeConverter.join(str, " ");
	}
}
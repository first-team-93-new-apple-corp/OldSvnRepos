import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Persons
 * The class that contains the program's representation of each person and their info.
 * @author Evans Chen
 * 
 * This class reads a text file of the format:
 * [Name],[ID],[Time logged (ms)],[Last signed in timestamp (ms)],[Signed in (1) or out (0)]
 * 
 * John Doe,1000123456,54839,1481845153932,0
 * Jane Doe,1000123457,5630995,1481845153932,1
 * 
 * This class loads this data into a map of Person objects.
 */
public class Persons {
	
	/**
	 * This String stores the filepath that the NameLinker reads from.
	 */
	String file;
	
	/**
	 * The map that links IDs to Person objects.
	 */
	HashMap<Integer, Person> personMap;
	
	/**
	 * The delimiter to use for saving and loading operations.
	 */
	public String delimiter = ",";
	
	/**
	 * The constructor for the Persons object.
	 * It simply loads from the specified filepath.
	 * @param file The filepath to load from.
	 */
	public Persons(String file)
	{
		load(file, ",");
	}
	
	/**
	 * Accesses the personMap and gives the specified person from an ID.
	 * @param ID The ID to find the linked person of.
	 * @return The Person object with the specified ID.
	 */
	public Person getPerson(int ID)
	{
		return personMap.get(ID);
	}
	
	/**
	 * Checks to see if the personMap already contains a certain ID.
	 * @param ID The ID to check for.
	 * @return Whether or not the ID exists in the personMap.
	 */
	public boolean contains(int ID)
	{
		return (getPerson(ID) != null);
	}
	
	/**
	 * Adds a person object to the personMap.
	 * @param p The Person object to add.
	 */
	public void addPerson(Person p)
	{
		if (personMap.containsKey(p.id))
		{
			personMap.replace(p.id, p);
		}
		else
		{
			personMap.put(p.id, p);
		}
	}
	
	/**
	 * Validates a line when reading.
	 * @param s The string to validate.
	 * @return Whether the string is valid or not.
	 */
	private boolean validateLine(String s)
	{
		if (s == null)
		{
			return false;
		}
		if (s == "")
		{
			return false;
		}
		if (s == "\n")
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Saves the personMap to a file, which can later be loaded.
	 */
	public void save()
	{
		save(file);
	}
	
	/**
	 * Saves the personMap to a specified file, which can later be loaded.
	 * @param fname The filepath to save to.
	 */
	public void save(String fname)
	{
		//Write contents to a file.
		try
		{
			OutputStream out = Files.newOutputStream(Paths.get(fname), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
			
			String newlineStr = System.getProperty("line.separator");
			byte[] eol = newlineStr.getBytes();
			
			//iterate over the list of people.
			for (Integer ID : personMap.keySet()) {
			    Person p = getPerson(ID);
			    //Format is: NAME,ID,TOTAL_TIME,LAST_TIME,ACTIVE
			    String personStr = p.toString(delimiter);
			    out.write(personStr.getBytes(), 0, personStr.getBytes().length);
			    out.write(eol, 0, eol.length);
			}
			
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads data from the text file.
	 */
	public void load()
	{
		load(file, delimiter);
	}
	
	/**
	 * Loads from a specified file, with a specified delimiter.
	 * @param fname The filepath to use.
	 * @param delimiter The delimiter to use when reading.
	 */
	public void load(String fname, String delimiter)
	{
		this.file = fname;
		//Initialize the linking map
		personMap = new HashMap<Integer, Person>();
		
		this.delimiter = delimiter;
		
		///Try to read from the file.
		Path path = Paths.get(file);
		BufferedReader br = null;
		//Attempt to create a new BufferedReader.
		try
		{
			br = new BufferedReader(new FileReader(file));
		}
		//If the file doesn't exist...
		catch (FileNotFoundException e)
		{
			//Create the file if it doesn't exist.
			try
			{
				OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				out.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		
		//If the BufferedReader exists,
		if (br != null)
		{
			//Iterate over the file, reading the names, ids, and their info, saving it to a map of people.
			try
			{
				for (String line = br.readLine(); validateLine(line); line = br.readLine())
				{
					try
					{
						String[] personStr = line.split(",", 5);
						//Format is: NAME,ID,TOTAL_TIME,LAST_TIME,ACTIVE
						String name = personStr[0];
						int id = TypeConverter.StringToInt(personStr[1]);
						long time = TypeConverter.StringToLong(personStr[2]);
						long last_time = TypeConverter.StringToLong(personStr[3]);
						boolean active = TypeConverter.StringToBoolean(personStr[4]);
						personMap.put(id, new Person(name, id, time, last_time, active));
					}
					catch (ArrayIndexOutOfBoundsException e)
					{
						System.out.println("File " + file + " contains invalid input: " + line);
					}
				}
			}
			catch (IOException e)
			{
				System.out.println("Reading of file " + file + " failed.");
			}
		}
		
		//Close the buffered reader.
		try
		{
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

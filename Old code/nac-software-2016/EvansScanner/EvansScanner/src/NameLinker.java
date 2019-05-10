import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Name Linker
 * @author Evans Chen
 * 
 * This class is designed to read a text file of format:
 * [ID],"[Name]"
 * 
 * 1000123456,"John Doe"
 * 1000123457,"Jane Doe"
 * 
 * Then, getName(int ID) and getID(String name) are used to access the data.
 */
public class NameLinker {

	/**
	 * This String stores the filepath that the NameLinker reads from.
	 */
	String file;
	
	/**
	 * This HashMap maps Names to IDs.
	 */
	public HashMap<String, Integer> names;
	
	/**
	 * The constructor for the NameLinker.
	 * @param file The filepath to read from.
	 */
	public NameLinker(String file)
	{
		//Initialize the linking map
		names = new HashMap<String, Integer>();
		
		///Try to read from the file.
		BufferedReader br = null;
		//Attempt to create a new BufferedReader.
		try
		{
			br = new BufferedReader(new FileReader(file));
		}
		//If the file doesn't exist...
		catch (FileNotFoundException e)
		{
			System.out.println("File " + file + " was not found.");
		}
		
		//If the BufferedReader exists,
		if (br != null)
		{
			//Iterate over the file, reading the names and ID's and putting them to a map.
			try
			{
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					String[] person = line.split(",", 2);
					String name = person[1].replaceAll("\"","");
					int id = TypeConverter.StringToInt(person[0]);
					names.put(name, id);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
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
	
	/**
	 * Gets an ID from a name.
	 * @param name The name to find the linked ID of.
	 * @return The ID linked to the name.
	 */
	public Integer getID(String name)
	{
		return names.get(name);
	}
	
	/**
	 * Gets a name from an ID.
	 * @param ID The ID to find the linked name of.
	 * @return The Name linked to the ID.
	 */
	public String getName(int ID)
	{
		return getKeyByValue(names, ID);
	}
	
	/**
	 * Get a Key using a Value in a map.
	 * @param map The map
	 * @param value The Value to get the Key of.
	 * @return The Key linked to the value.
	 */
	private <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
}

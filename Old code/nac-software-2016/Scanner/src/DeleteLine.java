import static java.nio.file.StandardOpenOption.*;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;

public class DeleteLine
{
	//This program is my way of removing names from one of the text files.
	//As far as I can tell, java doesn't have a nice 'delete line of text' command,
	//so we copy every line that isn't the line we want deleted from the first
	//document, write it to a new file, delete the first file, and rename the new
	//file.  This is used for activelist.txt and timeresults.txt when a student
	//signs in or out multiple times.
	public static void DeleteName(String name, String filename, String tempname)
	{
		Path oldpath = Paths.get(filename);
		Path newpath = Paths.get(tempname);
		String ENDL = System.getProperty("line.separator");
	    byte lineSeparator[] = ENDL.getBytes();
	    
	    //We're going to read all of the lines on activelist.txt
	    try (BufferedReader activeReader = new BufferedReader(new FileReader(filename)))
		{
	    	String readLine = activeReader.readLine();
			while(readLine != null)
			{
				String[] splitLine = readLine.split("\t");
				if(!splitLine[0].equals(name))
				{
					try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(newpath, CREATE, APPEND)))
					{
						byte[] writeLine = readLine.getBytes();
						out.write(writeLine);
						out.write(lineSeparator);
						out.close();
					}
					catch (IOException e)
					{
						System.err.println(e);
					}
				}
				readLine = activeReader.readLine();
			}
		}
	    //This catch prevents the program from trying to read a file that doesn't exist
		catch (IOException e)
		{
			System.err.println("File does not exist.  Cannot delete name.");
		}
		
		//This section deletes the old file
	    try
	    {
			Files.delete(oldpath);
		}
	    catch (IOException e)
	    {
			e.printStackTrace();
		}
	    
		
		//This section renames the new file to the old name
		File newName = new File(filename);
		File oldName = new File(tempname);
		boolean success = oldName.renameTo(newName);
		//If the file was properly renamed
		if(success)
		{
			System.out.println(filename +  " file renamed");
		}
		else
		{
			System.out.println("Failed to rename file " + filename);
		}
	}
	
	
	
	
	//This program returns the total time recorded previously.  It is called before we delete/replace
	//a line on timeresults.txt so that we have a cumulative time stored.
	public static double getTime(String name, String path)
	{
		//set timestored to a default value to avoid null pointers
		double timestored = 0;
		//We're going to read every line on timeresults.txt
	    try (BufferedReader timeReader = new BufferedReader(new FileReader(path)))
	    {
	    	String readLine = timeReader.readLine();
	    	while(readLine != null)
	    	{
	    		String[] splitLine = readLine.split("\t");
	    		//If the name we're looking for is the name we've read
	    		if(splitLine[0].equals(name))
	    		{
	    			//We set our variable to the time value associated with that name
	    			String timestring = splitLine[1];
	    			timestored = Double.parseDouble(timestring);
	    		}
	    		readLine = timeReader.readLine();
	    	}
	    	//Closing the program prevents problems when deleting or renaming files later on
	    	timeReader.close();
	    }
	    catch (FileNotFoundException e)
	    {
			e.printStackTrace();
		}
	    catch (IOException e)
	    {
			e.printStackTrace();
		}
	    
	    //We'll return the time value we found earlier
		return timestored;
	}
}
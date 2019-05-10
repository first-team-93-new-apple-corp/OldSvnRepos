package org.usfirst.frc.team93.robot.Utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This program reads a text file line by line and print to the console. It uses
 * FileOutputStream to read the file.
 * 
 */
public class FileInput
{

	String fileContents = null;
	File file;
	FileInputStream fileInput = null;
	BufferedInputStream bufferedInput = null;
	DataInputStream dataInput = null;

	public FileInput(String fileLocation)
	{
		new File(fileLocation);
	}

	public void load()
	{
		try
		{
			fileInput = new FileInputStream(file);

			// Here BufferedInputStream is added for fast reading.
			bufferedInput = new BufferedInputStream(fileInput);
			dataInput = new DataInputStream(bufferedInput);

			// dis.available() returns 0 if the file does not have more lines.
			while (dataInput.available() != 0)
			{
				// this statement reads the line from the file and print it to
				// the console.
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(dataInput));
				fileContents = bufferRead.readLine();
			}

			// dispose all the resources after using them.
			fileInput.close();
			bufferedInput.close();
			dataInput.close();

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
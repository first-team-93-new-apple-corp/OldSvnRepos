package org.usfirst.frc.team93.robot.Utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This program reads a text file line by line and print to the console. It uses
 * FileOutputStream to read the file.
 * 
 */
public class FileInput
{

	String fileContents = null;
	File fileLoad = null;
	FileInputStream fileInput = null;
	BufferedInputStream bufferedInput = null;
	DataInputStream dataInput = null;
	ArrayList<Double> list = new ArrayList<Double>();

	public FileInput(String fileLocation)
	{
		fileLoad = new File(fileLocation);
	}

	public void load()
	{
		try
		{
			fileInput = new FileInputStream(fileLoad);

			// Here BufferedInputStream is added for fast reading.
			bufferedInput = new BufferedInputStream(fileInput);
			dataInput = new DataInputStream(bufferedInput);

			// this statement reads the line from the file and print it to
			// the console.
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(dataInput));
			fileContents = bufferRead.readLine();
			String[] array = fileContents.split(",");
			for (int i = 0; i < array.length; i++)
			{
				// double element;
				// Double.parseDouble(fileContents);
				list.add(i, Double.valueOf(array[i]));
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

	public ArrayList<Double> getList()
	{
		return list;
	}
}
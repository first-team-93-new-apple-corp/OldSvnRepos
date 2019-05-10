import static java.nio.file.StandardOpenOption.*;

import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

public class DailyResults
{
	/**
	 * This program is used to create time result lists for every day so that mentors
	 * can see who was here on what days instead of only seeing how much time people
	 * were here for.  Each day will create a new text file with the date in the tile.
	 * 
	 * @param name
	 * 			The bytes of the name of the student that we are writing to the file
	 * 
	 * @param time
	 * 			The bytes of the time value that we are writing to the file (minutes)
	 */
	
	/*
	 * @codereview josh.hawbaker 1.4.18
	 * 
	 * This is one thing I remember wanting to do last year.  Hey, look at that, what a great learning opportunity.  Make the daily files automatically go into a folder based on the month so it's easy to navigate.
	 */
	static String timeStamp = new SimpleDateFormat("MMddyyyy").format(Calendar.getInstance().getTime());
	public static String dailyFile = "./DailyFiles/February/" + timeStamp + "SignInTimes.txt";
	public static void WriteDailyFile (String name, double time)
	{
		String endLine = System.getProperty("line.separator");
		byte[] newLine = endLine.getBytes();
		char tab = '\t';
		Path p = Paths.get(dailyFile);
		byte[] nameByte = name.getBytes();
		String timeString = Double.toString(time);
		byte[] writeTime = timeString.getBytes();
		boolean deleteLine = false;
		
		//This reader is to check and see if the student already has a time line on the daily file
		//If they do, it should delete the old line and make a new line with the total time
		try(BufferedReader dailyFileReader = new BufferedReader(new FileReader(dailyFile)))
		{
			String readLine = dailyFileReader.readLine();
			while(readLine != null)
			{
				String[] splitLine = readLine.split("\t");
				if(splitLine[0].equals(name))
				{
					//I don't know why it's so hard to convert a double to bytes / vise verse
					//so I keep doing it this weird way
					double previousTime = DeleteLine.getTime(name, dailyFile);
					double totalTime = previousTime + time;
					String totalTimeString = Double.toString(totalTime);
					writeTime = totalTimeString.getBytes();
					System.out.println("Previous Time " + previousTime);
					System.out.println("time to add" + time);
					System.out.println("total Time " + totalTime);
					GUIMain.printBox.append("Updated time total on " + dailyFile);
					deleteLine = true;
				}
				readLine = dailyFileReader.readLine();
			}
			dailyFileReader.close();
		}
		catch (IOException x)
		{
			System.err.println(x);
		}
		
		if(deleteLine)
			//If the program read through the daily file and found the student's name on it previously
			//It will write a new line with a cumulative daily time
		{
			DeleteLine.DeleteName(name, dailyFile, "tempDailyFile.txt");
		}
		
		try(OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, CREATE, APPEND)))
		{
			//We'll write the student's name and time signed in to the daily file, separated by tabs
			out.write(nameByte);
			out.write(tab);
			out.write(writeTime);
			out.write(newLine);
		}
		catch (IOException x)
		{
			System.err.println(x);
		}
	}
}

import static java.nio.file.StandardOpenOption.*;

import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

public class DailyResults
{
	/*
	 * This program is used to create time result lists for every day so that mentors
	 * can see who was here on what days instead of only seeing how much time people
	 * were here for.  Each day will create a new text file with the date in the tile.
	 */
	static String timeStamp = new SimpleDateFormat("MMddyyyy").format(Calendar.getInstance().getTime());
	public static String dailyFile = "./" + timeStamp + "SignInTimes.txt";
	public static void WriteDailyFile (byte[] name, byte[] time)
	{
		String endLine = System.getProperty("line.separator");
		byte[] newLine = endLine.getBytes();
		char tab = '\t';
		Path p = Paths.get(dailyFile);
		
		try(OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, CREATE, APPEND)))
		{
			out.write(name);
			out.write(tab);
			out.write(time);
			out.write(newLine);
		}
		catch (IOException x)
		{
			System.err.println(x);
		}
	}
}

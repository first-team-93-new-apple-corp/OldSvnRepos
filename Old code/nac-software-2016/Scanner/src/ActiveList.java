import static java.nio.file.StandardOpenOption.*;

import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

public class ActiveList
{
	/*
	 * This program will write a student's name to activelist.txt along with the current time.
	 */
  public static void WriteFile(String name)
  {
    //separator serves us on our text document by seperating names from numbers on the text document we make so that
	//it is easier to read and to use in code later on
    byte namebyte[] = name.getBytes();
    Path p = Paths.get("./activelist.txt");
    //Here, we're making sure that we can add multiple lines to the text document we create (1 per student)
    //Having multiple lines makes it much easier to understand information and get values from the text later
    String ENDL = System.getProperty("line.separator");
    byte newLine[] = ENDL.getBytes();
    
    try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, CREATE, APPEND)))
    //If there is no activelist.txt document in the same folder as the project, the program will create one
    {
    	      out.write(namebyte, 0, namebyte.length);
    	      
    	      //We store the tab value to separate the terms in activelist
    	      char tab = '\t';
    	      out.write(tab);
    	      
    	      //This bit adds a year/month/date/time term to activelist.
    	      //It isn't used by the program, but it's nice to see when somebody was signed in last
    	      String timeStamp = new SimpleDateFormat(" yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    	      byte timeDate[] = timeStamp.getBytes();    	      
    	      out.write(timeDate, 0, timeDate.length);
    	      
    	      out.write(tab);
    	      long getTimeMillis = System.currentTimeMillis();
    	      System.out.println("Get Time Millis " + getTimeMillis);
    	      
    	      //I was having trouble converting a long to bytes, but I knew a way to convert longs to strings and
    	      //strings to bytes, so I did this weird chain conversion thing.  Hey, it works.
    	      String weirdConversion = Long.toString(getTimeMillis);
    	      byte[] timeMillis = weirdConversion.getBytes();

    	      //We store the time signed in on activelist so that we can read it again later
    	      out.write(timeMillis);
    	      
    	      //This bit creates a new line on activelist.txt so that it can easily be read
    	      //by the program and any user.
    	      out.write(newLine, 0, newLine.length);
    	      
    	      String noSignOutYet = "Didn't sign out";
    	      byte[] notime = noSignOutYet.getBytes();
    	      DailyResults.WriteDailyFile(namebyte, notime);
    	      
			  System.out.println(name + " signed in");
			  GUIMain.printBox.append("\n" + name + " signed in");
    }
    catch (IOException x)
    {
    	 System.err.println(x);
    }
  }
}
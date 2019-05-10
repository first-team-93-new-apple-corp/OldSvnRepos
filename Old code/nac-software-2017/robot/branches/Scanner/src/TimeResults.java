import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;

public class TimeResults
{	
	/**
	 * If a student currently has a line on activelist.txt and their card is
	 * scanned again, the program will call this class to sign them out.
	 * 
	 * This program writes a name and time total in minutes to timeresults.txt.
	 * It will take the difference in the time signed in recorded on the
	 * activelist.txt file and the time when the card is scanned to sign out.
	 * If a student already has a line on timeresults.txt, then the program will
	 * replace that line with a new line featuring a cumulative time.
	 * 
	 * @param name
	 * 			The name of the student that we are signing out
	 * @param signInTime
	 * 			The time that a student signed in at in timemillis format (provided by activelist.txt)
	 */
  public static void WriteFile(String firstName, String lastName, long signInTime)
  {
    // Convert the string to a
    // byte array.
    byte firstNamebyte[] = firstName.getBytes();
    byte lastNamebyte[] = lastName.getBytes();
    byte[] totalminutebyte = null;
    boolean onList = false;
    boolean addtwotimes = false;
    Path p = Paths.get("./timeresults.txt");
	char tab = '\t';
	String name = firstName + " " + lastName;
    
	//Here, we're making sure that we can add multiple lines to the text document we create (1 per student)
    //Having multiple lines makes it much easier to understand information and get values from the text later
    String ENDL = System.getProperty("line.separator");
    byte newLine[] = ENDL.getBytes();
    try (BufferedReader timeReader = new BufferedReader(new FileReader("./timeresults.txt")))
    {
    	String readLine = timeReader.readLine();
    	while (readLine != null && !onList)
    	{
    		String[] line = readLine.split("\t");
    		if(line[0].equals(firstName) && line[1].equals(lastName))
    		{
    			onList = true;
    		}
    		readLine = timeReader.readLine();
    	}
        timeReader.close();
    }
    catch (IOException e)
    {
    	System.err.println(e);
    }
    try (OutputStream out = new BufferedOutputStream(
  	      Files.newOutputStream(p, CREATE, APPEND)))
    //If there is no timeresults.txt document in the same folder as the project, the program will create one
    {
    	
	      //Here, I'm getting the difference in time between the instant I signed in and the instant I signed
	      //out.  We're then doing what we did in activelist by converting the long to a string to bytes
	      long signOutTime = System.currentTimeMillis();
	      double timeIn = signOutTime - signInTime;

	      //60000 milliseconds in a minute (1000*60)
	      double minutes = timeIn / 60000;
	      //This section effectively rounds the number to two decimal places
	      minutes = Math.round(minutes * 100);
	      minutes = minutes/100;
  	      System.out.println(name + " was signed in for " + minutes + " minutes");
  	      GUIMain.printBox.append("\n" + name + " was signed in for " + minutes + " minutes");


  	      //If the program has read through timeresults.txt and didn't find the student name, it will
  	      //make a new line for them
  	      if(!onList)
  	      {
  	    	  String weirdConversion = Double.toString(minutes);
  	    	  byte[] minutesbytes = weirdConversion.getBytes();
  	    	  totalminutebyte = weirdConversion.getBytes();
  	      
  	    	  if(minutes < 600)
  	    	  {
  	    		  out.write(firstNamebyte, 0, firstNamebyte.length);
  	    		  out.write(tab);
  	    		  out.write(lastNamebyte);
  	    		  out.write(tab);
  	    		  out.write(minutesbytes);
  	    		  out.write(newLine, 0, newLine.length);
				  System.out.println(name + " signed out");
				  GUIMain.printBox.append("\n" + name + " signed out");
  	    		  //this little bit calls the function to write the daily time file
  	    		  DeleteLine.DeleteName(name, DailyResults.dailyFile, "tempDailyFile");
  	    		  DailyResults.WriteDailyFile(name, minutes);
  	    		  DeleteLine.DeleteName(name, "activelist.txt", "tempactivelist.txt");
  	    	  }
  	    	  else
  	    	  {
  	    		  //If the student didn't sign out, we don't add a line for them on timeresults.txt
  	    		  System.out.println("You forgot to sign out!");
  	    		  GUIMain.printBox.append("\n" + "You forgot to sign out!");
				  DeleteLine.DeleteName(name, "./activelist.txt", "./newactivelist.txt");
  	    		  ActiveList.WriteFile(name);
  	    	  }
  	      
  	      }
  	      else
  	      {
  	    	  double previousTime = DeleteLine.getTimeFromTimeresults(firstName, lastName, "./timeresults.txt");
  	    	  System.out.println("previous " + previousTime);
  	    	  double timeInMins = timeIn/60000;
  	    	  if(timeInMins < 600)
  	    	  {
  	    		  addtwotimes = true;
  	    		  double totalTime = previousTime + timeInMins;
  	    		  double totalMinutes = Math.round(totalTime * 100);
  	    		  totalMinutes = totalMinutes/100;
  	    		  String conversion = Double.toString(totalMinutes);
  	    		  totalminutebyte = conversion.getBytes();
  	    	  }
  	    	  else
  	    	  {
  	    		  //If the student didn't sign out, we don't add any time to their total.
  	    		  System.out.println("You forgot to sign out!");
  	    		  GUIMain.printBox.append("\n" + "You forgot to sign out!");
  	    		  DeleteLine.DeleteName(name, "./activelist.txt", "./newactivelist.txt");
  	    		  ActiveList.WriteFile(name);
  	    	  }

  	    	  out.close();
	    	
  	    	  if(addtwotimes)
  	    	  {
  	    		  //If we are changing a student's total time.
  	    		  DeleteLine.DeleteDoubleName(firstName, lastName, "./timeresults.txt", "./newtimeresults.txt");
  	    		  Path resultspath = Paths.get("./timeresults.txt");
  	    		  try (OutputStream out2 = new BufferedOutputStream(Files.newOutputStream(resultspath, CREATE, APPEND)))
  	    		  {
  	    			  out2.write(firstNamebyte);
  	    			  out2.write(tab);
  	  	    		  out2.write(lastNamebyte);
  	  	    		  out2.write(tab);
  	    			  out2.write(totalminutebyte);
  	    			  out2.write(newLine, 0, newLine.length);
  	    			  
  	  	    		  double totalMinutes2 = Math.round(timeInMins * 100);
  	  	    		  totalMinutes2 = totalMinutes2/100;
  	    			  DailyResults.WriteDailyFile(name, totalMinutes2);
  	    			  DeleteLine.DeleteName(name, "activelist.txt", "tempactivelist.txt");
  	    			  GUIMain.printBox.append("\n");
  	    			  GUIMain.printBox.append("''" + name + "''" + " signed out");
  	    		  }
  	    		  catch (IOException b)
  	    		  {
  	    			  System.err.println(b);
  	    		  }
  	    	  }
  	      }
    }
    catch (IOException x)
    {
    	System.err.println(x);
    }
  }
}
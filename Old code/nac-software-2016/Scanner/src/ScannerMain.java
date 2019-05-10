import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScannerMain 
{
   public static void main(String args[]) 
   {
	   InputStreamReader r = new InputStreamReader(System.in);  
	   BufferedReader br = new BufferedReader(r);
	   while(true)
	   { 
		   String ID = null;
		   try
		   {
			   ID = br.readLine();
		   }
		   catch (IOException e) 
		   {
			   e.printStackTrace();
		   }
		   
		   //I made a special case so that a user can type delete and the program will clear the
		   //activelist and timeresults files
		   if (ID.equals("delete"))
		   {
			   Path activelistpath = Paths.get("./activelist.txt");
			   Path timeresultpath = Paths.get("./timeresults.txt");
			   try
			   {
				   Files.delete(activelistpath);
			   }
			   catch (IOException e)
			   {
				   e.printStackTrace();
				   System.out.println("Problem deleting activelist.txt");
			   }
			   try
			   {
				   Files.delete(timeresultpath);
			   }
			   catch (IOException e)
			   {
				   System.out.println("Problem deleting timeresult.txt");
			   }
			   System.out.println("Deleted files");
		   }
		   
		   //This section runs when the scanner reads an ID card or a user input that isn't 'delete'
		   else if (ID != null)
		   {
			   Boolean nameFound = false;
			   Boolean validID = false;
			   Boolean onActive = false;
			   System.out.println("READ VALUE " + ID);
			   try (BufferedReader fileReader = new BufferedReader(new FileReader("./NamesNumbers.txt")))
		       {
				   String line = fileReader.readLine();
				   while (line != null)
		           {
					   //The program runs through the text file until it's read every line
		               String[] member = line.split(",");
		               //Here we're telling the program what character on the text document
		               //to use to separate values in an array list
		               if(member[0].equals(ID))
		               {
		            	   String name = member[1];
		            	   System.out.println(name);
		            	   try (BufferedReader activeReader = new BufferedReader(new FileReader("./activelist.txt")))
		            	   {
	            			   String activeLine = activeReader.readLine();
		            		   while (activeLine != null && !nameFound)
		            		   {
		            			   
		            			   String[] activeMember = activeLine.split("\t");
		            			   if(activeMember[0].equals(name))
		            			   //We're checking if the name corresponding to the ID no is already on the sign in list
		            			   {
		            				   activeReader.close();
		            				   TimeResults.WriteFile(name,Long.parseLong(activeMember[2]));
		            				   DeleteLine.DeleteName(name, "./activelist.txt", "./newactivelist.txt");
		            				   System.out.println(name + " signed out");
		            				   validID = true;
		            				   nameFound = true;
		            				   onActive = true;
		            			   }
		            			   activeLine = activeReader.readLine();
		            		   }
		            		   if (!onActive)
		            		   //If the program has run through the sign on list and the name has not been found
		            		   {
		            			   //The program will sign the person in
	            				   ActiveList.WriteFile(name);
	            				   System.out.println(name + " signed in");
	            				   validID = true;
		            		   }
		            	   }
		            	   catch (IOException e)
		            	   {
		            		   //If the program doesn't find an activelist.txt file, it will create a new one
		            		   if(!nameFound)
		            		   {
		            			   //As long as the program wasn't used to sign a user in or out in this run
		            		   System.out.println("No activelist file");
		            		   ActiveList.WriteFile(name);
		            		   System.out.println(name + "signed in on new activelist");
		            		   validID = true;
		            		   }
		            	   }
		               }
		            line = fileReader.readLine();   
		           }
				   if(!validID)
				   {
					   //If the input provided anything that wasn't an ID number on NamesNumbers.txt
					   //Note that NamesNumbers.txt includes all registered students on team 93.
					   System.out.println(ID + " is not a registered ID");
				   }
		       }
		       catch (IOException e)
		       {
		    	   e.printStackTrace();
		       }
		   }
	   }
   }
}
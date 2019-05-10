import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hub 
{
	/**
	 * This class is the heart of the scanner code.  When we get a new input, the code runs
	 * through this class to see what to do with it.  It will call active list or time
	 * results writers when needed.
	 * 
	 * @param ID
	 * 			The value input to the GUI text box
	 */
   public static void mainProgram(String ID) 
   {
	   boolean creatingWeekly = false;
	   boolean askingForDirectory = false;
	   boolean askingForName = false;
	   String directory = "./";
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
			   GUIMain.printBox.append("\n" + "Couldn't delete activelist.txt");
		   }
		   try
		   {
			   Files.delete(timeresultpath);
		   }
		   catch (IOException e)
		   {
			   System.out.println("Problem deleting timeresult.txt");
			   GUIMain.printBox.append("\n" + "Couldn't delete timeresults.txt");
		   }
		   System.out.println("Deleted files");
		   GUIMain.printBox.append("\n" + "Deleted files");
	   }
	   
//	   if(ID.equals("weekly"))
//	   {
//		   creatingWeekly = true;
//		   GUIMain.printBox.append("\n" + "Creating weekly file");
//		   GUIMain.printBox.append("\n" + "Type the name of the file you want to edit");
//		   GUIMain.printBox.append("\n" + "Type 'change directory to change what folder you are looking at");
//		   GUIMain.printBox.append("\n" + "Type 'done' to exit file editing");
//		   GUIMain.printBox.append("\n" + "Type 'rename' to rename the file you are creating");
//	   }
//	   
//	   if(creatingWeekly)
//	   {
//		   String fileName = "./" + ID + ".txt";
//		   if(ID.equals("done"))
//		   {
//			   GUIMain.printBox.append("\n" + "File completed");
//			   GUIMain.printBox.append("\n" + "What would you like to name your file?");
//			   askingForName = true;
//			   creatingWeekly = false;
//		   }
//		   else if(ID.equals("change directory"))
//		   {
//			   GUIMain.printBox.append("What would you like to change directory to?");
//			   
//		   }
//		   else
//		   {
//			   boolean onFile = false;
//			   Path combinedPath = Paths.get("./combinedFile.txt");
//			   try (BufferedReader inputReader = new BufferedReader(new FileReader(fileName)))
//			   {
//				   String inputReadLine = inputReader.readLine();
//				   while(inputReadLine != null)
//				   {
//					   String[] splitInput = inputReadLine.split("\t");
//					   try (BufferedReader createdReader = new BufferedReader(new FileReader("./combinedFile.txt")))
//					   {
//						   String createdFileReadLine = createdReader.readLine();
//						   while(createdFileReadLine != null)
//						   {
//							   String[] splitCreatedRead = createdFileReadLine.split("\t");
//							   if(splitInput[0].equals(splitCreatedRead[0]) && splitInput[1].equals(splitCreatedRead[1]))
//							   {
//								   onFile = true;
//							   }
//						   }
//						   if(!onFile)
//						   {
//							   try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(combinedPath, CREATE, APPEND)))
//							   {
//								   out.write(inputReadLine.getBytes());
//							   }
//							   catch (IOException e)
//							   {
//								   e.printStackTrace();
//							   }   
//						   }   
//					   }   
//					   inputReadLine = inputReader.readLine();
//				   }
//			   }
//			   catch (FileNotFoundException e1)
//			   {
//				e1.printStackTrace();
//			   }
//			   catch (IOException e1)
//			   {
//				e1.printStackTrace();
//			   }
//		   }
//	   }
//	   
//	   if(askingForName)
//	   {
//		   
//	   }
	   
	   //This section runs when the scanner reads an ID card or a user input that isn't 'delete'
	   else if (ID != null && !creatingWeekly)
	   {
		   int id = Integer.parseInt(ID) % 1000000;
		   ID = String.valueOf(id);
		   Boolean nameFound = false;
		   Boolean validID = false;
		   Boolean onActive = false;
		   System.out.println("READ VALUE " + ID);
		   GUIMain.printBox.append("\n" + "READ VALUE " + ID);
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
	            	   //Schuff wanted the name to be split between first and last name,
	            	   //so I split name into firstName and lastName.
	            	   String firstName = member[1];
	            	   String lastName = member[2];
	            	   String wholeName = firstName + " " + lastName;
	            	   System.out.println(firstName + " " + lastName);
	            	   try (BufferedReader activeReader = new BufferedReader(new FileReader("./activelist.txt")))
	            	   {
            			   String activeLine = activeReader.readLine();
	            		   while (activeLine != null && !nameFound)
	            		   {
	            			   
	            			   String[] activeMember = activeLine.split("\t");
	            			   if(activeMember[0].equals(wholeName))
	            			   //We're checking if the name corresponding to the ID no is already on the sign in list
	            			   {
	            				   activeReader.close();
	            				   TimeResults.WriteFile(firstName, lastName, Long.parseLong(activeMember[2]));
	            				   System.out.println("Hub firstName " + firstName);
	            				   System.out.println("Hub lastName " +  lastName);
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
            				   ActiveList.WriteFile(wholeName);
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
		            		   ActiveList.WriteFile(wholeName);
		            		   System.out.println(wholeName + "signed in on new activelist");
		            		   GUIMain.printBox.append("\n" + wholeName + " signed in on new activelist");
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
				   GUIMain.printBox.append("\n" + ID + " is not a registered ID");
			   }
	       }
		   catch (IOException e)
		   {
			   e.printStackTrace();
	       }
	   }
   }
}
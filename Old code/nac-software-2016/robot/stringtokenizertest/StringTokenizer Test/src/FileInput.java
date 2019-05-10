
 
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
 
/**
 * This program reads a text file line by line and print to the console. It uses
 * FileOutputStream to read the file.
 * 
 */
public class FileInput {
 
  public static void main(String[] args) {
 
	String fileContents = null;
    File file = new File("C:\\StringTokenizer\\test.txt");
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;
 
    try {
      fis = new FileInputStream(file);
 
      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);
 
      // dis.available() returns 0 if the file does not have more lines.
      while (dis.available() != 0) {

      // this statement reads the line from the file and print it to
        // the console.
    	  fileContents = dis.readLine();
      }
 
      // dispose all the resources after using them.
      fis.close();
      bis.close();
      dis.close();
      } 
    
    catch (FileNotFoundException e) 
    {
      e.printStackTrace();
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
    }
		StringTokenizer st = new StringTokenizer (fileContents, ",");
	
		System.out.println();
		while (st.hasMoreTokens())
			{
				System.out.println(st.nextToken());
			}
	
	/*
	  	System.out.println("fffffffff");
		/StringTokenizer st2 = new StringTokenizer(file, ",");

		while (st2.hasMoreElements()) {
		System.out.println(st2.nextElement());
		}
	*/
	
  }
}
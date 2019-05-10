import java.io.IOException;
import java.io.PrintWriter;

public class Print_Writer{
	double a = 2.7;
	double b = 5.6;
	double c = 12;
	
	
	public Print_Writer(){
	}
	
	public static void main( String [] args) {
		Print_Writer myPrintWriter = new Print_Writer();
		myPrintWriter.writeOutput();
	}
	
	public void writeOutput()
	{
		try{
		PrintWriter writer = new PrintWriter("NAME_HERE.txt", "UTF-8");
		writer.println(a + ", " + b + ", " + c + "!");
		writer.close();
		}catch(IOException e){	
		}
	}
	
}
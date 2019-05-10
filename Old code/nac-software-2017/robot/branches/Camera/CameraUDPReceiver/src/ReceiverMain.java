import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;

/**
 * @author Chenor Marevans
 */
public class ReceiverMain
{
	
	public static void main(String[] args)
	{
		//The socket that the Raspberry Pi is using.
		//This socket must match the socket used in the Raspberry Pi to receive correctly.
		int socket = 5005;
		
		//Create the UDPReceiver, which receives the data in the form of byte arrays.
		DatagramSocket UDPReceiver = createUDPReceiver(socket);
		
		//Set the timeout of the socket, in milliseconds.
		//This means that if multiple packets are received within this time frame, those will be discarded.
		int timeout = 1;
		setUDPReceiverTimeout(UDPReceiver, timeout);
		
		System.out.println("UDPReceiver created, waiting for packets.");
		
		//If the UDPReceiver was successfully created...
		while(UDPReceiver != null)
		{
			//Receive a string from the UDPReceiver.
			String packetString = receivePacketString(UDPReceiver);
			
			//If a packet string was received...
			double[] data = parsePacketString(packetString);
			
			//print the data
			if (data != null)
			{
				printDoubleArray(data);
			}
			
			//wait for 10 ms before getting enxt packet
			sleep(10);
		}
	}
	
	public static void printDoubleArray(double[] d)
	{
		String s = "";
		for(int i = 0; i < d.length; i++)
		{
			if (i != 0)
			{
				s += ", ";
			}
			s += d[i];
		}
		System.out.println(s);
	}
	
	//Creates a UDPReceiver.
	public static DatagramSocket createUDPReceiver(int socket)
	{
		//Create the UDPReceiver, which receives the data in the form of byte arrays.
		DatagramSocket UDPReceiver = null;
		try
		{
			UDPReceiver = new DatagramSocket(socket);
		}
		catch (SocketException e)
		{
			System.out.println("Socket failed to be created.");
			System.out.println("Exiting.");
			System.exit(1);
		}
		return UDPReceiver;
	}
	
	//Sets the timeout of a UDPReceiver.
	public static void setUDPReceiverTimeout(DatagramSocket UDPReceiver, int timeout)
	{
		try
		{
			//Set timeout
			UDPReceiver.setSoTimeout(timeout);
		}
		catch (SocketException e)
		{
			System.out.println("Socket timeout failed to be set to " + timeout + "ms.");
			System.exit(1);
		}
	}
	
	//Receives a packet string from a UDPReceiver.
	public static String receivePacketString(DatagramSocket UDPReceiver)
	{
		//Create a byte array to store the packet.
		byte[]  buf = new byte[256];
		//Try receiving a packet.
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		
		String packetString = null;
		try
		{
			//Receive the packet.
			UDPReceiver.receive(packet);
			System.out.println("Received UDP packet!");
			//Convert the packet to a string.
			packetString = new String(packet.getData(), 0, packet.getLength());
			System.out.println(packetString);
		} 
		catch (IOException e)
		{
			System.out.println("Packet failed to be received.");
		}
		return packetString;
	}
	
	//Parses a received packet string into a double array, whose data can be used.
	public static double[] parsePacketString(String packetString)
	{
		double[] data = null;
		
		if (packetString != null)
		{
			//Parse the packet
			//Packet string is in the format:
			//"DATA_A,DATA_B,DATA_C,DATA_D"
			//Example: "1.0,2.0,3.0,4.0"
			
			//There are 4 items to parse.
			final int DATA_LENGTH = 4;
			
			//Creates the array to store the numerical data after conversion.
			data = new double[DATA_LENGTH];
			
			//Splits the strings into separate string representations of doubles.
			String[] dataStrings = packetString.split(",");
			
			if (dataStrings.length != DATA_LENGTH)
			{
				System.out.println("String " + packetString + " is not formatted correctly.");
			}
			
			//For each string piece of data in dataStr, convert it to a double and put it in data.
			for (int i = 0; i < DATA_LENGTH; i++)
			{
				//Gets the string representation of the number.
				String str = dataStrings[i];
				//Converts the string representation of a number into a number.
				//If a number cannot be converted, it defaults to 0.
				double number = 0.0;
				try
				{
					number = Double.valueOf(str);
				} 
				catch (NumberFormatException e) 
				{
					//If str cannot be parsed to a double
					System.out.println("Could not parse " + str + " to double.");
				}
				//Store converted double in the data array.
				data[i] = number;
			}
		}
		
		//return the data array
		return data;
	}
	
	public static void sleep(int ms)
	{
		try
		{
			Thread.sleep(ms);
		} 
		catch (InterruptedException e)
		{
			System.out.println("Sleep interrupted.");
		}
	}
}
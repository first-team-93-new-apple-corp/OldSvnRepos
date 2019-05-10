package org.usfirst.frc.team93.robot.commands;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.VisionProcessor;
import org.usfirst.frc.team93.robot.utilities.DummyPIDSource;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is used to print out and update booleans for the camera.
 */
public class VisionProcessorUDPReceiver extends Command
{
	DatagramSocket UDPReceiver;
	public final int socket = 5005;
	public final int timeout = 1;
	
	public DummyPIDSource FORWARD_PID_SOURCE;
	public DummyPIDSource CENTER_PID_SOURCE;
	
	public double qualityStatus = 0.9;
	
	private int FORWARD_INDEX = 2;
	private int CENTER_INDEX = 3;
	private int STATUS_INDEX = 4;
	
	public boolean m_enablePrint = true;
	
	public VisionProcessorUDPReceiver()
	{
		FORWARD_PID_SOURCE = new DummyPIDSource();
		CENTER_PID_SOURCE = new DummyPIDSource();
		requires(Robot.visionProcessor);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		// The socket that the Raspberry Pi is using.
		// This socket must match the socket used in the Raspberry Pi to receive
		// correctly.
		
		// Create the UDPReceiver, which receives the data in the form of byte
		// arrays.
		UDPReceiver = createUDPReceiver(socket);
		
		// Set the timeout of the socket, in milliseconds.
		// This means that if multiple packets are received within this time
		// frame, those will be discarded.
		setUDPReceiverTimeout(UDPReceiver, timeout);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// If the UDPReceiver was successfully created...
		if (UDPReceiver != null)
		{
			// Receive a string from the UDPReceiver.
			String packetString = receivePacketString(UDPReceiver);
			// If a packet string was received...
			// parse
			double[] data = parsePacketString(packetString);
			if (data != null)
			{
				printDoubleArray(data);
				// if data status is OK
				if ((int) data[STATUS_INDEX] >= qualityStatus)
				{
					// update the data
					FORWARD_PID_SOURCE.set(data[FORWARD_INDEX]);
					CENTER_PID_SOURCE.set(data[CENTER_INDEX]);
					// we can see the tape.
					VisionProcessor.tapeVisible = true;
				}
				else
				{
					// we can't see the tape.
					print("TAPE NOT SEEN");
					VisionProcessor.tapeVisible = false;
				}
			}
			else
			{
				print("NO DATA RECEIVED");
			}
		}
		// if it wasn't successfully created, try to create it again.
		else
		{
			// try to create the UDPReceiver again if it wasn't created
			// successfully the first time.
			print("Retrying createUDPReceiver...");
			UDPReceiver = createUDPReceiver(socket);
			setUDPReceiverTimeout(UDPReceiver, timeout);
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return false;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
	
	/**
	 * Creates a UDPReceiver.
	 * 
	 * @param socket
	 * @return The UDPReceiver.
	 */
	private DatagramSocket createUDPReceiver(int socket)
	{
		// Create the UDPReceiver, which receives the data in the form of byte
		// arrays.
		DatagramSocket UDPReceiver = null;
		try
		{
			UDPReceiver = new DatagramSocket(socket);
		}
		catch (SocketException e)
		{
			print("Socket failed to be created.");
			print("Exiting.");
		}
		return UDPReceiver;
	}
	
	/**
	 * Sets the timeout of a UDPReceiver.
	 * 
	 * @param UDPReceiver
	 * @param timeout
	 */
	private void setUDPReceiverTimeout(DatagramSocket UDPReceiver, int timeout)
	{
		try
		{
			// Set timeout
			UDPReceiver.setSoTimeout(timeout);
		}
		catch (SocketException e)
		{
			print("Socket timeout failed to be set to " + timeout + "ms.");
		}
	}
	
	/**
	 * Receives a packet string from a UDPReceiver.
	 * 
	 * @param UDPReceiver
	 * @return The packet string
	 */
	private String receivePacketString(DatagramSocket UDPReceiver)
	{
		// Create a byte array to store the packet.
		byte[] buf = new byte[256];
		// Try receiving a packet.
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		
		String packetString = null;
		try
		{
			// Receive the packet.
			UDPReceiver.receive(packet);
			print("Received UDP packet!");
			// Convert the packet to a string.
			packetString = new String(packet.getData(), 0, packet.getLength());
			print(packetString);
		}
		catch (IOException e)
		{
			print("Packet failed to be received.");
		}
		return packetString;
	}
	
	/**
	 * Parses a received packet string into a double array, whose data can be
	 * used.
	 * 
	 * @param packetString
	 * @return The double array
	 */
	private double[] parsePacketString(String packetString)
	{
		double[] data = null;
		
		if (packetString != null)
		{
			// Parse the packet
			// Packet string is in the format:
			// "FORWARD_DATA, CENTER_DATA, STATUS"
			// Example: "1.0,2.0,0"
			
			// There are 2 items to parse.
			final int DATA_LENGTH = 5;
			
			// Creates the array to store the numerical data after conversion.
			data = new double[DATA_LENGTH];
			
			// Splits the strings into separate string representations of
			// doubles.
			String[] dataStrings = packetString.split(",");
			
			if (dataStrings.length != DATA_LENGTH)
			{
				print("String " + packetString + " is not formatted correctly: " + packetString);
			}
			
			// For each string piece of data in dataStr, convert it to a double
			// and put it in data.
			for (int i = 0; i < DATA_LENGTH; i++)
			{
				// Gets the string representation of the number.
				String str = dataStrings[i];
				// Converts the string representation of a number into a number.
				// If a number cannot be converted, it defaults to 0.
				double number = 0.0;
				try
				{
					number = Double.valueOf(str);
				}
				catch (NumberFormatException e)
				{
					// If str cannot be parsed to a double
					print("Could not parse " + str + " to double.");
				}
				// Store converted double in the data array.
				data[i] = number;
			}
		}
		
		// return the data array
		return data;
	}
	
	/**
	 * Prints a double array.
	 * 
	 * @param d
	 */
	private void printDoubleArray(double[] d)
	{
		String s = "";
		for (int i = 0; i < d.length; i++)
		{
			if (i != 0)
			{
				s += ", ";
			}
			s += d[i];
		}
		System.out.println(s);
	}
	
	private void print(Object o)
	{
		if (m_enablePrint)
		{
			System.out.println("" + o);
		}
	}
}

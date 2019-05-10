package org.usfirst.frc.team93.robot.commands;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UDPReceiver extends Command
{

	DatagramSocket cameraReceiver = null;
	int iterationNumber;

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		System.out.println("Waiting for data.");
		try
		{
			cameraReceiver = new DatagramSocket(5800);
			cameraReceiver.setSoTimeout(1000);
		} catch (SocketException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(e);
		}
		iterationNumber = 0;

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		if (4 == iterationNumber)
		{

			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			String packetstr = null;
			try
			{
				if (cameraReceiver != null)
				{
					cameraReceiver.receive(packet);
					// System.out.println("Received DataGram!");
					packetstr = new String(packet.getData(), 0, packet.getLength());
					// System.out.println(packetstr);
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Process new data
			if (packetstr != null)
			{
				// do work here
				final int len = 4;
				StringTokenizer st = new StringTokenizer(packetstr, ",");
				double[] cameraData = new double[len];
				System.out.println();
				for (int i = 0; (st.hasMoreTokens()) && i < len; i++)
				{
					String tok = st.nextToken();
					// is tok a number
					double dtok = 0.0;
					try
					{
						dtok = Double.valueOf(tok);
						cameraData[i] = dtok;
						System.out.println(dtok);
					} catch (NumberFormatException e)
					{
						// not a number
						// System.out.println("Not a Number!");
					}

				}

				SmartDashboard.putNumber("x", cameraData[0]);
				SmartDashboard.putNumber("y", cameraData[1]);
				SmartDashboard.putNumber("size", cameraData[2]);

				iterationNumber = 0;
			}
		}

		else
		{
			iterationNumber++;
		}

		// try
		// {
		// TODO may need to yield here
		// Thread.sleep(10);
		// } catch (InterruptedException e)
		// {
		// // TODO Auto-generated catch block
		// // e.printStackTrace();
		// }

	}
	// cameraReceiver.close();

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		/*
		 * Checking validity of code review
		 */
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		cameraReceiver.close();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}

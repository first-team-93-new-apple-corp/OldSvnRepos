package org.usfirst.frc.team93.robot.commands;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraCoordRecvCmd extends Command

{
	DatagramSocket cameraReceiver = null;

	public CameraCoordRecvCmd()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.camerareceive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{

		try
		{
			cameraReceiver = new DatagramSocket(5800);
			cameraReceiver.setSoTimeout(1);
		} catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		boolean haveData = true;
		while (haveData == true)
		{
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			String packetstr = null;
			try
			{
				if (cameraReceiver != null)
				{
					cameraReceiver.receive(packet);
					System.out.println("Received DataGram!");
					packetstr = new String(packet.getData(), 0, packet.getLength());
					System.out.println(packetstr);
					haveData = true;
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				// e.printStackTrace();
				haveData = false;
			}

			// Process new data
			if (packetstr != null)
			{
				// do work here
				final int len = 4;
				StringTokenizer st = new StringTokenizer(packetstr, ",");
				double[] cameraData = new double[len];
				int i = 0;
				System.out.println();
				while ((st.hasMoreTokens()) && i < len)
				{
					String tok = st.nextToken();
					// is tok a number
					double dtok = 0.0;
					try
					{
						dtok = Double.valueOf(tok);
						cameraData[i] = dtok;
						i++;
						System.out.println(dtok);
					} catch (NumberFormatException e)
					{
						// not a number
						System.out.println("Not a Number!");
					}

				} // while

				Robot.camerareceive.targetDistance = cameraData[0];
				Robot.camerareceive.targetAzimuth = cameraData[1];
				Robot.camerareceive.targetElevation = cameraData[2];
				Robot.camerareceive.targetQuality = cameraData[3];
			}
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
}

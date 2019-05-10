package org.usficrst.frc.team93.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Cameras extends Subsystem
{

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public static UsbCamera cam0 = new UsbCamera("cam0", 0);
	public static UsbCamera cam1 = new UsbCamera("cam1", 1);

	public static void initialize()
	{
		CameraServer.getInstance().startAutomaticCapture(cam0);
		// CameraServer.getInstance().startAutomaticCapture(cam1);

	}
}

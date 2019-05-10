package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.commands.CameraCoordRecvCmd;

import edu.wpi.first.wpilibj.command.Subsystem;

public class CameraCoordRecv extends Subsystem
{
	public double targetDistance = -1.0;
	public double targetAzimuth = -1.0;
	public double targetElevation = -1.0;
	public double targetQuality = -1.0;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		setDefaultCommand(new CameraCoordRecvCmd());
	}
}

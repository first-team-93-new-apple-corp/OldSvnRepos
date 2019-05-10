package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.commands.VisionProcessorUDPReceiver;
import org.usfirst.frc.team93.robot.utilities.DummyPIDSource;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * VisionProcessor subsystem.
 */
public class VisionProcessor extends Subsystem
{
	// the command
	static VisionProcessorUDPReceiver s_update;
	
	public static boolean tapeVisible = false;
	public static double targetYaw = 0.0;
	
	@Override
	protected void initDefaultCommand()
	{
		s_update = new VisionProcessorUDPReceiver();
		s_update.setRunWhenDisabled(true);
		setDefaultCommand(s_update);
	}
	
	/**
	 * Returns the PID source that tells us how far left or right we need to go.
	 * 
	 * @return
	 */
	public static DummyPIDSource centerSource()
	{
		return s_update.CENTER_PID_SOURCE;
	}
	
	/**
	 * Returns the PID source that tells us how far forward we need to go.
	 * 
	 * @return
	 */
	public static DummyPIDSource forwardSource()
	{
		return s_update.FORWARD_PID_SOURCE;
	}
}
package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command grants the operator the ability to control the gear belt using a
 * joystick axis.
 */
public class ManualControl extends Command
{
	double m_maintainSetpoint;
	boolean maintain;
	
	public ManualControl()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.gearManipulator);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		maintain = false;
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		double beltJoystick = OI.belt.get() * 1.0;
		
		// if no joystick input, hold position
		// - changed == 0 to check absolute value because deadzone
		if (Math.abs(beltJoystick) < .1)
		{
			if (!maintain)
			{
				GearManipulator.maintainBeltPID();
				m_maintainSetpoint = GearManipulator.getBeltPosition();
				maintain = true;
			}
		}
		else
		{
			// if receiving joystick input
			GearManipulator.disableBeltPID();
			maintain = false;
			
			// if going up and top limit switch isn't pressed
			// or if going down and the bottom limit switch isn't pressed,
			
			if ((beltJoystick > 0) && (GearManipulator.getReedSwitch() && (GearManipulator.getBeltPosition() > GearManipulator.getLocation(GearManipulator.GearLocation.PEG))))
			{
				GearManipulator.setBeltMotor(0);
			}
			else if ((beltJoystick < 0) && (GearManipulator.getReedSwitch() && (GearManipulator.getBeltPosition() < GearManipulator.getLocation(GearManipulator.GearLocation.PEG))))
			{
				GearManipulator.setBeltMotor(0);
			}
			else
			{
				GearManipulator.setBeltMotor(beltJoystick);
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
		GearManipulator.setBeltMotor(0.0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
}

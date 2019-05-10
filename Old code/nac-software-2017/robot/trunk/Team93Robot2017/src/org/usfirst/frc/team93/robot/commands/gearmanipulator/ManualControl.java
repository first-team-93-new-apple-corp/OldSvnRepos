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
	double beltJoystick;
	
	/**
	 * Grant the operator manual control of the gear manipulator belt.
	 */
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
		beltJoystick = OI.belt.get() * 1.0;
		
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
			// don't hold the manipulator in one position
			maintain = false;
			
			// stop intake wheels
			GearManipulator.setIntakeMotors(0);
			
			// STOP if going up and top limit switch is pressed
			// or STOP if going down and the bottom limit switch is pressed,
			if (GearManipulator.getReedSwitch())
			{
				/*
				 * These first two if statements determine whether the front or
				 * back limit switch is being activated
				 */
				// if the operator is trying to move the belt forward and the
				// front limit switch is pressed
				if ((beltJoystick > 0) && (GearManipulator.getBeltPosition() < GearManipulator.getLocation(GearManipulator.GearLocation.BOTTOM_FRONT)))
				{
					// stop the belt motor
					GearManipulator.setBeltMotor(0);
				}
				// if the operator is trying to move the belt backward and the
				// back limit switch is pressed
				else if ((beltJoystick < 0) && (GearManipulator.getBeltPosition() > GearManipulator.getLocation(GearManipulator.GearLocation.BOTTOM_FRONT)))
				{
					// stop the belt motor
					GearManipulator.setBeltMotor(0);
				}
				// if either limit switch is pressed, but the operator is
				// driving the belt away from the ends
				else
				{
					// allow the operator control of the belt
					GearManipulator.setBeltMotor(beltJoystick);
				}
			}
			// if the limit switch isn't pressed
			else
			{
				// allow the operator control of the belt
				GearManipulator.setBeltMotor(beltJoystick);
			}
			
			/**
			 * @codereview josh.hawbaker 4.6.17 I think the method we have to
			 *             grant the operator manual control is really sloppy.
			 *             Manual control is the default command, so it's
			 *             normally running, but it only allows them to override
			 *             the limit switches if the button is held down. I'm
			 *             not a fan of accessing controls outside of OI like
			 *             this.
			 */
			
			// if the button is held down, we allow the operator to override the
			// limit switches and keep the belt running
			if (OI.operator.getRawButton(OI.lstick))
			{
				// give the operator control of the belt (overrides limit
				// switches)
				GearManipulator.setBeltMotor(beltJoystick);
			}
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// manual control runs continuously until interrupted by another gear
		// manipulator command
		return false;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		// stop the belt motor
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

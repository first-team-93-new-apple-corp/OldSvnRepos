package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command has the gear manipulator move along the belt to a desired
 * setpoint.
 */
public class MoveGear extends Command
{
	double m_setpoint;
	double m_tolerance;
	
	// calculate speed
	Timer speedTimer;
	double oldError = 0;
	
	// The minimum speed to be considered moving. Otherwise, is considered
	// stopped
	private double stoppedThreshold = 5.0;
	// the amount of time to wait while stopped before ending command
	private double stoppedTime = 0.1;
	
	// the timer that keeps track of whether to end the command or not
	private Timer finishTimer;
	
	// how much pressing against one end is compared to a normal position at
	// that end
	// for example, pressing the carriage against the last bar, vs. normally at
	// chute position
	double pressOffset = 0.0;
	
	// Limit Switch stops the command
	boolean limitSwitchStop = false;
	
	public MoveGear(double setpoint, double tolerance)
	{
		m_setpoint = setpoint;
		m_tolerance = tolerance;
		speedTimer = new Timer();
		finishTimer = new Timer();
		requires(Robot.gearManipulator);
	}
	
	public MoveGear(double setpoint)
	{
		// MODIFY THIS NUMBER STEVEN
		this(setpoint, 15.0);
	}
	
	public MoveGear(GearLocation location)
	{
		this(GearManipulator.getLocation(location));
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		// close all claws
		if (!GearManipulator.checkAllClawsClosed())
		{
			GearManipulator.closeClaws();
		}
		// set PID setpoint
		GearManipulator.enableBeltPID();
		GearManipulator.setBeltPIDSetpoint(m_setpoint);
		GearManipulator.setBeltPIDTolerance(m_tolerance);
		
		// init timers
		speedTimer.start();
		speedTimer.reset();
		oldError = 0;
		
		finishTimer.start();
		finishTimer.reset();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// PID moves the belt
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// if we are on target, we're done
		if (GearManipulator.getBeltOnTarget())
		{
			// end the command
			return true;
		}
		
		if (limitSwitchStop)
		{
			// if the reed switch is active...
			if (GearManipulator.getReedSwitch())
			{
				// if we're within 1000 ticks of our goal (we're not on the
				// wrong
				// reed switch end)
				if (Math.abs(GearManipulator.GEAR_BELT_PID.getError()) < 1000)
				{
					// and we're going to one of the ends of the belt (top or
					// back)
					if ((m_setpoint < 100) || (m_setpoint > 5000))
					{
						// end the command
						return true;
					}
				}
			}
		}
		
		// if we've been stopped for a little bit, and we can't move
		// then end the command to avoid smoking the motor
		// calculate speed
		double error = GearManipulator.GEAR_BELT_PID.getError();
		double speed = (error - oldError) / speedTimer.get();
		oldError = error;
		speedTimer.reset();
		// if we aren't moving
		if (Math.abs(speed) < stoppedThreshold)
		{
			// if we aren't moving for 0.2 seconds
			if (finishTimer.get() >= stoppedTime)
			{
				// end command. Don't fry a motor.
				// if we're at a limit switch....
				if (GearManipulator.getReedSwitch())
				{
					// if we're commanded to go to top and we're stopped, then
					// we need to reset encoder to top
					if ((m_setpoint == GearManipulator.getLocation(GearLocation.CHUTE)) || (m_setpoint == GearManipulator.getLocation(GearLocation.BOTTOM_FRONT)) || (m_setpoint == GearManipulator.getLocation(GearLocation.PEG)))
					{
						GearManipulator.setBeltPosition(GearManipulator.getLocation(GearLocation.CHUTE) + pressOffset);
					}
					// if we're commanded to go to bottom and we're stopped
					// then we need to reset encoder to bottom
					else if (m_setpoint == GearManipulator.getLocation(GearLocation.BOTTOM_BACK))
					{
						GearManipulator.setBeltPosition(GearManipulator.getLocation(GearLocation.BOTTOM_BACK) - pressOffset);
					}
				}
				// end command
				return true;
			}
		}
		else
		{
			// if we are moving, reset the timer.
			finishTimer.reset();
		}
		return false;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		GearManipulator.disableBeltPID();
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

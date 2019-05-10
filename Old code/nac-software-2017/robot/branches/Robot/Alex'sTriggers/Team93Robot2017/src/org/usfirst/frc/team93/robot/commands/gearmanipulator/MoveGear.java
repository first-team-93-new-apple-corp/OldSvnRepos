package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveGear extends Command
{
	
	double m_setpoint;
	double m_tolerance;
	
	public MoveGear(double setpoint, double tolerance)
	{
		m_setpoint = setpoint;
		m_tolerance = tolerance;
		requires(Robot.gearManipulator);
	}
	
	public MoveGear(double setpoint)
	{
		this(setpoint, 10.0);
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
		return GearManipulator.getBeltOnTarget() || ((GearManipulator.getReedSwitch() && ((m_setpoint < 100) || (m_setpoint > 5000)) && (Math.abs(GearManipulator.GEAR_BELT_PID.getError()) < 1000)));
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

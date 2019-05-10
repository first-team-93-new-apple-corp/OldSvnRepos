package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveGear extends Command {

	double m_setpoint;

	public MoveGear(double setpoint) {
		m_setpoint = setpoint;
	}

	public MoveGear(GearLocation location) {
		this(GearManipulator.getLocation(location));
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}

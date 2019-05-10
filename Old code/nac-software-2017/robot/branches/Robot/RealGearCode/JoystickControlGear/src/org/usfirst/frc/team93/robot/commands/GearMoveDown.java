package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import org.usfirst.frc.team93.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearMoveDown extends Command {

    public GearMoveDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearManipulator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!GearManipulator.GetAllClawState())
    	{
    		GearManipulator.CloseClaws();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	GearManipulator.GearMotor.set(GearManipulator.ManualSpeed * -1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return GearManipulator.BottomLimitSwitch.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	GearManipulator.GearMotor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	GearManipulator.GearMotor.set(0);
    }
}
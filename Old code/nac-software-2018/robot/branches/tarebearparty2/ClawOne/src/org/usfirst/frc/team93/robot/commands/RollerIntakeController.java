package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.RollerIntake;
import org.usfirst.frc.team93.robot.utilities.DriveChooser;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RollerIntakeController extends Command {

    public RollerIntakeController() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.rollerintake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RollerIntake.leftIntake.set(1);
    	RollerIntake.rightIntake.set(1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return RollerIntake.cubeLimit.get();
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	RollerIntake.leftIntake.set(0);
    	RollerIntake.rightIntake.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

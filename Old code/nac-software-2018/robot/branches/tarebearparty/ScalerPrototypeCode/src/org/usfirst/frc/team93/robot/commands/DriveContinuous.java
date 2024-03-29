package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.DriveChooser;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveContinuous extends Command {

    public DriveContinuous() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Drive.driveControlMode = (int)Robot.DriveMode.getSelected();
    	Drive.left.set(DriveChooser.getDriverL() * -1);
    	Drive.right.set(DriveChooser.getDriverR());
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

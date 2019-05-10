package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.SwerveControl;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwerveRotPIDControl extends Command {

    public SwerveRotPIDControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
		
		SwerveControl.flRotController.reset();
		SwerveControl.frRotController.reset();
		SwerveControl.blRotController.reset();
		SwerveControl.brRotController.reset();
		
		SwerveControl.flRotController.setSetpoint(SwerveControl.flPIDTargetTicks);
		SwerveControl.frRotController.setSetpoint(SwerveControl.frPIDTargetTicks);
		SwerveControl.blRotController.setSetpoint(SwerveControl.blPIDTargetTicks);
		SwerveControl.brRotController.setSetpoint(SwerveControl.brPIDTargetTicks);
		
		SwerveControl.flRotController.setAbsoluteTolerance(SwerveControl.pidTolerances);
		SwerveControl.frRotController.setAbsoluteTolerance(SwerveControl.pidTolerances);
		SwerveControl.blRotController.setAbsoluteTolerance(SwerveControl.pidTolerances);
		SwerveControl.brRotController.setAbsoluteTolerance(SwerveControl.pidTolerances);
		
		SwerveControl.flRotController.enable();
		SwerveControl.frRotController.enable();
		SwerveControl.blRotController.enable();
		SwerveControl.brRotController.enable();
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
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

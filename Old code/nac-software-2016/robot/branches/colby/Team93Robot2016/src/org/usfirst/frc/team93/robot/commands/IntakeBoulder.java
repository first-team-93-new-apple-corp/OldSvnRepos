package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.BoulderAcquaition;
import org.usfirst.frc.team93.robot.subsystems.IntegratedBallAcqShooter;
/**
 * Sets up commands for getting the boulder. 
 */
public class IntakeBoulder extends Command 
{

	IntegratedBallAcqShooter ball = new IntegratedBallAcqShooter();
    public IntakeBoulder() 
    {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ballAcqShoot);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    { 
    	 ball.openMechanicalStop();
    	 ball.lowerDozer();
    	 ball.turnRollers(1.0);
    	 ball.acquireBall();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	ball.turnRollers(0.25);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
    	if (ball.getBallDetect())
    	{
    		return true;
    	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	
    }
}

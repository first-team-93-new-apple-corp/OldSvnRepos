package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeFrisbeeAndShoot extends Command {
	int state;
	boolean isFinished;
	
	private Timer m_timer = new Timer();
	
    public IntakeFrisbeeAndShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 1;
    	isFinished = false;
    	m_timer.reset();
		m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!isFinished) {
    		switch(state) 
    		{
    			case 1:
    				RobotMap.intakeWheel.set(-1);
    					state++;
    				break;
    			case 2:
    				
    				if(m_timer.get() >= 1.5) 
    				{
    					RobotMap.intakeWheel.set(0);
    					state++;
    					RobotMap.shootActuator.set(DoubleSolenoid.Value.kReverse);
    				}
    				
    				
    			case 3:
    				if(m_timer.get() >= 2)
    				{
    					RobotMap.shootActuator.set(DoubleSolenoid.Value.kForward);
    					state = 1;
    					isFinished = true;
    				}
    		}
    	}	
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

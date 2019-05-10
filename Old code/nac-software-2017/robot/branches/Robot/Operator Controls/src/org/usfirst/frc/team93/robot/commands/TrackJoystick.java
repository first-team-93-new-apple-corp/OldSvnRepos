package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Gears;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class TrackJoystick extends Command 
{
	
	double rawAxis;
	double slowerRawAxis;
	int triggered;
	
	Boolean run;

    public TrackJoystick() 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gears);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	run = true;
    	triggered = 1;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	
    	if(run){
    		
    		if (OI.TrackJoystickTrigger.get())
    		{
    			
    			if(triggered == 1)
    			{
        			rawAxis = OI.Operator.getRawAxis(3);
        	
        			slowerRawAxis = rawAxis * .1;
        			if(slowerRawAxis<= .1)
        			{
        				slowerRawAxis = 0;
        				
        			}
        			else;
        	
        			Gears.GearLocation.set(slowerRawAxis);
        			
        		
    			}
    			else if(triggered == 2)
    			{
    				run = false;
    			}
    		}
    		else;
    	}
    	else;
        	
        }
    			
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return !run;
        
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	Gears.GearLocation.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	Gears.GearLocation.set(0);
    	

    }
}

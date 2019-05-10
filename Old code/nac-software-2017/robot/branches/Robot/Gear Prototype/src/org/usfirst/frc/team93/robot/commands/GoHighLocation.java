package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Gear;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GoHighLocation extends Command {
	boolean hasHitLimSwitch;  // has hit limit switch
	int numberOfTimesInEncRange;  //how many times it is in range of pot
    public GoHighLocation() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {

		hasHitLimSwitch = false;
		numberOfTimesInEncRange = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(!hasHitLimSwitch)
    	{
    		Gear.MoveGearAcq.set(0.5);
    		if (Gear.UpperSwitch.get())
    		{
    			hasHitLimSwitch = true;
    			Gear.GearPID.reset();
    			Gear.GearPID.enable();
    		}
    	}
    	else
    	{
    		if(Gear.GearPID.onTarget())
    		{
    			numberOfTimesInEncRange++;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        if(Gear.GearPID.onTarget() && numberOfTimesInEncRange >= 5)
        {
        	Gear.AcqLoc = 3;
        	Gear.GearPID.disable();
        	return true;
        }
        else
        {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Gear.GearPID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

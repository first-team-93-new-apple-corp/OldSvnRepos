package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Gear;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GoLowLocation extends Command {
	Boolean State;
	int counter;
    public GoLowLocation() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	State = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!State)
    	{
    		Gear.MoveGearAcq.set(-0.5);
    		if (Gear.UpperSwitch.get())
    		{
    			State = true;
    			Gear.GearPID.reset();
    			Gear.GearPID.enable();
    		}
    	}
    	else
    	{
    		if(Gear.GearPID.onTarget())
    		{
    			counter++;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(Gear.GearPID.onTarget() && counter >= 5)
        {
        	Gear.AcqLoc = 1;
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

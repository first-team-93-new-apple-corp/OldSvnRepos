package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Gear;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GoMidLocation extends Command {
	int WhatDo;
	Boolean Terminated;
	Boolean State;
	int counter;
    public GoMidLocation() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		// Get everything in a safe starting state.
    	WhatDo = Gear.AcqLoc;
    	Terminated = false;
    	State = false;
    	counter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(WhatDo == 2 && !State)
    	{
    		Terminated = true;

    	}
    	else if(WhatDo == 1 && !State)
    	{
    		Gear.MoveGearAcq.set(0.5);
    		if(Gear.MidSwitch.get())
    		{
    			State = true;
    			Gear.GearPID.reset();
    			Gear.GearPID.enable();
    		}
    	}
    	else if(WhatDo == 3 && !State)
    	{
    		Gear.MoveGearAcq.set(-0.5);
    		if (Gear.MidSwitch.get())
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
        if( Terminated || counter >= 5)
        {
        	Gear.AcqLoc = 2;
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

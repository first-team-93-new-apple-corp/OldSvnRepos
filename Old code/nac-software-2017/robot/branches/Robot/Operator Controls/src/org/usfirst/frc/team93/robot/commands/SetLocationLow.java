package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Gears;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetLocationLow extends Command {
	boolean Terminated;
    public SetLocationLow() 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gears);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	if(Gears.gearManipLoc == 1)
    	{
    		Terminated = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(!Terminated)
    	{
    		Gears.GearLocation.set(-1);
    		if(Gears.HighSwitch.get())
    		{
    			Terminated = true;
    			Gears.GearLocGet.reset();
    			Gears.gearManipLoc = 1;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return Terminated;
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

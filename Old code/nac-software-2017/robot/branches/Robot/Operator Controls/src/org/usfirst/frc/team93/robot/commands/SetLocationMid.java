package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Gears;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetLocationMid extends Command {
Boolean Terminated;
int OnTargetAmount;
    public SetLocationMid() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	OnTargetAmount = 0;
    	Terminated = false;
    	if(Gears.gearManipLoc == 2)
    	{
    		Terminated = true;
    	}
    	else if(Gears.gearManipLoc == 1)
    	{
    		Gears.GearLocGet.setSetpoint(Gears.MidPIDLocation);
    		Gears.GearLocGet.enable();
    	}
    	else if(Gears.gearManipLoc == 3)
    	{
    		Gears.GearLocGet.setSetpoint(Gears.TopPIDLocation - Gears.MidPIDLocation);
    		Gears.GearLocGet.enable();
    	}
    	else
    	{
    		Terminated = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Gears.GearLocGet.onTarget())
    	{
    		OnTargetAmount++;
    	}
    	if(OnTargetAmount >= 5)
    	{
    		Terminated = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Terminated;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Gears.GearLocGet.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Gears.GearLocGet.disable();
    }
}

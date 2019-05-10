package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearLocationTwo extends Command {
	Boolean isFinished;
	double PIDTotal;
	int onTargetTotal;

    public GearLocationTwo() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearManipulator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!GearManipulator.GetAllClawState())
    	{
    		GearManipulator.CloseClaws();
    	}
    	if (GearManipulator.location == 2)
    	{
    		isFinished = true;
    		PIDTotal = 0;
    	}
    	else
    	{
    		isFinished = false;
    		if(GearManipulator.location == 1)
    		{
    			PIDTotal = GearManipulator.EncoderTicks12;
    		}
    		else if(GearManipulator.location == 3)
    		{
    			PIDTotal = (GearManipulator.EncoderTicks23) * -1;
    		}
    		else if (GearManipulator.location == 4)
    		{
    			PIDTotal = (GearManipulator.EncoderTicks23 + GearManipulator.EncoderTicks34) * -1;
    		}
    	}
    	onTargetTotal = 0;
    	GearManipulator.MoveGearPID.setSetpoint(PIDTotal);
    	GearManipulator.MoveGearPID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(GearManipulator.MoveGearPID.onTarget())
    	{
    		onTargetTotal++;
    	}
    	if (onTargetTotal >= 5)
    	{
    		isFinished = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	GearManipulator.MoveGearPID.disable();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

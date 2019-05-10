package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualControl extends Command {

    public ManualControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(OI.Operator.getRawAxis(3) >= 0.1)
    	{
    		if(!GearManipulator.TopLimitSwitch.get()) 
    		{
    			GearManipulator.GearMotor.set(OI.Operator.getRawAxis(3));
    		}
    	}
    	else if(OI.Operator.getRawAxis(3) <= -0.1)
    	{
    		if(!GearManipulator.BottomLimitSwitch.get())
    		{
    			GearManipulator.GearMotor.set(OI.Operator.getRawAxis(3));
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	GearManipulator.GearMotor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	GearManipulator.GearMotor.set(0);

    }
}

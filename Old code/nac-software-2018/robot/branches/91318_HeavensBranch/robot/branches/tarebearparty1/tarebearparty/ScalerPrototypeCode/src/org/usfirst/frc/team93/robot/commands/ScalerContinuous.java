package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Scaler;
import org.usfirst.frc.team93.robot.utilities.DriveChooser;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ScalerContinuous extends Command {

    public ScalerContinuous() {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.scaler);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double MotorSetVal;
    	double JoyInput = (DriveChooser.deadzone(OI.operator.getRawAxis(1), 0.1));
    	if(JoyInput > 0)
    	{
    		MotorSetVal = Scaler.upwardsMultiplier(Scaler.GetLocationPercentage(), 80, 0.2);
    	}
    	else if (JoyInput < 0)
    	{
    		MotorSetVal = Scaler.downwardsMultiplier(Scaler.GetLocationPercentage(), 20, 0.2);
    	}
    	else
    	{
    		MotorSetVal = 0;
    	}
    	if(MotorSetVal > 0 && Scaler.topLimit.get()) 
    	{
    		Scaler.scalerMotor.set(0);
    		Scaler.topEncoderTick = Scaler.ScalerEncoder.getRaw();
    	}
    	else if(MotorSetVal < 0 && Scaler.bottomLimit.get())
    	{
    		Scaler.scalerMotor.set(0);
    		Scaler.bottomEncoderTick = Scaler.ScalerEncoder.getRaw();
    	}
    	else
    	{
    		Scaler.scalerMotor.set(MotorSetVal);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}

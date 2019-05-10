package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.Robot;
import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.BinSnatcher;

/**
 *
 */
public class BinSnatch extends Command {
    boolean finished = false;

    public BinSnatch() {
        requires(Robot.binSnatcher);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        RobotMap.rakeMotorEncoder.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (RobotMap.rakeLimitSwitch.get()) {
            for (int i = 0; i <= BinSnatcher.timeFromSwitchtoRake; i += 20) {
                // freezes the command until timeFromSwitchtoRake milliseconds
                // have passed
            }
            if (RobotMap.rakeMotorEncoder.get() < BinSnatcher.oneEncoderRotation) {
                BinSnatcher.rakeMotorSet(BinSnatcher.rakeMax);
            }
            else {
                BinSnatcher.rakeMotorSet(BinSnatcher.rakeMin);
                finished = true;
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}

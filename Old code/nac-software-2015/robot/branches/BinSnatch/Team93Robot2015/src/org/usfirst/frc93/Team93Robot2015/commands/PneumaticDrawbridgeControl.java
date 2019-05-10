package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.BinSnatcher;

/**
 *
 */

// Note, this is in progress
public class PneumaticDrawbridgeControl extends Command {

    boolean state;

    public PneumaticDrawbridgeControl(boolean b_state) {
        state = b_state;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        if (BinSnatcher.solenoidsDeployed == false) {
            // RobotMap.drawbridgePneumaticArmLock
            // .set(DoubleSolenoid.Value.kReverse);
            RobotMap.drawbridgePneumaticArm.set(DoubleSolenoid.Value.kForward);
            // RobotMap.drawbridgePneumaticArmTwo
            // .set(DoubleSolenoid.Value.kForward);
        }
        else if (BinSnatcher.solenoidsDeployed == true) {
            RobotMap.drawbridgePneumaticArm.set(DoubleSolenoid.Value.kReverse);
            // RobotMap.drawbridgePneumaticArmLock
            // .set(DoubleSolenoid.Value.kForward);
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
        RobotMap.drawbridgePneumaticArm.set(DoubleSolenoid.Value.kOff);
        // RobotMap.drawbridgePneumaticArmTwo.set(DoubleSolenoid.Value.kOff);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}

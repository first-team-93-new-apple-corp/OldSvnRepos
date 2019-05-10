// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package org.usfirst.frc93.FinalRobot.commands;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc93.FinalRobot.Robot;
import org.usfirst.frc93.FinalRobot.RobotMap;
/**
 *
 */
public class DriveCommand extends PIDCommand {
    int driveTimeCounter = 0;
    public DriveCommand(double distance) {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        super("DriveCommand",0.06f, 0.003f, 0.3f, 0.02);
        getPIDController().setContinuous(false);
        getPIDController().setAbsoluteTolerance(0.2);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drive);
        getPIDController().setSetpoint(distance);
    }
    public void setDistance(double setpoint){
        getPIDController().setSetpoint(setpoint);
    }
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
        return RobotMap.driveEncoder.pidGet();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
    }
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
         RobotMap.driveOut.pidWrite(output);
         return;
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
        //RobotMap.driveFrontRight.pidWrite(output);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
       
    }
    // Called just before this Command runs the first time
    protected void initialize() {
        //Starts the PID controller and Encoders
        RobotMap.driveEncoder.start();
        getPIDController().enable();
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        SmartDashboard.putNumber("Drive Encoder Distance ",RobotMap.driveEncoder.get());
        SmartDashboard.putNumber("Drive Encoder Error ",RobotMap.driveOut.getOutput()*-1.0f);
        RobotMap.driveRobotDrive.mecanumDrive_Cartesian(0, RobotMap.driveOut.getOutput()*-1.0f, 0, 0);
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (getPIDController().getError() <= 150 && getPIDController().getError() >= -150) {
            driveTimeCounter++;
        } //resets the time counter to 0 if error is too large
        else {
            driveTimeCounter = 0;
        }
        //25 = .5 seconds
        if (driveTimeCounter >= 75) {
            return true;
        } else {
            return false;
        }
    }
    // Called once after isFinished returns true
    protected void end() {
        RobotMap.driveGyro.reset();
        RobotMap.driveEncoder.reset();
        getPIDController().disable();
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

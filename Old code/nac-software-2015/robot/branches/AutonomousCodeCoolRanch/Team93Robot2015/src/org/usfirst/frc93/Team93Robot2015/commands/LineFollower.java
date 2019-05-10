package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Drive;

/**
 *
 */
public class LineFollower extends Command {
    double lineFollowerLeftValue = RobotMap.lineFollowerOne.getVoltage();
    double lineFollowerMiddleValue = RobotMap.lineFollowerTwo.getVoltage();
    double lineFollowerRightValue = RobotMap.lineFollowerThree.getVoltage();

    boolean lineFollowerLeftOnCourse;
    boolean lineFollowerMiddleOnCourse;
    boolean lineFollowerRightOnCourse;

    public LineFollower() {

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
        // Test code, actual values will need to be tuned/adjusted.
        if (lineFollowerLeftValue <= 4.7) {
            lineFollowerLeftOnCourse = true;
        }
        else {
            lineFollowerLeftOnCourse = false;
        }

        if (lineFollowerMiddleValue <= 4.7) {
            lineFollowerMiddleOnCourse = true;
        }
        else {
            lineFollowerMiddleOnCourse = false;
        }

        if (lineFollowerRightValue <= 4.7) {
            lineFollowerRightOnCourse = true;
        }
        else {
            lineFollowerRightOnCourse = false;
        }

        // Test code, actual values will need to be tuned/adjusted.
        if (lineFollowerLeftOnCourse == true
                && lineFollowerRightOnCourse == true
                && lineFollowerMiddleOnCourse == true) {
            Drive.setAllMotors(0);
            System.out.println("Driving Straight");
        }
        if (lineFollowerLeftOnCourse == false) {
            Drive.setLeftMotors(1);
            System.out.println("Shifting Left");
        }
        if (lineFollowerRightOnCourse == false) {
            Drive.setRightMotors(1);
            System.out.println("Shifting Right");
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

package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc93.Team93Robot2015.RobotMap;
import org.usfirst.frc93.Team93Robot2015.subsystems.Elevator;

/**
 * This is a class that displays the elevator information on the smart
 * dashboard.
 */
public class DisplayElevatorPosition extends Command {

    double elevatorPositionBottom = -50.0;
    double elevatorPositionGround = 0.0;
    double elevatorPositionOne = 273.0;
    double elevatorPositionTwo = 486.0;
    double elevatorPositionThree = 670.0;
    double elevatorPositionFour = 823.0;
    double elevatorPositionFive = 987.0;
    double elevatorPositionSix = 1034.0;
    double elevatorPositionTop = 1294.0;

    double[] elevatorPositions = { elevatorPositionBottom,
            elevatorPositionGround, elevatorPositionOne, elevatorPositionTwo,
            elevatorPositionThree, elevatorPositionFour, elevatorPositionFive,
            elevatorPositionSix, elevatorPositionTop };

    boolean levelGround = false;
    boolean levelOne = false;
    boolean levelTwo = false;
    boolean levelThree = false;
    boolean levelFour = false;
    boolean levelFive = false;
    boolean levelSix = false;

    public DisplayElevatorPosition() {
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

        double elevatorEncoderPosition = Elevator.getEncoder();

        int elevatorRegion = searchElevatorPositions(elevatorEncoderPosition);

        String elevatorRegionName = convertElevatorRegionToString(elevatorRegion);

        SmartDashboard.putString("Elevator Position : ", elevatorRegionName);

        levelGround = isLevel(elevatorEncoderPosition, 0);
        levelOne = isLevel(elevatorEncoderPosition, 1);
        levelTwo = isLevel(elevatorEncoderPosition, 2);
        levelThree = isLevel(elevatorEncoderPosition, 3);
        levelFour = isLevel(elevatorEncoderPosition, 4);
        levelFive = isLevel(elevatorEncoderPosition, 5);
        levelSix = isLevel(elevatorEncoderPosition, 6);

        SmartDashboard.putNumber("Right Hand Voltage : ",
                RobotMap.RIGHT_HAND_ENCODER.getVoltage());
        SmartDashboard.putNumber("Left Hand Voltage : ",
                RobotMap.LEFT_HAND_ENCODER.getVoltage());

        SmartDashboard.putBoolean("Ground Level : ", levelGround);
        SmartDashboard.putBoolean("Tote Level One : ", levelOne);
        SmartDashboard.putBoolean("Tote Level Two : ", levelTwo);
        SmartDashboard.putBoolean("Tote Level Three : ", levelThree);
        SmartDashboard.putBoolean("Tote Level Four : ", levelFour);
        SmartDashboard.putBoolean("Tote Level Five : ", levelFive);
        SmartDashboard.putBoolean("Tote Level Six : ", levelSix);
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
        this.end();
    }

    /**
     * Checks if the elevator is in a certain level.
     * 
     * @param level
     *            The level to check.
     * @return Whether or not the elevator is in the specified level.
     */
    private boolean isLevel(double value, int level) {
        if (level < 0) {
            level = 0;
        }

        if (level > elevatorPositions.length - 1) {
            level = elevatorPositions.length - 1;
        }
        return (checkBetween(value, elevatorPositions[level],
                elevatorPositions[level + 1]));
    }

    /**
     * Checks if a value is between two other values.
     * 
     * @param value
     *            The value to check.
     * @param bottomLimit
     *            The lower limit.
     * @param topLimit
     *            The upper limit.
     * @return Whether or not the value is between those two values.
     */
    private boolean checkBetween(double value, double bottomLimit,
            double topLimit) {
        double lowerLimit;
        double upperLimit;

        if (bottomLimit <= topLimit) {
            lowerLimit = bottomLimit;
            upperLimit = topLimit;
        }
        else {
            lowerLimit = topLimit;
            upperLimit = bottomLimit;
        }

        return ((value > lowerLimit) && (value <= upperLimit));
    }

    /**
     * Searches the elevator position array to see which positions the value is
     * between.
     * 
     * @param value
     *            The position to search the array with.
     * @return The region that the position lies in.
     */
    private int searchElevatorPositions(double value) {
        int elevatorRegion = -1;
        for (int index = 0; index < elevatorPositions.length - 1; index++) {
            if (isLevel(value, index)) {
                elevatorRegion = index;
            }
        }
        return (elevatorRegion);
    }

    /**
     * Converts an elevator region int to a String name.
     * 
     * @param region
     *            The region to convert.
     * @return The String name of the region.
     */
    private String convertElevatorRegionToString(int region) {

        region += 1;

        String regionString;
        switch (region) {
        case 0:
            regionString = "Bottom";
            break;
        case 1:
            regionString = "Ground";
            break;
        case 2:
            regionString = "One";
            break;
        case 3:
            regionString = "Two";
            break;
        case 4:
            regionString = "Three";
            break;
        case 5:
            regionString = "Four";
            break;
        case 6:
            regionString = "Five";
            break;
        case 7:
            regionString = "Six";
            break;
        default:
            regionString = "Unknown";
            break;
        }
        return (regionString);
    }
}
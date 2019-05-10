package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 *
 */
public class AutonomousSelector extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public static boolean autonomousSelectorOne() {
        return RobotMap.autonomousSelectorOne.get();
    }

    public static boolean autonomousSelectorTwo() {
        return RobotMap.autonomousSelectorTwo.get();
    }

    public static boolean autonomousSelectorThree() {
        return RobotMap.autonomousSelectorThree.get();
    }

    public static boolean autonomousSelectorFour() {
        return RobotMap.autonomousSelectorFour.get();
    }

    // public static boolean autonomousSelectorFive() {
    // return RobotMap.autonomousSelectorFive.get();
    // }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public static int getAutoSelect() {
        int autoSelect = 0;
        if (autonomousSelectorOne()) {
            autoSelect += 1;
        }
        if (autonomousSelectorTwo()) {
            autoSelect += 2;
        }
        if (autonomousSelectorThree()) {
            autoSelect += 4;
        }
        if (autonomousSelectorFour()) {
            autoSelect += 8;
        }
        return (autoSelect);
    }

    public static int getStartSelect() {
        int startSelect = 0;
        // if (autonomousSelectorFive()) {
        // startSelect = 1;
        // }
        return (startSelect);
    }
}
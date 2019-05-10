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
        return RobotMap.AUTONOMOUS_SELECTOR_ONE.get();
    }

    public static boolean autonomousSelectorTwo() {
        return RobotMap.AUTONOMOUS_SELECTOR_TWO.get();
    }

    public static boolean autonomousSelectorThree() {
        return RobotMap.AUTONOMOUS_SELECTOR_THREE.get();
    }

    public static boolean autonomousSelectorFour() {
        return RobotMap.AUTONOMOUS_SELECTOR_FOUR.get();
    }

    // public static boolean autonomousSelectorFive() {
    // return RobotMap.autonomousSelectorFive.get();
    // }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public static enum EAutoSelect {
        ePushToteToAuto, eStackThreeTotes, eBinSnatcher, eStackToteBinToAuto
    }

    public static EAutoSelect getAutoSelect() {
        EAutoSelect AutoSelect = EAutoSelect.ePushToteToAuto;
        if (autonomousSelectorOne()) {
            AutoSelect = EAutoSelect.eStackThreeTotes;
        }
        else if (autonomousSelectorTwo()) {
            AutoSelect = EAutoSelect.eBinSnatcher;
        }
        else if (autonomousSelectorThree()) {
            AutoSelect = EAutoSelect.eStackToteBinToAuto;
        }
        return AutoSelect;
    }

    // public static int getAutoSelect() {
    // int autoSelect = 0;
    // if (autonomousSelectorOne()) {
    // autoSelect += 1;
    // }
    // if (autonomousSelectorTwo()) {
    // autoSelect += 2;
    // }
    // if (autonomousSelectorThree()) {
    // autoSelect += 4;
    // }
    // return (autoSelect);
    // }

    public static int getStartSelect() {
        int startSelect = 0;
        if (autonomousSelectorFour()) {
            startSelect = 1;
        }
        return startSelect;
    }
}

package org.usfirst.frc93.Team93Robot2015.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc93.Team93Robot2015.OI;
import org.usfirst.frc93.Team93Robot2015.RobotMap;

/**
 *
 */

public class BinSnatcher extends Subsystem {

    // Variables
    public static int pneumaticDeployIdentifier = 0;
    public static int pneumaticRetractIdentifier = 0;
    public static boolean solenoidsDeployed = false;
    public static boolean solenoidsRetracted = false;

    public static double rakeAcceleration = 0.0625;
    public static double rakeMax = 0.75;
    public static double rakeMin = 0;
    public static double rakeStartSpeed = 0.125;
    public static double rakeSpeed = 0;

    public static double oneEncoderRotation = 256;// WILL NEED TO CHANGE VALUE
    public static long timeFromSwitchtoRake;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void pneumaticStatusGoverner() {

        if (OI.operator.getRawButton(1) == true) {
            pneumaticDeployIdentifier = 1;
        }
        if (OI.operator.getRawButton(1) == false) {
            pneumaticDeployIdentifier = 0;
        }
        if (OI.operator.getRawButton(2) == true) {
            pneumaticRetractIdentifier = 1;
        }
        if (OI.operator.getRawButton(2) == false) {
            pneumaticRetractIdentifier = 0;
        }

        // This line checks if the two Solenoids are fully deployed
        if (RobotMap.drawbridgePneumaticArm.get() == DoubleSolenoid.Value.kForward) {
            solenoidsDeployed = true;
            solenoidsRetracted = false;
        }

        if (RobotMap.drawbridgePneumaticArm.get() == DoubleSolenoid.Value.kReverse) {
            solenoidsRetracted = true;
            solenoidsDeployed = false;
        }

        if (RobotMap.drawbridgePneumaticArm.get() == DoubleSolenoid.Value.kOff) {
            solenoidsRetracted = false;
            solenoidsDeployed = false;
        }

        // if (RobotMap.drawbridgePneumaticArm.get() ==
        // DoubleSolenoid.Value.kReverse) {
        // solenoidsRetracted = false;
        // solenoidsDeployed = false;
        // }

        // if (RobotMap.drawbridgePneumaticArm.get() ==
        // DoubleSolenoid.Value.kForward) {
        // solenoidsRetracted = false;
        // solenoidsDeployed = false;
        // }
    }

    public static void rakeMotorSet(double value) {
        RobotMap.rakeMotor.set(value);
    }

}

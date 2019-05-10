package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchManipulator extends Subsystem {

    public static DoubleSolenoid extender;
    public static DoubleSolenoid grabber1;
    public static DoubleSolenoid grabber2;

    public HatchManipulator(){
        extender = new DoubleSolenoid(RobotMap.ExtenderForward, RobotMap.ExtenderReverse);
        grabber1 = new DoubleSolenoid(RobotMap.Grabber1Forward, RobotMap.Grabber1Reverse);
        grabber2 = new DoubleSolenoid(RobotMap.Grabber2Forward, RobotMap.Grabber2Reverse);
    }

    public void initDefaultCommand() {
    }
}

package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.other.CANVictorSPX;

public class CargoManipulator extends Subsystem {

    public static CANVictorSPX intakeLeft;
    public static CANVictorSPX intakeRight;
    public static CANVictorSPX hinge;
    public static final double inchesToTicksConversion = 10;
    public static final double Tolerance = 0.1;
    public static Encoder hingeEncoder;

    public static PIDController hingePID;

    public CargoManipulator(){

        intakeLeft = new CANVictorSPX(RobotMap.IntakeSCLeft);
        intakeRight = new CANVictorSPX(RobotMap.IntakeSCRight);
        hinge = new CANVictorSPX(RobotMap.HingeSC);

        hingeEncoder = new Encoder(RobotMap.HingeEncoderA, RobotMap.HingeEncoderB);

        hingePID = new PIDController(0.1, 0.1, 0.1, hingeEncoder, hinge);
        
    }

    public void initDefaultCommand() {
    }
}

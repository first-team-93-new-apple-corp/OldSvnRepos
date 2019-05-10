package nacteam93.robot2014;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import nacteam93.robot2014.wpilibextensions.*;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    /*
     * Tuning Constants
     */
    
    public static double retractionDirection = -1.0;
    
    /*
     * PWM Outputs
     */    
    public static SpeedController driveFrontLeft = new Talon(1,1);    
    public static SpeedController driveBackLeft = new Talon(1,2);
    public static SpeedController driveBackRight = new Talon(1,3);
    public static SpeedController driveFrontRight = new Talon(1,4);
    
    public static SpeedController armLeft = new Talon(1,5);
    public static SpeedController armRight = new Talon(1,6);
    
    public static SpeedController shooterWinch = new Talon(1,7);
    
    public static SpeedController rollerOne = new Talon(1,8);
    public static SpeedController rollerTwo = new Talon(1,9);
    
    /*
     * Digital I/O
     */
    public static DigitalInput autoSelectorOne = new DigitalInput(2,9);
    public static DigitalInput autoSelectorTwo = new DigitalInput(2,10);
    public static DigitalInput autoSelectorThree = new DigitalInput(2,11);
    public static DigitalInput autoSelectorFour = new DigitalInput(2,12);
    public static DigitalInput autoSelectorFive = new DigitalInput(2,13);
    public static DigitalInput autoSelectorSix = new DigitalInput(2,14);
    
    public static DigitalInput ballPresentOne = new DigitalInput(1,5);
    public static DigitalInput ballPresentTwo = new DigitalInput(1,6);
    public static DigitalInput shooterRetractionIndex = new DigitalInput(1,7);
    public static DigitalInput pivoterMinimum = new DigitalInput(1,8);
    public static DigitalInput pivoterMaximum = new DigitalInput(1,9);
    public static DigitalInput shooterRetrationSwitch = new DigitalInput(1,10);


    
    public static Encoder driveLeftEncoder = new Encoder(2, 1, 2, 2);//PS talk to Zach about
    public static Encoder driveRightEncoder = new Encoder(2, 3, 2, 4); //These are in 6-pin housings, and so they're actually in two seperate digital IO slots. Good chance these inputs won't work
    public static Encoder shooterEncoder = new Encoder(2, 5, 2, 6);
    
    /*
    * Analogue I/O
    */
    //TODO: Test on robot with two known angles to find actual offset and scaling numbers
    public static Team93Potentiometer armPotentiometer = new Team93Potentiometer(1, 1, 134.632, -105.263);

    /*
    * Solenoids
    */
    public static Solenoid driveShift = new Solenoid(1,1);
    public static Solenoid shooterTrigger = new Solenoid(1,2);
    public static Solenoid armBrake = new Solenoid(1,3);

    
    public static Compressor CompressorOne = new Compressor(1,1);
    
    /*
    * Combination Objects
    */
    public static SpeedControllerGroup armMotors;
    public static SpeedControllerGroup driveLeft;
    public static SpeedControllerGroup driveRight;
    public static SpeedControllerGroup driveAll;
    public static PIDSourceGroup driveSourceAll;
    
    /*
    * PID Controllers
    */
    public static PIDController armPID;
    public static PIDController driveMasterPID;
    public static PIDController PIDLeft;
    public static PIDController PIDRight;
    public static PIDController winchPID;

    
    public static void initialize() {        
        driveLeftEncoder.start();
        driveLeftEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance);
        driveRightEncoder.start();
        driveRightEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance);
        shooterEncoder.start();
        shooterEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance);

        //The left encoder counts reverse, if it needs to be used, pass -1 for the gain.
        driveSourceAll = new PIDSourceGroup(driveLeftEncoder, driveRightEncoder, 0.0, 1.0);         
        
        armMotors = new SpeedControllerGroup(armRight, armLeft, -0.6, 0.6);
        driveRight = new SpeedControllerGroup(driveFrontRight, driveBackRight, -1.0, -1.0);
        driveLeft = new SpeedControllerGroup(driveFrontLeft, driveBackLeft, 1.0, 1.0);        
        driveAll = new SpeedControllerGroup(driveLeft, driveRight, -1.0, -1.0);
        
        
        //TODO: Test on robot to add optimal P, I and D values.
        armPID = new PIDController(0.15, 0.001, 0.0, armPotentiometer, armMotors);
        driveMasterPID = new PIDController(0.1, 0.001, 0.0, driveSourceAll, driveAll);
        PIDLeft = new PIDController(0.1, 0.001, 0.0, driveLeftEncoder, driveLeft);
        PIDRight = new PIDController(0.1, 0.001, 0.0, driveRightEncoder, driveRight);
        winchPID = new PIDController(0.1, 0.001, 0.0, shooterEncoder, shooterWinch);
        
        
        
        LiveWindow.addActuator("Drive","Front Left", (Talon) driveFrontLeft);
        LiveWindow.addActuator("Drive","Front Right", (Talon) driveFrontRight);
        LiveWindow.addActuator("Drive","Back Left", (Talon) driveBackLeft);
        LiveWindow.addActuator("Drive","Back Right", (Talon) driveBackRight);
        LiveWindow.addActuator("Drive","Shift Right", (Solenoid) driveShift);
        LiveWindow.addActuator("Drive","Shift Left", (Solenoid) driveShift);
        LiveWindow.addSensor("Drive","Encoder Left", (Encoder) driveLeftEncoder);
        LiveWindow.addSensor("Drive","Encoder Right", (Encoder) driveRightEncoder);
        
        LiveWindow.addActuator("Shooter","Trigger Solenoid", (Solenoid) shooterTrigger);
        LiveWindow.addActuator("Shooter","Winch Motor", (Talon) shooterWinch);
        LiveWindow.addSensor("Shooter","Winch Encoder", (Encoder) shooterEncoder);
        
        LiveWindow.addActuator("Bacquisition","Motor One", (Talon) rollerOne);
        LiveWindow.addActuator("Bacquisition","Motor Two", (Talon) rollerTwo);
        LiveWindow.addSensor("Bacquisition","Possession Switch One", (DigitalInput) ballPresentOne);
        LiveWindow.addSensor("Bacquisition","Possession Switch One", (DigitalInput) ballPresentTwo);
        
        LiveWindow.addActuator("Pivoter","Arm Right", (Talon) armRight);
        LiveWindow.addSensor("Pivoter","ArmLeft", (Talon) armLeft);
        LiveWindow.addSensor("Pivoter","Angle", (Team93Potentiometer) armPotentiometer);
        LiveWindow.addSensor("Pivoter","Minimum Switch", (DigitalInput) pivoterMinimum);
        LiveWindow.addSensor("Pivoter","Maximum Switch", (DigitalInput) pivoterMaximum);
    }    
}
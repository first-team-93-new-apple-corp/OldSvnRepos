package nacteam93.robot2014;
import edu.wpi.first.wpilibj.*;
import nacteam93.robot2014.wpilibextensions.*;
import nacteam93.robot2014.wpilibextensions.Team93Potentiometer;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    /*
     * Drive
     */
/*    public static DigitalInput switch1;
    public static DigitalInput switch2;
    public static Victor motor1;
    public static Victor motor2;
    
    public static Encoder encoderWheel1 = new Encoder(1,5,1,6);
    public static PIDController myPIDcontroller = new PIDController(1,1,1,encoderWheel1,motor1);
  */  
    //    public static Victor motor3;    
    
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    /*
     * PWM Outputs
     */    
    public static SpeedController driveFrontLeft;
    public static SpeedController driveBackLeft;
    public static SpeedController driveFrontRight;
    public static SpeedController driveBackRight;
    public static SpeedController shooterRetractionMotor;
    public static SpeedController armLeft;
    public static SpeedController armRight;
    
    /*
     * Solenoids
     */
    public static Solenoid shooterTrigger = new Solenoid(1,1);
    
    /*
     * Digital I/O
     */
    public static DigitalInput shooterRetractionGate;
    public static Encoder EncoderRight;
    public static Encoder EncoderLeft;

    
    /*
    public static PIDController PIDOne = new PIDController(0.1, 0.001, 0.0, sourceAll, driveAll);
     * Analogue I/O
     */
    //TODO: Test on robot with two known angles to find actual offset and scaling numbers
    public static Team93Potentiometer armPotentiometer = new Team93Potentiometer(1,1,1,0);

    /*
    * Combination Objects
    */
    public static SpeedControllerGroup armMotors;
    public static PIDSourceGroup sourceAll;    
    public static SpeedControllerGroup driveLeft;
    public static SpeedControllerGroup driveRight;
    public static SpeedControllerGroup driveAll;
    public static PIDSourceGroup driveSourceAll;
    
    /*
    * PID Controllers
    */       
    public static PIDController armPID;
    public static PIDController PIDOne;
    public static PIDController PIDLeft;
    public static PIDController PIDRight;
    
    public static void initialize(){

        shooterRetractionGate = new DigitalInput(1,1);
        EncoderRight = new Encoder(1, 8, 1, 9);//PS talk to Zach about
        EncoderLeft = new Encoder(1, 5, 1, 6); //These are in 6-pin housings, and so they're actually in two seperate digital IO slots. Good chance these inputs won't work
        
        
        driveFrontLeft = new Victor(1,1);
        driveBackLeft = new Victor(1,2);
        driveFrontRight = new Victor(1,3);
        driveBackRight = new Victor(1,4);
        shooterRetractionMotor = new Victor(1,5);
        armLeft = new Victor(1,6);
        armRight = new Victor(1,7);
        
        
        driveSourceAll = new PIDSourceGroup(EncoderLeft, EncoderRight, 0.75, 0.75);
        
        armMotors = new SpeedControllerGroup(armRight, armLeft);
        driveRight = new SpeedControllerGroup(driveFrontRight, driveBackRight, 1.0, 1.0);
        driveLeft = new SpeedControllerGroup(driveFrontLeft, driveBackLeft, 1.0, 1.0);        
        driveAll = new SpeedControllerGroup(driveLeft, driveRight, 1.0, 1.0);
        
        
        //TODO: Test on robot to add optimal P, I and D values.
        armPID = new PIDController(1.0f,1.0f,1.0f,armPotentiometer,armMotors);        
        PIDOne = new PIDController(0.1, 0.001, 0.0, driveSourceAll, driveAll);
        PIDLeft = new PIDController(0.001, 0.000, 0.0, EncoderLeft, driveLeft);
        PIDRight = new PIDController(0.1, 0.001, 0.0, EncoderRight, driveRight);
        
        EncoderLeft.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance);
        EncoderRight.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance);
        EncoderLeft.setDistancePerPulse(1.0);
        EncoderRight.setDistancePerPulse(1.0);
        
        EncoderLeft.setReverseDirection(true); // if need to flip, change true to false
        EncoderRight.setReverseDirection(true);// if need to flip, change true to false
    }
    
}
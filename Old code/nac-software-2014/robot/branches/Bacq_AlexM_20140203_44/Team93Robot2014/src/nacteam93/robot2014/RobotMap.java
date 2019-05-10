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
     * PWM Outputs
     */    
    public static Talon driveFrontLeft = new Talon(1,1);
     
    public static Talon driveBackLeft = new Talon(1,2);
    
    public static Talon driveFrontRight = new Talon(1,3);
    public static Talon driveBackRight = new Talon(1,4);
    public static Talon shooterRetractionMotor = new Talon(1,5);
    public static Talon armLeft = new Talon(1,6);
    public static Talon armRight = new Talon(1,7);
    
    /*
     * Solenoids
     */
    public static Solenoid shooterTrigger = new Solenoid(1,1);
    
    /*
     * Digital I/O
     */
    public static DigitalInput shooterRetractionGate = new DigitalInput(1,1);
    public static Encoder EncoderRight = new Encoder(1, 4, 1, 5);//PS talk to Zach about
    public static Encoder EncoderLeft = new Encoder(1, 2, 1, 3); //These are in 6-pin housings, and so they're actually in two seperate digital IO slots. Good chance these inputs won't work

    
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
        driveSourceAll = new PIDSourceGroup(EncoderLeft, EncoderRight, 0.75, 0.75);
        
        
        armMotors = new SpeedControllerGroup(armRight, armLeft);
        driveRight = new SpeedControllerGroup(driveFrontRight, driveBackRight, 0.75, 0.75);
        driveLeft = new SpeedControllerGroup(driveFrontLeft, driveBackLeft, 0.75, 0.75);        
        driveAll = new SpeedControllerGroup(driveLeft, driveRight, 0.75, 0.75);
        
        
        //TODO: Test on robot to add optimal P, I and D values.
        armPID = new PIDController(1.0f,1.0f,1.0f,armPotentiometer,armMotors);        
        PIDOne = new PIDController(0.1, 0.001, 0.0, driveSourceAll, driveAll);
        PIDLeft = new PIDController(0.1, 0.001, 0.0, EncoderLeft, driveLeft);
        PIDRight = new PIDController(0.1, 0.001, 0.0, EncoderRight, driveRight);
        
    }
    
}
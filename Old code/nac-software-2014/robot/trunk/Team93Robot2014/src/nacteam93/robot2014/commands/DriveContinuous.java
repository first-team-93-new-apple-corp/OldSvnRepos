package nacteam93.robot2014.commands;

import nacteam93.robot2014.JoystickValue;
import edu.wpi.first.wpilibj.Joystick;
import nacteam93.robot2014.OI;
import nacteam93.robot2014.RobotMap;

/**
 * @author NEW Apple Corpses Team 93 Controls
 */

//The function of this command is to take an input from the joysticks, and set the Driving SpeedControllers to those values.

public class DriveContinuous extends CommandBase {

    private final static double deadzone = 0.05;//Set to 0.05 to eliminate errors from the joystick reset
    private double driverLY;
    private double driverRY;
    
    /**
     * The Constructor for this class.
     */
    public DriveContinuous() {
        requires(CommandBase.drive);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
         
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        //sets the values of driverLY and driverRY to the new values given by JoystickValue.update(OI.driver, OI.DRIVER_JOYSTICK_AXIS, deadzone).
        driverLY = JoystickValue.update(OI.driver, OI.DRIVER_JOYSTICK_AXIS_LY, deadzone);
        driverRY = JoystickValue.update(OI.driver, OI.DRIVER_JOYSTICK_AXIS_RY, deadzone);
        
        // This sets the motors to go off of what they read off of the controller's axes
        RobotMap.driveLeft.set(driverLY);
        RobotMap.driveRight.set(driverRY);
        // Continuous control of the motors
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        // Always return false, once we're driving in TeleOp we never want to give up joystick control
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        RobotMap.driveLeft.set(0.0);
        RobotMap.driveRight.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        //calls the end command when interrupted.
        this.end();
    }
}

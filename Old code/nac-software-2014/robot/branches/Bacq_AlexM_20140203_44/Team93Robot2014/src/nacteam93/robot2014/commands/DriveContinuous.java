
package nacteam93.robot2014.commands;

import nacteam93.robot2014.OI;
import nacteam93.robot2014.RobotMap;



public class DriveContinuous extends CommandBase {

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
        
        double driverLY = OI.driver.getRawAxis(2);//XBOX CONTROLLER VALUE IS SAME AS PS4
        double driverRY = OI.driver.getRawAxis(4);//XBOX CONTROLLER VALUE: 5     PS4 CONTROLLER VALUE: 6
        
        
        RobotMap.driveBackLeft.set(driverLY);
        RobotMap.driveFrontLeft.set(driverLY);
        RobotMap.driveFrontRight.set(driverRY);
        RobotMap.driveBackRight.set(driverRY);
        RobotMap.driveBackLeft.set(driverRY * 0.75);
        //Continuous control of the motors
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        
        //Always return false
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    
    }
}

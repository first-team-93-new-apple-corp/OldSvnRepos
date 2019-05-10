
package nacteam93.robot2014.commands;
import nacteam93.robot2014.RobotMap;

import nacteam93.robot2014.OI;
import nacteam93.robot2014.RobotMap;



public class DriveContinuous extends CommandBase {

    public DriveContinuous() {
        requires(CommandBase.drive);
        
        
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
  int iterationNum=0;
    protected void initialize() {
        iterationNum=0;
        RobotMap.driveFrontLeft.set(0);
        RobotMap.driveBackLeft.set(0);
        RobotMap.driveFrontRight.set(0);
        RobotMap.driveBackRight.set(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        iterationNum+=1;
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
        if(iterationNum==20){
            return true;        
        }
        else{
            return false;
        }
        //Always return false
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        RobotMap.driveFrontLeft.set(0);
        RobotMap.driveBackLeft.set(0);
        RobotMap.driveFrontRight.set(0);
        RobotMap.driveBackRight.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    
    }
}

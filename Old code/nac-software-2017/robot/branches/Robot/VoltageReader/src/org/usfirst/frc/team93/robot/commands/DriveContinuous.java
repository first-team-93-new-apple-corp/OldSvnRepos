
package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.ctre.CANTalon;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;


/**
 *
 */
public class DriveContinuous extends Command {

    public DriveContinuous() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	
    	
    	
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    	if(RobotMap.listStrings.contains("toHigh")){
    		
    		RobotMap.Josh.set(0);
    		RobotMap.listStrings.remove("toHigh");
    	}
    	
    	
    	
		
    	
    	
    	//Gets Information from running the motor
    	//[0] the 0 is the master ID which can be confirmed through silverlight on the roboRio
    	
    	double current = RobotMap.pdp.getCurrent(RobotMap.pdpConnectionPortNumber);
    	
    	
    	
    	System.out.println("PDP Reading Amperage: " + current);
    	
    	double topNumber = 100;  //number that we tested as max
    
    	
    	double currentAmps = RobotMap.Josh.getOutputCurrent();
    	double outputV = RobotMap.Josh.getOutputVoltage();
    	double busV = RobotMap.Josh.getOutputVoltage();
    	double quadEncoderPos = RobotMap.Josh.getEncPosition();
    	double quadEncoderVelocity = RobotMap.Josh.getEncVelocity();
    	int analogPos = RobotMap.Josh.getAnalogInPosition();
    	double analogVelocty = RobotMap.Josh.getAnalogInVelocity();
    	double selectedSensorPos= RobotMap.Josh.getPosition();
    	double selectedSensorSpeed = RobotMap.Josh.getSpeed();
    	int closeLoopErr = RobotMap.Josh.getClosedLoopError();
    	
    	
    	System.out.println("currentAmps" + currentAmps);
//    	System.out.println("outputV:" + outputV);
//    	System.out.println("output%" + 100*(outputV/busV));
    	
    	
    	
    	if(current == topNumber ){
    		
    		RobotMap.listStrings.add("highNumber");
    		
    	}
    	
    	//System.out.println("secondCheck");
    	
    	
    	//System.out.println("analogPo:" + analogPos);
    	//System.out.println("anaglogVelocity:" + analogPos);
    	
    	
    	
    	//System.out.println("selectedsensorPos:" + selectedSensorPos);
    	//System.out.println("selectedsensorSpeed:" + selectedSensorSpeed);
    	
    	//System.out.println("closeLoopErr;" + closeLoopErr);
    	
    	
    	
    		
    	
    	
    	
  
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//stops all motors
    	//stops left motors
    	RobotMap.Josh.set(0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

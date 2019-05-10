package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.DriveSub;

import edu.wpi.first.wpilibj.command.Command;

public class JoshDrive extends Command 
{

	public JoshDrive() 
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() 
	{
		// TODO Auto-generated method stub
		double noYDeadZone = -1 * (OI.driverJoystick.getRawAxis(1));
		if(Math.abs(noYDeadZone) <.1)
		{
			noYDeadZone = 0;
		}
		
		double noXDeadZone = OI.driverJoystick.getRawAxis(0);
		if(Math.abs(noXDeadZone) <.25)
		{
			noXDeadZone = 0;
		}
		
		double noZDeadZone = OI.driverJoystick.getRawAxis(2);
		if(Math.abs(noZDeadZone) <.4)
		{
			noZDeadZone = 0;
		}
		
		if(OI.driverJoystick.getRawAxis(0) > .1)//if the joystick is pointing right
		{
		//we're setting the right motors to a smaller number so that we turn right
			DriveSub.setRightMotors(noYDeadZone*(1-noXDeadZone));
			DriveSub.setLeftMotors(noYDeadZone);
		}
		
		if(OI.driverJoystick.getRawAxis(0) < -.1)//if the joystick is pointing left
		{
		//we're setting the left motors to a smaller number so that we turn left
			DriveSub.setRightMotors(noYDeadZone);
			DriveSub.setLeftMotors(noYDeadZone*(1-(-1*noXDeadZone)));
		}
		if(noXDeadZone == 0) //if the x axis value is 0
		{
		//we're setting all the motors to the same value so we drive straight forward
			DriveSub.setAllMotors(noYDeadZone);
		}
		
		if(noYDeadZone == 0) //if the y axis value is 0
		{
		//we're setting the motors to the rotating axis
			DriveSub.setRightMotors(.5*-1*noZDeadZone);
			DriveSub.setLeftMotors(.5*noZDeadZone);
		}
	System.out.println(RobotMap.frontRight.get());
	}

	@Override
	protected boolean isFinished() 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void interrupted() 
	{
		// TODO Auto-generated method stub
		
	}

}

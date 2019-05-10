/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.SwapDriveMode;
import frc.robot.commands.setDriveTo180;
import frc.robot.commands.CameraSwitcher;
import frc.robot.commands.DriveElevatedRobot;
import frc.robot.commands.LockRotationDrive;
import frc.robot.commands.LockedAngleControl;
import frc.robot.commands.PositionRobot;
import frc.robot.commands.RaiseRobot;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	public static Joystick driver;
	public static JoystickButton lockAngleDrive;
	public static JoystickButton fieldCentricDrive;
	public static JoystickButton robotCentricDrive;
	public static LockRotationDrive lockRotationDrive;
	public static Joystick buttonBoard;
	public static Joystick operator;

	public static JoystickButton angleButton7;
	public static JoystickButton angleButton9;
	public static JoystickButton angleButton11;

	public static JoystickButton backAngleButton;

	public static Joystick joy1;
	public static JoystickButton cameraButton;

	public static JoystickButton elevateRobot;
	public static JoystickButton toggleDrive;
	public static JoystickButton raiseBackWheels;
	

	
	public OI()
	{
		lockRotationDrive = new LockRotationDrive();
		driver = new Joystick(0);
		lockAngleDrive = new JoystickButton(driver, 1);
		lockAngleDrive.whileHeld(lockRotationDrive);
		fieldCentricDrive = new JoystickButton(driver, 3);
		robotCentricDrive = new JoystickButton(driver, 4);
		fieldCentricDrive.whenPressed(new SwapDriveMode(false));
		robotCentricDrive.whenPressed(new SwapDriveMode(true));
		buttonBoard = new Joystick(1);
		operator = new Joystick(2);

		angleButton7 = new JoystickButton(driver, 7);
		angleButton9 = new JoystickButton(driver, 9);
		angleButton11 = new JoystickButton(driver, 11);
		backAngleButton = new JoystickButton(driver, 2);

		elevateRobot = new JoystickButton(driver, 5); //change later
		toggleDrive = new JoystickButton(driver, 6); //change later
		raiseBackWheels = new JoystickButton(driver, 8); //change later

		angleButton7.toggleWhenPressed(new LockedAngleControl(90, 118.75, -118.75));
		angleButton9.toggleWhenPressed(new LockedAngleControl(0, 90, -90));
		angleButton11.toggleWhenPressed(new LockedAngleControl(-90, 61.25, -61.25));
		backAngleButton.toggleWhenPressed(new setDriveTo180());

		
    	joy1 = new Joystick(1);
		cameraButton = new JoystickButton(joy1, 1);
		cameraButton.whenPressed(new CameraSwitcher());

		elevateRobot.whenPressed(new RaiseRobot());
		toggleDrive.toggleWhenPressed(new DriveElevatedRobot());
		raiseBackWheels.whenPressed(new PositionRobot(0));



	
	}
	
	public static double getAngleFromAxis()
	{
		double x = driver.getRawAxis(0) * -1;
		double y = driver.getRawAxis(1);
		double theta = Math.toDegrees(Math.atan(x/y));
		double out = theta;
		if (theta < 0)
		{
			out = theta + 360;
		}
		if(y < 0)
		{
			out = theta + 180;
		}
		out = out + 180;
		if(out > 360)
		{
			return out - 360;
		}
		else
		{
			return out;
		}
	}
	public static double getDriverX()
	{
		return(deadzone(applyCurve(driver.getRawAxis(0)), 0.1));
	}
	public static double getDriverY()
	{
		return(deadzone(applyCurve(driver.getRawAxis(1)), 0.1));
	}
	public static double getDriverRot()
	{

		return(deadzone(applyCurve(driver.getRawAxis(2)), 0.1));

	}
	private static double deadzone(double value, double deadzone)
	{
		if (Math.abs(value) < deadzone)
		{
			return 0;
		}
		return value;
	}
	private static double applyCurve(double joystickPosition)
{
    //apply piecewise logic
    if (joystickPosition > 0)
    {
        return (1 - RobotMap.TORQUE_RESISTANCE_THRESHOLD) * Math.pow(joystickPosition, 3) + RobotMap.TORQUE_RESISTANCE_THRESHOLD;
    }
    else if (joystickPosition < 0)
    {
        return (1 - RobotMap.TORQUE_RESISTANCE_THRESHOLD) * Math.pow(joystickPosition, 3) - RobotMap.TORQUE_RESISTANCE_THRESHOLD;
    }

    //return 0 if joystickPosition is 0
    return 0;
}

	
	
}

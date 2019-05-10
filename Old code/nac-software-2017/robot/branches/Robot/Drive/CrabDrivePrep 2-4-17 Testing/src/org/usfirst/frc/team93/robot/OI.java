package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.utilities.JoystickExtended;

import org.usfirst.frc.team93.robot.commands.CrabDriveSwitch;
import org.usfirst.frc.team93.robot.commands.TestMotor;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.utilities.JoystickAxis;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public static Joystick driver;
	public static Joystick operator;
	
	public static JoystickExtended movement;
	public static JoystickAxis yaw;
	public static JoystickAxis speedSlider;
	
	public static Button switchDrive;
	
	public static int resetButton;
	public static int switchButton;
	
	public static Button drive_left_front;
	public static Button drive_left_back;
	public static Button drive_right_front;
	public static Button drive_right_back;
	
	public OI()
	{
		driver = new Joystick(0);
		operator = new Joystick(1);
		
		movement = new JoystickExtended(driver, 0, 1);
		yaw = new JoystickAxis(driver, 2);
		
		yaw.setDeadzone(0.3);
		
		speedSlider = new JoystickAxis(driver, 3);

		switchButton = 2;
		resetButton = 1;
		
		switchDrive = new JoystickButton(driver, switchButton);
		//switchDrive.whenPressed(new CrabDriveSwitch());
		
		//baseline is 0.05, 100
		//1: X left front
		//2: A left back
		//3: B right front
		//4: Y right back
		
		
		//All PG71 polarity testing
		//1: Move counterclockwise
		//2: Move clockwise
		final double power = 0.4;
		final int timems = 100;
		drive_left_front = new JoystickButton(operator, 1);
		drive_left_front.whenPressed(new TestMotor(Drive.DRIVE_DIRECTION, power, timems));
		drive_right_front = new JoystickButton(operator, 2);
		drive_right_front.whenPressed(new TestMotor(Drive.DRIVE_DIRECTION, -power, timems));
		
		
		//Drive Wheel Direction (PG71) polarity testing
		/*
		final double power = 0.4;
		final int timems = 150;
		drive_left_front = new JoystickButton(driver, 1);
		drive_left_front.whenPressed(new TestMotor(Drive.DRIVE_DIRECTION_LEFT_FRONT, power, timems));
		drive_left_back = new JoystickButton(driver, 2);
		drive_left_back.whenPressed(new TestMotor(Drive.DRIVE_DIRECTION_LEFT_BACK, power, timems));
		drive_right_front = new JoystickButton(driver, 3);
		drive_right_front.whenPressed(new TestMotor(Drive.DRIVE_DIRECTION_RIGHT_FRONT, power, timems));
		drive_right_back = new JoystickButton(driver, 4);
		drive_right_back.whenPressed(new TestMotor(Drive.DRIVE_DIRECTION_RIGHT_BACK, power, timems));
		*/
		
		/*		
		//Drive Speed (CIM) polarity testing
		final double power = 0.4;
		final int timems = 200;
		drive_left_front = new JoystickButton(driver, 1);
		drive_left_front.whenPressed(new TestMotor(Drive.DRIVE_LEFT_FRONT, power, timems));
		drive_left_back = new JoystickButton(driver, 2);
		drive_left_back.whenPressed(new TestMotor(Drive.DRIVE_LEFT_BACK, power, timems));
		drive_right_front = new JoystickButton(driver, 3);
		drive_right_front.whenPressed(new TestMotor(Drive.DRIVE_RIGHT_FRONT, power, timems));
		drive_right_back = new JoystickButton(driver, 4);
		drive_right_back.whenPressed(new TestMotor(Drive.DRIVE_RIGHT_BACK, power, timems));
		*/
		
	}
}

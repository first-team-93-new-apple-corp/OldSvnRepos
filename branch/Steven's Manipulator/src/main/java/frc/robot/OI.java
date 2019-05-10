/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.ElevatorHeight;

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
	public static Joystick buttonBoard;
	public static JoystickButton hatch1;
	public static JoystickButton hatch2;
	public static JoystickButton hatch3;
	public static JoystickButton cargo1;
	public static JoystickButton cargo2;
	public static JoystickButton cargo3;
	
	public OI()
	{
		driver = new Joystick(0);
		buttonBoard = new Joystick(1);
		hatch1 = new JoystickButton(buttonBoard, 5);
		hatch2 = new JoystickButton(buttonBoard, 1);
		hatch3 = new JoystickButton(buttonBoard, 3);
		cargo1 = new JoystickButton(buttonBoard, 2);
		cargo2 = new JoystickButton(buttonBoard, 4);
		cargo3 = new JoystickButton(buttonBoard, 6);

		hatch1.whenPressed(new ElevatorHeight(19));
		hatch2.whenPressed(new ElevatorHeight(47));
		hatch3.whenPressed(new ElevatorHeight(75));
		cargo1.whenPressed(new ElevatorHeight(27.5));
		cargo2.whenPressed(new ElevatorHeight(55.5));
		cargo3.whenPressed(new ElevatorHeight(83.5));

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
	
}
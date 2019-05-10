/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.CargoIntake;
import frc.robot.commands.D_HatchMover;
import frc.robot.commands.EntireMovement;
import frc.robot.commands.CargoShooter;

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
	public static JoystickButton ToggleD_HatchMover;
	public static JoystickButton ToggleCargoIntake;
	public static JoystickButton ToggleEntireMovement;
	public static JoystickButton ToggleCargoShooter;
	
	public OI()
	{
		driver = new Joystick(0);
		ToggleD_HatchMover = new JoystickButton(driver, 3);
		ToggleCargoIntake = new JoystickButton(driver, 4);
		ToggleEntireMovement = new JoystickButton(driver, 5);
		ToggleCargoShooter = new JoystickButton(driver, 6);

		ToggleD_HatchMover.whenPressed(new D_HatchMover());
		ToggleCargoIntake.whenPressed(new CargoIntake());
		ToggleEntireMovement.whenPressed(new EntireMovement());
		ToggleCargoIntake.whenPressed(new CargoShooter());
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

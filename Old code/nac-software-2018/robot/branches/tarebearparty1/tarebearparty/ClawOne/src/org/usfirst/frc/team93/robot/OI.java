/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.RollerIntakeController;
import org.usfirst.frc.team93.robot.commands.RollerOuttakeController;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick driver;
	public static Joystick splitDriverControlOne;
	public static Joystick splitDriverControlTwo;
	public static Joystick operator;
	public static double ControllerDeadzone;
	public static double JoystickCutoffVal;
	public static JoystickButton motorIntakeDriver;
	public static JoystickButton motorOuttakeDriver;
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
	public OI() {
		operator = new Joystick(3);
		driver = new Joystick(0);
		splitDriverControlOne = new Joystick(1);
		splitDriverControlTwo = new Joystick(2);
		ControllerDeadzone = 0.1;
		JoystickCutoffVal = 1;
		motorIntakeDriver = new JoystickButton(operator,6);
		motorOuttakeDriver = new JoystickButton(operator,7);
		motorIntakeDriver.toggleWhenPressed(new RollerIntakeController());
		motorOuttakeDriver.toggleWhenPressed(new RollerOuttakeController());

	}
	//1 and 3 for 1 joystick
	//1 and 1 for 2




}

















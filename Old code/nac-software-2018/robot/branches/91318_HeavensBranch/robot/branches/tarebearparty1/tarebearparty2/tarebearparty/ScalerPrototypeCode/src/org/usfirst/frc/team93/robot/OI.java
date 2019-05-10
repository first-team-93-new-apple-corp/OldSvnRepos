/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.ScalerContinuous;
import org.usfirst.frc.team93.robot.commands.ScalerMoveBottom;
import org.usfirst.frc.team93.robot.commands.ScalerMoveTop;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick driver;
	public static Joystick operator;
	public static Joystick splitDriverControlOne;
	public static Joystick splitDriverControlTwo;
	public static double ControllerDeadzone;
	public static double JoystickCutoffVal;
	public static JoystickButton ScalerTop;
	public static JoystickButton ScalerBottom;
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
		driver = new Joystick(0);
		operator = new Joystick(3);
		splitDriverControlOne = new Joystick(1);
		splitDriverControlTwo = new Joystick(2);
		ControllerDeadzone = 0.1;
		JoystickCutoffVal = 1;
		ScalerTop = new JoystickButton(operator, 1);
		ScalerTop.whenPressed(new ScalerMoveTop());
		ScalerBottom = new JoystickButton(operator, 2);
		ScalerBottom.whenPressed(new ScalerMoveBottom());
	}
	//1 and 3 for 1 joystick
	//1 and 1 for 2




}

















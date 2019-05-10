/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.RollerIntakeController;
import org.usfirst.frc.team93.robot.commands.RollerOuttakeController;
import org.usfirst.frc.team93.robot.commands.ScalerMove;
import org.usfirst.frc.team93.robot.commands.TilterDown;
import org.usfirst.frc.team93.robot.commands.TilterUp;
import org.usfirst.frc.team93.robot.subsystems.Scaler;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
	
	public static Joystick operator;
	public static Joystick splitDriverControlOne;
	public static Joystick splitDriverControlTwo;
	public static Joystick Operator2;
	
	public static JoystickButton leftRampDown;
	public static JoystickButton leftRampUp;
	public static JoystickButton rightRampDown;
	public static JoystickButton rightRampUp;
	public static JoystickButton motorIntakeDriver;
	public static JoystickButton motorOuttakeDriver;
	public static JoystickButton DisableIntake;
	public static JoystickButton ScalePIDTest;
	public static JoystickButton ScalerDown;
	public static JoystickButton ScalerMid;
	public static JoystickButton TilterDown;
	public static JoystickButton TilterUp;
	
	public static JoystickButton Intake;
	
	public static double ControllerDeadzone;
	public static double JoystickCutoffVal;
	
	public static double scalerMinMotorVal;
	public static double scalerPercentDecrease; // The absolute percent where the scaler starts decreasing speed
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
	/**
	 * Initializes the Joysticks, buttons, and related variables
	 */
	public OI()
	{
		ControllerDeadzone = 0.1;
		JoystickCutoffVal = 1;
		scalerMinMotorVal = 0.2;
		scalerPercentDecrease = 20;
		
		splitDriverControlOne = new Joystick(1);
		splitDriverControlTwo = new Joystick(2);
		operator = new Joystick(3);
		Operator2 = new Joystick(1);
		
		motorIntakeDriver = new JoystickButton(operator, 6);
		motorOuttakeDriver = new JoystickButton(operator, 7);
		// leftRampDown = new JoystickButton(buttonBoard, 1);
		// leftRampUp = new JoystickButton(buttonBoard, 2);
		// rightRampDown = new JoystickButton(buttonBoard, 3);
		// rightRampUp = new JoystickButton(buttonBoard, 4);
		
		motorIntakeDriver.toggleWhenPressed(new RollerIntakeController());
		motorOuttakeDriver.toggleWhenPressed(new RollerOuttakeController());
		// leftRampDown.toggleWhenPressed(new LeftRampDown());
		// leftRampUp.toggleWhenPressed(new LeftRampUp());
		// rightRampDown.toggleWhenPressed(new RightRampDown());
		// rightRampUp.toggleWhenPressed(new RightRampUp());
		// DisableIntake = new JoystickButton(operator, 6);
		// DisableIntake.whenPressed(new DisableIntake());
		ScalePIDTest = new JoystickButton(operator, 4);
		ScalePIDTest.toggleWhenPressed(new ScalerMove(Scaler.ScalerLocation.TOP));
		ScalerDown = new JoystickButton(operator, 1);
		ScalerDown.toggleWhenPressed(new ScalerMove(Scaler.ScalerLocation.BOTTOM));
		ScalerMid = new JoystickButton(operator, 5);
		ScalerMid.toggleWhenPressed(new ScalerMove(Scaler.ScalerLocation.MIDDLE));
		
		TilterUp = new JoystickButton(operator, 3);
		TilterDown = new JoystickButton(operator, 2);
		
		TilterUp.toggleWhenPressed(new TilterUp());
		TilterDown.toggleWhenPressed(new TilterDown());
		
		Intake = new JoystickButton(operator, 8);
		Intake.toggleWhenPressed(new RollerIntakeController());
	}
}

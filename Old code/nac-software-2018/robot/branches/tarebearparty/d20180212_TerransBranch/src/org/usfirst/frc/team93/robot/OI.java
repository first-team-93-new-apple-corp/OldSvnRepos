/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.BothRampContinuous;
import org.usfirst.frc.team93.robot.commands.CancelAllCommands;
import org.usfirst.frc.team93.robot.commands.LeftRampContinuous;
import org.usfirst.frc.team93.robot.commands.RightRampContinuous;
import org.usfirst.frc.team93.robot.commands.RollerIntakeController;
import org.usfirst.frc.team93.robot.commands.RollerOuttakeController;
import org.usfirst.frc.team93.robot.commands.ScalerContinuous;
import org.usfirst.frc.team93.robot.commands.SlowerOuttakeController;
import org.usfirst.frc.team93.robot.triggers.POVAsButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
	public static Joystick driver;
	public static Joystick operator;
	public static Joystick splitDriverControlOne;
	public static Joystick splitDriverControlTwo;
	public static Joystick buttonBoard;
	// public static JoystickButton leftRampDown;
	// public static JoystickButton leftRampUp;
	// public static JoystickButton rightRampDown;
	// public static JoystickButton rightRampUp;
	// public static JoystickButton Turn45;
	// public static JoystickButton Turn90;
	// public static JoystickButton motorIntakeDriver;
	// public static JoystickButton motorOuttakeDriver;
	// public static JoystickButton DriveForwardsTuning;
	public static JoystickButton elevatorSetpointBottom;
	public static JoystickButton elevatorSetpointSwitch;
	public static JoystickButton elevatorSetpointScaleLow;
	public static JoystickButton elevatorSetpointScaleHigh;
	public static JoystickButton intake;
	public static JoystickButton fastOuttake;
	public static JoystickButton slowOuttake;
	public static JoystickButton cancel;
	
	public static POVAsButton rightDPad;
	public static POVAsButton leftDPad;
	public static POVAsButton downDPad;
	
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
		
		driver = new Joystick(0);
		splitDriverControlOne = new Joystick(1);
		splitDriverControlTwo = new Joystick(2);
		operator = new Joystick(3);
		buttonBoard = new Joystick(4);
		
		// Turn45 = new JoystickButton(driver, 1);
		// Turn90 = new JoystickButton(driver, 2);
		// motorIntakeDriver = new JoystickButton(operator, 6);
		// motorOuttakeDriver = new JoystickButton(operator, 7);
		// leftRampDown = new JoystickButton(buttonBoard, 1);
		// leftRampUp = new JoystickButton(buttonBoard, 2);
		// rightRampDown = new JoystickButton(buttonBoard, 3);
		// rightRampUp = new JoystickButton(buttonBoard, 4);
		elevatorSetpointBottom = new JoystickButton(operator, 2);
		elevatorSetpointSwitch = new JoystickButton(operator, 3);
		elevatorSetpointScaleLow = new JoystickButton(operator, 1);
		elevatorSetpointScaleHigh = new JoystickButton(operator, 4);
		intake = new JoystickButton(operator, 8);
		fastOuttake = new JoystickButton(operator, 7);
		slowOuttake = new JoystickButton(operator, 5);
		cancel = new JoystickButton(operator, 6);
		
		rightDPad = new POVAsButton(operator, 0, POVAsButton.ButtonTypeT.RIGHT);
		leftDPad = new POVAsButton(operator, 0, POVAsButton.ButtonTypeT.LEFT);
		downDPad = new POVAsButton(operator, 0, POVAsButton.ButtonTypeT.DOWN);
		// motorIntakeDriver.toggleWhenPressed(new RollerIntakeController());
		// motorOuttakeDriver.toggleWhenPressed(new RollerOuttakeController());
		// Turn90.whenPressed(new TurnDeg(90, 5));
		// Turn45.whenPressed(new TurnDeg(45, 5));
		// leftRampDown.toggleWhenPressed(new LeftRampDown());
		// leftRampUp.toggleWhenPressed(new LeftRampUp());
		// rightRampDown.toggleWhenPressed(new RightRampDown());
		// rightRampUp.toggleWhenPressed(new RightRampUp());
		/**
		 * CHANGE THESE LATER:
		 */
		elevatorSetpointBottom.whenPressed(new ScalerContinuous(0, 0));
		elevatorSetpointSwitch.whenPressed(new ScalerContinuous(0, 0));
		elevatorSetpointScaleLow.whenPressed(new ScalerContinuous(0, 0));
		elevatorSetpointScaleHigh.whenPressed(new ScalerContinuous(0, 0));
		// /\ CAHNGE THE ABOVE /\
		intake.toggleWhenPressed(new RollerIntakeController());
		fastOuttake.toggleWhenPressed(new RollerOuttakeController());
		slowOuttake.toggleWhenPressed(new SlowerOuttakeController());
		cancel.toggleWhenPressed(new CancelAllCommands());
		
		rightDPad.whileHeld(new RightRampContinuous());
		leftDPad.whileHeld(new LeftRampContinuous());
		downDPad.whileHeld(new BothRampContinuous());
		
		// DriveForwardsTuning = new JoystickButton(operator, 1);
		// DriveForwardsTuning.whenPressed(new DriveEncoderTicks(Drive.TicksPerFoot *
		// 1));
		
	}
}

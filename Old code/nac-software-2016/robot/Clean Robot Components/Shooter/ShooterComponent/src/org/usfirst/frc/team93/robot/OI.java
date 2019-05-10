package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.OutputBall;
import org.usfirst.frc.team93.robot.commands.PrepareShooter;
import org.usfirst.frc.team93.robot.commands.ShootingSequence;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
	public static Joystick operator;


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
	public static Button reverseIntake;
	public static Button startShooter;
	public static Button prepareShooter;

	public OI()
	{
		operator = new Joystick(1);
	
		/*
		 * 
		 * 
		 * OPERATOR BUTTONS
		 * 
		 * 
		 */

		prepareShooter = new JoystickButton(operator, 3);
		prepareShooter.toggleWhenPressed(new PrepareShooter(29000));

		reverseIntake = new JoystickButton(operator, 7);
		reverseIntake.toggleWhenPressed(new OutputBall());

		startShooter = new JoystickButton(operator, 8);
		startShooter.whenPressed(new ShootingSequence(29000));

	}

	
	public static double getOperatorLY(double deadzone)
	{
		return (deadzone(operator.getRawAxis(1), deadzone));
	}

	public static double getOperatorRY(double deadzone)
	{
		return (deadzone(operator.getRawAxis(3), deadzone));
	}

	public static double deadzone(double value, double deadzone)
	{
		double modifiedValue = value;

		if ((value <= deadzone) && (value >= 0))
		{
			modifiedValue = 0;
		}

		return modifiedValue;
	}
}

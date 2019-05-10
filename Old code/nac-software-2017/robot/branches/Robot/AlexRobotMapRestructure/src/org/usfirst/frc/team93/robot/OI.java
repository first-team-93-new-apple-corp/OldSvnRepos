package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.DriveForward;
import org.usfirst.frc.team93.robot.utilities.JoystickExtended;
import org.usfirst.frc.team93.robot.commands.CrabDriveSwitch;
import org.usfirst.frc.team93.robot.utilities.JoystickAxis;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
	
	public static JoystickExtended movement;
	public static JoystickAxis yaw;
	
	public static Button switchDrive;
	
	public static int resetButton;
	public static int switchButton;
	
	public OI()
	{
		driver = new Joystick(0);
		movement = new JoystickExtended(driver, 0, 1);
		yaw = new JoystickAxis(driver, 2);
		yaw.setDeadzone(0.3);
		
		switchButton = 2;
		resetButton = 1;
		
		switchDrive = new JoystickButton(driver, switchButton);
		switchDrive.whenPressed(new CrabDriveSwitch());
	}
}

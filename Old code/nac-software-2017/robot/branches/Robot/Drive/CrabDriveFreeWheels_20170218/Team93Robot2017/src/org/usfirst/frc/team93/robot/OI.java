package org.usfirst.frc.team93.robot;

import org.usfirst.frc.team93.robot.commands.CrabDriveSwitch;
import org.usfirst.frc.team93.robot.utilities.JoystickAxis;
import org.usfirst.frc.team93.robot.utilities.JoystickExtended;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{

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
		switchDrive.whenPressed(new CrabDriveSwitch());
	}
}

package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	Joystick Controller;
	Button Spi;
	
	public OI()
	{
		Controller = new Joystick(0);
		Spi = new JoystickButton(Controller, 1);
	}
}
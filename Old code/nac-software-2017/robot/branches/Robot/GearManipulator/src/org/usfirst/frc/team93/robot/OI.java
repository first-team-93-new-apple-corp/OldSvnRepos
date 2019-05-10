package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team93.robot.commands.ToggleBottomClaw;
import org.usfirst.frc.team93.robot.commands.ToggleGearMotors;
import org.usfirst.frc.team93.robot.commands.ToggleGrabber;
import org.usfirst.frc.team93.robot.commands.ToggleTopClaw;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	Joystick operator = new Joystick(1);
	JoystickButton toggleTopPiston = new JoystickButton(operator, 6);
	JoystickButton toggleBottomPiston = new JoystickButton(operator, 8);
	JoystickButton toggleBothPistons = new JoystickButton(operator, 2);
	JoystickButton toggleGearMotors = new JoystickButton(operator, 5);
	
	public OI()
	{
	toggleTopPiston.whenPressed(new ToggleTopClaw());
	toggleBottomPiston.whenPressed(new ToggleBottomClaw());
	toggleBothPistons.whenPressed(new ToggleGrabber());
	toggleGearMotors.whenPressed(new ToggleGearMotors());
	}
}

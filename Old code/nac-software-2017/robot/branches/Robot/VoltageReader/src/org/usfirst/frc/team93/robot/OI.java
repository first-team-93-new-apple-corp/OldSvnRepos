package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team93.robot.commands.CANTalon13Simple;
import org.usfirst.frc.team93.robot.commands.CANTalon19Simple;
import org.usfirst.frc.team93.robot.commands.CANTalon6Simple;
import org.usfirst.frc.team93.robot.commands.CANTalon8Simple;
import org.usfirst.frc.team93.robot.commands.DriveContinuous;
import org.usfirst.frc.team93.robot.commands.SetMotorEightyPercent;
import org.usfirst.frc.team93.robot.commands.SetMotorHundredPercent;
import org.usfirst.frc.team93.robot.commands.SetMotorNinetyPercent;
import org.usfirst.frc.team93.robot.commands.StopCANTalon;

import com.ctre.CANTalon;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//Create controllers here.
	
	
	public static Joystick Controller;
	public static JoystickButton portNumber13;
	public static JoystickButton portNumber8;
	public static JoystickButton portNumber19;
	public static JoystickButton portNumber6;
	public static JoystickButton speed8;
	public static JoystickButton speed9;
	public static JoystickButton speed10;
	public static JoystickButton stopMotor;

	
	OI(){
		
		
		Controller = new Joystick(0);
		portNumber13 = new JoystickButton(Controller,1);
		portNumber8 = new JoystickButton(Controller, 2);
		portNumber19 = new JoystickButton(Controller, 3);
		portNumber6 = new JoystickButton(Controller, 4);
		speed8 = new JoystickButton(Controller, 5);
		speed9 = new JoystickButton(Controller, 6);
		speed10 = new JoystickButton(Controller, 7);
		stopMotor = new JoystickButton(Controller, 8);
		
		portNumber13.whenPressed(new CANTalon13Simple());
		portNumber8.toggleWhenPressed(new CANTalon8Simple());
		portNumber19.toggleWhenPressed(new CANTalon19Simple());
		portNumber6.toggleWhenPressed(new CANTalon6Simple());
		speed8.toggleWhenPressed(new SetMotorEightyPercent());
		speed9.toggleWhenPressed(new SetMotorNinetyPercent());
		speed10.toggleWhenPressed(new SetMotorHundredPercent());
		stopMotor.whenPressed(new StopCANTalon());
	}
	
	
	
	
	
}


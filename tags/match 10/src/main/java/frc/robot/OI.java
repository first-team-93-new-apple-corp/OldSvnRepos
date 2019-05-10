/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import frc.robot.commands.SwapDriveMode;
import frc.robot.commands.angleWrist;
import frc.robot.commands.intakeOuttake;
import frc.robot.commands.setDriveTo180;
import frc.robot.commands.toggleHatch;
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Manipulator;
import frc.robot.commands.CameraSwitcher;
import frc.robot.commands.CancelDriveCommand;
import frc.robot.commands.DefenseElevator;
import frc.robot.commands.DefenseManipulator;
import frc.robot.commands.ElevatorSetpoint;
import frc.robot.commands.EndgameActivate;
import frc.robot.commands.HoldWristPID;
import frc.robot.commands.LockedAngleControl;
import frc.robot.commands.SetManipulatorMode;

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
	public static Joystick operator;

	public static JoystickButton fieldCentricDrive;
	public static JoystickButton robotCentricDrive;
	public static JoystickButton angleButton7;
	public static JoystickButton angleButton9;
	public static JoystickButton angleButton11;
	public static JoystickButton cancelAngle;
	public static JoystickButton backAngleButton;
	public static JoystickButton cameraButton;

	public static JoystickButton hatchMode;
	public static JoystickButton cargoMode;

	public static POVButton toggleHatchIntake;

	public static JoystickButton intake;
	public static JoystickButton outtake;

	public static JoystickButton setWristUp;
	public static JoystickButton setWristDown;

	public static HoldWristPID holdWristPID;
	public static DefenseElevator defenseElevator;

	public static JoystickButton levelOne;
	public static JoystickButton levelTwo;
	public static JoystickButton levelThree;
	public static JoystickButton levelC;

	public static POVButton defenseMode;

	public static JoystickButton activateEndgame;
	
	public OI()
	{
		driver = new Joystick(0);
		operator = new Joystick(1);
		fieldCentricDrive = new JoystickButton(driver, 3);
		robotCentricDrive = new JoystickButton(driver, 4);
		fieldCentricDrive.whenPressed(new SwapDriveMode(false));
		robotCentricDrive.whenPressed(new SwapDriveMode(true));

		hatchMode = new JoystickButton(operator, 10);
		cargoMode = new JoystickButton(operator, 9);
		hatchMode.whenPressed(new SetManipulatorMode(Manipulator.Mode.HATCH));
		cargoMode.whenPressed(new SetManipulatorMode(Manipulator.Mode.CARGO));

		angleButton7 = new JoystickButton(driver, 7);
		angleButton9 = new JoystickButton(driver, 9);
		angleButton11 = new JoystickButton(driver, 11);
		backAngleButton = new JoystickButton(driver, 2);
		cancelAngle = new JoystickButton(driver, 8);

		angleButton7.toggleWhenPressed(new LockedAngleControl(-90, 90+61.25, -(90+61.25)));
		angleButton9.toggleWhenPressed(new LockedAngleControl(0, 90, -90));
		angleButton11.toggleWhenPressed(new LockedAngleControl(90, 90-61.25, -(90-61.25)));
		backAngleButton.toggleWhenPressed(new setDriveTo180());
		cancelAngle.whenPressed(new CancelDriveCommand());
		    	
		cameraButton = new JoystickButton(operator, 1);
		cameraButton.whenPressed(new CameraSwitcher());
		
		toggleHatchIntake = new POVButton(operator, 180);
		toggleHatchIntake.whenPressed(new toggleHatch());

		intake = new JoystickButton(operator, 7);
		outtake = new JoystickButton(operator, 8);
		intake.toggleWhenPressed(new intakeOuttake(false));
		outtake.toggleWhenPressed(new intakeOuttake(true));

		setWristUp = new JoystickButton(operator, 6);
		setWristUp.toggleWhenPressed(new angleWrist(false));

		setWristDown = new JoystickButton(operator, 5);
		setWristDown.toggleWhenPressed(new angleWrist(true));

		holdWristPID = new HoldWristPID();
		defenseElevator = new DefenseElevator();

		levelOne = new JoystickButton(operator, 2);
		levelTwo = new JoystickButton(operator, 3);
		levelThree = new JoystickButton(operator, 4);
		levelC = new JoystickButton(operator, 1);

		levelOne.whenPressed(new ElevatorSetpoint(0, 7));
		levelTwo.whenPressed(new ElevatorSetpoint(225, 256));
		levelThree.whenPressed(new ElevatorSetpoint(460, 490));
		levelC.whenPressed(new ElevatorSetpoint(225, 182, true));

		defenseMode = new POVButton(operator, 180);
		defenseMode.whileHeld(new DefenseManipulator());

		activateEndgame = new JoystickButton(driver, 12);
		activateEndgame.whileHeld(new EndgameActivate());
	
	}
	
	public static double getAngleFromAxis()
	{
		double x = driver.getRawAxis(0) * -1;
		double y = driver.getRawAxis(1);
		double theta = Math.toDegrees(Math.atan(x/y));
		double out = theta;
		if (theta < 0)
		{
			out = theta + 360;
		}
		if(y < 0)
		{
			out = theta + 180;
		}
		out = out + 180;
		if(out > 360)
		{
			return out - 360;
		}
		else
		{
			return out;
		}
	}
	public static double getDriverX()
	{
		return(deadzone(applyCurve(driver.getRawAxis(0)), 0.1));
	}
	public static double getDriverY()
	{
		return(deadzone(applyCurve(driver.getRawAxis(1)), 0.1));
	}
	public static double getDriverRot()
	{

		return(Math.pow((deadzone(applyCurve(driver.getRawAxis(2)), 0.1)), 3));

	}
	public static double getOperatorStickOne()
	{
		return(deadzone(applyCurve(operator.getRawAxis(1)), 0.1) * -1);
	}
	public static double getOperatorStick()
	{
		if(!(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME))
    	{
		return(deadzone(applyCurve(operator.getRawAxis(3)), 0.1) * -1);
		}
		else
		{
			return(deadzone(applyCurve(operator.getRawAxis(3)), 0.1) * -.35);
		}
	}
	public static double getOperatorStick2()
	{
		return(deadzone(applyCurve(operator.getRawAxis(1)), 0.1) * -1);
	}
	private static double deadzone(double value, double deadzone)
	{
		if (Math.abs(value) < deadzone)
		{
			return 0;
		}
		return value;
	}
	private static double applyCurve(double joystickPosition)
{
    //apply piecewise logic
    if (joystickPosition > 0)
    {
        return (1 - RobotMap.TORQUE_RESISTANCE_THRESHOLD) * Math.pow(joystickPosition, 3) + RobotMap.TORQUE_RESISTANCE_THRESHOLD;
    }
    else if (joystickPosition < 0)
    {
        return (1 - RobotMap.TORQUE_RESISTANCE_THRESHOLD) * Math.pow(joystickPosition, 3) - RobotMap.TORQUE_RESISTANCE_THRESHOLD;
    }

    //return 0 if joystickPosition is 0
    return 0;
}

	
	
}

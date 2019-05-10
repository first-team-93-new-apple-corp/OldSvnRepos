/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.SwapDriveMode;
import frc.robot.commands.SwitchMode;
import frc.robot.commands.TestingPIDS;
import frc.robot.commands.TestingUp;
import frc.robot.commands.setDriveTo180;
import frc.robot.triggers.ArtificialButton;
import frc.robot.commands.CameraSwitcher;
import frc.robot.commands.CargoPlacer;
import frc.robot.commands.CargoPositioningForLS;
import frc.robot.commands.GrabCargoFromFloor;
import frc.robot.commands.GrabFromFloor;
import frc.robot.commands.GrabHatchFromFloor;
import frc.robot.commands.HatchPlacer;
import frc.robot.commands.HatchPositioningForLS;
import frc.robot.commands.LSPosition;
import frc.robot.commands.LockRotationDrive;
import frc.robot.commands.LockedAngleControl;
import frc.robot.commands.ManualElevator;
import frc.robot.commands.ManualHatchManipulator;
import frc.robot.commands.ManualKicker;
import frc.robot.commands.ManualWrist;
import frc.robot.commands.ManualWristWithCargo;
import frc.robot.commands.ManualWristWithHatch;
import frc.robot.commands.PlacingOnCS;
import frc.robot.commands.PlacingOnRocket;
import frc.robot.commands.RemoveHatch;
import frc.robot.commands.Removing;
import frc.robot.commands.SelectManualControl;
import frc.robot.commands.StartIntakeMotors;
import frc.robot.commands.StartOuttakeMotors;

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

	public static JoystickButton lockAngleDrive;
	public static JoystickButton fieldCentricDrive;
	public static JoystickButton robotCentricDrive;
	public static LockRotationDrive lockRotationDrive;
	public static JoystickButton angleButton7;
	public static JoystickButton angleButton9;
	public static JoystickButton angleButton11;
	public static JoystickButton backAngleButton;
	public static JoystickButton cameraButton;
	
	public static JoystickButton intake;
	public static JoystickButton positionL1;
	public static JoystickButton positionL2;
	public static JoystickButton positionL3;
	public static JoystickButton positionCS;
	public static JoystickButton swtichManipulatorMode;
	public static JoystickButton LSPositioning;
	public static JoystickButton floorGrabbing;	
	public static JoystickButton remover;
	public static JoystickButton zeroElevator;
	public static ArtificialButton hatchPlace;
	public static ArtificialButton cargoPlace;
	public static ArtificialButton hatchLSPosition;
	public static ArtificialButton cargoLSPosition;
	public static ArtificialButton grabHatchFloor;
	public static ArtificialButton grabCargoFloor;
	public static ArtificialButton outtake;
	public static ArtificialButton removerHatch;
	public static ArtificialButton elevatorManual;
	public static ArtificialButton wristManual;
	public static ArtificialButton hatchManipulatorManual;
	public static ArtificialButton selectManualControl;
	public static ArtificialButton wristManualWithCargo;
	public static ArtificialButton wristManualWithHatch;
	public static ArtificialButton kickerManual;

	public static JoystickButton testing; //delete
	public static JoystickButton testingUp; //delete


	

	
	public OI()
	{
		lockRotationDrive = new LockRotationDrive();
		driver = new Joystick(0);
		fieldCentricDrive = new JoystickButton(driver, 3);
		robotCentricDrive = new JoystickButton(driver, 4);
		fieldCentricDrive.whenPressed(new SwapDriveMode(false));
		robotCentricDrive.whenPressed(new SwapDriveMode(true));

		operator = new Joystick(1);

		zeroElevator = new JoystickButton(operator, 9); //change later
		intake = new JoystickButton(operator, 8);
		positionCS = new JoystickButton(operator, 6);
		positionL1 = new JoystickButton(operator, 1);
		positionL2 = new JoystickButton(operator, 3);
		positionL3 = new JoystickButton(operator, 4);
		swtichManipulatorMode = new JoystickButton(operator, 10);
		LSPositioning = new JoystickButton(operator, 5);
		floorGrabbing = new JoystickButton(operator, 2);
		remover = new JoystickButton(operator, 7);
		hatchPlace = new ArtificialButton();
		cargoPlace = new ArtificialButton();
		hatchLSPosition = new ArtificialButton();
		cargoLSPosition = new ArtificialButton();
		grabHatchFloor = new ArtificialButton();
		grabCargoFloor = new ArtificialButton();
		outtake = new ArtificialButton();
		removerHatch = new ArtificialButton();
		selectManualControl = new ArtificialButton();
		elevatorManual = new ArtificialButton();
		wristManual = new ArtificialButton();
		hatchManipulatorManual = new ArtificialButton();
		wristManualWithCargo = new ArtificialButton();
		wristManualWithHatch = new ArtificialButton();
		kickerManual = new ArtificialButton();

		testing = new JoystickButton(operator, 11); //delete
		testingUp = new JoystickButton(operator, 12); //delete


		angleButton7 = new JoystickButton(driver, 7);
		angleButton9 = new JoystickButton(driver, 9);
		angleButton11 = new JoystickButton(driver, 11);
		backAngleButton = new JoystickButton(driver, 2);

		angleButton7.toggleWhenPressed(new LockedAngleControl(90, 118.75, -118.75));
		angleButton9.toggleWhenPressed(new LockedAngleControl(0, 90, -90));
		angleButton11.toggleWhenPressed(new LockedAngleControl(-90, 61.25, -61.25));
		backAngleButton.toggleWhenPressed(new setDriveTo180());

		intake.toggleWhenPressed(new StartIntakeMotors());
		positionCS.whenPressed(new PlacingOnCS(RobotMap.hatchLevelOneHeight));
		positionL1.whenPressed(new PlacingOnRocket(RobotMap.hatchLevelOneHeight));
		positionL2.whenPressed(new PlacingOnRocket(RobotMap.hatchLevelTwoHeight));
		positionL3.whenPressed(new PlacingOnRocket(RobotMap.hatchLevelThreeHeight));
		LSPositioning.whenPressed(new LSPosition());
		floorGrabbing.whenPressed(new GrabFromFloor());
		remover.whenPressed(new Removing());
		swtichManipulatorMode.whenPressed(new SwitchMode());

		hatchPlace.whenActive(new HatchPlacer());
		cargoPlace.whenActive(new CargoPlacer());
		hatchLSPosition.whenActive(new HatchPositioningForLS());
		cargoLSPosition.whenActive(new CargoPositioningForLS());
		grabHatchFloor.whenActive(new GrabHatchFromFloor());
		grabCargoFloor.whenActive(new GrabCargoFromFloor());
		outtake.toggleWhenActive(new StartOuttakeMotors());
		removerHatch.whenActive(new RemoveHatch(90)); //change degrees
		elevatorManual.whileActive(new ManualElevator());
		wristManual.whileActive(new ManualWrist());
		hatchManipulatorManual.whileActive(new ManualHatchManipulator());
		selectManualControl.whenActive(new SelectManualControl());
		wristManualWithCargo.whileActive(new ManualWristWithCargo());
		wristManualWithHatch.whileActive(new ManualWristWithHatch());
		kickerManual.whileActive(new ManualKicker());

		testing.whenPressed(new TestingPIDS()); //delete
		testingUp.whenPressed(new TestingUp());
		

    	
		cameraButton = new JoystickButton(operator, 1);
		cameraButton.whenPressed(new CameraSwitcher());
		

	
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

		return(deadzone(applyCurve(driver.getRawAxis(2)), 0.2));

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

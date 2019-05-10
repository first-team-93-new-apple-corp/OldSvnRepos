/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.other.CameraCodeAsPIDSource;
import frc.robot.other.ChainedServos;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Endgame;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Manipulator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static Drive drive;
	public static Manipulator manipulator;
	public static Elevator elevator;
	public static OI m_oi;
	public static Endgame endgame;
	Thread m_visionThread;
	public static Limelight limeLight;


	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	


	public static UsbCamera camera1;
	public static UsbCamera camera2;
	public static VideoSink server;
	public static boolean cameraButtonPressed;


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		limeLight = new Limelight();
		drive = new Drive();
		manipulator = new Manipulator();
		elevator = new Elevator();
		endgame = new Endgame();
		m_oi = new OI();
		//m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		Drive.gyro.reset();

		camera1 = CameraServer.getInstance().startAutomaticCapture(0);
		camera2 = CameraServer.getInstance().startAutomaticCapture(1);
		server = CameraServer.getInstance().getServer();
		cameraButtonPressed = false;
		limeLight = new Limelight();

		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//THIS CODE SELECTS THE MODE AT THE BEGINNING OF THE MATCH
		if(Manipulator.intakeLightSensor.get())
		{
			Manipulator.robotMode = Manipulator.Mode.CARGO;
			Manipulator.haveHatch = false;
		}
		else
		{
			Manipulator.robotMode = Manipulator.Mode.HATCH;
			Manipulator.haveHatch = true;
		}
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		ChainedServos.set(1);

		// System.out.println(HatchRemoval.hatchRemoverRotator.getVoltage());
		// System.out.println(Elevator.elevatorEncoder.pidGet());

		Scheduler.getInstance().run();

		if(Manipulator.robotMode == Manipulator.Mode.CARGO)
		{
			SmartDashboard.putBoolean("CARGOHATCH", true);
		}
		else
		{
			SmartDashboard.putBoolean("CARGOHATCH", false);
		}

		SmartDashboard.putData("ELEVATOR PID", Elevator.ElevatorPID);
		SmartDashboard.putData("WRIST PID", Manipulator.wristPID);
		
		SmartDashboard.putData("CLOSE ROTATE PID", Drive.closeRotationPID);
		SmartDashboard.putData("FAR ROTATE PID", Drive.rotationPID);
		SmartDashboard.putNumber("GYRO VAL", Drive.gyroPIDSource.pidGet());

		SmartDashboard.putNumber("FLANGLE", Drive.FLModule.getRawAngle());
		SmartDashboard.putNumber("FRANGLE", Drive.FRModule.getRawAngle());
		SmartDashboard.putNumber("BRANGLE", Drive.BRModule.getRawAngle());
		SmartDashboard.putNumber("BLANGLE", Drive.BLModule.getRawAngle());
		SmartDashboard.putData("FLANGLE PID", Drive.FLModule.rotatePID);
		SmartDashboard.putData("FRANGLE PID", Drive.FRModule.rotatePID);
		SmartDashboard.putData("BRANGLE PID", Drive.BRModule.rotatePID);
		SmartDashboard.putData("BLANGLE PID", Drive.BLModule.rotatePID);

		SmartDashboard.putNumber("WRIST HEs", Manipulator.rotateWristSensor.getVoltage()) ;
		SmartDashboard.putNumber("KICKER HEs", Manipulator.kickerSensor.getVoltage());


		SmartDashboard.putBoolean("Reed val", Elevator.bottomLimit.get());
		SmartDashboard.putNumber("Eleavtor value", Elevator.elevatorEncoder.pidGet());

		SmartDashboard.putData("CAMERACODEPID", Drive.lockedXCamDrive);
		SmartDashboard.putNumber("CAMERACODEPIDLOCATION", CameraCodeAsPIDSource.cameraXSource.pidGet());
		SmartDashboard.putNumber("HABLIFTENCODER", Manipulator.habLiftEncoder.get());
		 if(OI.driver.getRawButton(1)){
			// NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledmode").setNumber(0);
			 NetworkTableEntry ledModeEntry = Limelight.table.getEntry("ledMode");
			 ledModeEntry.setDouble(3);
			 }
			 else{
				//NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledmode").setNumber(1);
				 NetworkTableEntry ledModeEntry = Limelight.table.getEntry("ledMode");
				 ledModeEntry.setDouble(0);
			 }
			 SmartDashboard.putBoolean("Have hatch", Manipulator.haveHatch);

		SmartDashboard.putBoolean("cargo sensor", Manipulator.intakeLightSensor.get());


		if(Endgame.endgameMode == Endgame.ENDGAMEMODE.ENDGAME)
		{
			System.out.println("We're in the endgame now");
			System.out.println("DONT FALL");
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
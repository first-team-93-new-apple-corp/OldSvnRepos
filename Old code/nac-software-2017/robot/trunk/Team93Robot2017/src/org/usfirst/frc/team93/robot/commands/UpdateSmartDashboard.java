package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator;
import org.usfirst.frc.team93.robot.subsystems.GearManipulator.GearLocation;
import org.usfirst.frc.team93.robot.subsystems.VisionProcessor;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Put SmartDashboard updating logic here.
 */
public class UpdateSmartDashboard extends Command
{
	/**
	 * @codereview josh.hawbaker 4.6.17 Moved all the variable initialization
	 *             out of execute. Also, these booleans are never updated, so
	 *             they can be set up here manually.
	 */
	boolean bottomFrontInPosition;
	boolean bottomBackInPosition;
	boolean chuteInPosition;
	boolean pegInPosition;
	
	boolean standard = true;
	boolean driveDistance = true;
	boolean driveRate = false;
	boolean driveSPI = false;
	boolean camera = false;
	boolean beltPoints = false;
	
	public UpdateSmartDashboard()
	{
		// Use requires() here to declare subsystem dependencies
		// This command is constantly running constantly to update the
		// dashboard.
		requires(Robot.smartDashboardWriter);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// SmartDashboard.putBoolean("MAINTAIN", Drive.getMaintainYaw());
		// SmartDashboard.putNumber("YAW TO MAINTAIN", Drive.getDefaultYaw());
		
		SmartDashboard.putBoolean("COMPRESSOR", GearManipulator.getCompressorActive());
		if (driveDistance)
		{
			SmartDashboard.putNumber("LEFT FRONT DRIVE DISTANCE", Drive.DRIVE_ENCODER_LEFT_FRONT_DISTANCE.get());
			SmartDashboard.putNumber("LEFT BACK DRIVE DISTANCE", Drive.DRIVE_ENCODER_LEFT_BACK_DISTANCE.get());
			SmartDashboard.putNumber("RIGHT FRONT DRIVE DISTANCE", Drive.DRIVE_ENCODER_RIGHT_FRONT_DISTANCE.get());
			SmartDashboard.putNumber("RIGHT BACK DRIVE DISTANCE", Drive.DRIVE_ENCODER_RIGHT_BACK_DISTANCE.get());
			SmartDashboard.putNumber("DRIVE DISTANCE", Drive.DRIVE_ENCODERS.get());
		}
		
		if (driveRate)
		{
			SmartDashboard.putNumber("LEFT FRONT DRIVE RATE", Drive.DRIVE_ENCODER_LEFT_FRONT_RATE.get());
			SmartDashboard.putNumber("LEFT BACK DRIVE RATE", Drive.DRIVE_ENCODER_LEFT_BACK_RATE.get());
			SmartDashboard.putNumber("RIGHT FRONT DRIVE RATE", Drive.DRIVE_ENCODER_RIGHT_FRONT_RATE.get());
			SmartDashboard.putNumber("RIGHT BACK DRIVE RATE", Drive.DRIVE_ENCODER_RIGHT_BACK_RATE.get());
		}
		
		if (driveSPI)
		{
			SmartDashboard.putNumber("LEFT FRONT SPI", Drive.LFSPI_dummy.get());
			SmartDashboard.putNumber("LEFT BACK SPI", Drive.LBSPI_dummy.get());
			SmartDashboard.putNumber("RIGHT FRONT SPI", Drive.RFSPI_dummy.get());
			SmartDashboard.putNumber("RIGHT BACK SPI", Drive.RBSPI_dummy.get());
		}
		
		if (beltPoints)
		{
			/*
			 * this section is used to calculate if the gear manipulator is in a
			 * certain setpoint's range
			 */
			if (Math.abs(GearManipulator.getBeltPosition() - GearManipulator.getLocation(GearLocation.BOTTOM_FRONT)) < 30)
			{
				bottomFrontInPosition = true;
			}
			else
			{
				bottomFrontInPosition = false;
			}
			if (Math.abs(GearManipulator.getBeltPosition() - GearManipulator.getLocation(GearLocation.BOTTOM_BACK)) < 30)
			{
				bottomBackInPosition = true;
			}
			else
			{
				bottomBackInPosition = false;
			}
			if (Math.abs(GearManipulator.getBeltPosition() - GearManipulator.getLocation(GearLocation.CHUTE)) < 30)
			{
				chuteInPosition = true;
			}
			else
			{
				chuteInPosition = false;
			}
			if (Math.abs(GearManipulator.getBeltPosition() - GearManipulator.getLocation(GearLocation.PEG)) < 30)
			{
				pegInPosition = true;
			}
			else
			{
				pegInPosition = false;
			}
			
			/*
			 * these booleans return true if the gear manipulator is at said
			 * locations
			 */
			SmartDashboard.putBoolean("Bottom Front Position", bottomFrontInPosition);
			SmartDashboard.putBoolean("Bottom Back Position", bottomBackInPosition);
			SmartDashboard.putBoolean("Chute Position", chuteInPosition);
			SmartDashboard.putBoolean("Peg Position", pegInPosition);
		}
		
		if (standard)
		{
			/*
			 * these are the standard printouts that we want during a game
			 */
			// return true if the claws are open
			SmartDashboard.putBoolean("TOP CLAW", !GearManipulator.frontClawIsClosed());
			SmartDashboard.putBoolean("BOTTOM CLAW", !GearManipulator.backClawIsClosed());
			
			// return a number corresponding to manipulator's location on belt
			SmartDashboard.putNumber("BELT ENCODER", GearManipulator.getBeltPosition());
			
			// return true if the intake motors are running
			SmartDashboard.putBoolean("TOP INTAKE", GearManipulator.GEAR_INTAKE_TOP_MOTOR.get() != 0);
			SmartDashboard.putBoolean("BOTTOM INTAKE", GearManipulator.GEAR_INTAKE_BOTTOM_MOTOR.get() != 0);
			
			// return true if the peg end of the robot is the front
			SmartDashboard.putBoolean("DRIVE ORIENTATION", Drive.getOrientation() > 0);
		}
		
		if (camera)
		{
			/*
			 * these are the printouts used to debug the camera code
			 */
			SmartDashboard.putNumber("CAMERA FORWARD", VisionProcessor.forwardSource().pidGet());
			SmartDashboard.putNumber("CAMERA LEFT RIGHT", VisionProcessor.centerSource().pidGet());
			SmartDashboard.putBoolean("CAMERA TAPE VISIBLE", VisionProcessor.tapeVisible);
		}
	}
	
	// Make this return true when this Command no longer needs to run
	// execute()
	@Override
	protected boolean isFinished()
	{
		// this runs during all of teleop - it is never 'finished'
		return false;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		// this command will run continuously while the robot is enabled
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		// nothing else uses this subsystem, so the command won't be interrupted
	}
}
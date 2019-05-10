package org.usfirst.frc.team93.robot.commands.drive;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import org.usfirst.frc.team93.robot.subsystems.VisionProcessor;
import org.usfirst.frc.team93.robot.utilities.DummyPIDSource;
import org.usfirst.frc.team93.robot.utilities.Team93PIDController;
import org.usfirst.frc.team93.robot.utilities.crabdrive.CrabDriveCoordinator.CrabDriveMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class takes input from Colby's VisionProcessor and turns it into robot
 * motion.
 * 
 * This first makes sure yaw is is correct
 * 
 * Then, this moves left/right (using the center source) until it is aligned.
 * 
 * Then, it goes forward to the peg, making sure to stay aligned via yaw and
 * left/right.
 */
public class DriveVisionProcessor extends Command
{
	Double yaw;
	DummyPIDSource center;
	DummyPIDSource forward;
	
	public enum State
	{
		YAW, CENTER, FORWARD;
	}
	
	State m_state;
	
	double yawTolerance;
	double centerTolerance;
	double forwardTolerance;
	
	/**
	 * @codereview josh.hawbaker 3.14.17 I initialized these up here instead of
	 * in functions lower down.
	 */
	double minimum;
	double closest;
	double dir;
	boolean yawOnTarget;
	boolean centerOnTarget;
	boolean forwardOnTarget;
	
	Team93PIDController centerControl;
	Team93PIDController forwardControl;
	
	Timer timer;
	double timeThreshold = 0.5;
	
	public DriveVisionProcessor(double yaw, double yawTolerance, double centerTolerance, double forwardTolerance)
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
		this.yaw = yaw;
		// get tolerances
		this.yawTolerance = yawTolerance;
		this.centerTolerance = centerTolerance;
		this.forwardTolerance = forwardTolerance;
	}
	
	public DriveVisionProcessor(double yawTolerance, double centerTolerance, double forwardTolerance)
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
		// set yaw in initialize if not given
		// get tolerances
		this.yawTolerance = yawTolerance;
		this.centerTolerance = centerTolerance;
		this.forwardTolerance = forwardTolerance;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		// if yaw was not set, default to closest peg to current yaw
		if (yaw == null)
		{
			// find closest peg angle
			List<Double> pegYaws = new ArrayList<Double>();
			pegYaws.add(0.0);
			pegYaws.add(60.0);
			pegYaws.add(-60.0);
			// find closest peg to current yaw
			minimum = 360.0;
			closest = 0.0;
			for (Double angle : pegYaws)
			{
				double angularDistance = Math.abs(closestAngle(Drive.GYRO.get(), angle));
				if (angularDistance < minimum)
				{
					minimum = angularDistance;
					closest = angle;
				}
			}
			// set yaw to closest
			yaw = closest;
		}
		
		// get instances of these here to avoid null pointers
		center = VisionProcessor.centerSource();
		forward = VisionProcessor.forwardSource();
		
		// initialize state to yaw
		m_state = State.YAW;
		
		// init PID Controllers
		centerControl = new Team93PIDController(0.005, 0, 0, 0, center, Drive.CRAB_DRIVE_COORDINATOR.speedReceiver);
		forwardControl = new Team93PIDController(0.005, 0, 0, 0, forward, Drive.CRAB_DRIVE_COORDINATOR.speedReceiver);
		
		// enable the crab drive coordinator
		Drive.enableCrabDrive();
		// use robot centric mode, since we are using the camera
		Drive.CRAB_DRIVE_COORDINATOR.setMode(CrabDriveMode.RobotCentric);
		
		timer = new Timer();
		timer.start();
		timer.reset();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// update VisionProcessor data
		VisionProcessor.targetYaw = yaw;
		
		// First, make sure the yaw is aligned.
		// Without an aligned yaw, camera feedback is not very useful.
		if (Math.abs(Drive.CRAB_DRIVE_COORDINATOR.yawError()) >= yawTolerance)
		{
			// set state
			m_state = State.YAW;
			// request from CrabDriveCoordinator
			Drive.CRAB_DRIVE_COORDINATOR.request(0, 0, yaw);
			return;
		}
		
		// Secondly, make sure we are centered left/right before moving forward.
		if (Math.abs(center.get()) > centerTolerance)
		{
			// disable forward movement
			forwardControl.disable();
			// try to turn wheels rightward
			// -90.0 is rightward, which is positive center value
			Drive.CRAB_DRIVE_COORDINATOR.directionReceiver.set(-90.0);
			// if wheels are on target, start using PID to move
			Drive.CRAB_DRIVE_COORDINATOR.wheelsSetTolerance(8);
			if (Drive.CRAB_DRIVE_COORDINATOR.wheelsOnTarget())
			{
				// enable left/right control
				centerControl.enable();
				// set setpoint if we can see the tape
				if (VisionProcessor.tapeVisible)
				{
					centerControl.setSetpoint(center.get());
				}
			}
			// set state
			m_state = State.CENTER;
			// maintain yaw
			Drive.CRAB_DRIVE_COORDINATOR.yawReceiver.set(yaw);
			return;
		}
		
		// Lastly, move forward
		if (Math.abs(forward.get()) < forwardTolerance)
		{
			// disable left/right movement
			centerControl.disable();
			// try to turn wheels forward
			// 0.0 is forward, which is positive center value
			Drive.CRAB_DRIVE_COORDINATOR.directionReceiver.set(0.0);
			// if wheels are on target, start using PID to move
			Drive.CRAB_DRIVE_COORDINATOR.wheelsSetTolerance(8);
			if (Drive.CRAB_DRIVE_COORDINATOR.wheelsOnTarget())
			{
				// enable left/right control
				forwardControl.enable();
				// set setpoint if we can see the tape
				if (VisionProcessor.tapeVisible)
				{
					forwardControl.setSetpoint(forward.get());
				}
			}
			// set state
			m_state = State.FORWARD;
			// maintain yaw
			Drive.CRAB_DRIVE_COORDINATOR.yawReceiver.set(yaw);
			return;
		}
		
		Drive.CRAB_DRIVE_COORDINATOR.request(0, 0, yaw);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		yawOnTarget = Math.abs(Drive.CRAB_DRIVE_COORDINATOR.yawError()) <= yawTolerance;
		centerOnTarget = Math.abs(center.get()) <= centerTolerance;
		forwardOnTarget = Math.abs(forward.get()) <= forwardTolerance;
		// if not all conditions are met
		if (!(yawOnTarget && centerOnTarget && forwardOnTarget))
		{
			timer.start();
			timer.reset();
		}
		// must be at ending point for a certain amount of time.
		return (yawOnTarget && centerOnTarget && forwardOnTarget && timer.get() > timeThreshold);
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		// reset VisionProcessor data
		VisionProcessor.targetYaw = 0.0;
		// back to field centric
		Drive.CRAB_DRIVE_COORDINATOR.setMode(CrabDriveMode.FieldCentric);
		// disable the crab drive coordinator
		Drive.disableCrabDrive();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		this.end();
	}
	
	/**
	 * Get the closest angle between the given angles.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private double closestAngle(double a, double b)
	{
		// get direction
		dir = modulo(b, 360.0) - modulo(a, 360.0);
		
		// convert from -360 to 360 to -180 to 180
		if (Math.abs(dir) > 180.0)
		{
			dir = -(Math.signum(dir) * 360.0) + dir;
		}
		return dir;
	}
	
	/**
	 * Modulo that works with negative numbers and always returns a positive
	 * number.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private double modulo(double a, double b)
	{
		/**
		 * @codereview josh.hawbaker 3.14.17 Again, we need a standard /
		 * agreement on turnary statements.
		 */
		return (a < 0 ? b + (a % b) : a % b);
	}
}

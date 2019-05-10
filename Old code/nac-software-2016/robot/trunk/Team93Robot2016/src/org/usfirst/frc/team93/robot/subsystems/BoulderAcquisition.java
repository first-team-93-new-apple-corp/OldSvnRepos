package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class is for the Ball Acquistion system
 */
public class BoulderAcquisition extends Subsystem
{

	private static boolean expectingBoulder = true;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new RecoverBall());
	}

	/**
	 * This method lowers the shooter
	 */
	public static void lowerShooter()
	{
		RobotMap.SHOOTING_ANGLE_TALON.set(-1.0);
	}

	/**
	 * This method raises shooter
	 */
	public static void raiseShooter()
	{
		RobotMap.SHOOTING_ANGLE_TALON.set(1.0);
	}

	/**
	 * This method returns if a ball is in the shooter.
	 * 
	 * @return state if ball is present in the shooter
	 * @codereview ColbyMckinley: As of Feb 20, there is a new sensor for ball
	 *             detection, implement this as soon as the sensor is wired
	 */
	public static boolean getBallDetect()
	{
		return (RobotMap.DetectBall.get());
	}

	/**
	 * This method acquires a ball from the ground.
	 */
	public static void acquireBall()
	{
		if (!getBallDetect() && expectingBoulder)
		{
			lowerShooter();
		} else
		{
			raiseShooter();
		}
	}

	/**
	 * This method sets the state if the robot should be expecting a ball
	 * 
	 * @param state
	 *            The state of expecting a ball in the shooter
	 * @codereview ColbyMcKinley: Is this method used outside this class?
	 */
	private static void setExpectingBoulder(boolean state)
	{
		expectingBoulder = state;
	}

	/**
	 * This method returns the state of expecting the ball
	 * 
	 * @return The state of expecting a ball in the shooter
	 */
	public static boolean getExpectingBoulder()
	{
		return expectingBoulder;
	}

	/**
	 * This method runs the ball acquisition motors
	 * 
	 * @param speed
	 *            the speed of the motors should run
	 * @codereview ColbyMcKinley: Would it be a good idea to use
	 *             getExpectingBoulder() as a requirement for turning the
	 *             motors?
	 */
	public static void turnBallRollers(double speed)
	{
		RobotMap.LEFT_BACQUASITION.set(speed);
		RobotMap.RIGHT_BACQUASITION.set(speed);
	}

}
package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.DriveContinuous;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This is the subsystem code for drive
 * 
 * @author New Apple Corps Robotics Team 93
 * 
 */
public class Drive extends Subsystem
{

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	static int m_direction = -1;

	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new DriveContinuous(1.0));

	}

	public static void shiftHighGear()
	{
		// shifts the solonoids outward, putting us in high gear
		RobotMap.GearShift.set(DoubleSolenoid.Value.kForward);

	}

	public static void shiftLowGear()
	{
		// retracts the solonoids inward, putting us in low gear
		RobotMap.GearShift.set(DoubleSolenoid.Value.kReverse);
	}

	public static boolean getGear()
	{
		if (RobotMap.GearShift.get() == DoubleSolenoid.Value.kForward)
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * Sets the left motor group.
	 * 
	 * @param value
	 *            The value to set the left motor group to.
	 */
	public static void setLeftMotors(double speed)
	{
		speed = m_direction * Math.pow(speed, 3);
		if (m_direction == 1)
		{
			RobotMap.frontLeft.set(-speed);
			RobotMap.topLeft.set(speed);
			RobotMap.backLeft.set(-speed);
		} else
		{
			RobotMap.frontRight.set(speed);
			RobotMap.topRight.set(-speed);
			RobotMap.backRight.set(speed);
		}

	}

	/**
	 * Sets the right motor group.
	 * 
	 * @param value
	 *            The value to set the right motor group to.
	 */
	public static void setRightMotors(double speed)
	{
		speed = m_direction * Math.pow(speed, 3);
		if (m_direction == 1)
		{
			RobotMap.frontRight.set(speed);
			RobotMap.topRight.set(-speed);
			RobotMap.backRight.set(speed);
		} else
		{
			RobotMap.frontLeft.set(-speed);
			RobotMap.topLeft.set(speed);
			RobotMap.backLeft.set(-speed);
		}
	}

	/**
	 * Sets all of the motors to the given value. Uses the gains of each motor
	 * group. To ignore gains, use setAllMotorsIgnoreGains.
	 * 
	 * @param value
	 *            The value to set the motors to.
	 */
	public static void setAllMotors(double speed)
	{
		setLeftMotors(speed);
		setRightMotors(speed);
	}

	/**
	 * Sets all of the motors to the given value, ignoring gains.
	 * 
	 * @param value
	 *            The value to set the motors to, ignoring gains.
	 */
	public static void setAllMotorsIgnoreGains(double value)
	{

		double oldLeftGain = RobotMap.driveAllMotorsGroup.getGainA();
		double oldRightGain = RobotMap.driveAllMotorsGroup.getGainB();

		RobotMap.driveAllMotorsGroup.setGains(1.0, 1.0);

		RobotMap.driveAllMotorsGroup.set(value);

		RobotMap.driveAllMotorsGroup.setGains(oldLeftGain, oldRightGain);

	}

	public static void SetDirection(int Direction)
	{
		m_direction = Direction;
	}

	/**
	 * Resets the Drive Encoders.
	 */
	public static void resetSensors()
	{
		RobotMap.RIGHT_DRIVE_ENCODER.reset();
		RobotMap.LEFT_DRIVE_ENCODER.reset();
	}

	public static int getDirection()
	{
		return m_direction;
	}

}

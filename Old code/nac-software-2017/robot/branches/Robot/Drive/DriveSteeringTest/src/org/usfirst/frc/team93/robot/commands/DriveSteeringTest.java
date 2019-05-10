package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Takes a port for the motor to be tested, and whether that motor is going
 * forward or backward. Then if the motor is going the wrong way it is added to
 * the bad motor list, which is returned once done.
 * 
 * @author Control 93
 *
 */

// port to be tested needs to be in the list created in RobotMap

public class DriveSteeringTest extends Command
{

	int m_testMotorNumber = 1;
	boolean m_testDirectionForward = true;
	int time = 2000;
	double firstSpeed = 0.5;
	boolean work_Is_Done = false;
	long start_time_msecs = 0;

	// ArrayList<String> badMotors = new ArrayList<>();

	public DriveSteeringTest(int testMotorNumber, boolean testDirectionForward)
	{
		m_testMotorNumber = testMotorNumber;
		m_testDirectionForward = testDirectionForward;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		start_time_msecs = System.currentTimeMillis();
		// time in milliseconds when initialized

	}

	@Override
	protected void execute()
	{
		// System.out.println("DriveSteeringTest::execute " + m_testMotorNumber
		// + " " + m_testDirectionForward);
		// update motor outputs
		int multiplier = 0;

		int portToTest = RobotMap.SteeringMotorTestSequence[m_testMotorNumber];
		// get a port from robot map
		// int multiplier = (m_testDirectionForward) ? 1 : -1;

		if (m_testDirectionForward == true)
		{
			multiplier = 1;
		} else
		{
			multiplier = -1;
		}

		if ((System.currentTimeMillis() - start_time_msecs) >= (time))
		// if time elapsed > time delay setting
		{
			work_Is_Done = true; // the command is finished
			multiplier = 0; // stop motors
		}

		// System.out.println("Port: " + portToTest + " Speed: " + (multiplier *
		// firstSpeed));
		switch (portToTest)
		{
		case RobotMap.FRONT_RIGHT_INDEX: // if the port is the front right motor
			RobotMap.frontRight.set(multiplier * firstSpeed);
			/*
			 * if (frontRight.getValue !<= value that means its going the right
			 * way) { badMotor.add("frontRight"); }
			 */
			break;
		case RobotMap.BACK_RIGHT_INDEX: // if the port is the back right motor
			RobotMap.backRight.set(multiplier * firstSpeed);
			/*
			 * if (backRight.getValue !<= value that means its going the right
			 * way) { badMotor.add("backRight"); }
			 */
			break;
		case RobotMap.FRONT_LEFT_INDEX: // if the port is the front left motor
			RobotMap.frontLeft.set(multiplier * firstSpeed);
			/*
			 * if (frontLeft.getValue !<= value that means its going the right
			 * way) { badMotor.add("frontLeft"); }
			 */
			break;

		default: // if the port is the back left motor
			RobotMap.backLeft.set(multiplier * firstSpeed);
			/*
			 * if (backLeft.getValue !<= value that means its going the right
			 * way) { badMotor.add("backLeft"); }
			 */
			break;

		}

		// done

		// fancy goal - check if the motor is working (maybe current sensing)

	}

	@Override
	protected boolean isFinished()
	{
		if (work_Is_Done == true)
		// if execute sets variable work_Is_Done to true command is finished
		{
			// System.out.println("DriveSteeringTest finished " +
			// m_testMotorNumber + " " + m_testDirectionForward);
			return true;
		}

		// test element is complete.

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		end();
	}

}
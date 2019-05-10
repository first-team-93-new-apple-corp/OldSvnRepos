package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearManipulator extends Subsystem
{
	// CHECK - Not certain that we are using single or double solenoids
	public static DoubleSolenoid topClawSolenoid;
	public static DoubleSolenoid bottomClawSolenoid;
	
	// CHECK - Not sure if we are using Victors or Talons
	public static SpeedController topIntakeMotor;
	public static SpeedController bottomIntakeMotor;
	
	// CHECK - Fairly sure but not certain that Nathan/Natus/Tyler's design
	// has the top claw open and bottom claw without pneumatic activation
	// **note that if this is wrong, I'll have to change !'s later on**
	boolean topClawClosed;
	boolean bottomClawClosed;
	boolean motorsRunning;
	
	public GearManipulator()
	{
		// initialize robot components
		// creating solenoids
		topClawSolenoid = new DoubleSolenoid(RobotMap.getMap().GEAR_TOP_SOLENOID_PIN_A, RobotMap.getMap().GEAR_TOP_SOLENOID_PIN_B);
		bottomClawSolenoid = new DoubleSolenoid(RobotMap.getMap().GEAR_BOTTOM_SOLENOID_PIN_A, RobotMap.getMap().GEAR_BOTTOM_SOLENOID_PIN_B);
		
		// creating talons for the gear intake
		topIntakeMotor = new Talon(RobotMap.getMap().GEAR_GRABBER_TOP_MOTOR);
		bottomIntakeMotor = new Talon(RobotMap.getMap().GEAR_GRABBER_BOTTOM_MOTOR);
		
		// set the initial the subsystem state
		topClawClosed = false;
		bottomClawClosed = true;
		motorsRunning = false;
		
		// set the subsystem state
		setTopClaw(topClawClosed);
		setBottomClaw(bottomClawClosed);
		setIntakeMotors(motorsRunning);
	}
	
	@Override
	protected void initDefaultCommand()
	{
		// I don't know if we should have a default command and what it should
		// be if so
	}
	
	/**
	 * This command toggles only the top claw. It will set it to whatever state
	 * it is not currently at.
	 */
	public void toggleTopClaw()
	{
		// topPneumatic will be extended when the top claw is closed
		boolean topClawClosed = (topClawSolenoid.get() == Value.kForward);
		
		// set the pneumatic to the opposite state that it is in now
		setTopClaw(!topClawClosed);
	}
	
	/**
	 * This command toggles only the bottom claw. It will set it to whatever
	 * state it is not currently at.
	 */
	public void toggleBottomClaw()
	{
		// bottomPneumatic will be extended when bottom claw is open
		boolean bottomClawClosed = (bottomClawSolenoid.get() == Value.kReverse);
		
		// set the pneumatic to the opposite state that it is in now
		setBottomClaw(!bottomClawClosed);
	}
	
	/**
	 * This command will set both the top and bottom pneumatics to the position
	 * that they are not currently set to. This is based off of a boolean that
	 * is stored in GearManipulator.
	 */
	public void toggleGrabber()
	{
		toggleTopClaw();
		toggleBottomClaw();
	}
	
	/**
	 * This command will turn the gear intake motors on if they are currently
	 * off or off if they are currently on.
	 */
	public void toggleIntakeMotors()
	{
		boolean motorsRunning;
		// if the top gear motor is moving at least a little bit
		// (not sure if they will ever return around 0.01 or something like
		// that)
		if ((Math.abs(topIntakeMotor.get()) > 0.1) || (Math.abs(bottomIntakeMotor.get()) > 0.1))
		{
			// then we will remember that the motors are running
			motorsRunning = true;
		}
		// if the motor isn't moving
		else
		{
			// then we will remember that the motors are not running
			motorsRunning = false;
		}
		
		// Set the motors to the opposite of their current state
		setIntakeMotors(!motorsRunning);
	}
	
	/**
	 * Sets the top claw to a value, closed = true, open = false.
	 * 
	 * @param closed
	 */
	public void setTopClaw(boolean closed)
	{
		// if we want to close it, set it to reverse
		if (closed)
		{
			topClawSolenoid.set(Value.kForward);
		}
		// if we want to open it, set it to forward
		else
		{
			topClawSolenoid.set(Value.kReverse);
		}
		topClawClosed = closed;
	}
	
	/**
	 * Sets the bottom claw to a value, closed = true, open = false.
	 * 
	 * @param closed
	 */
	public void setBottomClaw(boolean closed)
	{
		// if we want to close it, set it to reverse
		if (closed)
		{
			bottomClawSolenoid.set(Value.kReverse);
		}
		// if we want to open it, set it to forward
		else
		{
			bottomClawSolenoid.set(Value.kForward);
		}
		bottomClawClosed = closed;
	}
	
	/**
	 * Sets the intake motors to active or not active.
	 * 
	 * @param runMotors
	 */
	public void setIntakeMotors(boolean runMotors)
	{
		// if we want the motors to run
		if (runMotors)
		{
			// start the motors to intake
			setIntakeMotors(-1.0);
			
		}
		// if we want the motors to stop
		else
		{
			// stop the motors
			setIntakeMotors(0.0);
		}
		motorsRunning = runMotors;
	}
	
	/**
	 * Sets the both of the intake motors to a decimal value.
	 * 
	 * @param value
	 */
	public void setIntakeMotors(double value)
	{
		topIntakeMotor.set(value);
		bottomIntakeMotor.set(value);
	}
}
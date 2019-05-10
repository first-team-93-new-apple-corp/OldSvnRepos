package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearManipulatorSub extends Subsystem{
	
	//CHECK - Not certain that we are using single or double solenoids
	public static Solenoid topPneumatic = new Solenoid(0);
	public static Solenoid bottomPneumatic= new Solenoid(1);
	
	//CHECK - Not sure if we are using Victors or Talons
	public static Talon topGearMotor = new Talon(RobotMap.GearGrabberTopMotor);
	public static Talon bottomGearMotor = new Talon(RobotMap.GearGrabberBottomMotor);
	
	//Digital Inputs - If photogates aren't put on, just remove this
	//public static DigitalInput gearDetector = new DigitalInput(RobotMap.gearObtained);
	
	//CHECK - Fairly sure but not certain that Nathan/Natus/Tyler's design
	//has the top claw open and bottom claw without pneumatic activation
	//**note that if this is wrong, I'll have to change !'s later on**
	boolean topClawClosed = false;
	boolean bottomClawClosed = true;
	boolean motorsRunning = false;

	@Override
	protected void initDefaultCommand()
	{
		//I don't know if we should have a default command and what it should be if so
	}
	
	/*
	 * This command toggles only the top claw.  It will set it to whatever state
	 * it is not currently at.
	 */
	public void toggleTopClaw()
	{
		//topPneumatic will be activated when the top claw is closed
		topClawClosed = topPneumatic.get();
		
		//if the claw is closed, we want to open it
		if(topClawClosed)
		{
			//we retract the top pneumatic so that it opens
			topPneumatic.set(false);
		}
		
		//if the claw isn't closed, we want to close it
		else
		{
			//we extend the top pneumatic so that it closes
			topPneumatic.set(true);
		}
	}
	
	/*
	 * This command toggles only the bottom claw.  It will set it to whatever state
	 * it is not currently at.
	 */
	public void toggleBottomClaw()
	{
		//bottomPneumatic will be activated when bottom claw is open
		bottomClawClosed = !bottomPneumatic.get();
		
		//if the claw is closed, we want to open it
		if(bottomClawClosed)
		{
			//we extend the bottom pneumatic so that it opens
			bottomPneumatic.set(true);
		}
		
		//if the claw is not closed, we want to close it
		else
		{
			//we retract the bottom pneumatic so that it closes
			bottomPneumatic.set(false);
		}
	}
	
	/*
	 * This command will set both the top and bottom pneumatics to the position
	 * that they are not currently set to.  This is based off of a boolean that
	 * is stored in GearManipulatorSub.
	 */
	public void toggleGrabber()
	{
		this.toggleTopClaw();
		this.toggleBottomClaw();
	}

	/*
	 * This command will turn the gear intake motors on if they are currenlty off
	 * or off if they are currently on.
	 */
	public void toggleGearIntakeMotors()
	{
		//if the top gear motor is moving at least a little bit
		//(not sure if they will ever return around 0.01 or something like that)
		if(topGearMotor.get() > 0.1)
		{
			//then we will remember that the motors are running
			motorsRunning = true;
		}
		//if the motor isn't moving
		else
		{
			//then we will remember that the motors are not running
			motorsRunning = false;
		}
		
		//if we have detected that the motors are running
		if(motorsRunning)
		{
			//stop the motors
			topGearMotor.set(0);
			bottomGearMotor.set(0);
		}
		//if we detect that the motors are not running
		else
		{
			//start the motors
			topGearMotor.set(1);
			bottomGearMotor.set(1);
		}
		//I promised nathan that I would commit this comment, I'll probably remove it
		//if I make future revisions
		//lol im grant no comments here xd
	}
}
package org.usfirst.frc.team93.robot.commands.gearmanipulator;

import org.usfirst.frc.team93.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command group runs so that we can receive reading from LT and RT as if
 * they were buttons even though they're axis
 * 
 * @author josh hawbaker
 */
public class ShoulderButtonReader extends Command
{
	boolean ltState;
	boolean ltStatePrevious = false;
	boolean rtState;
	boolean rtStatePrevious = false;
	// drive station recognizes the axis in these ports
	final int ltAxis = 2;
	final int rtAxis = 3;
	
	Command back;
	Command wall;
	
	public ShoulderButtonReader()
	{
		// does NOT require the subsystem. The commands it starts do require it
		// instead.
		back = new BackAcquireA();
		wall = new WallAcquireA();
	}
	
	@Override
	protected void execute()
	{
		// set the booleans to be true if the triggers are held down and false
		// if they aren't
		ltState = OI.operator.getRawAxis(ltAxis) > .8 ? true : false;
		rtState = OI.operator.getRawAxis(rtAxis) > .8 ? true : false;
		
		// if the trigger is pressed down and wasn't pressed previously
		// (this makes sure that one press only runs the command once)
		if (ltState && !ltStatePrevious)
		{
			// move the gear manipulator to the far back setpoint
			back.start();
		}
		// do the same thing for right trigger
		if (rtState && !rtStatePrevious)
		{
			// move the gear manipulator to the far forward setpoint
			wall.start();
		}
		ltStatePrevious = ltState;
		rtStatePrevious = rtState;
	}
	
	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}
}

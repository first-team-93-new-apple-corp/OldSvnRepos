package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  This is the class that holds the auto commands for the robot.
 */
public class AutonomousDrive extends CommandGroup
{
	/*
     * Declare the desired autonomous modes here and assign them with a non
     * designated number
     */
    final int PLAN_NO_AUTONOMOUS = 0;
    final int PLAN_DRIVE_FORWARD = 1;

    /*
     * Declare variables, clearly named based off of the purpose of them
     * here, with the proper value here
     */
	private int driveDistance =  8744; //drives 134 inches or 8744 encoder ticks
	private int maxError = 87; //robot must be within 8 inches or 87 ticks

	public AutonomousDrive()
	{
		/*
	     * When not using switches, comment out the for loop and set the desired
	     * Autonomous mode to AutoPlan
	     */
	    int AutoPlan = PLAN_NO_AUTONOMOUS;
	
	    switch (AutoPlan) {
	    	case PLAN_NO_AUTONOMOUS:
		        // Does nothing
		        break;
	    	case PLAN_DRIVE_FORWARD:
		        addSequential(new DriveForward(driveDistance, maxError));
		        break;
			default:
	    		break;
	    }
	}
}

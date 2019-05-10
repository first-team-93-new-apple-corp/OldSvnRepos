package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CANTalon8Simple extends InstantCommand {

    public CANTalon8Simple() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
    	RobotMap.CANTalonConnectionPortNumber = 8;
    	RobotMap.pdpConnectionPortNumber = 14;
    }

}

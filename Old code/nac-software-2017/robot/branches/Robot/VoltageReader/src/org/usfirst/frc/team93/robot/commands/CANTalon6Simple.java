package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CANTalon6Simple extends InstantCommand {

    public CANTalon6Simple() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
    	RobotMap.CANTalonConnectionPortNumber = 6;
    	RobotMap.pdpConnectionPortNumber = 1;
    }

}

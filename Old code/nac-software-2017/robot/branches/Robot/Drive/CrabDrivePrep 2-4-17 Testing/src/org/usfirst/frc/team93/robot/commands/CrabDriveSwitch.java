package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CrabDriveSwitch extends InstantCommand {

    public CrabDriveSwitch() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
    	Drive.toggleOrientation();
    	System.out.println(Drive.getOrientation());
    }

}

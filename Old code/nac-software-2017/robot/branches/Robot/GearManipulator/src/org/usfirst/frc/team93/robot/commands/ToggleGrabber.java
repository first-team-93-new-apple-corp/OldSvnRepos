package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *This command toggles both the top and bottom claws of the gear manipulator simply by toggling both of them.
 *It will reverse both of the claws (if top is closed and bottom is open, top will open and bottom will close).
 */
public class ToggleGrabber extends InstantCommand {

    public ToggleGrabber()
    {
        super();
    	requires(Robot.gearSub);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize()
    {
    	Robot.gearSub.toggleGrabber();
    }
}
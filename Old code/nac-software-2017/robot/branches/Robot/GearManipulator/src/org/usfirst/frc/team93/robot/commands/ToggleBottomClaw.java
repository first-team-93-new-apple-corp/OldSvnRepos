package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *This command toggles the bottom claw of the gear manipulator using the method defined in GearManipulatorSub
 */
public class ToggleBottomClaw extends InstantCommand
{
    public ToggleBottomClaw()
    {
        super();
        requires(Robot.gearSub);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize()
    {
    	Robot.gearSub.toggleBottomClaw();
    }
}
package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *This command toggles the motors that intake the gear on either end of the manipulator
 *(if they are on, it turns them off and if they are off, it turns them on)
 */
public class ToggleGearMotors extends InstantCommand {

    public ToggleGearMotors()
    {
        super();
        //Although this command is a part of the gear manipulator subsystem, I don't think it's necessary to require
        //the subsystem as it shouldn't get in the way of the other components.
        //requires(Robot.gearSub);
    }

    // Called once when the command executes
    protected void initialize()
    {
    	Robot.gearSub.toggleGearIntakeMotors();
    }
}
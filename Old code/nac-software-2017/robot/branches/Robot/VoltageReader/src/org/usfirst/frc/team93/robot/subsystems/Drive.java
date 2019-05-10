
package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.commands.DriveContinuous;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveContinuous());
    }
}


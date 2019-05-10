
package nacteam93.robot2014.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import nacteam93.robot2014.commands.armContinuous;

/**
 *
 */
public class Pivoter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public double angle_min;
    public double angle_max;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new armContinuous());
    }
}

package nacteam93.robot2014.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import nacteam93.robot2014.RobotMap;
import nacteam93.robot2014.commands.DriveContinuous;
//import static nacteam93.robot2014.RobotMap.driveAll;

/**
 *
 */
public class Drive extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public boolean gearShifted = false;
    
    public Drive(){
        
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveContinuous());
    }
}

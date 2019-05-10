
package nacteam93.robot2014.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import nacteam93.robot2014.*;

/**
 *
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    private static boolean isRetracted = true;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public static boolean isRetracted() {
        return isRetracted;
    }
    
    public void setRetracted(boolean newValue){
        isRetracted = newValue;
    }
}
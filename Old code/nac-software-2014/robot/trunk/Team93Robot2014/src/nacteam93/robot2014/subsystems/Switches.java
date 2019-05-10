/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import nacteam93.robot2014.RobotMap;

/**
 *
 * @author NAC Controls
 */
public class Switches extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public boolean autonomousSelectorOne(){
        return RobotMap.autoSelectorOne.get();
    }
    
    public boolean autonomousSelectorTwo(){
        return RobotMap.autoSelectorTwo.get();
    }
    
    public boolean autonomousSelectorThree(){
        return RobotMap.autoSelectorThree.get();
    }
    
    public boolean autonomousSelectorFour(){
        return RobotMap.autoSelectorFour.get();
    }
    
    public boolean autonomousSelectorFive(){
        return RobotMap.autoSelectorFive.get();
    }
    
    public boolean autonomousSelectorSix(){
        return RobotMap.autoSelectorSix.get();
        //Whether or not we want to shoot our shooter while the robot is moving forward so the ball has more power.
        //True: Shoot the ball while the robot is moving. This is new, untested autonomous code. Do not use unless this has been tested.
        //False: Shoot the ball after stopping. This is the old, working autonomous code. Use if the new autonomous code is not tested.
        //WARNING: Untuned values with changing the robot's autonomous with this switch.
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

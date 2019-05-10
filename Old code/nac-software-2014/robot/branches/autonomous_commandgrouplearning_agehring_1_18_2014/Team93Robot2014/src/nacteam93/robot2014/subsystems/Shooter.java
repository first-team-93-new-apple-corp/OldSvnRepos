
package nacteam93.robot2014.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import nacteam93.robot2014.*;
import nacteam93.robot2014.commands.*;

/**
 *
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    private boolean retracted = false;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new RetractShooter());
    }
    
    private boolean checkRetracted(){
        boolean result = false;        
        //We should populate RobotMap soon! Errors are bad!
//        boolean photogateReading = RobotMap.whateverTheShooterPhotogateIsCalled.TELLMEYOURVALUE();
  //      if(photogateReading){
    //        result = true;
      //  }        
        return result;
    }

    public boolean isRetracted(){
        retracted = this.checkRetracted();
        return retracted;
    }
}
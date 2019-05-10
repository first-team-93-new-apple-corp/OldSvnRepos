/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Team 93 NEW Apple Corpses Controls
 */

//This class provides a getter for the value of any given X or Y axis on any
//given joystick on any given controller. A deadzone can be given as well.

public class JoystickValue {    
    /**
     * @param joystick The Controller on which the joystick is located.
     * @param axis Which axis the value is wanted from.
     * @param deadzone How large (between 0 and 1) the deadzone (a range that all values smaller than this are set to 0) should be.
     * @return
     */
    public static double update(Joystick joystick, int axis, double deadzone) {        
        double value = joystick.getRawAxis(axis);
        if(checkInDeadzone(value, deadzone)){
            return 0.0;
        }
        return value;
    }
    
    /**
     * @param value Value given from the function "update" to check whether the value is within the deadzone.
     * @param deadzone How large (between 0 and 1) the deadzone (a range that all values smaller than this are set to 0) should be.
     * @return
     */
    public static boolean checkInDeadzone(double value, double deadzone) {
        if (value >= -deadzone && value <= deadzone) {
            return true;
        }
        return false;
    }
}
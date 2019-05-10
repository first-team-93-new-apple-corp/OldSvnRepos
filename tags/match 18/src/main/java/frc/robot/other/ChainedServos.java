package frc.robot.other;

import frc.robot.subsystems.Manipulator;

public class ChainedServos
{
    static double setpoint = 0;
    public static void set(double value)
    {
         double writtenVal = 1;
        if(value < 0)
        {
           // writtenVal = 0;
        }
        else if(value > 1)
        {
            //writtenVal = 1;
        }
        else
        {
            //writtenVal = value;
        }
        writtenVal = ((0.69 * writtenVal) + 0.3);
        setpoint = writtenVal;
        Manipulator.hatchManipulatorServoRight.set(writtenVal);
        Manipulator.hatchManipulatorServoLeft.set(1-writtenVal);
        
    }
    public static double getSetpoint()
    {
        return setpoint;
    }
}
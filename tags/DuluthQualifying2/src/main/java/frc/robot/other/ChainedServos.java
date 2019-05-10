package frc.robot.other;

import frc.robot.subsystems.Manipulator;

public class ChainedServos
{
    static double setpoint = 0;
    public static void set(double value)
    {
        double writtenVal;
        if(value < 0)
        {
            writtenVal = 0;
        }
        else if(value > 1)
        {
            writtenVal = 1;
        }
        else
        {
            writtenVal = value;
        }
        writtenVal = ((0.79 * writtenVal) + 0.2);
        setpoint = writtenVal;
        Manipulator.hatchManipulatorServoRight.set(writtenVal);
        Manipulator.hatchManipulatorServoLeft.set(1-writtenVal);
    }
    public static double getSetpoint()
    {
        return setpoint;
    }
}
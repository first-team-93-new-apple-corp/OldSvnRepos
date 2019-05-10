package frc.robot.other;

import java.util.ArrayList;

import frc.robot.subsystems.Manipulator;

public class ChainedServos
{
    private static ArrayList<Double> outputAverage = new ArrayList<Double>();

    static double setpoint = 0;

    public ChainedServos()
    {
        for (int i = 0; i < 5; i++) {
            outputAverage.add(0.0);
        }
    }

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
        writtenVal = ((0.69 * writtenVal) + 0.3);
        setpoint = writtenVal;
        // if(usingMovingAvg)
        // {
        
            Manipulator.hatchManipulatorServoRight.set(writtenVal);
            Manipulator.hatchManipulatorServoLeft.set(1-writtenVal);
        outputAverage.add(writtenVal);
        outputAverage.remove(0);
        double sum = 0;
        for (int i = 0; i < outputAverage.size(); i++) {
            sum += outputAverage.get(i);
        }
        writtenVal = sum / outputAverage.size();

        
    }
    // else{

    // }
    
    public static double getSetpoint()
    {
        return setpoint;
    }
}
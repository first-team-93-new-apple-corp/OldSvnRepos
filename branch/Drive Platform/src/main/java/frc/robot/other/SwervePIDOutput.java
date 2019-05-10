package frc.robot.other;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.subsystems.Drive;

public class SwervePIDOutput
{
    public static SwervePIDOutput.TorqueOutput torqueOutput = new SwervePIDOutput.TorqueOutput();
    public static SwervePIDOutput.XOutput xOutput = new SwervePIDOutput.XOutput();
    public static SwervePIDOutput.YOutput yOutput = new SwervePIDOutput.YOutput();

    public static double valueOfTorqueOutput = 0;
    public static double valueOfXOutput = 0;
    public static double valueOfYOutput = 0;

    public static class TorqueOutput implements PIDOutput
    {

        @Override
        public void pidWrite(double output) 
        {
            valueOfTorqueOutput = output;
            SwerveDrive.move(valueOfYOutput, valueOfXOutput, valueOfTorqueOutput, -Drive.gyro.getAngle());

        }

    }
    public static class XOutput implements PIDOutput
    {

        @Override
        public void pidWrite(double output) 
        {
            valueOfXOutput = output;
            SwerveDrive.move(valueOfYOutput, valueOfXOutput, valueOfTorqueOutput, -Drive.gyro.getAngle());
        }

    }
    public static class YOutput implements PIDOutput
    {

        @Override
        public void pidWrite(double output) 
        {
            valueOfYOutput = output;
            SwerveDrive.move(valueOfYOutput, valueOfXOutput, valueOfTorqueOutput, -Drive.gyro.getAngle());
        }

    }
}
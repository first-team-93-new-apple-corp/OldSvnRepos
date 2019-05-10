package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.ScalerContinuous;
import org.usfirst.frc.team93.robot.utilities.CANTalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Scaler extends Subsystem {
	public static CANTalonSRX scalerMotor;
	public static DigitalInput bottomLimit;
	public static DigitalInput topLimit;
	public static Encoder ScalerEncoder;
	public static int bottomEncoderTick;
	public static int topEncoderTick;
    // Put methods for controlling this subsystem here. Call these from Commands.
	public Scaler()
	{
		scalerMotor = new CANTalonSRX(RobotMap.ScalerMotor);
		bottomLimit = new DigitalInput(RobotMap.ScalerBottomLimit);
		topLimit = new DigitalInput(RobotMap.ScalerTopLimit);
		ScalerEncoder = new Encoder(RobotMap.ScalerEncoderA, RobotMap.ScalerEncoderB);
		bottomEncoderTick = -10;     //Tune these
		topEncoderTick = 500;
	}

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ScalerContinuous());
    }
    public static double GetLocationPercentage() 
    {
    	int top = topEncoderTick;
    	int bottom = bottomEncoderTick;
    	int real = ScalerEncoder.getRaw();
    	double percent;
    	top -= bottom;
    	real -= bottom;
    	bottom = 0;
    	percent = (real / bottom) * 100;
    	return percent;
    }
    /**
     * 
     * @param percent
     * The current percent at which the device is located
     * @param minMotorThreashhold
     * The smallest value that the motor can be set to without not having enough torque
     * @param minThreshold
     * The percent at which the motor starts decreasing speed
     * 
     */
    public static double downwardsMultiplier(double percent, double minMotorThreshold, double minThreshold)
    {
    	if(percent >= minThreshold)
    	{
    		return 1;
    	}
    	else
    	{
    		double output = 1 / minMotorThreshold;
    		
    		if(output < minMotorThreshold)
    		{
    			return minMotorThreshold;
    		}
    		else
    		{
    			return output;
    		}
    	}
    	
    }
    /**
     * 
     * @param percent
     * The current percent at which the device is located
     * @param maxMotorThreshold
     * The smallest value that the motor can be set to without not having enough torque
     * @param maxThreshold
     * The percent at which the motor starts decreasing speed
     * @return
     */
    public static double upwardsMultiplier(double percent, double maxMotorThreshold, double maxThreshold)
    {
    	if(percent <= maxThreshold)
    	{
    		return 1;
    	}
    	else
    	{
    		double output = 1 / maxMotorThreshold;
    		
    		if(output > maxMotorThreshold)
    		{
    			return maxMotorThreshold;
    		}
    		else
    		{
    			return output;
    		}
    	}
    	
    }
}


package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.utilities.CANTalonSRX;
import org.usfirst.frc.team93.robot.utilities.InvertedDiPin;
import org.usfirst.frc.team93.robot.utilities.OutputOffset;
import org.usfirst.frc.team93.robot.utilities.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the appendage that holds the claw
 */
public class Scaler extends Subsystem
{
	public static final double ScalerTop = 1000;
	public static final double ScalerBottom = 0;
	public static final double ScalerDefault = 500;// should not have to be used but is here just in case needed
	public static final double ScalerTolerances = 5;
	public static final double ScalerMinSpeed = 5;
	
	public static CANTalonSRX scalerMotor;
	public static DigitalInput rawBottomLimit;
	public static InvertedDiPin bottomLimit;
	public static DigitalInput topLimit;
	public static Encoder ScalerEncoder;
	public static int bottomEncoderTick;
	public static int topEncoderTick;
	public static PIDController ScaleController;
	public static RelativeEncoder EncoderPIDOutput;
	public static OutputOffset offsetMotorOutput;
	
	public static enum ScalerLocation
	{
		TOP, BOTTOM
	}
	
	// Put methods for controlling this subsystem here. Call these from Commands.
	public Scaler()
	{
		scalerMotor = new CANTalonSRX(RobotMap.ScalerMotor);
		rawBottomLimit = new DigitalInput(RobotMap.ScalerBottomLimit);
		bottomLimit = new InvertedDiPin(rawBottomLimit);
		// topLimit = new DigitalInput(RobotMap.ScalerTopLimit);
		ScalerEncoder = new Encoder(RobotMap.ScalerEncoderA, RobotMap.ScalerEncoderB);
		bottomEncoderTick = -10; // Tune these
		topEncoderTick = 500;
		EncoderPIDOutput = new RelativeEncoder(ScalerEncoder);
		offsetMotorOutput = new OutputOffset(scalerMotor, -0.095);
		ScaleController = new PIDController(-0.002, 0, 0, EncoderPIDOutput, offsetMotorOutput); // To be tuned
	}
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		// setDefaultCommand(new ScalerContinuous(OI.scalerMinMotorVal,
		// OI.scalerPercentDecrease));
	}
	
	// public static double GetLocationPercentage()
	// {
	// int top = topEncoderTick;
	// int bottom = bottomEncoderTick;
	// int real = ScalerEncoder.getRaw();
	// double percent;
	// top -= bottom;
	// real -= bottom;
	// bottom = 0;
	// percent = (real / top) * 100;
	// return percent;
	// }
	
	/**
	 * 
	 * @param percent
	 *            The current percent at which the device is located
	 * @param minMotorThreashhold
	 *            The smallest value that the motor can be set to without not having
	 *            enough torque
	 * @param minThreshold
	 *            The percent at which the motor starts decreasing speed
	 * 
	 */
	public static double downwardsMultiplier(double percent, double minMotorThreshold, double minThreshold)
	{
		if (percent >= minThreshold)
		{
			return 1;
		}
		else
		{
			double output = 1 / minMotorThreshold;
			
			if (output < minMotorThreshold)
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
	 *            The current percent at which the device is located
	 * @param maxMotorThreshold
	 *            The smallest value that the motor can be set to without not having
	 *            enough torque
	 * @param maxThreshold
	 *            The percent at which the motor starts decreasing speed
	 * @return
	 */
	public static double upwardsMultiplier(double percent, double maxMotorThreshold, double maxThreshold)
	{
		if (percent <= maxThreshold)
		{
			return 1;
		}
		else
		{
			double output = 1 / maxMotorThreshold;
			
			if (output > maxMotorThreshold)
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

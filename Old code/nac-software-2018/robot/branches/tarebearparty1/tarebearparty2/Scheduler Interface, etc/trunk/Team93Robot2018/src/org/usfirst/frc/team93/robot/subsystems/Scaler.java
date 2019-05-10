package org.usfirst.frc.team93.robot.subsystems;

import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.commands.ScalerContinuous;
import org.usfirst.frc.team93.robot.utilities.CANTalonSRX;
import org.usfirst.frc.team93.robot.utilities.InvertedDiPin;
import org.usfirst.frc.team93.robot.utilities.OutputOffset;
import org.usfirst.frc.team93.robot.utilities.RelativeEncoder;
import org.usfirst.frc.team93.robot.utilities.Team93PIDController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the appendage that holds the claw
 */
public class Scaler extends Subsystem
{
	public static final double ScalerTop = 16000;
	public static final double ScalerBottom = 700;
	public static final double ScalerDefault = 5000;// should not have to be used but is here just in case needed
	public static final double ScalerSwitch = 6000;
	public static final double ScalerTolerances = 100;
	public static final double ScalerMinSpeed = 5;
	
	public static CANTalonSRX scalerMotor;
	public static DigitalInput rawBottomLimit;
	public static InvertedDiPin bottomLimit;
	public static DigitalInput topLimit;
	public static Encoder ScalerEncoder;
	public static int bottomEncoderTick;
	public static int topEncoderTick;
	public static Team93PIDController ScaleControllerUp;
	public static Team93PIDController ScaleControllerDown;
	public static RelativeEncoder EncoderPIDOutput;
	public static OutputOffset offsetMotorOutput;
	
	public static enum ScalerLocation
	{
		TOP, BOTTOM, MIDDLE
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
		EncoderPIDOutput = new RelativeEncoder(ScalerEncoder, bottomLimit);
		offsetMotorOutput = new OutputOffset(scalerMotor, -0.095);
		ScaleControllerUp = new Team93PIDController(-0.002, -0.0001, -0.00015, EncoderPIDOutput, offsetMotorOutput);
		ScaleControllerDown = new Team93PIDController(-0.0005, -0.00007, -0.0027, EncoderPIDOutput, offsetMotorOutput);
		
	}
	
	@Override
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		// setDefaultCommand(new ScalerContinuous(OI.scalerMinMotorVal,
		// OI.scalerPercentDecrease));
		setDefaultCommand(new ScalerContinuous());
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

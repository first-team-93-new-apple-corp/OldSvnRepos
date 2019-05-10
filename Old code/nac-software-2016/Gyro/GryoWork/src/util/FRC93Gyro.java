package util;
import org.usfirst.frc.team93.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.interfaces.Gyro;
public class FRC93Gyro extends AnalogGyro implements PIDSource{

	public FRC93Gryo(int channel)
	{
		super(channel);
	}
	public double pidGet()
	{
		return getAngle();
	}
	public void setSensitivity(double sensitivity)
	{
		
	}
}

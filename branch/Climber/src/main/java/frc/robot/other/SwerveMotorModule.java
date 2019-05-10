package frc.robot.other;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class SwerveMotorModule implements PIDSource {

	// THE LIMITS OF THE INPUT
	double MaxVolts;
	double MinVolts;
	double DeltaVolts;

	double VoltDegRatio;

	double m_ZeroAngle;

	AnalogInput m_encoder;
	public CANVictorSPX m_outputMovement;
	CANVictorSPX m_outputRotation;

	double m_kp;
	double m_ki;
	double m_kd;
	double m_PIDTolerance;
	boolean isInverted = false;

	public PIDController rotatePID;

	public SwerveMotorModule(double maxVolt, double minVolt, double ZeroAngle, AnalogInput encoder,
			CANVictorSPX outputMovement, CANVictorSPX outputRotation, double kp, double ki, double kd,
			double PIDTolerance) {
		MaxVolts = maxVolt;
		MinVolts = minVolt;
		DeltaVolts = (maxVolt - minVolt);
		VoltDegRatio = (360 / DeltaVolts);
		m_encoder = encoder;
		m_outputMovement = outputMovement;
		m_outputRotation = outputRotation;
		m_ZeroAngle = ZeroAngle;
		m_kp = kp;
		m_ki = ki;
		m_kd = kd;
		m_PIDTolerance = PIDTolerance;

		rotatePID = new PIDController(m_kp, m_ki, m_kd, this, m_outputRotation);
		rotatePID.setAbsoluteTolerance(m_PIDTolerance);
		rotatePID.setInputRange(0, 360);
		rotatePID.setContinuous();
	}

	public double getRawAngle() {
		return (m_encoder.getVoltage() - MinVolts) * VoltDegRatio;
	}

	public double getCompleteAngle()
	{
		if(!isInverted)
		{
			return getAngle();
		}
		else
		{
			return(modulo(getAngle() + 180, 360));
		}
	}

	public double getAngle() {
		double Angle = (m_encoder.getVoltage() - MinVolts) * VoltDegRatio;
		Angle -= m_ZeroAngle;
		return modulo(Angle * -1, 360);

	}

	// PID Methods
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	public double pidGet() {
		return getCompleteAngle();
	}

	public void setPIDSourceType(PIDSourceType type) {

	}

	// Setters

	public void setPID(double setpoint) {
		//Find whether it is closer to go forwards or swap polarity
	
		boolean isCurrentCloser = Math.abs(closestAngle(setpoint, getAngle())) < Math.abs(closestAngle(setpoint, getAngle() + 180));
		if(isCurrentCloser)
		{
			rotatePID.setSetpoint(modulo(setpoint, 360));
			isInverted = false;
		}
		else
		{
			rotatePID.setSetpoint(modulo(setpoint, 360));
			isInverted = true;
		}
		 if (!rotatePID.isEnabled()) {
		 	rotatePID.enable();
		 }
	}

	public void disablePID() {
		rotatePID.disable();
	}

	/**
	 * Get the closest angle between the given angles.
	 */
	private static double closestAngle(double a, double b) {
		// get direction
		double dir = modulo(b, 360.0) - modulo(a, 360.0);

		// convert from -360 to 360 to -180 to 180
		if (Math.abs(dir) > 180.0) {
			dir = -(Math.signum(dir) * 360.0) + dir;
		}
		return dir;
	}

	private static double modulo(double a, double b)
	{
		return (a < 0 ? b + (a % b) : a % b);
	}

	public void set(double angle, double output)
	{
		setPID(angle);
		if(isInverted)
		{
			m_outputMovement.set(output * -1);
		}
		else
		{
			
			m_outputMovement.set(output);
		}
	}


}

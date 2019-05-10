package frc.robot.other;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveMotorModule implements PIDSource {

	// THE LIMITS OF THE INPUT
	double MaxVolts;
	double MinVolts;
	double DeltaVolts;

	double VoltDegRatio;

	double m_ZeroAngle;

	AnalogInput m_encoder;
	CANVictorSPX m_outputMovement;
	CANVictorSPX m_outputRotation;

	double m_kp;
	double m_ki;
	double m_kd;
	double m_PIDTolerance;

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
		SmartDashboard.putData(rotatePID);
	}

	public double getRawAngle() {
		return (m_encoder.getVoltage() - MinVolts) * VoltDegRatio;
	}

	public double getAngle() {
		double Angle = (m_encoder.getVoltage() - MinVolts) * VoltDegRatio;
		Angle -= m_ZeroAngle;
		if (Angle < 0) {
			Angle += 360;
		}

		return Angle;
	}

	// PID Methods
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	public double pidGet() {
		return getAngle();
	}

	public void setPIDSourceType(PIDSourceType type) {

	}

	// Setters
	public void setPID(double setpoint) {

		rotatePID.setSetpoint(modulo(setpoint, 360));

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

	private static double modulo(double a, double b) {
		return (((-a) % b) + b) % b;
	}

	public void set(double angle, double output)
	{
		setPID(angle);
		m_outputMovement.set(output);
	}
}

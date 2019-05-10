package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;


/**
 * Crab Drive Robot Angle Control Flow:
 * Gyro -> GyroPIDSource (Source) + Joystick yaw (setpoint) -> PIDController -
 * -> CrabDriveAngleAdjuster (adjustment) + Joystick (control) -
 * -> CrabDriveSpeedMotor -> PIDOutputGroup -> Talon -> Motor -> Robot Angle
 * 
 * Crab Drive Wheel Direction Control Flow:
 * SPI Encoders -> SPIEncoderPIDSource -
 * -> CrabDriveDirectionPIDSource (source) + Joystick direction (setpoint) -> PIDController -
 * -> CrabDriveAngleAdjuster (adjustment) + Joystick (control) -
 * -> CrabDriveSpeedMotor -> PIDOutputGroup -> Talon -> Motor -> Robot Movement
 */
public class CrabDriveContinuous extends Command {
	
	CrabDriveMode m_mode;
	
	//the angle requested to the PID Controller.
	double requestAngle;
	
	public enum CrabDriveMode
	{
		RobotCentric, FieldCentric;
	}
	
    public CrabDriveContinuous() {
    	
    	//Reset these sensors at startup, NOT in initialize, since initialize runs multiple times
    	//and robot may not always be zeroed when it runs.
    	Drive.resetSensors();
    	OI.movement.direction().reset();
    	
    	//requires drive
    	requires(Robot.drive);
    	
    	m_mode = CrabDriveMode.RobotCentric;
    	
    	requestAngle = 0.0;
    }
    
    public CrabDriveContinuous(CrabDriveMode mode)
    {
    	this();
		m_mode = mode;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	//Resets PIDControllers.
    	Drive.YawController.reset();
    	Drive.DirectionController.reset();
    	//Enables PIDControllers.
    	Drive.YawController.enable();
    	Drive.DirectionController.enable();
    	
    	requestAngle = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	//update requested angle
    	double multiplier = 100;
    	if (OI.yaw.get() != 0)
    	{
    		requestAngle = Drive.GYRO.get() + (OI.yaw.get() * multiplier);
    	}
    	else
    	{
    		requestAngle = Drive.GYRO.get();
    	}
    	
    	//reset yaw on button press
    	if (OI.driver.getRawButton(OI.resetButton))
    	{
    		requestAngle = 0.0;
    	}
    	
    	//get wheel angle setpoint from joystick
    	double wheelAngle = 0.0;
    	double movementOrientation = 1.0;
    	//get wheel angle in forward direction
    	double forwardWheelAngle = closestAngle(Drive.DRIVE_DIRECTION_SPI.get(), OI.movement.direction().get());
    	//get wheel angle in reverse direction and compare to wheel angle in forward direction
    	double backwardWheelAngle = closestAngle(Drive.DRIVE_DIRECTION_SPI.get() + 180, OI.movement.direction().get());
    	//If faster way to move is forward...
    	if (Math.abs(forwardWheelAngle) >= Math.abs(backwardWheelAngle))
    	{
    		//set wheels to forward and move
    		wheelAngle = forwardWheelAngle;
    		movementOrientation = 1.0;
    	}
    	else
    	{
    		//set wheels to backward and move
    		wheelAngle = backwardWheelAngle;
    		movementOrientation = -1.0;
    	}
    	
    	//get setpoint from joysticks
    	//find closest angle from gyro to joystick
    	double robotAngle = closestAngle(Drive.GYRO.get(), requestAngle);
    	
    	//set PID setpoints based on mode
    	if (m_mode == CrabDriveMode.RobotCentric)
    	{
	    	/**
	    	 * Robot Centric:
	    	 * Yaw is set to yaw value
	    	 * Wheels are set relative to robot, no offset needed
	    	 */
	    	Drive.YawController.setSetpoint(Drive.GYRO.get() + robotAngle);
	    	Drive.DirectionController.setSetpoint(Drive.DRIVE_DIRECTION_SPI.get() + wheelAngle);
    	}
    	else if (m_mode == CrabDriveMode.FieldCentric)
    	{
	    	/**
	    	 * Field Centric:
	    	 * Yaw is set to yaw value
	    	 * Wheels are set relative to field by subtracting robot angle
	    	 */
	    	Drive.YawController.setSetpoint(Drive.GYRO.get() + robotAngle);
	    	Drive.DirectionController.setSetpoint(Drive.DRIVE_DIRECTION_SPI.get() - Drive.GYRO.get() + wheelAngle);
    	}
    	
    	//Set crab drive speed motor speeds to requested speed from the movement joystick
    	Drive.CRAB_DRIVE_LEFT.set(movementOrientation * OI.movement.magnitude().get() * Drive.getOrientation());
    	Drive.CRAB_DRIVE_RIGHT.set(movementOrientation * OI.movement.magnitude().get() * Drive.getOrientation());
    	
    	//System.out.println("CURRENT: " + Drive.GYRO.get());
    	//System.out.println("GOAL: " + (Drive.GYRO.get() + angle));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Is never finished unless interrupted
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	//disables PIDs
    	Drive.YawController.disable();
    	Drive.DirectionController.disable();
		//sets drive motors to 0.0 to stop
    	Drive.setAllMotors(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
    
    /**
     * Get the closest angle between the given angles.
     * @param a
     * @param b
     * @return
     */
    private double closestAngle(double a, double b)
    {
    	//get direction
    	double dir = modulo(b, 360.0) - modulo(a, 360.0);
    	
    	//convert from -360 to 360 to -180 to 180
    	if (Math.abs(dir) > 180.0)
    	{
    		dir = -(Math.signum(dir) * 360.0) + dir;
    	}
    	return dir;
    }
    
    /**
     * Modulo that works with negative numbers and always returns a positive number.
     * @param a
     * @param b
     * @return
     */
	private double modulo(double a, double b)
	{
		return (a < 0 ? b + (a % b) : a % b);
	}
}
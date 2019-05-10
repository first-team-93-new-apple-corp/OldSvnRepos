package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class SetFiringAngle extends Command
{

    private double m_Angle;

    /**
     * 
     * @param driveDistance
     *            How far to drive
     * @param maxError
     *           Acceptable range of this position
     */
    public SetFiringAngle(double Angle) 
    {

        m_Angle = Angle;

        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    @Override
    /**
     * This function sets the motors up to run to a certain distance
     */
    protected void initialize() 
    {
    	
    	RobotMap.firingAngleControl.enable();
        RobotMap.firingAngleControl.setSetpoint(m_Angle);
    }

	@Override
	protected void execute() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() 
	{
		// TODO Auto-generated method stub
		RobotMap.firingAngleControl.disable();
		RobotMap.FiringAngleTalon.set(0.0);
	}

	@Override
	protected void interrupted() 
	{
		// TODO Auto-generated method stub
		
	}

}

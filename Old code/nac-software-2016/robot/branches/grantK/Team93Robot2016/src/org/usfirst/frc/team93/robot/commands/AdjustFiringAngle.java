package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class AdjustFiringAngle extends Command
{
/*
 * This adjusts the shooter to fire at various angles.
 */
	double m_scalingFactor;
	
    public AdjustFiringAngle(double scalingFactor) 
    {
        // Use requires() here to declare subsystem dependencies
    	 requires(Robot.shooter);
        m_scalingFactor = scalingFactor;
    }
    
    
	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() 
	{
		// TODO Auto-generated method stub
		Shooter.AdjustShootingAngle(m_scalingFactor
                * OI.getDriverLY(0.05 * m_scalingFactor));
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
		
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		
	}

}

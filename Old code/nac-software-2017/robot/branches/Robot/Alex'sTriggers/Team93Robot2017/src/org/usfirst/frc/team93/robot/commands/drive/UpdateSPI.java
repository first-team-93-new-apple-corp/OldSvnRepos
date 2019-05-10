package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.SPIEncoders;
import org.usfirst.frc.team93.robot.utilities.WheelAnglePIDSource;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UpdateSPI extends Command
{
	
	public boolean reset = false;
	
	public UpdateSPI()
	{
		requires(Robot.spi);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		for (SPIEncoders.Port port : SPIEncoders.Port.values())
		{
			SPIEncoders.getSource(port).set(0);
		}
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		if (!reset)
		{
			for (SPIEncoders.Port port : SPIEncoders.Port.values())
			{
				SPIEncoders.getSource(port).set(0);
			}
			for (SPIEncoders.Port port : SPIEncoders.Port.values())
			{
				SPIEncoders.getSPIEncoderPIDSource(port).reset();
			}
			boolean complete = true;
			for (SPIEncoders.Port port : SPIEncoders.Port.values())
			{
				WheelAnglePIDSource waps = SPIEncoders.getSPIEncoderPIDSource(port);
				if ((Math.abs(waps.get()) > 1.0) || waps.getMisreads() > 1)
				{
					System.out.println("RESETTING " + port.name() + "!" + " MISREADS: " + waps.getMisreads());
					complete = false;
				}
			}
			reset = complete;
			if (complete)
			{
				for (SPIEncoders.Port port : SPIEncoders.Port.values())
				{
					WheelAnglePIDSource waps = SPIEncoders.getSPIEncoderPIDSource(port);
					waps.finishReset();
				}
			}
		}
		if (reset)
		{
			SPIEncoders.update();
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return false;
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
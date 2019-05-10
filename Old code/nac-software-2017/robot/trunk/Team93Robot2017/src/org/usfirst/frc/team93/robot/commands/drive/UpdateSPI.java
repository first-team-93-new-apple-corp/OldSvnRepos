package org.usfirst.frc.team93.robot.commands.drive;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.subsystems.SPIEncoders;
import org.usfirst.frc.team93.robot.utilities.WheelAnglePIDSource;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is used to update the readings from the SPI encoders, which
 * allow us to accurately control our crab wheel direction.
 */
public class UpdateSPI extends Command
{
	/**
	 * @codereview josh.hawbaker 3.16.17 Added a javadoc and some comments.
	 */
	
	public boolean reset = false;
	
	public UpdateSPI()
	{
		requires(Robot.spi);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		// for every SPI encoder connected
		for (SPIEncoders.Port port : SPIEncoders.Port.values())
		{
			// set current value to zero (avoid null pointers)
			SPIEncoders.getSource(port).set(0);
		}
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		// if the encoders haven't been reset
		if (!reset)
		{
			// this is necessary because the encoder takes an average over a few
			// readings, so we can't just reset it once during initialize.
			for (SPIEncoders.Port port : SPIEncoders.Port.values())
			{
				// set current value to zero again (software level)
				SPIEncoders.getSource(port).set(0);
			}
			for (SPIEncoders.Port port : SPIEncoders.Port.values())
			{
				// try to restart the encoder hardware
				SPIEncoders.getSPIEncoderPIDSource(port).reset();
			}
			// check if our reset is complete
			boolean complete = true;
			for (SPIEncoders.Port port : SPIEncoders.Port.values())
			{
				WheelAnglePIDSource waps = SPIEncoders.getSPIEncoderPIDSource(port);
				// if the encoder failed to reset or if we are getting misreads
				if ((Math.abs(waps.get()) > 1.0) || waps.getMisreads() > 1)
				{
					// then our reset is not complete yet
					System.out.println("RESETTING " + port.name() + "!" + " MISREADS: " + waps.getMisreads());
					complete = false;
				}
			}
			// update our reset-state checking boolean
			reset = complete;
			if (complete)
			{
				// if the reset is complete
				for (SPIEncoders.Port port : SPIEncoders.Port.values())
				{
					// mark every port as complete with the reset
					WheelAnglePIDSource waps = SPIEncoders.getSPIEncoderPIDSource(port);
					waps.finishReset();
				}
			}
		}
		// if the reset is complete
		if (reset)
		{
			// update the readings for each SPI encoder
			SPIEncoders.update();
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		// this command is meant to run continuously
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
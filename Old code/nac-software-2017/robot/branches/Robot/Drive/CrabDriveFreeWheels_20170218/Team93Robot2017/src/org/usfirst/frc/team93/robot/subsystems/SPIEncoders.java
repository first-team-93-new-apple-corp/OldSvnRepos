package org.usfirst.frc.team93.robot.subsystems;

import java.util.HashMap;

import org.usfirst.frc.team93.robot.commands.UpdateSPI;
import org.usfirst.frc.team93.robot.utilities.MovingDummyPIDSource;
import org.usfirst.frc.team93.robot.utilities.WheelAnglePIDSource;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * SPIEncoders subsystem
 * @author Evans Chen
 * 
 * This class holds the SPI Encoder values.
 * This class periodically updates itself using a Command, UpdateSPI.
 * 
 * To use a SPI as a PIDSource, use SPIEncoders.getSource(port).
 * 
 * This command was established to act as a "middle man" between hardware transactions and the PIDs.
 * This class exists because it is pointless to deal with concurrent programming's locks, synchronization, and debugging,
 * since it is easier and also fully effective just to have this "middle man" class instead.
 */
public class SPIEncoders extends Subsystem
{
	//The command that updates the SPI values.
	public static UpdateSPI s_update;
	
	//The HashMap storing the SPIEncoderPIDSources that perform hardware gets from the SPI.
	public static HashMap<Port, WheelAnglePIDSource> s_sources;
	
	//The HashMap storing SPIEncoderSources, which contain SPI Encoder values that are periodically updated.
	public static HashMap<Port, MovingDummyPIDSource> s_values;
	
	//list of possible ports.
	public enum Port
	{
		LEFT_FRONT, RIGHT_FRONT, LEFT_BACK, RIGHT_BACK;
	}
	
	// @REVIEW NJL This initialization is required before it is safe for static functions to run
	/**
	 * Constructor for SPIEncoders subsystem.
	 * @param leftFrontSPI
	 * @param leftBackSPI
	 * @param rightFrontSPI
	 * @param rightBackSPI
	 * @param leftFrontSource
	 * @param leftBackSource
	 * @param rightFrontSource
	 * @param rightBackSource
	 */
	public SPIEncoders(WheelAnglePIDSource leftFrontSPI, WheelAnglePIDSource leftBackSPI, WheelAnglePIDSource rightFrontSPI, WheelAnglePIDSource rightBackSPI,
			MovingDummyPIDSource leftFrontSource, MovingDummyPIDSource leftBackSource, MovingDummyPIDSource rightFrontSource, MovingDummyPIDSource rightBackSource)
	{
		s_values = new HashMap<Port, MovingDummyPIDSource>();
		s_sources = new HashMap<Port, WheelAnglePIDSource>();
		
		//maps each port to a SPIEncoderPIDSource
		s_sources.put(Port.LEFT_FRONT, leftFrontSPI);
		s_sources.put(Port.LEFT_BACK, leftBackSPI);
		s_sources.put(Port.RIGHT_FRONT, rightFrontSPI);
		s_sources.put(Port.RIGHT_BACK, rightBackSPI);
		
		//maps each port to a SPIEncoderSource
		s_values.put(Port.LEFT_FRONT, leftFrontSource);
		s_values.put(Port.LEFT_BACK, leftBackSource);
		s_values.put(Port.RIGHT_FRONT, rightFrontSource);
		s_values.put(Port.RIGHT_BACK, rightBackSource);
	}
	
	/**
	 * The updating command always runs.
	 */
	@Override
	protected void initDefaultCommand() {
		s_update = new UpdateSPI();
		setDefaultCommand(s_update);
	}
	
	/**
	 * Update the SPIEncoderSource values from the SPIEncoderPIDSource values..
	 */
	public static synchronized void update()
	{
		for (Port port : Port.values())
		{
			update(port);
		}
	}
	
	/**
	 * Update the SPIEncoderSource values from the SPIEncoderPIDSource values on a specific port.
	 * @param port
	 */
	public synchronized static void update(Port port)
	{
		WheelAnglePIDSource spi = getSPIEncoderPIDSource(port);
		MovingDummyPIDSource source = getSource(port);
		source.set(spi.get());
	}
	
	/**
	 * Gets a SPIEncoderPIDSource given a port.
	 * @param port
	 * @return
	 */
	public static WheelAnglePIDSource getSPIEncoderPIDSource(Port port)
	{
		return s_sources.get(port);
	}
	
	/**
	 * Gets a SPIEncoderSource given a port.
	 * @param port
	 * @return
	 */
	public static MovingDummyPIDSource getSource(Port port)
	{
		return s_values.get(port);
	}
	
	/**
	 * Checks whether the SPIEncoders subsystem is ready for use.
	 * @return
	 */
	public static boolean ready()
	{
		return s_update.reset;
	}
}
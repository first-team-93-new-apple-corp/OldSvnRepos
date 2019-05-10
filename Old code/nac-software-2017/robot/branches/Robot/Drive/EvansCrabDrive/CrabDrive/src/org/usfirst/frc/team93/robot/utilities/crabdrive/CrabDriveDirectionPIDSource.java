package org.usfirst.frc.team93.robot.utilities.crabdrive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * CrabDriveDirectionPIDSource
 * @author Evans Chen
 * 
 * Crab Drive Wheel Direction Control Flow:
 * SPI Encoders -> SPIEncoderPIDSource -
 * -> CrabDriveDirectionPIDSource (source) + Joystick direction (setpoint) -> PIDController -
 * -> CrabDriveAngleAdjuster (adjustment) + Joystick (control) -
 * -> CrabDriveSpeedMotor -> PIDOutputGroup -> Talon -> Motor -> Robot Movement
 * 
 * This class takes input from a collection of SPIEncoderPIDSources.
 * This class outputs to a PIDController that controls the Crab Drive Wheel Direction.
 * 
 * PIDSource that gives the direction the drive wheels are pointing.
 * This aggregates the 4 SPIEncoderPIDSources and uses them to give a median value for the Wheel Direction.
 * 
 * This class can be used to get the angles of the wheels relative to the robot.
 * To get the absolute angle of the wheels relative to the game field, use:
 * CrabDriveDirectionPIDSource.get() + GyroPIDSource.get().
 * 
 * The reason this class is separate from MedianPIDSource is because we may want to process SPI input as well,
 * and code for processing that goes in this class.
 */
public class CrabDriveDirectionPIDSource implements PIDSource
{
	//The list containing all of the SPIEncoderPIDSources.
	ArrayList<PIDSource> m_directions;
	
	/**
	 * Constructor for the CrabDriveDirectionPIDSource.
	 * @param directions A list of the SPIEncoderPIDSources.
	 */
	public CrabDriveDirectionPIDSource(List<PIDSource> directions)
	{
		m_directions = new ArrayList<PIDSource>(directions);
	}
	
	/**
	 * Constructor for the CrabDriveDirectionPIDSource.
	 * Supply all of the SPIEncoderPIDSources.
	 * @param LEFT_FRONT
	 * @param LEFT_BACK
	 * @param RIGHT_FRONT
	 * @param RIGHT_BACK
	 */
	public CrabDriveDirectionPIDSource(PIDSource LEFT_FRONT, PIDSource LEFT_BACK, PIDSource RIGHT_FRONT, PIDSource RIGHT_BACK)
	{
		m_directions = new ArrayList<PIDSource>();
		m_directions.add(LEFT_FRONT);
		m_directions.add(LEFT_BACK);
		m_directions.add(RIGHT_FRONT);
		m_directions.add(RIGHT_BACK);
	}
	
	/**
	 * Cannot set PIDSourceType.
	 * This always gives Displacement.
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
	}

	/**
	 * The median direction is a displacement, always.
	 */
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return PIDSourceType.kDisplacement;
	}
	
	/**
	 * Gets the median of the directions of the wheels.
	 * @return The median direction of the Crab Drive wheels.
	 */
	@Override
	public double pidGet() {
		return getMedian();
	}
	
	/**
	 * Gets the median of the directions of the wheels.
	 * @return The median direction of the Crab Drive wheels.
	 */
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * Gets the median of the directions of the wheels.
	 * @return The median direction of the Crab Drive wheels.
	 */
	private double getMedian()
	{
		return median(ToDoubleList(m_directions));
	}
	
	/**
	 * Converts a list of PIDSources to their pidGet double values.
	 * @param sources The list of sources to convert
	 * @return The list of their pidGet() double values.
	 */
	private ArrayList<Double> ToDoubleList(ArrayList<PIDSource> sources)
	{
		//Create a list
		ArrayList<Double> values = new ArrayList<Double>();
		//Add all PIDGet values to list
		for (int i = 0; i < sources.size(); i++)
		{
			values.add(sources.get(i).pidGet());
		}
		//return the list
		return values;
	}
	
	/**
	 * Finds the median of a list of doubles.
	 * @param l The list of doubles.
	 * @return The median of the list.
	 */
	private double median(ArrayList<Double> l)
	{
		//Sort list
		ArrayList<Double> sorted = new ArrayList<Double>(l);
		Collections.sort(sorted);
		
		//Get median
		int middle = sorted.size() / 2;
		if (l.size() % 2 == 1) 
		{
			return sorted.get(middle);
		}
		else
		{
			return (sorted.get(middle - 1) + sorted.get(middle)) / 2;
		}
	}
}

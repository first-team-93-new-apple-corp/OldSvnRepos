package org.usfirst.frc.team93.robot.utilities;

import java.util.ArrayList;
import java.util.Collections;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * MedianPIDSource
 * 
 * This class merges multiple PIDSources into one PIDSource using an arithmetic
 * median. This is useful for ignoring misreads from the encoders so we don't
 * set motors to a crazy value.
 * 
 * @author Evans Chen
 */
public class MedianPIDSource implements PIDSource
{
	/**
	 * @codereview josh.hawbaker 3.21.17 Changed the copy-pasted javadoc from
	 * AveragePIDSource. Also changed every comment that had Average copied
	 * over. Ha
	 */
	
	// the gain of the PIDSource
	double m_gain;
	// The list of PIDSources
	ArrayList<PIDSource> m_sources;
	// The PIDSourceType.
	PIDSourceType m_type;
	
	/*
	 * Large swath of overloaded constructors.
	 */
	/**
	 * The Constructor for the MedianPIDSource.
	 * 
	 * @param sources The ArrayList of sources.
	 * @param gain The pidGet() multiplier.
	 */
	public MedianPIDSource(ArrayList<PIDSource> sources, double gain)
	{
		m_gain = gain;
		m_sources = new ArrayList<PIDSource>(sources);
		m_type = PIDSourceType.kDisplacement;
	}
	
	/**
	 * A default of the Constructor for the MedianPIDSource. Sets gain to 1.0.
	 * 
	 * @param sources The ArrayList of sources.
	 */
	public MedianPIDSource(ArrayList<PIDSource> sources)
	{
		this(sources, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 1 PIDSource and a gain. Makes the
	 * supplied source the only source in the MedianPIDSource.
	 * 
	 * @param source1 The PIDSource
	 * @param gain The gain multiplier
	 */
	public MedianPIDSource(PIDSource source1, double gain)
	{
		this(new ArrayList<PIDSource>(), gain);
		add(source1);
	}
	
	/**
	 * A default of the overloaded Constructor for the MedianPIDSource with 1
	 * PIDSource. Sets gain to 1.0.
	 * 
	 * @param source1 The PIDSource
	 */
	public MedianPIDSource(PIDSource source1)
	{
		this(source1, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 2 PIDSources and a gain. Makes the
	 * supplied source the only source in the MedianPIDSource.
	 * 
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param gain The gain multiplier
	 */
	public MedianPIDSource(PIDSource source1, PIDSource source2, double gain)
	{
		this(new ArrayList<PIDSource>(), gain);
		add(source1);
		add(source2);
	}
	
	/**
	 * A default of the overloaded Constructor for the MedianPIDSource with 2
	 * PIDSources. Sets gain to 1.0.
	 * 
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 */
	public MedianPIDSource(PIDSource source1, PIDSource source2)
	{
		this(source1, source2, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 3 PIDSources and a gain. Makes the
	 * supplied source the only source in the MedianPIDSource.
	 * 
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param source3 The 3rd PIDSource
	 * @param gain The gain multiplier
	 */
	public MedianPIDSource(PIDSource source1, PIDSource source2, PIDSource source3, double gain)
	{
		this(new ArrayList<PIDSource>(), gain);
		add(source1);
		add(source2);
		add(source3);
	}
	
	/**
	 * A default of the overloaded Constructor for the MedianPIDSource with 3
	 * PIDSources. Sets gain to 1.0.
	 * 
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param source3 The 3rd PIDSource
	 */
	public MedianPIDSource(PIDSource source1, PIDSource source2, PIDSource source3)
	{
		this(source1, source2, source3, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 4 PIDSources and a gain.
	 * 
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param source3 The 3rd PIDSource
	 * @param source4 The 4th PIDSource
	 * @param gain The gain multiplier
	 */
	public MedianPIDSource(PIDSource source1, PIDSource source2, PIDSource source3, PIDSource source4, double gain)
	{
		this(new ArrayList<PIDSource>(), gain);
		add(source1);
		add(source2);
		add(source3);
		add(source4);
	}
	
	/**
	 * A default of the overloaded Constructor for the MedianPIDSource with 4
	 * PIDSources. Sets gain to 1.0.
	 * 
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param source3 The 3rd PIDSource
	 * @param source4 The 4th PIDSource
	 */
	public MedianPIDSource(PIDSource source1, PIDSource source2, PIDSource source3, PIDSource source4)
	{
		this(source1, source2, source3, source4, 1.0);
	}
	
	/**
	 * Returns the median of the PIDSources.
	 */
	@Override
	public double pidGet()
	{
		return getMedian();
	}
	
	/**
	 * Alias of pidGet() It does the same thing as pidGet()
	 * 
	 * @return The median of the PIDSources.
	 */
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * Adds a source to the PIDSource list.
	 * 
	 * @param source The source to add.
	 */
	public void add(PIDSource source)
	{
		m_sources.add(source);
	}
	
	/**
	 * Removes a source from the PIDSource list.
	 * 
	 * @param source The source to remove.
	 */
	public void remove(PIDSource source)
	{
		m_sources.remove(source);
	}
	
	/**
	 * Sets the PIDSourceType. This currently has no function.
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		m_type = pidSource;
	}
	
	/**
	 * Returns the PIDSourceType. This currently has no function.
	 */
	@Override
	public PIDSourceType getPIDSourceType()
	{
		return m_type;
	}
	
	/**
	 * Setter for the gain.
	 * 
	 * @param gain The multiplier to set for the MedianPIDSource.
	 */
	public void setGain(double gain)
	{
		m_gain = gain;
	}
	
	/**
	 * Gets the gain of the MedianPIDSource.
	 * 
	 * @return The multiplier for the MedianPIDSource/
	 */
	public double getGain()
	{
		return m_gain;
	}
	
	/**
	 * Getter for the ArrayList<PIDSource> m_sources.
	 * 
	 * @return The ArrayList of PIDSources of the MedianPIDSource.
	 */
	public ArrayList<PIDSource> getSources()
	{
		return m_sources;
	}
	
	/**
	 * Gets the median of the directions of the wheels.
	 * 
	 * @return The median direction of the Crab Drive wheels.
	 */
	private double getMedian()
	{
		return median(ToDoubleList(m_sources));
	}
	
	/**
	 * Converts a list of PIDSources to their pidGet double values.
	 * 
	 * @param sources The list of sources to convert
	 * @return The list of their pidGet() double values.
	 */
	private ArrayList<Double> ToDoubleList(ArrayList<PIDSource> sources)
	{
		// Create a list
		ArrayList<Double> values = new ArrayList<Double>();
		// Add all PIDGet values to list
		for (int i = 0; i < sources.size(); i++)
		{
			values.add(sources.get(i).pidGet());
		}
		// return the list
		return values;
	}
	
	/**
	 * Finds the median of a list of doubles.
	 * 
	 * @param l The list of doubles.
	 * @return The median of the list.
	 */
	private double median(ArrayList<Double> l)
	{
		// Sort list
		ArrayList<Double> sorted = new ArrayList<Double>(l);
		Collections.sort(sorted);
		
		// Get median
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
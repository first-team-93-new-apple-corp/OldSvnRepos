package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import java.util.ArrayList;

/**
 * PIDSourceGroup
 * @author Evans Chen
 * This class merges multiple PIDSources into one PIDSource by adding their values.
 * To use gains for each PIDSource, use PIDSourceExtended.
 */
public class PIDSourceGroup implements PIDSource
{
	
	//the gain of the PIDSource
	double m_gain;
	//The list of PIDSources
	ArrayList<PIDSource> m_sources;
	//The PIDSourceType.
	PIDSourceType m_type;
	
	/*
	 * Large swath of overloaded constructors.
	 */
	/**
	 * The Constructor for the PIDSourceGroup.
	 * @param sources The ArrayList of sources.
	 * @param gain The pidGet() multiplier.
	 */
	public PIDSourceGroup(ArrayList<PIDSource> sources, double gain)
	{
		m_gain = gain;
		m_sources = new ArrayList<PIDSource>(sources);
		m_type = PIDSourceType.kDisplacement;
	}
	
	/**
	 * A default of the Constructor for the PIDSourceGroup. Sets gain to 1.0.
	 * @param sources The ArrayList of sources.
	 */
	public PIDSourceGroup(ArrayList<PIDSource> sources)
	{
		this(sources, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 1 PIDSource and a gain.
	 * Makes the supplied source the only source in the PIDSourceGroup.
	 * @param source1 The PIDSource
	 * @param gain The gain multiplier
	 */
	public PIDSourceGroup(PIDSource source1, double gain)
	{
		this(new ArrayList<PIDSource>(), gain);
		add(source1);
	}
	
	/**
	 * A default of the overloaded Constructor for the PIDSourceGroup with 1 PIDSource. Sets gain to 1.0.
	 * @param source1 The PIDSource
	 */
	public PIDSourceGroup(PIDSource source1)
	{
		this(source1, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 2 PIDSources and a gain.
	 * Makes the supplied source the only source in the PIDSourceGroup.
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param gain The gain multiplier
	 */
	public PIDSourceGroup(PIDSource source1, PIDSource source2, double gain)
	{
		this(new ArrayList<PIDSource>(), gain);
		add(source1);
		add(source2);
	}
	
	/**
	 * A default of the overloaded Constructor for the PIDSourceGroup with 2 PIDSources. Sets gain to 1.0.
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 */
	public PIDSourceGroup(PIDSource source1, PIDSource source2)
	{
		this(source1, source2, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 3 PIDSources and a gain.
	 * Makes the supplied source the only source in the PIDSourceGroup.
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param source3 The 3rd PIDSource
	 * @param gain The gain multiplier
	 */
	public PIDSourceGroup(PIDSource source1, PIDSource source2, PIDSource source3, double gain)
	{
		this(new ArrayList<PIDSource>(), gain);
		add(source1);
		add(source2);
		add(source3);
	}
	
	/**
	 * A default of the overloaded Constructor for the PIDSourceGroup with 3 PIDSources. Sets gain to 1.0.
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param source3 The 3rd PIDSource
	 */
	public PIDSourceGroup(PIDSource source1, PIDSource source2, PIDSource source3)
	{
		this(source1, source2, source3, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 4 PIDSources and a gain.
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param source3 The 3rd PIDSource
	 * @param source4 The 4th PIDSource
	 * @param gain The gain multiplier
	 */
	public PIDSourceGroup(PIDSource source1, PIDSource source2, PIDSource source3, PIDSource source4, double gain)
	{
		this(new ArrayList<PIDSource>(), gain);
		add(source1);
		add(source2);
		add(source3);
		add(source4);
	}
	
	/**
	 * A default of the overloaded Constructor for the PIDSourceGroup with 4 PIDSources. Sets gain to 1.0.
	 * @param source1 The 1st PIDSource
	 * @param source2 The 2nd PIDSource
	 * @param source3 The 3rd PIDSource
	 * @param source4 The 4th PIDSource
	 */
	public PIDSourceGroup(PIDSource source1, PIDSource source2, PIDSource source3, PIDSource source4)
	{
		this(source1, source2, source3, source4, 1.0);
	}
	
	
	/**
	 * Returns the sum of the PIDSources.
	 */
	@Override
	public double pidGet()
	{
		double total = 0;
		for (PIDSource source : m_sources)
		{
			total += source.pidGet();
		}
		return total * m_gain / (double)m_sources.size();
	}
	
	/**
	 * Alias of pidGet()
	 * It does the same thing as pidGet()
	 * @return The sum of the PIDSources.
	 */
	public double get()
	{
		return pidGet();
	}
	
	/**
	 * Adds a source to the PIDSource list.
	 * @param source The source to add.
	 */
	public void add(PIDSource source)
	{
		m_sources.add(source);
	}
	
	/**
	 * Removes a source from the PIDSource list.
	 * @param source The source to remove.
	 */
	public void remove(PIDSource source)
	{
		m_sources.remove(source);
	}

	/**
	 * Sets the PIDSourceType.
	 * This currently has no function.
	 */
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		m_type = pidSource;
	}

	/**
	 * Returns the PIDSourceType.
	 * This currently has no function.
	 */
	@Override
	public PIDSourceType getPIDSourceType() {
		return m_type;
	}
	
	/**
	 * Setter for the gain.
	 * @param gain The multiplier to set for the PIDSourceGroup.
	 */
	public void setGain(double gain)
	{
		m_gain = gain;
	}
	
	/**
	 * Gets the gain of the PIDSourceGroup.
	 * @return The multiplier for the PIDSourceGroup/
	 */
	public double getGain()
	{
		return m_gain;
	}
	
	/**
	 * Getter for the ArrayList<PIDSource> m_sources.
	 * @return The ArrayList of PIDSources of the PIDSourceGroup.
	 */
	public ArrayList<PIDSource> getSources()
	{
		return m_sources;
	}
}
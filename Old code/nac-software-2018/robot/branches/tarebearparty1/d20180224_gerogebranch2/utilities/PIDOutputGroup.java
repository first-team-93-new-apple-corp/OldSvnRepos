package org.usfirst.frc.team93.robot.utilities;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * PIDOutputGroup
 * @author Evans Chen
 * This class merges multiple PIDOutputs into one PIDOutput.
 * set, or pidWrite, will apply to all of the PIDOutputs added to the PIDOutputGroup.
 * This is useful for tasks such as grouping together wheels/motors to form one PIDOutput.
 * To use gains for each PIDOutput, use PIDOutputExtended.
 */
public class PIDOutputGroup implements PIDOutput {
	
	//the gain multiplier of the PIDOutputGroup
	double m_gain;
	
	//The list of PIDOutputs contained in the group.
	ArrayList<PIDOutput> m_outputs;
	
	//The last set value of the PIDOutputGroup.
	double m_value;
	
	/*
	 * Large swath of overloaded constructors.
	 */
	public PIDOutputGroup()
	{
		this(new ArrayList<PIDOutput>());
	}
	
	/**
	 * The Constructor for the PIDOutputGroup.
	 * @param outputs The ArrayList of outputs.
	 * @param gain The pidGet() multiplier.
	 */
	public PIDOutputGroup(ArrayList<PIDOutput> outputs, double gain)
	{
		m_gain = gain;
		m_outputs = new ArrayList<PIDOutput>(outputs);
	}
	
	/**
	 * A default of the Constructor for the PIDOutputGroup. Sets gain to 1.0.
	 * @param outputs The ArrayList of outputs.
	 */
	public PIDOutputGroup(ArrayList<PIDOutput> outputs)
	{
		this(outputs, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 1 PIDOutput and a gain.
	 * Makes the supplied output the only output in the PIDOutputGroup.
	 * @param output1 The PIDOutput
	 * @param gain The gain multiplier
	 */
	public PIDOutputGroup(PIDOutput output1, double gain)
	{
		this(new ArrayList<PIDOutput>(), gain);
		add(output1);
	}
	
	/**
	 * A default of the overloaded Constructor for the PIDOutputGroup with 1 PIDOutput. Sets gain to 1.0.
	 * @param output1 The PIDOutput
	 */
	public PIDOutputGroup(PIDOutput output1)
	{
		this(output1, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 2 PIDOutputs and a gain.
	 * Makes the supplied output the only output in the PIDOutputGroup.
	 * @param output1 The 1st PIDOutput
	 * @param output2 The 2nd PIDOutput
	 * @param gain The gain multiplier
	 */
	public PIDOutputGroup(PIDOutput output1, PIDOutput output2, double gain)
	{
		this(new ArrayList<PIDOutput>(), gain);
		add(output1);
		add(output2);
	}
	
	/**
	 * A default of the overloaded Constructor for the PIDOutputGroup with 2 PIDOutputs. Sets gain to 1.0.
	 * @param output1 The 1st PIDOutput
	 * @param output2 The 2nd PIDOutput
	 */
	public PIDOutputGroup(PIDOutput output1, PIDOutput output2)
	{
		this(output1, output2, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 3 PIDOutputs and a gain.
	 * Makes the supplied output the only output in the PIDOutputGroup.
	 * @param output1 The 1st PIDOutput
	 * @param output2 The 2nd PIDOutput
	 * @param output3 The 3rd PIDOutput
	 * @param gain The gain multiplier
	 */
	public PIDOutputGroup(PIDOutput output1, PIDOutput output2, PIDOutput output3, double gain)
	{
		this(new ArrayList<PIDOutput>(), gain);
		add(output1);
		add(output2);
		add(output3);
	}
	
	/**
	 * A default of the overloaded Constructor for the PIDOutputGroup with 3 PIDOutputs. Sets gain to 1.0.
	 * @param output1 The 1st PIDOutput
	 * @param output2 The 2nd PIDOutput
	 * @param output3 The 3rd PIDOutput
	 */
	public PIDOutputGroup(PIDOutput output1, PIDOutput output2, PIDOutput output3)
	{
		this(output1, output2, output3, 1.0);
	}
	
	/**
	 * An overload of the Constructor, taking 4 PIDOutputs and a gain.
	 * @param output1 The 1st PIDOutput
	 * @param output2 The 2nd PIDOutput
	 * @param output3 The 3rd PIDOutput
	 * @param output4 The 4th PIDOutput
	 * @param gain The gain multiplier
	 */
	public PIDOutputGroup(PIDOutput output1, PIDOutput output2, PIDOutput output3, PIDOutput output4, double gain)
	{
		this(new ArrayList<PIDOutput>(), gain);
		add(output1);
		add(output2);
		add(output3);
		add(output4);
	}
	
	/**
	 * A default of the overloaded Constructor for the PIDOutputGroup with 4 PIDOutputs. Sets gain to 1.0.
	 * @param output1 The 1st PIDOutput
	 * @param output2 The 2nd PIDOutput
	 * @param output3 The 3rd PIDOutput
	 * @param output4 The 4th PIDOutput
	 */
	public PIDOutputGroup(PIDOutput output1, PIDOutput output2, PIDOutput output3, PIDOutput output4)
	{
		this(output1, output2, output3, output4, 1.0);
	}
	
	
	/**
	 * Writes to the PIDOutputGroup, which writes to all of the contained PIDOutputs, multiplied by the gain.
	 */
	@Override
	public void pidWrite(double output) {
		for (PIDOutput pidOutput : m_outputs)
		{
			pidOutput.pidWrite(output * m_gain);
		}
		m_value = output * m_gain;
	}
	
	/**
	 * Sets the value of the PIDOutput using pidWrite().
	 * @param value The value to set the PIDOutput to.
	 */
	public void set(double value)
	{
		pidWrite(value);
	}
	
	/**
	 * Gets the last set value.
	 * @return The last set avlue of the PIDOutputExtended
	 */
	public double get()
	{
		return m_value;
	}
	
	/**
	 * Adds an output to the PIDOutput list.
	 * @param output The output to add.
	 */
	public void add(PIDOutput output)
	{
		m_outputs.add(output);
	}
	
	/**
	 * Removes an output from the PIDOutput list.
	 * @param output The output to remove.
	 */
	public void remove(PIDOutput output)
	{
		m_outputs.remove(output);
	}
	
	/**
	 * Gets the gain.
	 * @return The gain multiplier
	 */
	public double getGain()
	{
		return m_gain;
	}
	
	/**
	 * Sets the gain.
	 * @param value The gain multiplier to set the PIDOutputGroup to.
	 */
	public void setGain(double value)
	{
		m_gain = value;
	}
	
	/**
	 * Returns the ArrayList of PIDOutputs that this PIDOutputGroup writes to.
	 * @return The ArrayList of PIDOutputs
	 */
	public ArrayList<PIDOutput> getPIDOutputList()
	{
		return m_outputs;
	}
	
}
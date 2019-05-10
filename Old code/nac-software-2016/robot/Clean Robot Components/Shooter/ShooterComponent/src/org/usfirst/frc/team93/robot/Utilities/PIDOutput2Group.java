/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.usfirst.frc.team93.robot.Utilities;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * This utility writes to two pidControllers
 * 
 * @author New Apple Corps Team 93 Controls
 */

// This class allows one to merge two PIDControllers into one
// PIDControllerGroup.
// The benefits of this are that one can set two SpeedControllers at once to the
// same value if this is wanted, and if the motors are linked in some way
// (e.g. both of the motors on the right, and both of the motors on the left,
// as we want them always going the same direction).

public class PIDOutput2Group implements PIDOutput
{

	// initializes the last set value. Sets it to 0.0 to avoid null pointers
	// when value has not been set yet.
	private double m_LastSetValue = 0.0;

	// allocates the PIDControllers to be set in the Constructor.
	private PIDOutput m_controllerA;
	private PIDOutput m_controllerB;

	// allocates the multipliers to be set in the constructor.
	private double m_gainA;
	private double m_gainB;

	// The gain (also called multiplier) structure allows the PIDSource to
	// accomplish more.
	// This structure allows for flexibility, such as:
	// One can use this to add, by setting both gains to 1,
	// One can subtract, by setting one gain to 1 and another gain to -1,
	// One can average, by setting both gains to 0.5,
	// and one can multiply, divide, and do other arithmetic, although these are
	// much less important.

	/**
	 * The Constructor for PIDControllerGroup.
	 * 
	 * @param controllerA
	 *            A PIDController to be merged into a single PIDControllerGroup.
	 * @param controllerB
	 *            Another PIDController to be merged into a single
	 *            PIDControllerGroup.
	 * @param gainA
	 *            The multiplier for the first PIDController.
	 * @param gainB
	 *            The multiplier for the second PIDController.
	 */
	public PIDOutput2Group(PIDOutput controllerA, PIDOutput controllerB, double gainA, double gainB)
	{
		// specifies the SpeedControllers to be "merged".
		m_controllerA = controllerA;
		m_controllerB = controllerB;

		// sets the multipliers to the values specified in the parameter list.
		m_gainA = gainA;
		m_gainB = gainB;
	}

	/**
	 * The Constructor for PIDControllerGroup. If not specified, the multipliers
	 * default to 1.0.
	 * 
	 * @param controllerA
	 * @param controllerB
	 */
	public PIDOutput2Group(PIDOutput controllerA, PIDOutput controllerB)
	{

		// exists as a default for the constructor, setting the multipliers to
		// 1.0.

		// //specifies the PIDControllers to be "merged".
		m_controllerA = controllerA;
		m_controllerB = controllerB;

		m_gainA = 1.0;
		m_gainB = 1.0;
	}

	public PIDOutput2Group(Team93PIDController controllerA, Team93PIDController controllerB)
	{
		// TODO Auto-generated constructor stub
		m_controllerA = (PIDOutput) controllerA;
		m_controllerB = (PIDOutput) controllerB;

		m_gainA = 1.0;
		m_gainB = 1.0;
	}

	/**
	 * Used to set the multipliers for the PIDControllers in the
	 * PIDControllerGroup.
	 * 
	 * @param gainA
	 *            Sets the multiplier for the First PIDController.
	 * @param gainB
	 *            Sets the multiplier for the Second PIDController.
	 */
	public void setGains(double gainA, double gainB)
	{
		// sets the multipliers.
		m_gainA = gainA;
		m_gainB = gainB;
	}

	/**
	 * Used to set the multipliers for the PIDControllers in the
	 * PIDControllerGroup.
	 * 
	 * @param gain
	 *            Sets both multipliers to this value.
	 */
	public void setGains(double gain)
	{
		// sets both gains to the same value.
		// serves as a default if only one value is passed as a parameter.
		// uses the command above to do this.
		setGains(gain, gain);
	}

	/**
	 * Common interface for getting the current set speed of a PID controller.
	 * 
	 * @return The current set speed. Value is between -1.0 and 1.0.
	 */
	public double get()
	{
		// This acts as a "getter" of sorts for PIDControllerGroups.

		// gets the value last set using using the value set(double speed) or
		// set(double speed, byte syncGroup).
		return m_LastSetValue;
	}

	/**
	 * Common interface for setting the speed of a PID controller.
	 *
	 * @param speed
	 *            The speed to set. Value should be between -1.0 and 1.0.
	 * @param syncGroup
	 *            The update group to add this Set() to, pending
	 *            UpdateSyncGroup(). If 0, update immediately.
	 */
	public void set(double speed, byte syncGroup)
	{
		// sets the speed. byte syncGroup does nothing, although it is a
		// parameter.
		set(speed);
	}

	/**
	 * Common interface for setting the speed of a PID controller.
	 *
	 * @param speed
	 *            The speed to set. Value should be between -1.0 and 1.0.
	 */
	public void set(double speed)
	{
		// This acts as a "setter" of sorts for PIDControllerGroups.

		// sets the last value set whenever this function is used.
		m_LastSetValue = speed;
		// Sets the speed controllers here
		m_controllerA.pidWrite(speed * m_gainA);
		m_controllerB.pidWrite(speed * m_gainB);
	}

	/**
	 * Disables the speed controllers in the pidControllerGroup.
	 */
	public void disable()
	{
		// disables motors by setting motors to 0, using the set method.
		set(0.0);
	}

	/**
	 * Set the output to the value calculated by PIDController
	 * 
	 * @param output
	 *            the value calculated by PIDController
	 */
	@Override
	public void pidWrite(double output)
	{
		// Sets the motors to the value output by the PIDController, using
		// set(speed).
		set(output);
	}

	/**
	 * Gets the gain for the first controller.
	 * 
	 * @return the gainA
	 */
	public double getGainA()
	{
		return m_gainA;
	}

	/**
	 * Gets the gain for the second controller.
	 * 
	 * @return the gainB
	 */
	public double getGainB()
	{
		return m_gainB;
	}
}

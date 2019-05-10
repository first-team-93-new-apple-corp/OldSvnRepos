package org.usfirst.frc.team93.robot.utilities;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A {@link Button} that gets its state from a {@link GenericHID}. A note on how
 * POVs work: POV is the D-pad on the controller. It returns a value from 0-359,
 * with 0 being up and 90 being right. I don't know if it automatically
 * thresholds the input, so I added that in just in case.
 *
 * @author alex.stevenson
 *
 */
public class POVAsButton extends Button
{
	public static final class Buttons
	{
		public static final int UP = 0, UPPER_RIGHT = 45, RIGHT = 90, LOWER_RIGHT = 135, DOWN = 180, LOWER_LEFT = 225,
				LEFT = 270, UPPER_LEFT = 315;
	}
	
	private final GenericHID m_joystick;
	private final int m_pov;
	private final int m_povPosition;
	private static int m_numberOfPOVButtons;
	
	/**
	 * Create a joystick POV as a button for triggering commands.
	 *
	 * @param joystick The GenericHID object that has the axis (e.g. Joystick,
	 * KinectStick, etc)
	 * @param pov The POV number (see {@link GenericHID#getPOV(int) }
	 * 
	 * @param povButton The button on the POV to monitor
	 */
	public POVAsButton(GenericHID joystick, int pov, int povButton)
	{
		m_joystick = joystick;
		m_pov = pov;
		m_povPosition = povButton;
		m_numberOfPOVButtons += 1;
	}
	
	private int checkThreshold()
	{
		if (m_numberOfPOVButtons < 5)
			return 44;
		else
			return 22;
	}
	
	@Override
	public boolean get()
	{
		int position = m_joystick.getPOV(m_pov);
		int target = m_povPosition;
		int threshold = checkThreshold();
		if (target > 360 - threshold)
			return ((position > target - threshold) || (position < target - 360 + threshold));
		else if (target < threshold)
			return ((position > 360 + target - threshold) || (position < target + threshold));
		else
			return ((position > target - threshold) && (position < target + threshold));
	}
}

package org.usfirst.frc.team93.robot.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A {@link Button} that gets its state from a {@link GenericHID}.
 * 
 * <p>
 * A note on how POVs work: POV is the D-pad on the controller. It returns a
 * value from 0-359, with 0 being up and 90 being right. I don't know if it
 * automatically thresholds the input, so I added that in just in case.
 * 
 * @author alex.stevenson
 *
 */
public class POVAsButton extends Button
{
	
	private final GenericHID m_JOYSTICK;
	private final int m_iPOV; // The POV channel on the joystick
	private final int m_iPOV_POSITION; // The pressed directional on the POV
	private static int m_iNumberOfPOVButtons = 0;
	
	public enum ButtonTypeT
	{
		UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, LEFT_DOWN, LEFT, LEFT_UP
	}
	
	public POVAsButton(GenericHID joystick, int pov, ButtonTypeT povButton)
	{
		m_JOYSTICK = joystick;
		m_iPOV = pov;
		m_iNumberOfPOVButtons += 1;
		switch (povButton)
		{
			case UP:
				m_iPOV_POSITION = 0;
				break;
			case UP_RIGHT:
				m_iPOV_POSITION = 45;
				break;
			case RIGHT:
				m_iPOV_POSITION = 90;
				break;
			case DOWN_RIGHT:
				m_iPOV_POSITION = 145;
				break;
			case DOWN:
				m_iPOV_POSITION = 180;
				break;
			case LEFT_DOWN:
				m_iPOV_POSITION = 225;
				break;
			case LEFT:
				m_iPOV_POSITION = 270;
				break;
			case LEFT_UP:
				m_iPOV_POSITION = 315;
				break;
			default:
				m_iPOV_POSITION = 0;
				System.out.println("You messed up, buddy. " + povButton
						+ " may not have thrown an error, but it's not a valid value.     File: robot.triggers.POVAsButton.java, Line: 64");
		}
	}
	
	@Override
	public boolean get()
	{
		int iPosition = m_JOYSTICK.getPOV(m_iPOV);
		int iTarget = m_iPOV_POSITION;
		return iPosition == iTarget;
	}
	
}

package org.usfirst.frc.team93.robot.triggers;

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
public abstract class POVAsButton extends Button{

	private final GenericHID m_joystick;
	private final int m_pov;
	private final Direction m_povPosition;
	private final int m_threshold;
	private static boolean m_customButtonsUsed;
	private static int m_numberOfPOVButtons;
	/*
	 * @codereview alex.stevenson
	 * 
	 * We should probably replace some of these names, I'm just not sure what to 
	 * call the 45 degree buttons
	 */
	public enum Direction {
		UP			(  0, 338,  22,  316,   44),
		UPPER_RIGHT	( 45,  23,  67, null, null),
		RIGHT		( 90,  68, 112,   46,  134),
		LOWER_RIGHT	(135, 113, 157, null, null),
		DOWN		(180, 158, 202,  136,  224),
		LOWER_LEFT	(225, 203, 247, null, null),
		LEFT		(270, 248, 292,  226,  314),
		UPPER_LEFT	(315, 293, 336, null, null);
		
		private final int actuationPoint;
		private final int bottomThresholdFor4;
		private final int bottomThresholdFor8;
		private final int topThresholdFor4;
		private final int topThresholdFor8;
		Direction(int actuationPoint, int bottomThresholdFor4, int bottomThresholdFor8, int topThresholdFor4, int topThresholdFor8) {
			this.actuationPoint = actuationPoint;
			this.bottomThresholdFor4 = bottomThresholdFor4;
			this.bottomThresholdFor8 = bottomThresholdFor8;
			this.topThresholdFor4 = topThresholdFor4;
			this.topThresholdFor8 = topThresholdFor8;
		}
		
	}
	
	public POVAsButton(GenericHID joystick, int pov, Direction povButton){
		m_joystick = joystick;
		m_pov = pov;
		for(int i = 0; i < 8; i++){
			if(Direction[i] == povButton) {
				
			}
		}
		
		
		
		m_povPosition = povButton;
		m_numberOfPOVButtons += 1;
	}
	
	public POVAsButton(GenericHID joystick, int pov, int activationPoint, int threshold){
		m_joystick = joystick;
		m_pov = pov;
		m_povPosition = activationPoint;
		m_threshold = threshold;
	}
	
	
	
}

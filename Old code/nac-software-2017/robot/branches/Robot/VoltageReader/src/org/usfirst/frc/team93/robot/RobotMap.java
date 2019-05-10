package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;

import java.util.ArrayList;
import java.util.List;
import org.usfirst.frc.team93.robot.OI;

import com.ctre.CANTalon;



/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Hardware and Robot Components go here.
	
	
	
	//I couldn't get it to switch motors during one run, so I gave up on that
	public static int pdpConnectionPortNumber = 0;
	
	//I couldn't get it to switch motors during one run, so I gave up on that
	public static int CANTalonConnectionPortNumber = 7;
	
	public static CANTalon Josh = new CANTalon(CANTalonConnectionPortNumber);
	
	public static List<String> listStrings = new ArrayList<String>();
	
	public static PowerDistributionPanel  pdp = new PowerDistributionPanel(2);
	
	
	
}

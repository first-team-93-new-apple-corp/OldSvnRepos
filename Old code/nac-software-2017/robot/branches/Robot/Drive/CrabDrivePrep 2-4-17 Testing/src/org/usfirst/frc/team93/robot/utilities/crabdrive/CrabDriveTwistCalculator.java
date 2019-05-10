package org.usfirst.frc.team93.robot.utilities.crabdrive;

import org.usfirst.frc.team93.robot.OI;
import org.usfirst.frc.team93.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.PIDOutput;

public class CrabDriveTwistCalculator implements PIDOutput{
	
	final double GAIN_FACTOR = 1.0;

	public CrabDriveTwistCalculator(){
		
	}
	
	public double getRightTwistTerm(){
		double output = (OI.yaw.get() * -GAIN_FACTOR);
		return output;
		
	}
	
	public double getLeftTwistTerm(){
		double output = (OI.yaw.get() * GAIN_FACTOR);
		return output;
		
	}
	
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		
	}
	
}

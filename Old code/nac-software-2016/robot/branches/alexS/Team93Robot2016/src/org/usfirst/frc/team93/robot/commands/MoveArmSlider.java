package org.usfirst.frc.team93.robot.commands;

import org.usfirst.frc.team93.robot.Robot;
import org.usfirst.frc.team93.robot.RobotMap;
import org.usfirst.frc.team93.robot.subsystems.DefenseManipulator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the arm slider.
 */
public class MoveArmSlider extends Command 
{

    private double m_armSliderPosition;
    private double m_maxError;
    private int m_errorTimer; 

    private static Timer totalTime;

    /**
     * 
     * @param movePosition
     *           Position to move the arm slider to
     * @param maxError
     *           Acceptable range of this position
     */
    public MoveArmSlider(double movePosition, double maxError) 
    {

        m_armSliderPosition = movePosition;
        m_maxError = maxError;

        // Use requires() here to declare subsystem dependencies
        requires(Robot.defense);
    }

    // Called just before this Command runs the first time    
    @Override
    /**
     * This function sets the motors up to run to a certain distance
     */
    protected void initialize() 
    {
        
        RobotMap.armSliderPID.reset();
        RobotMap.armSliderPID.enable();
        //Robot.defense.resetArmSliderEncoder();
        RobotMap.armSliderPID.setSetpoint(m_armSliderPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        // returns true when error is low enough for 0.5s
    	double currentError = Math.abs(RobotMap.armSliderPID.getError());
    	System.out.println(currentError);
        if (currentError <= m_maxError) 
        {
        	return true;
        }
		
        return false;
        
    }

    // Called once after isFinished returns true
    @Override
    protected void end() 
    {
        RobotMap.armSliderPID.disable();
        RobotMap.ARM_SLIDER.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() 
    {
        this.end();
    }
}

/**
 * @codereview NLuther: Not implemented at review time.  Review later.
 */
package nacteam93.robot2014.commands;

import nacteam93.robot2014.OI;
import nacteam93.robot2014.RobotMap;
import nacteam93.robot2014.subsystems.Bacquisition;

/**
 *
 * @author NEW Apple Corps Controls
 */

public class OutputBall extends CommandBase {

    private static double m_motorDirection;
    
    public OutputBall() {
        requires(CommandBase.bacquisition);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_motorDirection = -1.0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Bacquisition.setTopMotor(m_motorDirection);
        if(OI.centerBallAndAim0.get()){
            Bacquisition.setTopMotor(m_motorDirection * -1.0);
            Bacquisition.setBottomMotor(m_motorDirection * 0.7);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(OI.manualInterrupt.get()){
            return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Bacquisition.setBallExpected(false);
        RobotMap.rollerOne.set(0.0);
        RobotMap.rollerTwo.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}

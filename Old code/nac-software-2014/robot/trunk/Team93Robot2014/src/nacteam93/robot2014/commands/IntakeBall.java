/**
 * @codereview NLuther: Not implemented at review time.  Review later.
 */
package nacteam93.robot2014.commands;

import nacteam93.robot2014.OI;
import nacteam93.robot2014.RobotMap;
import nacteam93.robot2014.subsystems.Bacquisition;

/**
 * @author NEW Apple Corps Controls
 */

public class IntakeBall extends CommandBase {

    private static double m_motorDirection;

    public IntakeBall() {
        requires(CommandBase.bacquisition);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_motorDirection = 1.0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Bacquisition.setTopMotor(m_motorDirection);
        Bacquisition.setBottomMotor(m_motorDirection);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(OI.manualInterrupt.get()){
            return true;
        }
        return Bacquisition.ballPresent();
    }

    // Called once after isFinished returns true
    protected void end() {
        Bacquisition.setBallExpected(true);
        RobotMap.rollerOne.set(0.0);
        RobotMap.rollerTwo.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}

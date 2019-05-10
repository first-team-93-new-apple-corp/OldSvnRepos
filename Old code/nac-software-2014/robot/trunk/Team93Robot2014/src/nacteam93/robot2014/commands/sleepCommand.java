/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

/**
 *
 * @author naccontrols
 */
public class sleepCommand extends CommandBase {

    private long m_waitTime;
    
    public sleepCommand(long waitTime) {
        // Use requires() here to declare subsystem dependencies        }
        // eg. requires(chassis);]
        m_waitTime = waitTime;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        /**
         * @Codereview CMares
         * Consider using System.err.println(ex.getMessage());
         * over ex.printStackTrace();
         */
        /**
         * @codereview EvansChen:
         * Use comments to explain what the code below does.
         */
        try {
            Thread.sleep(m_waitTime);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}

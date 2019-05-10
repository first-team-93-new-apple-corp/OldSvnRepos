
package nacteam93.robot2014.commands;

import nacteam93.robot2014.RobotMap;


public class shiftDrive extends CommandBase {

    private boolean m_Retract;
    
    public shiftDrive(boolean retract) {
        // Use requires () here to declare subsystem dependencies
        // eg. requires(chassis);
        m_Retract = retract;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() 
    {

    }

    // Called repeatedly when this Command is scheduled to run
    /**
     * @param retract Actuates drive shifting solenoid True: Out False: In
     */
    /**
     * @codereview EvansChen: Although there is a javadoc parameter called "retract",
     * It doesn't exist. Either remove it or put it back in.
     * Also, this fake "parameter" is the same thing as shooter.isRetracted().
     */
    protected void execute() {
        RobotMap.driveShift.set(m_Retract);
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
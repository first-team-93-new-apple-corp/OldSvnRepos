package nacteam93.robot2014.commands;

import nacteam93.robot2014.RobotMap;

/**
 *
 * @author Conor
 */
public class PivoterAlign extends CommandBase {
    
    private double setpoint;
    private double maxError;
    /**
     * Construct the turn command
     * @param angle Angle, in degrees, to turn to. Reference point is the passing position = 0 degrees
     * @param maxError 
     */
    public PivoterAlign(double angle, double maxError) {
        requires(CommandBase.pivoter);
        setpoint = angle;
        this.maxError = maxError;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        RobotMap.armPID.enable();
        RobotMap.armPID.setSetpoint(setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        System.out.println("encoder value = " + RobotMap.armPotentiometer.pidGet());
        System.out.println("motors set to " + RobotMap.armMotors.get());
        System.out.println("Error = " + RobotMap.armPID.getError());        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {        
        boolean result;        
        result = Math.abs(RobotMap.armPID.getError()) <= maxError;
        return result;
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.armPID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
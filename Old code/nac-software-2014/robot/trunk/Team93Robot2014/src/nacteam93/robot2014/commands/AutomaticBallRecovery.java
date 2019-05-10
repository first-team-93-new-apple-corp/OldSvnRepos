/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nacteam93.robot2014.commands;

import nacteam93.robot2014.OI;
import nacteam93.robot2014.subsystems.Bacquisition;

/**
 *
 * @author Controls
 */
public class AutomaticBallRecovery extends CommandBase {

    private static final int ballExpected = 1;
    private static final int ballNotExpected = 2;
    private static int state;

    public AutomaticBallRecovery() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(CommandBase.bacquisition);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        state = Bacquisition.isBallExpected();
        switch(state) {
            /**
             * @codereview EvansChen: switch statements must have a default case.
             * Add a default case.
             */
            case ballExpected:
                if(!Bacquisition.ballPresent()) {
                    Bacquisition.setBottomMotor(0.5);
                    Bacquisition.setTopMotor(0.5);
                }
                else {
                    Bacquisition.setBottomMotor(0.0);
                    Bacquisition.setTopMotor(0.0);
                }
                break;
            case ballNotExpected:
                    Bacquisition.setBottomMotor(0.0);
                    Bacquisition.setTopMotor(0.0);
                break;
            /**
             * @codereview EvansChen: Is case and switch really needed here?
             * Here I think if statements would be better.
             * I think lint4j agrees.
             */
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(OI.manualInterrupt.get()) {
            return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Bacquisition.setBottomMotor(0.0);
        Bacquisition.setTopMotor(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}

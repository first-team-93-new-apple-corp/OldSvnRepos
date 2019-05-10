/**
 * @codereview NLuther: Should we have a default command?
 */
package nacteam93.robot2014.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import nacteam93.robot2014.RobotMap;
import nacteam93.robot2014.commands.AutomaticBallRecovery;

/**
 *
 */
public class Bacquisition extends Subsystem 
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private static int motor1 = 1;
    private static int motor2 = 2;
    private static int isBallExpected;
    private static final int ballExpected = 1;
    private static final int ballNotExpected = 2;
    
    private static final boolean SwitchOpen = true;
    
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new AutomaticBallRecovery());
    }
    
    public static void setTopMotor(double speed) 
    {
        final int topMotor = findTopMotor();
        if(motor1 == topMotor) 
        {
            RobotMap.rollerOne.set(-speed);    
        }
        else
        {
            RobotMap.rollerTwo.set(-speed);
        }
    }
    
    public static void setBottomMotor(double speed) 
    {
        final int topMotor = findTopMotor();
        if(motor1 == topMotor)
        {
            RobotMap.rollerTwo.set(-speed);    
        }
        else
        {
            RobotMap.rollerOne.set(-speed);
        }
    }
    
    public static int findTopMotor() 
    {
        final double currAngle = RobotMap.armPotentiometer.getAngle();
        if(currAngle <= 0)
        {
            return motor2;
        }
        else
        {
            return motor1;
        }
    }
    
    public static boolean ballPresent() 
    {
        boolean result = (RobotMap.ballPresentOne.get() != SwitchOpen || RobotMap.ballPresentTwo.get() != SwitchOpen);
        return result;
    }
    
    public static void setBallExpected(boolean expected){
        if(expected) {
            isBallExpected = ballExpected;
        }
        else {
            isBallExpected = ballNotExpected;
        }
    }
    
    public static int isBallExpected() {
        if(isBallExpected == 1) {
            return ballExpected;
        }
        else {
            return ballNotExpected;
        }
    }
}
/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package nacteam93.robot2014;



import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class EncoderTest {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    SpeedController motor1;
    Encoder encoder1;
    //PIDController controller1;
            
    
    public void robotInit() {
        
        //motor1 = new Victor(1);
        //encoder1 = new Encoder(1,5,1,6,false,CounterBase.EncodingType.k4X);
        //controller1 = new PIDController(0.1,0.001,0.0,encoder1,motor1);
        encoder1 = RobotMap.EncoderLeft;
    }
    
    public void autonomousInit(){
        //controller1.enable();
        //controller1.setSetpoint(1250);
        encoder1.reset();
        encoder1.start();
        //RobotMap.PIDLeft.enable();
        RobotMap.PIDLeft.reset();
        RobotMap.PIDLeft.setSetpoint(1250.0);
        RobotMap.PIDLeft.enable();
    }
    
    /**
     * This function is called periodically during autonomous
     */ 
    public void autonomousPeriodic() {
        System.out.println("getDistance: " + encoder1.getDistance());
        System.out.println("getRaw: " + encoder1.getRaw());
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}

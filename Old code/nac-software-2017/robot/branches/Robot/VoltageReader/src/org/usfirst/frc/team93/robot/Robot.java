
package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team93.robot.commands.DriveContinuous;
import org.usfirst.frc.team93.robot.commands.CANTalon13Simple;
import org.usfirst.frc.team93.robot.commands.CANTalon19Simple;
import org.usfirst.frc.team93.robot.commands.CANTalon6Simple;
import org.usfirst.frc.team93.robot.commands.CANTalon8Simple;
import org.usfirst.frc.team93.robot.commands.SetMotorEightyPercent;
import org.usfirst.frc.team93.robot.commands.SetMotorHundredPercent;
import org.usfirst.frc.team93.robot.commands.SetMotorNinetyPercent;
import org.usfirst.frc.team93.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//CANTalon(0); states that device ID is 0 which should be confirmed through the roboRIO
	
	
	public static Drive drive;
	public static RobotMap robotMap;
	public static OI oi;
	public static DriveContinuous driveCont;
	public static CANTalon13Simple port13;
	public static CANTalon19Simple port19;
	public static CANTalon6Simple port6;
	public static CANTalon8Simple port8;
	public static SetMotorEightyPercent speed8;
	public static SetMotorNinetyPercent speed9;
	public static SetMotorHundredPercent speed10;
	



    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
		robotMap = new RobotMap();
		drive = new Drive();
		driveCont = new DriveContinuous();
		port13 = new CANTalon13Simple();
		port19 = new CANTalon19Simple();
		port6 = new CANTalon6Simple();
		port8 = new CANTalon8Simple();
		speed8 = new SetMotorEightyPercent();
		speed9 = new SetMotorNinetyPercent();
		speed10 = new SetMotorHundredPercent();
		
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        driveCont.start();
//        System.out.println("CANTalon Port" + robotMap.CANTalonConnectionPortNumber);
//        System.out.println("PDP Port" + robotMap.pdpConnectionPortNumber);
        System.out.println("MotorSpeed" + robotMap.Josh.get());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}

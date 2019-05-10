
package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team93.robot.commands.ExampleCommand;
import org.usfirst.frc.team93.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

    Command autonomousCommand;
    Talon testTalon0 = new Talon(0);
    DoubleSolenoid Grabber = new DoubleSolenoid(0,1);
    Compressor compressor1 = new Compressor();
    Joystick Operator = new Joystick(1);
    DigitalInput rFlat = new DigitalInput(0);
    DigitalInput lFlat = new DigitalInput(1);
    DigitalInput rUpright = new DigitalInput(2);
    DigitalInput lUpright = new DigitalInput(3);
    Talon rRotator = new Talon(1);
    Talon lRotator = new Talon(2);
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
		//compressor1.start();
		CameraServer.getInstance().startAutomaticCapture("cam1");
        // instantiate the command used for the autonomous period
        autonomousCommand = new ExampleCommand();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        if(Operator.getRawButton(2) == true)
        {
        	if(rFlat.get() == true) //Should be false with standard Digital Input.
        	{
        		rRotator.set(0.5);
        	}
        	else
        	{
        		rRotator.set(0);
        	}
        	if(lFlat.get() == false)
        	{
        		lRotator.set(0.5);
        	}
        	else
        	{
        		lRotator.set(0);
        	}
        }
        else if(Operator.getRawButton(1) == true)
        {
        	if(rUpright.get() == false) 
        	{
        		rRotator.set(-0.5);
        	}
        	else
        	{
        		rRotator.set(0);
        	}
        	if(lUpright.get() == false) 
        	{
        		lRotator.set(-0.5);
        	}
        	else
        	{
        		lRotator.set(0);
        	}
        }
        else
        {
        	rRotator.set(0);
        	lRotator.set(0);
        }
        testTalon0.set(Operator.getRawAxis(1));
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}

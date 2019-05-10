// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc93.Team93Robot2015;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc93.Team93Robot2015.commands.AutonomousCommandGroup;
import org.usfirst.frc93.Team93Robot2015.commands.DriveContinuous;
import org.usfirst.frc93.Team93Robot2015.subsystems.Drive;
import org.usfirst.frc93.Team93Robot2015.subsystems.Elevator;
import org.usfirst.frc93.Team93Robot2015.subsystems.Switches;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    // Command AutonomousCommandGroup;
    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(value =
    // "MS_CANNOT_BE_FINAL", justification = "I know what I'm doing")
    public static Drive drive = new Drive();
    public static Elevator elevator = new Elevator();
    public static Switches switches = new Switches();
    // public static RobotMap robotMap;
    // public static RobotMap robotMap;

    public static AutonomousCommandGroup autonomousCommand;

    private static Button slowButton = new JoystickButton(OI.driver, 1);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

    @Override
    public void robotInit() {
        RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drive = new Drive();
        switches = new Switches();

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        autonomousCommand = new AutonomousCommandGroup();

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        // RobotMap.Camera.startAutomaticCapture("cam0");

    }

    /**
     * This function is called when the disabled button is hit. You can use it
     * to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        // schedule the autonomous command (example)
        Drive.setAllMotors(0.0);

        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        Drive.setAllMotors(0.0);
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {

        slowButton.whenPressed(new DriveContinuous(0.5));
        slowButton.whenReleased(new DriveContinuous(1.0));
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}

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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc93.Team93Robot2015.commands.DriveContinuous;
import org.usfirst.frc93.Team93Robot2015.commands.DriveForward;
import org.usfirst.frc93.Team93Robot2015.commands.ExtendDrawbridge;
import org.usfirst.frc93.Team93Robot2015.commands.RetractDrawbridge;
import org.usfirst.frc93.Team93Robot2015.commands.SleepCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    // // CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    // joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    // // TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    protected static Joystick operator;
    protected static Joystick driver;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        driver = new Joystick(1);

        operator = new Joystick(2);

        Button slowButton = new JoystickButton(OI.driver, 1);

        Button runDriveForwards = new JoystickButton(OI.driver, 2);

        runDriveForwards.whenPressed(new DriveForward(100, 0.0));

        slowButton.whenPressed(new DriveContinuous(0.5));
        slowButton.whenReleased(new DriveContinuous(1.0));

        Button extendButton = new JoystickButton(OI.operator, 3);
        Button retractButton = new JoystickButton(OI.operator, 4);

        extendButton.whenPressed(new ExtendDrawbridge());
        retractButton.whenPressed(new RetractDrawbridge());

        // SmartDashboard Buttons
        // SmartDashboard.putData("Autonomous Command", new
        // AutonomousCommands());

        SmartDashboard.putData("DriveForward", new DriveForward(0, 0));

        SmartDashboard.putData("DriveContinuous Default", new DriveContinuous(
                1.0));

        SmartDashboard.putData("SleepCommand", new SleepCommand(0.0));

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public static Joystick getoperator() {
        return operator;
    }

    public static Joystick getdriver() {
        return driver;
    }

    public static double getDriverLY() {
        return (deadzone(driver.getRawAxis(1), 0.2));
    }

    public static double getDriverRY() {
        return (deadzone(driver.getRawAxis(3), 0.2));
    }

    public static double getOperatorLY() {
        return (deadzone(operator.getRawAxis(1), 0.2));
    }

    public static double getOperatorRY() {
        return (deadzone(operator.getRawAxis(3), 0.2));
    }

    private static double deadzone(double value, double deadzone) {
        double modifiedValue = value;

        if ((value <= deadzone) && (value >= -deadzone)) {
            modifiedValue = 0;
        }

        return (modifiedValue);
    }

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

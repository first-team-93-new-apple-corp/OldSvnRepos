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

import org.usfirst.frc93.Team93Robot2015.commands.BridgeDownFixRake;
import org.usfirst.frc93.Team93Robot2015.commands.BridgeUpFixRake;
import org.usfirst.frc93.Team93Robot2015.commands.DriveContinuous;
import org.usfirst.frc93.Team93Robot2015.commands.DriveForward;
import org.usfirst.frc93.Team93Robot2015.commands.ElevatorControlContinuous;
import org.usfirst.frc93.Team93Robot2015.commands.ManualInterrupt;
import org.usfirst.frc93.Team93Robot2015.commands.MoveBackwards;
import org.usfirst.frc93.Team93Robot2015.commands.ObjectGrabber;
import org.usfirst.frc93.Team93Robot2015.commands.RakeTurnCommandGroup;
import org.usfirst.frc93.Team93Robot2015.commands.RotateContainer;
import org.usfirst.frc93.Team93Robot2015.commands.TransitionGear;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    protected static Joystick operator;
    protected static Joystick driver;

    public OI() {
        driver = new Joystick(1);
        operator = new Joystick(2);

        // --DRIVER--BUTTONS------------------------------------------------------------------
        Button slowButton = new JoystickButton(driver, 5);
        Button fastButton = new JoystickButton(driver, 6);
        Button runDriveForwards = new JoystickButton(driver, 2);
        Button theMorePlan = new JoystickButton(driver, 4);

        // --OPERATOR--BUTTONS----------------------------------------------------------------
        Button quarterRotate = new JoystickButton(operator, 1);
        Button fullRotate = new JoystickButton(operator, 4);
        Button grabButton = new JoystickButton(operator, 3);
        Button resetRotate = new JoystickButton(operator, 2);
        Button elevatorManualControl = new JoystickButton(operator, 5);
        Button toggleDrawbridge = new JoystickButton(operator, 6);
        Button moveRake = new JoystickButton(operator, 7);
        Button dropRake = new JoystickButton(operator, 10);
        Button manualRaking = new JoystickButton(operator, 8);
        Button grabberInterrupt = new JoystickButton(operator, 12);

        // --BUTTON--FUNCTIONS----------------------------------------------------------------
        runDriveForwards.whileHeld(new MoveBackwards());
        runDriveForwards.whenReleased(new ManualInterrupt(Robot.drive));
        slowButton.whenPressed(new DriveContinuous(0.4));
        fastButton.whenPressed(new TransitionGear(0.4, 1.0, 1000));
        theMorePlan.whenPressed(new DriveForward(600.0, 10.0));

        elevatorManualControl.whileHeld(new ElevatorControlContinuous());
        toggleDrawbridge.whenPressed(new BridgeUpFixRake());
        quarterRotate.whenPressed(new RotateContainer(-180));
        fullRotate.whenPressed(new RotateContainer(85));
        resetRotate.whenPressed(new RotateContainer(-90.0));
        grabButton.whenPressed(new ObjectGrabber());
        moveRake.whenPressed(new RakeTurnCommandGroup());
        dropRake.whenPressed(new BridgeDownFixRake());
        grabberInterrupt.whenPressed(new ManualInterrupt(Robot.grabber));

        manualRaking.whenPressed(new RakeTurnCommandGroup());

        // SmartDashboard Buttons
        // SmartDashboard.putData("Autonomous Command", new
        // AutonomousCommands());

    }

    /**
     * Gets the Operator joystick object.
     * 
     * @return the operator joystick.
     */
    public static Joystick getOperator() {
        return operator;
    }

    /**
     * Gets the Driver joystick object.
     * 
     * @return the driver joystick.
     */
    public static Joystick getDriver() {
        return driver;
    }

    /**
     * Gets the value of Driver joystick's left y axis.
     * 
     * @return the value of Driver's left y axis.
     */
    public static double getDriverLY(double deadzone) {
        return (deadzone(driver.getRawAxis(1), deadzone));
    }

    /**
     * Gets the value of Driver joystick's right y axis.
     * 
     * @return the value of Driver's right y axis.
     */
    public static double getDriverRY(double deadzone) {
        return -1.0 * (deadzone(driver.getRawAxis(3), deadzone));
    }

    /**
     * Gets the value of Operator joystick's left y axis.
     * 
     * @return the value of Operator's left y axis.
     */
    public static double getOperatorLY(double deadzone) {
        return (-1.0 * deadzone(operator.getRawAxis(1), deadzone));
    }

    /**
     * Gets the value of Operator joystick's right y axis.
     * 
     * @return the value of Operator's right y axis.
     */
    public static double getOperatorRY(double deadzone) {
        return (deadzone(operator.getRawAxis(3), deadzone));
    }

    /**
     * Returns a value with an absolute value less than the passed deadzone to
     * 0.
     * 
     * @param value
     *            The value to deadzone
     * @param deadzone
     *            The deadzone size
     * @return the modified value
     */
    private static double deadzone(double value, double deadzone) {
        double modifiedValue = value;

        if ((value <= deadzone) && (value >= -deadzone)) {
            modifiedValue = 0;
        }

        return (modifiedValue);
    }
}
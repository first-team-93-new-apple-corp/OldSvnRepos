package nacteam93.robot2014;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import nacteam93.robot2014.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    
    public static Joystick operator;
    public static Joystick driver;
    
    public static Button aimFar;
    public static Button aimNear;
    public static Button fireShooter;
    public static Button retractShooter;
    public static Button intakeBall;
    public static Button outputBall;
    public static Button centerBallAndAim0;
    public static Button brakeArm;
    public static Button brakeArmRelease;
            
    public static Button shiftDriveOut;
    public static Button shiftDriveIn;
    public static Button AutoTurnLeft45;
    public static Button AutoTurnRight45;
    public static Button manualInterrupt;
    public static Button driveForwards5000;
    
    public final static int DRIVER_JOYSTICK_AXIS_LY = 2;
    public final static int DRIVER_JOYSTICK_AXIS_RY = 4;    
    public final static int OPERATOR_JOYSTICK_AXIS_LY = 4;
    
    public OI() {
        operator = new Joystick(1);
        driver = new Joystick(2);
        
        centerBallAndAim0 = new JoystickButton(operator, 1);//allows the use of bottom button for output ball
                                                           //press along with button 6
            centerBallAndAim0.whenPressed(new aimWithBrakeGroup(0,2));
        aimFar = new JoystickButton(operator, 3);
            aimFar.whenPressed(new aimWithBrakeGroup(50, 2));
        aimNear = new JoystickButton(operator, 2);
            aimNear.whenPressed(new aimWithBrakeGroup(39, 2));
        manualInterrupt = new JoystickButton(operator, 4);
            /*
             * manualInterrupt is used to interrupt the FireShooter command, 
             * all retraction commands and 
             * intakeBall command
             */
        intakeBall = new JoystickButton(operator, 5);
            intakeBall.whenPressed(new IntakeBall());
            SmartDashboard.putData("IntakeBall", new IntakeBall());
        outputBall = new JoystickButton(operator, 6);
            outputBall.whileHeld(new OutputBall());
        /*
         * ALWAYS call retractionGroup with +1 or -1, depending on winch wiring.         * 
         */                
        fireShooter = new JoystickButton(operator, 8);
            fireShooter.whenPressed(new ShootAndRetractGroup());
            SmartDashboard.putData("FireShooter", new ShootAndRetractGroup());
        
        shiftDriveOut = new JoystickButton(driver, 5);
            shiftDriveOut.whenPressed(new shiftDrive(false));
            SmartDashboard.putData("shiftDriveOut", new shiftDrive(false));       
        shiftDriveIn = new JoystickButton(driver, 6);
            shiftDriveIn.whenPressed(new shiftDrive(true));
            SmartDashboard.putData("shiftDriveIn", new shiftDrive(true));

    }
}
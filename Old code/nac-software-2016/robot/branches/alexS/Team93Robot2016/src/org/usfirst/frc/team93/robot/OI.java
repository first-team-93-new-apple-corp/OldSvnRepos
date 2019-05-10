package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team93.robot.commands.ExampleCommand;
import org.usfirst.frc.team93.robot.commands.MoveArmSlider;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{

	protected static Joystick driver;
	protected static Joystick operator;
	
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
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
	
	public static Button armButtonOne;
	public static Button armButtonTwo;
	public static Button armButtonThree;
	public static Button armButtonFour;
	
	public OI()
	{
		driver = new Joystick(0);
		operator = new Joystick(1);
		
		//Driver Buttons

		
		armButtonOne = new JoystickButton(driver, 1);
		armButtonTwo = new JoystickButton(driver, 2);
		armButtonThree = new JoystickButton(driver, 3);
		armButtonFour = new JoystickButton(driver, 4);
		armButtonOne.whenPressed(new MoveArmSlider(10, 1));
		armButtonTwo.whenPressed(new MoveArmSlider(-10, 1));
		armButtonThree.whenPressed(new MoveArmSlider(20, 1));
		armButtonFour.whenPressed(new MoveArmSlider(-20, 1));
		
		//Operator Buttons
		
		//Button Functions
	}
	
	public static Joystick getDriver()
	{
		return driver;
	}
	
	public static Joystick getOperator()
	{
		return driver;
	}
	
	/*
	 * fix do our liking, here as a place holder for Drive Continuous stuff
	 */
	public static double getDriverLY(double deadzone) 
	{
		return (deadzone(driver.getRawAxis(1),deadzone));
	}

	
	/*
	 * fix do our liking, here as a place holder for Drive Continuous stuff
	 */
	public static double getDriverRY(double deadzone) 
	{
		return (deadzone(driver.getRawAxis(3),deadzone));
	}
	
	public static double getOperatorLY(double deadzone)
	{
		return (deadzone(operator.getRawAxis(1),deadzone));
	}
	
	public static double getOperatorRY(double deadzone)
	{
		return (deadzone(operator.getRawAxis(3),deadzone));
	}
	
	public static double deadzone(double value,double deadzone)
	{
		double modifiedValue = value;
		
		if((value <= deadzone) && (value >= 0)){
			modifiedValue = 0;
		}
		
		return modifiedValue;
	}
}


package org.usfirst.frc.team93.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team93.robot.commands.BallAcquisition;
import org.usfirst.frc.team93.robot.commands.ExampleCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
    public static Joystick operator;
    public static Button ball_acquisition;
    
    public OI()
    {
    operator = new Joystick(1); //This is the operator's controller
    ball_acquisition = new JoystickButton(operator, 1); //This button is linked to the ball acquisition command
    
    ball_acquisition.whenPressed(new BallAcquisition(1)); //When the button is pressed, the ball acquisition command runs
    }
}


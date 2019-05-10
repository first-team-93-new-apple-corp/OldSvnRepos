package org.usfirst.frc.team93.robot.commands;

//import com.sun.glass.ui.Robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc.team93.robot.subsystems.Camera;
import org.usfirst.frc.team93.robot.Robot;

/**
 *
 */
public class CameraSwitcher extends Command {
	
    public CameraSwitcher() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        
        //Button switchCamera;
        //switchCamera.whenPressed(Camera.camera = Camera.camera * -1);
    }

    // Called once when the command executes
    protected void initialize() {
        Robot.hasCameraSwitched = false;
    }

    protected void execute() {
    	Camera.camera = Camera.camera * -1;
        Robot.hasCameraSwitched = true;
    }
    
	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return Robot.hasCameraSwitched;
	}

}

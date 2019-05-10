package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

/**
 * New Camera that uses less synchronized threads than the original
 */
public class NewTeleOpCamera extends Command {

    protected int session;
    protected Image frame;
    protected int length;
    protected int imageHeight = 0;
    protected int imageWidth = 0;
    protected boolean m_keepRunning = true;
    TeleopCamera m_teleOperatedCamera;
    protected int requestedFPS = 15;

    public NewTeleOpCamera(TeleopCamera teleopCamera) {
        m_teleOperatedCamera = teleopCamera;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name can be found through the roborio web interface
        // session is what the images are sent `to
        session = NIVision.IMAQdxOpenCamera("cam2",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        // declares a new session
        NIVision.IMAQdxConfigureGrab(session);
        // starts the session
        NIVision.IMAQdxStartAcquisition(session);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // 1000 refers to the amount of milli secs in a sec
        int m_transferTimeInMillis = 1000 / requestedFPS;
        NIVision.IMAQdxGrab(session, frame, m_transferTimeInMillis);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        NIVision.IMAQdxStopAcquisition(session);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}

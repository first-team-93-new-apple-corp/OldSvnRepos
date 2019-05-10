package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;

public class TeleopCam extends Command
{
/*
 * This is the program that uses the camera to recongnize shapes of specific sizes.
 *  @author Admin93 
 */
	public TeleopCam() 
	{

    }
    int session;
    Image frame;
    NIVision.Rect rect = new NIVision.Rect(240, 160, 100, 100);
    NIVision.Rect circ = new NIVision.Rect(240, 310, 100, 100);
    
	@Override
	protected void initialize() 
	{
		// TODO Auto-generated method stub
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		// cam0 may not be the correct name of the camera
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        NIVision.IMAQdxStartAcquisition(session);
	}

	@Override
	protected void execute() 
	{
		// TODO Auto-generated method stub
		NIVision.IMAQdxGrab(session, frame, 1000/15);
        NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0.0f);
        NIVision.imaqDrawShapeOnImage(frame, frame, circ,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
        
        CameraServer.getInstance().setImage(frame);
	}

	@Override
	protected boolean isFinished() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() 
	{
		// TODO Auto-generated method stub
        NIVision.IMAQdxStopAcquisition(session);
	}

	@Override
	protected void interrupted() 
	{
		// TODO Auto-generated method stub
		
	}

}
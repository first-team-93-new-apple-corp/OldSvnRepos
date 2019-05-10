/**
 * 
 */
package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

/**
 * @author NAC Controls
 *
 * @codereview ColbyMcKinley: In this area you should write a brief description
 *             of the command, within the comments. Also remove the TODO
 *             annotation with what you are trying to accomplish, once it is
 *             done
 */
public class VisionProcessor extends Command {

    int session;
    Image frame;
    NIVision.Rect rect;

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#initialize()
     */
    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web
        // interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        rect = new NIVision.Rect(10, 10, 100, 100);
    }

    public void operatorControl() {
        NIVision.IMAQdxStartAcquisition(session);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */

    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#execute()
     */
    @Override
    protected void execute() {
        // TODO Auto-generated method stub

        NIVision.IMAQdxGrab(session, frame, 1);
        NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE,
                ShapeMode.SHAPE_OVAL, 0.0f);

        CameraServer.getInstance().setImage(frame);

        /** robot code here! **/
        Timer.delay(0.005); // wait for a motor update time

    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#isFinished()
     */
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#end()
     */
    @Override
    protected void end() {
        // TODO Auto-generated method stub
        NIVision.IMAQdxStopAcquisition(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#interrupted()
     */
    @Override
    protected void interrupted() {
        // TODO Auto-generated method stub

    }
}

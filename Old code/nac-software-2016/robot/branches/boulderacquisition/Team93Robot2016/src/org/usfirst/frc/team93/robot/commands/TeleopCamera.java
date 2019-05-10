package  org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

/**
 * This Class is for the camera in tele op. It is suppose to take up less
 * bandwidth than VisionProcessor.java, since it does not use a camera in the
 * autonomous period.
 * 
 * @author NAC Controls
 *
 */
public class TeleopCamera extends Command 
{

    int session;
    Image frame;
    int length;
    protected int imageHeight = 0;
    protected int imageWidth = 0;
    boolean m_keepRunning = true;

    public TeleopCamera() 
    {

    }

    public class RunnableVisionProcessor implements Runnable 
    {

        TeleopCamera m_teleOperatedCamera;

        /**
         * Initializes the class
         * 
         * @param teleopCamera
         *            the camera you wish to use
         */
        RunnableVisionProcessor(TeleopCamera teleopCamera) 
        {
            m_teleOperatedCamera = teleopCamera;
        }

        /**
         * runs the thread
         */
        @Override
        public void run()
        {
            m_teleOperatedCamera.threadRun();

        }
    }

    /**
     * threadRun acts as the execute function of the class, it sends the image
     * to the driver station until the TeleopCamera class ends
     */
    protected void threadRun() 
    {
        while (m_keepRunning) 
        {

            synchronized (this) 
            {
                // captures a new image
                int requestedFPS = 15;
                // 1000 refers to the amount of milli secs in a sec
                int m_transferTimeInMillis = 1000 / requestedFPS;
                NIVision.IMAQdxGrab(session, frame, m_transferTimeInMillis);

                // gets the size of the image and its properties.
                NIVision.GetImageSizeResult size = NIVision
                        .imaqGetImageSize(frame);
                imageWidth = size.width;
                imageHeight = size.height;
            }
            System.out.flush();

            // sends image to driver station
            synchronized (this) 
            {
                CameraServer.getInstance().setImage(frame);
            }
        }
    }

    /*
     * This function initializes the camera and a thread
     * 
     * @Override(non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#initialize()
     */
    @Override
    synchronized protected void initialize() 
    {
        (new Thread(new RunnableVisionProcessor(this))).start();
        // frame is the picture that is showing at the time
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name can be found through the roborio web interface
        // session is what the images are sent to
        session = NIVision.IMAQdxOpenCamera("cam2",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);

        NIVision.IMAQdxSetAttributeString(session,
                "AcquisitionAttributes::VideoMode", "320 x 240 YUY 2 30.00 fps");

        // declares a new session
        NIVision.IMAQdxConfigureGrab(session);

        // starts the session
        NIVision.IMAQdxStartAcquisition(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#execute()
     */
    @Override
    protected void execute() 
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#isFinished()
     */
    @Override
    protected boolean isFinished() 
    {
        return false;
    }

    /*
     * This function ends the feedback to the driver station (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#end()
     */
    @Override
    synchronized protected void end() 
    {
        // TODO Auto-generated method stub
        m_keepRunning = false;
        NIVision.IMAQdxStopAcquisition(session);
    }

    /**
     * 
     * @see edu.wpi.first.wpilibj.command.Command#interrupted()
     */
    @Override
    protected void interrupted() 
    {
    	
    }
}
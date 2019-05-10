package application;

import java.io.ByteArrayInputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FX_Controller {
	// FXML buttons
    @FXML
    private Button cameraButton;
    // the FXML area for showing the current frame
    @FXML
    private ImageView originalFrame;
    // checkbox for enabling/disabling Canny
    @FXML
    private CheckBox canny;
    // canny threshold value
    @FXML
    private Slider threshold;
    // checkbox for enabling/disabling background removal
    @FXML
    private CheckBox dilateErode;
    // inverse the threshold value for background removal
    @FXML
    private CheckBox inverse;

    // a timer for acquiring the video stream
    private Timer timer;
    // the OpenCV object that performs the video capture
    private VideoCapture capture;
    // a flag to change the button behavior
    private boolean cameraActive;
    
    
    private Image CamStream;

    /**
     * Init the controller variables
     */
    protected void init()
    {
            this.capture = new VideoCapture();
    }

    /**
     * The action triggered by pushing the button on the GUI
     */
    @FXML
    protected void startCamera()
    {
            if (!this.cameraActive)
            {
            	if (this.canny.isSelected()){
            	    frame = this.doCanny(frame);
            	}
                    // start the video capture
                    this.capture.open(0);
                    
                    // is the video stream available?
                    if (this.capture.isOpened())
                    {
                            this.cameraActive = true;

                            // grab a frame every 33 ms (30 frames/sec)
                            TimerTask frameGrabber = new TimerTask() {
                                    @Override
                                    public void run()
                                    {
                                            CamStream = grabFrame();
                                            Platform.runLater(new Runnable() {
                                                    @Override
                                        public void run() {

                                                            // show the original frames
                                                            originalFrame.setImage(CamStream);
                                                            // set fixed width
                                                            originalFrame.setFitWidth(600);
                                                            // preserve image ratio
                                                            originalFrame.setPreserveRatio(true);

                                            }
                                                    });
                                    }
                            };
                            this.timer = new Timer();
                            this.timer.schedule(frameGrabber, 0, 33);

                            // update the button content
                            this.cameraButton.setText("Stop Camera");
                    }
                    else
                    {
                            // log the error
                            System.err.println("Failed to open the camera connection...");
                    }
            }
            else
            {
                    // the camera is not active at this point
                    this.cameraActive = false;
                    // update again the button content
                    this.cameraButton.setText("Start Camera");

                    // stop the timer
                    if (this.timer != null)
                    {
                            this.timer.cancel();
                            this.timer = null;
                    }
                    // release the camera
                    this.capture.release();
                    // clean the image area
                    originalFrame.setImage(null);
            }
    }

    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Image} to show
     */
    private Image grabFrame()
    {
            // init everything
            Image imageToShow = null;
            Mat frame = new Mat();

            // check if the capture is open
            if (this.capture.isOpened())
            {
                    try
                    {
                            // read the current frame
                            this.capture.read(frame);

                            // if the frame is not empty, process it
                            if (!frame.empty())
                            {
                            	// handle edge detection
                                if (this.canny.isSelected())
                                {
                                        frame = this.doCanny(frame);
                                }
                                // foreground detection
                                else if (this.dilateErode.isSelected())
                                {
                                        frame = this.doBackgroundRemoval(frame);
                                }

                                // convert the Mat object (OpenCV) to Image (JavaFX)
                                imageToShow = mat2Image(frame);
                            }

                    }
                    catch (Exception e)
                    {
                            // log the (full) error
                            System.err.print("ERROR");
                            e.printStackTrace();
                    }
            }

            return imageToShow;
    }
    
    /**
     * Perform face detection and show a rectangle around the detected face.
     *
     * @param frame
     *            the current frame
     */
    private void detectAndDisplay(Mat frame)
    {
            // init
            MatOfRect faces = new MatOfRect();
            Mat grayFrame = new Mat();

            // convert the frame in gray scale
            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
            // equalize the frame histogram to improve the result
            Imgproc.equalizeHist(grayFrame, grayFrame);

    }

    /**
     * Convert a Mat object (OpenCV) in the corresponding Image for JavaFX
     *
     * @param frame
     *            the {@link Mat} representing the current frame
     * @return the {@link Image} to show
     */
    private Image mat2Image(Mat frame)
    {
            // create a temporary buffer
            MatOfByte buffer = new MatOfByte();
            // encode the frame in the buffer, according to the PNG format
            Highgui.imencode(".png", frame, buffer);
            // build and return an Image created from the image encoded in the
            // buffer
            return new Image(new ByteArrayInputStream(buffer.toArray()));
    }
    @FXML
    protected Mat doCanny(Mat frame)
    {
    	Imgproc.cvtColor(frame, grayImage, Imgproc.COLOR_BGR2GRAY);
    	Imgproc.blur(grayImage, detectedEdges, new Size(3, 3));
    	Imgproc.Canny(detectedEdges, detectedEdges, this.threshold.getValue(), this.threshold.getValue() * 3, 3, false);
    	Mat dest = new Mat();
    	Core.add(dest, Scalar.all(0), dest);
    	frame.copyTo(dest, detectedEdges);
    }
    
    @FXML
    protected void doBackgroundRemoval()
    {
    	
    }
}
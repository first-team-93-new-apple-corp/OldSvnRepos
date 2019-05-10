/**
 *
 */
package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.RawData;

import java.nio.ByteBuffer;

/**
 * This command is for the camera in Autonomous. It takes the images sent back
 * from the camera and looks for a line to follow.
 * 
 * @author NAC Controls
 * @codereivew ColbyMcKinley: As of April 11, 2015 This code detected a line and
 *             drove in a circle.
 */
// DONT TOUCH ANYTHING UNlESS YOU ARE EVANS/GRANT/NICK LUTHER/COLBY
public class VisionProcessor extends Command implements PIDSource {

    int session;
    Image frame;
    int length;
    int pixelIndex;
    int pixelnumber;
    ByteBuffer m_rawData = null;
    protected int imageHeight = 0;
    protected int imageWidth = 0;
    int bytesPerPixel = 4;
    int numberOfPictures = 0;
    int firstWhite = -1;
    int lastWhite = 0;
    double centerOfWhite;
    double value;
    boolean m_keepRunning = true;

    public enum ESide {
        eSide_FarLeft, eSide_Left, eSide_Center, eSide_Right, eSide_FarRight
    }

    public VisionProcessor() {

    }

    public class RunnableVisionProcessor implements Runnable {

        VisionProcessor m_Run;

        RunnableVisionProcessor(VisionProcessor vp) {
            m_Run = vp;
        }

        @Override
        public void run() {
            m_Run.threadRun();

        }
    }

    protected void threadRun() {
        while (m_keepRunning) {
            // TODO Auto-generated method stub

            synchronized (this) {

                // 1000 refers to the amount of milli secs in a sec and 15 is
                // the fps
                int m_transferTimeInMillis = 1000 / 15;
                // captures a new image
                NIVision.IMAQdxGrab(session, frame, m_transferTimeInMillis);

                // gets the size of the image and its properties.
                // NIVision.GetImageSizeResult size = NIVision
                // .imaqGetImageSize(frame);
                // imageWidth = size.width;
                // imageHeight = size.height;
            }
            System.out.flush();

            // Gets the raw data from the Camera.
            // grabRawData();

            // Finds the side with a larger intensity and uses the values to set
            // the motors
            // findSideWithLargerIntensity();

            // Centers the robot camera on the tape.
            // driveToCenterImage();

            // sends image to driver station
            synchronized (this) {
                CameraServer.getInstance().setImage(frame);
            }
        }
    }

    /**
     * Gets the intensity of one pixel.
     *
     * @param column
     *            The column location of the pixel
     * @param row
     *            The row location of the pixel
     * @return The pixel intensity
     */
    synchronized protected int getPixelIntensity(int column, int row) {
        // Finds the pixel index.
        pixelIndex = bytesPerPixel * ((column * row) + column);
        /**
         * @codereview ColbyMcKinley: Do we want to keep this the way it is or
         *             make it simpler
         */
        // converts m_rawData.get(pixelIndex) value into a positive value.
        int positivePixelIntensity = m_rawData.get(pixelIndex) & 0xFF;

        // Threshold for intensity for a pixel to be considered white
        int whitePixelIntensityThreshold = 220;

        // The first white pixel recognized is the first white row
        if ((positivePixelIntensity > whitePixelIntensityThreshold)
                && (firstWhite == -1)) {
            // the first white pixel in the image is at this y value.
            firstWhite = row;
        }

        // Runs after it finds the first white row. Because this function runs
        // every row, it will continuously replace the "last white row" with the
        // next white row it sees. So the final white row it sees will become
        // the
        // "last white row".
        else if (positivePixelIntensity > whitePixelIntensityThreshold) {
            // the last white pixel in the image is at this y value.
            lastWhite = row;
        }

        // Returns the result.
        return positivePixelIntensity;
    }

    public int getPxlIntensity(int x, int y) {
        pixelIndex = bytesPerPixel * ((x * y) + x);
        // converts m_rawData.get(pixelIndex) value into a positive value.
        int positivePixelIntensity = m_rawData.get(pixelIndex) & 0xFF;
        return (positivePixelIntensity);
    }

    public int getAveragePxlIntensity(int skip) {
        int totalPxlIntensity = 0;
        double totalPxlNumber = Math.floor(imageWidth / skip)
                * Math.floor(imageHeight / skip);
        for (int i = 0; i < imageWidth; i += skip) {
            for (int j = 0; j < imageHeight; j += skip) {
                totalPxlIntensity += getPixelIntensity(i, j);
            }
        }
        return ((int) Math.round(totalPxlIntensity / totalPxlNumber));
    }

    public int getMaxPxlIntensity(int skip) {
        int maxPxlIntensity = 0;
        for (int i = 0; i < imageWidth; i += skip) {
            for (int j = 0; j < imageHeight; j += skip) {
                if (getPixelIntensity(i, j) > maxPxlIntensity) {
                    maxPxlIntensity = getPixelIntensity(i, j);
                }
            }
        }
        return (maxPxlIntensity);
    }

    public int getMinPxlIntensity(int skip) {
        int minPxlIntensity = 0;
        for (int i = 0; i < imageWidth; i += skip) {
            for (int j = 0; j < imageHeight; j += skip) {
                if (getPixelIntensity(i, j) < minPxlIntensity) {
                    minPxlIntensity = getPixelIntensity(i, j);
                }
            }
        }
        return (minPxlIntensity);
    }

    public int getFirstWhitePixel(int skip, int threshold) {
        int returnValue = imageHeight / 2;
        for (int i = 0; i < imageWidth; i += skip) {
            for (int j = 0; j < imageHeight; j += skip) {
                if ((getPixelIntensity(i, j) > threshold)
                        && (returnValue == imageHeight / 2)) {
                    returnValue = j;
                }
            }
        }
        return (returnValue);
    }

    public int getLastWhitePixel(int skip, int threshold) {
        int returnValue = imageHeight / 2;
        for (int i = 0; i < imageWidth; i += skip) {
            for (int j = 0; j < imageHeight; j += skip) {
                if (getPixelIntensity(i, j) > threshold) {
                    returnValue = j;
                }
            }
        }
        return (returnValue);
    }

    public int getWhiteCenter() {
        return (Math.round(getFirstWhitePixel(10, 220)
                + getLastWhitePixel(10, 220)));
    }

    /**
     * Finds which side has a larger intensity, the right or left side.
     *
     * @return The enum Eside, eSide_Right or eSide_Left.
     */
    synchronized protected ESide findSideWithLargerIntensity() {

        // The Eside value to return.
        ESide largestSide = null;

        // initializes the integers that store the total intensities of regions.
        int farLeftSideTotalIntensity = 0;
        int leftSideTotalIntensity = 0;
        int centerTotalIntensity = 0;
        int rightSideTotalIntensity = 0;
        int farRightSideTotalIntensity = 0;

        // initializes the variable that stores which region the pixel is in.
        ESide region = ESide.eSide_FarRight;

        // Only processes every few pixels to reduce memory consumption, by
        // skipping pixels. This value is how far the processed pixels
        // should be away from each other.
        int skip = 5;

        // initializes variables used during the for loop blob.
        int pixelX;
        int pixelY;

        // gets the imageHeight and imageWidth above
        int copyOfimageHeight;
        int copyOfimageWidth;
        synchronized (this) {
            copyOfimageHeight = imageHeight;
            copyOfimageWidth = imageWidth;
        }

        // increments through each pixel
        for (pixelX = 1; pixelX < copyOfimageHeight; pixelX += skip) {
            for (pixelY = 1; pixelY < copyOfimageWidth; pixelY += skip) {

                // If the pixel is in the far right 20% of the image,
                if (pixelX <= copyOfimageHeight) {
                    region = ESide.eSide_FarRight;
                }

                // If the pixel is in the between the right 40% and the right
                // 20% of the image,
                else if (pixelX <= copyOfimageHeight * 4 / 5) {
                    region = ESide.eSide_Right;
                }

                // If the pixel is in the between the right 20% and the left
                // 20% of the image,
                else if (pixelX <= copyOfimageHeight * 3 / 5) {
                    region = ESide.eSide_Center;
                }

                // If the pixel is in the between the left 40% and the left
                // 20% of the image,
                else if (pixelX <= copyOfimageHeight * 2 / 5) {
                    region = ESide.eSide_Left;
                }

                // If the pixel is in the far left 20% of the image,
                /*
                 * can this be a direct else statement
                 */
                else if (pixelX <= copyOfimageHeight / 5) {
                    region = ESide.eSide_FarLeft;
                }

                // gets the pixel intensity of each pixel.
                synchronized (this) {

                    // Gets the intensity of the pixel.
                    int pixelIntensity = getPixelIntensity(pixelX, pixelY);

                    // Checks to see which region of the image the pixel is in,
                    // and adds the pixel intensity to that total.
                    switch (region) {

                    // Add the pixel intensity to the Far Left Side Total.
                    case eSide_FarLeft:
                        farLeftSideTotalIntensity += pixelIntensity;
                        break;

                    // Add the pixel intensity to the Left Side Total.
                    case eSide_Left:
                        leftSideTotalIntensity += pixelIntensity;
                        break;

                    // Add the pixel intensity to the Center Side Total.
                    case eSide_Center:
                        centerTotalIntensity += pixelIntensity;
                        break;

                    // Add the pixel intensity to the Right Side Total.
                    case eSide_Right:
                        rightSideTotalIntensity += pixelIntensity;
                        break;

                    // Add the pixel intensity to the Far Right Side Total.
                    case eSide_FarRight:
                        farRightSideTotalIntensity += pixelIntensity;
                        break;

                    // The default case should do nothing.
                    default:
                        break;
                    }
                }
            }
        }
        synchronized (this) {
            // Chooses which ESide, eSide_Left or eSide_Right to return, based
            // on which total intensity is greater.

            // sets the largest intensity to which side is the largest value
            int largestIntensity = findLargestValue(farLeftSideTotalIntensity,
                    leftSideTotalIntensity, centerTotalIntensity,
                    rightSideTotalIntensity, farRightSideTotalIntensity);

            // These if statements determine which side has the largest
            // intensity to set the largest side

            if (largestIntensity == farLeftSideTotalIntensity) {
                // the largest side is the far left side.
                largestSide = ESide.eSide_FarLeft;
            }

            else if (largestIntensity == leftSideTotalIntensity) {
                // the largest side is the left side.
                largestSide = ESide.eSide_Left;
            }

            else if (largestIntensity == centerTotalIntensity) {
                // the largest side is the center.
                largestSide = ESide.eSide_Center;
            }

            else if (largestIntensity == rightSideTotalIntensity) {
                // the largest side is the right side.
                largestSide = ESide.eSide_Right;
            }

            else {
                // the largest side is the far right side.
                largestSide = ESide.eSide_FarRight;
            }

            // Prints out the name of the bigger side, and the last pixel X, Y,
            // and pixel number and index, along with all of the intensities of
            // each region of the image.
            System.out.println(largestSide.name() + " "
                    + "farLeftSideIntensity:" + " " + farLeftSideTotalIntensity
                    + " " + "leftSideIntensity" + " " + leftSideTotalIntensity
                    + " " + "centerIntensity:" + " " + centerTotalIntensity
                    + " " + "rightintensity:" + " " + rightSideTotalIntensity
                    + " " + "farRightSideIntensity:" + " "
                    + farRightSideTotalIntensity);
        }
        System.out.flush();

        // returns the largest side eSide.
        return largestSide;
    }

    synchronized protected void driveToCenterImage() {
        double targetRow;

        // Finds the center region of the white.
        // It does this by finding the difference of the rightmost white region
        // detected and the leftmost white region detected, and averages them.

        // Sets targetRow, which is the center of the image. This is where the
        // robot will attempt to center the image on.
        targetRow = imageHeight / 2.0;

        centerOfWhite = getWhiteCenter();

        // If the center of the tape is below the center of the image
        // gotten from the camera, meaning that the robot is too far forward,
        if (centerOfWhite > targetRow) {

            // Sets the drive motors based on how far away the line is from the
            // center of the camera image.
            // RIGHT MOTOR
            value = ((centerOfWhite - targetRow) / targetRow);
            System.out.println("RightMotor: " + value);
        }

        // If the center of the tape is above the center of the image
        // gotten from the camera, meaning that the robot is too far back,
        else {

            // Sets the drive motors based on how far away the line is from the
            // center of the camera image.
            // LEFT MOTOR
            value = ((centerOfWhite - targetRow) / targetRow);
            System.out.println("LeftMotor: " + value);
        }
    }

    synchronized protected void grabRawData() {
        // gets the raw date.
        RawData myRawData = NIVision.imaqFlatten(frame,
                NIVision.FlattenType.FLATTEN_IMAGE,
                NIVision.CompressionType.COMPRESSION_NONE, 1);
        // sets the private m_rawData raw data to the raw data.
        if (m_rawData != null) {
            m_rawData.clear();
        }
        System.gc();
        m_rawData = myRawData.getBuffer();

    }

    /**
     * @Override(non-Javadoc)
     * @see edu.wpi.first.wpilibj.command.Command#initialize()
     */
    @Override
    synchronized protected void initialize() {
        (new Thread(new RunnableVisionProcessor(this))).start();
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        // frame is the picture that is showing at the time

        // the camera name can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam2",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        // session is what the images are sent to.
        NIVision.IMAQdxSetAttributeString(session,
                "AcquisitionAttributes::VideoMode", "320 x 240 YUY 2 15.00 fps");
        // initializes the session
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
    protected void execute() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#isFinished()
     */
    @Override
    protected boolean isFinished() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.first.wpilibj.command.Command#end()
     */
    @Override
    synchronized protected void end() {
        m_keepRunning = false;
        NIVision.IMAQdxStopAcquisition(session);
    }

    /**
     *
     * @see edu.wpi.first.wpilibj.command.Command#interrupted()
     */
    @Override
    protected void interrupted() {

    }

    /**
     * Chooses which value is larger.
     *
     * @param valueOne
     *            The first value.
     * @param valueTwo
     *            The second value.
     * @param valueThree
     *            The third value.
     * @param valueFour
     *            The fourth value.
     * @param valueFive
     *            The fifth value.
     * @return The largest value.
     */
    public int findLargestValue(int valueOne, int valueTwo, int valueThree,
            int valueFour, int valueFive) {
        return getLargestValue(valueOne, valueTwo, valueThree, valueFour,
                valueFive);
    }

    /**
     * Chooses which value is larger.
     *
     * @param valueOne
     *            The first value.
     * @param valueTwo
     *            The second value.
     * @return The largest value.
     */
    public int getLargestValue(int valueOne, int valueTwo) {
        if (valueOne > valueTwo) {
            return (valueOne);
        }
        else {
            return (valueTwo);
        }
    }

    /**
     * Chooses which value is larger.
     *
     * @param valueOne
     *            The first value.
     * @param valueTwo
     *            The second value.
     * @param valueThree
     *            The third value.
     * @param valueFour
     *            The fourth value.
     * @param valueFive
     *            The fifth value.
     * @return The largest value.
     */
    private int getLargestValue(int valueOne, int valueTwo, int valueThree,
            int valueFour, int valueFive) {
        int largestOfFive = getLargestValue(
                getLargestValue(getLargestValue(valueOne, valueTwo),
                        getLargestValue(valueThree, valueFour)), valueFive);
        return largestOfFive;

    }

    /**
     * (non-Javadoc)
     *
     * @see edu.wpi.first.wpilibj.PIDSource#pidGet()
     */
    @Override
    public double pidGet() {
        // TODO Auto-generated method stub
        double localValue;
        synchronized (this) {
            localValue = value;
            return localValue;
            // system.out.println;
        }
    }
}

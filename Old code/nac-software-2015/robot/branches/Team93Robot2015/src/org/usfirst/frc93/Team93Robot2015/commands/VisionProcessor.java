/**
 * 
 */
package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.RawData;

import java.nio.ByteBuffer;

/**
 * @author NAC Controls
 *
 */
public class VisionProcessor extends Command {

    int session;
    Image frame;
    NIVision.Rect rect;
    NIVision.Rect rect2;
    NIVision.Rect rect3;
    int rect3right;
    int rect3left;
    int length;
    int pixelIndex;
    int pixelnumber;
    ByteBuffer m_rawData;
    int row;
    int column;
    int height = 480;
    int width = 640;
    int bytesPerPixel = 4;

    public enum ESide {
        eSide_Left, eSide_Right
    }

    public VisionProcessor() {

    }

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web
        // interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        NIVision.IMAQdxStartAcquisition(session);

        // start the thread

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

        synchronized (this) {
            NIVision.IMAQdxGrab(session, frame, 1); // capture new image
            int bytesPerPixel = NIVision.imaqGetBytesPerPixel(frame);
            NIVision.GetImageSizeResult size = NIVision.imaqGetImageSize(frame);
            System.out.println("height: " + size.height + " width: "
                    + size.width + " bpp: " + bytesPerPixel);
            System.out.flush();
            grabRawData();
        }

        findSideWithLargerIntensity();

        synchronized (this) {
            CameraServer.getInstance().setImage(frame); // send to driver
                                                        // station
        }

        // process image

        /** robot code here! **/
    }

    protected int getPixelValue(int row, int column) {
        pixelIndex = bytesPerPixel * ((column * row) + column);
        int positiveNum = m_rawData.get(pixelIndex) & 0xFF;
        return positiveNum;
    }

    protected ESide findSideWithLargerIntensity() {
        ESide theBiggerSide;
        int leftSideIntensity = 0;
        int rightSideIntensity = 0;
        int skip = 240;
        int pixelIntensity;
        for (row = 0; row < height; row += skip) {
            for (column = 0; column < width; column += skip) {
                pixelIntensity = getPixelValue(row, column);
                if (column < (width / 2)) {
                    leftSideIntensity += pixelIntensity;
                }
                else {
                    rightSideIntensity += pixelIntensity;
                }
            }
        }
        System.out.println(m_rawData);
        System.out.flush();
        theBiggerSide = (leftSideIntensity > rightSideIntensity) ? ESide.eSide_Left
                : ESide.eSide_Right;
        System.out.println(theBiggerSide.name() + " " + row + " " + column
                + " " + pixelnumber + " " + pixelIndex);
        System.out.flush();
        return theBiggerSide;
    }

    protected void grabRawData() {
        // Alternate approach
        RawData myRawData = NIVision.imaqFlatten(frame,
                NIVision.FlattenType.FLATTEN_IMAGE,
                NIVision.CompressionType.COMPRESSION_NONE, 1);
        m_rawData = myRawData.getBuffer();
    }

    protected void findEdge() {
        // EdgeOptions2 edgeOptions = new EdgeOptions2();
        // edgeOptions.polarity =
        // EdgePolaritySearchMode.SEARCH_FOR_RISING_EDGES;
        // edgeOptions.kernelSize = 21;
        // edgeOptions.width = 1;
        // edgeOptions.minThreshold = 23;
        // edgeOptions.interpolationType = InterpolationMethod.BILINEAR;
        // edgeOptions.columnProcessingMode =
        // ColumnProcessingMode.AVERAGE_COLUMNS;

        // FindEdgeOptions2 findEdgeOptions = new FindEdgeOptions2();
        //
        // StraightEdgeOptions straightEdgeOptions = new StraightEdgeOptions();
        //
        // findEdgeOptions.direction = RakeDirection.BOTTOM_TO_TOP;
        // findEdgeOptions.showSearchArea = 1;
        // findEdgeOptions.showSearchLines = 1;
        // findEdgeOptions.showEdgesFound = 1;
        // findEdgeOptions.showResult = 1;
        // findEdgeOptions.searchAreaColor = NIVision.RGB_GREEN;
        // findEdgeOptions.searchLinesColor = NIVision.RGB_BLUE;
        // findEdgeOptions.searchEdgesColor = NIVision.RGB_YELLOW;
        // findEdgeOptions.resultColor = NIVision.RGB_RED;
        // // findEdgeOptions.overlayGroupName = null;
        // // findEdgeOptions.edgeOptions = edgeOptions;
        // findEdgeOptions.edgeOptions = new EdgeOptions2();
        // findEdgeOptions.edgeOptions.polarity =
        // EdgePolaritySearchMode.SEARCH_FOR_FALLING_EDGES;
        // findEdgeOptions.edgeOptions.kernelSize = 21;
        // findEdgeOptions.edgeOptions.width = 5;
        // findEdgeOptions.edgeOptions.minThreshold = 23;
        // findEdgeOptions.edgeOptions.interpolationType =
        // InterpolationMethod.BILINEAR_FIXED;
        // findEdgeOptions.edgeOptions.columnProcessingMode =
        // ColumnProcessingMode.AVERAGE_COLUMNS;
        //
        // straightEdgeOptions.numLines = 1;
        // straightEdgeOptions.searchMode =
        // StraightEdgeSearchMode.USE_BEST_RAKE_EDGES;//
        // StraightEdgeSearchMode.USE_BEST_RAKE_EDGES;
        // straightEdgeOptions.minScore = 11;
        // straightEdgeOptions.maxScore = 1000;
        // straightEdgeOptions.orientation = 0.0;
        // straightEdgeOptions.angleRange = 45;
        // straightEdgeOptions.angleTolerance = 1;
        // straightEdgeOptions.stepSize = 15;
        // straightEdgeOptions.minSignalToNoiseRatio = 3;
        // straightEdgeOptions.minCoverage = 25;
        // straightEdgeOptions.houghIterations = 5;
        //
        // NIVision.CoordinateSystem newSys = new NIVision.CoordinateSystem(
        // ByteBuffer.rv_buf, 8);
        //
        // NIVision.CoordinateSystem baseSys = new NIVision.CoordinateSystem(
        // ByteBuffer.rv_buf, 0);
        // ROI roi = NIVision.imaqCreateROI();
        // RotatedRect ROIrect4 = new RotatedRect(5, 2, 237, 318, 0); // 320,
        // 242
        // NIVision.imaqAddRotatedRectContour2(roi, ROIrect4);
        // FindEdgeReport edges;
        // System.out.println("Start: " + Timer.getFPGATimestamp());
        //
        //
        // NIVision.FindEdgeReport edges2 = NIVision.imaqFindEdge2(frame, roi,
        // baseSys, newSys, findEdgeOptions, straightEdgeOptions);
        //
        // long jn_rv = _imaqFindEdge2(frame.getAddress(), roi.getAddress(), 0,
        // 0,
        // findEdgeOptions.getAddress(), straightEdgeOptions.getAddress());
        // FindEdgeReport edges3 = new FindEdgeReport(jn_rv, true);
        //
        // // NIVision.imaqDetectLines(image, lineDescriptor, curveOptions,
        // // shapeDetectionOptions, roi)
        //
        // float startx = 1;
        // float starty = 1;
        // float endx = 2;
        // float endy = 2;
        //
        // if (edges.straightEdges.length >= 1) {
        // startx = edges.straightEdges[0].straightEdgeCoordinates.start.x;
        // starty = edges.straightEdges[0].straightEdgeCoordinates.start.y;
        // endx = edges.straightEdges[0].straightEdgeCoordinates.end.x;
        // endy = edges.straightEdges[0].straightEdgeCoordinates.end.y;
        //
        // System.out.println("Line detected " + Float.toString(startx) + ", "
        // + Float.toString(starty) + ", " + Float.toString(endx)
        // + ", " + Float.toString(endy));
        // }

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

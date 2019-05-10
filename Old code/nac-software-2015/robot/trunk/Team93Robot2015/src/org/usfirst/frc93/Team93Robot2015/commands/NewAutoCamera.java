package org.usfirst.frc93.Team93Robot2015.commands;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.RawData;
import com.ni.vision.NIVision.ShapeMode;

import java.nio.ByteBuffer;

// DONT TOUCH ANYTHING UNlESS YOU ARE GRANT/NICK LUTHER/COLBY
public class NewAutoCamera extends Command implements PIDSource {

    int session;
    Image frame;
    int pixelIndex;
    int pixelnumber;
    ByteBuffer m_rawData = null;
    int imageWidth;
    int imageHeight;
    int[] firstWhites = new int[(imageWidth / 4)];
    int[] LastWhites = new int[(imageWidth / 4)];
    int[] WhiteLineSlope = new int[(imageWidth / 4) - 1];
    int[] allTapeCenter = new int[imageWidth / 4];
    int bytesPerPixel = 4;
    int firstWhite;
    int lastWhite;
    double AverageCenter;
    boolean foundWhite;
    boolean stillWhite;
    double centerOfWhite;
    double value;
    double AverageSlope = 0;
    boolean run = false;
    double TapeCenter;
    boolean RepeatWhite = false;

    public NewAutoCamera() {

    }

    protected double findSlope() {
        AverageSlope = 0;
        for (int i = 0; i < imageHeight; i += 4) {
            for (int j = 0; j < imageWidth; j += 4) {
                if (FindFirstWhites(j, i)) {
                    firstWhites[i / 4] = j;
                    foundWhite = false;
                }
            }
        }
        for (int i = 0; i < firstWhites.length; i++) {
            WhiteLineSlope[i] = firstWhites[i + 1] - firstWhites[i];
            AverageSlope += WhiteLineSlope[i];
        }
        AverageSlope = AverageSlope / WhiteLineSlope.length;
        System.out.println("Slope: " + AverageSlope);
        return AverageSlope;
    }

    protected int getPixelIntensity(int column, int row) {
        // Finds the pixel index.
        pixelIndex = bytesPerPixel * ((column * row) + column);
        // converts m_rawData.get(pixelIndex) value into a positive value.
        int positivePixelIntensity = m_rawData.get(pixelIndex) & 0xFF;
        // Returns the result.
        return positivePixelIntensity;
    }

    protected boolean FindFirstWhites(int column, int row) {
        // Finds the pixel index.
        pixelIndex = bytesPerPixel * ((column * row) + column);
        // converts m_rawData.get(pixelIndex) value into a positive value.
        int positivePixelIntensity = m_rawData.get(pixelIndex) & 0xFF;
        // Threshold for intensity for a pixel to be considered white
        int whitePixelIntensityThreshold = 220;
        if (positivePixelIntensity >= whitePixelIntensityThreshold) {
            foundWhite = true;
        }
        // Returns the result.
        return foundWhite;
    }

    protected double findTapeCenter() {
        AverageCenter = 0;
        TapeCenter = 0;
        RepeatWhite = false;
        for (int i = 0; i < imageHeight; i += 4) {
            for (int j = 0; j < imageWidth; j += 4) {
                if (!RepeatWhite)
                    if (FindFirstWhites(j, i)) {
                        firstWhites[i / 4] = j;
                        RepeatWhite = true;
                    }
                if (RepeatWhite && !foundWhite) {
                    LastWhites[i / 4] = j;
                    RepeatWhite = false;
                }
                RepeatWhite = false;
            }
            NIVision.Rect rect = new NIVision.Rect(i, firstWhites[i / 4], 4,
                    LastWhites[i / 4] - firstWhites[i / 4]);
            NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                    DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0.0f);
        }
        for (int i = 0; i < firstWhites.length; i++) {
            allTapeCenter[i] = (LastWhites[i] + firstWhites[i]) / 2;
            AverageCenter += allTapeCenter[i];
        }
        AverageCenter = AverageCenter / allTapeCenter.length;
        System.out.println("Center of Line: " + AverageCenter);
        return AverageCenter;
    }

    protected void fixPosition() {
        double TapeCenter = findTapeCenter();
        double Slope = findSlope();
        while (TapeCenter < imageWidth - 50) {
            // TODO Fix the position
        }
        while (TapeCenter > imageWidth + 50) {
            // TODO Fix the position
        }
        while (Slope > .1) {
            // TODO Fix the slope
        }
        while (Slope < .1) {
            // TODO Fix the slope
        }
    }

    protected void grabRawData() {
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

    @Override
    public double pidGet() {
        // TODO Auto-generated method stub
        double localValue;
        localValue = value;
        return localValue;
    }

    @Override
    synchronized protected void initialize() {
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        // frame is the picture that is showing at the time

        // the camera name can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam1",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        // initializes the session
        NIVision.IMAQdxConfigureGrab(session);
        // starts the session
        NIVision.IMAQdxStartAcquisition(session);
        NIVision.GetImageSizeResult size = NIVision.imaqGetImageSize(frame);
        imageHeight = size.height;
        imageWidth = size.width;
        run = true;
    }

    @Override
    protected void execute() {
        // 1000 refers to the amount of milli secs in a sec and 15 is
        // the fps
        int m_transferTimeInMillis = 1000 / 15;
        // captures a new image
        NIVision.IMAQdxGrab(session, frame, m_transferTimeInMillis);
        CameraServer.getInstance().setImage(frame); // Send this frame to the
                                                    // driver station /
                                                    // dashboard
        fixPosition();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    synchronized protected void end() {
        NIVision.IMAQdxStopAcquisition(session);
        run = false;
    }

    @Override
    protected void interrupted() {

    }
}

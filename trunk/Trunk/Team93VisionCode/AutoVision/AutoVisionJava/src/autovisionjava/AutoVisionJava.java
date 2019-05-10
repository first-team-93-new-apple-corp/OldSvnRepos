/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autovisionjava;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs; // imread, imwrite, etc
import org.opencv.videoio.VideoCapture;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.ArrayList;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author jon
 */
public class AutoVisionJava {

    /**
     * @param args the command line arguments
     */
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new AutoVisionJava().run();
        
        
        
    }
    
    
    public void run() {
    VideoCapture capture = new VideoCapture(0);
    Mat source0 = new Mat();
    capture.read(source0);
    Gfilter filter = new Gfilter();
    filter.process(source0);
   // MatOfPoint tape =new ;
   // Rect rect = Imgproc.boundingRect();
   ArrayList<MatOfPoint> contours =  filter.filterContoursOutput();
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("datatable");
    NetworkTableEntry xEntry = table.getEntry("x");
    NetworkTableEntry yEntry = table.getEntry("y");
    inst.startClientTeam(93);  // where TEAM=190, 294, etc, or use inst.startClient("hostname") or similar
      // recommended if running on DS computer; this gets the robot IP from the DS
      
    while (capture.isOpened()){
         
        
        
        
    }
    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        System.out.println("interrupted");
        return;
      }
      double x = xEntry.getDouble(0.0);
      double y = yEntry.getDouble(0.0);
      System.out.println("X: " + x + " Y: " + y);
    }
  }
    
}

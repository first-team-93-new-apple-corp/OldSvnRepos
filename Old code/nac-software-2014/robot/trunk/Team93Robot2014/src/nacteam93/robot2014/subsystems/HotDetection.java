///*----------------------------------------------------------------------------*/
///* Copyright (c) FIRST 2008. All Rights Reserved.                             */
///* Open Source Software - may be modified and shared by FRC teams. The code   */
///* must be accompanied by the FIRST BSD license file in the root directory of */
///* the project.                                                               */
///*----------------------------------------------------------------------------*/
//
//package nacteam93.robot2014.subsystems;
//
//import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.camera.AxisCamera;
//import edu.wpi.first.wpilibj.camera.AxisCameraException;
//import edu.wpi.first.wpilibj.image.*;
//import nacteam93.robot2014.commands.*;
//import nacteam93.robot2014.*;
//
///**
// * The VM is configured to automatically run this class, and to call the
// * functions corresponding to each mode, as described in the IterativeRobot
// * documentation. If you change the name of this class or the package after
// * creating this project, you must also update the manifest file in the resource
// * directory.
// */
//
//public class HotDetection extends Subsystem {
//    
//    public HotDetection()
//    {
//        cc = new CriteriaCollection();
//        cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA, AREA_MINIMUM, 65535, false);  //Area criteria for determining an appropriately sized particle.
//    }
//    /**
//     * This function is run when the robot is first started up and should be
//     * used for any initialization code.
//     */
//    
//    //Minimum required area for the filter to recognize the zone as a particle.
//    final static int AREA_MINIMUM = 150;
//    
//    //Determines whether or not the Hot Zone was detected early into detectHotZone or not.
//    public boolean isHot;
//    CriteriaCollection cc;  //Criteria for filtering out small particles
//    
//
//    /**
//     * This function is called periodically during detectHotZone
//     */
//    public void detectHotZone(){        
//            try
//            {
//                AxisCamera camera;  //Creates an object for the camera.
//                camera = AxisCamera.getInstance();
//                
//                ColorImage imageUnfiltered = camera.getImage();  //Acquires an unfiltered image from the camera.
//                    imageUnfiltered.write("/images/imageUnfiltered.bmp");
//                MonoImage imageBW = imageUnfiltered.getGreenPlane();  //Converts the unfilterd image to a black and white image where the greens are bright white.
//                    imageBW.write("/images/imageBW.bmp");
//                ColorImage imageColor = imageUnfiltered.replaceRedPlane(imageBW);  //Converts the black and white image back to color, while keeping what was green white.
//                    imageColor.write("/images/imageColor.bmp");
//                BinaryImage imageThreshold = imageColor.thresholdRGB(230, 255, 230, 255, 230, 255);  //Looks for a white zone to detect; the "Hot Zone."
//                    imageThreshold.write("/images/imageThreshold.bmp");
//                BinaryImage imageFiltered = imageThreshold.particleFilter(cc);  //Filters out the small particles.
//                    imageFiltered.write("/images/imageFiltered.bmp");
//                    
//                System.out.println("Particles:" + imageFiltered.getNumberParticles());
//                if(imageFiltered.getNumberParticles() >= 2) //Detect if there are 2 or more Zones detected.
//                {
//                    isHot = true;
//                    System.out.println("Hot Zone Detected");                   
//                } //End getNumberParticles if statement.
//                else //This will print if no Hot Zones are detected.
//                {
//                    isHot = false;
//                    System.out.println("No Hot Zones Detected");
//                } //End else clause.
//        
//        
//            //Frees the image data so that it does not build up after each pass through the loop.
//                imageUnfiltered.free();
//                imageBW.free();
//                imageColor.free();
//                imageThreshold.free();
//                imageFiltered.free();
//            } //End try loop.
//            catch (AxisCameraException ex) { 
//                ex.printStackTrace();
//            } //End catch.
//            catch (NIVisionException ex) {
//                ex.printStackTrace();
//            } //End catch.      
//    } // End detectHotZone loop.
//    protected void initDefaultCommand() {
//    }
//}
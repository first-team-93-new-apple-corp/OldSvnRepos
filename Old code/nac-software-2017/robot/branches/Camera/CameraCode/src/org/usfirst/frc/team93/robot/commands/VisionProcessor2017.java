package org.usfirst.frc.team93.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.*;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *  This is the VisionProcessor2017 class.
 *  This class' goal is to recognize the retro-reflective tape on the air ship and then gives outputs to position the robot to drive straight and put on a gear.
 *  @author Colby McKinley
 */
public class VisionProcessor2017 extends Command{
	
	private String CameraName = "cam0";  //pretty sure these should be private, think of possible instances this should not be
	private final int portNumber = 0;
	UsbCamera camera;
	private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
	private ArrayList<MatOfPoint> filteredContoursOutput  = new ArrayList<MatOfPoint>();
	
	private double m_pixelDifference = 0;
	
	/**
	 * These are ints for image processing 
	 * DO NOT MESS WITH THESE
	 */
	private final static int 
		CV_RETR_EXTERNAL = 0,
		CV_RETR_LIST = 1,
		CV_RETR_CCOMP = 2,
		CV_RETR_TREE = 3,
		CV_CHAIN_APPROX_NONE = 1,
        CV_CHAIN_APPROX_SIMPLE = 2,
        CV_CHAIN_APPROX_TC89_L1 = 3,
        CV_CHAIN_APPROX_TC89_KCOS = 4;
	/**
	 * These are for filtering contours
	 * Values as of 1/31/2017 need to be tuned
	 * Area : self explainitory // fix this reasonsing
	 * Ratio : width to height
	 */
	private final static double 
//		MIN_CONTOUR_AREA = 10,
//		MAX_CONTOUR_AREA = 100,
//		MIN_BOX_WIDTH_HEIGHT_RATIO = (2.0/5.0) - .1,  
//		MAX_BOX_WIDTH_HEIGHT_RATIO = (2.0/5.0) + .1,
//		MIN_HEIGHT_BELOW_HEIGHT_ABOVE_RATIO = 0,
//		MAX_HEIGHT_BELOW_HEIGHT_ABOVE_RATIO = 1,
//		MIN_WIDTH = 5,
//		MAX_WIDTH = 75,
//		MIN_BOX_STARTING_HEIGHT_BOX_HEIGHT_RATIO = ((10.75)/(15.75)) - .2,
//		MAX_BOX_STARTING_HEIGHT_BOX_HEIGHT_RATIO = ((10.75)/(15.75)) - .2,
		BOX_WIDTH_HEIGHT_RATIO = 2.0/5.0,
		BOX_WIDTH_HEIGHT_TOLERANCE = .2;
	
	public enum ShipSide{
		SHIP_LEFT, SHIP_RIGHT
	}
	ShipSide m_side = null;
	ShipSide m_initialStartingSide = null;
	final int imageWidth = 640;
	final int imageHeight = 480;
	Team93Cam cam = new Team93Cam(CameraName, portNumber, imageWidth, imageHeight);
	boolean m_keepRunning = true;
	//Mat hsvOutput = new Mat(); //should not need in final version
	
	Mat output = new Mat();
	//Mat threashCopy = new Mat(); // up for debate on to use this or not
	//final int toleranceW = 10; //ill fix naming convention later
	//final int toleranceH = 10;
	//Range roiRows = new Range(toleranceW, cam.getWidth()-toleranceW);
	//Range roiCols = new Range(toleranceH, cam.getHeight()-toleranceH);
	/**
	 * This class is for storing the camera properties.  It is primarily used for having a convienct place to store values.
	 * 
	 * @author Colby McKinley
	 *
	 */
	class Team93Cam
	{
		String m_camName = "";
		int m_camPort = 0;
		int m_width = 0;
		int m_height = 0;
		/**
		 * 
		 * @param camName  a unique name for the camera
		 * @param camPort  the port the camera is plug into on the roborio
		 * @param width  the image width that the camera should capture
		 * @param height  the image height the camera should capture
		 */
		public Team93Cam(String camName, int camPort, int width, int height)
		{
			m_camName = camName;
			m_camPort = camPort;
			m_width = width;
			m_height = height;
		}
		/**
		 * Returns the unique name of the camera
		 * @return the name of the camera in the form of a string
		 */
		public String getCamName()
		{
			return m_camName;
		}
		/**
		 * Returns the USB port that the camera is plugged into
		 * @return the usb port that the camera is plugged into
		 */
		public int getCamPort()
		{
			return m_camPort;
		}
		/**
		 * 
		 * @return the width of the image from the frame of the camera
		 */
		public int getWidth()
		{
			return m_width;
		}
		/**
		 * 
		 * @return the height of the image from the frame of the camera
		 */
		public int getHeight()
		{
			return m_height;
		}
	}
	/**
	 * the Scalar lowerGreen is used for storing the minimum hsv values that an pixel must have to be considered green
	 */
	private final Scalar LOWER_GREEN = new Scalar(0,0,250);
	/**
	 * the Scalar upperGreen is used for storing the maximum hsv values that an pixel must have to be considered green
	 */
	private final Scalar UPPER_GREEN = new Scalar(255,255,255);
	/**
	 * the Scalar drawnContourLineColor is used for drawing on frc driver station
	 * I am not sure as of 2/10/2017 if this is rgb or hsv and I am not sure if I actually am getting this to appear on driver station
	 */
	private final Scalar drawnContourLineColor = new Scalar(19,100,100); //orange?
	
	/**
	 * CvSink is used for storing the video on the roborio for image processing
	 */
	CvSink mySink;
	/**
	 * CvSource is used for streaming a video to driver station
	 */
	CvSource outputStream;
	CameraData myData = new CameraData();;
    public VisionProcessor2017(ShipSide initialStartingSide) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//camera = new UsbCamera(CameraName, deviceNum);
    	m_side = initialStartingSide;
    	m_initialStartingSide = initialStartingSide;
    }
    /*
     * ideally we do not use this one
     */
    public VisionProcessor2017()
    {
    	m_side = ShipSide.SHIP_LEFT;
    }

    // Called just before this Command runs the first time
    synchronized protected void initialize() {
    	//get better variable names for readability 
    	resetAll();
    	camera = CameraServer.getInstance().startAutomaticCapture(cam.getCamName(), cam.getCamPort());
        camera.setResolution(cam.getWidth(),cam.getHeight());
        (new Thread(new RunnableVisionProcessor(this))).start();
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_keepRunning = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	resetAll();
    }
    
    protected void processor()
    {
    	
		mySink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo(cam.getCamName(), cam.getWidth(), cam.getHeight());
		//CvSource rgb = CameraServer.getInstance().putVideo(cam.getCamName(), cam.getWidth(), cam.getHeight());
		//CvSource outputStream = CameraServer.getInstance().putVideo(cam.getCamName(), cam.getWidth(), cam.getHeight());
		//Mat driverView = new Mat();
		//CvSource dv = CameraServer.getInstance().putVideo(cam.getCamName(), cam.getWidth(), cam.getHeight());
		Mat source = new Mat();  //should we move this out of processor?
		
    	while(m_keepRunning)
    	{
    		
    		synchronized(this)
    		{
    			mySink.grabFrame(source);
    			Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2HSV); //problem here
    			
    			imageProcesser();
    			//testing purposes:
    			//get a value for colorIdx ie a interger from Imgproc
    			//threashHolding();
    			//Imgproc.drawContours(output, filteredContoursOutput,0, drawnContourLineColor);
    			findSide(filteredContoursOutput);
    			outputStream.putFrame(output);
    			System.out.println("left right distance " + myData.getLeftRightDistance());
    			System.out.println("foward distance " + myData.getForwardDistance());
    			//System.out.println(m_side);
    			
    			//Imgproc.drawContours(image, findContoursOutput, contourIdx, color);
    			
    			//resetAll();
    		}
    		source.release();
    		output.release();
    	}
    }
    
    /**
     * takes in the images from and goes through each pixel determining its greeness and then returns a mat in binary (is pixel green or not)
     */
    protected void threashHolding()
    {
    	Core.inRange(output, LOWER_GREEN, UPPER_GREEN, output);
    }
    //does imageProcesser need to be synchroized?
    /**
     * This function 
     * @param source hsv threshold image
     */
    synchronized protected void imageProcesser()
    {
    	
    	threashHolding();
    	
    	//threashCopy = output.clone();
    	//maybe use simple instead of none
    	findContours(output, findContoursOutput, CV_RETR_CCOMP , CV_CHAIN_APPROX_NONE);
    	//Mat hierarchy = new Mat();
    	
    	//Imgproc.findContours(threashCopy, findContoursOutput, new Mat(), CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);
    	filterContours(findContoursOutput);
    	
    	System.out.println("found contours: " + findContoursOutput.size());
    	System.out.println("filitered contours:  " + filteredContoursOutput.size());
    	//this is used below, i might need this later find it in isTape()
    	//Imgproc.approxPolyDP(curve, approxCurve, epsilon, closed);
    	
    }
    
   
    /**
     *This function takes in the threshold imaged and finds all of the points that are on the edge between green and not green. 
     *It fills an arraylist that we used, called findContoursOutput
     *Mode:
     *CV_RETR_EXTERNAL retrieves only the extreme outer contours.
	 *CV_RETR_LIST retrieves all of the contours without establishing any hierarchical relationships.
	 *CV_RETR_CCOMP retrieves all of the contours and organizes them into a two-level hierarchy. At the top level, there are external boundaries of the components. At the second level, there are boundaries of the holes. If there is another contour inside a hole of a connected component, it is still put at the top level.
	 *CV_RETR_TREE retrieves all of the contours and reconstructs a full hierarchy of nested contours. This full hierarchy is built and shown in the OpenCV contours.c demo.
	 *		method - Contour approximation method 
	 *Method:
	 *CV_CHAIN_APPROX_NONE stores absolutely all the contour points. That is, any 2 subsequent points (x1,y1) and (x2,y2) of the contour will be either horizontal, vertical or diagonal neighbors, that is, max(abs(x1-x2),abs(y2-y1))==1.
	 *CV_CHAIN_APPROX_SIMPLE compresses horizontal, vertical, and diagonal segments and leaves only their end points. For example, an up-right rectangular contour is encoded with 4 points.
	 *CV_CHAIN_APPROX_TC89_L1,CV_CHAIN_APPROX_TC89_KCOS applies one of the flavors of the Teh-Chin chain approximation algorithm.
	 *
	 *
     * @param source  the filitered image that needs to be processed
     * @param contours  a list to store all of the contours that are found
     * @param mode  the mode that determines the way to collect and store contours  (in form of relationship)
     * @param method  the method that determines a way to store contours (in form of compression)
     */
    protected void findContours(Mat source,List<MatOfPoint> contours,int mode, int method)
    {
    	Mat hierarchy = new Mat();
		contours.clear();
		//Mat roi = new Mat(); //source.clone;
		//roi = source.clone().submat(roiRows, roiCols);
		Imgproc.findContours(source, contours, hierarchy, mode, method);
		//roi.release();
    }

   
    /*
	* TODO:  add more ways for filtering contours
	* Could just be a getter that returns an arraylist
	*/
    protected void filterContours(ArrayList<MatOfPoint> list)
    {
    	//use these to look in certain area of image 
    	//cant seem to get this to work right now
    	//int removingRows = 20;
    	//int removingColumns = 20;
    	Rect box;
    	double boxWidthHeightRatio;
    	if((!list.isEmpty()) && filteredContoursOutput != null){
    		filteredContoursOutput.clear();
    	}
    	for(MatOfPoint mop:list)
    	{
    		
    		//final double area = Imgproc.contourArea(mop);
    		box = Imgproc.boundingRect(mop);
    		//final double area = box.area();
    		boxWidthHeightRatio =  (double)box.width/(double)box.height;
    		//should we be using area?  i mean area can change drastically
    		
//    		if ((area <= MAX_CONTOUR_AREA) && (area >= MIN_CONTOUR_AREA))
//    		{
//    			continue;	
//    		}
    		
    		//int boxStartingHeightToHeightRatio = box.y/(box.y+box.height);
    		//if(((boxStartingHeightToHeightRatio > MIN_BOX_STARTING_HEIGHT_BOX_HEIGHT_RATIO) &&
    		//		(boxStartingHeightToHeightRatio < MAX_BOX_STARTING_HEIGHT_BOX_HEIGHT_RATIO))) continue;	
    		
    		//final int PERMITER = (2*box.width)+(2*box.height);
//    		if((PERMITER > MIN_PERMITER) &&(PERMITER < MAX_PERMITER)) 
//    		{
//    			continue;
//    		}
//    		if(!((box.width > MIN_WIDTH) && (box.width < MAX_WIDTH)))continue;
    		if(Math.abs(boxWidthHeightRatio - (BOX_WIDTH_HEIGHT_RATIO)) > BOX_WIDTH_HEIGHT_TOLERANCE)
    		{
    			continue;
			}
    			
//    		if ((boxWidthHeightRatio > MIN_BOX_WIDTH_HEIGHT_RATIO && 
//    				boxWidthHeightRatio < MAX_BOX_WIDTH_HEIGHT_RATIO)) continue;
    		//if(!((box.width > MIN_WIDTH)&&(box.width < MAX_WIDTH))) continue;
    		//if(((box.x > removingColumns) && ((box.x+box.width) <(cam.getWidth()-removingColumns)))) continue;
    		//if((box.y > removingRows) && (box.x+box.width < (cam.getHeight()-removingRows))) continue;
//    		if(isTape(mop, 100))
//    		{
//    			continue;
//    		}
    		//System.out.println("index size "+ filteredContoursOutput.size());
    		//filteredContoursOutput.add(list.get(i));
    		filteredContoursOutput.add(mop);
    		//System.out.println("possible rect: " + "(" + box.x + "," + box.y + ")");
    		
    		//System.out.println("contour " + mop.toString());
    	}
    }
    /*
     * we may want this be be a class holding an enum and a distance for pid purposes.
     * TODO implement pid.
     */
    synchronized protected void findSide( List<MatOfPoint> contours)
    {
    	ShipSide side = null;
    	
    	//maybe the best way is using a switch case
		int method = 3;
		/*
		*  This is method one.  It converts the contours into a list then I sort through it to find the points I want.  
		*  This will ideally be the primary  method for finding the sides
		*  
		*  As of 2/16/17 this method has not been teseted and it no longer should be the primary way
		*
		*/
		if(method == 1)
		{
			MatOfPoint[]  contourArray = (MatOfPoint[])contours.toArray();
			List<Point> pointsList = contourArray[0].toList();
			double firstx = 0;
			//double firsty = 0;
			double lastx = 0;
			//double lasty = 0;
			for(int i = 0; i< pointsList.size(); ++i)
			{
				for(int j = 0; j < pointsList.size(); ++j)
				{
					//System.out.println("in for loop");
					if((i == 0) &&(j==0))
					{
						firstx = pointsList.get(i).x;
						//firsty = pointsList.get(j).y;
					}
					else if((i == pointsList.size())&&(i == pointsList.size()))
					{
						lastx = pointsList.get(i).x;
						//lasty = pointsList.get(j).y;
					}
				}
			}
			
			int leftSidePixels = (int)firstx;
			int rightSidePixels = (int)(cam.getWidth() - lastx);
			double leftRightRatio = leftSidePixels/ rightSidePixels; //do we need this or can we go without
			
			if (leftRightRatio < 1)
			{
				//Drive Right
				//side = ShipSide.SHIP_RIGHT;
				System.out.println("driving left");
				setPixelDistance(1/leftRightRatio, leftSidePixels);
				
			}
			else if (leftRightRatio > 1)
			{
				//Drive left
				System.out.println("drivingRight");
				//side = ShipSide.SHIP_LEFT;
				//is that the right ratio?
				setPixelDistance(1/leftRightRatio, rightSidePixels);
			}
			
//			if(firstx > cam.getWidth()/2)
//			{
//				side = ShipSide.SHIP_RIGHT;
//			}
//			else if(lastx < cam.getWidth()/2)
//			{
//				side = ShipSide.SHIP_LEFT;
//			}
		}
    	
    
    	/*
		* This is method two.  It uses the rectangle tool in Imgproc to get x and * y values from the contours.  This is a backup if method one does not   	* work. 
		*/
		else if(method == 2)
		{
			if(filteredContoursOutput != null){
			Rect boxZero = Imgproc.boundingRect(filteredContoursOutput.get(0));
			if(boxZero.x > cam.getWidth()/2)
			{
				side = ShipSide.SHIP_RIGHT;
				System.out.println("driving left");
				System.out.println("box zero " + boxZero.area());
			}
			//TODO finish this method
//			Rect boxOne = Imgproc.boundingRect(filteredContoursOutput.get(1));
//			if(boxOne.x < cam.getWidth()/2)
//			{
//				side = ShipSide.SHIP_LEFT;
//				System.out.println("driving right");
//				System.out.println("box zero " + boxOne.area());
//			}
			}
			else {
				System.out.println("filtered contours is empty");
			}
		}
		/*
		 * Method 3 is the method where we have an advance detection using boundrect to determine which side is left or right
		 * It works out on paper and needs to be tested as of 2/16/17.  Personally I would prefer this to be the primary way.
		 * 
		 */
		else if (method == 3)
		{
			System.out.println("starting method 3");
			if(filteredContoursOutput != null)
			{

				//then determine which on is left or right based off of rect.x, where rect is the name of a rect
				Rect tapeLeft = null;
				Rect tapeRight = null;
				ArrayList<Rect> sortedListOfRect = sort(filteredContoursOutput);
				double leftRightRatio;
				double inches;
				double leftSidePixels;
				double rightSidePixels;
				double fowardDistance;
				//System.out.println("returned list size " + sortedListOfRect.size());
				if(sortedListOfRect.size() == 2)
				{
					if(sortedListOfRect.get(0).x < sortedListOfRect.get(1).x)
					{
						tapeLeft = sortedListOfRect.get(0);
						tapeRight = sortedListOfRect.get(1);
					}
					else
					{
						tapeLeft = sortedListOfRect.get(1);
						tapeRight = sortedListOfRect.get(0);
					}
					
					System.out.println("two pieces of tape found");
					//System.out.println("left tape area " + tapeLeft.area());
					//System.out.println("right tape area " + tapeRight.area());
					System.out.println("left tape x " + tapeLeft.x);
					//System.out.println("left tape width " + tapeLeft.width);
					System.out.println("right tape x " + tapeRight.x);
					System.out.println("left tape y " + tapeLeft.y);
					System.out.println("right tape y " + tapeRight.y);
					System.out.println("height of tape " + getDriveXDistance(tapeRight, tapeRight.y));
				}
				
				else if (sortedListOfRect.size() == 1)
				{
					String outputMessage = "found one piece of tape Robot thinks it is ";
					if(m_initialStartingSide != null)
					{
						if (m_initialStartingSide == ShipSide.SHIP_LEFT)
						{
							tapeLeft = sortedListOfRect.get(0);
							System.out.println(outputMessage + m_initialStartingSide);
						}
						else if (m_initialStartingSide  == ShipSide.SHIP_LEFT)
						{
							tapeRight = sortedListOfRect.get(0);
							System.out.println(outputMessage + m_initialStartingSide);
						}
						System.out.println("using initial starting side to find if we are left or right");
					}
					else 
					{
						
						tapeRight = sortedListOfRect.get(0);
						//maybe get direction of the wheels to determine way in the future
						System.out.println("found one piece of tape, assumed right");
					}
					System.out.println("one piece of tape found");
					
				}
				else{
					System.out.println("no pieces of tape");
				}
				//Rect box = Imgproc.boundingRect(filteredContoursOutput.get(0));
				//System.out.println("got 'em");
				//System.out.println(box.width);
				if((tapeLeft != null)&&(tapeRight != null))
				{
					leftSidePixels = (double)tapeLeft.x;
					rightSidePixels = (double)(cam.getWidth()-(tapeRight.x+tapeRight.width)); //does this work?
					//System.out.println("left side " + leftSidePixels);
					//System.out.println("right side " + rightSidePixels);
					leftRightRatio = (double)leftSidePixels/ (double)rightSidePixels;
					System.out.println("left-right ratio " + leftRightRatio);
					if (leftRightRatio < 1)
					{
						//Drive Right
						side = ShipSide.SHIP_RIGHT;
						System.out.println("driving left");
						setPixelDistance(1.0/leftRightRatio, leftSidePixels);
						System.out.println("pixels" + m_pixelDifference);
						inches = getDriveXDistance(tapeRight, m_pixelDifference);
						System.out.println("inches" + inches);
						fowardDistance = getDriveYDistance(tapeLeft.width);
						myData.setAll(inches, side, fowardDistance);// = new CameraData(inches, side, fowardDistance);
						
					}
					else if (leftRightRatio > 1)
					{
						//Drive left
						side = ShipSide.SHIP_LEFT;
						System.out.println("drivingRight");
						//side = ShipSide.SHIP_LEFT;
						//is that the right ratio? - it should be
						setPixelDistance(1.0/leftRightRatio, rightSidePixels);
						System.out.println("pixels" + m_pixelDifference);
						inches = getDriveXDistance(tapeRight, m_pixelDifference);
						System.out.println("inches" + inches);
						fowardDistance = getDriveYDistance(tapeLeft.width);
						myData.setAll(inches, side, fowardDistance); //= new CameraData(inches, side, fowardDistance);
					}
					//should this be an else?
					else if (leftRightRatio == 1)
					{
						System.out.println("driving foward");
					}
				}
				else if (tapeRight != null)
				{
					/*
					 * I believe this should work
					 */
					System.out.println("one piece of tape found");
					//lets drive left
					System.out.println("driving left");
					leftSidePixels = (double)tapeRight.x;
					rightSidePixels = (double)(cam.getWidth()-(tapeRight.x+tapeRight.width)); //does this work?
					//System.out.println("left side " + leftSidePixels);
					//System.out.println("right side " + rightSidePixels);
					leftRightRatio = (double)leftSidePixels/ (double)rightSidePixels;
					setPixelDistance(1.0/leftRightRatio, leftSidePixels);
					//System.out.println("pixels" + m_pixelDifference);
					inches = getDriveXDistance(tapeRight, m_pixelDifference);
					System.out.println("driving inches: "  + inches);
					fowardDistance = getDriveYDistance(tapeRight.width);
					myData.setAll(inches, side, fowardDistance); //= new CameraData(inches, side, fowardDistance);
				}
				else if (tapeLeft != null)
				{
					/*
					 * I believe this should work
					 */
					System.out.println("one piece of tape found");
					//lets drive left
					System.out.println("driving left");
					leftSidePixels = (double)tapeLeft.x;
					rightSidePixels = (double)(cam.getWidth()-(tapeLeft.x+tapeLeft.width)); //does this work?
					//System.out.println("left side " + leftSidePixels);
					//System.out.println("right side " + rightSidePixels);
					leftRightRatio = (double)leftSidePixels/ (double)rightSidePixels;
					setPixelDistance(1.0/leftRightRatio, leftSidePixels);
					//System.out.println("pixels" + m_pixelDifference);
					inches = getDriveXDistance(tapeLeft, m_pixelDifference);
					System.out.println("driving inches: "  + inches);
					fowardDistance = getDriveYDistance(tapeLeft.width);
					myData.setAll(inches, side, fowardDistance);// = new CameraData(inches, side, fowardDistance);
				}
				else{
					System.out.println("no pieces of tape found");
				}
				
				
			}
			else
			{
				System.out.println("something is wrong with filtering contours");
			}
		}
    	
    }
    
    /**
     * Returns the inches that the robot must drive in order to center
     * @return inches for encoder ticks
     */
    protected double getDriveXDistance(Rect piece, double inputPixels)
    {
    	int inchesTapeWidth = 2;
    	//countour width of one rectange is 2 inches
    	//pixel to inches is (pixelWidth/2) = (inputPixels/ x inches)
    	//Rect box = Imgproc.boundingRect(filteredContoursOutput.get(0));
    	return (double)piece.x/(double)(inchesTapeWidth * inputPixels);
    }
    protected void setPixelDistance(double ratio, double leftSidePixels)
    {
    	m_pixelDifference = ratio*leftSidePixels;
    }
    
    protected double getDriveYDistance(double inputPixels)
    {
		//double m_inputPixels = inputPixels;
		double focalLength = 705.5625;  // @Colby_McKinley calculated this out
		double distanceFromTarget = 0.0;
		final double knownWidth = 2.0; //inches
		
		distanceFromTarget = focalLength*knownWidth/inputPixels;
		return distanceFromTarget;
    	
    }
    
    /**
     * This function sorts through all possible combinations of the given contour list and sees if they are the pieces of tape
     * @param listOfMOP filtered contours
     * @return the two pieces of tape
     */
    protected synchronized ArrayList<Rect> sort(ArrayList<MatOfPoint> listOfMOP)
    {
    	//double lastMaxArea = 0;
    	//Rect[] myList = new Rect[listOfMOP.size()];
    	ArrayList<Rect> twoPiecesOfTape = new ArrayList<Rect>();
    	ArrayList<Rect> listOfRect = new ArrayList<Rect>();
    	int yTolerance = 10;
    	double tapeWidthOverTotalWidthTolerance = 0.2;
    	
    	double interiorWidthOverTotalWidthTolerance = 0.2;
    	double tapeOneWidthOverTapeTwoWidthTolerance = 0.2;
    	//Rect test;// = new Rect();
    	//final double INTERIOR_WIDTH_RATIO = 6.25;
    	final double INTERIOR_TOTAL_WIDTH_RATIO = 6.25/10.25;
    	final double TAPE_WIDTH_TOTAL_WIDTH_RATIO = 2.0/10.25;
    	final double TAPE_WIDTH_TAPE_WIDTH_RATIO = 2.0/2.0;
    	double tapeZeroWidthOverTapeOneWidth = 0.0;
    	double tapeZeroWidthOverTotalWidth = 0.0;
    	double tapeOneWidthOverTotalWidth = 0.0;
    	
    	double interiorWidthOverTotalWidth = 0.0;
    	double totalWidth = 0.0;
    	double interiorWidth = 0.0;
    	for(MatOfPoint mop : listOfMOP)
    	{
    		//test = ;
    		listOfRect.add(Imgproc.boundingRect(mop));
    		
    	}
    	//print(listOfMOP, true);
    	System.out.println("rectangle list size " + listOfRect.size());
    	boolean isFound = false; 
    	if(listOfRect.size() > 1){
	        for (int i = 0; i < listOfRect.size(); i++) {
	        	for (int j = 0; j < listOfRect.size(); j++)
	        	{
	        		//twoPiecesOfTape.clear();
	        		if(i >= j) continue;
	        		Rect tempZero = listOfRect.get(i);
		            Rect tempOne = listOfRect.get(j);
		            
		            if(tempOne.x < tempZero.x)
		            {
		            	Rect tempTwo = tempZero;
            			tempZero = tempOne;
            			tempOne = tempTwo;
		            }
		            interiorWidth = tempOne.x - (tempZero.x+tempZero.width);
		            if(checkForIntersection(tempZero, tempOne)) continue;
		            //System.out.println("interior width " + interiorWidth);
		            //System.out.println("tempZero (x,y) " + "(" + tempZero.x + "," + tempZero.y + ")");
		            //System.out.println("tempOne (x,y) " + "(" + tempOne.x + "," + tempOne.y + ")");
		            totalWidth = tempZero.width+interiorWidth+tempOne.width;
		            //System.out.println("total width " + totalWidth );
		            tapeZeroWidthOverTapeOneWidth = (double)tempZero.width/(double)tempOne.width;
		            tapeZeroWidthOverTotalWidth = (double)tempZero.width/(double)totalWidth;
		            interiorWidthOverTotalWidth = (double)interiorWidth/(double)totalWidth;
		            
		            tapeOneWidthOverTotalWidth = (double)tempOne.width/(double)totalWidth;
		            //int j = i;
		            if(Math.abs(tempZero.y - tempOne.y) > yTolerance) continue;
		            //System.out.println("less than 50");
		            if(Math.abs(tapeZeroWidthOverTapeOneWidth - TAPE_WIDTH_TAPE_WIDTH_RATIO) > tapeOneWidthOverTapeTwoWidthTolerance) continue;
		            //System.out.println("not same width ratio");
		            if(Math.abs(tapeZeroWidthOverTotalWidth - TAPE_WIDTH_TOTAL_WIDTH_RATIO) > tapeWidthOverTotalWidthTolerance)continue;
		            //System.out.println("good same box-total ratio");
		            if(Math.abs(tapeOneWidthOverTotalWidth - TAPE_WIDTH_TOTAL_WIDTH_RATIO) > tapeWidthOverTotalWidthTolerance)continue;
		            //System.out.println("good same box-total ratio 2 ");
		            if(Math.abs(interiorWidthOverTotalWidth - INTERIOR_TOTAL_WIDTH_RATIO) > interiorWidthOverTotalWidthTolerance)continue;
		            //System.out.println("interior width good");
		            twoPiecesOfTape.add(tempZero);
			    	twoPiecesOfTape.add(tempOne);
			    	//System.out.println("left counter"  + leftCounter);
			    	//System.out.println("right counter" + rightCounter);
			    	//System.out.println("interior width " + interiorWidth);
			    	//System.out.println("total width " + totalWidth);
			    	System.out.println("size of found tape " + twoPiecesOfTape.size());
			    	System.out.println(("my width " + tempZero.width));
			    	System.out.println(("my hieght " + tempZero.height));
			    	isFound = true;
			    	break;
	        	}
	        	if(isFound) break;
	        }
	    	
    	}
    	return twoPiecesOfTape;
    }
    /**
     * Returns true if there is an intersection between those two rectangles
     * @return 
     */
    protected boolean checkForIntersection(Rect zero, Rect one)
    {
    	//System.out.println("intersection width print" + zero.width);
    	return !(one.x > zero.x+zero.width);
    	
    }
    // modified from http://stackoverflow.com/questions/22131032/detecting-triangles-in-opencv-approxpoly
    protected boolean isTape(MatOfPoint thisContour, int maxPoints) {

        Rect rectangle = null;

        MatOfPoint2f thisContour2f = new MatOfPoint2f();
        MatOfPoint approxContour = new MatOfPoint();
        MatOfPoint2f approxContour2f = new MatOfPoint2f();

        thisContour.convertTo(thisContour2f, CvType.CV_32FC2);

        Imgproc.approxPolyDP(thisContour2f, approxContour2f, 2, true);

        approxContour2f.convertTo(approxContour, CvType.CV_32S);

        if (approxContour.size().height <= maxPoints) {
        	rectangle = Imgproc.boundingRect(approxContour);
        }

        return (rectangle != null);
    }
   
    
    /*
     * not working - memory issues
     */
    public void print( ArrayList<MatOfPoint> list, boolean filitered)
    {
    	MatOfPoint[]  contourArray = (MatOfPoint[])list.toArray();
		List<Point> pointsList = contourArray[0].toList();
    	for(int i =0; i< pointsList.size(); ++i)
    	{
    		if(filitered)
    		{
    			System.out.println("filtired contours  "  + pointsList.get(i));
    		}
    		else
    		{
    			System.out.println("found contours  " +  pointsList.get(i));
    		}
    		
    	}
    	
//    	for(int i = 0; i < image.width(); ++i)
//    	{
//    		forf(int j =0; j < image.height(); ++j)
//    		{
//    			Mat hello = (image.submat(new Range(10,image.width()-10), new Range(10, image.height()-10)));
//    			System.out.println(hello.get(11, 11));
//    			hello.release();
//    		}
//    	}
    }
    /**
     * This function resets all of the informatin we grab during the image processing.
     */
    protected synchronized void resetAll()
    {
    	findContoursOutput.clear();
    	filteredContoursOutput.clear();
    	m_pixelDifference = 0;
    }
    
    protected class CameraData
    {
    	double m_inchesLeftRight = 0.0;
    	double m_encoderTicks = 0.0;
    	final double DIAMETER = 3.25; 
    	final double CIRCUMPRANCE = Math.PI * DIAMETER;
    	double m_inchesForward;
    	ShipSide currentSide = null;
    	public CameraData()
    	{
    		
    	}
    	public CameraData(double inchesLeftRight, ShipSide side, double inchesForwad)
    	{
    		m_inchesLeftRight = inchesLeftRight;
    		currentSide = side;
    		m_inchesForward = inchesForwad;
    	}
    	public void setAll(double inchesLeftRight, ShipSide side, double inchesForwad)
    	{
    		m_inchesLeftRight = inchesLeftRight;
    		currentSide = side;
    		m_inchesForward = inchesForwad;
    	}
    	private double convertInchesEncoderTicks()
    	{
    		m_encoderTicks = m_inchesLeftRight * 360/ CIRCUMPRANCE;
    		return m_encoderTicks;
    	}
    	
    	public double getLeftRightDistance()
    	{
    		if(currentSide == ShipSide.SHIP_LEFT)
    		{
    			return convertInchesEncoderTicks();
    		}
    		else if (currentSide == ShipSide.SHIP_RIGHT)
    		{
    			return  -convertInchesEncoderTicks();
    		}
    		return 0;
    	}
    	public double getForwardDistance()
    	{
    		return m_inchesForward;
    	}
    	
    	
    }
   /**
   * This class returns the value for the PID to drive foward
   * @author Colby
   *
   */
   public class FowardOutpt implements PIDSource
   {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) 
		{
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public PIDSourceType getPIDSourceType() 
		{
			// TODO Auto-generated method stub
			return null;
		}
	
		@Override
		public double pidGet() 
		{
			// TODO Auto-generated method stub
			return myData.getForwardDistance();
		}
	   
   }
   /**
    * This class returns the value for the PID to center itself according to the tape
    * @author Colby
    *
    */
   public class CenterOutput implements PIDSource
   {

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return myData.getLeftRightDistance();
	}
	   
   }
    
	
    /**
     * This class runs the main processing thread found earlier in the VisionProcessor2017 class
     * It is needed to run threads
     * @author Colby McKinley
     *
     */
    public class RunnableVisionProcessor implements Runnable {

        VisionProcessor2017 m_Run;

        RunnableVisionProcessor(VisionProcessor2017 vp_2017) {
            m_Run = vp_2017;
        }

        @Override
        public void run() {
            m_Run.processor(); //problem here
            
        }
    }
    

}

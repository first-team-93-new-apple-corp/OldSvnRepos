package Vision;

import org.opencv.imgproc.Imgproc;

import UDP.UDPServer;

//import UDP.UDPServer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;


/**
 * This is the VisionProcessor2017 class. This class' goal is to recognize the
 * retro-reflective tape on the air ship and then outputs data to go to the position 
 * so the robot can put a gear on the peg
 * 
 * @author Colby McKinley
 */
public class VisionProcessorPI
{

	/**
	 * CameraName is a unique name for the camera
	 */
	private String CameraName = "cam0";
	/**
	 * PORT_NUMBER is used for telling the program what usb port the camera
	 * is active on.
	 */
	private final int PORT_NUMBER = 1;
	/**
	 * imageWidth is the pixel width of the image read from the camera
	 */
	private final int imageWidth = 640;
	/**
	 * imageHeight is the pixel height of the image read from the camera
	 */
	private final int imageHeight = 480;
	/**
	 * capture is the series of images from the camera
	 */
	private VideoCapture capture;
	/**
	 * captureActive is a boolean to detmine if the capturing of images has started
	 */
	private boolean captureActive;
	/**
	 * debugMode is used to display a new window too see what the camera sees
	 */
	private boolean debugMode = true;
	/**
	 * findContoursOutput is an arraylist to store all possible contours where a single piece of tape could be
	 */
	private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
	/**
	 * filteredContoursOutput is an arraylist to store all possible contours where two pieces of tape could be
	 */
	private ArrayList<MatOfPoint> filteredContoursOutput = new ArrayList<MatOfPoint>();
	/**
	 * m_pixelDifference is a double to store the value of the pixels 
	 */
	private double m_pixelDifference = 0;
	/**
	 * These values are used for processing the images returned from the camera.
	 * The first four values are used for telling the program how to generate a list of
	 * contours.  The last four are used for organizing the contours by their properties.
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
	 * These values are used for finding a piece of tape based off of one tape's properties
	 * FRC Manual dictates a 2in by 5in piece of tape, this along with a high error tolerance will allow the
	 * program to generate a list of possible pieces of tape
	 */
	private final static double
		BOX_WIDTH_HEIGHT_RATIO = 2.0 / 5.0,
		BOX_WIDTH_HEIGHT_TOLERANCE = .2;

	/**
	 * ShipSide is side of the peg the robot is on
	 * @author Colby McKinley
	 *
	 */
	public enum ShipSide_t
	{
		SHIP_LEFT, SHIP_RIGHT, SHIP_CENTER
	}

	/**
	 * m_side is the current side of the peg we are on
	 */
	ShipSide_t m_side = null;
	/**
	 * m_initalStartingSide is the side the robot starts on, it is used for when only one tape can be found
	 */
	//should be removed in a update
	ShipSide_t m_initialStartingSide = null;
	/**
	 * cam is a storage area for storing the properties for our camera
	 */
	//it is better to use a struct here, as we can have multiple cameras running this if desired
	Team93Cam cam = new Team93Cam(CameraName, PORT_NUMBER, imageWidth, imageHeight);
	/**
	 * m_keepRunning is a boolean used to determine when the thread should stop
	 */
	boolean m_keepRunning = true;
	//private Timer timer; //ill remove this when i consult a mentor and have SVN availbe
	/*
	 * OpenCV treats a Mat as a matrix, and when we do image processing, we can consider a image
	 * as a matrix of values, at location (0,0) the pixel color value is (0,0,0).
	 */
	/**
	 * output is a mat that stores the values from image processing.  This is constantly being updated.
	 */
	Mat output = new Mat();
	
	/**
	 * the Scalar lowerGreen is used for storing the minimum hsv values that an
	 * pixel must have to be considered green
	 */
	private final Scalar LOWER_GREEN = new Scalar(0, 0, 250);
	/**
	 * the Scalar upperGreen is used for storing the maximum hsv values that an
	 * pixel must have to be considered green
	 */
	private final Scalar UPPER_GREEN = new Scalar(255, 255, 255);
	
	/**
	 * myData is a storage space for storing the values calculated during the processing of the image
	 */
	CameraData myData = new CameraData();
	/**
	 * regulator sorts through the data and assigns a quality score to the data
	 */
	VisionDataRegulator regulator = new VisionDataRegulator();
	/**
	 * viewer is a window that displays the image captured from the camera
	 */
	Imshow viewer = new Imshow("Debug Window");
	/**
	 * This constructor sets the current side and initial starting side to the passed in side 
	 * @param initialStartingSide the initial starting side of the peg the robot is on, this is only used
	 * in event that the camera can only see one piece of tape
	 */
	public VisionProcessorPI(ShipSide_t initialStartingSide)
	{
		m_side = initialStartingSide;
		m_initialStartingSide = initialStartingSide;
	}

	/**
	 * This constructor assumes that the current robot is left of the peg.
	 */
	public VisionProcessorPI()
	{

		m_side = ShipSide_t.SHIP_LEFT;
	}
	/**
	 * This function prepares the classes to be run
	 */
	synchronized protected void initialize()
	{
		resetAll();
		this.capture = new VideoCapture();
		//camera = CameraServer.getInstance().startAutomaticCapture(cam.getCamName(), cam.getCamPort());
		//camera.setResolution(cam.getWidth(), cam.getHeight());
		(new Thread(new RunnableVisionProcessor(this))).start();

	}

	/**
	 * This function ends the class
	 */
	protected void end()
	{
		m_keepRunning = false;
		this.captureActive = false;
		
	}

	/**
	 * This function starts the camera feed
	 */
	protected void startCamera()
	{
		if(!captureActive)
		{
			this.capture.open(cam.getCamPort());
			// is the video stream available?
            if (this.capture.isOpened())
            {
                    this.captureActive = true;
                    //camera.read(source)
                    // grab a frame every 33 ms (30 frames/sec)
                    
//                    TimerTask frameGrabber = new TimerTask() 
//                    {
//                            @Override
//                            public void run()
//                            {
//                            	readCamera();
//                            	//processor(); //may not be needed here
//                            }
//                    };
//                    this.timer = new Timer();
//                    this.timer.schedule(frameGrabber, 0, 33);
            }
		}
		else
        {
			this.captureActive = false;
//            if (this.timer != null)
//            {
//            	this.timer.cancel();
//                this.timer = null;
//            }
            this.capture.release();
        }
	}
	/**
	 * This function reads in the image
	 * !note this is not used as of 3-2-2017!
	 */
	//lets remove
	private void readCamera()
	{
		Mat source_cc = new Mat();
		if (this.capture.isOpened())
		{
			this.capture.read(source_cc);
			//Imgproc.cvtColor(source_cc, output, Imgproc.COLOR_BGR2HSV);
		}
		//source_cc.release();
	}
	/**
	 * This function process the image and sends out the values to the roborio
	 */
	protected void processor()
	{
		
		//Mat source = new Mat();
		startCamera();
		/**
		 * source is mat for the camera to set values to.  It will keep getting reset
		 */
		Mat source = new Mat();

		while (m_keepRunning)
		{

			synchronized (this)
			{
				
                this.capture.read(source);
                Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2HSV);
				if(!output.empty()){
					imageProcesser();
					findSide();
					if(debugMode)
					{
						viewer.showImage(output);
					}
				
					System.out.println("left right distance " + myData.getLeftRightDistance());
					System.out.println("foward distance " + myData.getForwardDistance());
					UDPServer.setData(myData);
					UDPServer.send();
				}
				
				// resetAll();
			}
			
		}
		source.release();//release the values to prevent memory overflow
		output.release();//release the values to prevent memory overflow
	}

	/**
	 * This function takes in the images from and goes through each pixel determining its
	 * greeness and then sets binary values into our output matrix, based on if the pixel is considered green
	 * 
	 */
	private void threashHolding()
	{
		Core.inRange(output, LOWER_GREEN, UPPER_GREEN, output);
	}

	// does imageProcesser need to be synchroized?
	/**
	 * This function takes in the image and processes where the pieces of tape are.  It then populates these into an arraylist.
	 * 
	 */
	synchronized private void imageProcesser()
	{

		threashHolding();

		findContours(output, findContoursOutput, CV_RETR_CCOMP, CV_CHAIN_APPROX_NONE);
		filterContours(findContoursOutput);

		System.out.println("found contours: " + findContoursOutput.size());
		System.out.println("filitered contours:  " + filteredContoursOutput.size());

	}

	/**
	 * This function takes in the threshold imaged and finds all of the points
	 * that are on the edge between green and not green. It fills an arraylist
	 * that we used, called findContoursOutput
	 * 
	 * Mode: 
	 * CV_RETR_EXTERNAL retrieves only the extreme outer contours. 
	 * CV_RETR_LIST retrieves all of the contours without establishing any hierarchical relationships.
	 * CV_RETR_CCOMP retrieves all of the contours and organizes them into a
	 * two-level hierarchy. At the top level, there are external boundaries of
	 * the components. At the second level, there are boundaries of the holes.
	 * If there is another contour inside a hole of a connected component, it is
	 * still put at the top level. 
	 * CV_RETR_TREE retrieves all of the contours
	 * and reconstructs a full hierarchy of nested contours. This full hierarchy
	 * is built and shown in the OpenCV contours.c demo. method - Contour
	 * approximation method 
	 * Method: 
	 * CV_CHAIN_APPROX_NONE stores absolutely all
	 * the contour points. That is, any 2 subsequent points (x1,y1) and (x2,y2)
	 * of the contour will be either horizontal, vertical or diagonal neighbors,
	 * that is, max(abs(x1-x2),abs(y2-y1))==1. 
	 * CV_CHAIN_APPROX_SIMPLE compresses
	 * horizontal, vertical, and diagonal segments and leaves only their end
	 * points. For example, an up-right rectangular contour is encoded with 4
	 * points. 
	 * CV_CHAIN_APPROX_TC89_L1,
	 * CV_CHAIN_APPROX_TC89_KCOS 
	 * applies one of the flavors of the Teh-Chin chain approximation algorithm.
	 *
	 *
	 * @param source
	 *            the filtered/threshold image that needs to be processed
	 * @param contours
	 *            a list to store all of the contours that are found
	 * @param mode
	 *            the mode that determines the way to collect and store contours
	 *            (in form of relationship)
	 * @param method
	 *            the method that determines a way to store contours (in form of
	 *            compression)
	 */
	private void findContours(Mat source, List<MatOfPoint> contours, int mode, int method)
	{
		Mat hierarchy = new Mat();
		contours.clear();
		Imgproc.findContours(source, contours, hierarchy, mode, method);
	}

	/**
	 * This function sorts through all of the contours and the determines if it is a single piece of tape using 
	 * a single piece of tape properties.
	 * @param list a list of all possible contours, found through findContours()
	 */
	private void filterContours(ArrayList<MatOfPoint> list)
	{
		Rect box;
		double boxWidthHeightRatio;
		if ((!list.isEmpty()) && filteredContoursOutput != null)
		{
			filteredContoursOutput.clear();
		}
		for (MatOfPoint mop : list)
		{
			/*
			 * Imgproc.boundingRect will draw a rectangle around a general group of contours. 
			 * This rectangle is estimated, but will be good for getting accurate feedback 
			 */
			box = Imgproc.boundingRect(mop);
			boxWidthHeightRatio = (double) box.width / (double) box.height;

			/*
			 * if the width/height ratio is not within tolerance it will skip over that point
			 */
			if (Math.abs(boxWidthHeightRatio - (BOX_WIDTH_HEIGHT_RATIO)) > BOX_WIDTH_HEIGHT_TOLERANCE)
			{
				continue;
			}
			filteredContoursOutput.add(mop);
			// System.out.println("possible rect: " + "(" + box.x + "," + box.y + ")");
		}
	}

	/**
	 * This function finds what side the robot is on relative to the peg.
	 * It also finds how far away the robot is and how far off center the robot is
	 */
	synchronized private void findSide()
	{
		ShipSide_t side = null;

		if (filteredContoursOutput.size() > 0)
		{

			Rect tapeLeft = null;
			Rect tapeRight = null;
			ArrayList<Rect> sortedListOfRect = findTwoPiecesOfTape(filteredContoursOutput);
			double leftRightRatio;
			double leftRightDistance;
			double leftSidePixels;
			double rightSidePixels;
			double fowardDistance;
			// System.out.println("returned list size " +
			// sortedListOfRect.size());
			if (sortedListOfRect.size() == 2)
			{
				/*
				 * here we make sure that the tapeLeft is to the left of tapeRight,
				 * this is important for assumptions made for the processing
				 */
				if (sortedListOfRect.get(0).x < sortedListOfRect.get(1).x)
				{
					tapeLeft = sortedListOfRect.get(0);
					tapeRight = sortedListOfRect.get(1);
				} 
				else
				{
					tapeLeft = sortedListOfRect.get(1);
					tapeRight = sortedListOfRect.get(0);
				}
				
				if((tapeLeft != null)&&(tapeRight != null))
				{
					//here the program determines how many pixels are left of to the start of tapeLeft
					leftSidePixels = (double)tapeLeft.x;
					//here the program determines how many pixels are right of to the end of tapeRight
					rightSidePixels = (double)(cam.getWidth()-(tapeRight.x+tapeRight.width));
					//System.out.println("left side " + leftSidePixels);
					//System.out.println("right side " + rightSidePixels);
					leftRightRatio = (double)leftSidePixels/ (double)rightSidePixels;
					System.out.println("left-right ratio " + leftRightRatio);
					if (leftRightRatio < 1)
					{
						//Drive Right
						side = ShipSide_t.SHIP_RIGHT;
						System.out.println("driving left");
						setPixelDistance(1.0/leftRightRatio, leftSidePixels);
						System.out.println("pixels" + m_pixelDifference);
						leftRightDistance = getDriveXDistance(tapeRight, m_pixelDifference);
						System.out.println("inches" + leftRightDistance);
						fowardDistance = getDriveYDistance(tapeLeft.width);
						regulator.setQualtityControlVariables(fowardDistance, leftRightDistance, tapeLeft.width, tapeLeft.height);
						myData.setAll(leftRightDistance, side, fowardDistance, regulator.getScore());
						
					}
					else if (leftRightRatio > 1)
					{
						//Drive left
						side = ShipSide_t.SHIP_LEFT;
						System.out.println("drivingRight");
						setPixelDistance(1.0/leftRightRatio, rightSidePixels);
						System.out.println("pixels" + m_pixelDifference);
						leftRightDistance = getDriveXDistance(tapeRight, m_pixelDifference);
						System.out.println("inches" + leftRightDistance);
						fowardDistance = getDriveYDistance(tapeLeft.width);
						regulator.setQualtityControlVariables(fowardDistance, leftRightDistance, tapeLeft.width, tapeLeft.height);
						myData.setAll(leftRightDistance, side, fowardDistance, regulator.getScore());
					}
					else if (leftRightRatio == 1)
					{
						System.out.println("driving foward");
						leftRightDistance = 0;
						fowardDistance= getDriveYDistance(tapeLeft.width);
						regulator.setQualtityControlVariables(fowardDistance, leftRightDistance, tapeLeft.width, tapeLeft.height);
						myData.setAll(leftRightDistance, side, fowardDistance, regulator.getScore());
					}
				}

//				System.out.println("two pieces of tape found");
//				System.out.println("left tape area " + tapeLeft.area());
//				System.out.println("right tape area " + tapeRight.area());
//				System.out.println("left tape x " + tapeLeft.x);
//				System.out.println("left tape width " + tapeLeft.width);
//				System.out.println("right tape x " + tapeRight.x);
//				System.out.println("left tape y " + tapeLeft.y);
//				System.out.println("right tape y " + tapeRight.y);
//				System.out.println("height of tape " + getDriveXDistance(tapeRight, tapeRight.y));
			}
			/*
			 *  for finding just one piece of tape 
			 *  As of 3/2/2017 @Colby McKinley has reason to believe that this actually does not
			 *  as intended.  
			 *  Theorictally this will find the rectangle by find the contours group with
			 *  the largest area.  Then return values based off of that.
			 */
			else{
				ArrayList<Rect> rectList = new ArrayList<Rect>();
				sortContoursByArea(filteredContoursOutput);
				makeRect(filteredContoursOutput, rectList);
				Rect foundRect = rectList.get(0);
				if(foundRect.x > cam.getWidth()/2)
				{
					side = ShipSide_t.SHIP_LEFT;
					leftSidePixels = (double)foundRect.x;
					rightSidePixels = (double)(cam.getWidth()-(foundRect.x+foundRect.width)); 
					leftRightRatio = (double)leftSidePixels/ (double)rightSidePixels;
					setPixelDistance(1/leftRightRatio, rightSidePixels);
					leftRightDistance = getDriveXDistance(foundRect, m_pixelDifference);
					fowardDistance = 0;
					regulator.setQualtityControlVariables(fowardDistance, leftRightDistance, foundRect.width, foundRect.height);
					myData.setAll(leftRightDistance, side, fowardDistance, regulator.getScore());
				}
				else
				{
					side = ShipSide_t.SHIP_RIGHT;
					leftSidePixels = (double)foundRect.x;
					rightSidePixels = (double)(cam.getWidth()-(foundRect.x+foundRect.width)); 
					leftRightRatio = (double)leftSidePixels/ (double)rightSidePixels;
					setPixelDistance(1/leftRightRatio, leftSidePixels);
					leftRightDistance = getDriveXDistance(foundRect, m_pixelDifference);
					fowardDistance = 0;
					regulator.setQualtityControlVariables(fowardDistance, leftRightDistance, foundRect.width, foundRect.height);
					myData.setAll(leftRightDistance, side, fowardDistance, regulator.getScore());
				}
			}
		}
			
	}

	/**
	 * Returns the inches that the robot must drive in order to center
	 * @param tape a piece of tape
	 * @param inputPixels the found number of pixels to drive left or right inorder to center the robot
	 * @return inches for encoder ticks
	 */
	private double getDriveXDistance(Rect piece, double inputPixels)
	{
		int inchesTapeWidth = 2;  //width in inches
		// countour width of one rectange is 2 inches
		// pixel to inches is (pixelWidth/2) = (inputPixels/ x inches)
		// Rect box = Imgproc.boundingRect(filteredContoursOutput.get(0));
		return piece.x / (inchesTapeWidth * inputPixels);
	}

	/**
	 * This function sets the amount of pixels to drive left or right to center the robot
	 * @param ratio  the leftRight ratio found
	 * @param leftSidePixels //maybe a better param name
	 */
	private void setPixelDistance(double ratio, double leftSidePixels)
	{
		m_pixelDifference = ratio * leftSidePixels;
	}

	/**
	 * This function determines how far the robot is from the piece of tape
	 * @param inputPixels 
	 * @return the distance in inches the robot is from the piece of tape
	 */
	private double getDriveYDistance(double inputPixels)
	{
		// double m_inputPixels = inputPixels;
		final double FOCAL_LENGTH = 705.5625; // @Colby McKinley calculated this out
		double distanceFromTarget = 0.0;
		final double KNOWN_WIDTH_OF_PIECE_OF_TAPE = 2.0; // inches

		distanceFromTarget = FOCAL_LENGTH * KNOWN_WIDTH_OF_PIECE_OF_TAPE / inputPixels;
		return distanceFromTarget;

	}
	/**
	 * This function draws rectangles around a group of contours and the populates a list with these rectangles
	 * @param contoursList the list of found contours
	 * @param output a list of rectangles
	 */
	private void  makeRect(List<MatOfPoint> contoursList, List<Rect> output)
	{
		Rect rect;
		output.clear();
		for(MatOfPoint mop: contoursList)
		{
			rect = Imgproc.boundingRect(mop);
			output.add(rect);
		}
	}
	/**
	 * This function sorts through a list of contours and determines then reorders the list based
	 * on the size of the contour group
	 * @param contours list of contours to sort
	 */
	private void sortContoursByArea(List<MatOfPoint> contours) 
	{
		  Collections.sort(contours, Comparator.comparingDouble(Imgproc::contourArea));
	}

	/**
	 * This function sorts through all possible combinations of the given
	 * contour list and sees if they are two pieces of tape
	 * 
	 * @param listOfMOP
	 *            filtered contours
	 * @return the two pieces of tape
	 */
	private synchronized ArrayList<Rect> findTwoPiecesOfTape(ArrayList<MatOfPoint> listOfMOP)
	{
		ArrayList<Rect> twoPiecesOfTape = new ArrayList<Rect>();
		ArrayList<Rect> listOfRect = new ArrayList<Rect>();
		/**
		 * yTolerance is the amount of pixels that another piece of tape can start at higher or lower
		 * than what the first piece of tape started at
		 */
		int yTolerance = 10;  //px
		double tapeWidthOverTotalWidthTolerance = 0.2;

		double interiorWidthOverTotalWidthTolerance = 0.2;
		double tapeOneWidthOverTapeTwoWidthTolerance = 0.2;
		/**
		 * INTERIOR_TOTAL_WIDTH_RATIO is the interior width between two pieces of tape
		 * divided by the total width that the two pieces of tape occupy
		 */
		final double INTERIOR_TOTAL_WIDTH_RATIO = 6.25 / 10.25;
		/**
		 * TAPE_WIDTH_TOTAL_WIDTH_RATIO is the width of a piece of tape over the total width
		 * two pieces of tape occupy
		 */
		final double TAPE_WIDTH_TOTAL_WIDTH_RATIO = 2.0 / 10.25;
		/**
		 * TAPE_WIDTH_TAPE_WIDTH_RATIO is the width of one piece of tape divided by
		 * the width of another piece of tape
		 */
		final double TAPE_WIDTH_TAPE_WIDTH_RATIO = 2.0 / 2.0;
		double tapeZeroWidthOverTapeOneWidth = 0.0;
		double tapeZeroWidthOverTotalWidth = 0.0;
		double tapeOneWidthOverTotalWidth = 0.0;

		double interiorWidthOverTotalWidth = 0.0;
		double totalWidth = 0.0;
		double interiorWidth = 0.0;
		/*
		 * draw rectangles so analyzing the contours will be easier
		 */
		for (MatOfPoint mop : listOfMOP)
		{
			listOfRect.add(Imgproc.boundingRect(mop));

		}
		//System.out.println("rectangle list size " + listOfRect.size());
		/*
		 * the boolean is used to break out of both for loops once the two
		 * pieces of tape are found
		 */
		boolean isFound = false;
		if (listOfRect.size() > 1)
		{
			for (int i = 0; i < listOfRect.size(); i++)
			{
				for (int j = 0; j < listOfRect.size(); j++)
				{
					//go through every combination of rectangles
					if (i >= j)
						continue;
					Rect tempZero = listOfRect.get(i);
					Rect tempOne = listOfRect.get(j);

					/*
					 * It is assumed that the tapeZero is set is left to tapeOne
					 * this ensures the logic
					 */
					if (tempOne.x < tempZero.x)
					{
						Rect tempTwo = tempZero;
						tempZero = tempOne;
						tempOne = tempTwo;
					}
					interiorWidth = tempOne.x - (tempZero.x + tempZero.width);
					if (checkForIntersection(tempZero, tempOne))
						continue;
					// System.out.println("interior width " + interiorWidth);
					// System.out.println("tempZero (x,y) " + "(" + tempZero.x +
					// "," + tempZero.y + ")");
					// System.out.println("tempOne (x,y) " + "(" + tempOne.x +
					// "," + tempOne.y + ")");
					totalWidth = tempZero.width + interiorWidth + tempOne.width;
					// System.out.println("total width " + totalWidth );
					tapeZeroWidthOverTapeOneWidth = (double) tempZero.width / (double) tempOne.width;
					tapeZeroWidthOverTotalWidth = tempZero.width / totalWidth;
					interiorWidthOverTotalWidth = interiorWidth / totalWidth;

					tapeOneWidthOverTotalWidth = tempOne.width / totalWidth;
					if (Math.abs(tempZero.y - tempOne.y) > yTolerance)
						continue;
					// System.out.println("less than 50");
					if (Math.abs(tapeZeroWidthOverTapeOneWidth
							- TAPE_WIDTH_TAPE_WIDTH_RATIO) > tapeOneWidthOverTapeTwoWidthTolerance)
						continue;
					// System.out.println("not same width ratio");
					if (Math.abs(tapeZeroWidthOverTotalWidth
							- TAPE_WIDTH_TOTAL_WIDTH_RATIO) > tapeWidthOverTotalWidthTolerance)
						continue;
					// System.out.println("good same box-total ratio");
					if (Math.abs(tapeOneWidthOverTotalWidth
							- TAPE_WIDTH_TOTAL_WIDTH_RATIO) > tapeWidthOverTotalWidthTolerance)
						continue;
					// System.out.println("good same box-total ratio 2 ");
					if (Math.abs(interiorWidthOverTotalWidth
							- INTERIOR_TOTAL_WIDTH_RATIO) > interiorWidthOverTotalWidthTolerance)
						continue;
					// System.out.println("interior width good");
					twoPiecesOfTape.add(tempZero);
					twoPiecesOfTape.add(tempOne);
					// System.out.println("left counter" + leftCounter);
					// System.out.println("right counter" + rightCounter);
					// System.out.println("interior width " + interiorWidth);
					// System.out.println("total width " + totalWidth);
					System.out.println("size of found tape " + twoPiecesOfTape.size());
					System.out.println(("my width " + tempZero.width));
					System.out.println(("my hieght " + tempZero.height));
					isFound = true;
					break;
				}
				if (isFound)
					break;
			}

		}
		return twoPiecesOfTape;
	}

	/**
	 * This boolean function checks to see if two rectangles are 
	 * overlapping one another
	 *
	 * @return true if there is an intersection between those two rectangles
	 */
	protected boolean checkForIntersection(Rect zero, Rect one)
	{
		return !(one.x > zero.x + zero.width);

	}

	/**
	 * This function resets all of the information we grab during the image
	 * processing.
	 */
	private synchronized void resetAll()
	{
		findContoursOutput.clear();
		filteredContoursOutput.clear();
		m_pixelDifference = 0;
	}

	
	/**
	 * This class runs the main processing thread found earlier in the
	 * VisionProcessor2017 class It is needed to run threads
	 * 
	 * @author Colby McKinley
	 *
	 */
	public class RunnableVisionProcessor implements Runnable
	{

		VisionProcessorPI m_Run;

		RunnableVisionProcessor(VisionProcessorPI vp_2017)
		{
			m_Run = vp_2017;
		}

		@Override
		public void run()
		{
			m_Run.processor();

		}
	}
}
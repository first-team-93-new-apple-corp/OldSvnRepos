package Vision;

public class VisionDataRegulator
{
	/**
	 * MAX_WIDTH_INCHES is the maximum inches a robot can travel left or right and still stay on the field
	 * in inches
	 */
	private final static int MAX_WIDTH_INCHES = 324; //27ft - the field width
	/**
	 * MAX_FORWARD is the maximum inches a robot can travel forward and still stay on the field
	 * in inches
	 */
	private final static int MAX_FORWARD_INCHES = 652; //54ft and 4 in - the field length
	/**
	 * REAL_MAX_FORWARD is a realistic maximum distance that the robot will travel forward
	 * in inches
	 */
	private final static int REAL_MAX_FORWARD = 50; //length of neutral zone
	/**
	 * REAL_MAX_CENTER is a realistic maximum distance in inches that the robot will travel to center itself
	 * in inches
	 */
	private final static int REAL_MAX_CENTER = 40;
	/**
	 * MAX_TAPE_WIDTH is the maximum width a piece of tape can be
	 * in pixels
	 */
	private final static int MAX_TAPE_WIDTH = 240;//half the screen
	/**
	 * MAX_TAPE_HEIGHT is the maximum height a piece of tape can be
	 * in pixels
	 */
	private final static int MAX_TAPE_HEIGHT = 640;//hieght of screen
	/**
	 * REAL_TAPE_WIDTH_MAX is this realistic maximum width that two pieces of tape can be
	 * and both can still be seen
	 * in pixels
	 */
	private final static int REAL_TAPE_WIDTH_MAX = 90;
	/**
	 * REAL_TAPE_HEIGH_MAX is this realistic maximum height that two pieces of tape can be
	 * and both can still be seen
	 * in pixels
	 */
	private final static int REAL_TAPE_HEIGHT_MAX = 225;
	/**
	 * REAL_TAPE_WIDTH_MIN is the realistic minimum of a tape's width, it is set up for 20ft away
	 * in pixels 
	 */
	private final static int REAL_TAPE_WIDTH_MIN = 5;
	/**
	 * REAL_TAPE_WIDTH_MIN is the realistic minimum of a tape's height, it is set up for 20ft away
	 * in pixels 
	 */
	private final static int REAL_TAPE_HEIGHT_MIN = 13;
	private double forwardDistance = 0.0;
	private double centerDistance = 0.0;
	private double tapeWidth = 0.0;
	private double tapeHeight = 0.0;
	private double score = 0.0;
	/**
	 * This function checks to make sure the data is within the maximum bounds
	 * @param leftRightDistance the distance left or right wanted to travel, in inches 
	 * @return if the inputed value is in bounds
	 */
	public boolean checkLeftRightBounds(double leftRightDistance)
	{
		return leftRightDistance < MAX_WIDTH_INCHES;
	}
	/**
	 * This function checks to make sure the data is within the maximum bounds
	 * @param forwardDistance the distance forward wanted to travel, in inches 
	 * @return if the inputed value is in bounds
	 */
	public boolean checkFowardBounds(double forwardDistance)
	{
		return forwardDistance < MAX_FORWARD_INCHES;
	}
	/**
	 * This function sets all the variables needed to determine the quality of the data calculated
	 * @param disFor the distance foward, in inches
	 * @param disCen the distance to center, in inches
	 * @param tWidth the tape width, in pixels
	 * @param tHeight the tape height, in pixels
	 */
	public void setQualtityControlVariables(double disFor, double disCen, double tWidth, double tHeight)
	{
		forwardDistance = disFor;
		centerDistance = disCen;
		tapeWidth = tWidth;
		tapeHeight = tHeight;
	}
	/**
	 * This function determines a quality score for the data.  It is in the form of a percentage
	 */
	private void setQualityScore()
	{
		final int NUMBER_OF_TESTS = 4;
		if(checkLeftRightBounds(centerDistance))
		{
			if(centerDistance < REAL_MAX_CENTER)
			{
				score +=1;
			}
			else
			{
				score+=.25;
			}
		}
		if(checkFowardBounds(forwardDistance))
		{
			if(forwardDistance < REAL_MAX_FORWARD)
			{
				score +=1;
			}
			else
			{
				score+=.25;
			}
		}
		
		if(tapeWidth < MAX_TAPE_WIDTH)
		{
			if((tapeWidth < REAL_TAPE_WIDTH_MAX)&&(tapeWidth > REAL_TAPE_WIDTH_MIN))
			{
				score += 1;
			}
			else
			{
				score +=0.25;
			}
		}
		
		if(tapeHeight< MAX_TAPE_HEIGHT)
		{
			if((tapeHeight < REAL_TAPE_HEIGHT_MAX)&&(tapeHeight > REAL_TAPE_HEIGHT_MIN))
			{
				score += 1;
			}
			else
			{
				score +=0.25;
			}
		}
		score /= NUMBER_OF_TESTS;
			
			
	}
	/**
	 * This function returns the the quality score determined
	 * @return the quality score of the data
	 */
	public double getScore()
	{
		setQualityScore();
		return score;
	}
	
//	public boolean getReadyStatus()
//	{
//		return readyToSend;
//	}
//	
//	public void setReadyStatus(boolean status)
//	{
//		readyToSend = status;
//	}
}
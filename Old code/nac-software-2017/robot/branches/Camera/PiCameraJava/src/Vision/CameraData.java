package Vision;

import Vision.VisionProcessorPI.ShipSide_t;

/**
 * This class holds the encoder ticks for the robot so it 
 * can position itself to place a gear
 * @author Colby McKinley
 *
 */
public class CameraData
{
	/**
	 * m_inchesLeftRight stores the value in inches that a robot
	 * needs to travel left or right to be center
	 */
	private double m_inchesLeftRight = 0.0;
	
	/**
	 * DIAMETER is the a robot's driving wheel's diameter in inches
	 */
	private final double DIAMETER = 3.25;
	/**
	 * CIRCUMPRANCE is the robot's driving wheel's circumprance in inches
	 */
	private final double CIRCUMPRANCE = Math.PI * DIAMETER;
	/**
	 * m_inchesFoward is the driving distance that the robot must travel
	 * in the forward direction to place a gear
	 */
	private double m_inchesForward = 0.0;
	/**
	 * currentSide this the currentSide the robot is on relative to the peg
	 */
	ShipSide_t currentSide = null;
	/**
	 * m_qualityScore is the quality of that that was collected, based on percentage
	 */
	private double m_qualityScore = 0.0;
	/**
	 * This is the proper constructor when using this class to store data
	 */
	public CameraData()
	{
	
	}
	/**
	 * This constructor is used for testing purposes only
	 * @param inchesLeftRight how many inches a robot must move left or right to center itself
	 * @param side the side the robot is on relative to the peg
	 * @param inchesForwad how many inches a robot must drive forward to place a peg
	 */
	public CameraData(double inchesLeftRight, ShipSide_t side, double inchesForwad)
	{
		m_inchesLeftRight = inchesLeftRight;
		currentSide = side;
		m_inchesForward = inchesForwad;
	}

	/**
	 * This function is how to set data that was collected.  It should be used by only having on instance
	 * of this class then constantly writing over data and sending it back.  This helps limits memory usage.
	 * 
	 * @param inchesLeftRight how many inches a robot must move left or right to center itself
	 * @param side the side the robot is on relative to the peg
	 * @param inchesForwad how many inches a robot must drive forward to place a peg
	 * @param score the quality of the data being transmitted
	 */
	public void setAll(double inchesLeftRight, ShipSide_t side, double inchesForwad, double score)
	{
		m_inchesLeftRight = inchesLeftRight;
		currentSide = side;
		m_inchesForward = inchesForwad;
		m_qualityScore = score;
	}

	/**
	 * This function converts inches into encoder ticks
	 * @param inches the inches desired to drive
	 * @return the encoder ticks the inches corresponds to
	 */
	private double convertInchesEncoderTicks(double inches)
	{
		return inches * 360 / CIRCUMPRANCE;
	}
	/**
	 * This function is by the regulator for determine the quality of the data
	 * @return the inches left or right to center the robot
	 */
	public double getLeftRightDistanceInches()
	{
		return m_inchesLeftRight;
	}
	/**
	 * This function is by the regulator for determine the quality of the data
	 * 
	 * @return the inches forward the robot needs to go to place a gear
	 */
	public double getFowardDistanceInches()
	{
		return m_inchesForward;
	}
	/**
	 * This function determines if the data should get negated and returns the new value
	 * @return a vector quantity for centering the robot, left is positive and right is negative
	 * it is in encoder ticks
	 */
	public double getLeftRightDistance()
	{
		if (currentSide == ShipSide_t.SHIP_LEFT)
		{
			return convertInchesEncoderTicks(m_inchesLeftRight);
		} 
		else if (currentSide == ShipSide_t.SHIP_RIGHT)
		{
			return -convertInchesEncoderTicks(m_inchesLeftRight);
		}
		return 0;
	}
	/**
	 * This function is used for getting the ticks forward a robot wants to travel
	 * @return the ticks to drive forward
	 */
	public double getForwardDistance()
	{
		return convertInchesEncoderTicks(m_inchesForward);
	}
	/**
	 * This function returns the quality score of the data
	 * @return the quality score of the data, in percentage
	 */
	public double getScore()
	{
		return m_qualityScore;
	}
}
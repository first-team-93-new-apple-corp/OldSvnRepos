package Vision;

/**
 * This class is for storing the camera properties. It is primarily used for
 * having a convienct place to store values.
 *
 * @author Colby McKinley
 *
 */
public class Team93Cam
{
	String m_camName = "";
	int m_camPort = 0;
	int m_width = 0;
	int m_height = 0;

	/**
	 *
	 * @param camName
	 *            a unique name for the camera
	 * @param camPort
	 *            the port the camera is plug into on the roborio
	 * @param width
	 *            the image width that the camera should capture
	 * @param height
	 *            the image height the camera should capture
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
	 * 
	 * @return the name of the camera in the form of a string
	 */
	public String getCamName()
	{
		return m_camName;
	}

	/**
	 * Returns the USB port that the camera is plugged into
	 * 
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
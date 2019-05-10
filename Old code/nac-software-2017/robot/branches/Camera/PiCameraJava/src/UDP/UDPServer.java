package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import Vision.CameraData;
import Vision.VisionProcessorPI;

/**
 * 
 * @author Colby McKinley
 *
 */
public class UDPServer {
	
	//very crude UDP sender code
	static String data = "";
	final static double threshold = 2;
	/**
	 * This function sets the data that needs to be sent
	 * @param dataClass
	 */
	public static void setData(CameraData dataClass)
	{
		data = String.valueOf(dataClass.getForwardDistance()) + ","  + String.valueOf(dataClass.getLeftRightDistance()) + "," + String.valueOf(dataClass.getScore());	
	}
	/**
	 * This function sends the data to the robo rio
	 */
	public static void send()
	{
		int port = 5005;
		String roboRioIP = "10.0.93.2";
		InetAddress address = null;
		try{
			address = InetAddress.getByName(roboRioIP);
			DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, address,port);
			DatagramSocket socket = new DatagramSocket();
			socket.send(packet);
			socket.close();
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}

}

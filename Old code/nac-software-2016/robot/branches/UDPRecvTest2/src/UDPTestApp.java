import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;

/**
 * 
 */

/**
 * @author NAC Controls
 *
 */
public class UDPTestApp
{

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		
	 DatagramSocket cameraReceiver = null;
	 System.out.println("Waiting for data.");
		try
		{
			cameraReceiver = new DatagramSocket(5800);
			cameraReceiver.setSoTimeout(1);
		} 
		catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		while(true)
		{
			byte[]  buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			String packetstr = null;
			try
			{
				if (cameraReceiver != null)
				{
					cameraReceiver.receive(packet);
					System.out.println("Received DataGram!");
					packetstr = new String(packet.getData(),0,packet.getLength());
					System.out.println(packetstr);
				}
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			// Process new data
			if (packetstr != null)
			{
				// do work here
				final int len = 4;
				StringTokenizer st = new StringTokenizer (packetstr, ",");
				double[] cameraData = new double[len];
				int i = 0;
				System.out.println();
				while ((st.hasMoreTokens()) && i < len)
				{
					String tok = st.nextToken();
					// is tok a number
					double dtok = 0.0;
					try
					{
						dtok = Double.valueOf(tok);
						cameraData[i] = dtok; 
						i++;
						System.out.println(dtok);
					} 
					catch (NumberFormatException e) 
					{
						// not a number
						System.out.println("Not a Number!");
					}
					
				}
				
				
			}
			
			
			try
			{
				Thread.sleep(10);
			} 
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}

		}
	//cameraReceiver.close();
	}//main
	
	
}//class

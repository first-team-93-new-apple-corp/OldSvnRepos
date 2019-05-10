import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 
 */

/**
 * @author Admin93
 *
 */
public class UDPTxApp 
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) 
			throws IOException {
		// TODO Auto-generated method stub
		
		// borrowing code from http://stackoverflow.com/questions/10556829/sending-and-receiving-udp-packets-using-java
		String myMessage = new String ("this is annoyingggggg");
		byte [] buf = myMessage.getBytes();
		byte [] IP={10,1,-15,55};
		InetAddress address = InetAddress.getByAddress(IP);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 5800);
		DatagramSocket datagramSocket = new DatagramSocket();
		datagramSocket.send(packet);
		datagramSocket.close();
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}

}

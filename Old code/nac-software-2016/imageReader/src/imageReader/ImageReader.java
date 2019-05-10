package imageReader;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class grabs an image from the camera and displays it.
 * @author NAC Controls
 *
 */
public class ImageReader{


	private int screenWidth = 500;
	private int screenHeight = 200;
	Timer m_timer = new Timer();
	private int m_waitTime = 20 *1000;
	private String m_imageLocation ="https://upload.wikimedia.org/wikipedia/commons/7/74/4th_Ring_road.jpg";
    public ImageReader() 
    {
        
    }
    
    private class DisplayTask extends TimerTask {
    	private ImageReader m_reader;
    	public DisplayTask(ImageReader myReader){
    		m_reader = myReader;
    	}
    	public void run(){
    		try {
    			m_reader.displayImage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    
    
    public void displayImage()throws IOException
    {
    	BufferedImage img = ImageIO.read(new URL(m_imageLocation));
        ImageIcon scaledImage = new ImageIcon(img.getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT));
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(screenWidth,screenHeight);
        JLabel lbl=new JLabel();
        lbl.setIcon(scaledImage);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    }
    
    public void start()
    {
//    	m_timer.cancel();
//    	m_timer.purge();
    	DisplayTask myTask = new DisplayTask(this);
    	m_timer.scheduleAtFixedRate(myTask, m_waitTime, m_waitTime);
    }
}
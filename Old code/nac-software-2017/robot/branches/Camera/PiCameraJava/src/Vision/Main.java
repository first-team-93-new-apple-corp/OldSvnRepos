package Vision;

import org.opencv.core.Core;

public class Main
{
	public static void main(String args[])
    {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	VisionProcessorPI vp_pi = new VisionProcessorPI();
    	vp_pi.initialize();
    }

}

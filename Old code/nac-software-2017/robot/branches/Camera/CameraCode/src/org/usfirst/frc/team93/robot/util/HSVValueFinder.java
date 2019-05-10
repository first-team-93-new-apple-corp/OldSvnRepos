//package org.usfirst.frc.team93.robot.util;
//
//import org.opencv.core.*;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.videoio.VideoCapture;
//
//public class HSVValueFinder {
//	VideoCapture capture;
//	int hueLow = 0;
//	int hueHight = 256;
//	int saturationLow = 0;
//	int satureationHigh = 256;
//	int valueLow = 0;
//	int valueHigh = 256;
//	Mat hsv = new Mat();
//	Mat feed = new Mat();
//	Mat threshold = new Mat();
//	Imshow feed_view;// = new Imshow("feed");
//	Imshow thresh_view;// = new Imshow("hsv threshed");
//	public void init()
//	{
//		Imshow feed_view = new Imshow("feed");
//		Imshow thresh_view = new Imshow("hsv threshed");
//		capture.open(0);
//	}
//	public void process()
//	{
//		capture.read(feed);
//		feed_view.show(feed);
//		Imgproc.cvtColor(feed, hsv, Imgproc.COLOR_BGR2HSV);
//		Core.inRange(hsv, new Scalar(hueLow, saturationLow, valueLow), new Scalar(hueHight, satureationHigh, valueHigh), threshold);
//		thresh_view.show(threshold);
//	}
//}

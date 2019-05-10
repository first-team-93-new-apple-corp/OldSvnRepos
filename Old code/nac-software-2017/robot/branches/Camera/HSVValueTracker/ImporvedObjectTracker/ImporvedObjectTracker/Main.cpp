#include "Main.h"

#include <sstream>
#include <string>
#include <iostream>

#include "opencv\highgui.h"
#include "opencv\cv.h"


using namespace cv;
using namespace std;

const int FRAME_HEIGHT = 640;
const int FRAME_WIDTH = 480;

int hueLow = 0;
int hueHight = 256;
int saturationLow = 0;
int satureationHigh = 256;
int valueLow = 0;
int valueHigh = 256;
const string trackbarWindowName = "Trackbars";
void on_trackbars(int,void*)
{

}
void createTrackbars()
{
	namedWindow(trackbarWindowName);
	char TrackbarName[50];
	sprintf(TrackbarName, "Hue Low", hueLow);
	sprintf(TrackbarName, "Hue High", hueHight);
	sprintf(TrackbarName, "Saturation Low", saturationLow);
	sprintf(TrackbarName, "Saturation High", satureationHigh);
	sprintf(TrackbarName, "Value Low", valueLow);
	sprintf(TrackbarName, "Value High", valueHigh);
	createTrackbar("Hue Low", trackbarWindowName, &hueLow, 255, on_trackbars);
	createTrackbar("Hue High", trackbarWindowName, &hueHight, 255, on_trackbars);
	createTrackbar("Saturation Low", trackbarWindowName, &saturationLow, 255, on_trackbars);
	createTrackbar("Saturation High", trackbarWindowName, &satureationHigh, 255, on_trackbars);
	createTrackbar("Value Low", trackbarWindowName, &valueLow, 255, on_trackbars);
	createTrackbar("Value High", trackbarWindowName, &valueHigh, 255, on_trackbars);
}
string intToString(int number)
{
	stringstream ss;
	ss << number;
	return ss.str();
}
void morphOps(Mat &thresh)
{
	int erodeX = 3, erodeY = 3;
	int dilateX = 8, dilateY = 8;
	Mat erodeElement = getStructuringElement(MORPH_RECT,Size(erodeX, erodeY));
	Mat dilateElement = getStructuringElement(MORPH_RECT, Size(dilateX, dilateY));

	erode(thresh, thresh, erodeElement);
	erode(thresh, thresh, erodeElement);
	dilate(thresh, thresh, dilateElement);
	dilate(thresh, thresh, dilateElement);
}

int main()
{
	bool testColorValues = true;
	Mat feed;
	Mat hsv;
	Mat threshold;
	VideoCapture capture;
	createTrackbars();
	int x = 0 , y = 0;
	capture.open(0);
	capture.set(CV_CAP_PROP_FRAME_WIDTH, FRAME_WIDTH);
	capture.set(CV_CAP_PROP_FRAME_HEIGHT, FRAME_HEIGHT);
	while (true)
	{
		capture.read(feed);
		imshow("Capture", feed);
		cvtColor(feed, hsv, COLOR_BGR2HSV);
		inRange(hsv, Scalar(hueLow, saturationLow, valueLow), Scalar(hueHight, satureationHigh, valueHigh), threshold);
		morphOps(threshold);
		imshow("Thresholded Image", threshold);
		waitKey(30);
		createTrackbars();
	}
	return 0;
}









Main::Main()
{
}
Main::~Main()
{
}

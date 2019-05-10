package org.usfirst.frc.team93.robot.Utilities;

import java.util.ArrayList;

/**
*
* @author New Apple Corps Team 93 Controls
*/
public class Interpolate
{
	protected static ArrayList<Double> rpmSetPoints = new ArrayList<Double>();
	protected static ArrayList<Double> distanceSetPoints = new ArrayList<Double>();
	protected static ArrayList<Double> angleSetPoints = new ArrayList<Double>();

	private static double outputRPM = 0.0;
	private static double outputAngle = 0.0;
	private static double tempZero;
	private static double tempOne;

	String distance = "C://Users//NAC Controls//Desktop//file//distance.txt";
	String angle = "C://Users//NAC Controls//Desktop//file//angle.txt";
	String rpm = "C://Users//NAC Controls//Desktop//file//rpm.txt";

	FileInput distanceData = new FileInput(distance);
	FileInput angleData = new FileInput(angle);
	FileInput rpmData = new FileInput(rpm);

	public Interpolate()
	{
		rpmData.load();
		distanceData.load();
		angleData.load();
		setPoints(rpmData.getList(), distanceData.getList(), angleData.getList());
	}

	public void setPoints(ArrayList<Double> rpmToSet, ArrayList<Double> distanceToSet, ArrayList<Double> angleToSet)
	{
		if (rpmToSet.size() == distanceToSet.size() && rpmToSet.size() == angleToSet.size())
		{
			for (int i = 0; i < distanceToSet.size(); i++)
			{
				rpmSetPoints.add(i, rpmToSet.get(i));
				distanceSetPoints.add(i, distanceToSet.get(i));
				angleSetPoints.add(i, angleToSet.get(i));
			}
		}
	}

	public static void getInterpolation(double searchDistance)
	{
		// x is distance, y is rpms

		if (distanceSetPoints.size() == rpmSetPoints.size() && distanceSetPoints.size() == angleSetPoints.size())
		{
			int sizeTest = distanceSetPoints.size();
			if (sizeTest > 1)
			{
				sort();

				// interpolate Distance -- RPMs
				for (int i = 0; distanceSetPoints.get(i) < searchDistance && i < distanceSetPoints.size(); i++)
				{

					if (i >= 1)
					{
						outputRPM = rpmSetPoints.get(i) + ((rpmSetPoints.get(i + 1) - rpmSetPoints.get(i))
								* ((searchDistance - distanceSetPoints.get(i))
										/ (distanceSetPoints.get(i + 1) - distanceSetPoints.get(i))));
						outputAngle = angleSetPoints.get(i) + ((angleSetPoints.get(i + 1) - angleSetPoints.get(i))
								* ((searchDistance - distanceSetPoints.get(i))
										/ (distanceSetPoints.get(i + 1) - distanceSetPoints.get(i))));
					}
				}
			}
		}
	}

	public static void sort()
	{
		for (int i = 1; i < distanceSetPoints.size(); i++)
		{
			for (int k = i; k > 0 && distanceSetPoints.get(k) < distanceSetPoints.get(k - 1); k--)
			{
				tempZero = distanceSetPoints.get(k);
				tempOne = distanceSetPoints.get(k - 1);
				distanceSetPoints.set(k, tempOne);
				distanceSetPoints.set(k - 1, tempZero);

				tempZero = rpmSetPoints.get(k);
				tempOne = rpmSetPoints.get(k - 1);
				rpmSetPoints.set(k, tempOne);
				rpmSetPoints.set(k - 1, tempZero);

				tempZero = angleSetPoints.get(k);
				tempOne = angleSetPoints.get(k - 1);
				angleSetPoints.set(k, tempOne);
				angleSetPoints.set(k - 1, tempZero);

			}
		}
	}

	public static double getAngle(double distance)
	{
		getInterpolation(distance);
		return outputAngle;
	}

	public static double getRPM(double distance)
	{
		getInterpolation(distance);
		return outputRPM;
	}

}

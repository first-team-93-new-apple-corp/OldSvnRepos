package org.usfirst.frc.team93.robot.Utilities;

import java.util.ArrayList;
import java.util.Collections;

public class Interpolate
{
	protected ArrayList<Double> rpmSetPoints = new ArrayList<Double>();
	protected ArrayList<Double> distanceSetPoints = new ArrayList<Double>();
	protected ArrayList<Double> angleSetPoints = new ArrayList<Double>();

	private double outputRPM = 0.0;
	private double outputAngle = 0.0;
	private double tempZero;
	private double tempOne;

	String distance = "C:\\Users\\NAC Controls\\Desktop\\file\\distance.txt";
	String angle = "C:\\Users\\NAC Controls\\Desktop\\file\\angle.txt";
	String rpm = "C:\\Users\\NAC Controls\\Desktop\\file\\rpm.txt";

	FileInput distanceData = new FileInput(distance);
	FileInput angleData = new FileInput(angle);
	FileInput rpmData = new FileInput(rpm);

	public Interpolate()
	{

	}

	public void setPoints(ArrayList<Double> rpmToSet, ArrayList<Double> distanceToSet, ArrayList<Double> angleToSet)
	{
		if (rpmToSet.size() == distanceToSet.size() && rpmToSet.size() == angleToSet.size())
		{
			for (int i = 0; i < rpmSetPoints.size(); i++)
			{
				rpmSetPoints.add(i, rpmToSet.get(i));
				distanceSetPoints.add(i, distanceToSet.get(i));
				angleSetPoints.add(i, angleToSet.get(i));
			}
		}
	}

	public void getInterpolation(double searchDistance)
	{
		// x is distance, y is rpms

		int indexLesser;

		if (distanceSetPoints.size() == rpmSetPoints.size() && distanceSetPoints.size() == angleSetPoints.size())
		{
			if (distanceSetPoints.size() > 1)
			{
				// sort();
				Collections.sort(distanceSetPoints); // be more sophisticated
				Collections.sort(rpmSetPoints);
				Collections.sort(angleSetPoints);

				// interpolate Distance -- RPMs
				for (int i = 0; distanceSetPoints.get(i) < searchDistance && i < distanceSetPoints.size(); i++)
				{

					if (i > 1)
					{
						indexLesser = i - 1;
						outputRPM = rpmSetPoints.get(indexLesser) + (rpmSetPoints.get(i) - rpmSetPoints.get(indexLesser)
								* (searchDistance - distanceSetPoints.get(indexLesser) / distanceSetPoints.get(i)
										- distanceSetPoints.get(indexLesser)));
						outputRPM = angleSetPoints.get(indexLesser) + (angleSetPoints.get(i) - angleSetPoints
								.get(indexLesser)
								* (searchDistance - distanceSetPoints.get(indexLesser) / distanceSetPoints.get(i)
										- distanceSetPoints.get(indexLesser)));
					}
				}
			}
		}
	}

	public void sort()
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

	public double getAngle()
	{
		return outputAngle;
	}

	public double getRPM()
	{
		return outputRPM;
	}

}

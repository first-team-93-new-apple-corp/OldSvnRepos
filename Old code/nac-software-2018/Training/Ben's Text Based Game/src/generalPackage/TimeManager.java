package generalPackage;

public class TimeManager
{
	/**
	 * Waits for the given value in ms
	 */
	public void pause(double milliseconds)
	{
		try
		{
			Thread.sleep((long) milliseconds);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void printDelayed(String s, double msBetweenChar)
	{
		for (int i = 0; i < s.length(); i++)
		{
			System.out.print(s.charAt(i));
			pause(msBetweenChar);
		}
	}
}

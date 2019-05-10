import java.util.concurrent.TimeUnit;

public class TypeConverter {
	
	public static int BooleanToInteger(boolean b)
	{
		return (b ? 1 : 0);
	}
	public static boolean IntegerToBoolean(int i)
	{
		return(i > 0.5);
	}
	public static boolean StringToBoolean(String s)
	{
		return IntegerToBoolean(Integer.parseInt(s));
	}
	public static int StringToInt(String s)
	{
		int i = 0;
		try
		{
			i = Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Attempted conversion of string " + s + "to integer failed.");
		}
		return i;
	}
	public static long StringToLong(String s)
	{
		long l = 0;
		try
		{
			l = Long.parseLong(s);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Attempted conversion of string " + s + "to long failed.");
		}
		return l;
	}
	public static String join(String[] s, String delim)
	{
		String str = "";
		for (int i = 0; i < s.length; i++)
		{
			str += s[i];
			if (i != s.length - 1)
			{
				str += delim;
			}
		}
		return str;
	}
	public static String TimeToString(long ms)
	{
		String str = "";
		long hours = TimeUnit.MILLISECONDS.toHours(ms);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(ms) -  
				TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(ms) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms));
		
		if (hours > 0)
		{
			str += hours + " hour";
			if (hours > 1)
			{
				str += "s";
			}
		}
		
		if (minutes > 0)
		{
			if (hours > 0)
			{
				str += ", ";
			}
			str += minutes + " minute";
			if (minutes > 1)
			{
				str += "s";
			}
		}
		
		if (seconds > 0)
		{
			if (minutes > 0)
			{
				str += ", ";
			}
			str += seconds + " second";
			if (seconds > 1)
			{
				str += "s";
			}
		}
		
		if (TimeUnit.MILLISECONDS.toSeconds(ms) == 0)
		{
			str = "no time.";
		}
		
		return str;
	}
}

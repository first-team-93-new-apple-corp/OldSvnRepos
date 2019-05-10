import java.util.ArrayList;
import java.util.StringTokenizer;

public class ToSeperateDoubles {
	static ArrayList<Double> A  = new ArrayList<Double>();
	static ArrayList<Double> B  = new ArrayList<Double>();
	static ArrayList<Double> C  = new ArrayList<Double>();
	static ArrayList<String> D = new ArrayList<String>();
	static int i = 0;
	public static void divideAmongArrays(String input)
	{
		D.clear();
		StringTokenizer st = new StringTokenizer(input, ",");
	     while (st.hasMoreTokens()) {
	    	 D.add (st.nextToken());
	     }
	     A.add(Double.parseDouble(D.get(0)));
	     B.add(Double.parseDouble(D.get(1)));
	     C.add(Double.parseDouble(D.get(2)));
	    System.out.println("A" + (i + 1) + " " + A.get(i));
	    System.out.println("B" + (i + 1) + " " + B.get(i));
	    System.out.println("C" + (i + 1) + " " + C.get(i));
	    i++;
	}
}

/*
 * 
 */
package stringtokenizertest;

import java.util.StringTokenizer;

/**
 * @author NAC Controls
 *
 */
public class StringTokenizerTestMain
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
				
		String file = "aaaaaa, bbbbbb, cccc, ddddddd";
		StringTokenizer st = new StringTokenizer (file, ",");
		
		//System.out.println("eeeeeee");
		while (st.hasMoreTokens())
		{
			System.out.println(st.nextToken());
		}
		
		/*System.out.println("fffffffff");
		/StringTokenizer st2 = new StringTokenizer(file, ",");

		while (st2.hasMoreElements()) {
			System.out.println(st2.nextElement());
		}
		*/
		
	}

}

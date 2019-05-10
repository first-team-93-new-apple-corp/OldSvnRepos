import java.util.*;


public class zoomain {

	public static void main(String[] args) {
		ArrayList<animal> zoo = new ArrayList<animal>();

		Scanner scan = new Scanner  (System.in);
		String key;





		key = scan.nextLine();

		while(!key.equals("e")) {
			if (key.equals("l")) {
				zoo.add (new Lion());


			}
			if (key.equals("m")) {
				zoo.add (new Monkey());


			}
			if (key.equals("b")) {
				zoo.add (new bat());


			}
			if (key.equals("s")) {
				for (int i = 0; i < zoo.size(); i++) {


				}


			}
		}
	}
}
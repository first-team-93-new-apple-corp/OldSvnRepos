public class Monkey implements Animal {
	/**
	 * awarenes is true if daytime awareness is false if nighttime
	 */
	private boolean awareness; 

	public void Speak() {
		if (awareness == true) {
			System.out.println("Oo! Oo! Ah! Ah!");
		} else {

		}

	}


	public void TimeOfDay(boolean time) {
		awareness = time;
		System.out.println("Time of Day is: "+awareness);

	} 
}

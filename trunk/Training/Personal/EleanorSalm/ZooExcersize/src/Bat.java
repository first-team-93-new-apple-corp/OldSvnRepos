public class Bat implements Animal {
private boolean awareness; 
	
	public void Speak() {
		if (awareness == false) {
			System.out.println("SKREETCH!");
		} else {
			
		}
		
	}
	
	public void TimeOfDay(boolean time) {
		awareness = time;
		
	} 
}

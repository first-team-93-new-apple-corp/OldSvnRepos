
public class Lion implements Animal {
	private boolean awareness; 
	
	public void Speak() {
		
		if (awareness == true) {
			System.out.println("ROAR!");
		} else {
			
		}
		
		
	}
	
	
	public void TimeOfDay(boolean time) {
		awareness = time;
		
	} 
}



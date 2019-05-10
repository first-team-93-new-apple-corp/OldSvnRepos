import java.util.*;
public class Main {

	public static void main(String args[]) {

		ArrayList<Animal> array = new ArrayList<Animal>();


		Scanner sc = new Scanner(System.in);
		String typed; 
		boolean endings = true;
		boolean timeOfDay = true;

		while (endings) {
			typed = sc.nextLine();
			if (typed.equals("L")) {
				array.add(new Lion());

			} else if (typed.equals("M")) {
				array.add(new Monkey());
				
			} else if (typed.equals("B")) {
				array.add(new Bat());

			} else if (typed.equals("S")) {
				for(int i = 0; i < array.size(); i++) {
					Animal animal = array.get(i);
					animal.Speak();
				} 

			} else if (typed.equals("T")) {
				System.out.println(timeOfDay);
				timeOfDay = !timeOfDay; 
				for (int i = 0; i < array.size(); i++) {
					Animal animal = array.get(i);
					animal.TimeOfDay(timeOfDay);
				}
				
			} else if (typed.equals("E")) {
				endings = false;

			} 


		}



	}
}

import java.util.Scanner;

public class bhuwanCode1 {

	public static void main(String[] args) {
		System.out.println("Type 'start' to Begin");
		Scanner Start = new Scanner(System.in);
		String input = Start.nextLine();
		if(input.equals("start"))
		{
			System.out.println("You get a call at 2:00pm at night");
			System.out.println("Type Yes To Accept the call or No to decline the call.");
			input = Start.nextLine();
			System.out.println("");
		}
		
	}

}

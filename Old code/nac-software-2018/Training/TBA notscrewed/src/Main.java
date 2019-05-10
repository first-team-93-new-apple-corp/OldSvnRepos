
import java.util.Scanner;


public class Main
{//this is the first song choice
	public static void main(String[]args)
	{	System.out.println("TURN CAPS LOCK ON AND LEAVE IT ON");
		System.out.println("Only type words in caps, and only type words that you see in caps");
		System.out.println("LEST YOU DIE!!!!!");
		System.out.println("\n\n\n\n");
		System.out.println("A man comes up to you with a TIDE pod. He sreams for you to eat it.  What do you do? You can EAT, HIT, or RUN");
		Scanner scan=new Scanner(System.in);
		String man=scan.next();
		switch(man) {
		case "EAT":
			System.out.println("Eat?  Really? Okay then, what happens now is your own doing ;)");
			psycho();
			break;
		default:
			System.out.println("Told you idiot");
			System.exit(0);
		}
			
		}
	//This is if the Tide pod gets eaten
	public static void psycho() {
		System.out.println("u get WOKE buy$ a burnin in yo throte. It the juul, *cough cough* pod");
		System.out.println("Evrythg round U all RAINBOW like, TASTE THE RAINBOW!!!!!!!!!!!!!!!!!!");
		System.out.println("U stubble and fall,  Help, U fallen and U cant get UP:(");
		System.out.println("An Elefant comes to U and asks if U want a ride   YES or NO?");
		Scanner scan=new Scanner(System.in);
		String fant=scan.next();
		switch(fant) {
		case "YES":
			System.out.print("'Clim on top buddy;)' When climing up U fall of and hit yo hed.  Lite goes fade to black and U get the");
			System.out.println("BLUE screen of DEATH!");
			fant(fant, fant);
			break;
		case "NO":
			System.out.println("What a donkey butt... I no like the people like U.  Goodbuy$$.  *Squish*");
		default:
			System.out.println("I warned you, now you are dead XDXDXDXDXDXDXDXDXDXDXDXDXDXDXDXDXDXDXDXDXD");
			System.exit(0);
		}
	}
	//this is if you become an elephant
	public static void fant(String name, String urlString){
		Scanner scan=new Scanner(System.in);
		String matrix=scan.next();
		System.out.println("I am vry BIG U thinking to yoself(ie)");
		System.out.println("U r the ELepHanT");
		System.out.println("A pooaaacherr is coming.  He shots at yo feat, but time pulls a Matrix.  ");
		System.out.println("Do you JUMP, DODGE, CHARGE at him, stand STILL, be a gangster and CATCH it");
		switch(matrix) {
		//put link below to site saying elephants cant jump
			case "JUMP":
				System.out.println("Elephants cant jump you fool");
				System.exit(0);
			case "STILL":
				System.out.println("What did you think that was going to do");
				System.out.println("Your dead moron");
				System.exit(0);
			case "CATCH":
				System.out.println("U hear he scream'Holy christ JESUS it JASON BOURNE' and he start too RUN ");
				System.out.println("Butt it to late fo him");
				System.out.println("You can either THROW it back or you can let them LIVE");
			String save=scan.next();
			switch(save) {
				case "LIVE":
					System.out.println("Compasion gets you killed fool");
					System.out.println("He shoots you again");
					System.exit(0);
				case "THROW":
					fantkill();
			}
		}
	}
	public static void fantkill(){
		//this is when
		System.out.println("It hit him.  Congrats, U a murdurer");
		System.out.println("have fun JK");
		System.out.println("Suddenly, you are standing over a body");
		System.out.println("There is blood coming out of his head, and you are holding a gun.  You have realized what you've done, but what will you do now?");
		System.out.println("You could CALL 911, but you could get arrested, you can try to HELP him, or you can just RUN");
		System.out.println("");
	}
	}




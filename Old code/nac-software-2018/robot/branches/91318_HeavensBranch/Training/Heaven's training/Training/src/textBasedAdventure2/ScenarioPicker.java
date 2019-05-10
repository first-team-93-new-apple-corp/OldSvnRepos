package textBasedAdventure2;

public class ScenarioPicker {
	public static boolean endGame = false;
	public static void pickScenario(){
		if(endGame==true) {
			System.out.println("Game over, if you wish to play again rerun the program");
			Main.playerType = false;
		}
		else if (Scenario4.needToFindAntedote == true) {
			String playerInput = Main.sc.nextLine();
			if(playerInput.equals("2") || playerInput.equals("4")) {
				System.out.println("you didn't find the antedote and died");
				endGame=true;
				Scenario2.scenario2done=true;
			}
			else if(playerInput.equals("3")) {
				System.out.println("you survived! pick a new cupboard by typing '2' or '4'");
				Main.playerType=true;
				Scenario2.scenario2done=false;
			}
		}
		else if (Scenario1.scenario1done==false) {
			while(Scenario1.scenario1done==false) {
				String playerInput = Main.sc.nextLine();
				if (playerInput.equals("1")) {
					Scenario1.flashlight();
					Main.playerType=true;
				}
				else if (playerInput.equals("2")) {
					Scenario1.noFlashlight();
					Main.playerType=true;
				}
				else if (playerInput.equals("3")) {
					Scenario1.jumpOutWindow();
					Main.playerType=true;
				}
				else {
					System.out.println("command not recognized");
					Main.playerType=true;
				}
			}
		}
		else if (Scenario1.scenario1done==true && Scenario1.pickFlashlight == false) {
			while(Scenario1.scenario1done==true) {
				String playerInput = Main.sc.nextLine();
				if (playerInput.equals("1")) {
					Scenario3.bandaid();
					Main.playerType=true;
				}
				else if (playerInput.equals("2")) {
					Scenario3.noBandaid();
					Main.playerType=true;
				}
				else {
					System.out.println("command not recognized");
					Main.playerType=true;
				}
			}
		}
		else if(Scenario3.scenario3done==true) {
			while(Scenario2.scenario2done==false) {
				String playerInput = Main.sc.nextLine();
				if (playerInput.equals("1")) {
					Scenario2.cupboard1();
					Main.playerType=true;
				}
				else if (playerInput.equals("2")) {
					Scenario2.cupboard2();
					Main.playerType=true;
				}
				else if (playerInput.equals("3")) {
					Scenario2.cupboard3();
					Main.playerType=true;
				}
				else if (playerInput.equals("4")) {
					Scenario2.cupboard4();
					Main.playerType=true;
				}
				else if (playerInput.equals("5")) {
					Scenario2.cupboard5();
					Main.playerType=true;
				}
				else if (playerInput.equals("6")) {
					Scenario2.cupboard6();
					Main.playerType=true;
				}
				else {
					System.out.println("command not recognized");
					Main.playerType=true;
				}
			}
		}
		else if(Scenario2.scenario2done==true && Scenario2.cupboard1==true) {
			while(Scenario2.scenario2done==true) {
				String playerInput = Main.sc.nextLine();
				if (playerInput.equals("1")) {
					Scenario4.magic();
					Main.playerType=true;
				}
				else if (playerInput.equals("2")) {
					Scenario4.noMagic();
					Main.playerType=true;
				}
				else {
					System.out.println("command not recognized");
					Main.playerType=true;
				}
			}
		}
		else if(Scenario2.scenario2done==true && Scenario2.cupboard2==true) {
			while(Scenario2.scenario2done==true) {
				String playerInput = Main.sc.nextLine();
				if (playerInput.equals("1")) {
					Scenario5.milk();
					Main.playerType=true;
				}
				else if (playerInput.equals("2")) {
					Scenario5.noMilk();
					Main.playerType=true;
				}
				else {
					System.out.println("command not recognized");
					Main.playerType=true;
				}
			}
		}
		else if(Scenario2.scenario2done==true && Scenario2.cupboard4==true) {
			while(Scenario2.scenario2done==true) {
				String playerInput = Main.sc.nextLine();
				if (playerInput.equals("1")) {
					Scenario7.portal();
					Main.playerType=true;
				}
				else if (playerInput.equals("2")) {
					Scenario7.noPortal();
					Main.playerType=true;
				}
				else {
					System.out.println("command not recognized");
					Main.playerType=true;
				}
			}
		}
	}	
}
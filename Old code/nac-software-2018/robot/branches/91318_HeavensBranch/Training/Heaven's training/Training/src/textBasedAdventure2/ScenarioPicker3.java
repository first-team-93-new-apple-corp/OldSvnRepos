package textBasedAdventure2;

public class ScenarioPicker3 {
	/*public static boolean endGame = false;
	public static void pickScenario(){
		if(endGame==true) {
			System.out.println("Game over, if you wish to play again rerun the program");
			Main.playerType = false;
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
		else if (Scenario2.scenario2done==false && Scenario3.scenario3done==true) {
			while(Scenario2.scenario2done==false) {
				String playerInput = Main.sc.nextLine();
				if (playerInput.equals("1")) {
					if (Scenario4.scenario4done==false) {
						Scenario2.cupboard1();
					}
					else {
						System.out.println("you have already opened this cupboard, select a new cupboard");
					}
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
		else if (Scenario3.scenario3done==false) {
			while(Scenario3.scenario3done==false) {
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
		else if (Scenario4.needToFindAntedote = true) {
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
			else {
				System.out.println("command not recognized");
			}
		}
		else if(Scenario2.scenario2done==true && Scenario6.scenario6done==true) {
			
		}
		else {
			
		}
	}
	*/
}

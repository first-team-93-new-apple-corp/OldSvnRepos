package TBA;
import java.util.Scanner;
public class TextBasedAdventure {
	/*Small goals:
	 * 1 Room
	 * 3 Commands:
	 	* Look
	 	* Get
	 	* Help
	* Game Policies
		* Surrounding Valid items to get
 	* Game State
 		* Player Inventory
 		* Room
 		* Room Objects
 	*/
	public static GameState gameState;
	public static ItemContainer itemContainer;
	public static ItemUser itemUser;
	public static void main(String args[])
	{
		
		gameState = new GameState();
		itemContainer = new ItemContainer();
		System.out.println("Welcome, Player. In this adventure, your controls are:");
		System.out.println("You can type 'look' in order to look around, or you can look at a specific item by adding the name of the item you want to look at after.");
		System.out.println("You can type 'grab' before the name of an item to pick it up.");
		// Add new game rules here
		System.out.println("And in order to continue to the next room, you type in 'advance'.");
		System.out.println("Type '1' to continue.");
		
		Scanner scanner = new Scanner(System.in);
		boolean tutorialFinished = false;
		while (tutorialFinished == false)
		{
			String input = scanner.nextLine();
			if (input.equals("1"))
			{
				tutorialFinished = true;
			}
			else
			{
				System.out.println("That is not a valid option. Type '1' to continue.");
			}
		}
		System.out.println("Good. Welcome to the game! \n");
		System.out.println(gameState.roomOne.m_Desc);
		while (true)
		{
			String input = scanner.nextLine();
			boolean isHandledLook = LookHandler.LookHandle(input, gameState);
			boolean isHandledGrab = GrabHandler.GrabHandle(input, gameState);
			boolean isHandledAdvance = RoomChange.RoomChange(input, gameState, itemContainer, itemUser);
			boolean isHandledUser = ItemUser.ItemUser(input, gameState);
			
			if(input.equals("bet"))
			{
				System.out.println("Go eat a Tide Pod, Terran!");
			}
		}
	}
}

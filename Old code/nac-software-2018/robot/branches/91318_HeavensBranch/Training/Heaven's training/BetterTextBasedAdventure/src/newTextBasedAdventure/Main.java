package newTextBasedAdventure;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		ScenarioTreeBuilder treeBuilder = new ScenarioTreeBuilder();
		Scenario currentScenario = treeBuilder.buildTree();
		Scanner userInput = new Scanner(System.in);
		
		while(true)
		{
			currentScenario.printDescription();
			currentScenario.printOptions();
			
			if(!currentScenario.hasChildren())
			{
				break;
			}

			int userChoice = userInput.nextInt();
			
			if(!currentScenario.isUserChoiceValid(userChoice))
			{
				System.out.println();
				System.out.println("Invalid selection");
				System.out.println();
				continue;
			}
		
			currentScenario = currentScenario.getNextScenario(userChoice);
			System.out.println();
		}

		userInput.close();
	}
	
}

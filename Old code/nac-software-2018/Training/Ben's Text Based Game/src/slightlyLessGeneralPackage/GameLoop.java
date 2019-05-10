package slightlyLessGeneralPackage;

import generalPackage.Input;
import generalPackage.TimeManager;

public class GameLoop
{
	public static TimeManager timeManager;
	public static Input input;
	
	public enum Mode
	{
		GAME_MENU, PASUE_MENU, EXPLORE, BATTLE
	}
	
	public void init()
	{
		timeManager = new TimeManager();
		input = new Input();
		
	}
	
	public void loop()
	{
		String s = input.getInput();
		if (s.equals("end"))
		{
			System.exit(1);
		}
		timeManager.printDelayed(s, 100);
		System.out.println("");
	}
}

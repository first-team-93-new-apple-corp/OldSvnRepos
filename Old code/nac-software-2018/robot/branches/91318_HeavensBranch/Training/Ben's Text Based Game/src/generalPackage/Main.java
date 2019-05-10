package generalPackage;

import slightlyLessGeneralPackage.GameLoop;

public class Main
{
	public static GameLoop gameLoop;
	
	public static void main(String[] args)
	{
		gameLoop = new GameLoop();
		gameLoop.init();
		while (true)
		{
			gameLoop.loop();
		}
	}
	
}
/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;

/**
 * TODO: Copy this class, and write a more intelligent ColbyPlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class ColbyPlayer extends GamePlayer {

	Random random_generator = new Random();
	private int turnNumber = 0;
	public int fireX = 0;
	public int fireY = 0;
	private int mapLength = 10;
	private int mapHeight = 10;
	double[][] myProbablityMap = new double[10][10];
	
	/**
	 */
	public ColbyPlayer() 
	{
		super();
		// TODO Auto-generated constructor stub
		generateMap();
	}

	public void generateMap()
	{
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				myProbablityMap[i][j] = 1;
			}
		}
//		myProbablityMap[0][0] = 1.0;
//		myProbablityMap[1][0] = 1.1;
//		myProbablityMap[2][0] = 1.5;
//		myProbablityMap[3][0] = 1.5;
//		myProbablityMap[4][0] = 1.5;
//		myProbablityMap[5][0] = 1.5;
//		myProbablityMap[6][0] = 1.5;
//		myProbablityMap[7][0] = 1.5;
//		myProbablityMap[8][0] = 1.1;
//		myProbablityMap[9][0] = 1.0;
	}
	
	/**
	 * Notification that a new match is about to start. 
	 */
	@Override
	public void notifyReset()
	{
		turnNumber = 0;
		generateMap();
	}
	
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#placeShips()
	 */
	/**
	 * Place your ships.  You must place exactly five.  One of each type.
	 */
	@Override
	public void placeShips() 
	{
		// TODO Required: place five ships, one of each type, with no collisions
		placeShip(new Ship(new Ship.GridCoord(0,0), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
		placeShip(new Ship(new Ship.GridCoord(0,2), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_BATTLESHIP));
		placeShip(new Ship(new Ship.GridCoord(0,4), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CRUISER));
		placeShip(new Ship(new Ship.GridCoord(0,6), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_SUBMARINE));
		placeShip(new Ship(new Ship.GridCoord(0,9), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_DESTROYER));
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#fireNow()
	 */
	/**
	 * It's your turn.  Fire your shot!  Inspect the result.
	 */
	@Override
	public void fireNow() 
	{
		int x = getFireX();
		int y = getFireY();
		FireResult result = fireShot(new Ship.GridCoord(x, y));;
		// TODO Required: Fire your shot.  Override this logic with your own.
			//x = random_generator.nextInt(10);
			//y = random_generator.nextInt(10);
		// Example: Fire at x, y
		if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
		{
			//System.out.println("We hit something!");
			// Note: result.ship_type has information that would be useful.
		}
		turnNumber++;
		setProbablity(x, y, 0);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#notifyOpponentShot()
	 */
	/**
	 * This is to notify you of your opponent's shot against your Ocean.  You can do something with 
	 * this, or not.
	 */
	@Override
	public void notifyOpponentShot(FireResult result) 
	{
		// You're not required to do anything here. 
	}
	
	private void getHighestProbablityCoords()
	{
		fireX = 0;
		fireY = 0;
		double heighest = 0;
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				if(myProbablityMap[i][j] > heighest)
				{
					heighest = myProbablityMap[i][j];
					fireX = i;
					fireY = j;
				}		
			}
		}
		//return greatest prob
		//if prob at tile is not 0,  proceed
		//if prob is greater than current max, set to new max
		//once going trough all tiles, return highest one.
	}
	
	private int getFireX()
	{
		getHighestProbablityCoords();
		return fireX;
	}
	private int getFireY()
	{
		getHighestProbablityCoords();
		return fireY;
	}
	private void setProbablity(int x, int y, int score)
	{
		myProbablityMap[x][y] = score;
	}
}

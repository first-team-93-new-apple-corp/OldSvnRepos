/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean.PlaceResult_t;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author evans.chen
 *
 */
public class SprayAndPrayPlayer extends GamePlayer {

	Random random_generator = new Random();
	
	public enum OceanStatus
	{
		HIT, MISS, UNKNOWN, NULL;
	}
	
	public enum State
	{
		HUNT, KILL, NULL;
	}
	
	OceanStatus[][] oceanMap = new OceanStatus[10][10];
	State state = State.HUNT;
	
	/**
	 * @param own_ocean
	 */
	public SprayAndPrayPlayer() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Notification that a new match is about to start. 
	 */
	@Override
	public void notifyReset()
	{
		clearOcean();
		state = State.HUNT;
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
		int x = random_generator.nextInt(10);
		int y = random_generator.nextInt(10);
		int o = random_generator.nextInt(2);
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_CARRIER)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_BATTLESHIP)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_CRUISER)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_SUBMARINE)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_DESTROYER)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
		}
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
		// TODO Required: Fire your shot.  Override this logic with your own.
		
		//Choose an x and y.
		Coordinate fireCoordinate = chooseFire();
		
		//fire!
		GameBoard.FireResult result = fire(fireCoordinate);
		
		//process result.
		updateOceanMap(fireCoordinate, result);
		updateState(fireCoordinate, result);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#notifyOpponentShot()
	 */
	/**
	 * This is to notify you of your opponent's shot against your Ocean.  You can do something with 
	 * this, or not.
	 */
	@Override
	public void notifyOpponentShot(GameBoard.FireResult result) 
	{
		// You're not required to do anything here. 
	}

	private Coordinate chooseFire()
	{
		int x = random_generator.nextInt(10);
		int y = random_generator.nextInt(10);
		while(oceanMap[x][y] != OceanStatus.UNKNOWN)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
		}
		return (new Coordinate(x, y));
	}
	
	private GameBoard.FireResult fire(int x, int y)
	{
		GameBoard.FireResult result = fireShot(new Ship.GridCoord(x, y));
		return (result);
	}
	private GameBoard.FireResult fire(Coordinate c)
	{
		return (fire(c.x, c.y));
	}
	
	private void updateOceanMap(Coordinate coord, GameBoard.FireResult result)
	{
		switch(result.result_type)
		{
			case SHOT_MISS:
				oceanMap[coord.x][coord.y] = OceanStatus.MISS;
				break;
			case SHOT_HIT:
				oceanMap[coord.x][coord.y] = OceanStatus.HIT;
				break;
			case SHOT_SUNK:
				oceanMap[coord.x][coord.y] = OceanStatus.HIT;
				break;
			default:
				break;
		}
	}
	
	private void updateState(Coordinate coord, GameBoard.FireResult result)
	{
		switch(result.result_type)
		{
			case SHOT_MISS:
				//System.out.println("MISS!");
				break;
			case SHOT_HIT:
				//System.out.println("HIT!");
				break;
			case SHOT_SUNK:
				//System.out.println("SUNK!");
				break;
			default:
				break;
		}
	}
	
	private void clearOcean()
	{
		for (int i = 0; i < 10; i ++)
		{
			for (int j = 0; j < 10; j ++)
			{
				oceanMap[i][j] = OceanStatus.UNKNOWN;
			}
		}
	}
	
	public class Coordinate
	{
		int x;
		int y;
		Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	private Ship.Orientation_t getOrientationFromInt(int o)
	{
		Ship.Orientation_t or = Ship.Orientation_t.OR_RIGHT;
		switch(o)
		{
			case 0:
				or = Ship.Orientation_t.OR_RIGHT;
				break;
			case 1:
				or = Ship.Orientation_t.OR_DOWN;
				break;
			default:
				break;
		}
		return or;
	}
}

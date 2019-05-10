/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship.players;

import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class EvansCoolCode extends GamePlayer {

	Random random_generator = new Random();
	
	/**
	 */
	public EvansCoolCode() 
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
	}
	
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#placeShips()
	 */
	/**
	 * Place your ships.  You must place exactly five.  One of each type.
	 */
	@Override
	public void placeShips() 
	{Random random=new Random();
	int x= random.nextInt(10);
	int y= random.nextInt(10);
	
	
		// TODO Required: place five ships, one of each type, with no collisions
	Ocean.PlaceResult_t a = Ocean.PlaceResult_t.E_COLLISION;
	while(a != Ocean.PlaceResult_t.E_OK)
	{
		x= random.nextInt(10);
		y= random.nextInt(10);	
		a = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
	}
	
	Ocean.PlaceResult_t b = Ocean.PlaceResult_t.E_COLLISION;
	while(b != Ocean.PlaceResult_t.E_OK)
	{
		x= random.nextInt(10);
		y= random.nextInt(10);
		b = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_BATTLESHIP));
		}
	
	Ocean.PlaceResult_t c = Ocean.PlaceResult_t.E_COLLISION;
	while(c != Ocean.PlaceResult_t.E_OK)
	{
		x= random.nextInt(10);
		y= random.nextInt(10);
		c = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CRUISER));
		}
	
	Ocean.PlaceResult_t d = Ocean.PlaceResult_t.E_COLLISION;
	while(d != Ocean.PlaceResult_t.E_OK)
	{
		x= random.nextInt(10);
	y= random.nextInt(10);
	d = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_SUBMARINE));
	}
	
	Ocean.PlaceResult_t e = Ocean.PlaceResult_t.E_COLLISION;
	while(e != Ocean.PlaceResult_t.E_OK)
	{
		x= random.nextInt(10);
	y= random.nextInt(10);
	e = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_DESTROYER));
	}
	
	}
	
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#fireNow()
	 */
	/**
	 * It's your turn.  Fire your shot!  Inspect the result.
	 */
	int x=3;
	int y=3;
	int hit=0;
	@Override
	public void fireNow() 
	{
		FireResult result = fireShot(new Ship.GridCoord(x, y));
		if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
		{
			System.out.println("We hit something!");
			// Note: result.ship_type has information that would be useful.
		}
		
		if (FireResult.FireResultType_t.SHOT_MISS==result.result_type)
		{
			
		}
		//this moves X 2 every time
		x=x+2;
		//run code till end of X then move Y down 1
		if (x>9){
		x=0;
		y++;
		}
		//this code resets the y and the x to the beginning of grid
		if (x>9 && y>9){
	x=0;
	y=0;
		}
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

}

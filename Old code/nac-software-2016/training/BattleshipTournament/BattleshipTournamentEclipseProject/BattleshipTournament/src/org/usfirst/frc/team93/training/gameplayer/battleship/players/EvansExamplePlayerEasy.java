/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship.players;

import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult.FireResultType_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.GridCoord;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.Orientation_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.ShipType_t;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class EvansExamplePlayerEasy extends GamePlayer {

	Random random_generator = new Random();
	int x = -2;
	int y = 0;
	boolean hit = false;
	
	/**
	 */
	public EvansExamplePlayerEasy() 
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
		//start at -2 because we will add 2 right at the start, making it 0
		x = -2;
		//start y at 0
		y = 0;
		//reset hit to false
		hit = false;
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
		//first, add
		x += 1;
		//process coordinates
		//if x is past the board, subtract 10
		if (x >= 10)
		{
			x -= 10;
			//go to next row
			y += 1;
		}
		//if y is outside of board
		if (y >= 10)
		{
			//go back to start
			y -= 10;
		}
		
		//shoot
		FireResult result = fireShot(new Ship.GridCoord(x, y));
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

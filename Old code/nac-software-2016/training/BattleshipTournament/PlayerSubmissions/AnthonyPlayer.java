/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.GridCoord;
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

public class AnthonyPlayer extends GamePlayer {
	
	ArrayList<Point> moves = new ArrayList<Point>();
	ArrayList<Point> hits = new ArrayList<Point>();
	boolean hited = false;
	

	Random random_generator = new Random();
	
	/**
	 */
	public AnthonyPlayer() 
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
		moves.clear();
		hits.clear();
		hited = false;
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
		placeShip(new Ship(new Ship.GridCoord(0,1), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
		placeShip(new Ship(new Ship.GridCoord(0,2), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_BATTLESHIP));
		placeShip(new Ship(new Ship.GridCoord(0,3), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CRUISER));
		placeShip(new Ship(new Ship.GridCoord(0,4), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_SUBMARINE));
		placeShip(new Ship(new Ship.GridCoord(0,5), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_DESTROYER));
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
		
		// Example: Fire at x, y
		
		if(hited == true){
			
			
			
			
		}
		
		else;
		
		int x = random_generator.nextInt(10);  // Limiting range where we shoot should prevent this from winning
		int y = random_generator.nextInt(10);
		
		Point numbers = new Point(x,y);
		
		
		
		while(moves.contains(numbers)){
			int i = random_generator.nextInt(10);  // Limiting range where we shoot should prevent this from winning
			int j = random_generator.nextInt(10);
			
			numbers = new Point(i,j);
		}
		
		moves.add(numbers);
		
		Point pablo = moves.get(moves.size()-1);
		
		
		
		
		
		FireResult result = fireShot(new Ship.GridCoord(pablo.x, pablo.y));
		if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
		{
			System.out.println("We hit something!");
			// Note: result.ship_type has information that would be useful.
			
			hited = true;
			
			
			
		}
	}
	
//	private Object Point(int x, int y) {
//		// TODO Auto-generated method stub
//		return null;
//	}


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

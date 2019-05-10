/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship.players;

import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean.PlaceResult_t;
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
public class AmithPlayer extends GamePlayer {

	Random random_generator = new Random();
	
	/**
	 */
	public AmithPlayer() 
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
	{
		// TODO Required: place five ships, one of each type, with no collisions
		int x = random_generator.nextInt(10);
		int y = random_generator.nextInt(10);
		PlaceResult_t CarrierStatus;
		PlaceResult_t SubmarineStatus;
		PlaceResult_t BattleshipStatus;
		PlaceResult_t DestroyerStatus;
		PlaceResult_t CruiserStatus;
		
		CarrierStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
		while(CarrierStatus != Ocean.PlaceResult_t.E_OK)
		{
			//If Carrier placement is not OK
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			CarrierStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
		}
		
		BattleshipStatus = placeShip(new Ship(new Ship.GridCoord(0,2), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_BATTLESHIP));
		while(BattleshipStatus != Ocean.PlaceResult_t.E_OK)
		{
			//If Carrier placement is not OK
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			BattleshipStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
		}
		
		CruiserStatus = placeShip(new Ship(new Ship.GridCoord(0,4), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CRUISER));
		while(CruiserStatus != Ocean.PlaceResult_t.E_OK)
		{
			//If Carrier placement is not OK
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			CruiserStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
		}
		
		SubmarineStatus = placeShip(new Ship(new Ship.GridCoord(0,6), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_SUBMARINE));
		while(SubmarineStatus != Ocean.PlaceResult_t.E_OK)
		{
			//If Carrier placement is not OK
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			SubmarineStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_SUBMARINE));
		}
		
		DestroyerStatus = placeShip(new Ship(new Ship.GridCoord(0,9), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_DESTROYER));
		while(DestroyerStatus != Ocean.PlaceResult_t.E_OK)
		{
			//If Carrier placement is not OK
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			DestroyerStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_DESTROYER));
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
		
		// Example: Fire at x, y
		int x = random_generator.nextInt(10);
		int y = random_generator.nextInt(10);
		FireResult result = fireShot(new Ship.GridCoord(x, y));
		if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
		{
			println("We hit something!");
			// Note: result.ship_type has information that would be useful.
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

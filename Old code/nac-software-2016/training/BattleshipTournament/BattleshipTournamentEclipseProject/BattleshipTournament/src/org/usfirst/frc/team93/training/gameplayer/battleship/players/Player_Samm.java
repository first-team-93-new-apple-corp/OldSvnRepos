
package org.usfirst.frc.team93.training.gameplayer.battleship.players;

import java.lang.reflect.Array;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult.FireResultType_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.GridCoord;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.Orientation_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.ShipType_t;

public class Player_Samm extends GamePlayer {

	Random random_generator = new Random();
	
	int currentShot = 0;
	int randVar = 0;
	
	boolean[][] attacks = new boolean[10][10];
	boolean[] vertAttacks = new boolean[10];
	boolean[] horzAttacks = new boolean[10];
	
	int vertShotsFired = 0;
	int horzShotsFired = 0;

	int numShotsFired = 0;
	boolean horizontalAttack = true;
	
	boolean firstHit = true;
	/**
	 */
	public Player_Samm() 
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
		currentShot = 0;
		vertShotsFired = 0;
		horzShotsFired = 0;
		numShotsFired = 0;
		firstHit = true;
		horizontalAttack = true;

		int i = 0;
		int j = 0;
		for (i = 0; i < 10; i++)
		{
			vertAttacks[i] = false;
			horzAttacks[i] = false;
			
			for (j = 0; j < 10; j++)
			{
				attacks[i][j] = false;
			}
		}
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
		int rightShipHorizontalLocation = 7;
		int leftShipHorizontalLocation = 6;
		
		int horizontalSeed = random_generator.nextInt(6);
		
		if (horizontalSeed == 3)
		{
			rightShipHorizontalLocation = 0;
			leftShipHorizontalLocation = leftShipHorizontalLocation + horizontalSeed;
		}
		else if (horizontalSeed >= 4)
		{
			rightShipHorizontalLocation = ((rightShipHorizontalLocation + horizontalSeed) - 9);
			leftShipHorizontalLocation = ((leftShipHorizontalLocation + horizontalSeed) - 9);
		}
		else
		{
			rightShipHorizontalLocation = rightShipHorizontalLocation + horizontalSeed;
			leftShipHorizontalLocation = leftShipHorizontalLocation + horizontalSeed;
		}
		
		// TODO Required: place five ships, one of each type, with no collisions
		placeShip(new Ship(new Ship.GridCoord((horizontalSeed),1), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_DESTROYER));
		placeShip(new Ship(new Ship.GridCoord((horizontalSeed),5), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
		placeShip(new Ship(new Ship.GridCoord((horizontalSeed),8), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_SUBMARINE));
		placeShip(new Ship(new Ship.GridCoord(leftShipHorizontalLocation,2), Ship.Orientation_t.OR_UP, Ship.ShipType_t.SHIP_BATTLESHIP));
		placeShip(new Ship(new Ship.GridCoord(rightShipHorizontalLocation,7), Ship.Orientation_t.OR_UP, Ship.ShipType_t.SHIP_CRUISER));
		
		int i = 0;
		int j = 0;
		for (; i < 10; i++)
		{
			vertAttacks[i] = false;
			horzAttacks[i] = false;
			
			for (; j < 10; j++)
			{
				attacks[i][j] = false;
			}
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
		
		FireResult result = null;
		
		if (currentShot == 0)
		{
			result = fireShot(new Ship.GridCoord(2,numShotsFired));
			
			attacks[2][numShotsFired] = true;
			vertAttacks[2] = true;
			
			if (numShotsFired == 9)
			{
				vertShotsFired++;
				currentShot++;
				numShotsFired = 0;
				firstHit = true;
			}
			else
			{
				numShotsFired++;	
			}
		}
		else if (currentShot == 1)
		{
			result = fireShot(new Ship.GridCoord(5,numShotsFired));
			
			attacks[5][numShotsFired] = true;
			vertAttacks[5] = true;
			
			if (numShotsFired == 9)
			{
				vertShotsFired++;
				currentShot++;
				numShotsFired = 0;
				firstHit = true;
			}
			else
			{
				numShotsFired++;	
			}
		}
		else if (currentShot == 2)
		{
			result = fireShot(new Ship.GridCoord(8,numShotsFired));
						
			attacks[8][numShotsFired] = true;
			vertAttacks[8] = true;
			
			if (numShotsFired == 9)
			{
				vertShotsFired++;
				currentShot++;
				numShotsFired = 0;
				firstHit = true;
			}
			else
			{
				numShotsFired++;	
			}
		}
		else if (currentShot == 3)
		{
			result = fireShot(new Ship.GridCoord(numShotsFired,2));
			
			attacks[numShotsFired][2] = true;
			horzAttacks[2] = true;
			
			if (numShotsFired == 9)
			{
				horzShotsFired++;
				currentShot++;
				numShotsFired = 0;
				firstHit = true;
			}
			else
			{
				numShotsFired++;	
			}
		}
		else if (currentShot == 4)
		{
			result = fireShot(new Ship.GridCoord(numShotsFired,5));
			
			attacks[numShotsFired][5] = true;
			horzAttacks[5] = true;
			
			if (numShotsFired == 9)
			{
				horzShotsFired++;
				currentShot++;
				numShotsFired = 0;
				firstHit = true;
			}
			else
			{
				numShotsFired++;	
			}
		}
		else if (currentShot == 5)
		{
			result = fireShot(new Ship.GridCoord(numShotsFired,8));
			
			attacks[numShotsFired][8] = true;
			horzAttacks[8] = true;
			
			if (numShotsFired == 9)
			{
				horzShotsFired++;
				currentShot++;
				numShotsFired = 0;
				firstHit = true;
			}
			else
			{
				numShotsFired++;	
			}
		}
		else
		{
			if (horizontalAttack)
			{
				if (firstHit == true)
				{
					randVar = random_generator.nextInt(10);

					boolean newRandNeeded = false;
					while (newRandNeeded == false)
					{
						if (horzAttacks[randVar] == false)
						{
							newRandNeeded = true;
						}
						else
						{
							randVar = random_generator.nextInt(10);
						}
					}
					firstHit = false;
				}
				
				boolean newShot = false;
				while (!newShot && (numShotsFired < 9))
				{
					if (attacks[numShotsFired][randVar] == true)
					{
						numShotsFired++;
					}
					else
					{
						newShot = true;
					}
				}

				result = fireShot(new Ship.GridCoord(numShotsFired,randVar));
				attacks[numShotsFired][randVar] = true;
				
				if (numShotsFired == 9)
				{
					horzAttacks[randVar] = true;
					firstHit = true;
					horzShotsFired++;
					numShotsFired = 0;
					horizontalAttack = !horizontalAttack;
				}
				else
				{
					numShotsFired++;	
				}
			}
			else
			{
				if (firstHit == true)
				{
					randVar = random_generator.nextInt(10);

					boolean newRandNeeded = false;
					while (newRandNeeded == false)
					{
						if (vertAttacks[randVar] == false)
						{
							newRandNeeded = true;
						}
						else
						{
							randVar = random_generator.nextInt(10);
						}
					}
					firstHit = false;
				}
				
				boolean newShot = false;
				while (!newShot && (numShotsFired < 9))
				{
					if (attacks[randVar][numShotsFired] == true)
					{
						numShotsFired++;
					}
					else
					{
						newShot = true;
					}
				}

				result = fireShot(new Ship.GridCoord(randVar,numShotsFired));
				attacks[randVar][numShotsFired] = true;
				
				if (numShotsFired == 9)
				{
					vertAttacks[randVar] = true;
					firstHit = true;
					vertShotsFired++;
					numShotsFired = 0;
					horizontalAttack = !horizontalAttack;
				}
				else
				{
					numShotsFired++;	
				}
			}
		}
		
		
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

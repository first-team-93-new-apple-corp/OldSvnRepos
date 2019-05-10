/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class BenPlayer extends GamePlayer 
{
	boolean[][] field = new boolean[10][10];
	
	FireResult result;

	Random random_generator = new Random();
	
	int fireStage = 0;
	
	int FireX;
	int FireY;
	
	/**
	 */
	public BenPlayer() 
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
		resetfield();
	}
	
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#placeShips()
	 */
	/**
	 * Place your ships.  You must place exactly five.  One of each type.
	 */
	public Ship.Orientation_t randomarrange()
	{
		Random random = new Random();
		Ship.Orientation_t ship = Ship.Orientation_t.OR_DOWN;
		int arrangement = random.nextInt(4);

		if(arrangement == 0)
		{
			ship = Ship.Orientation_t.OR_DOWN;
		}
		if(arrangement == 1)
		{
			ship = Ship.Orientation_t.OR_UP;
		}
		if(arrangement == 2)
		{
			ship = Ship.Orientation_t.OR_LEFT;
		}
		if(arrangement == 3)
		{
			ship = Ship.Orientation_t.OR_RIGHT;
		}
		return ship;
	}
	
	@Override
	public void placeShips()
	{
		// TODO Required: place five ships, one of each type, with no collisions
		Random random = new Random();
		int x = random.nextInt(10);
		int y = random.nextInt(10);

		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_CARRIER)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_BATTLESHIP)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_CRUISER)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_SUBMARINE)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_DESTROYER)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
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
			switch(fireStage)
			{
			case 0:
				// TODO Required: Fire your shot.  Override this logic with your own.
		
				// Example: Fire at x, y
				fireGen();
		
				if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
				{
					fireStage++;
					// Note: result.ship_type has information that would be useful.
				}
			case 1:
				fireStage++;
				if(field[FireX - 1][FireY])
				{
					fireNow();
				}
				else
				{
					result = fireShot(new Ship.GridCoord(FireX - 1, FireY));
				}
			case 2:
				fireStage++;
				if(field[FireX][FireY + 1])
				{
					fireNow();
				}
				else
				{
					result = fireShot(new Ship.GridCoord(FireX, FireY + 1));
				}
			case 3:
				fireStage++;
				if(field[(FireX + 1)][FireY])
				{
					fireNow();
				}
				else
				{
					result = fireShot(new Ship.GridCoord(FireX + 1, FireY));
				}
			case 4:
				fireStage = 0;
				if(field[FireX][(FireY - 1)])
				{
					fireNow();
				}
				else
				{
					result = fireShot(new Ship.GridCoord(FireX, FireY - 1));
				}
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
	public void resetfield()
	{
		int i = 0;
		int j = 0;
		while(i < 10)
		{
			j = 0;
			while (j < 10)
			{
				field[i][j] = false;
				//System.out.println(i + ", " + j);
				j++;
			}
			i++;
		}
		//print2DArray(field);
	}
	public void fireGen()
	{
		boolean loopFin = true;
		int x = 0;
		int y = 0;
		int i = 0;
		while(loopFin)
		{

			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			FireX = x;
			FireY = y;
			if(evtst(x, y))
			{
				loopFin = field[x][y];
			}
			
			i++;
			if (i > 50)
			{
				//print2DArray(field);
			}
			
		}
		field[x][y] = true;
		result = fireShot(new Ship.GridCoord(x, y));
	}
	
	public void print2DArray(boolean[][] array)
	{
		String s = "{\n";
		for(int i = 0; i < array.length; i++)
		{
			boolean[] x = array[i];
			s += "{";
			for (int j = 0; j < array.length; j++)
			{
				if (j != 0)
				{
					s += ", ";
				}
				s += String.valueOf(x[j]);
			}
			s += "}\n";
		}
		s += "\n}";
		System.out.println(s);
	}
	
	public boolean fireTest(int x, int y)
	{
		if(field[x][y])
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean evtst(double x, double y)
	{
		if ((x + y) % 2 == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}

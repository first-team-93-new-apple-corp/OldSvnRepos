/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship.players;

import java.util.ArrayList;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean;
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
public class Player_Ben_BenPlayer extends GamePlayer {

	Random random_generator = new Random();
	FireResult[][] field;
	PointList pointlist;
	public int StrikeDis;
	public boolean StrikeUpDown;
	public Point rootPoint;
	public PointList HitList;
	public enum FireState
	{
		SEARCH, CARDINAL, STRIKE, HITLIST // Search searches, cardinal goes in cardinal directions until hit, strike attacks the ship.  Hitlist is for overlap and if it ends up hittong another ship
	}
	public enum CardinalFireState
	{
		NORTH, EAST, SOUTH, WEST
	}
	public FireState firestate;
	public CardinalFireState cardinalfirestate;
	public CardinalFireState strikeDirection;
	
	/**
	 */
	public Player_Ben_BenPlayer() 
	{
		super();
		field = new FireResult[10][10];
		pointlist = new PointList();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Notification that a new match is about to start. 
	 */
	@Override
	public void notifyReset()
	{
		ResetField();
		firestate = FireState.SEARCH;
		cardinalfirestate = CardinalFireState.NORTH;
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
		// TODO Required: Fire your shot.  Override this logic with your own.
		if(firestate == FireState.SEARCH)
		{
			// Example: Fire at x, y
			FireResult result = GetShots();
			if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
			{
				//println("We hit something!");
				// Note: result.ship_type has information that would be useful.
				firestate = FireState.CARDINAL;
			}
		}
		else if(firestate == FireState.CARDINAL)
		{
			FireResult result;
			switch(cardinalfirestate)
			{
			case NORTH:
				result = Shoot(rootPoint.x,rootPoint.y - 1 );
				if(result == null)
				{
					//shoot at next cardinal
					cardinalfirestate = CardinalFireState.EAST;
					fireNow();
				}
				else if(result.result_type == FireResultType_t.SHOT_HIT)
				{
					//goto strike mode
					field[rootPoint.x][rootPoint.y - 1] = result;
					cardinalfirestate = CardinalFireState.NORTH;//CHANGE
					firestate = FireState.STRIKE;
					strikeDirection = CardinalFireState.NORTH;
					
				}
				else if(result.result_type == FireResultType_t.SHOT_MISS)
				{
					//shoot at next cardinal
					field[rootPoint.x][rootPoint.y - 1] = result;
					cardinalfirestate = CardinalFireState.EAST;
				}
				break;
			case EAST:
				result = Shoot(rootPoint.x + 1,rootPoint.y);
				if(result == null)
				{
					//shoot at next cardinal
					cardinalfirestate = CardinalFireState.SOUTH;
					fireNow();
				}
				else if(result.result_type == FireResultType_t.SHOT_HIT)
				{
					//goto strike mode
					field[rootPoint.x + 1][rootPoint.y] = result;
					cardinalfirestate = CardinalFireState.NORTH;//CHANGE
					firestate = FireState.STRIKE;
					strikeDirection = CardinalFireState.EAST;
					
				}
				else if(result.result_type == FireResultType_t.SHOT_MISS)
				{
					//shoot at next cardinal
					field[rootPoint.x + 1][rootPoint.y] = result;
					cardinalfirestate = CardinalFireState.SOUTH;
				}
				break;
			case SOUTH:
				result = Shoot(rootPoint.x + 1,rootPoint.y);
				if(result == null)
				{
					//shoot at next cardinal
					cardinalfirestate = CardinalFireState.WEST;
					fireNow();
				}
				else if(result.result_type == FireResultType_t.SHOT_HIT)
				{
					//goto strike mode
					field[rootPoint.x][rootPoint.y + 1] = result;
					cardinalfirestate = CardinalFireState.EAST;//CHANGE
					firestate = FireState.STRIKE;
					strikeDirection = CardinalFireState.EAST;
					
				}
				else if(result.result_type == FireResultType_t.SHOT_MISS)
				{
					//shoot at next cardinal
					field[rootPoint.x][rootPoint.y + 1] = result;
					cardinalfirestate = CardinalFireState.SOUTH;
				}
				break;
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
	public void ResetField()
	{
		field = new FireResult[10][10];
	}
	public FireResult GetShots()
	{
		int x = 0;
		int y = 0;
		boolean isFinished = false;
		while(!isFinished)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			if(field[x][y] == null)
			{
				isFinished = true;
			}
		}
		FireResult result = fireShot(new Ship.GridCoord(x, y));
		if(FireResult.FireResultType_t.SHOT_HIT == result.result_type)
		{
			rootPoint = new Point(x, y, result);
		}
		if(FireResult.FireResultType_t.SHOT_HIT == result.result_type || FireResult.FireResultType_t.SHOT_SUNK == result.result_type)
		{
			pointlist.AddPoint(new Point(x, y, result));
			field[x][y] = result;
		}
		else
		{
			pointlist.AddPoint(new Point(x, y, result));
			field[x][y] = result;
		}
		return result;
	}
	public class Point
	{
		public int x;
		public int y;
		public FireResult hasHit;
		public Point(int x, int y, FireResult hasHit)
		{
			this.x = x;
			this.y = y;
			this.hasHit = hasHit;
		}
	}
	public class PointList
	{
		public ArrayList<Point> PointStored;

		public PointList()
		{
			PointStored = new ArrayList<Point>();
		}
		public void AddPoint(Point point)
		{
			PointStored.add(point);
		}
		public Point getLast(int DistanceBack)
		{
			int j = PointStored.size();
			if(DistanceBack >= j)
			{
				return null;
			}
			else
			{
				return PointStored.get(j - DistanceBack);
			}
		}
	}
	public FireResult Shoot(int firex,int firey)
	{
		FireResult result;
		if(IsGoodLocation(firex, firey))
		{
		result = fireShot(new Ship.GridCoord(firex, firey));
		pointlist.AddPoint(new Point(firex, firey, result));
		}
		else
		{
			result = null;
		}
		return result;
	}
	public boolean IsGoodLocation(int x, int y)
	{
		return (x >= 0 && x <= 9 && y >= 0 && y <= 9 && (field[x][y] == null));
	}
}

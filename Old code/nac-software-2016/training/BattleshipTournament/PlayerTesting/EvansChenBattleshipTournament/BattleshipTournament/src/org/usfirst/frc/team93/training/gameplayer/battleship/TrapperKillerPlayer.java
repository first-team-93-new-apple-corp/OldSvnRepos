/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.ArrayList;
import java.util.List;
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
public class TrapperKillerPlayer extends GamePlayer {

	Random random_generator = new Random();
	
	public enum OceanStatus
	{
		HIT, MISS, UNKNOWN, NULL;
	}
	
	public enum State
	{
		HUNT, KILL, NULL;
	}
	
	enum ShipOrientation
	{
		HORIZONTAL, VERTICAL, UNKNOWN, NULL;
	}
	
	OceanStatus[][] oceanMap = new OceanStatus[10][10];
	State state = State.HUNT;
	
	List<TargetShip> TargetShipList = new ArrayList<TargetShip>();
	
	List<Ship.ShipType_t> RemainingShips = new ArrayList<Ship.ShipType_t>();
	
	/**
	 * @param own_ocean
	 */
	public TrapperKillerPlayer() 
	{
		super();
	}
	public TrapperKillerPlayer(int player) 
	{
		super(player);
	}

	
	/**
	 * Notification that a new match is about to start. 
	 */
	@Override
	public void notifyReset()
	{
		clearOcean();
		clearTargets();
		resetRemainingShips();
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
		//update state
		if (TargetShipList.size() == 0)
		{
			state = State.HUNT;
		}
		else
		{
			state = State.KILL;
		}
		
		Coordinate c;
		switch(state)
		{
			case HUNT:
				int x = random_generator.nextInt(10);
				int y = random_generator.nextInt(10);
				while((oceanMap[x][y] != OceanStatus.UNKNOWN) || (((x + (11 * y)) % getShipMinLength()) != 0))
				{
					x = random_generator.nextInt(10);
					y = random_generator.nextInt(10);
				}
				c = new Coordinate(x, y);
				break;
			case KILL:
				if (TargetShipList.get(0).queue.size() == 0)
				{
					print("WAT");
				}
				c = TargetShipList.get(0).queue.get(0);
				break;
			default:
				c = new Coordinate(0, 0);
				break;
		}
		return c;
	}
	
	private GameBoard.FireResult fire(int x, int y)
	{
		GameBoard.FireResult result = fireShot(new Ship.GridCoord(x, y));
		printLog("(" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
		removeQueuePoint(x, y);
		print(getName() + " fires at (" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
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
		//process hit and miss
		switch(result.result_type)
		{
			case SHOT_MISS:
				//nothing happens.
				if (TargetShipList.size() == 0)
				{
					print("MISS!");
				}
				else
				{
					print("MISSED THE " + TargetShipList.get(0).ship.name() + "!");
				}
				printLog(", 0\n");
				break;
			case SHOT_HIT:
				print("HIT THE " + result.ship_type.name() + "!");
				printLog(", 1\n");
				//if a ship was hit...
				if (checkShipType(result.ship_type))
				{
					//if first time, add ship to target list.
					TargetShip targetShip = searchTargetList(result.ship_type);
					if (targetShip == null)
					{
						TargetShipList.add(new TargetShip(result.ship_type, coord));
					}
					//if not first time, update that ship's queue.
					else
					{
						targetShip.addHit(coord);
					}
				}
				break;
			case SHOT_SUNK:
				print("SUNK THE " + result.ship_type.name() + "!");
				printLog(", 1\n");
				//remove the ship from the target list, as it is destroyed.
				TargetShipList.remove(searchTargetListIndex(result.ship_type));
				RemainingShips.remove(result.ship_type);
				break;
			default:
				break;
		}
		
		//update state
		if (TargetShipList.size() == 0)
		{
			state = State.HUNT;
		}
		else
		{
			state = State.KILL;
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
	
	private void clearTargets()
	{
		TargetShipList.clear();
	}
	
	private void resetRemainingShips()
	{
		RemainingShips.clear();
		RemainingShips.add(Ship.ShipType_t.SHIP_CARRIER);
		RemainingShips.add(Ship.ShipType_t.SHIP_BATTLESHIP);
		RemainingShips.add(Ship.ShipType_t.SHIP_CRUISER);
		RemainingShips.add(Ship.ShipType_t.SHIP_SUBMARINE);
		RemainingShips.add(Ship.ShipType_t.SHIP_DESTROYER);
	}
	
	private int getShipLength(Ship.ShipType_t s)
	{
		int l = -1;
		switch(s)
		{
			case SHIP_CARRIER:
				l = 5;
				break;
			case SHIP_BATTLESHIP:
				l = 4;
				break;
			case SHIP_CRUISER:
				l = 3;
				break;
			case SHIP_SUBMARINE:
				l = 3;
				break;
			case SHIP_DESTROYER:
				l = 2;
				break;
			default:
				break;
		}
		return l;
	}
	
	private int getShipMinLength()
	{
		int minLength = 5;
		for(int i = 0; i < RemainingShips.size(); i ++)
		{
			if (minLength > getShipLength(RemainingShips.get(i)))
			{
				minLength = getShipLength(RemainingShips.get(i));
			}
		}
		return minLength;
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
	
	public class TargetShip
	{
		List<Coordinate> hits = new ArrayList<Coordinate>();
		List<Coordinate> queue = new ArrayList<Coordinate>();
		ShipOrientation shipOrientation;
		Ship.ShipType_t ship;
		
		TargetShip(Ship.ShipType_t type, int hitX, int hitY, ShipOrientation orientation)
		{
			ship = type;
			shipOrientation = orientation;
			addHit(new Coordinate(hitX, hitY));
		}
		TargetShip(Ship.ShipType_t type, int hitX, int hitY)
		{
			this(type, hitX, hitY, ShipOrientation.UNKNOWN);
		}
		TargetShip(Ship.ShipType_t type, Coordinate hit)
		{
			this(type, hit.x, hit.y, ShipOrientation.UNKNOWN);
		}
		
		public void addHit(Coordinate hit)
		{
			hits.add(hit);
			
			//add points to queue.
			addQueuePoints(hit);
			
			//check orientation
			shipOrientation = findOrientation();
			
			//remove points that do not align with orientation.
			straightenQueue(shipOrientation);
		}
		
		private ShipOrientation findOrientation()
		{
			//figure out the ship's orientation.
			ShipOrientation orientation = ShipOrientation.UNKNOWN;
			if (hits.size() < 2)
			{
				return orientation;
			}
			if ((hits.get(0).x != hits.get(1).x) && (hits.get(0).y == hits.get(1).y))
			{
				orientation = ShipOrientation.HORIZONTAL;
			}
			if ((hits.get(0).y != hits.get(1).y) && (hits.get(0).x == hits.get(1).x))
			{
				orientation = ShipOrientation.VERTICAL;
			}
			print("Target's orientation is " + orientation.name() + ".");
			return orientation;
		}
		
		private void addQueuePoints(Coordinate hit)
		{
			//add the points surrounding the hit point to the queue.
			//TODO: Randomize this so that it doesn't do it in the same order every time.
			addQueuePoint(new Coordinate(hit.x - 1, hit.y));
			addQueuePoint(new Coordinate(hit.x, hit.y - 1));
			addQueuePoint(new Coordinate(hit.x + 1, hit.y));
			addQueuePoint(new Coordinate(hit.x, hit.y + 1));
		}
		
		private void addQueuePoint(Coordinate queuePoint)
		{
			//Points added to the queue must be within the ocean's bounds and also not be already fired at and not already be in the queue.
			if ((checkWithin(queuePoint)) && (getOceanMap(queuePoint) == OceanStatus.UNKNOWN) && (searchCoordinates(queuePoint, queue) == -1))
			{
				queue.add(queuePoint);
			}
		}
		
		private void straightenQueue(ShipOrientation orientation)
		{
			for (int i = 0; i < queue.size(); i ++)
			{
				switch(orientation)
				{
					case HORIZONTAL:
						//check if queue point does not with orientation.
						if (!(queue.get(i).y == hits.get(0).y))
						{
							//if it doesn't, remove the point.
							queue.remove(i);
							i -= 1;
						}
						break;
					case VERTICAL:
						//check if queue point does not with orientation.
						if (!(queue.get(i).x == hits.get(0).x))
						{
							//if it doesn't, remove the point.
							queue.remove(i);
							i -= 1;
						}
						break;
					case UNKNOWN:
						break;
					default:
						break;
				}
			}
		}
	}
	
	private boolean checkWithin(int x, int y)
	{
		return ((x >= 0) && (x < 10) && (y >= 0) && (y < 10));
	}
	private boolean checkWithin(Coordinate c)
	{
		return checkWithin(c.x, c.y);
	}
	
	private OceanStatus getOceanMap(Coordinate c)
	{
		return oceanMap[c.x][c.y];
	}
	
	private boolean checkShipType(Ship.ShipType_t type)
	{
		return (!(((type == Ship.ShipType_t.SHIP_NULL) || (type == Ship.ShipType_t._SHIP_TOP))));
	}
	
	private TargetShip searchTargetList(Ship.ShipType_t type)
	{
		TargetShip exists = null;
		for (int i = 0; i < TargetShipList.size(); i ++)
		{
			if (TargetShipList.get(i).ship == type)
			{
				exists = TargetShipList.get(i);
			}
		}
		return exists;
	}
	
	private int searchTargetListIndex(Ship.ShipType_t type)
	{
		for (int i = 0; i < TargetShipList.size(); i ++)
		{
			if (TargetShipList.get(i).ship == type)
			{
				return i;
			}
		}
		return -1;
	}
	
	private int searchCoordinates(Coordinate c, List<Coordinate> l)
	{
		for (int i = 0; i < l.size(); i ++)
		{
			if ((l.get(i).x == c.x) && (l.get(i).y == c.y))
			{
				return i;
			}
		}
		return -1;
	}
	
	private void removeQueuePoint(Coordinate c)
	{
		for (int i = 0; i < TargetShipList.size(); i ++)
		{
			int coordIndex = searchCoordinates(c, TargetShipList.get(i).queue);
			if (coordIndex > -1)
			{
				TargetShipList.get(i).queue.remove(coordIndex);
			}
		}
	}
	private void removeQueuePoint(int x, int y)
	{
		removeQueuePoint(new Coordinate(x, y));
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
	
	public void printLog(String s)
	{
		//System.out.print(s);
	}
}

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
 */
public class BetterColbyPlayer extends GamePlayer {

	Random random_generator = new Random();
	private int mapLength = 10;
	private int mapHeight = 10;
	double[][] myProbablityMap = new double[mapLength][mapHeight];
	private double AI_OPEN_LOW_MIN = 5;
	private double AI_OPEN_LOW_MAX = 10;
	private double AI_OPEN_MED_MIN = 5;
	private double AI_OPEN_MED_MAX = 10;
	private double AI_OPEN_HIGH_MIN = 5;
	private double AI_OPEN_HIGH_MAX = 10;
	private final int BATTLESHIP_SIZE = 2;
	private final int CARRIER_SIZE = 5;
    private final int CRUISER_SIZE = 4;
	private final int DESTROYER_SIZE = 3;
	private final int SUBMARINE_SIZE = 3;
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
	
	OceanStatus[][] oceanMap = new OceanStatus[mapLength][mapHeight];
	State state = State.HUNT;
	
	ArrayList<TargetShip> TargetShipList = new ArrayList<TargetShip>();
	ArrayList<Ship.ShipType_t> RemainingShips = new ArrayList<Ship.ShipType_t>();
	/**
	 * @param own_ocean
	 */
	public BetterColbyPlayer() 
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
		clearTargets();
		state = State.HUNT;
		resetRemainingShips();
		
	}
	private class ShipPlacerHelper
	{
		protected Ship.Orientation_t getOrientation()
		{
			int orientation = random_generator.nextInt(4);
			if(orientation == 0) return Ship.Orientation_t.OR_RIGHT;
			else if(orientation ==1) return Ship.Orientation_t.OR_LEFT;
			else if(orientation ==2) return Ship.Orientation_t.OR_UP;
			return Ship.Orientation_t.OR_DOWN;
		}
		protected int getX()
		{
			return random_generator.nextInt(mapLength); 
		}
		protected int getY()
		{
			return random_generator.nextInt(mapHeight);
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
		ShipPlacerHelper helper = new ShipPlacerHelper();
		int shipx = helper.getX();
		int shipy = helper.getY();
		Ship.Orientation_t shipOrientation = helper.getOrientation();
		while(placeShip(new Ship(new Ship.GridCoord(shipx, shipy), shipOrientation, Ship.ShipType_t.SHIP_BATTLESHIP)) != PlaceResult_t.E_OK)
		{
			shipx = helper.getX();
			shipy = helper.getY();
			shipOrientation = helper.getOrientation();
		}
		while(placeShip(new Ship(new Ship.GridCoord(shipx, shipy), shipOrientation, Ship.ShipType_t.SHIP_CARRIER)) != PlaceResult_t.E_OK)
		{
			shipx = helper.getX();
			shipy = helper.getY();
			shipOrientation = helper.getOrientation();
		}
		while(placeShip(new Ship(new Ship.GridCoord(shipx, shipy), shipOrientation, Ship.ShipType_t.SHIP_CRUISER)) != PlaceResult_t.E_OK)
		{
			shipx = helper.getX();
			shipy = helper.getY();
			shipOrientation = helper.getOrientation();
		}
		while(placeShip(new Ship(new Ship.GridCoord(shipx, shipy), shipOrientation, Ship.ShipType_t.SHIP_DESTROYER)) != PlaceResult_t.E_OK)
		{
			shipx = helper.getX();
			shipy = helper.getY();
			shipOrientation = helper.getOrientation();
		}
		while(placeShip(new Ship(new Ship.GridCoord(shipx, shipy), shipOrientation, Ship.ShipType_t.SHIP_SUBMARINE)) != PlaceResult_t.E_OK)
		{
			shipx = helper.getX();
			shipy = helper.getY();
			shipOrientation = helper.getOrientation();
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
		Coordinate fireCoordinate = chooseFire();
		GameBoard.FireResult result = fire(fireCoordinate);
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
		Coordinate coord;
		if (TargetShipList.size() == 0)
		{
			state = State.HUNT;
		}
		else
		{
			state = State.KILL;
		}
		switch(state)
		{
			case HUNT:
				int x = random_generator.nextInt(10);
				int y = random_generator.nextInt(10);
				while(oceanMap[x][y] != OceanStatus.UNKNOWN)
				{
					x = random_generator.nextInt(10);
					y = random_generator.nextInt(10);
				}
				coord = new Coordinate(x, y);
				break;
			case KILL:
				coord = TargetShipList.get(0).queue.get(0);
				break;
			default:
				coord = new Coordinate(0, 0);
				break;
		}
		return coord;
	}
	
	private GameBoard.FireResult fire(int x, int y)
	{
		GameBoard.FireResult result = fireShot(new Ship.GridCoord(x, y));
		removeQueuePoint(x, y);
		System.out.println(getClass().getSimpleName() + " fires at (" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
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
				setProbablity(coord, 0);
				break;
			case SHOT_HIT:
				System.out.println("HIT THE " + result.ship_type.name() + "!");
				//if a ship was hit...
				//if first time, add ship to target list.
				TargetShip targetShip = searchTargetList(result.ship_type);
				if (targetShip == null)
				{
					TargetShipList.add(new TargetShip(result.ship_type, coord));
				}
				//if not first time, update that ship's queue.
				else
				{
					//targetShip.queue.add(0, coord);
					targetShip.addHit(coord);
				}
				
				break;
			case SHOT_SUNK:
				TargetShipList.remove(searchTargetListIndex(result.ship_type));
				RemainingShips.remove(searchTargetListIndex(result.ship_type));
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
		generateMap();
	}
	
	private void clearTargets()
	{
		TargetShipList.clear();
	}
	private void resetRemainingShips()
	{
		RemainingShips.clear();
		RemainingShips.add(Ship.ShipType_t.SHIP_BATTLESHIP);
		RemainingShips.add(Ship.ShipType_t.SHIP_CARRIER);
		RemainingShips.add(Ship.ShipType_t.SHIP_CRUISER);
		RemainingShips.add(Ship.ShipType_t.SHIP_DESTROYER);
		RemainingShips.add(Ship.ShipType_t.SHIP_SUBMARINE);	
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
	
	private class TargetShip
	{
		ArrayList<Coordinate> hits = new ArrayList<Coordinate>();
		ArrayList<Coordinate> queue = new ArrayList<Coordinate>();
		ShipOrientation m_Orientation;
		Ship.ShipType_t ship;
		
		TargetShip(Ship.ShipType_t type, Coordinate hit)
		{
			ship = type;
			m_Orientation = ShipOrientation.UNKNOWN;
			addHit(new Coordinate(hit.x,hit.y));
		}
		
		public void addHit(Coordinate hit)
		{
			hits.add(hit);
			addQueuePoints(hit);
			organizeQueue(m_Orientation);
		}
		
		private ShipOrientation getOrientation()
		{
			int xZero = hits.get(0).x;
			int xOne = hits.get(1).x;
			int yZero = hits.get(0).y;
			int yOne = hits.get(0).y;
			if (isChange(xZero ,xOne) && !isChange(yZero ,yOne))
			{
				return ShipOrientation.HORIZONTAL;
			}
			return ShipOrientation.VERTICAL;
			
		}
		
		private void addQueuePoints(Coordinate hit)
		{			
			int amountToAdd = getShipSize(ship);
			if(hits.size() >= 2)
			{
				m_Orientation = getOrientation();
				if(m_Orientation == ShipOrientation.HORIZONTAL)
				{
					for(int i = 0; i < amountToAdd; ++i)
					{
						
						addQueuePoint(new Coordinate(hit.x - i, hit.y));
						
				//		if(onGrid(new Coordinate(hit.x-i,hit.y)))
				//		{
				//			if(myProbablityMap[hit.x-1][hit.y] > 0)
				//			{
								
				//			}
				//		}
					}
					for(int i = 0; i < amountToAdd; ++i)
					{
				//		if(onGrid(new Coordinate(hit.x+1,hit.y)))
				//		{
				//			if(myProbablityMap[hit.x][hit.y+1] > 0)
				//			{
								addQueuePoint(new Coordinate(hit.x + i, hit.y));
				//			}
				//		}
					}
				}
				else if(m_Orientation == ShipOrientation.VERTICAL)
				{
					for(int i = 0; i < amountToAdd; ++i)
					{
				//		if(onGrid(new Coordinate(hit.x,hit.y-1)))
				//		{
				//			if(myProbablityMap[hit.x][hit.y-1] > 0)
				//			{
								addQueuePoint(new Coordinate(hit.x, hit.y - i));
				//			}
				//		}
					}
					for(int i = 0; i < amountToAdd; ++i)
					{
				//		if(onGrid(new Coordinate(hit.x,hit.y+1)))
				//		{
				//			if(myProbablityMap[hit.x][hit.y+1] > 0)
				//			{
								addQueuePoint(new Coordinate(hit.x, hit.y + i));
				//			}
				//		}
					}
				}
			}
			else {
				m_Orientation = ShipOrientation.UNKNOWN;
				for(int i = 0; i < amountToAdd; ++i)
				{
				//	if(hit.x -1 > 0)
				//	{
				//		if(myProbablityMap[hit.x-1][hits.get(0).y] > 0)
				//		{
							addQueuePoint(new Coordinate(hit.x - i, hit.y));
				//		}
				//	}
				}
				for(int i = 0; i < amountToAdd; ++i)
				{
				//	if(hit.x + 1 < mapLength)
				//	{
				//		if(myProbablityMap[hit.x+1][hits.get(0).y] > 0)
				//		{
							addQueuePoint(new Coordinate(hit.x + i, hit.y));
				//		}
				//	}
				}
				for(int i = 0; i < amountToAdd; ++i)
				{
					addQueuePoint(new Coordinate(hit.x, hit.y - 1));
				}
				for(int i = 0; i < amountToAdd; ++i)
				{
					addQueuePoint(new Coordinate(hit.x, hit.y + 1));
				}
			}
			
		}
		
		private void addQueuePoint(Coordinate queuePoint)
		{
			//Points added to the queue must be within the ocean's bounds and also not be already fired at and not already be in the queue
			if ((onGrid(queuePoint)) && (getOceanMap(queuePoint) == OceanStatus.UNKNOWN) && (searchCoordinates(queuePoint, queue) == -1))
			{
				queue.add(queuePoint);
			}
		}
		
		private void organizeQueue(ShipOrientation orientation)
		{
			for (int i = 0; i < queue.size(); i ++)
			{
				switch(orientation)
				{
					case HORIZONTAL:
						if (!(queue.get(i).y == hits.get(0).y))
						{
							queue.remove(i);
							i -= 1;
						}
						break;
					case VERTICAL:
						if (!(queue.get(i).x == hits.get(0).x))
						{
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
		private boolean isChange(int varZero, int varOne)
		{
			return !(varZero == varOne);
		}
	}

	private boolean onGrid(Coordinate coord)
	{
		return ((coord.x >= 0) && (coord.x < mapLength) && (coord.y >= 0) && (coord.y < mapHeight));
	}
	
	private OceanStatus getOceanMap(Coordinate coord)
	{
		return oceanMap[coord.x][coord.y];
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
	
	public void removeQueuePoint(Coordinate c)
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
	public void removeQueuePoint(int x, int y)
	{
		removeQueuePoint(new Coordinate(x, y));
	}
	public void generateMap()
	{
		for(int i = 0; i < mapLength; ++i)
		{
			for(int j = 0; j < mapHeight; ++j)
			{
				myProbablityMap[i][j] = 1;
			}
		}
//		myProbablityMap[7][3] += getRandom(AI_OPEN_LOW_MIN, AI_OPEN_LOW_MAX);
//		myProbablityMap[6][2] += getRandom(AI_OPEN_LOW_MIN, AI_OPEN_LOW_MAX);
//		myProbablityMap[3][7] += getRandom(AI_OPEN_LOW_MIN, AI_OPEN_LOW_MAX);
//		myProbablityMap[2][6] += getRandom(AI_OPEN_LOW_MIN, AI_OPEN_LOW_MAX);
//		myProbablityMap[6][6] += getRandom(AI_OPEN_LOW_MIN, AI_OPEN_LOW_MAX);
//		myProbablityMap[3][3] += getRandom(AI_OPEN_LOW_MIN, AI_OPEN_LOW_MAX);
//		myProbablityMap[5][5] += getRandom(AI_OPEN_LOW_MIN, AI_OPEN_LOW_MAX);
//		myProbablityMap[4][4] += getRandom(AI_OPEN_LOW_MIN, AI_OPEN_LOW_MAX);
//		myProbablityMap[0][8] += getRandom(AI_OPEN_MED_MIN, AI_OPEN_MED_MAX);
//		myProbablityMap[8][0] += getRandom(AI_OPEN_MED_MIN, AI_OPEN_MED_MAX);
//		myProbablityMap[1][9] += getRandom(AI_OPEN_HIGH_MIN, AI_OPEN_HIGH_MAX);
//		myProbablityMap[9][1] += getRandom(AI_OPEN_HIGH_MIN, AI_OPEN_HIGH_MAX);
//		myProbablityMap[9][9] += getRandom(AI_OPEN_HIGH_MIN, AI_OPEN_HIGH_MAX);
//		myProbablityMap[0][0] += getRandom(AI_OPEN_HIGH_MIN, AI_OPEN_HIGH_MAX);
//		myProbablityMap[1][9] += getRandom(AI_OPEN_HIGH_MIN, AI_OPEN_HIGH_MAX);
	}
	private void setProbablity(Coordinate coord, int score)
	{
		myProbablityMap[coord.x][coord.y] = score;
	}
	public int getShipSize(Ship.ShipType_t myShipType)
	{
		int size = 0;
		
		switch(myShipType)
		{
			case SHIP_BATTLESHIP:
				size = BATTLESHIP_SIZE;
				break;
			case SHIP_CARRIER:
				size = CARRIER_SIZE;
				break;
			case SHIP_CRUISER:
				size = CRUISER_SIZE;
				break;
			case SHIP_DESTROYER:
				size = DESTROYER_SIZE;
				break;
			case SHIP_SUBMARINE:
				size = SUBMARINE_SIZE;
				break;
			default:
				break;
		}
		return size;
	}
	public Coordinate getHighestProbablityCoords()
	{
		double heighestProbablity = 0;
		for(int i = 0; i < mapLength; ++i)
		{
			for(int j = 0; j < mapHeight; ++j)
			{
				if(myProbablityMap[i][j] > heighestProbablity)
				{
					heighestProbablity = myProbablityMap[i][j];
					return new Coordinate(i,j);
				}		
			}
		}
		return null;
	}
}

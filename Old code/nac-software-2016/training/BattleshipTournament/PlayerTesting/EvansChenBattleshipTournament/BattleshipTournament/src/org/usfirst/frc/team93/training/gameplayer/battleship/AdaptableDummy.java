/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean.PlaceResult_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.Orientation_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.TrapperKillerPlayer.OceanStatus;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author evans.chen
 *
 */
public class AdaptableDummy extends GamePlayer {

	Random random_generator = new Random();
	
	public enum OceanStatus
	{
		HIT, MISS, UNKNOWN, NULL, SHIP;
	}
	
	public enum State
	{
		HUNT, KILL, NULL;
	}
	
	enum ShipOrientation
	{
		HORIZONTAL, VERTICAL, UNKNOWN, NULL;
	}
	
	enum FiringStrategy
	{
		RANDOM, CENTER, EDGE, NULL;
	}
	
	enum PlacingStrategy
	{
		RANDOM, CENTER, EDGE, NULL;
	}
	
	OceanStatus[][] oceanMap = new OceanStatus[10][10];
	State state = State.HUNT;
	
	List<TargetShip> TargetShipList = new ArrayList<TargetShip>();
	
	List<Ship.ShipType_t> RemainingShips = new ArrayList<Ship.ShipType_t>();
	
	EnumMap<Ship.ShipType_t, int[][]> probability = new EnumMap<Ship.ShipType_t, int[][]>(Ship.ShipType_t.class);
	
	int edgeWeight = 0;
	
	int opponentTurn = 0;
	
	int parityRandomizer = 0;
	
	int minimumPlacingActionLength = 3;
	int edgeShotThreshold = 12;
	List<Coordinate> opponentShots = new ArrayList<Coordinate>();
	List<FiringStrategy> opponentFiringStrategies = new ArrayList<FiringStrategy>();
	int opponentFiringStrategyTrackerLength = 5;
	
	int minimumFiringActionLength = 3;
	int minimumHitSample = 12;
	int edgeShipThreshold = 10;
	EnumMap<Ship.ShipType_t, List<Coordinate>> opponentHits = new EnumMap<Ship.ShipType_t, List<Coordinate>>(Ship.ShipType_t.class);
	List<PlacingStrategy> opponentPlacingStrategies = new ArrayList<PlacingStrategy>();
	int opponentPlacingStrategyTrackerLength = 5;
	
	/**
	 * @param own_ocean
	 */
	public AdaptableDummy() 
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
		resetProbability();
		clearOcean();
		clearTargets();
		resetRemainingShips();
		state = State.HUNT;
		resetOpponentTracker();
		randomizeFiring();
		setFireMode(getFiringStrategy(opponentPlacingStrategies));
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
		//Required: place five ships, one of each type, with no collisions
		placeShips(getPlacingStrategy(opponentFiringStrategies));
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
		//Coordinate fireCoordinate = chooseFire();
		Coordinate fireCoordinate = new Coordinate(0, 0);
		
		//fire!
		GameBoard.FireResult result = fire(fireCoordinate);
		
		//process result.
		updateState(fireCoordinate, result);
		updateOceanMap(fireCoordinate, result);
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
		opponentShots.add(new Coordinate(result.coord.x, result.coord.y));
		opponentTurn += 1;
	}

	private void placeShips(PlacingStrategy p)
	{
		switch(p)
		{
			case RANDOM:
				placeShipsRandomlyEnhanced();
				break;
			case CENTER:
				placeShipsRandomlyEnhanced();
				break;
			case EDGE:
				placeShipsEdges();
				break;
			default:
				placeShipsRandomlyEnhanced();
				break;
		}
	}
	private void placeShipsRandomly()
	{
		int x = random_generator.nextInt(10);
		int y = random_generator.nextInt(10);
		int o = random_generator.nextInt(2);
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), RemainingShips.get(i))) != PlaceResult_t.E_OK)
			{
				x = random_generator.nextInt(10);
				y = random_generator.nextInt(10);
				o = random_generator.nextInt(2);
			}
		}
	}
	private void placeShipsRandomlyEnhanced()
	{
		//create and open the set.
		boolean[][] free = new boolean[10][10];
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				free[i][j] = true;
			}
		}
		//get an initial guess.
		int x = random_generator.nextInt(10);
		int y = random_generator.nextInt(10);
		int o = random_generator.nextInt(4);
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			boolean placed = false;
			while(!placed)
			{
				x = random_generator.nextInt(10);
				y = random_generator.nextInt(10);
				o = random_generator.nextInt(2);
				//try a place
				if (checkShipEnhanced(x, y, RemainingShips.get(i), getOrientationFromInt(o), free))
				{
					//see if it was successful
					placed = placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), RemainingShips.get(i))) == PlaceResult_t.E_OK;
				}
				//if successful...
				if (placed)
				{
					//block off points
					int dirX = 0;
					int dirY = 0;
					switch(getOrientationFromInt(o))
					{
						case OR_LEFT:
							dirX = -1;
							break;
						case OR_UP:
							dirY = -1;
							break;
						case OR_RIGHT:
							dirX = 1;
							break;
						case OR_DOWN:
							dirY = 1;
							break;
						default:
							break;
					}
					for (int p = 0; p < getShipLength(RemainingShips.get(i)); p++)
					{
						int pointX = x + (i * dirX);
						int pointY = y + (i * dirY);
						blockSurroundingPoints(pointX, pointY, free);
					}
				}
			}
		}
	}
	/**
	 * Checks to see if a ship is able to be placed in this area given an xy coordinate, a set of open points, a ship type, and an orientation.
	 * @param x The starting X of the ship.
	 * @param y The starting Y of the ship.
	 * @param ship The ship type.
	 * @param or The orientation of the ship.
	 * @param open The set of open points.
	 */
	private boolean checkShipEnhanced(int x, int y, Ship.ShipType_t ship, Ship.Orientation_t or, boolean[][] open)
	{
		boolean valid = true;
		//gets what points to check based on the direction of the ship.
		int dirX = 0;
		int dirY = 0;
		switch(or)
		{
			case OR_LEFT:
				dirX = -1;
				break;
			case OR_UP:
				dirY = -1;
				break;
			case OR_RIGHT:
				dirX = 1;
				break;
			case OR_DOWN:
				dirY = 1;
				break;
			default:
				valid = false;
				break;
		}
		//iterate through the points of the ship, checking each one.
		for (int i = 0; i < getShipLength(ship); i++)
		{
			int pointX = x + (i * dirX);
			int pointY = y + (i * dirY);
			//if the point is within boundaries
			if (checkWithin(pointX, pointY))
			{
				//and the point is within the open set
				if (open[pointX][pointY])
				{
					//then this point is valid
				}
				else
				{
					valid = false;
				}
			}
			else
			{
				valid = false;
			}
		}
		//if all points are valid, return true. Otherwise, return false.
		return valid;
	}
	/**
	 * Blocks the point and the surrounding points in a boolean 2D array.
	 * @param x
	 * @param y
	 * @param open The target boolean 2D array.
	 */
	private void blockSurroundingPoints(int x, int y, boolean[][] open)
	{
		blockPoint(x, y, open);
		blockPoint(x - 1, y, open);
		blockPoint(x, y - 1, open);
		blockPoint(x + 1, y, open);
		blockPoint(x, y + 1, open);
	}
	/**
	 * Blocks the point in the boolean 2D array.
	 * @param x
	 * @param y
	 * @param open The target boolean 2D array.
	 */
	private void blockPoint(int x, int y, boolean[][] open)
	{
		if (checkWithin(x, y))
		{
			open[x][y] = false;
		}
	}
	private void placeShipsClustered()
	{
		int x = random_generator.nextInt(10);
		int y = random_generator.nextInt(10);
		int o = random_generator.nextInt(2);
		List<Coordinate> clusterPoints = new ArrayList<Coordinate>();
		boolean[][] points = new boolean[10][10];
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				points[i][j] = false;
			}
		}
		
		//place the rest of the ships
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			if (i == 0)
			{
				//place the first ship randomly
				while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), RemainingShips.get(i))) != PlaceResult_t.E_OK)
				{
					x = random_generator.nextInt(10);
					y = random_generator.nextInt(10);
					o = random_generator.nextInt(2);
				}
			}
			else
			{
				//following ships use the stack
				boolean shipPlaced = false;
				for (int j = 0; (j < clusterPoints.size()) && (!shipPlaced); j++)
				{
					x = clusterPoints.get(j).x;
					y = clusterPoints.get(j).y;
					o = random_generator.nextInt(2);
					if (placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), RemainingShips.get(i))) == PlaceResult_t.E_OK)
					{
						shipPlaced = true;
					}
				}
				//if placing failed
				if (!shipPlaced)
				{
					//place ship randomly
					while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), RemainingShips.get(i))) != PlaceResult_t.E_OK)
					{
						x = random_generator.nextInt(10);
						y = random_generator.nextInt(10);
						o = random_generator.nextInt(2);
					}
				}
				
			}
			//then add surrounding points to the stack
			int l = getShipLength(RemainingShips.get(i));
			switch(getOrientationFromInt(o))
			{
				case OR_RIGHT:
					for (int n = 0; n < l; n++)
					{
						//add surrounding points to queue
						queueShipPoint(x + n - 1, y, clusterPoints, points);
						queueShipPoint(x + n, y - 1, clusterPoints, points);
						queueShipPoint(x + n + 1, y, clusterPoints, points);
						queueShipPoint(x + n, y + 1, clusterPoints, points);
					}
					break;
				case OR_DOWN:
					for (int n = 0; n < l; n++)
					{
						//add surrounding points to queue
						queueShipPoint(x - 1, y + n, clusterPoints, points);
						queueShipPoint(x, y + n - 1, clusterPoints, points);
						queueShipPoint(x + 1, y + n, clusterPoints, points);
						queueShipPoint(x, y + n + 1, clusterPoints, points);
					}
					break;
				default:
					break;
			}
		}
	}
	private void queueShipPoint(int x, int y, List<Coordinate> l, boolean[][] p)
	{
		if (checkWithin(x, y))
		{
			if (!(p[x][y]))
			{
				l.add(new Coordinate(x, y));
				p[x][y] = true;
			}
		}
	}
	private void placeShipsEdges()
	{
		placeShipsEdges(0, true);
	}
	private void placeShipsEdges(int border, boolean strict)
	{	
		//place the rest of the ships
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			int l = getShipLength(RemainingShips.get(i));
			boolean shipPlaced = false;
			int x = random_generator.nextInt(10);
			int y = random_generator.nextInt(10);
			int o = random_generator.nextInt(2);
			while(!((checkWithinEdge(y, border, strict) || checkWithinEdge(x, border, strict))))
			{
				x = random_generator.nextInt(10);
				y = random_generator.nextInt(10);
				o = random_generator.nextInt(2);
			}
			//if the piece is a horizontal edge piece...
			if (checkWithinEdge(y, border, strict))
			{
				if (random_generator.nextInt(2) == 0)
				{
					if (placeShip(new Ship(new Ship.GridCoord(x, y), Orientation_t.OR_LEFT, RemainingShips.get(i))) == PlaceResult_t.E_OK)
					{
						shipPlaced = true;
					}
					if (!shipPlaced)
					{
						if (placeShip(new Ship(new Ship.GridCoord(x, y), Orientation_t.OR_RIGHT, RemainingShips.get(i))) == PlaceResult_t.E_OK)
						{
							shipPlaced = true;
						}
					}
				}
				else
				{
					if (placeShip(new Ship(new Ship.GridCoord(x, y), Orientation_t.OR_RIGHT, RemainingShips.get(i))) == PlaceResult_t.E_OK)
					{
						shipPlaced = true;
					}
					if (!shipPlaced)
					{
						if (placeShip(new Ship(new Ship.GridCoord(x, y), Orientation_t.OR_LEFT, RemainingShips.get(i))) == PlaceResult_t.E_OK)
						{
							shipPlaced = true;
						}
					}
				}
			}
			//if the piece is a vertical edge piece...
			else if (checkWithinEdge(x, border, strict))
			{
				if (random_generator.nextInt(2) == 0)
				{
					if (placeShip(new Ship(new Ship.GridCoord(x, y), Orientation_t.OR_UP, RemainingShips.get(i))) == PlaceResult_t.E_OK)
					{
						shipPlaced = true;
					}
					if (!shipPlaced)
					{
						if (placeShip(new Ship(new Ship.GridCoord(x, y), Orientation_t.OR_DOWN, RemainingShips.get(i))) == PlaceResult_t.E_OK)
						{
							shipPlaced = true;
						}
					}
				}
				else
				{
					if (placeShip(new Ship(new Ship.GridCoord(x, y), Orientation_t.OR_DOWN, RemainingShips.get(i))) == PlaceResult_t.E_OK)
					{
						shipPlaced = true;
					}
					if (!shipPlaced)
					{
						if (placeShip(new Ship(new Ship.GridCoord(x, y), Orientation_t.OR_UP, RemainingShips.get(i))) == PlaceResult_t.E_OK)
						{
							shipPlaced = true;
						}
					}
				}
			}
			if (!shipPlaced)
			{
				x = random_generator.nextInt(10);
				y = random_generator.nextInt(10);
				o = random_generator.nextInt(2);
				while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), RemainingShips.get(i))) != PlaceResult_t.E_OK)
				{
					x = random_generator.nextInt(10);
					y = random_generator.nextInt(10);
					o = random_generator.nextInt(2);
				}
			}
		}
	}
	private boolean checkWithinEdge(int value, int border, boolean strict)
	{
		if (strict)
		{
			return ((value == 0 + border) || (value == 10 - 1 - border));
		}
		return ((value <= 0 + border) || (value >= 10 - 1 - border));
	}
	
	private void setFireMode(FiringStrategy f)
	{
		switch(f)
		{
			case RANDOM:
				edgeWeight = 0;
				break;
			case CENTER:
				edgeWeight = 0;
				break;
			case EDGE:
				edgeWeight = 100;
				break;
			default:
				edgeWeight = 0;
				break;
		}
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
				final int minimumProbability = 0;
				int maximum = minimumProbability;
				for (int i = 0; i < 10; i++)
				{
					for (int j = 0; j < 10; j++)
					{
						if ((oceanMap[i][j] == OceanStatus.UNKNOWN) && (((i + (11 * j) + parityRandomizer) % getShipMinLength()) == 0))
						{
							if (getProbabilityWeighted(i, j) > maximum)
							{
								x = i;
								y = j;
								maximum = getProbabilityWeighted(i, j);
							}
							else if (getProbabilityWeighted(i, j) < 0)
							{
								//print("Coordinate (" + i + ", " + j + " has been eliminated, Probability = " + getProbability(i, j) + ".");
							}
						}
					}
				}
				if (maximum == minimumProbability)
				{
					for (int i = 0; i < 10; i++)
					{
						for (int j = 0; j < 10; j++)
						{
							if (oceanMap[i][j] == OceanStatus.UNKNOWN)
							{
								if (getProbabilityWeighted(i, j) > maximum)
								{
									x = i;
									y = j;
									maximum = getProbabilityWeighted(i, j);
								}
							}
						}
					}
				}
				c = new Coordinate(x, y);
				break;
			case KILL:
				if (TargetShipList.get(0).queue.size() == 0)
				{
					print("WAT");
				}
				List<Coordinate> points = TargetShipList.get(0).queue;
				int max = 0;
				c = points.get(0);
				for (int i = 0; i < points.size(); i++)
				{
					if (getProbabilityWeighted(points.get(i).x, points.get(i).y) > max)
					{
						max = getProbabilityWeighted(points.get(i).x, points.get(i).y);
						c = points.get(i);
					}
				}
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
		removeQueuePoint(x, y);
		print(getName() + " fires at (" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
		printLog("(" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
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
				updateProbability(coord.x, coord.y, result);
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
				updateProbability(coord.x, coord.y, result);
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
					opponentHits.get(result.ship_type).add(new Coordinate(coord.x, coord.y));
				}
				break;
			case SHOT_SUNK:
				print("SUNK THE " + result.ship_type.name() + "!");
				printLog(", 1\n");
				updateProbability(coord.x, coord.y, result);
				//remove the ship from the target list, as it is destroyed.
				TargetShipList.remove(searchTargetListIndex(result.ship_type));
				RemainingShips.remove(result.ship_type);
				//set probability to 0 for all other squares for this ship, as ship is sunk
				for (int i = 0; i < 10; i ++)
				{
					for (int j = 0; j < 10; j ++)
					{
						setProbability(i, j, result.ship_type, 0);
					}
				}
				opponentHits.get(result.ship_type).add(new Coordinate(coord.x, coord.y));
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
	private void resetOpponentTracker()
	{
		updateOpponentFiringStrategyList(opponentFiringStrategyTrackerLength);
		updateOpponentPlacingStrategyList(opponentPlacingStrategyTrackerLength);
		opponentTurn = 0;
		opponentShots.clear();
		opponentHits.clear();
		for (Ship.ShipType_t s : Ship.ShipType_t.values())
		{
			opponentHits.put(s, new ArrayList<Coordinate>());
		}
	}
	private void randomizeFiring()
	{
		parityRandomizer = random_generator.nextInt(2);
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
	
	private void updateProbability(int x, int y, GameBoard.FireResult r)
	{
		switch(r.result_type)
		{
			case SHOT_MISS:
				//set probability to 0 and decrease the probabilities of neighboring tiles
				setProbability(x, y, 0);
				updateProbabilityMiss(x, y, true);
				break;
			case SHOT_HIT:
				//set probability to 0 and do not decrease the probabilities of neighboring tiles
				setProbability(x, y, 0);
				break;
			case SHOT_SUNK:
				//change hit tiles to act as obstructions, similar to miss
				setProbability(x, y, 0);
				updateProbabilitySunk(x, y, r);
				break;
			default:
				break;
		}
	}
	
	private void updateProbabilityMiss(int x, int y, boolean check)
	{
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			int l = getShipLength(RemainingShips.get(i));
			//horizontal
			for (int n = -l + 1; n < 1; n++)
			{
				//for this configuration...
				if ((checkWithin(x + n, y)) && (checkWithin(x + n + l - 1, y)))
				{
					boolean valid = true;
					for(int k = 0; k < l; k++)
					{
						if (oceanMap[x + n + k][y] != OceanStatus.UNKNOWN)
						{
							if (check)
							{
								valid = false;
							}
						}
					}
					if (valid)
					{
						for(int k = 0; k < l; k++)
						{
							incrementProbability(x + n + k, y, RemainingShips.get(i), -1);
							print("Coordinate (" + (x + n + k) + ", " + y + ") now has Probability = " + getProbabilityWeighted(x + n + k, y) + ".");
							//print("Subtracting (" + (x + n + k) + ", " + y + ").");
						}
					}
				}
			}
			
			//vertical
			for (int n = -l + 1; n < 1; n++)
			{
				//for this configuration...
				if ((checkWithin(x, y + n)) && (checkWithin(x, y + n + l - 1)))
				{
					boolean valid = true;
					for(int k = 0; k < l; k++)
					{
						if (oceanMap[x][y + n + k] != OceanStatus.UNKNOWN)
						{
							if (check)
							{
								valid = false;
							}
						}
					}
					if (valid)
					{
						for(int k = 0; k < l; k++)
						{
							incrementProbability(x, y + n + k, RemainingShips.get(i), -1);
							print("Coordinate (" + x + ", " + (y + n + k) + ") now has Probability = " + getProbabilityWeighted(x, y + n + k) + ".");
							//print("Subtracting (" + x + ", " + (y + n + k) + ").");
						}
					}
				}
			}
		}
	}
	private void updateProbabilitySunk(int x, int y, GameBoard.FireResult r)
	{
		Ship.ShipType_t s = r.ship_type;
		List<Coordinate> hits = searchTargetList(s).hits;
		for (int i = 0; i < hits.size(); i++)
		{
			if (getOceanMap(hits.get(i)) == OceanStatus.HIT)
			{
				updateProbabilityMiss(hits.get(i).x, hits.get(i).y, true);
				oceanMap[hits.get(i).x][hits.get(i).y] = OceanStatus.SHIP;
			}
		}
	}
	
	private int getProbability(int x, int y, Ship.ShipType_t s)
	{
		return (probability.get(s)[x][y]);
	}
	private int getProbability(int x, int y)
	{
		int total = 0;
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			total += getProbability(x, y, RemainingShips.get(i));
		}
		return total;
	}
	private int getProbabilityWeighted(int x, int y)
	{
		int prob = getProbability(x, y);
		if (checkWithinEdge(x, 0, true) || checkWithinEdge(y, 0, true))
		{
			prob += edgeWeight;
		}
		return prob;
	}
	
	private void setProbability(int x, int y, Ship.ShipType_t s, int value)
	{
		probability.get(s)[x][y] = value;
	}
	private void setProbability(int x, int y, int value)
	{
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			setProbability(x, y, RemainingShips.get(i), value);
		}
	}
	
	private void incrementProbability(int x, int y, Ship.ShipType_t s, int value)
	{
		setProbability(x, y, s, getProbability(x, y, s) + value);
	}
	private void incrementProbability(int x, int y, int value)
	{
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			setProbability(x, y, RemainingShips.get(i), getProbability(x, y, RemainingShips.get(i)) + value);
		}
	}
	
	private void resetProbability()
	{
		probability.clear();
		probability.put(Ship.ShipType_t.SHIP_CARRIER, calculateInitialProbability(Ship.ShipType_t.SHIP_CARRIER));
		probability.put(Ship.ShipType_t.SHIP_BATTLESHIP, calculateInitialProbability(Ship.ShipType_t.SHIP_BATTLESHIP));
		probability.put(Ship.ShipType_t.SHIP_CRUISER, calculateInitialProbability(Ship.ShipType_t.SHIP_CRUISER));
		probability.put(Ship.ShipType_t.SHIP_SUBMARINE, calculateInitialProbability(Ship.ShipType_t.SHIP_SUBMARINE));
		probability.put(Ship.ShipType_t.SHIP_DESTROYER, calculateInitialProbability(Ship.ShipType_t.SHIP_DESTROYER));
	}
	
	private int[][] calculateInitialProbability(Ship.ShipType_t s)
	{
		int[][] initialProbability = new int[10][10];
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				initialProbability[i][j] = 0;
			}
		}
		int shipLength = getShipLength(s);
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				//horizontal
				if (checkWithin(i + shipLength - 1, j))
				{
					for (int k = 0; k < shipLength; k++)
					{
						initialProbability[i + k][j] += 1;
					}
				}
				//vertical
				if (checkWithin(i, j + shipLength - 1))
				{
					for (int k = 0; k < shipLength; k++)
					{
						initialProbability[i][j + k] += 1;
					}
				}
			}
		}
		
		//print(Arrays.deepToString(initialProbability));
		return initialProbability;
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
			//print("Target's orientation is " + orientation.name() + ".");
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
	
	private FiringStrategy getShotType(GameBoard.FireResult r)
	{
		return(getShotType(r.coord.x, r.coord.y));
	}
	private FiringStrategy getShotType(Coordinate c)
	{
		return(getShotType(c.x, c.y));
	}
	private FiringStrategy getShotType(int x, int y)
	{
		if (checkWithinEdge(x, 0, true) || checkWithinEdge(y, 0, true))
		{
			return FiringStrategy.EDGE;
		}
		return FiringStrategy.CENTER;
	}
	private FiringStrategy getOpponentFiringStrategy(List<Coordinate> shots)
	{
		FiringStrategy target = FiringStrategy.RANDOM;
		
		//if any of the first 13 shots deviate from the center, then it is not center.
		boolean center = true;
		for (int i = 0; i < 13; i++)
		{
			if (!(getShotType(shots.get(i)) == FiringStrategy.CENTER))
			{
				center = false;
			}
		}
		//if any of the first n shots deviate from the edge, then it is not edge.
		boolean edge = true;
		for (int i = 0; i < edgeShotThreshold; i++)
		{
			if (!(getShotType(shots.get(i)) == FiringStrategy.EDGE))
			{
				edge = false;
			}
		}
		
		if (center)
		{
			target = FiringStrategy.CENTER;
		}
		else if (edge)
		{
			target = FiringStrategy.EDGE;
		}
		else
		{
			target = FiringStrategy.RANDOM;
		}
		return target;
	}
	private void updateOpponentFiringStrategyList(int length)
	{
		if (!opponentShots.isEmpty())
		{
			if(opponentFiringStrategies.size() >= length)
			{
				opponentFiringStrategies.remove(0);
			}
			opponentFiringStrategies.add(getOpponentFiringStrategy(opponentShots));
		}
	}
	private PlacingStrategy getPlacingStrategy(List<FiringStrategy> strategies)
	{
		PlacingStrategy shipPlacement = PlacingStrategy.RANDOM;
		if (strategies.isEmpty() || (strategies.size() < minimumPlacingActionLength))
		{
			return shipPlacement;
		}
		//find max
		int maximum = 0;
		FiringStrategy opponentStrategy = FiringStrategy.RANDOM;
		for (FiringStrategy f : FiringStrategy.values())
		{
			if (countFiringStrategy(strategies, f) > maximum)
			{
				maximum = countFiringStrategy(strategies, f);
				opponentStrategy = f;
			}
		}
		shipPlacement = getPlacingStrategyResponse(opponentStrategy);
		return shipPlacement;
	}
	private PlacingStrategy getPlacingStrategyResponse(FiringStrategy target)
	{
		PlacingStrategy p = PlacingStrategy.RANDOM;
		switch(target)
		{
			case RANDOM:
				p = PlacingStrategy.CENTER;
				break;
			case CENTER:
				p = PlacingStrategy.EDGE;
				break;
			case EDGE:
				p = PlacingStrategy.CENTER;
				break;
			default:
				p = PlacingStrategy.RANDOM;
				break;
		}
		return p;
	}
	private int countFiringStrategy(List<FiringStrategy> strategies, FiringStrategy strategy)
	{
		int count = 0;
		for (int i = 0; i < strategies.size(); i++)
		{
			if (strategies.get(i) == strategy)
			{
				count++;
			}
		}
		return count;
	}

	private PlacingStrategy getHitType(GameBoard.FireResult r)
	{
		return(getHitType(r.coord.x, r.coord.y));
	}
	private PlacingStrategy getHitType(Coordinate c)
	{
		return(getHitType(c.x, c.y));
	}
	private PlacingStrategy getHitType(int x, int y)
	{
		if (checkWithinEdge(x, 0, true) || checkWithinEdge(y, 0, true))
		{
			return PlacingStrategy.EDGE;
		}
		return PlacingStrategy.CENTER;
	}
	private PlacingStrategy getOpponentPlacingStrategy(EnumMap<Ship.ShipType_t, List<Coordinate>> hits)
	{
		//if not enough hits, null
		PlacingStrategy p = PlacingStrategy.RANDOM;
		if(getHitNumber(hits) < minimumHitSample)
		{
			return PlacingStrategy.NULL;
		}
		//IF THE GAME IS WON...
		else if (getHitNumber(hits) >= 17)
		{
			//count edge ships
			int edgeTiles = 0;
			for(Ship.ShipType_t s : Ship.ShipType_t.values())
			{
				for (int i = 0; i < hits.get(s).size(); i++)
				{
					if (checkWithinEdge(hits.get(s).get(i).x, 0, true) || checkWithinEdge(hits.get(s).get(i).y, 0, true))
					{
						edgeTiles++;
					}
				}
			}
			if (edgeTiles >= edgeShipThreshold)
			{
				p = PlacingStrategy.EDGE;
			}
			else if (edgeTiles == 0)
			{
				p = PlacingStrategy.CENTER;
			}
			else if (edgeTiles < edgeShipThreshold)
			{
				p = PlacingStrategy.RANDOM;
			}
		}
		// the game is not won, but there enough hits
		else
		{
			//do nothing for now
			return PlacingStrategy.NULL;
		}
		return p;
	}
	private int getHitNumber(EnumMap<Ship.ShipType_t, List<Coordinate>> hits)
	{
		int c = 0;
		for(Ship.ShipType_t s : Ship.ShipType_t.values())
		{
			c += hits.get(s).size();
		}
		return c;
	}
	private void updateOpponentPlacingStrategyList(int length)
	{
		if (!opponentHits.isEmpty())
		{
			if (getOpponentPlacingStrategy(opponentHits) != PlacingStrategy.NULL)
			{
				if(opponentPlacingStrategies.size() >= length)
				{
					opponentPlacingStrategies.remove(0);
				}
				opponentPlacingStrategies.add(getOpponentPlacingStrategy(opponentHits));
			}
		}
	}
	private FiringStrategy getFiringStrategy(List<PlacingStrategy> strategies)
	{
		FiringStrategy shotPlacement = FiringStrategy.RANDOM;
		if (strategies.isEmpty() || (strategies.size() < minimumFiringActionLength))
		{
			return shotPlacement;
		}
		//find max
		int maximum = 0;
		PlacingStrategy opponentStrategy = PlacingStrategy.RANDOM;
		for (PlacingStrategy f : PlacingStrategy.values())
		{
			if (countPlacingStrategy(strategies, f) > maximum)
			{
				maximum = countPlacingStrategy(strategies, f);
				opponentStrategy = f;
			}
		}
		shotPlacement = getFiringStrategyResponse(opponentStrategy);
		return shotPlacement;
	}
	private FiringStrategy getFiringStrategyResponse(PlacingStrategy target)
	{
		FiringStrategy p = FiringStrategy.RANDOM;
		switch(target)
		{
			case RANDOM:
				p = FiringStrategy.CENTER;
				break;
			case CENTER:
				p = FiringStrategy.CENTER;
				break;
			case EDGE:
				p = FiringStrategy.EDGE;
				break;
			default:
				p = FiringStrategy.RANDOM;
				break;
		}
		return p;
	}
	private int countPlacingStrategy(List<PlacingStrategy> strategies, PlacingStrategy strategy)
	{
		int count = 0;
		for (int i = 0; i < strategies.size(); i++)
		{
			if (strategies.get(i) == strategy)
			{
				count++;
			}
		}
		return count;
	}
	
	private void printLog(String s)
	{
		//System.out.print(s);
	}
}

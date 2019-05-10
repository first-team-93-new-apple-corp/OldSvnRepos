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
public class AdaptableIIPlayer extends GamePlayer {

	//the random number generator.
	Random random_generator = new Random();
	
	/**
	 * The types of states that each tile in the ocean can be in.
	 */
	public enum OceanStatus
	{
		HIT, MISS, UNKNOWN, NULL, SHIP;
	}
	
	/**
	 * the type of states that the program can be in.
	 * In HUNT mode, the program is looking for an enemy ship.
	 * in KILL mode, the program has already found an enemy ship and is working to destroy it.
	 */
	public enum State
	{
		HUNT, KILL, NULL;
	}
	
	/**
	 * The different types of orientations that an enemy ship can be in.
	 */
	enum ShipOrientation
	{
		HORIZONTAL, VERTICAL, UNKNOWN, NULL;
	}
	
	/**
	 * The different types of firing strategies that the program can use.
	 * Random and Center mean that the normal Probability Density function will be used.
	 * Edge means that the enemy ship placement is predicted to be on the edges, so the program will fire at edges first.
	 */
	enum FiringStrategy
	{
		RANDOM, CENTER, EDGE, NULL;
	}
	
	/**
	 * The different types of ship placing strategies that the program can use.
	 * Random and Center mean that the ships will be placed randomly.
	 * Edges means that the ships will be placed on the edges, usually to avoid the enemy's Probability Density Function.
	 */
	enum PlacingStrategy
	{
		RANDOM, CENTER, EDGE, NULL;
	}
	
	//the map of the enemy ocean's tile states.
	OceanStatus[][] oceanMap = new OceanStatus[10][10];
	
	//the current state of the program.
	State state = State.HUNT;
	
	//The list of ships that have not yet been sunk, but have been hit.
	//This is what the program actively targets when in KILL mode.
	List<TargetShip> TargetShipList = new ArrayList<TargetShip>();
	
	//The list of the remaining enemy ships yet to sink.
	List<Ship.ShipType_t> RemainingShips = new ArrayList<Ship.ShipType_t>();
	
	//The probability map, that holds arrays of probabilities for each ship.
	//This is used for the Probability Density Function.
	EnumMap<Ship.ShipType_t, int[][]> probability = new EnumMap<Ship.ShipType_t, int[][]>(Ship.ShipType_t.class);
	
	//The amount of probability to weight the edges by.
	//Set this to a high number to shoot at edges first.
	int edgeWeight = 0;
	
	//Which turn the opponent is on.
	int opponentTurn = 0;
	
	//Used to make the Probability Density function non-deterministic, and less exploitable.
	int parityRandomizer = 0;
	
	//These are used for the program's placing strategy adaptation,
	//in response to the opponent's firing strategies.
	int minimumPlacingActionLength = 3;
	int edgeShotThreshold = 4;
	List<Coordinate> opponentShots = new ArrayList<Coordinate>();
	List<FiringStrategy> opponentFiringStrategies = new ArrayList<FiringStrategy>();
	int opponentFiringStrategyTrackerLength = 10;
	
	//These are used for the program's firing strategy adaptation,
	//in response to the opponent's placing strategies.
	int minimumFiringActionLength = 3;
	int minimumHitSample = 12;
	int edgeShipThreshold = 10;
	EnumMap<Ship.ShipType_t, List<Coordinate>> opponentHits = new EnumMap<Ship.ShipType_t, List<Coordinate>>(Ship.ShipType_t.class);
	List<PlacingStrategy> opponentPlacingStrategies = new ArrayList<PlacingStrategy>();
	int opponentPlacingStrategyTrackerLength = 10;
	
	/**
	 * Constructor for the Player.
	 * @param own_ocean
	 */
	public AdaptableIIPlayer() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Notification that a new match is about to start. 
	 * Is called at the start of each game of Battleship.
	 */
	@Override
	public void notifyReset()
	{
		//resets the probability density map
		resetProbability();
		//clears the ocean tiles of their state back to UNKNOWN
		clearOcean();
		//removes all active target ships, as it is a new game
		clearTargets();
		//the opponent gets all their ships back, so reset the list of remaining enemy ships
		resetRemainingShips();
		//Reset state to the HUNT state, as know ships' locations are known anymore
		state = State.HUNT;
		//reset and update the opponent placing and firing strategy tracker
		resetOpponentTracker();
		//randomize parity
		randomizeFiring();
		//Pick a firing mode to counter the opponent
		setFireMode(getFiringStrategy(opponentPlacingStrategies));
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#placeShips()
	 */
	/**
	 * Place your ships.  You must place exactly five.  One of each type.
	 * This is called following notifyReset().
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
		Coordinate fireCoordinate = chooseFire();
		
		//fire!
		GameBoard.FireResult result = fire(fireCoordinate);
		
		//process result, by updating the ocean tiles and probability density.
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
		//Update the opponent shot array
		opponentShots.add(new Coordinate(result.coord.x, result.coord.y));
		opponentTurn += 1;
	}

	/**
	 * Place ships according to a PlacingStrategy.
	 * @param p The PlacingStrategy to use.
	 * One can use RANDOM, CENTER, or EDGE ship placing.
	 */
	private void placeShips(PlacingStrategy p)
	{
		switch(p)
		{
			case RANDOM:
				placeShipsRandomly();
				break;
			case CENTER:
				placeShipsRandomly();
				break;
			case EDGE:
				placeShipsEdges();
				break;
			default:
				placeShipsRandomly();
				break;
		}
	}
	/**
	 * Place the ships randomly.
	 */
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
	/**
	 * Place the ships in a clustered formation.
	 * This is a statistically unfavorable way to place ships.
	 */
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
	/**
	 * DO NOT USE THIS METHOD!
	 * This is only used internally for ship placing.
	 * @param x The x of the point to queue for a ship placement test.
	 * @param y The y of the point to queue for a ship placement test.
	 * @param l The list to add the point to.
	 * @param p The point array to update.
	 */
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
	/**
	 * Default for placing ships on edges.
	 * This is the statistically best way to place ships on the edges.
	 */
	private void placeShipsEdges()
	{
		placeShipsEdges(0, true);
	}
	/**
	 * Place the ships on the edges.
	 * @param border How far from the edge is allowed.
	 * 0 yields best results.
	 * @param strict Whether the ships' distance from the edges is allowed using == or <=.
	 */
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
	/**
	 * Checks whether an X or Y value lies within the edge.
	 * @param value The X or Y value to check.
	 * @param border How far from the edge is allowed.
	 * @param strict Whether the point's distance from the edges is evaluated using == or <=.
	 * @return
	 */
	private boolean checkWithinEdge(int value, int border, boolean strict)
	{
		if (strict)
		{
			return ((value == 0 + border) || (value == 10 - 1 - border));
		}
		return ((value <= 0 + border) || (value >= 10 - 1 - border));
	}
	
	/**
	 * Fire at the enemy according to a FiringStrategy.
	 * @param f The FiringStrategy to use.
	 * One can use RANDOM, CENTER, or EDGE firing.
	 */
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
	
	/**
	 * Pick a coordinate to fire at.
	 * @return The coordinate to fire at.
	 */
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
	
	/**
	 * Fire at an XY coordinate.
	 * @param x The X of the coordinate to fire at.
	 * @param y The Y of the coordinate to fire at.
	 * @return The turn result.
	 */
	private GameBoard.FireResult fire(int x, int y)
	{
		GameBoard.FireResult result = fireShot(new Ship.GridCoord(x, y));
		removeQueuePoint(x, y);
		print(getName() + " fires at (" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
		printLog("(" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
		return (result);
	}
	/**
	 * Fire at an XY coordinate.
	 * @param c The Coordinate to fire at.
	 * @return The turn result.
	 */
	private GameBoard.FireResult fire(Coordinate c)
	{
		return (fire(c.x, c.y));
	}
	
	/**
	 * Update the ocean map using the coordinate fired at and the result.
	 * @param coord
	 * @param result
	 */
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
	
	/**
	 * Update the program's state using the coordinate fired at and the result.
	 * @param coord
	 * @param result
	 */
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
	
	/**
	 * Clear the ocean tile states, at the beginning of a new game.
	 */
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
	/**
	 * Clear the list of active targets, as a new game changes ship placement.
	 */
	private void clearTargets()
	{
		TargetShipList.clear();
	}
	/**
	 * Reset the opponent's remaining ships, as a new game gives them their ships back.
	 */
	private void resetRemainingShips()
	{
		RemainingShips.clear();
		RemainingShips.add(Ship.ShipType_t.SHIP_CARRIER);
		RemainingShips.add(Ship.ShipType_t.SHIP_BATTLESHIP);
		RemainingShips.add(Ship.ShipType_t.SHIP_CRUISER);
		RemainingShips.add(Ship.ShipType_t.SHIP_SUBMARINE);
		RemainingShips.add(Ship.ShipType_t.SHIP_DESTROYER);
	}
	/**
	 * Update the opponent strategy list, and clear the lists of the shots last game, as this is a new game.
	 */
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
	/**
	 * Randomize parity so that firing is not deterministic.
	 */
	private void randomizeFiring()
	{
		parityRandomizer = random_generator.nextInt(2);
	}
	
	/**
	 * Get the length of a ship.
	 * @param s The ship to get the length of.
	 * @return The integer length of the ship.
	 */
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
	
	/**
	 * Gets the minimum length of the remaining ships.
	 * Useful for updating parity.
	 * @return The integer length of the smallest remaining ship.
	 */
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
	
	/**
	 * Updates the probability density map, after a shot is taken at the opponent's ocean.
	 * @param x The x of the coordinate to update.
	 * @param y The y of the coordinate to update.
	 * @param r The result of the shot taken.
	 */
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
	
	/**
	 * Updates the probability density map, in the case of a miss.
	 * @param x The x of the coordinate to update.
	 * @param y The y of the coordinate to update.
	 * @param check Whether to avoid already-counted misses.
	 */
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
	/**
	 * Updates the probability density map, in the case of a hit.
	 * @param x The x of the coordinate to update.
	 * @param y The y of the coordinate to update.
	 * @param r The result of the shot taken.
	 */
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
	
	/**
	 * Gets a single ship's probability for the specified coordinate.
	 * @param x The X of the coordinate to get the probability of.
	 * @param y The Y of the coordinate to get the probability of.
	 * @param s The ship to get the probability of.
	 * @return The number of ways that the specified ship can be located at the specified coordinate.
	 */
	private int getProbability(int x, int y, Ship.ShipType_t s)
	{
		return (probability.get(s)[x][y]);
	}
	/**
	 * Gets the sum of all of the ships' probabilities for one coordinate.
	 * @param x The X of the coordinate to get the total probability of.
	 * @param y The Y of the coordinate to get the total probability of.
	 * @return The number of ways that any remaining ship can be located at the specified coordinate.
	 */
	private int getProbability(int x, int y)
	{
		int total = 0;
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			total += getProbability(x, y, RemainingShips.get(i));
		}
		return total;
	}
	/**
	 * Gets the weighted probability of a coordinate.
	 * USE THIS FUNCTION!
	 * @param x The X of the coordinate to get the weighted probability of.
	 * @param y The Y of the coordinate to get the weighted probability of.
	 * @return The weighted probability of the specified coordinate.
	 */
	private int getProbabilityWeighted(int x, int y)
	{
		int prob = getProbability(x, y);
		if (checkWithinEdge(x, 0, true) || checkWithinEdge(y, 0, true))
		{
			prob += edgeWeight;
		}
		return prob;
	}
	
	/**
	 * Sets the probability of a specified ship for a specified coordinate.
	 * @param x The X of the coordinate to set the probability.
	 * @param y The Y of the coordinate to set the probability.
	 * @param s The ship to set the probability.
	 * @param value The value to set the probability to.
	 */
	private void setProbability(int x, int y, Ship.ShipType_t s, int value)
	{
		probability.get(s)[x][y] = value;
	}
	/**
	 * Sets the probability of a coordinate to a value for ALL ships.
	 * @param x The X of the coordinate to set the probability.
	 * @param y The Y of the coordinate to set the probability.
	 * @param value The value to set ALL ships' probability maps to for this coordinate.
	 */
	private void setProbability(int x, int y, int value)
	{
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			setProbability(x, y, RemainingShips.get(i), value);
		}
	}
	
	/**
	 * Increments the probability of a specified ship for a specified coordinate.
	 * @param x The X of the coordinate to increment the probability.
	 * @param y The Y of the coordinate to increment the probability.
	 * @param s The ship to increment the probability.
	 * @param value The value to increment the probability by.
	 */
	private void incrementProbability(int x, int y, Ship.ShipType_t s, int value)
	{
		setProbability(x, y, s, getProbability(x, y, s) + value);
	}
	/**
	 * Increments the probability of a coordinate by a value for ALL ships.
	 * @param x The X of the coordinate to increment the probability.
	 * @param y The Y of the coordinate to increment the probability.
	 * @param value The value to increment ALL ships' probability maps by for this coordinate.
	 */
	private void incrementProbability(int x, int y, int value)
	{
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			setProbability(x, y, RemainingShips.get(i), getProbability(x, y, RemainingShips.get(i)) + value);
		}
	}
	
	/**
	 * Resets the probability map at the beginning of a new game.
	 */
	private void resetProbability()
	{
		probability.clear();
		probability.put(Ship.ShipType_t.SHIP_CARRIER, calculateInitialProbability(Ship.ShipType_t.SHIP_CARRIER));
		probability.put(Ship.ShipType_t.SHIP_BATTLESHIP, calculateInitialProbability(Ship.ShipType_t.SHIP_BATTLESHIP));
		probability.put(Ship.ShipType_t.SHIP_CRUISER, calculateInitialProbability(Ship.ShipType_t.SHIP_CRUISER));
		probability.put(Ship.ShipType_t.SHIP_SUBMARINE, calculateInitialProbability(Ship.ShipType_t.SHIP_SUBMARINE));
		probability.put(Ship.ShipType_t.SHIP_DESTROYER, calculateInitialProbability(Ship.ShipType_t.SHIP_DESTROYER));
	}
	
	/**
	 * Calculates the initial probability map for a specified ship and creates the probability map accordingly.
	 * @param s The ship for which to calculate the inital probability map.
	 * @return The calculated initial probability map for the ship.
	 */
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
	
	/**
	 * The class defining a coordinate for this player.
	 */
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
	
	/**
	 * The class defining a TargetShip for this player.
	 * TargetShips are enemy ships that the program has discovered via a HIT.
	 * When a ship is HIT, then this class represents that ship.
	 * This class keeps track of the ship's location, orientation, and future firing queue.
	 */
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
	
	/**
	 * Checks of a specific X and Y is within the bounds of the ocean.
	 * The bounds of the ocean are X:[0, 9], Y:[0, 9].
	 * @param x
	 * @param y
	 * @return Whether or not the xy-pair is within the ocean's bounds.
	 */
	private boolean checkWithin(int x, int y)
	{
		return ((x >= 0) && (x < 10) && (y >= 0) && (y < 10));
	}
	/**
	 * Checks of a Coordinate is within the bounds of the ocean.
	 * The bounds of the ocean are X:[0, 9], Y:[0, 9].
	 * @param c
	 * @return Whether or not the coordinate is within the ocean's bounds.
	 */
	private boolean checkWithin(Coordinate c)
	{
		return checkWithin(c.x, c.y);
	}
	
	/**
	 * Gets an ocean tile's status for a given coordinate.
	 * @param c The coordinate to check an ocean tile's status for.
	 * @return The ocean tile status for the given coordinate.
	 */
	private OceanStatus getOceanMap(Coordinate c)
	{
		return oceanMap[c.x][c.y];
	}
	
	/**
	 * Checks if a ship is a valid ship type.
	 * All "real" ship types are valid.
	 * Ships that can not actually exist, are not valid, such as SHIP_TYPE_NULL AND _SHIP_TOP.
	 * @param type
	 * @return whether the ship type is valid or not.
	 */
	private boolean checkShipType(Ship.ShipType_t type)
	{
		return (!(((type == Ship.ShipType_t.SHIP_NULL) || (type == Ship.ShipType_t._SHIP_TOP))));
	}
	
	/**
	 * Searches the TargetShip list for a certain type of ship.
	 * @param type The ship type to search for.
	 * @return The TargetShip, if the ship type is found. Otherwise, return null.
	 */
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
	/**
	 * Searches the TargetShip list for a certain type of ship, and returns an index.
	 * @param type The ship type to search for.
	 * @returnThe TargetShip's index, if the ship type is found. Otherwise, return -1.
	 */
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
	
	/**
	 * Searches for the index of a coordinate c in a coordinate list l.
	 * @param c The coordinate to search for.
	 * @param l The list in which to search.
	 * @return The index of the coordinate in the list.
	 */
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
	
	/**
	 * Remove a point from the TargetShip queue if it has already been shot at.
	 * @param c The coordinate to remove.
	 */
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
	/**
	 * Remove an xy point from the TargetShip queue if it has already been shot at.
	 * @param x The x of the coordinate to remove.
	 * @param y The y of the coordinate to remove.
	 */
	private void removeQueuePoint(int x, int y)
	{
		removeQueuePoint(new Coordinate(x, y));
	}
	
	/**
	 * Maps in integer to an orientation for use with the random_generator.
	 * @param o An integer.
	 * @return An orientation.
	 */
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
	
	/**
	 * These functions are used for determining the opponent's firing strategy,
	 * and picking a placing strategy to counter it.
	 */
	/**
	 * Get whether a specific enemy shot is aimed at the center or the edges.
	 * @param r The firing result given by notifyOpponentShot().
	 * @return Whether the enemy shot is aimed at the edges or the center.
	 */
	private FiringStrategy getShotType(GameBoard.FireResult r)
	{
		return(getShotType(r.coord.x, r.coord.y));
	}
	/**
	 * Get whether a specific enemy shot is aimed at the center or the edges.
	 * @param c The coordinate of the enemy shot.
	 * @return Whether the enemy shot is aimed at the edges or the center.
	 */
	private FiringStrategy getShotType(Coordinate c)
	{
		return(getShotType(c.x, c.y));
	}
	/**
	 * Get whether a specific enemy shot is aimed at the center or the edges.
	 * @param x The X of the coordinate of the enemy shot.
	 * @param y The Y of the coordinate of the enemy shot.
	 * @return Whether the enemy shot is aimed at the edges or the center.
	 */
	private FiringStrategy getShotType(int x, int y)
	{
		if (checkWithinEdge(x, 0, true) || checkWithinEdge(y, 0, true))
		{
			return FiringStrategy.EDGE;
		}
		return FiringStrategy.CENTER;
	}
	/**
	 * Determines the opponent's firing strategy this game.
	 * @param shots The list of enemy shots.
	 * @return The opponent's firing strategy this game.
	 */
	private FiringStrategy getOpponentFiringStrategy(List<Coordinate> shots)
	{
		FiringStrategy target = FiringStrategy.RANDOM;
		
		//if any of the first 13 shots deviate from the center, then it is not center firing.
		boolean center = true;
		for (int i = 0; i < 13; i++)
		{
			if (!(getShotType(shots.get(i)) == FiringStrategy.CENTER))
			{
				center = false;
			}
		}
		//if any of the first few shots deviate from the edge, then it is not edge firing.
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
	/**
	 * Updates the running list of the opponent's firing strategies.
	 * @param length The length of the list to keep.
	 */
	private void updateOpponentFiringStrategyList(int length)
	{
		if (!opponentShots.isEmpty())
		{
			if ((opponentFiringStrategies.size() >= length) && (length != 0))
			{
				opponentFiringStrategies.remove(0);
			}
			opponentFiringStrategies.add(getOpponentFiringStrategy(opponentShots));
		}
	}
	/**
	 * Picks the ship placing strategy determined to counter the opponent's firing strategy.
	 * This is determined by looking at the opponent's past strategies.
	 * @param strategies The list of the most recent opponent firing strategies.
	 * @return The best ship placing strategy to use.
	 */
	private PlacingStrategy getPlacingStrategy(List<FiringStrategy> strategies)
	{
		PlacingStrategy shipPlacement = PlacingStrategy.RANDOM;
		if (strategies.isEmpty() || (strategies.size() < minimumFiringActionLength))
		{
			return shipPlacement;
		}
		//find most likely following action
		FiringStrategy last = strategies.get(strategies.size() - 1);
		int maximum = 0;
		FiringStrategy opponentStrategy = FiringStrategy.RANDOM;
		for (FiringStrategy f : FiringStrategy.values())
		{
			if (countFiringStrategy(strategies, last, f) > maximum)
			{
				maximum = countFiringStrategy(strategies, last, f);
				opponentStrategy = f;
			}
		}
		shipPlacement = getPlacingStrategyResponse(opponentStrategy);
		return shipPlacement;
	}
	/**
	 * Gives the counter PlacingStrategy to a given FiringStrategy.
	 * @param target The FiringStrategy of the opponent.
	 * @return The best PlacingStrategy to use against the FiringStrategy.
	 */
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
	/**
	 * Counts the number of one specific strategy out of the opponent firing strategies in the running list.
	 * @param strategies The opponent's firing strategy list.
	 * @param strategy The strategy to count.
	 * @return The number of the specified strategy counted in the list of opponent firing strategies.
	 */
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
	/**
	 * Counts the number of a specified strategy following a specified strategy out of the opponent firing strategies in the running list.
	 * This is used for pattern recognition.
	 * @param strategies The opponent's firing strategy list.
	 * @param strategy The given strategy.
	 * @param followingStrategy The strategy following the given strategy.
	 * @return The number of followingStrategy following the strategy counted in the list of opponent firing strategies. 
	 */
	private int countFiringStrategy(List<FiringStrategy> strategies, FiringStrategy strategy, FiringStrategy followingStrategy)
	{
		int count = 0;
		for (int i = 0; i < strategies.size() - 1; i++)
		{
			if ((strategies.get(i) == strategy) && (strategies.get(i + 1) == followingStrategy))
			{
				count++;
			}
		}
		return count;
	}
	/**
	 * Clear the running list of opponent firing strategies.
	 * Used for debug purposes.
	 */
	public void clearFiringMemory()
	{
		opponentFiringStrategies.clear();
	}
	
	/**
	 * Get whether a specific hit on an enemy ship is on center or the edges.
	 * @param r The firing result given by the firing function.
	 * @return Whether the hit on the enemy ship is on the edges or the center.
	 */
	private PlacingStrategy getHitType(GameBoard.FireResult r)
	{
		return(getHitType(r.coord.x, r.coord.y));
	}
	/**
	 * Get whether a specific hit on an enemy ship is on center or the edges.
	 * @param c The coordinate of the hit on an enemy ship.
	 * @return Whether the hit on the enemy ship is on the edges or the center.
	 */
	private PlacingStrategy getHitType(Coordinate c)
	{
		return(getHitType(c.x, c.y));
	}
	/**
	 * Get whether a specific hit on an enemy ship is on center or the edges.
	 * @param x The X of the coordinate of the hit on an enemy ship.
	 * @param y The Y of the coordinate of the hit on an enemy ship.
	 * @return Whether the hit on the enemy ship is on the edges or the center.
	 */
	private PlacingStrategy getHitType(int x, int y)
	{
		if (checkWithinEdge(x, 0, true) || checkWithinEdge(y, 0, true))
		{
			return PlacingStrategy.EDGE;
		}
		return PlacingStrategy.CENTER;
	}
	/**
	 * Determines the opponent's ship placing strategy this game.
	 * @param hits The EnumMap list of hits on enemy ships this game.
	 * @return The opponent's ship placing strategy this game.
	 */
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
	/**
	 * Get the number of hits scored this game.
	 * @param hits The EnumMap list of hits this game.
	 * @return The integer number of hits scored this game.
	 */
	private int getHitNumber(EnumMap<Ship.ShipType_t, List<Coordinate>> hits)
	{
		int c = 0;
		for(Ship.ShipType_t s : Ship.ShipType_t.values())
		{
			c += hits.get(s).size();
		}
		return c;
	}
	/**
	 * Updates the running list of the opponent's ship placing strategies.
	 * @param length The length of the list to keep.
	 */
	private void updateOpponentPlacingStrategyList(int length)
	{
		if (!opponentHits.isEmpty())
		{
			if (getOpponentPlacingStrategy(opponentHits) != PlacingStrategy.NULL)
			{
				if ((opponentPlacingStrategies.size() >= length) && (length != 0))
				{
					opponentPlacingStrategies.remove(0);
				}
				opponentPlacingStrategies.add(getOpponentPlacingStrategy(opponentHits));
			}
		}
	}
	/**
	 * Picks the firing strategy determined to counter the opponent's ship placing strategy.
	 * This is determined by looking at the opponent's past strategies.
	 * @param strategies The list of the most recent opponent ship placing strategies.
	 * @return The best firing strategy to use.
	 */
	private FiringStrategy getFiringStrategy(List<PlacingStrategy> strategies)
	{
		FiringStrategy shotPlacement = FiringStrategy.RANDOM;
		if (strategies.isEmpty() || (strategies.size() < minimumFiringActionLength))
		{
			return shotPlacement;
		}
		//find most likely following action
		PlacingStrategy last = strategies.get(strategies.size() - 1);
		int maximum = 0;
		PlacingStrategy opponentStrategy = PlacingStrategy.RANDOM;
		for (PlacingStrategy f : PlacingStrategy.values())
		{
			if (countPlacingStrategy(strategies, last, f) > maximum)
			{
				maximum = countPlacingStrategy(strategies, last, f);
				opponentStrategy = f;
			}
		}
		shotPlacement = getFiringStrategyResponse(opponentStrategy);
		return shotPlacement;
	}
	/**
	 * Gives the counter FiringStrategy to a given PlacingStrategy.
	 * @param target The PlacingStrategy of the opponent.
	 * @return The best FiringStrategy to use against the PlacingStrategy.
	 */
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
	/**
	 * Counts the number of one specific strategy out of the opponent ship placing strategies in the running list.
	 * @param strategies The opponent's ship placing strategy list.
	 * @param strategy The strategy to count.
	 * @return  The number of the specified strategy counted in the list of opponent ship placing strategies.
	 */
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
	/**
	 * Counts the number of a specified strategy following a specified strategy out of the opponent ship placing strategies in the running list.
	 * This is used for pattern recognition.
	 * @param strategies The opponent's ship placing strategy list.
	 * @param strategy The given strategy.
	 * @param followingStrategy The strategy following the given strategy.
	 * @return The number of followingStrategy following the strategy counted in the list of opponent firing strategies. 
	 */
	private int countPlacingStrategy(List<PlacingStrategy> strategies, PlacingStrategy strategy, PlacingStrategy followingStrategy)
	{
		int count = 0;
		for (int i = 0; i < strategies.size() - 1; i++)
		{
			if ((strategies.get(i) == strategy) && (strategies.get(i + 1) == followingStrategy))
			{
				count++;
			}
		}
		return count;
	}
	/**
	 * Clear the running list of opponent ship placement strategies.
	 * Used for debug purposes.
	 */
	public void clearPlacingMemory()
	{
		opponentPlacingStrategies.clear();
	}
	
	/**
	 * Clear all information about opponent strategy.
	 * Used for debug purposes.
	 */
	public void clearStrategyMemory()
	{
		clearFiringMemory();
		clearPlacingMemory();
	}

	/**
	 * Print a coordinate sequence compatible with Battleship_Log_Viewer.
	 * @param s The coordinate sequence string to print.
	 * Uncomment this to print.
	 */
	private void printLog(String s)
	{
		//System.out.print(s);
	}
}

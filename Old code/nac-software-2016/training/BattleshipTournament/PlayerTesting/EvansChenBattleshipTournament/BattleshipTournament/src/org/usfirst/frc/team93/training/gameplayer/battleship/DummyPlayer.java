/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.AdaptableEnhancedPlayer.PlacingStrategy;
import org.usfirst.frc.team93.training.gameplayer.battleship.ExperimentalInsightfulPlayer.Coordinate;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean.PlaceResult_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.Orientation_t;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class DummyPlayer extends GamePlayer {

	Random random_generator = new Random();
	List<Ship.ShipType_t> RemainingShips = new ArrayList<Ship.ShipType_t>();
	
	/**
	 */
	public DummyPlayer() 
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
		resetRemainingShips();
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
		placeShipsHybrid(4);
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
		int x = 0;
		int y = 0;
		FireResult result = fireShot(new Ship.GridCoord(x, y));
		if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
		{
			//System.out.println("We hit something!");
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

	private boolean checkWithin(int x, int y)
	{
		return ((x >= 0) && (x < 10) && (y >= 0) && (y < 10));
	}
	private boolean checkWithin(Coordinate c)
	{
		return checkWithin(c.x, c.y);
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
	 * Place ships according to a PlacingStrategy.
	 * @param p The PlacingStrategy to use.
	 * One can use RANDOM, CENTER, or EDGE ship placing.
	 */
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
				placeShipsEdgesEnhanced();
				break;
			default:
				placeShipsRandomlyEnhanced();
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
		int o = random_generator.nextInt(4);
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
	 * Place the ships randomly, avoiding clustering.
	 */
	private void placeShipsRandomlyEnhanced()
	{
		//place the ships
		//create the set of open points.
		boolean[][] free = new boolean[10][10];
		List<Coordinate> freePoints = new ArrayList<Coordinate>();
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				free[i][j] = true;
				freePoints.add(new Coordinate(i, j));
			}
		}
		//copy the list of existing ships.
		List<Ship.ShipType_t> shipsToPlace = new ArrayList<Ship.ShipType_t>(RemainingShips);
		int shipNumber = shipsToPlace.size();
		//place the center ships
		for(int i = 0; i < shipNumber; i++)
		{
			//pick a ship
			int ship = random_generator.nextInt(shipsToPlace.size());
			
			//get a list of points to use
			List<Coordinate> points = new ArrayList<Coordinate>(freePoints);
			int size = points.size();
			boolean placed = false;
			
			for (int p = 0; (p < size) && (!placed); p++)
			{
				//get a random point
				int cIndex = random_generator.nextInt(points.size());
				Coordinate c = points.get(cIndex);
				
				//pick a random orientation
				int o = random_generator.nextInt(4);
				//try all 4 placements
				for (int ori = 0; ori < 4; ori++)
				{
					int or = (o + ori) % 4;
					if (checkShipEnhanced(c.x, c.y, shipsToPlace.get(ship), getOrientationFromInt(or), free))
					{
						if (placeShip(new Ship(new Ship.GridCoord(c.x, c.y), getOrientationFromInt(or), shipsToPlace.get(ship))) == PlaceResult_t.E_OK)
						{
							placed = true;
							//place and remove points
							blockSurroundingShip(c.x, c.y, shipsToPlace.get(ship), getOrientationFromInt(or), free, freePoints);
						}
					}
				}
				//if failed to place, remove the point from the list and try again
				if (!placed)
				{
					points.remove(cIndex);
				}
			}
			if (!placed)
			{
				//if all fails, place randomly
				int x = random_generator.nextInt(10);
				int y = random_generator.nextInt(10);
				int o = random_generator.nextInt(2);
				PlaceResult_t r = placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), shipsToPlace.get(ship)));
				while((r != PlaceResult_t.E_OK) && (r != PlaceResult_t.E_OCEAN_FULL) && (r != PlaceResult_t.E_SHIP_TYPE_USED))
				{
					x = random_generator.nextInt(10);
					y = random_generator.nextInt(10);
					o = random_generator.nextInt(2);
					r = placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), shipsToPlace.get(ship)));
				}
			}
			shipsToPlace.remove(ship);
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
	 * Default for placing ships on edges.
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
		//place the ships
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
				//try left then right
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
				//try right then left
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
				//try up then down
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
				//try down then up
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
	 * Default for placing ships on edges, avoiding clustering.
	 */
	private void placeShipsEdgesEnhanced()
	{
		placeShipsEdgesEnhanced(0, true);
	}
	/**
	 * Place the ships on the edges, avoiding clustering.
	 * @param border border How far from the edge is allowed.
	 * 0 yields best results.
	 * @param strict Whether the ships' distance from the edges is allowed using == or <=.
	 */
	private void placeShipsEdgesEnhanced(int border, boolean strict)
	{
		//create the set of open points.
		boolean[][] free = new boolean[10][10];
		List<Coordinate> freePoints = new ArrayList<Coordinate>();
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (checkWithinEdge(i, border, strict) || checkWithinEdge(j, border, strict))
				{
					free[i][j] = true;
					freePoints.add(new Coordinate(i, j));
				}
				else
				{
					free[i][j] = false;
				}
			}
		}
		
		//copy the list of existing ships.
		List<Ship.ShipType_t> shipsToPlace = new ArrayList<Ship.ShipType_t>(RemainingShips);
		
		//place the ships
		for (int i = 0; i < shipsToPlace.size(); i++)
		{
			//get a list of points to use
			List<Coordinate> points = new ArrayList<Coordinate>(freePoints);
			int size = points.size();
			boolean placed = false;
			for (int p = 0; (p < size) && (!placed); p++)
			{
				//get a random point
				int cIndex = random_generator.nextInt(points.size());
				Coordinate c = points.get(cIndex);
				
				int orientationAdjust = 0;
				//if horizontal edge...
				if (checkWithinEdge(c.y, border, strict))
				{
					orientationAdjust = 0;
				}
				//if vertical edge...
				else if (checkWithinEdge(c.x, border, strict))
				{
					orientationAdjust = 1;
				}
				//pick a random orientation
				int o = ((2 * random_generator.nextInt(2)) + orientationAdjust) % 4;
				if (checkShipEnhanced(c.x, c.y, shipsToPlace.get(i), getOrientationFromInt(o), free))
				{
					if (placeShip(new Ship(new Ship.GridCoord(c.x, c.y), getOrientationFromInt(o), shipsToPlace.get(i))) == PlaceResult_t.E_OK)
					{
						placed = true;
						//place and remove points
						blockSurroundingShip(c.x, c.y, shipsToPlace.get(i), getOrientationFromInt(o), free, freePoints);
					}
				}
				//pick the other orientation, if placing failed
				if (!placed)
				{
					o = (o + 2) % 4;
					if (checkShipEnhanced(c.x, c.y, shipsToPlace.get(i), getOrientationFromInt(o), free))
					{
						if (placeShip(new Ship(new Ship.GridCoord(c.x, c.y), getOrientationFromInt(o), shipsToPlace.get(i))) == PlaceResult_t.E_OK)
						{
							placed = true;
							//place and remove points
							blockSurroundingShip(c.x, c.y, shipsToPlace.get(i), getOrientationFromInt(o), free, freePoints);
						}
					}
				}
				//if failed to place, remove the point from the list and try again
				if (!placed)
				{
					points.remove(cIndex);
				}
			}
			if (!placed)
			{
				//if all fails, place randomly
				int x = random_generator.nextInt(10);
				int y = random_generator.nextInt(10);
				int o = random_generator.nextInt(2);
				while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), shipsToPlace.get(i))) != PlaceResult_t.E_OK)
				{
					x = random_generator.nextInt(10);
					y = random_generator.nextInt(10);
					o = random_generator.nextInt(2);
				}
			}
		}
	}
	/**
	 * Place ships using a hybrid of enhanced Center and Edge placements.
	 * This causes programs to be unable to counter the placement by using center or edge firing.
	 * @param randomShipNumber The number of ships out of 5 to place randomly. The rest go on the edges.
	 */
	private void placeShipsHybrid(int randomShipNumber)
	{
		//create the list of ships to place on the edges
		randomShipNumber = Math.max(Math.min(randomShipNumber, 5), 0);
		List<Ship.ShipType_t> randomShips = new ArrayList<Ship.ShipType_t>(RemainingShips);
		for (int i = 0; i < (5 - randomShipNumber); i++)
		{
			//remove a ship
			int shipIndex = random_generator.nextInt(randomShips.size());
			randomShips.remove(shipIndex);
		}
		
		//place the ships
		//create the set of open points.
		boolean[][] free = new boolean[10][10];
		List<Coordinate> freePoints = new ArrayList<Coordinate>();
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				free[i][j] = true;
				freePoints.add(new Coordinate(i, j));
			}
		}
		//copy the list of existing ships.
		List<Ship.ShipType_t> shipsToPlace = new ArrayList<Ship.ShipType_t>(RemainingShips);
		//place the center ships
		for(int i = 0; i < randomShips.size(); i++)
		{
			//get a list of points to use
			List<Coordinate> points = new ArrayList<Coordinate>(freePoints);
			int size = points.size();
			boolean placed = false;
			
			for (int p = 0; (p < size) && (!placed); p++)
			{
				//get a random point
				int cIndex = random_generator.nextInt(points.size());
				Coordinate c = points.get(cIndex);
				
				//pick a random orientation
				int o = random_generator.nextInt(4);
				//try all 4 placements
				for (int ori = 0; ori < 4; ori++)
				{
					int or = (o + ori) % 4;
					if (checkShipEnhanced(c.x, c.y, randomShips.get(i), getOrientationFromInt(or), free))
					{
						if (placeShip(new Ship(new Ship.GridCoord(c.x, c.y), getOrientationFromInt(or), randomShips.get(i))) == PlaceResult_t.E_OK)
						{
							placed = true;
							//place and remove points
							blockSurroundingShip(c.x, c.y, randomShips.get(i), getOrientationFromInt(or), free, freePoints);
						}
					}
				}
				//if failed to place, remove the point from the list and try again
				if (!placed)
				{
					points.remove(cIndex);
				}
			}
			if (!placed)
			{
				//if all fails, place randomly
				int x = random_generator.nextInt(10);
				int y = random_generator.nextInt(10);
				int o = random_generator.nextInt(2);
				PlaceResult_t r = placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), randomShips.get(i)));
				while((r != PlaceResult_t.E_OK) && (r != PlaceResult_t.E_OCEAN_FULL) && (r != PlaceResult_t.E_SHIP_TYPE_USED))
				{
					x = random_generator.nextInt(10);
					y = random_generator.nextInt(10);
					o = random_generator.nextInt(2);
					r = placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), randomShips.get(i)));
				}
			}
			shipsToPlace.remove(searchShipType(randomShips.get(i), shipsToPlace));
		}
		//place the rest of the ships on the edges
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (!((checkWithinEdge(i, 0, true)) || (checkWithinEdge(j, 0, true))))
				{
					free[i][j] = false;
					blockPoint(i, j, free, freePoints);
				}
			}
		}
		//place the edge ships
		int shipsLeft = shipsToPlace.size();
		for(int i = 0; i < shipsLeft; i++)
		{
			int ship = random_generator.nextInt(shipsToPlace.size());
			
			//get a list of points to use
			List<Coordinate> points = new ArrayList<Coordinate>(freePoints);
			int size = points.size();
			boolean placed = false;
			
			for (int p = 0; (p < size) && (!placed); p++)
			{
				//get a random point
				int cIndex = random_generator.nextInt(points.size());
				Coordinate c = points.get(cIndex);
				
				//pick a random orientation
				int o = random_generator.nextInt(4);
				//try all 4 placements
				for (int ori = 0; ori < 4; ori++)
				{
					int or = (o + ori) % 4;
					if (checkShipEnhanced(c.x, c.y, shipsToPlace.get(ship), getOrientationFromInt(or), free))
					{
						if (placeShip(new Ship(new Ship.GridCoord(c.x, c.y), getOrientationFromInt(or), shipsToPlace.get(ship))) == PlaceResult_t.E_OK)
						{
							placed = true;
							//place and remove points
							blockSurroundingShip(c.x, c.y, shipsToPlace.get(ship), getOrientationFromInt(or), free, freePoints);
						}
					}
				}
				//if failed to place, remove the point from the list and try again
				if (!placed)
				{
					points.remove(cIndex);
				}
			}
			if (!placed)
			{
				//if all fails, place randomly
				int x = random_generator.nextInt(10);
				int y = random_generator.nextInt(10);
				int o = random_generator.nextInt(2);
				PlaceResult_t r = placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), shipsToPlace.get(ship)));
				while((r != PlaceResult_t.E_OK) && (r != PlaceResult_t.E_OCEAN_FULL) && (r != PlaceResult_t.E_SHIP_TYPE_USED))
				{
					x = random_generator.nextInt(10);
					y = random_generator.nextInt(10);
					o = random_generator.nextInt(2);
					r = placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), shipsToPlace.get(ship)));
				}
			}
			shipsToPlace.remove(ship);
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
	 * Blocks the points on and surrounding a ship in a boolean 2D array.
	 * @param x The X of the ship
	 * @param y The Y of the ship
	 * @param s The type of the ship
	 * @param o The orientation of the ship
	 * @param open The target boolean 2D array.
	 */
	private void blockSurroundingShip(int x, int y, Ship.ShipType_t s, Ship.Orientation_t o, boolean[][] open)
	{
		//block off points
		int dirX = 0;
		int dirY = 0;
		switch(o)
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
		for (int p = 0; p < getShipLength(s); p++)
		{
			int pointX = x + (p * dirX);
			int pointY = y + (p * dirY);
			blockSurroundingPoints(pointX, pointY, open);
		}
	}
	/**
	 * Blocks the points on and surrounding a ship in a boolean 2D array, and removes them from the coordinate list.
	 * @param x The X of the ship
	 * @param y The Y of the ship
	 * @param s The type of the ship
	 * @param o The orientation of the ship
	 * @param open The target boolean 2D array.
	 * @param l The list to remove the points from.
	 */
	private void blockSurroundingShip(int x, int y, Ship.ShipType_t s, Ship.Orientation_t o, boolean[][] open, List<Coordinate> l)
	{
		//block off points
		int dirX = 0;
		int dirY = 0;
		switch(o)
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
		for (int p = 0; p < getShipLength(s); p++)
		{
			int pointX = x + (p * dirX);
			int pointY = y + (p * dirY);
			blockSurroundingPoints(pointX, pointY, open, l);
		}
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
	 * Blocks the point and the surrounding points in a boolean 2D array, and removes them from the coordinate list.
	 * @param x
	 * @param y
	 * @param open The target boolean 2D array.
	 * @param l The list to remove the points from.
	 */
	private void blockSurroundingPoints(int x, int y, boolean[][] open, List<Coordinate> l)
	{
		blockPoint(x, y, open, l);
		blockPoint(x - 1, y, open, l);
		blockPoint(x, y - 1, open, l);
		blockPoint(x + 1, y, open, l);
		blockPoint(x, y + 1, open, l);
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
	/**
	 * Blocks the point in the boolean 2D array, and removes it from the coordinate list.
	 * @param x
	 * @param y
	 * @param open The target boolean 2D array.
	 * @param l The list to remove the point from.
	 */
	private void blockPoint(int x, int y, boolean[][] open, List<Coordinate> l)
	{
		if (checkWithin(x, y))
		{
			open[x][y] = false;
			int index = searchCoordinates(new Coordinate(x, y), l);
			if (index >= 0)
			{
				l.remove(index);
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
	 * Searches for the index of a Ship.ShipType_t s in a coordinate list l.
	 * @param s The ship type to search for.
	 * @param l The list in which to search.
	 * @return The index of the coordinate in the list.
	 */
	private int searchShipType(Ship.ShipType_t s, List<Ship.ShipType_t> l)
	{
		for (int i = 0; i < l.size(); i ++)
		{
			if ((l.get(i) == s))
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
	
}

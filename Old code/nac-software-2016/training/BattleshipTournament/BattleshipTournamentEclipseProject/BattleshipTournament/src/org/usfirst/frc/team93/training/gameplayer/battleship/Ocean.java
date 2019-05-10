/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nick.luther
 *
 * Ocean and Ship are meant to implement core architecture for the
 * rules of Battleship:
 * http://www.hasbro.com/common/instruct/Battleship.PDF 
 */
public class Ocean {

	final public static int OCEAN_ORIGIN_X = 0;
	final public static int OCEAN_ORIGIN_Y = 0;
	final public static int OCEAN_WIDTH = 10;
	final public static int OCEAN_HEIGHT = 10;
	final public static int OCEAN_SHIPS_COUNT = 5;
	
	public enum PlaceResult_t {E_OK, E_OFF_OCEAN, E_COLLISION, E_OCEAN_FULL, E_BAD_SHIP_TYPE, E_SHIP_TYPE_USED};
	
	private List<Ship> m_ships = new ArrayList<Ship>();
	
	public Ocean()
	{
		reset();
	}
	
	public void reset()
	{
		m_ships.clear();
	}
	
	public PlaceResult_t addShip(Ship ship)
	{
		if (false == isShipOnOcean(ship))
		{
			return PlaceResult_t.E_OFF_OCEAN;
		}
		else if (true == testIntersectShips(ship))
		{
			return PlaceResult_t.E_COLLISION;
		}
		else if (m_ships.size() >= OCEAN_SHIPS_COUNT)
		{
			return PlaceResult_t.E_OCEAN_FULL;
		}
		else if (false == Ship.validateShipType(ship.getType()))
		{
			return PlaceResult_t.E_BAD_SHIP_TYPE;
		}
		else if (false == findShipByType(ship.getType()).isNull()) // true expression if ship type already used
		{
			return PlaceResult_t.E_SHIP_TYPE_USED;
		}
		else
		{
			m_ships.add(ship);
			return PlaceResult_t.E_OK;
		}
	}
	
	/**
	 * Everything else checked at ship insertion.  Just verify length now.
	 * @return True if Ocean is full and ready to go
	 */
	public boolean isValid()
	{
		return (m_ships.size() == OCEAN_SHIPS_COUNT);
	}
	
	/**
	 * Are all ships sunk?  / Has the game been won? 
	 */
	public boolean testAllShipsSunk()
	{
		// assume all sunk, set to false when we find a ship that's still alive
		boolean all_sunk = true;
		for (Ship it : m_ships)
		{
			if (false == it.isSunk())
			{
				all_sunk = false;
			}
		}
		// all_sunk is now correct
		return all_sunk;
	}

	/**
	 * Is the proposed ship fully on the grid?  This is not a test of the exact ship instance being on a specific Ocean,
	 * only a test of if the proposed ship could fit on an empty Ocean of the defined dimensions.
	 * @param ship Proposed ship placement
	 * @return True if fits within constant-defined Ocean grid, false if not completely on the grid.
	 */
	public static boolean isShipOnOcean(Ship ship)
	{
		Ship.GridCoord origin = ship.getLocation();
		Ship.GridCoord inc = ship.getCountIncrement();
		int len = ship.getLength();
		
		// Find the end cell
		// If ship is 5 units long, need to add four times to cover five cells
		Ship.GridCoord it_coord = origin;
		for (int i = 0; i < len - 1; ++i)
		{
			it_coord = it_coord.add(inc);
		}
		// it_coord is now the other end (stern) of the ship
		
		// if both ship bow and stern are on ocean, then whole ship is
		return (isPointOnOcean(origin) && isPointOnOcean(it_coord));
	}
	
	
	public boolean testIntersectShips(Ship.GridCoord coord)
	{
		Ship result = findIntersectShip(coord);
		if (result.isNull()) {
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public Ship findIntersectShip(Ship.GridCoord coord)
	{
		for (Ship it : m_ships)
		{
			if (it.testIntersect(coord))
			{
				return it;
			}
		}
		return new Ship(); // null ship
	}
	
	public boolean testIntersectShips(Ship ship) 
	{
		Ship result = findIntersectShip(ship);
		if (result.isNull()) {
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public Ship findIntersectShip(Ship ship) {
		Ship.GridCoord origin = ship.getLocation();
		Ship.GridCoord inc = ship.getCountIncrement();
		int len = ship.getLength();
		
		// Iterate, testing each cell
		// If ship is 5 units long, need to add four times to cover five cells
		Ship.GridCoord it_coord = origin;
		if (testIntersectShips(it_coord))
		{
			return findIntersectShip(it_coord);
		}
		for (int i = 0; i < len - 1; ++i)
		{
			it_coord = it_coord.add(inc);
			if (testIntersectShips(it_coord))
			{
				return findIntersectShip(it_coord);
			}
		}
		// it_coord is now the other end (stern) of the ship
				
		// if we made it here we found no intersection
		return new Ship();		
	}
	
	// Need ability to see if a ship is already on the ocean
	public Ship findShipByType(Ship.ShipType_t type)
	{
		for (Ship it : m_ships)
		{
			if (it.getType() == type)
			{
				return it;
			}
		}
		return new Ship();
	}
	
	public static boolean isPointOnOcean(int x, int y)
	{
		if (	x >= OCEAN_ORIGIN_X && x < (OCEAN_ORIGIN_X + OCEAN_WIDTH)
			&& 	y >= OCEAN_ORIGIN_Y && y < (OCEAN_ORIGIN_Y + OCEAN_HEIGHT))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isPointOnOcean(Ship.GridCoord coord)
	{
		return isPointOnOcean(coord.x, coord.y);
	}
}

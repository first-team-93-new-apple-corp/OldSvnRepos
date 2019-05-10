/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ship
 * 
 * @author nick.luther
 * 
 */
public class Ship {
	public enum ShipType_t {SHIP_NULL, SHIP_CARRIER, SHIP_BATTLESHIP, SHIP_CRUISER, SHIP_SUBMARINE, SHIP_DESTROYER, _SHIP_TOP};
	public enum Orientation_t {OR_NULL, OR_RIGHT, OR_UP, OR_LEFT, OR_DOWN};
	
	/**
	 * Utility class to represent coordinate pairs
	 * 
	 * @author nick.luther
	 *
	 */
	public static class GridCoord  // TODO Suspicious of static keyword here
	{
		public int x;
		public int y;
		
		public GridCoord(int x, int y)
		{
			this.x = x; this.y = y;
		}
		
		public GridCoord(GridCoord coord)
		{
			this.x = coord.x; this.y = coord.y;
		}
		
		public GridCoord add(GridCoord inc)
		{
			return new GridCoord(this.x + inc.x, this.y + inc.y);
		}

		public boolean testMatch(GridCoord coord) 
		{
			return (x == coord.x && y == coord.y);
		}
	}
	
	private ShipType_t m_ship_type;
	private Orientation_t m_orientation;
	private GridCoord m_ship_origin;
	private boolean m_is_sunk = false;
	private List<GridCoord> m_hits = new ArrayList<GridCoord>();
	
	
	
	/**
	 * Construct a new ship.  You need to do this to place the Ship on your Ocean.  The intended call 
	 * is new Ship(location, orientation, ship_type).
	 * 
	 * Ships can be placed anywhere on the Ocean.  They must be horizontal or vertical.  This implementation
	 * uses a base location grid coordinate (x,y pair) and a direction from that grid coordinate (up, down,
	 * left, right).  You must also specify a ship type.  There are five ship types in Battleship, and the
	 * enumeration ShipType_t includes an option for each of those five types.
	 * 
	 * @param location Base grid point of ship
	 * @param orientation Direction of ship from that point
	 * @param ship_type Type of ship (determines ship length)
	 */
	public Ship(GridCoord location, Orientation_t orientation, ShipType_t ship_type) 
	{
		super();
		this.m_ship_origin = location;
		this.m_ship_type = ship_type;
		this.m_orientation = orientation;
		m_is_sunk = false;
	}
	
	/**
	 * Don't use this unless you know what you're doing
	 */
	public Ship()
	{
		super();
		this.m_ship_origin = new GridCoord(-1,-1);
		this.m_ship_type = ShipType_t.SHIP_NULL;
		this.m_orientation = Orientation_t.OR_NULL;
	}
	
	
	public boolean isSunk()
	{
		return m_is_sunk;
	}
	
	public boolean isNull()
	{
		return (ShipType_t.SHIP_NULL == this.m_ship_type);
	}
	
	// This is a kludge.  It'll work though, so we'll just go with it.
	public void notifyShot(GridCoord coord)
	{
		if (testIntersect(coord))
		{
			// have we already been hit in the same spot?
			boolean have_match = false;
			for (GridCoord it : m_hits)
			{
				if (it.testMatch(coord))
				{
					// Already hit here
					have_match = true;
					break;
				}
			}
			// have_match is now correct
			if (true == have_match)
			{
				// repeat hit, do nothing
			}
			else
			{
				// log this one
				m_hits.add(coord);
				
				// Now, test if we're sunk
				if (m_hits.size() == getLength())
				{
					// we're sunk!
					m_is_sunk = true;
				}
			}
		}
	}
	
	/**
	 * Need ability to test for collisions and hits
	 * @param coord
	 * @return true if intersect/collide, false if clear
	 */
	public boolean testIntersect(GridCoord coord)
	{
		Ship.GridCoord origin = getLocation();
		Ship.GridCoord inc = getCountIncrement();
		int len = getLength();
		boolean found_intersection = false;
		
		// Test each cell for intersection
		// If ship is 5 units long, need to add four times to cover five cells
		Ship.GridCoord it_coord = origin;
		if (it_coord.testMatch(coord))
		{
			found_intersection = true;
		}
		for (int i = 0; i < len - 1; ++i)
		{
			it_coord = it_coord.add(inc);
			if (it_coord.testMatch(coord))
			{
				found_intersection = true;
			}
		}
		// it_coord is now the other end (stern) of the ship
		
		// if both ship bow and stern are on ocean, then whole ship is
		return found_intersection;
	}
	
	/**
	 * Need ability to test for collisions and hits
	 * @param coord
	 * @return true if intersect/collide, false if clear
	 */
	public boolean testIntersect(Ship ship)
	{
		// Strategy: Iterate through our ship, test each point for collisions with other ship
		
		Ship.GridCoord origin = getLocation();
		Ship.GridCoord inc = getCountIncrement();
		int len = getLength();
		boolean found_intersection = false;
		
		// Test each cell for intersection
		// If ship is 5 units long, need to add four times to cover five cells
		Ship.GridCoord it_coord = origin;
		if (ship.testIntersect(it_coord))
		{
			found_intersection = true;
		}
		for (int i = 0; i < len - 1; ++i)
		{
			it_coord = it_coord.add(inc);
			if (ship.testIntersect(it_coord))
			{
				found_intersection = true;
			}
		}
		// it_coord is now the other end (stern) of the ship
		
		// if both ship bow and stern are on ocean, then whole ship is
		return found_intersection;
	}

	public ShipType_t getType() 
	{
		return m_ship_type;
	}

	public Orientation_t getOrientation() 
	{
		return m_orientation;
	}

	public GridCoord getLocation() 
	{
		return m_ship_origin;
	}

	public int getLength()
	{
		return getLengthByType(m_ship_type);
	}

	/**
	 * Utility function
	 * @param shiptype Type of ship to evaluate
	 * @return Length of ship type in grid spaces
	 */
	public static int getLengthByType(ShipType_t shiptype)
	{
		int len = -1; // This should always be changed
		switch (shiptype) 
		{
		case SHIP_CARRIER:
			len = 5;
			break;
		case SHIP_BATTLESHIP:
			len = 4;
			break;
		case SHIP_CRUISER:
			len = 3;
			break;
		case SHIP_SUBMARINE:
			len = 3;
			break;
		case SHIP_DESTROYER:
			len = 2;
			break;
		default:  // This code should never execute.
			len = -2;
			break;
		}
		return len;
	}
	
	/**
	 * 
	 * @param shiptype Value to validate
	 * @return True if a valid ship type
	 */
	public static boolean validateShipType(ShipType_t shiptype)
	{
		int len = getLengthByType(shiptype);
		if (len > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Utility function
	 * @param orientation
	 * @return How to increment a coordinate counter
	 */
	public static GridCoord getCountIncrementByOrientation(Orientation_t orientation)
	{
		switch (orientation)
		{
		case OR_DOWN:
			return new GridCoord(0,-1);
		case OR_LEFT:
			return new GridCoord(-1,0);
		case OR_RIGHT:
			return new GridCoord(1,0);
		case OR_UP:
			return new GridCoord(0,1);
		default: // This should never execute
			return new GridCoord(-999,-999);
		}
	}
	
	public GridCoord getCountIncrement()
	{
		return getCountIncrementByOrientation(m_orientation);
	}
}

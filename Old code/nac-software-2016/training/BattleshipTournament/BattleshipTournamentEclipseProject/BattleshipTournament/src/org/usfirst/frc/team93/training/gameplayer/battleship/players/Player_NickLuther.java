/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship.players;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult.FireResultType_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean.PlaceResult_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.GridCoord;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.Orientation_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.ShipType_t;

/**
 * This is Nick's game player.  Its objective is to beat Samm.  It is OK if this player loses to Evans.
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class Player_NickLuther extends GamePlayer {
	
	public static boolean isCoordValid(int x, int y)
	{
		final int cols = Ocean.OCEAN_WIDTH;
		final int rows = Ocean.OCEAN_HEIGHT;	
		return (x >= 0 && x < cols && y >= 0 && y < rows);
	}
	
	abstract class ShootingStrategy
	{
		protected Random random_generator = new Random();
		protected List<GridCoord> m_firing_plan = new ArrayList<GridCoord>(Ocean.OCEAN_WIDTH * Ocean.OCEAN_HEIGHT + 10);
		
		// MAGIC NUMBER - We know something about the total size of the gameboard
		public static final int FIRING_PLAN_RAND_EXP = 7;
		
		public void reset() {}
		
		public boolean isEmpty()
		{
			return (m_firing_plan.isEmpty() || m_firing_plan.size() == 0);
		}
		
		public GridCoord getNextShot()
		{
			// now let's make it more interesting -- randomly pick within the plan
			int len = m_firing_plan.size();
			if (len == 0)
			{
				println("*** NickLutherGamePlayer Assertion empty firing plan polled HitShootingStrategy");
				return new GridCoord(0,0);
			}
			int rand_sel = random_generator.nextInt(FIRING_PLAN_RAND_EXP) % len;
			GridCoord res = new GridCoord(m_firing_plan.get(rand_sel));
			m_firing_plan.remove(rand_sel);
			return res;
		}
		
		public abstract boolean isTargeted();
		
		public boolean isCoordValid(int x, int y)
		{
			return Player_NickLuther.isCoordValid(x, y);
		}
	}
	
	/*
	 * Strategy:
	 *   0123456789
	 *  0 1 2 3 4 5
	 *  1
	 *  26 7 8 9 0
	 *  3
	 *  4 1 2 3 4 5
	 *  5
	 *  66 7 8 9 0
	 *  7
	 *  8 1 2 3 4 5
	 *  9
	 */
	class SkipShootingStrategy extends ShootingStrategy
	{
		
		public SkipShootingStrategy(int row_start, int col_start)
		{
			m_firing_plan.clear();
			
			final int cols = Ocean.OCEAN_WIDTH;
			final int rows = Ocean.OCEAN_HEIGHT;
			// First pass, make a skip pattern
			// Start at row 0, col 1
			for (int i = row_start*cols + col_start; i < rows*cols; i += 2)
			{
				final int x = i%cols;
				final int y = i/cols;
				m_firing_plan.add(new GridCoord(x,y));  // From addLast for Deque
				//println("Adding to sequence " + String.valueOf(x) + "," + String.valueOf(y));
				if (x == 9) // if in column 8 or 9
				{
					i += cols - 1; // skip a row, plus normal increment
				}
				else if (x == 8)
				{
					i += cols + 1;
				}
			}
		}
		
		@Override
		public boolean isTargeted()
		{
			return false;
		}
	}
	
	
	class HitAreaShootingStrategy extends ShootingStrategy
	{
		
		public HitAreaShootingStrategy(int row_hit, int col_hit)
		{
			m_firing_plan.clear();
			
			// Make a box around the ship, but only left and right
			// addToFiringPlanIfValid(row_hit - 1, col_hit - 1); // not valid
			addToFiringPlanIfValid(row_hit - 1, col_hit + 0);
			// addToFiringPlanIfValid(row_hit - 1, col_hit + 1); // not valid
			addToFiringPlanIfValid(row_hit + 0, col_hit - 1);
			addToFiringPlanIfValid(row_hit + 0, col_hit + 1);
			// addToFiringPlanIfValid(row_hit + 1, col_hit - 1); // not valid
			addToFiringPlanIfValid(row_hit + 1, col_hit + 0);
			// addToFiringPlanIfValid(row_hit + 1, col_hit + 1); // not valid
		}
		
		public void addToFiringPlanIfValid(int x, int y)
		{			
			if (isCoordValid(x,y))
			{
				m_firing_plan.add(new GridCoord(x, y));
			}
		}
		
		@Override
		public boolean isTargeted()
		{
			return true;
		}
	}
	
	/**
	 * Note: This may create a null strategy, but that's OK because it will be immediately purged.
	 * 
	 * @author nick.luther
	 *
	 */
	class HitLineShootingStrategy extends ShootingStrategy
	{
		Player_NickLuther m_parent_player = null;
		
		public HitLineShootingStrategy(int row_hit, int col_hit, Player_NickLuther player)
		{	
			m_parent_player = player;
			m_firing_plan.clear();
			
			// Check for adjacent hits.  Add planned shots on both ends.  Add exactly 1 on each end.
			// This could double-count for some situations.  That's not a problem.  We'll never shoot
			// in the same place twice.
			// This logic will not be fool-proof, but should be good enough for most games
			checkAdjacent(row_hit, col_hit, -1, +0);
			checkAdjacent(row_hit, col_hit, +0, -1);
			checkAdjacent(row_hit, col_hit, +0, +1);
			checkAdjacent(row_hit, col_hit, +1, +0);
		}
		
		public void checkAdjacent(int x, int y, int x_inc, int y_inc)
		{	
			final int check_x = x + x_inc;
			final int check_y = y + y_inc;
			
			int count_inc = 0;
			int count_dec = 0;
			
			if (isCoordValid(check_x, check_y))
			{
				// we have a valid coordinate, now check if it's a hit
				if (m_parent_player.shotResultsProbeIsHit(check_x, check_y) == true) // not needed, handled by while loops, but leaving in place
				{
					// we now have a confirmed hit adjacent to us
					// see how far the sequence goes

					int x_test = 0;
					int y_test = 0;
					
					// check "decrementing"
					x_test = x - x_inc;
					y_test = y - y_inc;
					while (isCoordValid(x_test, y_test) && m_parent_player.shotResultsProbeIsHit(x_test, y_test))
					{
						++count_dec;
						
						x_test -= x_inc;
						y_test -= y_inc;
					}
					// add one past decrement sequence if the coordinate is valid
					if (isCoordValid(x_test, y_test))
					{
						// Valid non-hit coordinate at the decrement end.  Add to firing plan.
						m_firing_plan.add(new GridCoord(x_test, y_test));
					}
					
					// check "incrementing"
					x_test = check_x;
					y_test = check_y;
					while (isCoordValid(x_test, y_test) && m_parent_player.shotResultsProbeIsHit(x_test, y_test))
					{
						++count_inc;
						
						x_test += x_inc;
						y_test += y_inc;
					}
					// add one past decrement sequence if the coordinate is valid
					if (isCoordValid(x_test, y_test))
					{
						// Valid non-hit coordinate at the decrement end.  Add to firing plan.
						m_firing_plan.add(new GridCoord(x_test, y_test));
					}
					
				}
			}
		}
		
		@Override
		public boolean isTargeted()
		{
			return true;
		}
	}
	
	
	// MAGIC NUMBER - We know something about the total size of the gameboard
	private final int FIRING_PLAN_RAND_EXP = ShootingStrategy.FIRING_PLAN_RAND_EXP;

	Random random_generator = new Random();
	Deque<ShootingStrategy> strategy_stack = new ArrayDeque<ShootingStrategy>();
	//Map<GridCoord,FireResult> shot_results = new HashMap<GridCoord,FireResult>();
	Map<GridCoord,FireResult> shot_results = new LinkedHashMap<GridCoord,FireResult>();
	// For some reason, the map isn't working, so let's do this with a primitive array
	FireResult[][] shot_results_array = new FireResult[Ocean.OCEAN_WIDTH][Ocean.OCEAN_HEIGHT]; 
	
	/**
	 */
	public Player_NickLuther() 
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
		strategy_stack.clear();
		
		// Do this in groups.  First two groups being first two is important.  Want a skip pattern.
		// randomly flip order of the first two
		if (true == random_generator.nextBoolean())
		{
			strategy_stack.addLast(new SkipShootingStrategy(0,1));
			strategy_stack.addLast(new SkipShootingStrategy(1,0));
		}
		else
		{
			strategy_stack.addLast(new SkipShootingStrategy(1,0));
			strategy_stack.addLast(new SkipShootingStrategy(0,1));
		}
		
		// Randomly flip order of the last two.  This is less important.
		if (true == random_generator.nextBoolean())
		{
			strategy_stack.addLast(new SkipShootingStrategy(1,1));
			strategy_stack.addLast(new SkipShootingStrategy(0,0));
		}
		else
		{
			strategy_stack.addLast(new SkipShootingStrategy(0,0));
			strategy_stack.addLast(new SkipShootingStrategy(1,1));
		}
		
		// Other cleanup
		
		// clear shot results map (not working)
		shot_results.clear();
		
		// clear shot results array
		for (int x = 0; x < Ocean.OCEAN_WIDTH; ++x)
		{
			for (int y = 0; y < Ocean.OCEAN_HEIGHT; ++y)
			{
				shot_results_array[x][y] = new FireResult();
				shot_results_array[x][y].result_type = FireResult.FireResultType_t.SHOT_NOT_AUTHORIZED;
				shot_results_array[x][y].coord = new Ship.GridCoord(-1, -1);
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
		// TODO Required: place five ships, one of each type, with no collisions
		//Ocean.PlaceResult_t result = placeShip(new Ship(new Ship.GridCoord(0,0), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
		//placeShip(new Ship(new Ship.GridCoord(0,2), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_BATTLESHIP));
		//placeShip(new Ship(new Ship.GridCoord(0,4), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CRUISER));
		//placeShip(new Ship(new Ship.GridCoord(0,6), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_SUBMARINE));
		//placeShip(new Ship(new Ship.GridCoord(0,9), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_DESTROYER));
		placeShipRandom(Ship.ShipType_t.SHIP_CARRIER);
		placeShipRandom(Ship.ShipType_t.SHIP_BATTLESHIP);
		placeShipRandom(Ship.ShipType_t.SHIP_CRUISER);
		placeShipRandom(Ship.ShipType_t.SHIP_SUBMARINE);
		placeShipRandom(Ship.ShipType_t.SHIP_DESTROYER);
	}
	
	public void placeShipRandom(Ship.ShipType_t ship_type)
	{
		// First, choose orientation
		int or_idx = random_generator.nextInt(2) % 4;
		Ship.Orientation_t or = Ship.Orientation_t.OR_NULL;
		switch (or_idx) {
		case 0:
			or = Ship.Orientation_t.OR_DOWN;
			break;
		case 1:
			or = Ship.Orientation_t.OR_UP;
			break;
		case 2:
			or = Ship.Orientation_t.OR_LEFT;
			break;
		case 3:
			or = Ship.Orientation_t.OR_RIGHT;
			break;
		}
		
		// Then pick coordinates.  Keep picking until something works.
		boolean success = false;
		while (false == success)
		{
			int x = random_generator.nextInt(FIRING_PLAN_RAND_EXP) % Ocean.OCEAN_WIDTH;
			int y = random_generator.nextInt(FIRING_PLAN_RAND_EXP) % Ocean.OCEAN_HEIGHT;
			Ocean.PlaceResult_t result = placeShip(new Ship(new Ship.GridCoord(x,y), or, ship_type));
			if (Ocean.PlaceResult_t.E_OK == result)
			{
				success = true; // we could do break, but this is readable too
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
		// Choose shot with respect to shooting strategy
		
		boolean have_shot_soln = false;
		GridCoord next_option = new GridCoord(0,0); // Initialization to avoid errors, but should not be needed
		while (false == have_shot_soln)
		{
			if (strategy_stack.peekFirst().isEmpty())
			{
				strategy_stack.removeFirst();
			}
			// Must always have an element in strategy_stack.  No need for exception handling here because
			// the code guarantees this.
			next_option = strategy_stack.peekFirst().getNextShot();
			// Verify if we have fired here yet
			// This isn't working right... if (shot_results.containsKey(next_option))
			FireResult check = shot_results_array[next_option.x][next_option.y];
			if (check.coord.equals(next_option))  // if passes then we've fired here before
			{
				have_shot_soln = false;
			}
			else
			{
				have_shot_soln = true;
			}
		}
		
		FireResult result;
		if (false == have_shot_soln)
		{
			println("*** NickLutherGamePlayer::fireNow: Assertion Failed - No shot solution.");
			// Example: Fire at x, y
			int x = random_generator.nextInt(10);
			int y = random_generator.nextInt(10);
			next_option = new Ship.GridCoord(x, y);
		}
		
		// Make the shot and store the result
		result = fireShot(next_option);
		shot_results.put(next_option, result);
		shot_results_array[next_option.x][next_option.y] = result;
		
		if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
		{
			println("We hit something!");
			// Note: result.ship_type has information that would be useful.
			// Now we need to prepend a shooting strategy that exploits this information
			
			// Basic answer: HitShootingStrategy makes a box around the hit 
			// if it is a HitShootingStrategy and replace with a LineShootingStrategy.
			strategy_stack.addFirst(new HitAreaShootingStrategy(result.coord.x, result.coord.y));
			
			// Fancy case: If we have a sequence of multiple hits vertical or horizontal, then
			// we want to keep firing in that line.  Detect that and add planned shots.
			// This may return an empty strategy.  That's OK.
			strategy_stack.addFirst(new HitLineShootingStrategy(result.coord.x, result.coord.y, this));
		}
		else if (FireResult.FireResultType_t.SHOT_SUNK == result.result_type)
		{
			println("SUNK");
			
			// In this case, we want to remove ALL targeted shooting strategies from the beginning of the stack
			// Then we return to default strategies.
			while (strategy_stack.peekFirst().isTargeted())
			{
				strategy_stack.removeFirst();
				// There is guaranteed to always be a default strategy, so this is safe.
			}
		}
	}
	
	
	public boolean shotResultsProbeIsHit(int x, int y)
	{
		FireResult check = shot_results_array[x][y];
		if (check.coord.x >= 0 && check.coord.y >= 0)  // valid data
		{
			if (check.result_type == FireResult.FireResultType_t.SHOT_HIT)  // hit but not miss or sunk
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
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

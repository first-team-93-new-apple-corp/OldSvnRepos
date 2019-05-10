/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult.FireResultType_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.ShipType_t;

/**
 * @author nick.luther
 *
 */
public class GameBoard {
	
	public static class FireResult
	{
		/**
		 * This is the main notification of the result of a shot.  If you try to fire more than one shot per turn, the second
		 * will result in a response of SHOT_NOT_AUTHORIZED.
		 */
		public enum FireResultType_t {SHOT_MISS, SHOT_HIT, SHOT_SUNK, SHOT_NOT_AUTHORIZED};
		
		public FireResultType_t result_type;
		public Ship.ShipType_t ship_type = Ship.ShipType_t.SHIP_NULL;
		public Ship.GridCoord coord;
	}
	
	public Ocean m_player_a = new Ocean();
	public Ocean m_player_b = new Ocean();
	private GamePlayer m_player_a_player;
	private GamePlayer m_player_b_player; 
	enum PlayerID_t {PLAYER_NULL, PLAYER_A, PLAYER_B, _PLAYER_TOP};
	private boolean m_shot_authorized[] = new boolean[PlayerID_t._PLAYER_TOP.ordinal()];
	
	
	public GameBoard()
	{
		reset();
	}
	
	public void reset()
	{
		m_player_a.reset();
		m_player_b.reset();
		m_shot_authorized[PlayerID_t.PLAYER_A.ordinal()] = false;
		m_shot_authorized[PlayerID_t.PLAYER_B.ordinal()] = false;
	}
	
	public boolean testPlacementComplete()
	{
		return (m_player_a.isValid() && m_player_b.isValid());
	}

	public static PlayerID_t invertPlayer(PlayerID_t player)
	{
		if (PlayerID_t.PLAYER_A == player)
		{
			return PlayerID_t.PLAYER_B;
		}
		else
		{
			return PlayerID_t.PLAYER_A;
		}
	}
	
	public Ocean getOcean(PlayerID_t player)
	{
		if (PlayerID_t.PLAYER_A == player)
		{
			return m_player_a;
		}
		else if (PlayerID_t.PLAYER_B == player)
		{
			return m_player_b;
		}
		else
		{
			// TODO throw exception
			return new Ocean();
		}
	}
	
	private GamePlayer getPlayer(PlayerID_t player)
	{
		if (PlayerID_t.PLAYER_A == player)
		{
			return m_player_a_player;
		}
		else if (PlayerID_t.PLAYER_B == player)
		{
			return m_player_b_player;
		}
		else
		{
			// TODO throw exception
			// BAD
			return m_player_a_player;
		}
	}
	
	public void registerGamePlayers(GamePlayer player_a, GamePlayer player_b)
	{
		m_player_a_player = player_a;
		m_player_b_player = player_b;
	}
	
	public void notifyShot(FireResult result, PlayerID_t player)
	{
		getPlayer(player).notifyOpponentShot(result);
	}
	
	public String getPlayerName(PlayerID_t player)
	{
		return getPlayer(player).getClass().getSimpleName();
	}
	
	public PlayerID_t gameWinner()
	{
		if (m_player_a.testAllShipsSunk())
		{
			return PlayerID_t.PLAYER_B;
		}
		else if (m_player_b.testAllShipsSunk())
		{
			return PlayerID_t.PLAYER_A;
		}
		else
		{
			return PlayerID_t.PLAYER_NULL;
		}
	}
	
	public boolean testGameOver()
	{
		return (gameWinner() != PlayerID_t.PLAYER_NULL);
	}
	
	/**
	 * This will be called when a GamePlayer implementation fires a shot.  The logic for determining the result 
	 * of the shot and updating the GameBoard (pair of Oceans) lives here.  This function also enforces the rules
	 * (one shot per turn).
	 * @param firing_player Is Player A or Player B shooting?
	 * @param coord What coordinate is this player requesting a shot upon?
	 * @return What happened?  See the FireResult class for detail.  Hit, Miss, Sunk, ship type, etc.
	 */
	public FireResult fireShot(GameBoard.PlayerID_t firing_player, Ship.GridCoord coord)
	{
		// Setup
		coord = new Ship.GridCoord(coord); // Extra protection, on top of GamePlayer implementation, probably not necessary
		FireResult result = new FireResult();
		result.coord = coord;
		
		// Quit early if it isn't our turn (we're trying to take more than one shot in the same turn)
		if (false == m_shot_authorized[firing_player.ordinal()])
		{
			result.result_type = FireResultType_t.SHOT_NOT_AUTHORIZED;
			result.ship_type = ShipType_t.SHIP_NULL;
			return result;
		}
		else  // we didn't need to quit early
		{     // Style note: Nick likes to explicitly state else.  This may help the optimizer infer what I'm doing.
			// Execute the shot
			GameBoard.PlayerID_t opponent_id = GameBoard.invertPlayer(firing_player);
			Ocean opponent = this.getOcean(opponent_id);
			if (opponent.testIntersectShips(coord))
			{
				Ship hit_ship = opponent.findIntersectShip(coord);
				hit_ship.notifyShot(coord);
				result.ship_type = hit_ship.getType();
				if (hit_ship.isSunk())
				{
					result.result_type = FireResult.FireResultType_t.SHOT_SUNK;
				}
				else
				{
					result.result_type = FireResult.FireResultType_t.SHOT_HIT;
				}
			}
			else
			{
				result.result_type = FireResult.FireResultType_t.SHOT_MISS;
			}
			// Service opponent with notification of the shot and result
			notifyShot(result, opponent_id);
			// Clear the shot authorization.  The shot has been fired.
			m_shot_authorized[firing_player.ordinal()] = false;
			// Service the shooter with notification of what happened
			return result;
		}
	}
	
	public void setFireAuthorized(PlayerID_t authorized_player)
	{
		m_shot_authorized[authorized_player.ordinal()] = true;
	}
	
}

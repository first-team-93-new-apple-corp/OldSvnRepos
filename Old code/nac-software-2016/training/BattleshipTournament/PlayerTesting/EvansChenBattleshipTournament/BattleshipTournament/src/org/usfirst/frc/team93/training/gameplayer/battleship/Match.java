/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

/**
 * @author nick.luther
 *
 */
public class Match {
	
	// This ideally should be: private final int MAX_MATCH_STEPS = 2 * Ocean.OCEAN_HEIGHT * Ocean.OCEAN_WIDTH;
	// But for allowing a simple random shooter to work, we'll make it bigger
	private final int MAX_MATCH_STEPS = 200 * Ocean.OCEAN_HEIGHT * Ocean.OCEAN_WIDTH;
	
	private GameBoard m_gameboard;
	private GamePlayer m_player_a;
	private GamePlayer m_player_b;
	private int m_shot_count;
	
	private GameBoard.PlayerID_t m_next_player;

	/**
	 * 
	 */
	public Match() {
		m_gameboard = new GameBoard();
		m_next_player = GameBoard.PlayerID_t.PLAYER_A;  // TODO either make it random, or better yet, guarantee equal opportunities both ways in a tournament
	}
	
	public void setPlayers(GamePlayer player_a, GamePlayer player_b)
	{
		m_player_a = player_a;
		m_player_b = player_b;
	}
	
	public String getPlayerName(GameBoard.PlayerID_t player)
	{
		return m_gameboard.getPlayerName(player);
	}
	
	public void initializeMatch()
	{
		m_gameboard.reset();
		
		GameBoardView view_a = new GameBoardView(m_gameboard, GameBoard.PlayerID_t.PLAYER_A);
		GameBoardView view_b = new GameBoardView(m_gameboard, GameBoard.PlayerID_t.PLAYER_B);
		m_player_a.setGameBoardView(view_a);
		m_player_b.setGameBoardView(view_b);
		
		m_gameboard.registerGamePlayers(m_player_a, m_player_b);
		
		m_player_a.notifyReset();
		m_player_b.notifyReset();
		
		m_player_a.placeShips();
		m_player_b.placeShips();
		
		m_shot_count = 0;
	}
	
	public boolean testInitialized()
	{
		return m_gameboard.testPlacementComplete();
	}
	
	/**
	 * One step of the game, by one player
	 */
	public void playStep()
	{
		// Who is playing?
		GamePlayer cur_player;
		if (GameBoard.PlayerID_t.PLAYER_A == m_next_player)
		{
			cur_player = m_player_a;
		}
		else
		{
			cur_player = m_player_b;
		}
		
		// Notify the GameBoard that a shot is authorized.  GameBoard to enforce only one shot per authorization.
		m_gameboard.setFireAuthorized(m_next_player);
		// Execute the move
		cur_player.fireNow();
		
		// Flip for next move
		m_next_player = GameBoard.invertPlayer(m_next_player);
		
		// keep track of this for statistical interest
		++m_shot_count;
	}
	
	public void playToEnd()
	{
		for (int i = 0; i < this.MAX_MATCH_STEPS; ++i)
		{
			if (testGameOver())
			{
				break;
			}
			playStep();
		}
	}

	public boolean testGameOver()
	{
		return m_gameboard.testGameOver();
	}
	
	public GameBoard.PlayerID_t gameWinner()
	{
		return m_gameboard.gameWinner();
	}
	
	public int getShotCount()
	{
		return m_shot_count;
	}
}

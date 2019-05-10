package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.PlayerID_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.GridCoord;

public class Tournament {

	public class PlayerPair
	{
		GamePlayer player_a;
		GamePlayer player_b;
	}
	
	public class MatchResult
	{
		PlayerPair players;
		GamePlayer winner;
	}
	
	public final int ALLOC_FORECAST_PLAYERS = 25;
	public final int ALLOC_FORECAST_MATCHES_PER_PAIR = 10;
	private List<GamePlayer> m_players_registered = new ArrayList<GamePlayer>(ALLOC_FORECAST_PLAYERS);
	private List<PlayerPair> m_match_plan = new ArrayList<PlayerPair>();
	
	private List<MatchResult> m_match_results = new ArrayList<MatchResult>();
	private Map<String,Integer> m_matches_won = new HashMap<String,Integer>();
	private Map<String,Integer> m_matches_lost = new HashMap<String,Integer>();
	
	
	private Match m_current_match = null;
	private int m_current_match_num = 0;
	
	private String m_match_log;
	
	public boolean enablePrint;
	
	public Tournament() {
		// start with a blank slate
		enablePrint = false;
		
	}

	/**
	 * Call once to register each player before building a schedule
	 * @param player
	 */
	public void registerPlayer(GamePlayer player)
	{
		m_players_registered.add(player);
	}
	
	/**
	 * Reset the tournament before trying to play.
	 * This does not eliminate player registrations.  It does empty the match schedule.
	 */
	public void resetTournament()
	{
		m_match_plan.clear();
		m_current_match_num = 0;
		m_current_match = null;
		
		m_matches_won.clear();
		m_matches_lost.clear();
		m_match_results.clear();
		
		m_match_log = "";
		
		for (GamePlayer player : m_players_registered)
		{
			m_matches_won.put(player.getName(), 0);   // TODO should really have a player.getName()
			m_matches_lost.put(player.getName(), 0);
		}
	}
	
	/**
	 * One way to build a match schedule.  Each player plays every other player num_matches_per_pair times.
	 * @param num_matches_per_pair
	 */
	public void buildTournamentSchedule_RoundRobin(int num_matches_per_pair)
	{
		int num_players = m_players_registered.size();
		for (int player_a_id = 0; player_a_id < num_players; ++player_a_id)
		{
			for (int player_b_id = player_a_id + 1; player_b_id < num_players; ++player_b_id)
			{
				for (int repeat_count = 0; repeat_count < num_matches_per_pair; ++repeat_count)
				{
					PlayerPair my_pair = new PlayerPair();
					my_pair.player_a = m_players_registered.get(player_a_id);
					my_pair.player_b = m_players_registered.get(player_b_id);
					m_match_plan.add(my_pair);
				}
			}
		}
	}
	
	public void playTournamentToEnd()
	{
		while (isTournamentComplete() == false)
		{
			setupCurrentMatch();
			currentMatch().playToEnd();
			advanceToNextMatch();
		}
	}
	
	public void setupCurrentMatch()
	{
		print("----- Begin Tournament-coordinated Match Setup -----");
		print("Match number: " + String.valueOf(m_current_match_num));
		m_current_match = new Match();
		PlayerPair my_pair = currentPair();
		m_current_match.setPlayers(my_pair.player_a, my_pair.player_b);
		m_current_match.initializeMatch();
		
		if (false == m_current_match.testInitialized())
		{
			print("Oceans not initialized.  Verify all ships placed.");
		}
	}
	
	public boolean isTournamentComplete()
	{
		return (m_current_match_num + 1 >= m_match_plan.size());  // only really need ==
	}
	
	public void advanceToNextMatch()
	{
		if (isTournamentComplete() == false)
		{
			m_current_match_num += 1;
		}
	}
	
	
	
	public Match currentMatch()
	{
		return m_current_match;
	}
	
	private PlayerPair currentPair()
	{
		return m_match_plan.get(m_current_match_num);
	}
	
	public void playMatch()
	{

		if (false == currentMatch().testInitialized())
		{
			print("Oceans not initialized.  Verify all ships placed.  The match will not be played.");
		}
		else
		{ 
			// play the match
			String player_a_name = currentMatch().getPlayerName(PlayerID_t.PLAYER_A);
			String player_b_name = currentMatch().getPlayerName(PlayerID_t.PLAYER_B);
			
			print("----- Begin Tournament-coordinated Match Play -----");
			print("Player A is a " + player_a_name);
			print("Player B is a " + player_b_name);
			print("Playing the match...");
			print("");	
			currentMatch().playToEnd();
			final GameBoard.PlayerID_t winner_id = currentMatch().gameWinner();
			String winner_name = "N/A";
			String winner_player_name = "";
			switch (winner_id)
			{
			case PLAYER_A:
				winner_name = "Player A " + player_a_name;
				winner_player_name = player_a_name;
				m_matches_won.put(player_a_name, m_matches_won.getOrDefault(player_a_name, 0) + 1);
				m_matches_lost.put(player_b_name, m_matches_won.getOrDefault(player_b_name, 0) + 1);
				break;
			case PLAYER_B:
				winner_name = "Player B " + player_b_name;
				winner_player_name = player_b_name;
				m_matches_lost.put(player_a_name, m_matches_won.getOrDefault(player_a_name, 0) + 1);
				m_matches_won.put(player_b_name, m_matches_won.getOrDefault(player_b_name, 0) + 1);
				break;
			default:
				winner_name = "N/A";
				break;
			}
			
			// it's really cheating to keep this in memory, but it'll work for our purposes here
			m_match_log += String.valueOf(m_current_match_num) + "," + player_a_name + "," + player_b_name + "," + winner_player_name + "," + currentMatch().getShotCount() + "\r\n";
			
			print("The winner is " + winner_name);
			print("The game took " + String.valueOf(currentMatch().getShotCount()) + " shots.");
		}//else - play the match
		
		
	}//playMatch()
	
	public String getResultsCSV_WinLoss()
	{
		String output = new String();
		output += "PlayerName,Wins,Losses\r\n";
		for (GamePlayer player : m_players_registered)
		{
			String name = player.getName();
			int wins = m_matches_won.getOrDefault(name, 0);
			int losses = m_matches_lost.getOrDefault(name, 0);
			output += name + "," + String.valueOf(wins) + "," + String.valueOf(losses) + "\r\n";
		}
		
		return output;
	}
	
	public String getResultsCSV_MatchLog()
	{
		return m_match_log;  
	}
	
	public GamePlayer findWinner()
	{
		GamePlayer winner = null;
		int winner_wins = 0;
		// not using this if logic now, but will hold on to it
		if (true == m_players_registered.isEmpty())
		{
			return null;
		}
		else
		{
			// simple search - note that we double check the first, doesn't really matter though
			for (GamePlayer player : m_players_registered)
			{
				String name = player.getName();
				int wins = m_matches_won.getOrDefault(name, 0);
				if (wins > winner_wins)
				{
					winner = player;
				}
			}
			
			return winner;
		}
	}
	
	public String findWinnerStr()
	{
		GamePlayer winner = findWinner();
		if (null == winner)
		{
			return new String();
		}
		else
		{
			return winner.getName();
		}
	}
	
	void print(String s)
	{
		if (enablePrint)
		{
			System.out.println(s);
		}
	}

}//class

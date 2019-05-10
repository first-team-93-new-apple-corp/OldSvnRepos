package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult.FireResultType_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.PlayerID_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.ShipType_t;

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
	
	private List<GamePlayer> m_players_registered = new ArrayList<GamePlayer>();
	private List<PlayerPair> m_match_plan = new ArrayList<PlayerPair>();
	
	private List<MatchResult> m_match_results = new ArrayList<MatchResult>();
	private Map<String,Integer> m_matches_won = new HashMap<String,Integer>();
	private Map<String,Integer> m_matches_lost = new HashMap<String,Integer>();
	private Map<String,Integer> m_matches_won_shot_count = new HashMap<String,Integer>();
	private Map<String,Integer> m_matches_won_shot_count_valid = new HashMap<String,Integer>();
	
	private Match m_current_match = null;
	private int m_current_match_num = 0;
	
	public boolean m_enablePrint = false;
	
	protected static TournamentLog log;
	
	public Tournament() {
		// start with a blank slate
	}
	
	public Tournament(boolean log)
	{
		if (log)
		{
			Tournament.log = new TournamentLog();
		}
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
		
		for (GamePlayer player : m_players_registered)
		{
			m_matches_won.put(player.getClass().getSimpleName(), 0);   // TODO should really have a player.getName()
			m_matches_lost.put(player.getClass().getSimpleName(), 0);
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
		println("----- Begin Tournament-coordinated Match Setup -----");
		println("Match number: " + String.valueOf(m_current_match_num));
		m_current_match = new Match();
		PlayerPair my_pair = currentPair();
		m_current_match.setPlayers(my_pair.player_a, my_pair.player_b);
		m_current_match.initializeMatch();
		
		// set who goes first in a way that alternates with match number
		// This will be fair as long as an even number of matches are played at each match-up
		if (m_current_match_num % 2 == 0)
		{
			m_current_match.setFirstPlayer(GameBoard.PlayerID_t.PLAYER_A);
		}
		else
		{
			m_current_match.setFirstPlayer(GameBoard.PlayerID_t.PLAYER_B);
		}
		
		// Complain if not properly initialized
		if (false == m_current_match.testInitialized())
		{
			println("Oceans not initialized.  Verify all ships placed.");
		}
	}
	
	public boolean isTournamentComplete()
	{
		boolean complete = (m_current_match_num >= m_match_plan.size());
		if (complete)
		{
			if (Tournament.log != null)
			{
				Tournament.log.close();
			}
		}
		return complete;  // only really need ==
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
	
	public int getCurrentMatchNumber()
	{
		return m_current_match_num;
	}
	
	private PlayerPair currentPair()
	{
		return m_match_plan.get(m_current_match_num);
	}
	
	public void playMatch()
	{
		// for scoring
		String player_a_name = currentMatch().getPlayerName(PlayerID_t.PLAYER_A);
		String player_b_name = currentMatch().getPlayerName(PlayerID_t.PLAYER_B);

		if (false == currentMatch().testInitialized())
		{
			println("Oceans not initialized.  Verify all ships placed.  The match will not be played.");
			
			boolean player_a_ok = currentMatch().testInitializationPlayerA();
			boolean player_b_ok = currentMatch().testInitializationPlayerB();
			if (player_a_ok == true && player_b_ok == false)
			{
				println("Player A forfeits for failure to place all ships.");
				m_matches_won.put(player_a_name, m_matches_won.getOrDefault(player_a_name, 0) + 1);
				m_matches_lost.put(player_b_name, m_matches_won.getOrDefault(player_b_name, 0) + 1);
			}
			else if (player_a_ok == false && player_b_ok == true)
			{
				println("Player B forfeits for failure to place all ships.");
				m_matches_won.put(player_b_name, m_matches_won.getOrDefault(player_b_name, 0) + 1);
				m_matches_lost.put(player_a_name, m_matches_won.getOrDefault(player_a_name, 0) + 1);
			}
			else
			{
				println("Both players failed to initialize.  Unable to declare a winner.");
			}
			
		}
		else
		{ 
			//get ready to record new match's log
			if (Tournament.log != null)
			{
				Tournament.log.newFile();
			}
			// play the match
			
			println("----- Begin Tournament-coordinated Match Play -----");
			println("Player A is a " + player_a_name);
			println("Player B is a " + player_b_name);
			println("Playing the match...");
			println("");
			currentMatch().playToEnd();
			//close the log
			final GameBoard.PlayerID_t winner_id = currentMatch().gameWinner();
			String winner_name = "N/A";
			String winner_player_name = "";
			switch (winner_id)
			{
			case PLAYER_A:
				winner_name = "Player A " + player_a_name;
				winner_player_name = player_a_name;
				m_matches_won.put(player_a_name, m_matches_won.getOrDefault(player_a_name, 0) + 1);
				m_matches_lost.put(player_b_name, m_matches_lost.getOrDefault(player_b_name, 0) + 1);
				m_matches_won_shot_count.put(player_a_name, m_matches_won_shot_count.getOrDefault(player_a_name, 0) + currentMatch().getShotCount());
				m_matches_won_shot_count_valid.put(player_a_name, m_matches_won_shot_count_valid.getOrDefault(player_a_name, 0) + 1);
				break;
			case PLAYER_B:
				winner_name = "Player B " + player_b_name;
				winner_player_name = player_b_name;
				m_matches_lost.put(player_a_name, m_matches_lost.getOrDefault(player_a_name, 0) + 1);
				m_matches_won.put(player_b_name, m_matches_won.getOrDefault(player_b_name, 0) + 1);
				m_matches_won_shot_count.put(player_b_name, m_matches_won_shot_count.getOrDefault(player_b_name, 0) + currentMatch().getShotCount());
				m_matches_won_shot_count_valid.put(player_b_name, m_matches_won_shot_count_valid.getOrDefault(player_b_name, 0) + 1);
				break;
			default:
				winner_name = "N/A";
				break;
			}
			
			if (Tournament.log != null)
			{
				Tournament.log.writeMatch(String.valueOf(m_current_match_num) + "," + player_a_name + "," + player_b_name + "," + winner_player_name + "," + (int)Math.ceil(0.5 * currentMatch().getShotCount()) + "\r\n");
			}
			
			println("The winner is " + winner_name);
			println("The game took " + String.valueOf((int)Math.ceil(0.5 * currentMatch().getShotCount())) + " shots.");
		}//else - play the match
		
		
	}//playMatch()
	
	public String getResultsCSV_WinLoss()
	{
		String output = new String();
		output += "PlayerName,Wins,Losses,AveWinningShotCount\r\n";
		for (GamePlayer player : m_players_registered)
		{
			String name = player.getClass().getSimpleName();
			int wins = m_matches_won.getOrDefault(name, 0);
			int losses = m_matches_lost.getOrDefault(name, 0);
			// don't divide by zero.  If denom defaults 1, then numerator will be defaulting 0 anyway, and even that should never happen.
			double shot_count = (double)m_matches_won_shot_count.getOrDefault(name, 0) / m_matches_won_shot_count_valid.getOrDefault(name, -1);
			output += name + "," + String.valueOf(wins) + "," + String.valueOf(losses) + "," + String.valueOf(0.5 * shot_count) + "\r\n";
		}
		
		return output;
	}
	
	public String getResultsPrintable()
	{
		String output = new String();
		ArrayList<GamePlayer> players = new ArrayList<GamePlayer>(m_players_registered);
		//sort list of players
		Collections.sort(players, new Comparator<GamePlayer>() {
		    @Override
		    public int compare(GamePlayer a, GamePlayer b) {
		    	Integer winsA = m_matches_won.getOrDefault(a.getClass().getSimpleName(), 0);
		    	Integer winsB = m_matches_won.getOrDefault(b.getClass().getSimpleName(), 0);
		        return winsB.compareTo(winsA);
		    }
		});
		//display stats in orde
		for (int i = 0; i < players.size(); i++)
		{
			GamePlayer player = players.get(i);
			String name = player.getClass().getSimpleName();
			int wins = m_matches_won.getOrDefault(name, 0);
			int losses = m_matches_lost.getOrDefault(name, 0);
			// don't divide by zero.  If denom defaults 1, then numerator will be defaulting 0 anyway, and even that should never happen.
			double shot_count = 0.5 * (double)m_matches_won_shot_count.getOrDefault(name, 0) / m_matches_won_shot_count_valid.getOrDefault(name, -1);
			String shot_count_str = String.valueOf(shot_count);
			if (shot_count < 1)
			{
				shot_count_str = "N/A";
			}
			output += (i + 1) + ": " + name + ": " + "Record: " + String.valueOf(wins) + " wins / " + String.valueOf(losses) + " losses, " + "Avg shots: " + String.valueOf(shot_count_str) + "\r\n";
		}
		return output;
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
					winner_wins = wins;
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
	
	public void println(Object o)
	{
		if (m_enablePrint)
		{
			System.out.println(o);
		}
	}
	
	protected class TournamentLog
	{
		String name;
		String folderName;
		String path;
		PrintWriter writer;
		int match;
		
		String matchPath = "battleship_match_log.csv";
		PrintWriter matchWriter;
		
		public TournamentLog(String name)
		{
			this.name = name;
			reset();
		}
		public TournamentLog()
		{
			this("Tournament");
		}
		
		public void reset()
		{
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy, HH'h', mm'm', ss.SSS's'");
			Date date = new Date();
			this.folderName = this.name + " " + dateFormat.format(date);
			this.path = System.getProperty("user.dir") + "\\logs\\" + this.folderName;
			new File(path).mkdirs();
			match = 0;
			
			if (matchWriter != null)
			{
				matchWriter.close();
			}
		    try
		    {
				matchWriter = new PrintWriter(matchPath, "UTF-8");
			}
		    catch (IOException e) {}
		    writeMatch("MatchID,PlayerA,PlayerB,Winner,ShotCount\r\n");
		}
		
		public void newFile()
		{
			//close the old one if it's not null
			if (writer != null)
			{
				writer.close();
			}
			try
			{
				writer = new PrintWriter(path + "\\" + "Match " + match + ".csv");
			}
			catch (IOException e)
			{
				System.out.println("PrintWriter failed to be created.");
			}
			
			match++;
		}
		
		public void writeActionHeader(String playerStr, String playerName)
		{
			if (writer != null)
			{
				char delim = ',';
				String line = "ACTION" + delim + playerStr + delim + playerName;
				writer.write(line);
				writer.write("\n");
			}
		}
		
		public void writeAction(int x, int y, FireResultType_t result, ShipType_t ship)
		{
			if (writer != null)
			{
				char delim = ',';
				String line = "FIRE" + delim + x + delim + y + delim + result.name() + delim + ship.name();
				writer.write(line);
				writer.write("\n");
			}
		}
		
		public void writeMatch(String s)
		{
			if (matchWriter != null)
			{
				matchWriter.print(s);
			}
		}
		
		//close at end
		public void close()
		{
			if (writer != null)
			{
				writer.close();
			}
			if (matchWriter != null)
			{
				matchWriter.close();
			}
		}
	}

}//class

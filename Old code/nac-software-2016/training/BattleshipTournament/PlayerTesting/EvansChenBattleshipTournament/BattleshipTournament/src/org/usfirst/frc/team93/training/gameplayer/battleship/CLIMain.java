/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author nick.luther
 *
 */
public class CLIMain {

	/**
	 * 
	 */
	public CLIMain() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Team 93 Battleship Tournament - Basic CLI");
		System.out.println("Sep 2016 Tournament-Based Basic Setup");
		System.out.println("");
		
		// Setup the match
		Tournament tourn = new Tournament();
		
		UltimatePlayer plyr = new UltimatePlayer(0);
		tourn.registerPlayer(plyr);
		tourn.registerPlayer(new HunterKillerPlayer(1));
		
		Map<String, Integer> wins = new HashMap<String, Integer>();
		Map<String, Integer> shots = new HashMap<String, Integer>();
		
		int rounds = 1000;
		for (int i = 0; i < rounds; i++)
		{
			//System.out.println("Starting round " + i + ":");
			
			// Call before building the tournament schedule
			tourn.resetTournament();
			
			// build a simple single match per pair round robin
			tourn.buildTournamentSchedule_RoundRobin(1);
			
			// for simple use case, play through one match in a step fashion
			tourn.setupCurrentMatch();
			if (false == tourn.currentMatch().testInitialized())
			{
				System.out.println("Oceans not initialized.  Verify all ships placed.");
			}
			tourn.playMatch();
			String winner_name = tourn.findWinnerStr();
			
			//track wins
			if (!wins.containsKey(winner_name))
			{
				wins.put(winner_name, 0);
			}
			wins.replace(winner_name, wins.get(winner_name) + 1);
			
			//track shots
			if (!shots.containsKey(winner_name))
			{
				shots.put(winner_name, 0);
			}
			shots.replace(winner_name, shots.get(winner_name) + tourn.currentMatch().getShotCount());
		}
		// In the future, will need to step through all matches like this or play tournament to end
		
		//get winner
		String winner = "NO_WINNER";
		int count = -1;
		for (Entry<String, Integer> entry : wins.entrySet())
		{
			if (entry.getValue() > count)
			{
				winner = entry.getKey();
				count = entry.getValue();
			}
		}
		
		int totalShots = 0;
		for (Entry<String, Integer> entry : shots.entrySet())
		{
			totalShots += entry.getValue();
		}
		
		// report results
		System.out.println("----- Tournament Results -----");
		System.out.println("The tournament winner is " + winner + ", winning " + wins.get(winner) + "/" + rounds + " games," + 
				" with average shot counts of " + ((float)shots.get(winner) * 0.5 / (float)wins.get(winner)) + " when winning, and " +
				((float)totalShots * 0.5 / (float)rounds) + " on average.");
		
		System.out.println(mean(plyr.positionScores));
		System.out.println(stddev(plyr.positionScores));
		
	}
	
	public static double mean(List<Double> l)
	{
		double sum = 0;
		for (Double d : l)
		{
			sum += d;
		}
		return (sum / l.size());
	}
	
	public static double stddev(List<Double> l)
	{
		double mn = mean(l);
		double variance = 0;
		for (Double d : l)
		{
			variance += Math.pow(d - mn, 2);
		}
		return Math.sqrt(variance / l.size());
	}

}

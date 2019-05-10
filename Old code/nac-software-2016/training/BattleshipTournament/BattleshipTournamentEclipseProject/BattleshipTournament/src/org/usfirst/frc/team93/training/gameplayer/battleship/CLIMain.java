/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.io.IOException;
import java.io.PrintWriter;

import org.usfirst.frc.team93.training.gameplayer.battleship.players.EvansExamplePlayerCheckerboard;
import org.usfirst.frc.team93.training.gameplayer.battleship.players.EvansExamplePlayerEasy;
import org.usfirst.frc.team93.training.gameplayer.battleship.players.GeorgePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.players.HeavenPlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.players.Player_Colby_BetterColbyPlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.players.Player_EvansChen_AdaptableEnhancedPlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.players.Player_HeavenIsCool_player;
import org.usfirst.frc.team93.training.gameplayer.battleship.players.Sewer_Turtle_Player;
import org.usfirst.frc.team93.training.gameplayer.battleship.players.EvansCoolCode;
import org.usfirst.frc.team93.training.gameplayer.battleship.players.TestGamePlayer;

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
		Tournament tourn = new Tournament(true);
		
		tourn.registerPlayer(new GeorgePlayer());
		tourn.registerPlayer(new TestGamePlayer());
		
		// Call before building the tournament schedule
		tourn.resetTournament();
		
		int matches = 10;
		// build a simple round robin at x matches per pair
		// because of first turn alternation, this number must be even to be fair
		tourn.buildTournamentSchedule_RoundRobin(matches);
		
		// Execute the full tournament
		while (tourn.isTournamentComplete() == false)
		{
			// for simple use case, play through one match in a step fashion
			tourn.setupCurrentMatch();
			if (false == tourn.currentMatch().testInitialized())
			{
				System.out.println("Match " + tourn.getCurrentMatchNumber() + " Oceans not initialized.  Verify all ships placed.");
			}
			tourn.playMatch();
			// In the future, will need to step through all matches like this or play tournament to end
			
			String match_winner_name = tourn.currentMatch().getPlayerName(tourn.currentMatch().gameWinner());
			//System.out.println("+++ Match " + tourn.getCurrentMatchNumber() + " winner: " + match_winner_name);
			
			tourn.advanceToNextMatch();
		}
		
		// report results
		String winner_name = tourn.findWinnerStr();
		System.out.println("----- Tournament Results -----");
		System.out.println("The tournament winner is " + winner_name);
		
		System.out.println(tourn.getResultsPrintable());
		
		// write log files
		try
		{
		    PrintWriter writer = new PrintWriter("battleship_win_loss.csv", "UTF-8");
		    writer.print(tourn.getResultsCSV_WinLoss());
		    writer.close();
		}
		catch (IOException e)
		{
		   // do something
		}
		
	}

}

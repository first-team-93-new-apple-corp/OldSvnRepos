/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

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
		
		tourn.registerPlayer(new TestGamePlayer());
		tourn.registerPlayer(new TestGamePlayer2());
		
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
		// In the future, will need to step through all matches like this or play tournament to end
		
		// report results
		System.out.println("----- Tournament Results -----");
		System.out.println("The tournament winner is " + winner_name);
		
	}

}

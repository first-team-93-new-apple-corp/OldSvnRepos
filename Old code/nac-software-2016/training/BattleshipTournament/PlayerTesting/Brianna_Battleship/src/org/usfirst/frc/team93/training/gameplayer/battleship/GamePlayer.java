package org.usfirst.frc.team93.training.gameplayer.battleship;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;

/**
 * Base class for game player classes.  Extend this and override functions as necessary to get up and running.
 * See TestGamePlayer for an example of how to extend this.
 * @author nick.luther
 *
 */
public abstract class GamePlayer {

	private GameBoardView m_gameboard_view;
	
	/**
	 */
	GamePlayer()
	{
	}
	
	/**
	 * For setup purposes.  Called from Match.
	 * @param own_ocean Restricted view of the GameBoard.  Passed when called by Match.
	 */
	public void setGameBoardView(GameBoardView own_ocean)
	{
		m_gameboard_view = own_ocean;
	}
	
	/**
	 * Use this function to fire shots
	 * @param coord Set the x,y coordinate you are firing at.
	 * @return Store the FireResult response and inspect it for the result type (HIT, MISS, SUNK, NOT_AUTHORIZED).  You'll also have access to a ship type and coordinate (the same you sent) as applicable.
	 */
	protected FireResult fireShot(Ship.GridCoord coord)
	{
		// DEBUG use only for first line
		System.out.println(getClass().getSimpleName() + " fires at " + String.valueOf(coord.x) + "," + String.valueOf(coord.y));
		return m_gameboard_view.fireShot(new Ship.GridCoord(coord));  // If a player uses one GridCoord object, this will protect logs
	}
	
	/** 
	 * Use this function to place ships
	 * @param ship Create a new Ship() with a ship type, orientation, and location.  Then pass it to this function to place it on your Ocean.
	 * @return The result of the placement.  Store the PlaceResult_t and inspect it to learn if the placement was successful.  
	 * Expect PlaceResult_t.E_OK if you were successful.  Anything else indicates a problem.
	 */
	public Ocean.PlaceResult_t placeShip(Ship ship)
	{
		System.out.println(getClass().getSimpleName() + " places ship at " + String.valueOf(ship.getLocation().x) + "," + String.valueOf(ship.getLocation().y));
		return m_gameboard_view.myOcean().addShip(ship);
	}
	
	/**
	 * Reset in preparation for a new match.  You are not required to act.  You must override this function.  Your override implementation will be called
	 * to notify you that a new match is about to start.
	 */
	public abstract void notifyReset();
	
	
	/**
	 * Access own gameboard view.
	 * Should this be private or protected?  Initially leaning protected, but seems easier
	 * to expose everything needed in GamePlayer ADT and then all reporting can happen here too.
	 * 
	 * Currently private to force everything through GamePlayer class for reporting.  That could change.
	 */
	private GameBoardView myGameBoardView()
	{
		return m_gameboard_view;
	}
	
	/**
	 * Override this.  Your override function will be called to tell you it's time to place your ships.  
	 * In your placeShips function, you must place exactly five ships, and of them exactly one of each type of ship.
	 * @post All five ships are placed by you.
	 */
	public abstract void placeShips();
	
	/**
	 * Override this.  Your override function will be called to tell you it's time to fire a shot.
	 * From your override function, call fireShot() to specify where you want to shoot.  
	 * Fire exactly one shot each time fireNow is called.  If you try to fire more, the additional shots
	 * won't be processed.
	 * @post One shot has been fired by you.  No more, no less.
	 */
	public abstract void fireNow();
	
	/**
	 * This is to notify you of your opponent's shot against your Ocean.  You don't need to do anything with this.
	 * You are provided the opportunity to override this to capture the information.  This is because in a normal
	 * Battleship game you would have access to information about where your opponent is shooting.  Perhaps you
	 * could use this information to learn about your opponent's shooting patterns.
	 */
	public void notifyOpponentShot(FireResult result)
	{
	}
	
	/**
	 * In real Battleship, you can see and identify your opponent.  Therefore you are allowed to ask the identity
	 * of your opponent in this game too.  
	 * @return String name of your current opponent player
	 */
	protected String getOpponentName()
	{
		return myGameBoardView().getOpponentName();
	}
	
	public String getName()
	{
		return getClass().getSimpleName();
	}
}

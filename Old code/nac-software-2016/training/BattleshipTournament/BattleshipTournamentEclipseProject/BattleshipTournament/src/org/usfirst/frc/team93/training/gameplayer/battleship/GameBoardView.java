/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;

/**
 * @author nick.luther
 *
 */
public class GameBoardView {
	private GameBoard m_gameboard;
	private GameBoard.PlayerID_t m_player;
	
	public GameBoardView(GameBoard gameboard, GameBoard.PlayerID_t player)
	{
		m_gameboard = gameboard;
		m_player = player;
	}
	
	public Ocean myOcean()
	{
		return m_gameboard.getOcean(m_player);
	}
	
	public Ocean.PlaceResult_t placeShip(Ship ship)
	{
		return myOcean().addShip(ship);
	}
	
	public FireResult fireShot(Ship.GridCoord coord)
	{
		return m_gameboard.fireShot(m_player, coord);
	}
	
	public String getOpponentName()
	{
		GameBoard.PlayerID_t opponent_id = GameBoard.invertPlayer(m_player);
		return m_gameboard.getPlayerName(opponent_id);
	}

}

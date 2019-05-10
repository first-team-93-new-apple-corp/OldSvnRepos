/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean.PlaceResult_t;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class InputPlayer extends GamePlayer {

	Random random_generator = new Random();
	String name;
	List<Ship.ShipType_t> ships = new ArrayList<Ship.ShipType_t>();
	
	/**
	 */
	public InputPlayer() 
	{
		super();
		this.name = "";
	}
	
	public InputPlayer(String name)
	{
		super();
		this.name = ", " + name;
	}

	
	/**
	 * Notification that a new match is about to start. 
	 */
	@Override
	public void notifyReset()
	{
		ships.clear();
		ships.add(Ship.ShipType_t.SHIP_CARRIER);
		ships.add(Ship.ShipType_t.SHIP_BATTLESHIP);
		ships.add(Ship.ShipType_t.SHIP_CRUISER);
		ships.add(Ship.ShipType_t.SHIP_SUBMARINE);
		ships.add(Ship.ShipType_t.SHIP_DESTROYER);
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		for (int s = 0; s < ships.size(); s ++)
		{
			PlaceResult_t placeResult = PlaceResult_t.E_BAD_SHIP_TYPE;
			while(placeResult != PlaceResult_t.E_OK)
			{
				System.out.println("Place " + getShipName(ships.get(s)) + " at:");
				String coordStr = null;
				Coordinate c = null;
			    while(c == null)
			    {
				    try
				    {
				    	coordStr = reader.readLine();
				    }
				    catch (IOException e)
				    {
				        e.printStackTrace();
				    }
			    	c = stringToCoordinate(playerStringToCoordinateString(coordStr));
			    }
			    System.out.println("With orientation:");
				String orStr = null;
				Ship.Orientation_t or = null;
			    while(or == null)
			    {
				    try
				    {
				    	orStr = reader.readLine();
				    }
				    catch (IOException e)
				    {
				        e.printStackTrace();
				    }
				    or = getEnumFromString(Ship.Orientation_t.class, orStr);
				    if (or == null)
				    {
				    	System.out.println("Invalid Orientation.");
				    }
			    }
			    placeResult = placeShip(new Ship(new Ship.GridCoord(c.x,c.y), or, ships.get(s)));
			    if (placeResult != PlaceResult_t.E_OK)
			    {
			    	System.out.println("ERROR: Could not place ship: " + placeResult.name());
			    }
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
		// TODO Required: Fire your shot.  Override this logic with your own.
		System.out.println("It's your turn" + name + ".");
		// Example: Fire at x, y
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Fire at:");
		String coordStr = null;
		Coordinate c = null;
	    while(c == null)
	    {
		    try
		    {
		    	coordStr = reader.readLine();
		    }
		    catch (IOException e)
		    {
		        e.printStackTrace();
		    }
	    	c = stringToCoordinate(playerStringToCoordinateString(coordStr));
	    }
	    
	    FireResult result = fireShot(new Ship.GridCoord(c.x, c.y));
	    System.out.println("You fired at (" + coordinateToPlayerString(new Coordinate(c.x, c.y)) + ")");
	    printResult(result, "the opponent's");
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
		System.out.println("It's your opponent's turn" + name + ".");
		System.out.println("The opponent fired at (" + coordinateToPlayerString(new Coordinate(result.coord.x, result.coord.y)) + ")");
		printResult(result, "your");
	}
	
	private Coordinate stringToCoordinate(String s)
	{
		String[] numbers = s.split(",");
		Coordinate c = null;
		try
		{
			c = new Coordinate(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
		}
		catch (NumberFormatException e)
		{
			c = null;
			System.out.println("Invalid Numbers: Numbers must be integers.");
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Invalid Coordinate Format. Format must be \"x,y\" ");
		}
		return c;
	}
	
	public class Coordinate
	{
		int x;
		int y;
		Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	private void printResult(FireResult r, String side)
	{
		switch(r.result_type)
		{
			case SHOT_HIT:
				System.out.println("The shot hit " + side + " " + getShipName(r.ship_type) + ".");
				break;
			case SHOT_SUNK:
				System.out.println("The shot sunk " + side + " " + getShipName(r.ship_type) + ".");
				break;
			case SHOT_MISS:
				System.out.println("The shot missed.");
				break;
			case SHOT_NOT_AUTHORIZED:
				System.out.println("The shot was invalid. Try again.");
				break;
			default:
				System.out.println("The shot was invalid.");
				break;
		}
	}
	
	private String getShipName(Ship.ShipType_t s)
	{
		String str = "";
		switch(s)
		{
			case SHIP_CARRIER:
				str = "Carrier";
				break;
			case SHIP_BATTLESHIP:
				str = "Battleship";
				break;
			case SHIP_CRUISER:
				str = "Cruiser";
				break;
			case SHIP_SUBMARINE:
				str = "Submarine";
				break;
			case SHIP_DESTROYER:
				str = "Destroyer";
				break;
			default:
				str = "Unknown Ship";
				break;
		}
		return str;
	}
	
	private String playerStringToCoordinateString(String s)
	{
		String[] playerCoord;
		if (s.length() > 2)
		{
			playerCoord = s.split(",");
		}
		else
		{
			playerCoord = new String[2];
			playerCoord[0] = Character.toString(s.charAt(0));
			playerCoord[1] = Character.toString(s.charAt(1));
		}
		String letter = playerCoord[0].toLowerCase();
		final String alphabet = "abcdefghijklmnopqrstuvwxyz";
		int letterInt = alphabet.indexOf(letter.charAt(0));
		int numberInt = -1;
		try
		{
			numberInt = Integer.parseInt(playerCoord[1]);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Invalid Number: Number must be an integer.");
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Invalid Sequence.");
		}
		numberInt -= 1;
		return (numberInt + "," + letterInt);
	}
	
	private String coordinateToPlayerString(Coordinate c)
	{
		final String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String letter = Character.toString(alphabet.charAt(c.y)).toUpperCase();
		return ("(" + letter + (c.x + 1) + ")");
	}
	
	/**
	 * A common method for all enums since they can't have another base class
	 * @param <T> Enum type
	 * @param c enum type. All enums must be all caps.
	 * @param string case insensitive
	 * @return corresponding enum, or null
	 */
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
	    if( c != null && string != null ) {
	        try {
	            return Enum.valueOf(c, string.trim().toUpperCase());
	        } catch(IllegalArgumentException ex) {
	        }
	    }
	    return null;
	}
}

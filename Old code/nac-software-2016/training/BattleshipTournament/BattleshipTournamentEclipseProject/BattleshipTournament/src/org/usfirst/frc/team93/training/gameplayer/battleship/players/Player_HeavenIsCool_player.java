/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship.players;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult.FireResultType_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.GridCoord;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class Player_HeavenIsCool_player extends GamePlayer {

	Random random_generator = new Random();
	boolean[][] arr;
	ArrayList<Integer> xHitListCarrier;
	ArrayList<Integer> yHitListCarrier;
	ArrayList<Integer> xHitListBattleship;
	ArrayList<Integer> yHitListBattleship;
	ArrayList<Integer> xHitListCruiser;
	ArrayList<Integer> yHitListCruiser;
	ArrayList<Integer> xHitListSubmarine;
	ArrayList<Integer> yHitListSubmarine;
	ArrayList<Integer> xHitListDestroyer;
	ArrayList<Integer> yHitListDestroyer;
	ArrayList<String> MyHitList = new ArrayList<String>();
	/**
	 * 	public Player_HeavenIsCool_player() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * Notification that a new match is about to start. 
	 */
	@Override
	public void notifyReset()
	{
		System.out.println("NEW GAMU");
		hitMode = false;
		arr = new boolean [10][10];
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				arr[i][j] = false;
			}
		}
		xHitListCarrier = new ArrayList<Integer>();
		yHitListCarrier = new ArrayList<Integer>();
		xHitListBattleship = new ArrayList<Integer>();
		yHitListBattleship = new ArrayList<Integer>();
		xHitListCruiser = new ArrayList<Integer>();
		yHitListCruiser = new ArrayList<Integer>();
		xHitListSubmarine = new ArrayList<Integer>();
		yHitListSubmarine = new ArrayList<Integer>();
		xHitListDestroyer = new ArrayList<Integer>();
		yHitListDestroyer = new ArrayList<Integer>();
		NextShot();
		lastHitXCarrier = -1;
		lastHitYCarrier = -1;
		lastHitXbattleship = -1;
		lastHitYBattleship = -1;
		lastHitXCruiser = -1;
		lastHitYCruiser = -1;
		lastHitXSubmarine = -1;
		lastHitYSubmarine = -1;
		lastHitXDestroyer = -1;
		lastHitYDestroyer = -1;
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
		Random random = new Random();
		int x = random.nextInt(10);
		int y = random.nextInt(10);
		//creates random x and y coordinates
		// TODO Required: place five ships, one of each type, with no collisions
		
		//generate 0 or 1
		 //if 0 use OR_RIGHT
		//if 1 use OR_DOWN
		int a = random.nextInt(2);
		Ship.Orientation_t or;
		if (a==0)
		{
			or = Ship.Orientation_t.OR_RIGHT;
		}
		else
		{
			or = Ship.Orientation_t.OR_DOWN;
		}
		Ocean.PlaceResult_t carrierstatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_CARRIER));
		while(carrierstatus != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
			carrierstatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_CARRIER));
		}
		//places SHIP_CARRIER and makes sure it is placed
		if (a==0)
		{
			or = Ship.Orientation_t.OR_RIGHT;
		}
		else
		{
			or = Ship.Orientation_t.OR_DOWN;
		}
		Ocean.PlaceResult_t battleshipstatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_BATTLESHIP));
		while(battleshipstatus != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
			battleshipstatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_BATTLESHIP));
		}
		//places SHIP_BATTLESHIP and makes sure it is placed
		if (a==0)
		{
			or = Ship.Orientation_t.OR_RIGHT;
		}
		else
		{
			or = Ship.Orientation_t.OR_DOWN;
		}
		Ocean.PlaceResult_t cruiserstatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_CRUISER));
		while(cruiserstatus != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
			cruiserstatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_CRUISER));
		}
		//places SHIP_CRUISER and makes sure it is placed
		if (a==0)
		{
			or = Ship.Orientation_t.OR_RIGHT;
		}
		else
		{
			or = Ship.Orientation_t.OR_DOWN;
		}
		Ocean.PlaceResult_t submarinestatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_SUBMARINE));
		while(submarinestatus != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
			submarinestatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_SUBMARINE));
		}
		//places SHIP_SUBMARINE and makes sure it is placed
		if (a==0)
		{
			or = Ship.Orientation_t.OR_RIGHT;
		}
		else
		{
			or = Ship.Orientation_t.OR_DOWN;
		}
		Ocean.PlaceResult_t destroyerstatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_DESTROYER));
		while(destroyerstatus != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
			destroyerstatus = placeShip(new Ship(new Ship.GridCoord(x,y), or, Ship.ShipType_t.SHIP_DESTROYER));
		}
		//places SHIP_DESTROYER and makes sure it is placed
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#fireNow()
	 */
	/**
	 * It's your turn.  Fire your shot!  Inspect the result.
	 */
	int x;
	int y;
	int lastHitXCarrier = -1;
	int lastHitYCarrier = -1;
	int lastHitXbattleship = -1;
	int lastHitYBattleship = -1;
	int lastHitXCruiser = -1;
	int lastHitYCruiser = -1;
	int lastHitXSubmarine = -1;
	int lastHitYSubmarine = -1;
	int lastHitXDestroyer = -1;
	int lastHitYDestroyer = -1;
	boolean hitMode = false;
	FireResultType_t f = FireResultType_t.SHOT_MISS;
	@Override
	public void fireNow() 
	{
		//process last hit
		if (f == FireResultType_t.SHOT_HIT)
		{
			hitMode = true;
		}
		else if (f == FireResultType_t.SHOT_SUNK)
		{
			//clear the hit list
			//get out of hitMode
			//lastHit = -1
			xHitListCarrier.clear();
			yHitListCarrier.clear();
			xHitListBattleship.clear();
			yHitListBattleship.clear();
			xHitListCruiser.clear();
			yHitListCruiser.clear();
			xHitListSubmarine.clear();
			yHitListSubmarine.clear();
			xHitListDestroyer.clear();
			yHitListDestroyer.clear();
			hitMode = false;
			lastHitXCarrier = -1;
			lastHitYCarrier = -1;
			lastHitXbattleship = -1;
			lastHitYBattleship = -1;
			lastHitXCruiser = -1;
			lastHitYCruiser = -1;
			lastHitXSubmarine = -1;
			lastHitYSubmarine = -1;
			lastHitXDestroyer = -1;
			lastHitYDestroyer = -1;
		}
		
		//calculate coordinates
		if(!hitMode)
		{
			NextShot();	
		}
		if(hitMode){
			Point nextSpotToShoot = popNextPointFromHitList();
			if (nextSpotToShoot != null)
			{
				x = nextSpotToShoot.x;
				y = nextSpotToShoot.y;
			}
			else
			{
				System.out.println("Wat the faq");
				System.exit(0);
			}
		}
		//makes sure a spot isn't hit twice

		if(arr[x][y]==false){
			
			FireResult res = fireShot(new GridCoord(x, y));
			f = res.result_type;
			Ship.ShipType_t ship = res.ship_type;
			if(f==FireResultType_t.SHOT_HIT){
				x++;
				if(x>=0 && x<10 && y>=0 && y<10)
				{
					if (arr[x][y]==false)
					{
						addPointToHitList(ship, x, y);
					}
				}
				
				x=x-2;
				if(x>=0 && x<10 && y>=0 && y<10)
				{
					if (arr[x][y]==false)
					{
						addPointToHitList(ship, x, y);
					}
				}
				
				x++;
				y++;
				if(x>=0 && x<10 && y>=0 && y<10)
				{
					if (arr[x][y]==false)
					{
						addPointToHitList(ship, x, y);
					}
				}
				y=y-2;
				
				if(x>=0 && x<10 && y>=0 && y<10)
				{
					if (arr[x][y]==false)
					{
						addPointToHitList(ship, x, y);
					}
				}
				y++;
				
				//if we're in hitmode
				//then our last shot was a hit
				//then since this a hit
				//compare this to our last hit

				//if the y's are equal
				//filter by y's
				if(lastHitY == y)
				{
					filterHitListY(y);
				}
				//if the x's are equal
				//filter by x's
				if(lastHitX == x)
				{
					filterHitListX(x);
				}
				
				lastHitX = x;
				lastHitY = y;
				//store our last hit
			}
			arr[x][y]=true;
		}
	}
	public void RandomShot()
	{
		Random random = new Random();
		x = random.nextInt(10);
		y = random.nextInt(10);
	}
	public void NextShot()
	{
		RandomShot();
		while(arr[x][y] == true){
			RandomShot();
		}
	}
	//save me
	// STOP BOOLEAN ME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//save////////////////////////////////YAY///////////////////////////////YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaY
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
	
	}
	
	private void addPointToHitList(Ship.ShipType_t ship, int x, int y)
	{
		if (ship == Ship.ShipType_t.SHIP_CARRIER)
		{
			xHitListCarrier.add(new Integer(x));
			yHitListCarrier.add(new Integer(y));
		}
		if (ship == Ship.ShipType_t.SHIP_BATTLESHIP)
		{
			xHitListBattleship.add(new Integer(x));
			yHitListBattleship.add(new Integer(y));
		}
		if (ship == Ship.ShipType_t.SHIP_CRUISER)
		{
			xHitListCruiser.add(new Integer(x));
			yHitListCruiser.add(new Integer(y));
		}
		if (ship == Ship.ShipType_t.SHIP_SUBMARINE)
		{
			xHitListSubmarine.add(new Integer(x));
			yHitListSubmarine.add(new Integer(y));
		}
		if (ship == Ship.ShipType_t.SHIP_DESTROYER)
		{
			xHitListDestroyer.add(new Integer(x));
			yHitListDestroyer.add(new Integer(y));
		}
	}
	
	//Type name = new Type()
	private void filterHitListY(Ship.ShipType_t ship, int y)
	{
		ArrayList<Integer> newXHitList = new ArrayList<Integer>();
		ArrayList<Integer> newYHitList = new ArrayList<Integer>();
		int i = 0;
		while(i < hitListX.size())
		{
			if(y==yHitList.get(i))
			{
				newXHitList.add(hitListX.get(i));
				newYHitList.add(yHitList.get(i));
			}
			i++;
		}
		
		hitListX = newXHitList;
		yHitList = newYHitList;
	}
	
	private void filterHitListX(int x)
	{
		ArrayList<Integer> newXHitList = new ArrayList<Integer>();
		ArrayList<Integer> newYHitList = new ArrayList<Integer>();
		int i = 0;
		while(i < xHitList.size())
		{
			if(x==xHitList.get(i))
			{
				newXHitList.add(xHitList.get(i));
				newYHitList.add(yHitList.get(i));
			}
			i++;
		}
		xHitList = newXHitList;
		yHitList = newYHitList;
	}
	
	private Point popNextPointFromHitList()
	{
		Point returnValue = null;
		
		if(!xHitList.isEmpty() && !yHitList.isEmpty())
		{
			int x = xHitList.remove(0).intValue();
			int y = yHitList.remove(0).intValue();
			
			returnValue = new Point(x,y);
		}
		
		return returnValue;
	}
}

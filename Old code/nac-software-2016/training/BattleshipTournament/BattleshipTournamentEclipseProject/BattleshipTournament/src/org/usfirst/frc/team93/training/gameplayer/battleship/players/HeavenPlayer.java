/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship.players;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult.FireResultType_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.GridCoord;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.Orientation_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.ShipType_t;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class HeavenPlayer extends GamePlayer {
	public class EnemyShip{
		ArrayList<Point> hitList;
		boolean sunk;
		int length;
		boolean horizontal;
		int numHits;
		EnemyShip(int length){
			this.length = length;
			sunk = false;
			horizontal = false;
			numHits = 0;
			hitList = new ArrayList<Point>();
		}
		
		public boolean isSunk()
		{
			return sunk;
		}
		
		public boolean isHorizontal()
		{
			return false;
		}
		
		// handles a hit on this ship at the specified point
		public void handleHit(Point hitPoint)
		{
			
		}
		
		public Point getNextFromHitList()
		{
			return null;
		}
		
		public boolean isHitListEmpty()
		{
			return false;
		}
	}
	Random random_generator = new Random();
	ArrayList<Integer> yHitList;
	ArrayList<Integer> xHitList;
	boolean[][] arr;
	/**
	 */
	public HeavenPlayer() 
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
		hitMode = false;
		xHitList = new ArrayList<Integer>();
		yHitList = new ArrayList<Integer>();
		arr = new boolean [10][10];
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				arr[i][j] = false;
			}
		}
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
	boolean hitMode = false;
	FireResultType_t f = FireResultType_t.SHOT_MISS;
	@Override
	public void fireNow() 
	{
		// TODO Required: Fire your shot.  Override this logic with your own.
		if (f == FireResultType_t.SHOT_HIT)
		{
			hitMode = true;
			
		}
		else if (f == FireResultType_t.SHOT_SUNK)
		{
			hitMode = false;
		}
		if(!hitMode){
			NextShot();
		}
		if(hitMode){
			Point nextSpotToShoot = popNextPointFromHitList();
			if (nextSpotToShoot != null)
			{
				x = nextSpotToShoot.x;
				y = nextSpotToShoot.y;
			}
		}
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
						addPointToHitList(x, y);
					}
				}
				
				x=x-2;
				if(x>=0 && x<10 && y>=0 && y<10)
				{
					if (arr[x][y]==false)
					{
						addPointToHitList(x, y);
					}
				}
				
				x++;
				y++;
				if(x>=0 && x<10 && y>=0 && y<10)
				{
					if (arr[x][y]==false)
					{
						addPointToHitList(x, y);
					}
				}
				y=y-2;
				
				if(x>=0 && x<10 && y>=0 && y<10)
				{
					if (arr[x][y]==false)
					{
						addPointToHitList(x, y);
					}
				}
				y++;
			}
			arr[x][y]=true;
		}
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
	}
	
	private void addPointToHitList(int x, int y)
	{
		xHitList.add(new Integer(x));
		yHitList.add(new Integer(y));
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

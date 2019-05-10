package org.usfirst.frc.team93.training.gameplayer.battleship.players;

import java.util.ArrayList;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult.FireResultType_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean.PlaceResult_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.GridCoord;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.Orientation_t;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.ShipType_t;

/**
 * 
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class GeorgePlayer extends GamePlayer {
	
	Random random_generator = new Random();
	public int[][] Field;
	ArrayList<Point> shotsFired;
	Random random;
	//Number 1-4 that defines direction: 1 = Up, 2 = Right, 3 = Down, 4 = Left
	int shotDirection = 1;
	//Are we seeking around last hit for rest of ship?
	boolean seekingMode = false;
	boolean SmOnBoard = false;
	public GeorgePlayer() 
	{
		m_enablePrint = true;//   					        ENABLES PRINT
		random = new Random();
		//super();
		//Field = new int[10][10];
		//Field[0][0] = 1;
		//int x = Field[0][0];
		shotsFired = new ArrayList<Point>();
	}

	
	public class Point {
		int x;
		int y;
		public Point(int x, int y){// Feeds x and y
			//Point's x equals fed x
			this.x = x;
			//Point's y equals fed y
			this.y = y;
		}
		
	}
	
//Defines a Point that keeps the coordinates of the last shot fired
	Point lastPointHit;//Last shot that "Shot_Hit" was received
	Point shipFirstHit;//First spot that was hit on the ship that is currently being tracked
	Point nextShot;//The next location to be shot
	/**
	 * Notification that a new match is about to start. 
	 */
	@Override
	public void notifyReset()
	{
		shotsFired.clear();
		shipFirstHit = new Point (-1, -1);
		lastPointHit = new Point(-1,-1);
		nextShot = new Point(-1,-1);
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
	int x = random.nextInt(10);
		int y = random.nextInt(10);
		//For random Ship placements
		int Ca = random.nextInt(2);// Carrier
		Ca++;
		int B = random.nextInt(2);// Battleship
		B++;
		int Cr = random.nextInt(2);// Cruiser
		Cr++;
		int S = random.nextInt(2);// Submarine
		S++;
		int D = random.nextInt(2);// Destroyer
		D++;
//Carrier
		Ocean.PlaceResult_t CarrierStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[Ca], Ship.ShipType_t.SHIP_CARRIER));
		while(CarrierStatus != Ocean.PlaceResult_t.E_OK)//                                                  /\Randomizes ship placement
		{
			//If Carrier placement is not OK
			x = random.nextInt(10);
			y = random.nextInt(10);
			Ca = random.nextInt(2);
			Ca++;
			CarrierStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[Ca], Ship.ShipType_t.SHIP_CARRIER));
		}
//Battleship
		Ocean.PlaceResult_t BattleshipStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[B], Ship.ShipType_t.SHIP_BATTLESHIP));
		while(BattleshipStatus != Ocean.PlaceResult_t.E_OK)
		{
			//If Battleship placement is not OK
			x = random.nextInt(10);
			y = random.nextInt(10);
			B = random.nextInt(2);
			B++;
			BattleshipStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[B], Ship.ShipType_t.SHIP_BATTLESHIP));
		}
//Cruiser
		Ocean.PlaceResult_t CruiserStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[Cr], Ship.ShipType_t.SHIP_CRUISER));
		while(CruiserStatus != Ocean.PlaceResult_t.E_OK)
		{
			//If Cruiser placement is not OK
			x = random.nextInt(10);
			y = random.nextInt(10);
			Cr = random.nextInt(2);
			Cr++;
			CruiserStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[Cr], Ship.ShipType_t.SHIP_CRUISER));
		}
//Submarine
		Ocean.PlaceResult_t SubmarineStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[S], Ship.ShipType_t.SHIP_SUBMARINE));
		while(SubmarineStatus != Ocean.PlaceResult_t.E_OK)
		{
			//If Submarine placement is not OK
			x = random.nextInt(10);
			y = random.nextInt(10);
			S = random.nextInt(2);
			S++;
			SubmarineStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[S], Ship.ShipType_t.SHIP_SUBMARINE));
		}
//Destroyer
		Ocean.PlaceResult_t DestroyerStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[D], Ship.ShipType_t.SHIP_DESTROYER));
		while(DestroyerStatus != Ocean.PlaceResult_t.E_OK)
		{
			//If Destroyer placement is not OK
			x = random.nextInt(10);
			y = random.nextInt(10);
			D = random.nextInt(2);
			D++;
			DestroyerStatus = placeShip(new Ship(new Ship.GridCoord(x,y), Ship.Orientation_t.values()[D], Ship.ShipType_t.SHIP_DESTROYER));
		}
	}
	
	@Override
	public void fireNow() 
	{
		int x = lastPointHit.x;
		int y = lastPointHit.y;
		boolean hasHit = true;
		boolean forceRandom = false;
/*
		if (x ==! 9)
		{
			x == x+2;
		}
		else
		(
			x == 0;
			y ++
		)
*/
		
/*
		//Randomly sets x
		int x = random.nextInt(10);
		//Randomly sets y
		int y = random.nextInt(10);
		//Makes it so that it initially runs the "if hit" loop
		boolean hasHit = true;
*/
		while (hasHit == true)
		{
			if (seekingMode == false || forceRandom == true)
			{
				//Randomizes x and y
				x = random.nextInt(10);
				y = random.nextInt(10);
				forceRandom = false;
			}
			hasHit = false;
			//If (x,y) has already been shot, re-rolls
			for (int i = 0; i < shotsFired.size(); i++)
			{
				//If the last shot x is equal to the newly rolled x,
				//AND the last shot y is equal to the newly rolled y,
				if(shotsFired.get(i).x == x && shotsFired.get(i).y == y)
				{
					hasHit = true;
					forceRandom = true;
				}
			}
			if (x>9 || x<0)
			{
				if (x>9)
				{
					x --;
				}
				else//  (x<0)
				{
					x ++;
				}
			}
			else if (y>9 || y<0)
			{
				if (y>9)
				{
					y --;
				}
				else//  (x<0)
				{
					y ++;
				}
			}
		}
										//SEEKING MODE
		if (seekingMode == true)
		{
			SmOnBoard = false;	//Assumes the worst, being the Seeking Mode shot going off of the board
			while (SmOnBoard == false){
				x = lastPointHit.x;
				y = lastPointHit.y;
				if (shotDirection == 1){//Down
					y++;
					for (int i = 0; i<shotsFired.size();++i){
						if (shotsFired.get(i).x == x && shotsFired.get(i).y == y){
							y++;
						}
						println("Down");
					}
				}
				if (shotDirection == 2){//Right
					x++;
					for (int i = 0; i<shotsFired.size();++i){
						if (shotsFired.get(i).x == x && shotsFired.get(i).y == y){
							x++;
						}
						println("Right");
					}
				}
				if (shotDirection == 3){//Up
					y--;
					for (int i = 0; i<shotsFired.size();++i){
						if (shotsFired.get(i).x == x && shotsFired.get(i).y == y){
							y--;
						}
						println("Up");
					}
				}
				if (shotDirection == 4){//Left
					x--;
					for (int i = 0; i<shotsFired.size();++i){
						if (shotsFired.get(i).x == x && shotsFired.get(i).y == y){
							x--;
						}
						println("Left");
					}
				}
				if (x < 0 || x > 10 || y < 0 || y > 10){//Checks to see if the shot is not on the board
					SmOnBoard = false;
					shotDirection ++;
				}
				else
				{
					SmOnBoard = true;
				}
			}
		}
	/*										//Gets "seekingMode" to stop shooting off of the board
		if (x>9 || x<0 && seekingMode == true)
		{
			if (shotDirection == 1)//Down
			{
				for (int i = 0; i < shotsFired.size(); i++)
				while (hasHit == true)
				{
					y--;
					hasHit = false;
					if(shotsFired.get(i).x == x && shotsFired.get(i).y == y)
					{
						hasHit = true;
					}
				}
			}
			if (shotDirection == 2)//Right
			{
				for (int i = 0; i < shotsFired.size(); i++)
				while (hasHit == false)
				{
					if(shotsFired.get(i).x == x && shotsFired.get(i).y == y)
					{
						x--;
					}
				}
			}
			if (shotDirection == 3)//Up
			{
				for (int i = 0; i < shotsFired.size(); i++)
				while (hasHit == false)
				{
					if(shotsFired.get(i).x == x && shotsFired.get(i).y == y)
					{
						y++;
					}
				}
			}
			if (shotDirection == 4)//Left
			{
				for (int i = 0; i < shotsFired.size(); i++)
				while (hasHit == false)
				{
					if(shotsFired.get(i).x == x && shotsFired.get(i).y == y)
					{
						x++;
					}
				}
			}
		}
		*/
		if (nextShot.x != -1 && nextShot.y != -1 && seekingMode != true)
		{
			x = nextShot.x;
			y = nextShot.y;
		}
		FireResult result = fireShot(new Ship.GridCoord(x, y));
		if (result.result_type == FireResult.FireResultType_t.SHOT_HIT){
			if (shotDirection == 5){
				shotDirection = 1;
			}
			if (seekingMode == false)//If hit during the random-hitting mode
			{
				shipFirstHit = new Point (x,y);//Logs the first hit of a ship
			}
			seekingMode = true;//Turning on SeekingMode
			println("				Hit!");
			lastPointHit = new Point(x, y);
		}
		shotsFired.add(new Point(x,y));//adds last (x,y) to the shotsFired
		if (result.result_type == FireResult.FireResultType_t.SHOT_MISS){
			println("				Miss!");
			if (seekingMode == true)//		THIS IS WHERE THE SEEKING MODE HAPPENS
			{
				if (shotDirection != 4)
				{
					shotDirection ++;
				}
				else
				{
					shotDirection = 1;
				}
				//x1 (and y1) is the first hit on ship, with x2 (and y2) being the last before a miss 
				int x1 = shipFirstHit.x;
				int y1 = shipFirstHit.y;
				int x2 = lastPointHit.x;
				int y2 = lastPointHit.y;
				if (x1 == x2)// If the first x is equal to the last x, that means that the ship I am shooting is horizontal.
				{
					if (y1 > y2)// If shotDirection = Down
					{
						y1++;
						nextShot = new Point(x1,y1);
					}
					else// only when y1 < y1 (shotDirection = Up)
					{
						y1--;
						nextShot = new Point(x1,y1);
					}
				}
				else//only when y1 = y2 (The ship is vertical)
				{
					if (x1 > x2)//If shotDirection = Left
					{
						x1++;
						nextShot = new Point(x1,y1);
					}
					else// only when x1 < x2 (shotDirection = Right);
					{
						x1--;
						nextShot = new Point(x1,y1);
					}
				}
			}	
		}
		if (result.result_type == FireResult.FireResultType_t.SHOT_SUNK){
			seekingMode = false;
			shotDirection = 1;
			println("				Sunk!");
			nextShot = new Point (-1,-1);
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
}
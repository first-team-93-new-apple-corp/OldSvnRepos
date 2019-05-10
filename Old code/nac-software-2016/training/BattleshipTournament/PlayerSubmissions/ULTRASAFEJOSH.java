package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.ArrayList;
import java.util.Random;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ship.ShipType_t;

import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean.PlaceResult_t;

/**
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF
 * 
 * @author josh.hawbaker
 *
 */

public class ULTRASAFEJOSH extends GamePlayer
{

	boolean shotReady;;
	boolean huntingDestroyer;
	//note that the destroyer is 2 spots long in Nick's code, but it's supposed to be 3
	boolean huntingCruiser;
	//note that cruiser is supposed to be 3 spots long  instead of 2
	boolean huntingSub;
	boolean huntingBattleship;
	boolean huntingCarrier;
	boolean firstShotReceived;
	int DestroyerHits;
	int CruiserHits;
	int SubHits;
	int BattleshipHits;
	int CarrierHits;
	int lastHitX;
	int lastHitY;
	int lastShotX;
	int lastShotY;

	public class Point
	{
		public int x;
		public int y;

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o)
		{
			boolean returnValue = true;
			if (o instanceof Point)
			{
				Point a = (Point) o;
				if (this.x != a.x)
				{
					returnValue = false;
				}
				if (this.y != a.y)
				{
					returnValue = false;
				}
			} else
			{
				returnValue = false;
			}
			return returnValue;
		}
	}

	Random random_generator = new Random();
	Point checkYUp;
	Point checkYDown;
	Point checkXLeft;
	Point checkXRight;
	Point missUp;
	Point missDown;
	Point missLeft;
	Point missRight;
	Point firstHitDestroyer;
	Point firstHitCruiser;
	Point firstHitSub;
	Point firstHitBattleship;
	Point firstHitCarrier;
	ArrayList<Point> shotList = new ArrayList<Point>();
	ArrayList<Point> netList = new ArrayList<Point>();
	ArrayList<Point> missList = new ArrayList<Point>();
	ArrayList<Point> DestroyerKillList = new ArrayList<Point>();
	ArrayList<Point> CruiserKillList = new ArrayList<Point>();
	ArrayList<Point> SubKillList = new ArrayList<Point>();
	ArrayList<Point> BattleshipKillList = new ArrayList<Point>();
	ArrayList<Point> CarrierKillList = new ArrayList<Point>();
	enum shipAlignment{Up, Down, Left, Right, Unknown}
	shipAlignment Destroyer;
	shipAlignment Cruiser;
	shipAlignment Sub;
	shipAlignment Battleship;
	shipAlignment Carrier;
	enum myShip{Destroyer, Cruiser, Sub, Battleship, Carrier, Sunk, Unknown}
	myShip targetedShip; 

	/**
	 * TO DO
	 * 
	 * Make an enum so that I can try to detect if the ship that I am hunting is
	 * arranged horizontally or vertically (list would include horizontal,
	 * vertical, and undetermined Make a few cases for huntingShip = true
	 * depending on those enum values
	 * 
	 * Actually do something for place ships
	 * 
	 */

	/**
	 */
	public ULTRASAFEJOSH()
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
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				Point netShot = new Point(i, j);
				if (i % 2 == j % 2)
				{
					netList.add(netShot);
				}
			}

		}
		lastHitX = 999;
		lastHitY = 999;
		lastShotX = 999;
		lastShotY = 999;
		DestroyerKillList.clear();
		CruiserKillList.clear();
		SubKillList.clear();
		BattleshipKillList.clear();
		CarrierKillList.clear();
		shotList.clear();
		missList.clear();
		firstHitDestroyer = null;
		firstHitCruiser = null;
		firstHitSub = null;
		firstHitBattleship = null;
		firstHitCarrier = null;
		checkYUp = null;
		firstShotReceived = false;
		huntingDestroyer = false;
		huntingCruiser = false;
		huntingSub = false;
		huntingBattleship = false;
		huntingCarrier = false;
		DestroyerHits = 0;
		CruiserHits = 0;
		SubHits = 0;
		BattleshipHits = 0;
		CarrierHits = 0;
		targetedShip = myShip.Unknown;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#
	 * placeShips()
	 */
	/**
	 * Place your ships. You must place exactly five. One of each type.
	 */
	public Ship.Orientation_t randomarrange()
	{
		Random random = new Random();
		Ship.Orientation_t ship = Ship.Orientation_t.OR_DOWN;
		int arrangement = random.nextInt(4);

		if(arrangement == 0)
		{
			ship = Ship.Orientation_t.OR_DOWN;
		}
		if(arrangement == 1)
		{
			ship = Ship.Orientation_t.OR_UP;
		}
		if(arrangement == 2)
		{
			ship = Ship.Orientation_t.OR_LEFT;
		}
		if(arrangement == 3)
		{
			ship = Ship.Orientation_t.OR_RIGHT;
		}
		return ship;
	}
	@Override
	public void placeShips()
	{
		// TODO Required: place five ships, one of each type, with no collisions
		Random random = new Random();
		int x = random.nextInt(10);
		int y = random.nextInt(10);

		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_CARRIER)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_BATTLESHIP)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_CRUISER)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_SUBMARINE)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), randomarrange(), Ship.ShipType_t.SHIP_DESTROYER)) != Ocean.PlaceResult_t.E_OK)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#fireNow(
	 * )
	 */
	/**
	 * It's your turn. Fire your shot! Inspect the result.
	 */
	@Override
	public void fireNow()
	{
		shotReady = false;
		Point shotResult = null;
		FireResult result = null;
		
		while (!shotReady)
		{
			int x = random_generator.nextInt(10);
			int y = random_generator.nextInt(10);
			shotResult = new Point(x, y);
			if (!DestroyerKillList.isEmpty() && targetedShip == myShip.Destroyer)
			{
				shotResult = DestroyerKillList.get(0);
				DestroyerKillList.remove(0);
				System.out.println("HuntingDestroyer");
			} 
			if (!CruiserKillList.isEmpty() && targetedShip == myShip.Cruiser)
				{
				shotResult = CruiserKillList.get(0);
				CruiserKillList.remove(0);
				System.out.println("HuntingCruiser");
				}
			if(!SubKillList.isEmpty() && targetedShip == myShip.Sub)
			{
				shotResult = SubKillList.get(0);
				SubKillList.remove(0);
				System.out.println("HuntingSub");
			}
			if(!BattleshipKillList.isEmpty() && targetedShip == myShip.Battleship)
			{
				shotResult = BattleshipKillList.get(0);
				BattleshipKillList.remove(0);
				System.out.println("HuntingBattleship");
			}
			if(!CarrierKillList.isEmpty() && targetedShip == myShip.Carrier)
			{
				shotResult = CarrierKillList.get(0);
				CarrierKillList.remove(0);
				System.out.println("HuntingCarrier");
			}
			if(targetedShip == myShip.Unknown)
			{
				if(huntingDestroyer)
				{
					shotResult = DestroyerKillList.get(0);
					DestroyerKillList.remove(0);
				}
				else if(huntingCruiser)
				{
					shotResult = CruiserKillList.get(0);
					CruiserKillList.remove(0);
				}
				else if(huntingSub)
				{
					shotResult = SubKillList.get(0);
					SubKillList.remove(0);
				}
				else if(huntingBattleship)
				{
					shotResult = BattleshipKillList.get(0);
					BattleshipKillList.remove(0);
				}
				else if(huntingCarrier)
				{
					shotResult = CarrierKillList.get(0);
					CarrierKillList.remove(0);
				}
				else
				{
					if (!shotList.contains(shotResult))
					{
						if (!netList.contains(shotResult))
						{
							shotReady = true;
						}
					}					
				}
			}
			int missYUp = (shotResult.getY() == 0) ? 0 : shotResult.getY() - 1;
			int missYDown = (shotResult.getY() == 9) ? 9 : shotResult.getY() + 1;
			int missXLeft = (shotResult.getX() == 0) ? 0 : shotResult.getX() - 1;
			int missXRight = (shotResult.getX() == 9) ? 9 : shotResult.getX() + 1;

			missUp = new Point(shotResult.getX(), missYUp);
			missDown = new Point(shotResult.getX(), missYDown);
			missLeft = new Point(missXLeft, shotResult.getY());
			missRight = new Point(missXRight, shotResult.getY());
			if(missList.contains(missUp))
			{
				if(missList.contains(missDown) && missList.contains(missLeft) && missList.contains(missLeft) && missList.contains(missRight))
				{
							System.out.println("Miss avoided");
				}
			}

			shotReady = false;
			if (!shotList.contains(shotResult))
			{
				shotReady = true;
			}
		}
		result = fireShot(new Ship.GridCoord(shotResult.getX(), shotResult.getY()));
		shotList.add(shotResult);
		lastShotX = shotResult.getX();
		lastShotY = shotResult.getY();

		if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
		{
			System.out.println("We hit something!");
			System.out.println(result.ship_type);
			lastHitX = shotResult.getX();
			lastHitY = shotResult.getY();
			int checkingYUp = (lastHitY == 0) ? 0 : lastHitY - 1;
			int checkingYDown = (lastHitY == 9) ? 9 : lastHitY + 1;
			int checkingXLeft = (lastHitX == 0) ? 0 : lastHitX - 1;
			int checkingXRight = (lastHitX == 9) ? 9 : lastHitX + 1;
			checkYUp = new Point(lastHitX, checkingYUp);
			checkYDown = new Point(lastHitX, checkingYDown);
			checkXLeft = new Point(checkingXLeft, lastHitY);
			checkXRight = new Point(checkingXRight, lastHitY);
			if (result.ship_type == ShipType_t.SHIP_DESTROYER)
			{
				if(DestroyerHits == 0)
				{
					firstHitDestroyer = new Point (shotResult.getX(), shotResult.getY());
				}
				huntingDestroyer = true;
				targetedShip = myShip.Destroyer;
				DestroyerHits += 1;
				DestroyerKillList.add(checkYUp);
				DestroyerKillList.add(checkYDown);
				DestroyerKillList.add(checkXLeft);
				DestroyerKillList.add(checkXRight);
				Destroyer = shipAlignment.Unknown;
			}
			if (result.ship_type == ShipType_t.SHIP_CRUISER)
			{
				if(CruiserHits == 0)
				{
					firstHitCruiser = new Point (shotResult.getX(), shotResult.getY());
				}
				huntingCruiser = true;
				targetedShip = myShip.Cruiser;
				CruiserHits += 1;
				CruiserKillList.add(checkYUp);
				CruiserKillList.add(checkYDown);
				CruiserKillList.add(checkXLeft);
				CruiserKillList.add(checkXRight);
				Cruiser = shipAlignment.Unknown;
				if (shotResult.getX() == firstHitCruiser.getX() && shotResult.getY() == firstHitCruiser.getY() - 1)
				{
					Cruiser = shipAlignment.Up;
				}
				if (shotResult.getX() == firstHitCruiser.getX() && shotResult.getY() == firstHitCruiser.getY() + 1)
				{
					Cruiser = shipAlignment.Down;
				}
				if (shotResult.getX() == firstHitCruiser.getX() - 1 && shotResult.getY() == firstHitCruiser.getY())
				{
					Cruiser = shipAlignment.Left;
				}
				if (shotResult.getX() == firstHitCruiser.getX() + 1 && shotResult.getY() == firstHitCruiser.getY())
				{
					Cruiser = shipAlignment.Right;
				}
			}
			if (result.ship_type == ShipType_t.SHIP_SUBMARINE)
			{
				if(SubHits == 0)
				{
					firstHitSub = new Point (shotResult.getX(), shotResult.getY());
				}
				huntingSub = true;
				targetedShip = myShip.Sub;
				SubHits += 1;
				SubKillList.add(checkYUp);
				SubKillList.add(checkYDown);
				SubKillList.add(checkXLeft);
				SubKillList.add(checkXRight);
				Sub = shipAlignment.Unknown;
				if (SubHits == 2)
				{
				if (shotResult.getX() == firstHitSub.getX() && shotResult.getY() == firstHitSub.getY() - 1)
				{
					Sub = shipAlignment.Up;
				}
				if (shotResult.getX() == firstHitSub.getX() && shotResult.getY() == firstHitSub.getY() + 1)
				{
					Sub = shipAlignment.Down;
				}
				if (shotResult.getX() == firstHitSub.getX() - 1 && shotResult.getY() == firstHitSub.getY())
				{
					Sub = shipAlignment.Left;
				}
				if (shotResult.getX() == firstHitSub.getX() + 1 && shotResult.getY() == firstHitSub.getY())
				{
					Sub = shipAlignment.Right;
				}
				}
			}
			if (result.ship_type == ShipType_t.SHIP_BATTLESHIP)
			{
				if(BattleshipHits == 0)
				{
					firstHitBattleship = new Point (shotResult.getX(), shotResult.getY());
				}
				huntingBattleship = true;
				targetedShip = myShip.Battleship;
				BattleshipHits += 1;
				BattleshipKillList.add(checkYUp);
				BattleshipKillList.add(checkYDown);
				BattleshipKillList.add(checkXLeft);
				BattleshipKillList.add(checkXRight);
				Battleship = shipAlignment.Unknown;
				if (BattleshipHits == 2)
				{
				if (shotResult.getX() == firstHitBattleship.getX() && shotResult.getY() == firstHitBattleship.getY() - 1)
				{
					Battleship = shipAlignment.Up;
				}
				if (shotResult.getX() == firstHitBattleship.getX() && shotResult.getY() == firstHitBattleship.getY() + 1)
				{
					Battleship = shipAlignment.Down;
				}
				if (shotResult.getX() == firstHitBattleship.getX() - 1 && shotResult.getY() == firstHitBattleship.getY())
				{
					Battleship = shipAlignment.Left;
				}
				if (shotResult.getX() == firstHitBattleship.getX() + 1 && shotResult.getY() == firstHitBattleship.getY())
				{
					Battleship = shipAlignment.Right;
				}
				}
			}
			if (result.ship_type == ShipType_t.SHIP_CARRIER)
			{
				if(CarrierHits == 0)
				{
					firstHitCarrier = new Point (shotResult.getX(), shotResult.getY());
				}
				huntingCarrier = true;
				targetedShip = myShip.Carrier;
				CarrierHits += 1;
				CarrierKillList.add(checkYUp);
				CarrierKillList.add(checkYDown);
				CarrierKillList.add(checkXLeft);
				CarrierKillList.add(checkXRight);
				Carrier = shipAlignment.Unknown;
				if (shotResult.getX() == firstHitCarrier.getX() && shotResult.getY() == firstHitCarrier.getY() - 1)
				{
					Carrier = shipAlignment.Up;
				}
				if (shotResult.getX() == firstHitCarrier.getX() && shotResult.getY() == firstHitCarrier.getY() + 1)
				{
					Carrier = shipAlignment.Down;
				}
				if (shotResult.getX() == firstHitCarrier.getX() - 1 && shotResult.getY() == firstHitCarrier.getY())
				{
					Carrier = shipAlignment.Left;
				}
				if (shotResult.getX() == firstHitCarrier.getX() + 1 && shotResult.getY() == firstHitCarrier.getY())
				{
					Carrier = shipAlignment.Right;
				}
			}
			int totalHunts = 0;
			totalHunts = (huntingDestroyer) ? totalHunts + 1 : totalHunts;
			totalHunts = (huntingCruiser) ? totalHunts + 1 : totalHunts;
			totalHunts = (huntingSub) ? totalHunts + 1 : totalHunts;
			totalHunts = (huntingBattleship) ? totalHunts + 1 : totalHunts;
			totalHunts = (huntingCarrier) ? totalHunts + 1 : totalHunts;
			
			if(totalHunts > 1)
			{
				targetedShip = myShip.Unknown;
			}

			if (DestroyerHits == 2)
			{
				DestroyerKillList.clear();
			}
			if (CruiserHits == 3)
			{
				CruiserKillList.clear();
			}
			if (SubHits == 3)
			{
				SubKillList.clear();
			}
			if (BattleshipHits == 4)
			{
				BattleshipKillList.clear();
			}
			if (CarrierHits == 5)
			{
				CarrierKillList.clear();
			}
		}
		else
		{
			missList.add(shotResult);
		}
		if (FireResult.FireResultType_t.SHOT_SUNK == result.result_type)
		{
			huntingDestroyer = (result.ship_type == ShipType_t.SHIP_DESTROYER) ? false : huntingDestroyer;
			huntingCruiser = (result.ship_type == ShipType_t.SHIP_CRUISER) ? false : huntingCruiser;
			huntingSub = (result.ship_type == ShipType_t.SHIP_SUBMARINE) ? false : huntingSub;
			huntingBattleship = (result.ship_type == ShipType_t.SHIP_BATTLESHIP) ? false : huntingBattleship;
			huntingCarrier = (result.ship_type == ShipType_t.SHIP_CARRIER) ? false : huntingCarrier;
			targetedShip = myShip.Sunk;
			System.out.println("Ship sunk!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team93.training.gameplayer.battleship.GamePlayer#
	 * notifyOpponentShot()
	 */
	/**
	 * This is to notify you of your opponent's shot against your Ocean. You can
	 * do something with this, or not.
	 */
	@Override
	public void notifyOpponentShot(FireResult result)
	{
		//I don't know how to read your opponent's shots as a coordinate.
		//In the future, I want to check if their first shot was in a corner
		//if it was, I place all of my ships in the opposite corner the next game
	}

	public String stringList(ArrayList<Point> l)
	{
		String s = "[";
		for (int i = 0; i < l.size(); i++)
		{
			if (i != 0)
			{
				s = s + ", ";
			}
			s = s + stringPoint(l.get(i));
		}
		s = s + "]";
		return s;
	}

	public String stringPoint(Point p)
	{
		return ("(" + p.getX() + ", " + p.getY() + ")");
	}
}

/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;
import org.usfirst.frc.team93.training.gameplayer.battleship.InputPlayer.Coordinate;
import org.usfirst.frc.team93.training.gameplayer.battleship.InsightfulHunterPlayer.OceanStatus;
import org.usfirst.frc.team93.training.gameplayer.battleship.InsightfulHunterPlayer.ShipOrientation;
import org.usfirst.frc.team93.training.gameplayer.battleship.InsightfulHunterPlayer.State;
import org.usfirst.frc.team93.training.gameplayer.battleship.InsightfulHunterPlayer.TargetShip;
import org.usfirst.frc.team93.training.gameplayer.battleship.Ocean.PlaceResult_t;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class CheaterIIPlayer extends GamePlayer {

	Random random_generator = new Random();
	
	public enum OceanStatus
	{
		HIT, MISS, UNKNOWN, NULL, SHIP;
	}
	
	public enum State
	{
		HUNT, KILL, NULL;
	}
	
	enum ShipOrientation
	{
		HORIZONTAL, VERTICAL, UNKNOWN, NULL;
	}
	
	OceanStatus[][] oceanMap = new OceanStatus[10][10];
	State state = State.HUNT;
	
	List<TargetShip> TargetShipList = new ArrayList<TargetShip>();
	
	List<Ship.ShipType_t> RemainingShips = new ArrayList<Ship.ShipType_t>();
	
	EnumMap<Ship.ShipType_t, int[][]> probability = new EnumMap<Ship.ShipType_t, int[][]>(Ship.ShipType_t.class);
	ConsoleOutputCapturer tracker = new ConsoleOutputCapturer();
	ArrayList<String> stream = new ArrayList<String>();
	ArrayList<Coordinate> enemyShips = new ArrayList<Coordinate>();
	int turn;
	
	/**
	 */
	public CheaterIIPlayer() 
	{
		super();
		turn = 0;
	}

	
	/**
	 * Notification that a new match is about to start. 
	 */
	@Override
	public void notifyReset()
	{
		tracker.start();
		turn = 0;
		stream.clear();
		resetProbability();
		clearOcean();
		clearTargets();
		resetRemainingShips();
		state = State.HUNT;
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
		// TODO Required: place five ships, one of each type, with no collisions
		int x = random_generator.nextInt(10);
		int y = random_generator.nextInt(10);
		int o = random_generator.nextInt(2);
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_CARRIER)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_BATTLESHIP)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_CRUISER)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_SUBMARINE)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
		}
		while(placeShip(new Ship(new Ship.GridCoord(x, y), getOrientationFromInt(o), Ship.ShipType_t.SHIP_DESTROYER)) != PlaceResult_t.E_OK)
		{
			x = random_generator.nextInt(10);
			y = random_generator.nextInt(10);
			o = random_generator.nextInt(2);
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
		Coordinate c = new Coordinate(0, 0);
		try {
			c = chooseFire();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GameBoard.FireResult result = fire(c);
		//process result.
		updateState(c, result);
		updateOceanMap(c, result);
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
	
	public class ConsoleOutputCapturer {
	    private ByteArrayOutputStream baos;
	    private PrintStream previous;
	    private boolean capturing;

	    public void start() {
	        if (capturing) {
	            return;
	        }

	        capturing = true;
	        previous = System.out;      
	        baos = new ByteArrayOutputStream();

	        OutputStream outputStreamCombiner = 
	                new OutputStreamCombiner(Arrays.asList(previous, baos)); 
	        PrintStream custom = new PrintStream(outputStreamCombiner);

	        System.setOut(custom);
	    }

	    public String stop() {
	        if (!capturing) {
	            return "";
	        }

	        System.setOut(previous);

	        String capturedValue = baos.toString();             

	        baos = null;
	        previous = null;
	        capturing = false;

	        return capturedValue;
	    }

	    private class OutputStreamCombiner extends OutputStream {
	        private List<OutputStream> outputStreams;

	        public OutputStreamCombiner(List<OutputStream> outputStreams) {
	            this.outputStreams = outputStreams;
	        }

	        public void write(int b) throws IOException {
	            for (OutputStream os : outputStreams) {
	                os.write(b);
	            }
	        }

	        public void flush() throws IOException {
	            for (OutputStream os : outputStreams) {
	                os.flush();
	            }
	        }

	        public void close() throws IOException {
	            for (OutputStream os : outputStreams) {
	                os.close();
	            }
	        }
	    }
	}


	private Coordinate chooseFire() throws IOException
	{
		if (turn == 0)
		{
			//grab the stream
			BufferedReader rdr = new BufferedReader(new StringReader(tracker.stop()));
			for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
			    stream.add(line);
			}
			rdr.close();
			
			//read the stream
			for (int i = 0; i < stream.size(); i ++)
			{
				String str = stream.get(i);
				String key = " places ship at ";
				//if line is about a ship placement
				if (str.contains(key))
				{
					//and is not about self
					if (!str.contains(getName()))
					{
						//println is about opponent ship so read it
						//get number pair
						int pos = str.indexOf(key) + key.length();
						String pair = str.substring(pos);
						enemyShips.add(stringToCoordinate(pair));
					}
				}
			}
			
			//add enemy ship coords to probability map
			for (Coordinate c : enemyShips)
			{
				incrementProbability(c.x, c.y, 10000);
			}
		}
		turn ++;
		
		//update state
		if (TargetShipList.size() == 0)
		{
			state = State.HUNT;
		}
		else
		{
			state = State.KILL;
		}
		
		Coordinate c;
		switch(state)
		{
			case HUNT:
				int x = random_generator.nextInt(10);
				int y = random_generator.nextInt(10);
				final int minimumProbability = 0;
				int maximum = minimumProbability;
				for (int i = 0; i < 10; i++)
				{
					for (int j = 0; j < 10; j++)
					{
						if ((oceanMap[i][j] == OceanStatus.UNKNOWN) && (((i + (11 * j)) % getShipMinLength()) == 0))
						{
							if (getProbability(i, j) > maximum)
							{
								x = i;
								y = j;
								maximum = getProbability(i, j);
							}
							else if (getProbability(i, j) < 0)
							{
								//print("Coordinate (" + i + ", " + j + " has been eliminated, Probability = " + getProbability(i, j) + ".");
							}
						}
					}
				}
				if (maximum == minimumProbability)
				{
					for (int i = 0; i < 10; i++)
					{
						for (int j = 0; j < 10; j++)
						{
							if (oceanMap[i][j] == OceanStatus.UNKNOWN)
							{
								if (getProbability(i, j) > maximum)
								{
									x = i;
									y = j;
									maximum = getProbability(i, j);
								}
							}
						}
					}
				}
				c = new Coordinate(x, y);
				break;
			case KILL:
				if (TargetShipList.get(0).queue.size() == 0)
				{
					print("WAT");
				}
				List<Coordinate> points = TargetShipList.get(0).queue;
				int max = 0;
				c = points.get(0);
				for (int i = 0; i < points.size(); i++)
				{
					if (getProbability(points.get(i).x, points.get(i).y) > max)
					{
						max = getProbability(points.get(i).x, points.get(i).y);
						c = points.get(i);
					}
				}
				break;
			default:
				c = new Coordinate(0, 0);
				break;
		}
		return c;
	}
	
	private GameBoard.FireResult fire(int x, int y)
	{
		GameBoard.FireResult result = fireShot(new Ship.GridCoord(x, y));
		removeQueuePoint(x, y);
		print(getName() + " fires at (" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
		printLog("(" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
		return (result);
	}
	private GameBoard.FireResult fire(Coordinate c)
	{
		return (fire(c.x, c.y));
	}
	
	private void updateOceanMap(Coordinate coord, GameBoard.FireResult result)
	{
		switch(result.result_type)
		{
			case SHOT_MISS:
				oceanMap[coord.x][coord.y] = OceanStatus.MISS;
				break;
			case SHOT_HIT:
				oceanMap[coord.x][coord.y] = OceanStatus.HIT;
				break;
			case SHOT_SUNK:
				oceanMap[coord.x][coord.y] = OceanStatus.HIT;
				break;
			default:
				break;
		}
	}
	
	private void updateState(Coordinate coord, GameBoard.FireResult result)
	{
		//process hit and miss
		switch(result.result_type)
		{
			case SHOT_MISS:
				updateProbability(coord.x, coord.y, result);
				//nothing happens.
				if (TargetShipList.size() == 0)
				{
					print("MISS!");
				}
				else
				{
					print("MISSED THE " + TargetShipList.get(0).ship.name() + "!");
				}
				printLog(", 0\n");
				break;
			case SHOT_HIT:
				print("HIT THE " + result.ship_type.name() + "!");
				printLog(", 1\n");
				updateProbability(coord.x, coord.y, result);
				//if a ship was hit...
				if (checkShipType(result.ship_type))
				{
					//if first time, add ship to target list.
					TargetShip targetShip = searchTargetList(result.ship_type);
					if (targetShip == null)
					{
						TargetShipList.add(new TargetShip(result.ship_type, coord));
					}
					//if not first time, update that ship's queue.
					else
					{
						targetShip.addHit(coord);
					}
				}
				break;
			case SHOT_SUNK:
				print("SUNK THE " + result.ship_type.name() + "!");
				printLog(", 1\n");
				updateProbability(coord.x, coord.y, result);
				//remove the ship from the target list, as it is destroyed.
				TargetShipList.remove(searchTargetListIndex(result.ship_type));
				RemainingShips.remove(result.ship_type);
				//set probability to 0 for all other squares for this ship, as ship is sunk
				for (int i = 0; i < 10; i ++)
				{
					for (int j = 0; j < 10; j ++)
					{
						setProbability(i, j, result.ship_type, 0);
					}
				}
				break;
			default:
				break;
		}
		
		//update state
		if (TargetShipList.size() == 0)
		{
			state = State.HUNT;
		}
		else
		{
			state = State.KILL;
		}
	}
	
	private void clearOcean()
	{
		for (int i = 0; i < 10; i ++)
		{
			for (int j = 0; j < 10; j ++)
			{
				oceanMap[i][j] = OceanStatus.UNKNOWN;
			}
		}
	}
	
	private void clearTargets()
	{
		TargetShipList.clear();
	}
	
	private void resetRemainingShips()
	{
		RemainingShips.clear();
		RemainingShips.add(Ship.ShipType_t.SHIP_CARRIER);
		RemainingShips.add(Ship.ShipType_t.SHIP_BATTLESHIP);
		RemainingShips.add(Ship.ShipType_t.SHIP_CRUISER);
		RemainingShips.add(Ship.ShipType_t.SHIP_SUBMARINE);
		RemainingShips.add(Ship.ShipType_t.SHIP_DESTROYER);
	}
	
	private int getShipLength(Ship.ShipType_t s)
	{
		int l = -1;
		switch(s)
		{
			case SHIP_CARRIER:
				l = 5;
				break;
			case SHIP_BATTLESHIP:
				l = 4;
				break;
			case SHIP_CRUISER:
				l = 3;
				break;
			case SHIP_SUBMARINE:
				l = 3;
				break;
			case SHIP_DESTROYER:
				l = 2;
				break;
			default:
				break;
		}
		return l;
	}
	
	private int getShipMinLength()
	{
		int minLength = 5;
		for(int i = 0; i < RemainingShips.size(); i ++)
		{
			if (minLength > getShipLength(RemainingShips.get(i)))
			{
				minLength = getShipLength(RemainingShips.get(i));
			}
		}
		return minLength;
	}
	
	private void updateProbability(int x, int y, GameBoard.FireResult r)
	{
		switch(r.result_type)
		{
			case SHOT_MISS:
				//set probability to 0 and decrease the probabilities of neighboring tiles
				setProbability(x, y, 0);
				updateProbabilityMiss(x, y, true);
				break;
			case SHOT_HIT:
				//set probability to 0 and do not decrease the probabilities of neighboring tiles
				setProbability(x, y, 0);
				break;
			case SHOT_SUNK:
				//change hit tiles to act as obstructions, similar to miss
				setProbability(x, y, 0);
				updateProbabilitySunk(x, y, r);
				break;
			default:
				break;
		}
	}
	
	private void updateProbabilityMiss(int x, int y, boolean check)
	{
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			int l = getShipLength(RemainingShips.get(i));
			//horizontal
			for (int n = -l + 1; n < 1; n++)
			{
				//for this configuration...
				if ((checkWithin(x + n, y)) && (checkWithin(x + n + l - 1, y)))
				{
					boolean valid = true;
					for(int k = 0; k < l; k++)
					{
						if (oceanMap[x + n + k][y] != OceanStatus.UNKNOWN)
						{
							if (check)
							{
								valid = false;
							}
						}
					}
					if (valid)
					{
						for(int k = 0; k < l; k++)
						{
							incrementProbability(x + n + k, y, RemainingShips.get(i), -1);
							print("Coordinate (" + (x + n + k) + ", " + y + ") now has Probability = " + getProbability(x + n + k, y) + ".");
							//print("Subtracting (" + (x + n + k) + ", " + y + ").");
						}
					}
				}
			}
			
			//vertical
			for (int n = -l + 1; n < 1; n++)
			{
				//for this configuration...
				if ((checkWithin(x, y + n)) && (checkWithin(x, y + n + l - 1)))
				{
					boolean valid = true;
					for(int k = 0; k < l; k++)
					{
						if (oceanMap[x][y + n + k] != OceanStatus.UNKNOWN)
						{
							if (check)
							{
								valid = false;
							}
						}
					}
					if (valid)
					{
						for(int k = 0; k < l; k++)
						{
							incrementProbability(x, y + n + k, RemainingShips.get(i), -1);
							print("Coordinate (" + x + ", " + (y + n + k) + ") now has Probability = " + getProbability(x, y + n + k) + ".");
							//print("Subtracting (" + x + ", " + (y + n + k) + ").");
						}
					}
				}
			}
		}
	}
	private void updateProbabilitySunk(int x, int y, GameBoard.FireResult r)
	{
		Ship.ShipType_t s = r.ship_type;
		List<Coordinate> hits = searchTargetList(s).hits;
		for (int i = 0; i < hits.size(); i++)
		{
			if (getOceanMap(hits.get(i)) == OceanStatus.HIT)
			{
				updateProbabilityMiss(hits.get(i).x, hits.get(i).y, true);
				oceanMap[hits.get(i).x][hits.get(i).y] = OceanStatus.SHIP;
			}
		}
	}
	
	private int getProbability(int x, int y, Ship.ShipType_t s)
	{
		return (probability.get(s)[x][y]);
	}
	private int getProbability(int x, int y)
	{
		int total = 0;
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			total += getProbability(x, y, RemainingShips.get(i));
		}
		return total;
	}
	
	private void setProbability(int x, int y, Ship.ShipType_t s, int value)
	{
		probability.get(s)[x][y] = value;
	}
	private void setProbability(int x, int y, int value)
	{
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			setProbability(x, y, RemainingShips.get(i), value);
		}
	}
	
	private void incrementProbability(int x, int y, Ship.ShipType_t s, int value)
	{
		setProbability(x, y, s, getProbability(x, y, s) + value);
	}
	private void incrementProbability(int x, int y, int value)
	{
		for (int i = 0; i < RemainingShips.size(); i++)
		{
			setProbability(x, y, RemainingShips.get(i), getProbability(x, y, RemainingShips.get(i)) + value);
		}
	}
	
	private void resetProbability()
	{
		probability.clear();
		probability.put(Ship.ShipType_t.SHIP_CARRIER, calculateInitialProbability(Ship.ShipType_t.SHIP_CARRIER));
		probability.put(Ship.ShipType_t.SHIP_BATTLESHIP, calculateInitialProbability(Ship.ShipType_t.SHIP_BATTLESHIP));
		probability.put(Ship.ShipType_t.SHIP_CRUISER, calculateInitialProbability(Ship.ShipType_t.SHIP_CRUISER));
		probability.put(Ship.ShipType_t.SHIP_SUBMARINE, calculateInitialProbability(Ship.ShipType_t.SHIP_SUBMARINE));
		probability.put(Ship.ShipType_t.SHIP_DESTROYER, calculateInitialProbability(Ship.ShipType_t.SHIP_DESTROYER));
	}
	
	private int[][] calculateInitialProbability(Ship.ShipType_t s)
	{
		int[][] initialProbability = new int[10][10];
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				initialProbability[i][j] = 0;
			}
		}
		int shipLength = getShipLength(s);
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				//horizontal
				if (checkWithin(i + shipLength - 1, j))
				{
					for (int k = 0; k < shipLength; k++)
					{
						initialProbability[i + k][j] += 1;
					}
				}
				//vertical
				if (checkWithin(i, j + shipLength - 1))
				{
					for (int k = 0; k < shipLength; k++)
					{
						initialProbability[i][j + k] += 1;
					}
				}
			}
		}
		
		//print(Arrays.deepToString(initialProbability));
		return initialProbability;
	}
	
	public class TargetShip
	{
		List<Coordinate> hits = new ArrayList<Coordinate>();
		List<Coordinate> queue = new ArrayList<Coordinate>();
		ShipOrientation shipOrientation;
		Ship.ShipType_t ship;
		
		TargetShip(Ship.ShipType_t type, int hitX, int hitY, ShipOrientation orientation)
		{
			ship = type;
			shipOrientation = orientation;
			addHit(new Coordinate(hitX, hitY));
		}
		TargetShip(Ship.ShipType_t type, int hitX, int hitY)
		{
			this(type, hitX, hitY, ShipOrientation.UNKNOWN);
		}
		TargetShip(Ship.ShipType_t type, Coordinate hit)
		{
			this(type, hit.x, hit.y, ShipOrientation.UNKNOWN);
		}
		
		public void addHit(Coordinate hit)
		{
			hits.add(hit);
			
			//add points to queue.
			addQueuePoints(hit);
			
			//check orientation
			shipOrientation = findOrientation();
			
			//remove points that do not align with orientation.
			straightenQueue(shipOrientation);
		}
		
		private ShipOrientation findOrientation()
		{
			//figure out the ship's orientation.
			ShipOrientation orientation = ShipOrientation.UNKNOWN;
			if (hits.size() < 2)
			{
				return orientation;
			}
			if ((hits.get(0).x != hits.get(1).x) && (hits.get(0).y == hits.get(1).y))
			{
				orientation = ShipOrientation.HORIZONTAL;
			}
			if ((hits.get(0).y != hits.get(1).y) && (hits.get(0).x == hits.get(1).x))
			{
				orientation = ShipOrientation.VERTICAL;
			}
			//print("Target's orientation is " + orientation.name() + ".");
			return orientation;
		}
		
		private void addQueuePoints(Coordinate hit)
		{
			//add the points surrounding the hit point to the queue.
			//TODO: Randomize this so that it doesn't do it in the same order every time.
			addQueuePoint(new Coordinate(hit.x - 1, hit.y));
			addQueuePoint(new Coordinate(hit.x, hit.y - 1));
			addQueuePoint(new Coordinate(hit.x + 1, hit.y));
			addQueuePoint(new Coordinate(hit.x, hit.y + 1));
		}
		
		private void addQueuePoint(Coordinate queuePoint)
		{
			//Points added to the queue must be within the ocean's bounds and also not be already fired at and not already be in the queue.
			if ((checkWithin(queuePoint)) && (getOceanMap(queuePoint) == OceanStatus.UNKNOWN) && (searchCoordinates(queuePoint, queue) == -1))
			{
				queue.add(queuePoint);
			}
		}
		
		private void straightenQueue(ShipOrientation orientation)
		{
			for (int i = 0; i < queue.size(); i ++)
			{
				switch(orientation)
				{
					case HORIZONTAL:
						//check if queue point does not with orientation.
						if (!(queue.get(i).y == hits.get(0).y))
						{
							//if it doesn't, remove the point.
							queue.remove(i);
							i -= 1;
						}
						break;
					case VERTICAL:
						//check if queue point does not with orientation.
						if (!(queue.get(i).x == hits.get(0).x))
						{
							//if it doesn't, remove the point.
							queue.remove(i);
							i -= 1;
						}
						break;
					case UNKNOWN:
						break;
					default:
						break;
				}
			}
		}
	}
	
	private boolean checkWithin(int x, int y)
	{
		return ((x >= 0) && (x < 10) && (y >= 0) && (y < 10));
	}
	private boolean checkWithin(Coordinate c)
	{
		return checkWithin(c.x, c.y);
	}
	
	private OceanStatus getOceanMap(Coordinate c)
	{
		return oceanMap[c.x][c.y];
	}
	
	private boolean checkShipType(Ship.ShipType_t type)
	{
		return (!(((type == Ship.ShipType_t.SHIP_NULL) || (type == Ship.ShipType_t._SHIP_TOP))));
	}
	
	private TargetShip searchTargetList(Ship.ShipType_t type)
	{
		TargetShip exists = null;
		for (int i = 0; i < TargetShipList.size(); i ++)
		{
			if (TargetShipList.get(i).ship == type)
			{
				exists = TargetShipList.get(i);
			}
		}
		return exists;
	}
	
	private int searchTargetListIndex(Ship.ShipType_t type)
	{
		for (int i = 0; i < TargetShipList.size(); i ++)
		{
			if (TargetShipList.get(i).ship == type)
			{
				return i;
			}
		}
		return -1;
	}
	
	private int searchCoordinates(Coordinate c, List<Coordinate> l)
	{
		for (int i = 0; i < l.size(); i ++)
		{
			if ((l.get(i).x == c.x) && (l.get(i).y == c.y))
			{
				return i;
			}
		}
		return -1;
	}
	
	private void removeQueuePoint(Coordinate c)
	{
		for (int i = 0; i < TargetShipList.size(); i ++)
		{
			int coordIndex = searchCoordinates(c, TargetShipList.get(i).queue);
			if (coordIndex > -1)
			{
				TargetShipList.get(i).queue.remove(coordIndex);
			}
		}
	}
	private void removeQueuePoint(int x, int y)
	{
		removeQueuePoint(new Coordinate(x, y));
	}
	
	private Ship.Orientation_t getOrientationFromInt(int o)
	{
		Ship.Orientation_t or = Ship.Orientation_t.OR_RIGHT;
		switch(o)
		{
			case 0:
				or = Ship.Orientation_t.OR_RIGHT;
				break;
			case 1:
				or = Ship.Orientation_t.OR_DOWN;
				break;
			default:
				break;
		}
		return or;
	}
	
	private void printLog(String s)
	{
		//System.out.print(s);
	}
}
/**
 * 
 */
package org.usfirst.frc.team93.training.gameplayer.battleship;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.swing.filechooser.FileSystemView;

import org.usfirst.frc.team93.training.gameplayer.battleship.GameBoard.FireResult;

/**
 * TODO: Copy this class, and write a more intelligent GamePlayer based on it
 * Call it something unique based on your name, like NickLutherGamePlayer
 * 
 * Rules of Battleship: http://www.hasbro.com/common/instruct/Battleship.PDF 
 * 
 * @author nick.luther
 *
 */
public class GrantKeaneGamePlayer2 extends GamePlayer {

	String timestamp;
	
	int MISS = 1;
	int HIT = 2;
	int boardsize = 10;
	int hitsMade = 0;
	int[] ships = {5, 4, 3, 3, 2};
	int[][] positions = new int[boardsize][boardsize];
	int[][] probabilities = new int[boardsize][boardsize];
	boolean hitsSkewProbabilities = true;
	int skewFactor = 2;
	Vector<Integer> hits = new Vector<Integer>();

	
	List<String> log = new ArrayList<String>();
	List<String> prob = new ArrayList<String>();
	
	/**
	 * @param own_ocean
	 */
	public GrantKeaneGamePlayer2() 
	{
		super();
		timestamp = getCurrentTimeStamp();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Notification that a new match is about to start. 
	 */
	@Override
	public void notifyReset()
	{
		timestamp = getCurrentTimeStamp();
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
		
		placeShip(new Ship(new Ship.GridCoord(0,0), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CARRIER));
		placeShip(new Ship(new Ship.GridCoord(0,2), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_BATTLESHIP));
		placeShip(new Ship(new Ship.GridCoord(0,4), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_CRUISER));
		placeShip(new Ship(new Ship.GridCoord(0,6), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_SUBMARINE));
		placeShip(new Ship(new Ship.GridCoord(0,9), Ship.Orientation_t.OR_RIGHT, Ship.ShipType_t.SHIP_DESTROYER));
		
		for(int i = 0; i < boardsize; i++)
		{
			for(int j = 0; j < boardsize; j++)
			{
				positions[i][j] = 0;
				probabilities[i][j] = 0;
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
		fireAtBestPosition();
		printProbability();
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
		
	}
	
    public void fireAtBestPosition() 
    {
        recalculateProbabilities();
        int pos = getBestUnplayedPosition();
        int x = ValueToCoordinates(pos)[0];
        int y = ValueToCoordinates(pos)[1];

        FireResult result = fireShot(new Ship.GridCoord(x, y));
        
        log.add(x + "," + y + "," + ((result.result_type == FireResult.FireResultType_t.SHOT_MISS) ? 0 : 1));
        if (FireResult.FireResultType_t.SHOT_HIT == result.result_type)
		{
        	hitsMade++;
        	positions[y][x] = HIT;
			//System.out.println("We hit something!");
            hitsSkewProbabilities = true;
		}
        else if(FireResult.FireResultType_t.SHOT_SUNK == result.result_type)
        {
        	hitsMade++;
        	if(result.ship_type == Ship.ShipType_t.SHIP_CARRIER)
        	{
        		hitsMade -= 5;
        		ships[0] = 11;
            	//System.out.println("We sunk the Carrier!");
        	}
        	if(result.ship_type == Ship.ShipType_t.SHIP_BATTLESHIP)
        	{
        		hitsMade -= 4;
        		ships[1] = 11;
            	//System.out.println("We sunk the Battleship!");
        	}
        	if(result.ship_type == Ship.ShipType_t.SHIP_CRUISER)
        	{
        		hitsMade -= 3;
        		ships[2] = 11;
            	//System.out.println("We sunk the Cruiser!");
        	}
        	if(result.ship_type == Ship.ShipType_t.SHIP_SUBMARINE)
        	{
        		hitsMade -= 3;
        		ships[3] = 11;
            	//System.out.println("We sunk the Submarine!");
        	}
        	if(result.ship_type == Ship.ShipType_t.SHIP_DESTROYER)
        	{
        		hitsMade -= 2;
        		ships[4] = 11;
            	//System.out.println("We sunk the Destroyer!");
        	}
        	
        	positions[y][x] = HIT;
        	if(hitsMade == 0)
        	{
                hitsSkewProbabilities = false;	
        	}
        }
        else
        {
        	positions[y][x] = MISS;
        }
    }
    
	public void recalculateProbabilities() 
	{
		for(int i = 0; i < boardsize; i++)
		{
			for(int j = 0; j < boardsize; j++)
			{
				probabilities[i][j] = 0;
			}
		}
		
        for (int i = 0; i < ships.length; i++) 
        {
            for (int y = 0; y < boardsize; y++) 
            {
                for (int x = 0; x < boardsize; x++) 
                {
                    if (shipCanOccupyPosition(MISS, x, y, ships[i], false)) 
                    {
                        increaseProbability(x, y, ships[i], false);
                    }
                    if (shipCanOccupyPosition(MISS, x, y, ships[i], true)) 
                    {
                        increaseProbability(x, y, ships[i], true);
                    }
                }
            }
        }
        
        if (hitsSkewProbabilities) 
        {
            skewProbabilityAroundHits(hits);
        }
    }

    public void increaseProbability(int x,int y,int shipSize,boolean vertical) 
    {
    	int z = (vertical ? y : x);
        int end = z + shipSize - 1;

        for (int i = z; i <= end; i++) 
        {
            if (vertical) probabilities[i][x]++;
            else probabilities[y][i]++;
        }
    }

    public void skewProbabilityAroundHits(Vector<Integer> toSkew) 
    {
    	Vector<Integer> uniques = new Vector<Integer>();
    	boolean add;
    	
        for (int i = 0; i < toSkew.size(); i++) 
        {
        	add = true;
        	
        	for (int j = 0; j < uniques.size(); j++)
        	{
        		if(toSkew.get(i) == uniques.get(j))
        		{
        			add = false;
        		}
        	}
        	
        	if(add)
        	{
        		uniques.add(toSkew.get(i));
        	}
        }

        for(int i = 0; i < uniques.size(); i++)
        {
            int x = ValueToCoordinates(uniques.get(i))[0];
            int y = ValueToCoordinates(uniques.get(i))[1];
            probabilities[y][x] *= skewFactor;
        }

    }

    public Vector<Integer> getAdjacentPositions(int value) 
    {
    	Vector<Integer> adj = new Vector<Integer>();
    	int x = ValueToCoordinates(value)[0];
    	int y = ValueToCoordinates(value)[1];
    	
        if (y + 1 < boardsize) adj.add(y + 1, x);
        if (y - 1 >= 0) adj.add(y - 1, x);
        if (x + 1 < boardsize) adj.add(y, x + 1);
        if (x - 1 >= 0) adj.add(y, x - 1);

        return adj;
    }

    public boolean shipCanOccupyPosition(int criteriaForRejection, int X, int Y, int shipSize, boolean vertical) 
    {
        int x = X;
        int y = Y;
        int z = (vertical ? y : x);
        int end = z + shipSize - 1;

        if (end >= boardsize) 
        {
        	return false;
        }

        for (int i = z; i <= end; i++) 
        {
        	int thisPos = (vertical ? positions[i][x] : positions[y][i]);
            if (thisPos == criteriaForRejection) return false;
        }

        return true;
    }

    public int getBestUnplayedPosition() 
    {
        int bestProb = 0;
        int BestPos = 0;
        for (int y = 0; y < boardsize; y++) 
        {
            for (int x = 0; x < boardsize; x++) 
            {
                if (probabilities[y][x] > bestProb) 
                {
                	if(positions[y][x] != HIT && positions[y][x] != MISS)
                	{
                        BestPos = (boardsize * y) + x;
                        bestProb = probabilities[y][x];
                	}
                }
            }
        }
        return BestPos;
    }
	
    public int CoordToNumber(int x, int y)
    {
    	int value = (boardsize * y) + x;
		return value;
    }
    
    public int[] ValueToCoordinates(int total)
    {
    	 int x = (total % boardsize);
         int y = (int) Math.floor(total / boardsize);
    	 int[] XY = new int[2];
    	 XY[0] = x;
    	 XY[1] = y;
    	 return XY;
    }
    
//    public int[] ArrayToCoordinates(int x, int y)
//    {
//    	
//    }
    
	public class Opponent
	{
		public int id;
		public int[][] OpponentShots = new int[10][10];
		public int[][] MyShots = new int[10][10];
		public boolean IsRandom;
		public String Name;
		
		public Opponent()
		{
			ResetValues();
		}
		
		public Opponent(String Name, int id)
		{
			ResetValues();
			this.Name = Name;
			this.id = id;
		}	
		
		public void ResetValues()
		{
			for(int i = 0; i < 10; i++)
			{
				for(int j = 0; j < 10; j++)
				{
					OpponentShots[j][i] = 0;
					MyShots[j][i] = 0;
				}
			}
			IsRandom = false;
		}
	}
	/**
	 * Prints a JSON compatible string of the probability array.
	 */
	private void printProbability()
	{
		int[][] print = new int[10][10];
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				print[i][j] = probabilities[j][i];
			}
		}
		prob.add(Arrays.deepToString(print));
	}
	
	/**
	 * Print
	 */
	public void printInfo()
	{
		for (int i = 0; i < log.size(); i++)
		{
			//System.out.println(log.get(i));
		}
		for (int i = 0; i < prob.size(); i++)
		{
			//System.out.println(prob.get(i));
		}
		
		//log
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		Path file = Paths.get(home.getAbsolutePath() + "/BattleshipLogs/" + timestamp + "/" + getName() + "/log.txt");      //create a file with a set of specified attributes
		file.toFile().getParentFile().mkdirs();
		file.toFile().setWritable(true, false);
		try
		{
			Files.createFile(file);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			Files.write(file, log, Charset.forName("UTF-8"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//probability
		file = Paths.get(home.getAbsolutePath() + "/BattleshipLogs/" + timestamp + "/" + getName() + "/prob.txt");
		file.toFile().getParentFile().mkdirs();
		file.toFile().setWritable(true, false);
		try
		{
			Files.createFile(file);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			Files.write(file, prob, Charset.forName("UTF-8"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getCurrentTimeStamp()
	{
	    return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS").format(new Date());
	}
}
package game;

import java.util.Random;

import com.base.engine.core.math.Vector3f;


/*
 * Holds a collection of Room objects
 * 
 */
public class Map 
{
	private final int MAX_MONKEY_ROOMS = 4;
	private final int MAX_TRAP_ROOMS = 4;
	
	private int width, height; 
	private Room[][] rooms;
	private Random rng;
	
	public Map(int width, int height)
	{
		if(width % 2 == 0 || height % 2 == 0 || width < 3 || height < 3)
		{
			//We need to be able to find a middle of the map.
			throw new IllegalArgumentException("Map: width and height must be odd and >= 3."); 
		}
		this.width = width;
		this.height = height;
		rooms = new Room[width][height];
		rng = new Random();
		
		randomize();
	}
	
	/*
	 * TODO: Complete method
	 * Randomly sorts the Map
	 */
	public void randomize()
	{
		setEssentialRooms();
		
		for(int i = 0; i < width; ++i)
		{
			for(int j = 0; j < height; ++j)
			{
				//TODO: Set Generic Rooms here
			}
		}
	}
	
	private void setEssentialRooms()
	{
		//TODO: Set vector3f location correctly
		rooms[width / 2][height / 2] = new MainRoom(new Vector3f(0,0,0),((width*height) + width), height / 2, width / 2); //MainRoom is always in the middle!
		
		for(int i = 0; i < MAX_MONKEY_ROOMS; ++i) 
		{
			int randomWidth = rng.nextInt(width), randomHeight = rng.nextInt(height);
			while(!isValidMonkeyPlacement(randomWidth, randomHeight))
			{
				randomWidth = rng.nextInt(width);
				randomHeight = rng.nextInt(height);
			}
			
			//TODO: Set Vector3f location correctly
			rooms[randomWidth][randomHeight] = new MonkeyRoom(new Vector3f(0,0,0),12);			
		}
		
		for(int i = 0; i < MAX_TRAP_ROOMS; ++i)
		{
			int randomWidth = rng.nextInt(width), randomHeight = rng.nextInt(height);
			while(!isValidTrapRoom(randomWidth, randomHeight))
			{
				randomWidth = rng.nextInt(width);
				randomHeight = rng.nextInt(height);
			}
			
			//TODO: Set Vector3f location correctly
			rooms[randomWidth][randomHeight] = new TrapRoom(new Vector3f(0,0,0),12);
		}
	}
	
	private boolean isValidTrapRoom(int x, int y)
	{
		if(x > width || y > height) { return false; }
		
		return rooms[x][y] == null;
	}
	
	private boolean isValidMonkeyPlacement(int x, int y)
	{
		if(x > width || y > height) { return false; }
		int midX = width / 2;
		int midY = height / 2;
		
		return (rooms[x][y] == null) || (x == midX && y == midY + 1) ||
				(x == midX && y == midY - 1) || (x == midX - 1 || y == midY) ||
				(x == midX + 1 && y == midY);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}

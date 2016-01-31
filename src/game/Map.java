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
		rooms[width / 2][height / 2] = new MainRoom(new Vector3f(0,0,0),width/2,height/2); //MainRoom is always in the middle!
		
		for(int i = 0; i < MAX_MONKEY_ROOMS; ++i) 
		{
			int randomWidth = rng.nextInt(width), randomHeight = rng.nextInt(height);
			
			while(!isValidMonkeyPlacement(randomWidth, randomHeight))
			{
				randomWidth = rng.nextInt(width);
				randomHeight = rng.nextInt(height);
			}
			
			//TODO: Set Vector3f location correctly
			rooms[randomWidth][randomHeight] = new MonkeyRoom(new Vector3f(0,0,0),randomWidth,randomHeight);
			rotHandle(rooms[randomWidth][randomHeight]);
		}
		
		for(int i = 0; i < MAX_TRAP_ROOMS; ++i)
		{
			int randomWidth = rng.nextInt(width), randomHeight = rng.nextInt(height);
			while(!isValidTrapPlacement(randomWidth, randomHeight))
			{
				randomWidth = rng.nextInt(width);
				randomHeight = rng.nextInt(height);
			}
			
			//TODO: Set Vector3f location correctly
			rooms[randomWidth][randomHeight] = new TrapRoom(new Vector3f(0,0,0),randomWidth,randomHeight);
			rotHandle(rooms[randomWidth][randomHeight]);
		}
	}
	
<<<<<<< HEAD
	private boolean isValidTrapPlacement(int x, int y)
=======
	public void setGenericRooms()
	{
		
	}
	
	//debug
	public void showMap()
	{
		for(int i = 0; i < width; i++)
		{
			for(int j= 0; j < height; j++)
			{
				System.out.print(rooms[i][j] + "   ");
			}
			
			System.out.print("\n");
		}
	}
	
	private boolean isValidTrapRoom(int x, int y)
>>>>>>> 18896c02cf6610a597f15100a690e35788b7dff6
	{
		if(x > width || y > height) { return false; }
		
		return rooms[x][y] != null;
	}
	
	private boolean isValidMonkeyPlacement(int x, int y)
	{
		if(x > width || y > height) { return false; }
		int midX = width / 2;
		int midY = height / 2;
		
		return (rooms[x][y] != null) || (x == midX && y == midY + 1) ||
				(x == midX && y == midY - 1) || (x == midX - 1 || y == midY) ||
				(x == midX + 1 && y == midY);
	}
	
	public Room rotHandle(Room room)
	{
		try
		{	
			if(room.conSum() == 1)
			{
				if(room.conPeek(0) == 1)
				{
					rooms[room.getxPos()-1][room.getyPos()].conPeek(2);
				}
				else if(room.conPeek(1) == 1)
				{
					rooms[room.getxPos()][room.getyPos()-1].conPeek(3);
				}
				else if(room.conPeek(2) == 1)
				{
					rooms[room.getxPos()+1][room.getyPos()].conPeek(0);
				}
				else if(room.conPeek(3) == 1)
				{
					rooms[room.getxPos()][room.getyPos()+1].conPeek(1);
				}
			}
			else if(room.conSum() == 2)
			{
				if(room.conPeek(0) == 1 && room.conPeek(1) == 1)
				{
					rooms[room.getxPos()][room.getyPos()].conPeek(1);
				}
				if(room.conPeek(1) == 1 && room.conPeek(2) == 1)
				{
					rooms[room.getxPos()][room.getyPos()].conPeek(2);
				}
				if(room.conPeek(2) == 1 && room.conPeek(3) == 1)
				{
					rooms[room.getxPos()][room.getyPos()].conPeek(3);
				}
				if(room.conPeek(3) == 1 && room.conPeek(0) == 1)
				{
					rooms[room.getxPos()][room.getyPos()].conPeek(0);
				}
			}
		}
		catch(Exception e) 
		{ 
			room.roomRotate(); 
			
			return rotHandle(room);
		}
		
		return room;
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

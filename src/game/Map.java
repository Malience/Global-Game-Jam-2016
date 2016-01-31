package game;

import java.util.Random;

import com.base.engine.core.World;
import com.base.engine.core.math.Vector3f;

/*
 *              x  o  B     x  o  B
               o                 oo
              B                 x x
                                   
            x                 B   B
           o                 o    o
          B  o  x     B  o  x     x
          o                 o    o
          x                 B   B
                Monkey          
          B                 x x
          o                 oo
          x  o  B     x  o  B
 */

/**
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
	
	/**
	 * TODO: Complete method
	 * Randomly sorts the Map and connections
	 */
	public void randomize()
	{
		setEssentialRooms();
		
		for(int i = 0; i < width; ++i)
		{
			for(int j = 0; j < height; ++j)
			{
				//looks at every generated room in game-space
				//if connections are available to be made
				//links them together
				try
				{
					if(rooms[i][j] == null)
					{
						rooms[i][j] = new GenericRoom("genericroom" + i + j);			
					}
					
					if(i > 0)
					{
						if(rooms[i][j].conPeek(1) == 1 && rooms[i-1][j].conPeek(3) == 1)
						{
							rooms[i][j].setConnection(1, 2);
							rooms[i-1][j].setConnection(3, 2);
							rooms[i][j].setDoor(rooms[i-1][j].door[3],1);
						}
					}
					
					if(rooms[i][j].conPeek(0) == 1 && rooms[i][j-1].conPeek(2) == 1)
					{
						rooms[i][j].setConnection(0, 2);
						rooms[i][j-1].setConnection(2, 2);
						rooms[i][j].setDoor(rooms[i][j-1].door[2],0);
					}
		
					if(rooms[i][j].conPeek(2) == 1 && rooms[i][j+1].conPeek(0) == 1)
					{
						rooms[i][j].setConnection(2, 2);
						rooms[i][j+1].setConnection(0, 2);
						rooms[i][j].setDoor(rooms[i][j+1].door[0],2);
					}
						
					if(rooms[i][j].conPeek(3) == 1 && rooms[i+1][j].conPeek(1) == 1)
					{
						rooms[i][j].setConnection(3, 2);
						rooms[i+1][j].setConnection(1, 2);
						rooms[i][j].setDoor(rooms[i+1][j].door[1],3);
					}
				}
				catch(NullPointerException e) {}
				catch(ArrayIndexOutOfBoundsException e) {}
				
				rotHandle(rooms[i][j]);
				
				World.world.add(rooms[i][j]);
				rooms[i][j].setPosition(new Vector3f(i * Room.roomSize.x, Room.roomSize.y, j * Room.roomSize.z));		
				
				if(rooms[i][j] instanceof MonkeyRoom)
				{
					MonkeyRoom monkeyRoom = (MonkeyRoom)rooms[i][j];
					rooms[i][j].moveTo(new Vector3f(i * Room.roomSize.x, Room.roomSize.y, j * Room.roomSize.z));
				}
			}
		}
	}
	
	
	/**
	 * Sets game essential rooms in the game-space array.
	 */
	private void setEssentialRooms()
	{
		//TODO: Set vector3f location correctly
		rooms[width / 2][height / 2] = new MainRoom("main"); 	//MainRoom is always in the middle!
		rooms[width / 2][height / 2].setTexture("Wall001.png");
		
		for(int i = 0; i < MAX_MONKEY_ROOMS; ++i) 
		{
			int randomWidth = rng.nextInt(width), randomHeight = rng.nextInt(height);
			
			while(!isValidMonkeyPlacement(randomWidth, randomHeight))
			{
				randomWidth = rng.nextInt(width);
				randomHeight = rng.nextInt(height);
			}
			
			//TODO: Set Vector3f location correctly

			rooms[randomWidth][randomHeight] = new MonkeyRoom("monkeyroom" + i);
			rooms[randomWidth][randomHeight].setTexture("bricks.jpg");
		}
		
		for(int i = 0; i < MAX_TRAP_ROOMS; ++i)
		{
			int randomWidth = rng.nextInt(width), randomHeight = rng.nextInt(height);
			
			while(!isValidTrapPlacement(randomWidth, randomHeight))
			{
				randomWidth = rng.nextInt(width);
				randomHeight = rng.nextInt(height);
			}
			
			rooms[randomWidth][randomHeight] = new TrapRoom("traproom" + i);
			rooms[randomWidth][randomHeight].setTexture("bricks2.jpg");
		}
	}
	
	//debug
	public void showMap()
	{
		for(int i = 0; i < width; i++)
		{
			for(int j= 0; j < height; j++)
			{
				System.out.print(rooms[i][j] + "\t"); //Print them for debugging.
			}
			System.out.print("\n");
		}
	}
	
	private boolean isValidTrapPlacement(int x, int y)
	{
		if(x > width || y > height) { return false; }
		
		return rooms[x][y] == null;
	}
	
	private boolean isValidMonkeyPlacement(int x, int y)
	{
		if(x > width || y > height) { return false; }
		
		int midX = width / 2;
		int midY = height / 2;
		
		return (rooms[x][y] == null) && 
			   (!((x == midX && y == (midY + 1)) ||
			   (x == midX && y == (midY - 1)) || 
			   (x == (midX - 1) && y == midY) ||
			   (x == (midX + 1) && y == midY)));
	}
	
	/**
	 * Handles room rotation for edges/connections.
	 * 
	 * @param room
	 * @return
	 */
	private Room rotHandle(Room room)
	{
		try
		{	
			if(room.conSum() == 1)
			{
				if(room.conPeek(0) == 1)
				{
					rooms[room.getxPos()][room.getyPos()-1].conPeek(0);
				}
				else if(room.conPeek(1) == 1)
				{
					rooms[room.getxPos()-1][room.getyPos()].conPeek(0);
				}
				else if(room.conPeek(2) == 1)
				{
					rooms[room.getxPos()][room.getyPos()+1].conPeek(0);
				}
				else if(room.conPeek(3) == 1)
				{
					rooms[room.getxPos()+1][room.getyPos()].conPeek(0);
				}
			}
			
			if(room.conSum() == 2)
			{
				if(room.conPeek(0) == 1 && room.conPeek(1) == 1)
				{
					rooms[room.getxPos()][room.getyPos()-1].conPeek(0);
				}
				if(room.conPeek(1) == 1 && room.conPeek(2) == 1)
				{
					rooms[room.getxPos()+1][room.getyPos()].conPeek(0);
				}
				if(room.conPeek(2) == 1 && room.conPeek(3) == 1)
				{
					rooms[room.getxPos()][room.getyPos()+1].conPeek(0);
				}
				if(room.conPeek(3) == 1 && room.conPeek(0) == 1)
				{
					rooms[room.getxPos()-1][room.getyPos()].conPeek(0);
				}
			}
		}
		catch(NullPointerException e) { }
		catch(ArrayIndexOutOfBoundsException e)
		{
			room.roomRotate();
			
			rotHandle(room);
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

package game;


/*
 * Holds a collection of Room objects
 * 
 */
public class Map 
{
	private int width, height; 
	private Room[][] rooms; 
	
	public Map(int width, int height)
	{
		this.width = width;
		this.height = height;
		rooms = new Room[width][height];
	}
	
	/*
	 * TODO: Complete method
	 * Randomly sorts the Map
	 */
	public void randomize()
	{
		
	}
	
	public int width()
	{
		return width;
	}
	
	public int height()
	{
		return height;
	}
}

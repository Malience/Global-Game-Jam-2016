package game;

import com.base.engine.core.math.Vector3f;


public class Room 
{
	public static Vector3f roomSize = new Vector3f(10,10,10); //TODO: check room size
	
	private Vector3f position;
	
	public Room(Vector3f position)
	{
		this.position = position;		
	}
	
	
}

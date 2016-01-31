package game;

import com.base.engine.core.math.Vector3f;

public class MainRoom extends Room 
{
	public MainRoom(String name)
	{
		super(name);
	}
	
	public MainRoom(Vector3f position,int xPos, int yPos) 
	{
		super(position,xPos,yPos);
		
		roomType = "O";
	}
}

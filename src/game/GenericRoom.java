package game;

import com.base.engine.core.math.Vector3f;

public class GenericRoom extends Room 
{
	private enum GenericRoomTypes
	{
		TWOWAY, THREEWAY, FOURWAY, BLANK, STEPPINGSTONES
	}
	
	private final int ENUMSIZE = GenericRoomTypes.values().length;
	
	public GenericRoom(Vector3f position, int xPos, int yPos) 
	{
		super(position,xPos,yPos);
		
		handleConnectors();
	}
	
	public GenericRoom(String name) 
	{
		super(name);
		
		handleConnectors();
	}

	public String toString()
	{
		return "G" + super.toString();
	}
}

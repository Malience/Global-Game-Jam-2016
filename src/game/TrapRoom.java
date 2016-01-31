package game;

import com.base.engine.core.math.Vector3f;

public class TrapRoom extends Room 
{
	private static int roomIterator = 0;
	
	private enum TrapRoomTypes
	{
		PRESSUREPLATES, PARANOIA, FAKEPOWER, MAZE
	}
	
	private final int ENUMSIZE = TrapRoomTypes.values().length;
	
	public TrapRoom(Vector3f position,int xPos, int yPos) 
	{
		super(position,xPos,yPos);
		
		roomType = "t";
		
		handleConnectors();
		
		roomIterator += 1;
	}
	
	@Override
	public void handleConnectors()
	{
		if(roomIterator == 0)
		{
			setConnection(1,0,0,0);
		}
		else if(roomIterator == 1)
		{
			setConnection(1,1,1,1);
		}
		else if(roomIterator == 2)
		{
			setConnection(1,1,1,1);
		}			
		else if(roomIterator == 3)
		{
			setConnection(1,0,0,0);
		}
		else if(roomIterator == 4)
		{
			roomIterator = 0;
		}
	}
	
	public String toString()
	{
		return "T" + super.toString();
	}
}

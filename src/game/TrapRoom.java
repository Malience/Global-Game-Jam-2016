package game;

import com.base.engine.core.math.Vector3f;

public class TrapRoom extends Room 
{
	private static int roomIterator = -1;
	
	private enum TrapRoomTypes
	{
		PRESSUREPLATES, PARANOIA, FAKEPOWER, MAZE
	}
	
	private final int ENUMSIZE = TrapRoomTypes.values().length;
	
	public TrapRoom(Vector3f position, int xPos, int yPos) 
	{
		super(position, xPos, yPos);
		
		roomIterator += 1;
	}
	
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
			roomIterator = -1;
		}
	}

}

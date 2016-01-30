package game;

import com.base.engine.core.math.Vector3f;

public class TrapRoom extends Room 
{
	private enum TrapRoomTypes
	{
		PRESSUREPLATES, PARANOIA, FAKEPOWER, MAZE
	}
	
	private final int ENUMSIZE = TrapRoomTypes.values().length;
	
	public TrapRoom(Vector3f position) 
	{
		super(position);
		// TODO Auto-generated constructor stub
	}

}

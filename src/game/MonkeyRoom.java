package game;

import com.base.engine.core.math.Vector3f;

public class MonkeyRoom extends Room 
{
	private enum MonkeyRoomTypes
	{
		PLACEBO, TIME, GREED, CHAMELEON
	}
	
	private final int ENUMSIZE = MonkeyRoomTypes.values().length;
	
	public MonkeyRoom(Vector3f position) 
	{
		super(position);
		// TODO Auto-generated constructor stub
	}

}

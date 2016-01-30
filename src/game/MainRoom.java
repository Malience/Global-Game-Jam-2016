package game;

import com.base.engine.core.math.Vector3f;

public class MainRoom extends Room 
{

	public MainRoom(Vector3f position) 
	{
		super(position);
		
		setConnection(1,1,1,1);
	}

}

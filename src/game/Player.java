package game;

import com.base.engine.components.GameComponent;
import com.base.engine.core.GameObject;

public class Player extends GameObject
{
	boolean alive = true;
	GameComponent gc;
	
	public Player()
	{
		
	}
	
	public boolean checkPulse()
	{
		return alive;
	}
	
	public int update(float delta)
	{
		
		return 1;
	}
}

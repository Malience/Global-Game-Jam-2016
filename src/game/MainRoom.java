package game;

import com.base.engine.core.math.Vector3f;

public class MainRoom extends Room 
{
	Stand stand;
	
	public MainRoom(String name)
	{
		super(name);
		
		stand = new Stand(0,0,0);
		this.addChild(stand);
		
		//stand.getTransform().setPos(this.getPosition());
	}
	
	public MainRoom(Vector3f position,int xPos, int yPos) 
	{
		super(position,xPos,yPos);
		
		roomType = "O";
	}
	
	public void setStuff()
	{
		stand.getTransform().setPos(this.getPosition());
	}
}

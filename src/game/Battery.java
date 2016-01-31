package game;

import com.base.engine.components.attachments.Interactable;

public class Battery extends Gear implements Interactable
{	
	PickUpItem cmd =  new PickUpItem();
	
	public Battery(float x, float y, float z)
	{
		super(x, y, z);
		
		
	}
	
	public String toString()
	{
		return "Battery";
	}

	public void interact() 
	{	
		cmd.pickUp(this);
	}
}

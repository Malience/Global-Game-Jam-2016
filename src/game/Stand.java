package game;

import com.base.engine.components.attachments.Interactable;

public class Stand extends Gear //implements Interactable
{
	PickUpItem cmd =  new PickUpItem();
	
	public Stand (float x, float y, float z)
	{
		super (x,y,z);
	}
	
	public String toString()
	{
		return "Stand";
	}
	
	public void interact()
	{
		cmd.pickUp(this);
	}
}

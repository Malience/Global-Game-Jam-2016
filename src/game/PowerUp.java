package game;

import com.base.engine.components.attachments.Interactable;

public class PowerUp extends Gear implements Interactable
{
	PickUpItem cmd =  new PickUpItem();
	
	public PowerUp (float x, float y, float z)
	{
		super(x, y, z);
	}
	
	public String toString()
	{
		return "Power-Up";
	}
	
	public void interact()
	{
		cmd.pickUp(this);
	}
}

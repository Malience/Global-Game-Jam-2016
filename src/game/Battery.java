package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class Battery extends Gear //implements Interactable
{	
	PickUpItem cmd =  new PickUpItem();
	BatteryComponent bc = new BatteryComponent();
	
	public Battery(float x, float y, float z)
	{
		super(x, y, z);
		
		addComponent(bc);
	}
	
	public String toString()
	{
		return "Battery";
	}

	
	private class BatteryComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			cmd.pickUp(this.getParent());
		}
		
	}
}

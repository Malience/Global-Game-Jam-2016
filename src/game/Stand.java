package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class Stand extends Gear //implements Interactable
{
	PickUpItem cmd =  new PickUpItem();
	StandComponent sc = new StandComponent();
	
	public Stand (float x, float y, float z)
	{
		super (x,y,z);
		
		addComponent(sc);
	}
	
	public String toString()
	{
		return "Stand";
	}
	
	private class StandComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			// places monkey head
			cmd.pickUp(this.getParent());
		}
		
	}
}

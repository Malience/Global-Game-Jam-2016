package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class ArrowTrap extends Gear
{
	ArrowTrapComponent atc = new ArrowTrapComponent();
	
	public ArrowTrap (float x, float y, float z)
	{
		super (x,y,z);
		
		addComponent(atc);
	}
	
	public String toString()
	{
		return "Arrow Trap";
	}
	
	private class ArrowTrapComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			
		}
		
	}
}

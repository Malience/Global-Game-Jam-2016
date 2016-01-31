package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class SpikeTrap extends Gear
{
	SpikeTrapComponent stc = new SpikeTrapComponent();
	
	public SpikeTrap (float x, float y, float z)
	{
		super (x,y,z);
		
		addComponent(stc);
	}
	
	public String toString()
	{
		return "Spike Trap";
	}
	
	private class SpikeTrapComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			
		}
		
	}
}

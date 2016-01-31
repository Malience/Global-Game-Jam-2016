package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class Tripwire extends Gear
{
	TripwireComponent tc = new TripwireComponent();
	
	public Tripwire (float x, float y, float z)
	{
		super (x,y,z);
		
		addComponent(tc);
	}
	
	private class TripwireComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			
		}
		
	}
}

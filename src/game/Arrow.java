package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class Arrow extends Gear 
{
	ArrowComponent ac = new ArrowComponent();
	
	public Arrow (float x, float y, float z)
	{
		super (x,y,z);
		
		addComponent(ac);
	}
	
	public String toString()
	{
		return "Arrow";
	}
	
	private class ArrowComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			
		}
		
	}
}

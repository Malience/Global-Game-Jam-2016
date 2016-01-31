package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class PressurePlate extends Gear
{
	PressurePlateComponent ppc = new PressurePlateComponent();
	
	public PressurePlate (float x, float y, float z)
	{
		super (x,y,z);
		
		addComponent(ppc);
	}
	
	public String toString()
	{
		return "Pressure Plate";
	}
	
	private class PressurePlateComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			
		}
		
	}
}

package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class PowerUp extends Gear //implements Interactable
{
	PickUpItem cmd =  new PickUpItem();
	PowerUpComponent puc = new PowerUpComponent();
	
	public PowerUp (float x, float y, float z)
	{
		super(x, y, z);
		
		addComponent(puc);
	}
	
	public String toString()
	{
		return "Power-Up";
	}

	private class PowerUpComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			cmd.pickUp(this.getParent());
		}
		
	}
}

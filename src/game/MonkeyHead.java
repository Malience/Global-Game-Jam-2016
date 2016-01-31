package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class MonkeyHead extends Gear //implements Interactable
{
	String identify = "";
	
	PickUpItem cmd =  new PickUpItem();
	MonkeyHeadComponent mhc = new MonkeyHeadComponent();
	
	public MonkeyHead (String type, float x, float y, float z)
	{
		super( x, y, z);
		
		identify = type;
		addComponent(mhc);
		
		if (type.equals("placebo"))
		{
			identify = "Placebo";
		}
		else if (type.equals("time"))
		{
			identify = "Time";
		}
		else if (type.equals("chameleon"))
		{
			identify = "Chameleon";
		}
		else if (type.equals("greed"))
		{
			identify = "Greed";
		}
		
	}
	
	public String toString()
	{
		return "Artifact : " + identify;
	}

	
	private class MonkeyHeadComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			cmd.pickUp(this.getParent());
		}
		
	}
}

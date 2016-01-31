package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.attachments.Interactable;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class MonkeyHead extends Gear //implements Interactable
{
	String identify = "";
	
	PickUpItem cmd =  new PickUpItem();
	MonkeyHeadComponent mhc = new MonkeyHeadComponent();
	
	public MonkeyHead (String type, float x, float y, float z)
	{
		super( x, y, z);
		
		Material mat = new Material();
		mat.addTexture("diffuse", new Texture("red.png"));
		MeshRenderer renderer = new MeshRenderer(new Mesh("monkey3.obj"),mat);
		
		identify = type;
		addComponent(mhc);
		this.addComponent(renderer);
		
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

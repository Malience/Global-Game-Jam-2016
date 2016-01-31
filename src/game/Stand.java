package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.attachments.Interactable;
import com.base.engine.core.GameObject;
import com.base.engine.core.World;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class Stand extends Gear //implements Interactable
{
	PickUpItem cmd =  new PickUpItem();
	StandComponent sc = new StandComponent();
	Vector3f headplace = new Vector3f(0,1,0);
	
	public Stand (float x, float y, float z)
	{
		super (x,y,z);
		Material m = new Material();
		m.addTexture("diffuse", new Texture("stand001.png"));
		
		MeshRenderer renderer = new MeshRenderer(new Mesh("stand001.obj"), m);
		
		addComponent(sc);
		this.addComponent(renderer);
		
		this.getTransform().setScale(.05f);
	}
	
	public String toString()
	{
		return "Stand";
	}
	
	private class StandComponent extends GameComponent implements Interactable
	{
		public void interact() 
		{	
			GameObject[] objects = Inventory.backpack;
			MonkeyHead head = null;
			for(int i = 0; i < objects.length; i++)
			{
				if(objects[i] instanceof MonkeyHead)
					head = (MonkeyHead)objects[0];
			}
			if(head != null)
			{
				this.parent.addChild(head);
				head.getTransform().setPos(headplace);
				Player p = (Player) World.world.focus;
				p.win();
			}
		}
		
	}
}

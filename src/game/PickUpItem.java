package game;

import com.base.engine.components.GameComponent;
import com.base.engine.core.GameObject;
import com.base.engine.core.Input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;

public class PickUpItem extends GameComponent 
{
	private Inventory inv = new Inventory();
	private int grabKey = GLFW_KEY_E;
	private boolean grabable = true;
	
	public PickUpItem()
	{
		
	}
	
	public void collided(GameObject go)
	{
		//if player is close enough to object
			//show e to pick up
			//grabable = true;
			//if(object is grabable)
			//{
			//	pickUp(go);
			//}
	}
	
	public void pickUp(GameObject go)
	{
		if (Input.getKey(grabKey))
		{
			inv.addItem(go);
			//remove item from world || view
		}
	}
}

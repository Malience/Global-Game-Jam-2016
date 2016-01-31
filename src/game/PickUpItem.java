package game;

import com.base.engine.components.GameComponent;
import com.base.engine.core.GameObject;
import com.base.engine.core.Input;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.UI.UIText;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;

public class PickUpItem extends GameComponent 
{
	private Inventory inv = new Inventory();
	Stand s;
	private int grabKey = GLFW_KEY_E;
	Player player = new Player();
	
	UIText message = new UIText(300,300,"timesNewRoman.png",  " ", 24);
	
	public PickUpItem()
	{
		RenderingEngine.UI.add(message);
	}
	
	public void pickUp(GameObject go)
	{
		if (go != s)
		{
			message.text = "Pick-Up (e)";
			message.generate();
			
			if (Input.getKey(grabKey))
			{
				inv.addItem(go);
				go.moveTo(new Vector3f(-10,-10,-10));
				message.text = "";
				message.generate();
			}
		}
		else if (go == s)
		{
			Vector3f mH = new Vector3f(0,0,0);
			
			message.text = "Place Head (e)";
			message.generate();
			
			if (Input.getKey(grabKey))
			{
				inv.removeItem(go);
				message.text = "";
				message.generate();
				
				mH = s.getPosition();
				mH.setY(mH.getY() + 1);
			}
		}
	}
	
	public void openDoor(Door go)
	{
		message.text = "Open (e)";
		message.generate();
		
		if (Input.getKey(grabKey))
		{	
			message.text = "";
			message.generate();
			go.getNewLoc();
		}
	}
}

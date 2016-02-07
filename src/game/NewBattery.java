package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Controlable;
import com.base.engine.core.Input;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.UI.UIText;

public class NewBattery extends GameComponent implements Controlable
{
	Inventory inv = new Inventory();
	AccessItems aI = new AccessItems();
	
	private int reloadKey = GLFW_KEY_R;
	private int acceptKey = GLFW_KEY_Y;
	private int rejectKey = GLFW_KEY_N;

	UIText ask = new UIText(200,400,"timesNewRoman.png",  " ", 40);
	
	int timer = 0;
	Battery bty;
	
	public NewBattery()
	{
		RenderingEngine.UI.add(ask);
	}
	
	public int input(float delta)
	{
		timer++;
		
		
		if (timer >= 10)
		{
			if(Input.getKey(reloadKey))
			{	
				ask.text = "Change Battery (y/n)? You have " + inv.findItem(bty);
				ask.generate();
			}
			
			if(Input.getKey(acceptKey))
			{
				ask.text = " ";
				ask.generate();
				if (inv.findItem(bty) >= 1)
				{
					inv.removeItem(bty);
				}
			}
			else if (Input.getKey(rejectKey))
			{
				ask.text = " ";
				ask.generate();
			}
			
			timer = 0;
		}
		return 1;
	}
}

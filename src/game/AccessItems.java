package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Controlable;
import com.base.engine.core.Input;

import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.UI.UIText;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;


public class AccessItems extends GameComponent implements Controlable
{
	private boolean opened = false;
	private int invKey = GLFW_KEY_I;
	int timer = 0;

	UIText items = new UIText(300,500,"timesNewRoman.png",  " ", 40);
	
	Inventory stuff = new Inventory();
	
	public AccessItems()
	{
		RenderingEngine.UI.add(items);
	}
	
	public int input(float delta)
	{	
		timer++;
		
		if (timer >= 10)
		{
			if(Input.getKey(invKey))
			{
				if (opened == true)
				{
					//System.out.println("CLOSE INV");
					opened = false;
					closeInv();
				}
				else
				{
					opened = true;
					openInv();
				}
			}
			timer = 0;
		}
		
		return 1;
	}
	
	public void openInv()
	{
		items.text = stuff.getItems();
		items.generate();
		//System.out.println("OPEN INV");
	}
	
	public void closeInv()
	{	
		items.text = " ";
		items.generate();		
	}
	
	public boolean getState()
	{
		return opened;
	}
}

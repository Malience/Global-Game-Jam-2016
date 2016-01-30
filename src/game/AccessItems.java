package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Controlable;
import com.base.engine.core.Input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;

import com.base.engine.components.attachments.Controlable;
import com.base.engine.core.Input;
import com.base.engine.core.math.Vector3f;


public class AccessItems extends GameComponent implements Controlable
{
	private boolean opened = false;
	
	public void keyPressed()
	{
		System.out.println("In Keypress");
		
		if(Input.getKey(GLFW_KEY_I))
		{
			if (opened == true)
			{
				opened = false;
				closeInv();
			}
			else
			{
				opened = true;
				openInv();
			}
		}
	}
	
	public void openInv()
	{
		System.err.println("INVENTORY OPENED");
	}
	
	public void closeInv()
	{
		System.err.println("INVENTORY CLOSED");
	}
}

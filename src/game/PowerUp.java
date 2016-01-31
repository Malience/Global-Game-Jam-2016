package game;

import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class PowerUp extends Gear
{
	
	public PowerUp (float x, float y, float z)
	{
		super(x, y, z);
	}
	
	public void PowerUp1()
	{
		Material m = new Material();
		m.addTexture("Color", new Texture("blue.png"));
		
		Mesh n = new Mesh("PowerUp001.obj");
	}
	
	public String toString()
	{
		return "Power-Up";
	}
}

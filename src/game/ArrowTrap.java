package game;

import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class ArrowTrap extends Gear
{
	public ArrowTrap (float x, float y, float z)
	{
		super (x,y,z);
		
		Material m = new Material();
		m.addTexture("Testing", new Texture("brown.png"));
		
		Mesh n = new Mesh("");

	}
	
	public String toString()
	{
		return "Arrow Trap";
	}
}

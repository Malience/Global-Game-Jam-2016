package game;

import com.base.engine.components.MeshRenderer;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class Arrow extends Gear 
{
	public Arrow (float x, float y, float z)
	{
		super (x,y,z);
		
		Material m = new Material();
		m.addTexture("Arrow", new Texture("Arrow001.png"));
		
		Mesh n = new Mesh("Arrow001.obj");
		MeshRenderer mR = new MeshRenderer(n, m);
	}
	
	public String toString()
	{
		return "Arrow";
	}
}

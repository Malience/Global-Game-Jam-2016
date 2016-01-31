package game;

import com.base.engine.components.MeshRenderer;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class Stand extends Gear
{
	public Stand (float x, float y, float z)
	{
		super (x,y,z);
		
		Material m = new Material();
		m.addTexture("Stand", new Texture("Stand001.png"));
		
		Mesh n = new Mesh("Stand001.obj");
		MeshRenderer mR = new MeshRenderer(n, m);

	}
	
	public String toString()
	{
		return "Stand";
	}
}

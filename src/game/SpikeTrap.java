package game;

import com.base.engine.components.MeshRenderer;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class SpikeTrap extends Gear
{
	public SpikeTrap (float x, float y, float z)
	{
		super (x,y,z);
		
		Material m = new Material();
		m.addTexture("Spike", new Texture("Spike001.png"));
		
		Mesh n = new Mesh("Spike001.obj");
		MeshRenderer mR = new MeshRenderer(n, m);

	}
	
	public String toString()
	{
		return "Spike Trap";
	}
}

package game;

import com.base.engine.components.MeshRenderer;
import com.base.engine.core.World;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class Battery extends Gear
{

	private World wrd = new World();
	
	public Battery(float x, float y, float z)
	{
		super(x, y, z);
		
		Material m = new Material();
		m.addTexture("Battery", new Texture("Battery001.png"));
		
		Mesh n = new Mesh("Battery001.obj");
		
		
		MeshRenderer mR = new MeshRenderer (n, m);
	}
	
	public String toString()
	{
		return "Battery";
	}
}

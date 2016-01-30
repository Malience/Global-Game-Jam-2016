package game;

import com.base.engine.components.MeshRenderer;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class PressurePlate extends Gear
{
	public PressurePlate (float x, float y, float z)
	{
		super (x,y,z);
		
		Material m = new Material();
		m.addTexture("Pressure Plate", new Texture("PressurePlate001.png"));
		
		Mesh n = new Mesh("PressurePlate001.obj");
		MeshRenderer mR = new MeshRenderer(n, m);
	}
	
	public String toString()
	{
		return "Pressure Plate";
	}
}

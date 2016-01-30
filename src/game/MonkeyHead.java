package game;

import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class MonkeyHead extends Gear
{
	public MonkeyHead (float x, float y, float z)
	{
		super( x, y, z);
		
		Material m = new Material();
		m.addTexture("MonkeyHead", new Texture("brown.png"));
		
		Mesh n = new Mesh("monkey3.obj");
		
	}
	
	public String toString()
	{
		return "Artifact";
	}
}

package game;

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
		m.addTexture("Testing", new Texture("brown.png"));
		
		Mesh n = new Mesh("");
		
		wrd.add(this);
	}
}

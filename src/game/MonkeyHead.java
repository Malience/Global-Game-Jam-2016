package game;

import com.base.engine.components.MeshRenderer;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class MonkeyHead extends Gear
{
	Material m = new Material();
	Mesh n = new Mesh("monkey3.obj");
	MeshRenderer mR;
	
	public MonkeyHead (String type, float x, float y, float z)
	{
		super( x, y, z);
		
		if (type.equals("placebo"))
		{
			placebo();
		}
		else if (type.equals("time"))
		{
			time();
		}
		else if (type.equals("chameleon"))
		{
			chameleon();
		}
		else if (type.equals("greed"))
		{
			greed();
		}
		
	}
	
	public void placebo()
	{
		m.addTexture("placebo", new Texture("blue.png"));
		mR = new MeshRenderer (n, m);
	}
	
	public void time()
	{
		m.addTexture("time", new Texture("red.png"));
		mR = new MeshRenderer (n, m);
	}
	
	public void chameleon()
	{
		m.addTexture("chameleon", new Texture("green.png"));
		mR = new MeshRenderer (n, m);
	}
	
	public void greed()
	{
		m.addTexture("greed", new Texture("black.png"));
		mR = new MeshRenderer (n, m);
	}
	
	public String toString()
	{
		return "Artifact";
	}
}

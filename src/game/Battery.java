package game;

/*
 *  Material m = new Material();
	m.addTexture("Battery", new Texture("Battery001.png"));
		
	Mesh n = new Mesh("Battery001.obj");

	MeshRenderer mR = new MeshRenderer (n, m);
 * 
 */

public class Battery extends Gear
{	
	public Battery(float x, float y, float z)
	{
		super(x, y, z);
		
		
	}
	
	public String toString()
	{
		return "Battery";
	}
}

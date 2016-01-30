package game;

import com.base.engine.core.math.Vector3f;
import com.base.engine.core.GameObject;
import com.base.engine.rendering.Vertex;


public class Room extends GameObject
{
	public static Vector3f roomSize = new Vector3f(10,10,10); //TODO: check room size
	
	private Vertex[] vertices;
	private int[] indices;
	
	public Room(Vector3f position)
	{
		indices = new int[48];
		vertices = new Vertex[8];		
		getTransform().setPos(position);
		
		recalculate();
	}
	
	
	public void recalculate()
	{
		float x = getTransform().getPos().getX(), y = getTransform().getPos().getY(), z = getTransform().getPos().getZ();
		float halfX = roomSize.getX(), halfY = roomSize.getY(), halfZ = roomSize.getZ();
		
		
		vertices[0] = new Vertex(new Vector3f(x + halfX, y + halfY, z + halfZ));
		vertices[1] = new Vertex(new Vector3f(x + halfX, y + halfY, z - halfZ));
		vertices[2] = new Vertex(new Vector3f(x + halfX, y - halfY, z + halfZ));
		vertices[3] = new Vertex(new Vector3f(x + halfX, y - halfY, z - halfZ));
		vertices[4] = new Vertex(new Vector3f(x - halfX, y + halfY, z + halfZ));
		vertices[5] = new Vertex(new Vector3f(x - halfX, y + halfY, z - halfZ));
		vertices[6] = new Vertex(new Vector3f(x - halfX, y - halfY, z + halfZ));
		vertices[7] = new Vertex(new Vector3f(x - halfX, y - halfY, z - halfZ));
		
		
		for(int i = 0; i < 48; i += 2)
		{
			int start = i * 3;
			indices[start] = i;
			indices[start + 1] = i + 2;
			indices[start + 2] = i + 1;
			indices[start + 3] = i + 2;
			indices[start + 4] = i + 3;
			indices[start + 5] = i + 1;
		}
		
	}
	
	
}

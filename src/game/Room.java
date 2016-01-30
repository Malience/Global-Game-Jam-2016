package game;

import com.base.engine.core.math.Vector3f;
import com.base.engine.core.GameObject;
import com.base.engine.rendering.Vertex;


public class Room extends GameObject
{
	public static Vector3f roomSize = new Vector3f(10,10,10); //TODO: check room size
	
	private Vertex[] vertices;
	private int[] indices;
	private int[] connectors;
	
	public Room(Vector3f position, int index)
	{
		indices = new int[48];
		vertices = new Vertex[8];		
		connectors = new int[4];
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
	
	public void setConnection(int left, int top, int right, int down)
	{
		connectors[0] = left;
		connectors[1] = top;
		connectors[2] = right;
		connectors[3] = down;
	}
	
	public void roomRotate()
	{
		int hold0 = connectors[0] , hold1 = connectors[1], hold2 = connectors[2];
		
		connectors[0] = connectors[3];
		connectors[1] = hold0;
		connectors[2] = hold1;
		connectors[3] = hold2;
		
		getTransform().rotate(new Vector3f(0,1,0), 90);
	}
	
	public void edgeHandle()
	{
		int connectorSum = 0;
		
		for(int i=0;i<4;i++)
			connectorSum+=connectors[i];
		
		if(connectorSum == 1)
		{
			try
			{
				if(connectors[0] == 1)
				{
					
				}
				else if(connectors[1] == 1)
				{
					
				}
				else if(connectors[2] == 1)
				{
					
				}
				else if(connectors[3] == 1)
				{
					
				}
			} 
			catch(Exception e ) 
			{
				System.out.print("Dont do that.");
			}
		}
		
		if(connectorSum == 2)
		{
			
		}
	}
	
	public void getMapPos()
	{
		
	}
}

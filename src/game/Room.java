package game;

import com.base.engine.components.MeshRenderer;
import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.core.GameObject;
import com.base.engine.core.World;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Vertex;


public class Room extends GameObject
{
	public static Vector3f roomSize = new Vector3f(10,10,10); //TODO: check room size
	
	private MeshRenderer meshRenderer;
	private Mesh mesh;
	private Vertex[] vertices;
	private int[] indices;
	private int[] connectors;
	private int xPos;
	private int yPos;
	private Door door;
	
	protected String roomType;
	
	protected String textureName;
	
	protected boolean hasBattery, hasPowerup;
	
	public Room(String name)
	{
		super(name);
		indices = new int[48];
		vertices = new Vertex[8];		
		connectors = new int[4];
		roomType = "g";
		setConnection(1,1,1,1);
		setTexture("EmptyRoom001Template.png");
	}

	public Room(Vector3f position)
	{		
		this(position, 0, 0);
	}
	
	public Room(Vector3f position, int xPos, int yPos)
	{
		indices = new int[48];
		vertices = new Vertex[8];		
		connectors = new int[4];
		roomType = "g";
		getTransform().setPos(position);
		setTexture("Wall001.png");
		setConnection(1,1,1,1);
//		recalculate();
//		
//		World.world.add(this);
	}
	
	protected void setTexture(String textureName)
	{
		this.textureName = textureName;
	}
	
	public void setPosition(Vector3f position)
	{
		getTransform().setPos(position);
		this.addComponent(calculate());
		//World.world.add(this);
	}
	
	public void recalculate()
	{
		float x = getTransform().getPos().getX(), y = getTransform().getPos().getY(), z = getTransform().getPos().getZ();
		float halfX = roomSize.getX(), halfY = roomSize.getY(), halfZ = roomSize.getZ();
		
		
		vertices[0] = new Vertex(new Vector3f(halfX, halfY, halfZ), new Vector2f(0,0));
		vertices[1] = new Vertex(new Vector3f(halfX, halfY, -halfZ), new Vector2f(0,1));
		vertices[2] = new Vertex(new Vector3f(halfX, -halfY, halfZ), new Vector2f(1,0));
		vertices[3] = new Vertex(new Vector3f(halfX, -halfY, -halfZ), new Vector2f(1,1));
		vertices[4] = new Vertex(new Vector3f(-halfX, -halfY, halfZ), new Vector2f(0,0));
		vertices[5] = new Vertex(new Vector3f(-halfX, -halfY, -halfZ), new Vector2f(0,1));
		vertices[6] = new Vertex(new Vector3f(-halfX, -halfY, halfZ), new Vector2f(1,0));
		vertices[7] = new Vertex(new Vector3f(-halfX, -halfY, -halfZ), new Vector2f(1,1));
		//
		/*
		for(int i = 0; i < 8; i += 2)
		{	
			
			
			int start = i * 3;
			indices[start] = i;
			indices[start + 1] = i + 2;
			indices[start + 2] = i + 1;
			indices[start + 3] = i + 2;
			indices[start + 4] = i + 3;
			indices[start + 5] = i + 1;
					
		}
		*/
		
		
		int bi[] = { 0, 1, 2,
				2, 1, 3,
				
				//Bottom
				1, 3, 5,
				5, 1, 7,
				
				//Top
				0, 2, 4,
				4, 2, 6,
				
				2, 3, 4,
				4, 3, 5,
				
				4, 5, 6,
				6, 5, 7,
				
				6, 7, 0,
				0, 7, 1
		};
		mesh = new Mesh(vertices, bi);
		
		Material mat = new Material();
		mat.addTexture("diffuse", new Texture("red.png"));
		
		if(meshRenderer == null)
		{
			meshRenderer = new MeshRenderer(mesh, mat);
			this.addComponent(meshRenderer);
		}
		else
		{
			meshRenderer.set(mesh, mat);
		}
		// MWAHAHAHAHAHAHA!
		// I can write what ever i want to write
		
	}
	
	public MeshRenderer calculate()
	{
		float x = getTransform().getPos().getX(), y = getTransform().getPos().getY(), z = getTransform().getPos().getZ();
		float halfX = roomSize.getX(), halfY = roomSize.getY(), halfZ = roomSize.getZ();
		
		
		vertices[0] = new Vertex(new Vector3f(x + halfX, y + halfY, z + halfZ), new Vector2f(1.0f, 0.0f)); //+++
		vertices[1] = new Vertex(new Vector3f(x + halfX, y + halfY, z - halfZ), new Vector2f(1.0f, 1.0f)); //++-
		vertices[2] = new Vertex(new Vector3f(x + halfX, y - halfY, z + halfZ), new Vector2f(0.75f, 0.25f)); //+-+
		vertices[3] = new Vertex(new Vector3f(x + halfX, y - halfY, z - halfZ), new Vector2f(0.75f, 0.75f)); //+--
		vertices[4] = new Vertex(new Vector3f(x - halfX, y + halfY, z + halfZ), new Vector2f(0.0f, 0.0f)); //-++
		vertices[5] = new Vertex(new Vector3f(x - halfX, y + halfY, z - halfZ), new Vector2f(0.0f, 1.0f)); //-+-
		vertices[6] = new Vertex(new Vector3f(x - halfX, y - halfY, z + halfZ), new Vector2f(0.25f, 0.25f)); //--+
		vertices[7] = new Vertex(new Vector3f(x - halfX, y - halfY, z - halfZ), new Vector2f(0.25f, 0.75f)); //---		
		
		//DO NOT CHANGE THESE UNLESS YOU KNOW WHAT YOU'RE DOING
		int bi[] = {
				7, 5, 4,
				4, 6, 7,
				
				3, 2, 0,
				0, 1, 3,
				
				4, 0, 2,
				2, 6, 4,
				
				7, 3, 1,
				1, 5, 7,
				
				5, 1, 0,
				0, 4, 5,
				
				6, 2, 3,
				3, 7, 6,
		};
		mesh = new Mesh(vertices, bi);
		
		Material mat = new Material();

		mat.addTexture("diffuse", new Texture(textureName));
		
		MeshRenderer renderer = new MeshRenderer(mesh, mat);
		return renderer;
	}
	
	public void setConnection(int left, int top, int right, int down)
	{
		connectors[0] = left;
		connectors[1] = top;
		connectors[2] = right;
		connectors[3] = down;
	}
	
	public void setConnection(int pos, int x)
	{
		connectors[pos] = x;
	}
	
	/**
	 * Rotates room 90 degrees clockwise
	 */
	public void roomRotate()
	{ 
		setConnection(connectors[3],connectors[0],connectors[1],connectors[2]);
	}
	
	public int conPeek(int index)
	{
		return index >= 0 && index <= 3 ? connectors[index] : -1;
	}
	
	public int conSum()
	{
		return connectors[0] + connectors[1] + connectors[2] + connectors[3];
	}
	
	
	public int getxPos()
	{
		return xPos;
	}
	
	public int getyPos()
	{
		return yPos;
	}
	
	public String toString()
	{
		return connectors[0] + "" + connectors[1] + "" + connectors[2] + "" + connectors[3];
	}
	
	public String getRoomType()
	{
		return roomType;
	}
}

package game;

import com.base.engine.components.MeshRenderer;
import com.base.engine.core.GameObject;
import com.base.engine.core.World;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class MonkeyRoom extends Room 
{
	private static int roomIterator = 0;
	
	private enum MonkeyRoomTypes
	{
		PLACEBO, TIME, GREED, CHAMELEON
	}
	
	private final int ENUMSIZE = MonkeyRoomTypes.values().length;
	private  GameObject monkey;
	
	public MonkeyRoom(Vector3f position, int xPos, int yPos) 
	{	
		super(position,xPos,yPos);
		
		roomType = "m";
		
		handleConnectors();
		
		roomIterator += 1;
		
		Material mat = new Material();
		//mat.addTexture("diffuse", new Texture("red.png"));
		
		monkey = new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), mat));
		
		//this.addChild(monkey);
	}
	
	public MonkeyRoom(String name)
	{
		super(name);
		
		Material mat = new Material();
		mat.addTexture("diffuse", new Texture("red.png"));
		
		monkey = new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), mat));
		
		//this.addChild(monkey);
		
		handleConnectors();
		
		roomIterator += 1;
	}
	
	public void setMonkey()
	{
		//Material mat = new Material();
		//mat.addTexture("diffuse", new Texture("red.png"));
		
		//monkey = new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), mat));
		
		monkey.getTransform().setPos(getTransform().getPos());
		World.world.add(monkey);
		
		//this.addChild(monkey);
	}
	
	public void setObjectPositions(Vector3f position)
	{		
		this.moveTo(position);
	}
	
	public void handleConnectors()
	{
		if(roomIterator == 0)
		{
			setConnection(1,0,1,0);
		}
		else if(roomIterator == 1)
		{
			setConnection(1,1,1,1);
		}
		else if(roomIterator == 2)
		{
			setConnection(1,0,1,0);
		}			
		else if(roomIterator == 3)
		{
			setConnection(1,1,1,1);
		}
		else if(roomIterator == 4)
		{
			roomIterator = 0;
		}
	}
	
	public String toString()
	{
		return "M" + super.toString();
	}
}

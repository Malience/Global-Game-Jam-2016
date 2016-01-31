package game;

import com.base.engine.components.attachments.Interactable;
import com.base.engine.core.GameObject;
import com.base.engine.core.math.Vector3f;

public class Door extends GameObject implements Interactable
{
	private Door door = this;
	private Door anotherDoor;
	private int conType;
	
	private PickUpItem get = new PickUpItem();
	
	public Door(int conType)
	{
		this.conType = conType;
	}
	
	public Door(Door anotherDoor)
	{
		this.anotherDoor = anotherDoor;
		conType = 2;
		anotherDoor.setConType(2);
	}
	
	public void interact()
	{
		get.openDoor(door);
	}
	
	public void getNewLoc()
	{
		Player player = new Player();
		Vector3f newLoc= new Vector3f(anotherDoor.getPosition());
		
		player.getTransform().setRot(anotherDoor.getTransform().getRot());
		newLoc.setX(0.5f);
		newLoc.setY(0.5f);
		
		player.move(newLoc);
	}
	
	public void setConType(int a)
	{
		conType = a;
	}
	
	public String peekDoor()
	{
		return anotherDoor.toString();
	}
	
	public String toString()
	{
		return conType + "";
	}
}

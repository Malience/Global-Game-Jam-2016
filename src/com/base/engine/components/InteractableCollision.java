package com.base.engine.components;

import com.base.engine.components.attachments.Interactable;
import com.base.engine.physics.collision.Collider;
import com.base.engine.physics.collision.Sphere;

public class InteractableCollision extends GameComponent implements Interactable
{
	Sphere sphere;
	
	public InteractableCollision(Sphere sphere)
	{
		this.sphere = sphere;
	}
	
	@Override
	public void interact() 
	{
		System.out.println("Works!");
		
	}
	
	@Override
	public Collider getCollider()
	{
		return sphere;
	}

}

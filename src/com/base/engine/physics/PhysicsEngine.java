package com.base.engine.physics;

import java.util.ArrayList;

import com.base.engine.components.attachments.Collidable;
import com.base.engine.components.attachments.Physical;
import com.base.engine.core.World;

public class PhysicsEngine 
{
	World world;
	ArrayList<Physical> physicalComponents;
	ArrayList<Collidable> collidableComponents;
	
	public PhysicsEngine()
	{
		world = World.world;
	}
	
	public void gather()
	{
		physicalComponents = world.getPhysical();
		collidableComponents = world.getCollidable();
	}
	
	public void integrate(float delta)
	{
		for(Physical object : physicalComponents)
		{
			object.integrate(delta);
		}
	}
	
	public void simulate(float delta)
	{
		for(Physical object : physicalComponents)
		{
			object.simulate(delta);
		}
	}
	
	public void detectCollisions(float delta)
	{
		//Low-cost collision detection
		
		//In-depth collision detection
		
	}
	
	public void handleCollisions(float delta)
	{
		
	}
}

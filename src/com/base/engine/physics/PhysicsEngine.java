package com.base.engine.physics;

import java.util.ArrayList;

import com.base.engine.components.attachments.Collidable;
import com.base.engine.components.attachments.Physical;
import com.base.engine.core.World;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;
import com.base.engine.physics.RigidBody.ForceGenerators.Gravity;
import com.base.engine.physics.collision.CollisionData;
import com.base.engine.physics.collision.CollisionDetector;
import com.base.engine.physics.collision.Contact;
import com.base.engine.physics.collision.ContactResolver;

public class PhysicsEngine 
{
	World world;
	ArrayList<Physical> physicalComponents;
	ArrayList<Collidable> collidableComponents;
	ArrayList<RigidBody> bodies;
	CollisionDetector detector;
	ContactResolver resolver;
	CollisionData data;
	Contact[] contacts;
	int maxContacts;
	int iterations;
	boolean calculateIterations;
	Gravity gravity;
	
	public PhysicsEngine()
	{
		world = World.world;
		detector = new CollisionDetector();
		maxContacts = 20;
		iterations = 3;
		data = new CollisionData(maxContacts);
		contacts = new Contact[maxContacts];
		resolver = new ContactResolver(iterations);
		calculateIterations = (iterations == 0);
		bodies = new ArrayList<RigidBody>();
		gravity = new Gravity(new Vector3f(0,-20,0));
	}
	
	public void startFrame()
	{
		for(RigidBody body : bodies)
		{
			body.clearAccumulators();
			body.calculateDerivedData();
			gravity.updateForce(body, 0);
		}
	}
	
	public void gather()
	{
		physicalComponents = world.getPhysical();
		collidableComponents = world.getCollidable();
		bodies.clear();
		for(Physical physic : physicalComponents)
		{
			bodies.add(physic.getBody());
		}
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
	
	public void generateContacts(float delta)
	{
		for(int i = 0; i < collidableComponents.size(); i++)
		{
			for(int j = i + 1; j < collidableComponents.size(); j++)
			{
				detector.checkCollision(collidableComponents.get(i).getPrimitive(),collidableComponents.get(j).getPrimitive(), data);
			}
		}
	}
	
	public void handleCollisions(float delta)
	{
		if(calculateIterations)
		{
			resolver.setIterations(0);
		}
		contacts = data.getContacts();
		resolver.resolveContacts(contacts, 0, delta);
	}
	
	public void runPhysics(float delta)
	{
		
	}
}

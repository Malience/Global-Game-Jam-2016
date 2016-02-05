package com.base.engine.physics.PremadeObjects;

import com.base.engine.core.Shapes;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.collision.Box;

public class BoxObject extends PremadeObjects
{
	Box box;
	
	public BoxObject(){this(1,1,1);}
	public BoxObject(float mass, float ldamping, float adamping){this(mass, ldamping, adamping, new Vector3f(1,1,1));}
	public BoxObject(float mass, float ldamping, float adamping, Vector3f halfSize)
	{
		super(Shapes.box(halfSize, true), mass, ldamping, adamping);
		
		box = new Box(halfSize);		
		box.attach(this.getRigidBody());
		this.addComponent(box);
		box.calculateInternals();
	}
}

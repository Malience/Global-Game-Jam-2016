package com.base.engine.physics.PremadeObjects;

import com.base.engine.core.GameObject;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.collision.Plane;

public class PlaneObject extends GameObject
{
	private Plane plane;
	
	public PlaneObject(){this(new Vector3f(0,1,0),0);}
	public PlaneObject(Vector3f direction, float offset)
	{
		plane = new Plane(direction.normal(), offset);
		this.addComponent(plane);
	}
}

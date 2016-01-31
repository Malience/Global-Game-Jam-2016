package com.base.engine.physics.collision;

import com.base.engine.components.attachments.Collidable;
import com.base.engine.core.math.Vector3f;

public class Plane extends Primitive implements Collidable
{
	public Vector3f direction;
	public float offset;
	
	@Override
	public void calculateInternals()
	{
		
	}
}

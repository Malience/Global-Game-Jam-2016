package com.base.engine.physics.collision;

import com.base.engine.components.attachments.Collidable;

public class Sphere extends Primitive implements Collidable
{
	float radius;
	
	public Sphere(float radius)
	{
		this.radius = radius;
	}
}

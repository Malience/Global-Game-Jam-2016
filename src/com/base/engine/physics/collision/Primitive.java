package com.base.engine.physics.collision;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Collidable;
import com.base.engine.core.math.Matrix4f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class Primitive extends GameComponent implements Collidable
{
	RigidBody body;
	Matrix4f offset;
	public Matrix4f transform;
	
	public void calculateInternals()
	{
		transform = body.getTransform().getTransformation().mul(offset);
	}
	
	
	@Override
	public Primitive getPrimitive()
	{
		return this;
	}
	
	public void attach(RigidBody body)
	{
		this.body = body;
	}
	
	public Vector3f getAxis(int index)
	{
		return transform.getAxisVector(index);
	}
	
}

package com.base.engine.physics.collision;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class Contact 
{
	Vector3f contactPoint;
	Vector3f contactNormal;
	float penetration;
	RigidBody body[];
}

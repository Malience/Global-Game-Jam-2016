package com.base.engine.physics.collision;

import com.base.engine.core.math.Vector3f;

public class CollisionDetector 
{
	int sphereAndSphere(Sphere one, Sphere two, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		Vector3f pos1 = one.getAxis(3);
		Vector3f pos2 = two.getAxis(3);
		
		Vector3f midline = pos1.sub(pos2);
		float size = midline.magnitude();
		
		if(size <= 1.0f | size >= one.radius + two.radius)
		{
			return 0;
		}
		
		Vector3f normal = midline.mul(1.0f/size);
		
		Contact contact = data.nextContact();
		contact.contactNormal = normal;
		contact.contactPoint = pos1.add(midline.mul(.5f));
		contact.penetration = one.radius + two.radius - size;
		
		contact.body[0] = one.body;
		contact.body[1] = two.body;
//		contact.restitution = data.restitution;
//		contact.friction = data.friction;
		
		return 1;
	}
	
	//USE THIS ONE WHEN ONE-SIDED COLLISION IS NEEDED
	int sphereAndHalfSpace(Sphere sphere, Plane plane, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		Vector3f pos = sphere.getAxis(3);
		
		float ballDistance = plane.direction.mul(pos).magnitude() - sphere.radius - plane.offset;
		
		if(ballDistance >= 0) return 0;
		
		Contact contact = data.nextContact();
		contact.contactNormal = plane.direction;
		contact.penetration = -ballDistance;
		contact.contactPoint = pos.sub(plane.direction.mul(ballDistance + sphere.radius));
		
		contact.body[0] = sphere.body;
		contact.body[1] = null;
//		contact.restitution = data.restitution;
//		contact.friction = data.friction;
		
		return 1;
	}
	
	//USE THIS ONE WHEN TWO-SIDED COLLISION IS NEEDED
	int sphereAndTruePlane(Sphere sphere, Plane plane, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		Vector3f pos = sphere.getAxis(3);
		
		float centerDistance = plane.direction.mul(pos).magnitude() - plane.offset;
		
		if(centerDistance*centerDistance > sphere.radius*sphere.radius) return 0;
		
		Vector3f normal = plane.direction;
		float penetration = -centerDistance;
		if(centerDistance < 0)
		{
			normal = normal.mul(-1);
			penetration = -penetration;
		}
		
		penetration += sphere.radius;
		
		Contact contact = data.nextContact();
		contact.contactNormal = normal;
		contact.penetration = penetration;
		contact.contactPoint = pos.sub(plane.direction.mul(centerDistance));
		
		contact.body[0] = sphere.body;
		contact.body[1] = null;
//		contact.restitution = data.restitution;
//		contact.friction = data.friction;
		
		return 1;
	}
	
	int boxAndHalfSpace(Box box, Plane plane, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		float halfX = box.halfSize.getX();
		float halfY = box.halfSize.getY();
		float halfZ = box.halfSize.getZ();
		Vector3f vertices[] =
			{
			new Vector3f(-halfX, -halfY, -halfZ),
			new Vector3f(-halfX, -halfY, +halfZ),
			new Vector3f(-halfX, +halfY, -halfZ),
			new Vector3f(-halfX, +halfY, +halfZ),
			new Vector3f(+halfX, -halfY, -halfZ),
			new Vector3f(+halfX, -halfY, +halfZ),
			new Vector3f(+halfX, +halfY, -halfZ),
			new Vector3f(+halfX, +halfY, +halfZ)
			};
			for (int i = 0; i < 8; i++)
			{
				float vertexDistance = vertices[i].mul(plane.direction).magnitude();
				
				if(vertexDistance <= plane.offset + data.tolerance)
				{
					
					Contact contact = data.nextContact();
					contact.contactNormal = plane.direction;
					contact.penetration = plane.offset - vertexDistance;
					contact.contactPoint = plane.direction.mul(vertexDistance-plane.offset);
					
					contact.body[0] = box.body;
					contact.body[1] = null;
//					contact.restitution = data.restitution;
//					contact.friction = data.friction;
				}
			}
		
		
		return 1;
	}
	
//	int boxAndSphere(Box box, Sphere sphere, CollisionData data)
//	{
//		Vector3f center = sphere.getAxis(3);
//		Vector3f relCenter = box.transform.trasformInverse(center);
//	}
}

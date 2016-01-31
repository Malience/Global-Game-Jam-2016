package com.base.engine.physics.collision;

import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;

public class CollisionDetector 
{
	public int checkCollision(Primitive one, Primitive two, CollisionData data)
	{
		Shape s1 = parseShape(one);
		Shape s2 = parseShape(two);
		
		if(s1 == Shape.Sphere && s2 == Shape.Sphere)
		{
			return sphereAndSphere((Sphere)one, (Sphere)two, data);
		}
		if(s1 == Shape.Sphere && s2 == Shape.Plane)
		{
			return sphereAndHalfSpace((Sphere)one, (Plane)two, data);
		}
		if(s1 == Shape.Plane && s2 == Shape.Sphere)
		{
			return sphereAndHalfSpace((Sphere)two, (Plane)one, data);
		}
//		if(s1 == Shape.Box && s2 == Shape.Box)
//		{
//			return boxAndBox((Box)one, (Box)two, data);
//		}
			
		if(s1 == Shape.Box && s2 == Shape.Plane)
		{
			return boxAndHalfSpace((Box)one, (Plane)two, data);
		}
		if(s1 == Shape.Plane && s2 == Shape.Box)
		{
			return boxAndHalfSpace((Box)two, (Plane)one, data);
		}
		
		if(s1 == Shape.AABB && s2 == Shape.Plane)
		{
			return AABBAndHalfSpace((AABB)one, (Plane)two, data);
		}
		if(s1 == Shape.Plane && s2 == Shape.AABB)
		{
			return AABBAndHalfSpace((AABB)two, (Plane)one, data);
		}
		return 0;
	}
	
	private Shape parseShape(Primitive p)
	{
		String name = p.getClass().getSimpleName();
		switch(name)
		{
		case "Sphere":
			return Shape.Sphere;
		case "Plane":
			return Shape.Plane;
		case "Box":
			return Shape.Box;
		case "AABB":
			return Shape.AABB;
		}
		return null;
	}
	
	private enum Shape
	{
		Sphere,
		Plane,
		Box,
		AABB;
	}
	
	int sphereAndSphere(Sphere one, Sphere two, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		Vector3f pos1 = one.getAxis(3);
		Vector3f pos2 = two.getAxis(3);
		
		Vector3f midline = pos1.sub(pos2);
		float size = midline.magnitude();
		
		if(size <= 0.0f | size >= one.radius + two.radius)
		{
			return 0;
		}
		
		Vector3f normal = midline.mul(1.0f/size);
		
		Contact contact = data.nextContact();
		contact.contactNormal = normal;
		contact.contactPoint = pos1.add(midline.mul(.5f));
		contact.penetration = one.radius + two.radius - size;
		
		contact.setBodyData(one.body, two.body, data.friction, data.restitution);
		
		return 1;
	}
	
	//USE THIS ONE WHEN ONE-SIDED COLLISION IS NEEDED
	int sphereAndHalfSpace(Sphere sphere, Plane plane, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		Vector3f pos = sphere.getAxis(3);
		
		float ballDistance = plane.direction.dot(pos) - sphere.radius - plane.offset;
		
		if(ballDistance >= 0) return 0;
		
		Contact contact = data.nextContact();
		contact.contactNormal = plane.direction;
		contact.penetration = -ballDistance;
		contact.contactPoint = pos.sub(plane.direction.mul(ballDistance + sphere.radius));
		
		contact.setBodyData(sphere.body, null, data.friction, data.restitution);
		
		return 1;
	}
	
	//USE THIS ONE WHEN TWO-SIDED COLLISION IS NEEDED
	int sphereAndTruePlane(Sphere sphere, Plane plane, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		Vector3f pos = sphere.getAxis(3);
		
		float centerDistance = plane.direction.dot(pos) - plane.offset;
		
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
		
		contact.setBodyData(sphere.body, null, data.friction, data.restitution);
		
		return 1;
	}
	
	
//	int boxAndSphere(Box box, Sphere sphere, CollisionData data)
//	{
//		Vector3f center = sphere.getAxis(3);
//		Vector3f relCenter = box.transform.trasformInverse(center);
//	}
	
	public void fillPointFaceBoxBox(Box one, Box two, Vector3f toCentre, CollisionData data, int best, float pen)
	{
		Contact contact = data.nextContact();
		
		Vector3f normal = one.getAxis(best);
		if(one.getAxis(best).dot(toCentre) > 0)
		{
			normal = normal.mul(-1);
		}
		
		Vector3f vertex = two.halfSize;
		if(two.getAxis(0).dot(normal) < 0) vertex.x = -vertex.x;
		if(two.getAxis(1).dot(normal) < 0) vertex.y = -vertex.y;
		if(two.getAxis(2).dot(normal) < 0) vertex.z = -vertex.z;
		
		contact.contactNormal = normal;
		contact.penetration = pen;
		contact.contactPoint = two.getTransform().getTransformation().transform(vertex);
		contact.setBodyData(one.body, two.body, data.friction, data.restitution);
	}
	
	public int boxAndBox(Box one, Box two, CollisionData data)
	{
		Vector3f toCentre = two.getAxis(3).sub(one.getAxis(3));
		
		float pen = Float.MAX_VALUE;
		int best = 0xffffff;
		
		Vector2f penstuff = new Vector2f(pen,best);
		
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0)), toCentre, (0), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1)), toCentre, (1), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2)), toCentre, (2), penstuff)) return 0;
		
		if (!IntersectionTests.tryAxis(one, two, (two.getAxis(0)), toCentre, (3), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (two.getAxis(1)), toCentre, (4), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (two.getAxis(2)), toCentre, (5), penstuff)) return 0;
		
		int bestSingleAxis = (int) penstuff.y;
		
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0).cross(two.getAxis(0))), toCentre, (6), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0).cross(two.getAxis(1))), toCentre, (7), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0).cross(two.getAxis(2))), toCentre, (8), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1).cross(two.getAxis(0))), toCentre, (9), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1).cross(two.getAxis(1))), toCentre, (10), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1).cross(two.getAxis(2))), toCentre, (11), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2).cross(two.getAxis(0))), toCentre, (12), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2).cross(two.getAxis(1))), toCentre, (13), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2).cross(two.getAxis(2))), toCentre, (14), penstuff)) return 0;
		
		best = (int) penstuff.y;
		
		if(best == 0xffffff) return 0;
		
		if(best < 3)
		{
			fillPointFaceBoxBox(one, two, toCentre, data, (int)penstuff.y, penstuff.x);
			return 1;
		}
		else if(best < 6)
		{
			fillPointFaceBoxBox(two, one, toCentre.mul(-1f), data, (int)penstuff.y-3, penstuff.x);
			return 1;
		}
		else
		{
			best -= 6;
			int oneAxisIndex = best / 3;
			int twoAxisIndex = best % 3;
			Vector3f oneAxis = one.getAxis(oneAxisIndex);
			Vector3f twoAxis = two.getAxis(twoAxisIndex);
			Vector3f axis = oneAxis.cross(twoAxis);
			axis.normalize();
			
			if(axis.dot(toCentre) > 0) axis = axis.mul(-1f);
			
			Vector3f ptOnOneEdge = one.halfSize;
			Vector3f ptOnTwoEdge = two.halfSize;
			for(int i = 0; i < 3; i++)
			{
				if(i == oneAxisIndex) ptOnOneEdge.set(i, 0);
				else if(one.getAxis(i).dot(axis) > 0) ptOnOneEdge.set(i, -ptOnOneEdge.get(i));
				
				if(i == twoAxisIndex) ptOnTwoEdge.set(i, 0);
				else if(two.getAxis(i).dot(axis) < 0) ptOnTwoEdge.set(i, -ptOnTwoEdge.get(i));
			}
			
			ptOnOneEdge = one.getTransform().getTransformation().transform(ptOnOneEdge);
			ptOnTwoEdge = two.getTransform().getTransformation().transform(ptOnTwoEdge);
			
			Vector3f vertex = IntersectionTests.contactPoint(
					ptOnOneEdge, oneAxis, one.halfSize.get(oneAxisIndex),
					ptOnTwoEdge, twoAxis, two.halfSize.get(twoAxisIndex),
					bestSingleAxis > 2
							);
			
			Contact contact = data.nextContact();
			
			contact.penetration = pen;
			contact.contactNormal = axis;
			contact.contactPoint = vertex;
			contact.setBodyData(one.body, two.body, data.friction, data.restitution);
			return 1;
		}
	}
	
	public int boxAndPoint(Box box, Vector3f point, CollisionData data)
	{
		Vector3f relPt = box.getTransform().getTransformation().transformInverse(point);
		
		Vector3f normal = new Vector3f(0,0,0);
		
		float mindepth = box.halfSize.x - Math.abs(relPt.x);
		if(mindepth < 0) return 0;
		normal = box.getAxis(0).mul((relPt.x < 0)?-1:1);
		
		float depth = box.halfSize.y - Math.abs(relPt.y);
		if(depth < 0) return 0;
		else if(depth < mindepth)
		{
			mindepth = depth;
			normal = box.getAxis(1).mul((relPt.y < 0)?-1:1);
		}
		
		depth = box.halfSize.z - Math.abs(relPt.y);
		if(depth < 0) return 0;
		else if(depth < mindepth)
		{
			mindepth = depth;
			normal = box.getAxis(2).mul((relPt.z < 0)?-1:1);
		}
		
		Contact contact = data.nextContact();
		contact.contactNormal = normal;
		contact.contactPoint = point;
		contact.penetration = mindepth;
		
		contact.setBodyData(box.body, null, data.friction, data.restitution);
		
		return 1;
	}
	
	public int boxAndSphere(Box box, Sphere sphere, CollisionData data)
	{
		Vector3f centre = sphere.getAxis(3);
		Vector3f relCentre = box.getTransform().getTransformation().transformInverse(centre);
		
		if(
				Math.abs(relCentre.x) - sphere.radius > box.halfSize.x ||
				Math.abs(relCentre.y) - sphere.radius > box.halfSize.y ||
				Math.abs(relCentre.z) - sphere.radius > box.halfSize.z
				) return 0;
		
		Vector3f closestPt = new Vector3f(0,0,0);
		float dist;
		
		dist = relCentre.x;
		if(dist > box.halfSize.x) dist = box.halfSize.x;
		if(dist < -box.halfSize.x) dist = -box.halfSize.x;
		closestPt.x = dist;
		
		dist = relCentre.y;
		if(dist > box.halfSize.y) dist = box.halfSize.y;
		if(dist < -box.halfSize.y) dist = -box.halfSize.y;
		closestPt.y = dist;
		
		dist = relCentre.z;
		if(dist > box.halfSize.z) dist = box.halfSize.z;
		if(dist < -box.halfSize.z) dist = -box.halfSize.z;
		closestPt.z = dist;
		
		Vector3f s = closestPt.sub(relCentre);
		dist = (s.magnitude()*s.magnitude());
		if(dist > sphere.radius * sphere.radius) return 0;
		
		Vector3f closestPtWorld = box.getTransform().getTransformation().transform(closestPt);
		
		Contact contact = data.nextContact();
		contact.contactNormal = (closestPtWorld.sub(centre));
		contact.contactNormal.normalize();
		contact.contactPoint = closestPtWorld;
		contact.penetration = (float) (sphere.radius - Math.sqrt(dist));
		contact.setBodyData(box.body, sphere.body, data.friction, data.restitution);
		
		return 1;
	}
	
	public int boxAndHalfSpace(Box box, Plane plane, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		if(!IntersectionTests.boxAndHalfSpace(box, plane))
		{
			return 0;
		}
		
		float mults[][] = {{1,1,1},{-1,1,1},{1,-1,1},{-1,-1,1},{1,1,-1},{-1,1,-1},{1,-1,-1},{-1,-1,-1}};
		
		Contact contact;
		int contactsUsed = 0;
		for(int i = 0; i < 8; i++)
		{
			Vector3f vertexPos = new Vector3f(mults[i][0], mults[i][1], mults[i][2]);
			
			vertexPos = vertexPos.mul(box.halfSize);
			vertexPos = box.getTransform().getTransformation().transform(vertexPos);
			
			float vertexDistance = vertexPos.dot(plane.direction);
			if(vertexDistance <= plane.offset)
			{
				contact = data.nextContact();
				contact.contactPoint = plane.direction.mul((vertexDistance - plane.offset));
				contact.contactPoint = contact.contactPoint.add(vertexPos);
				contact.contactNormal = plane.direction;
				contact.penetration = plane.offset - vertexDistance;
				
				contact.setBodyData(box.body, null, data.friction, data.restitution);
				contactsUsed++;
				if(contactsUsed == data.contactsLeft) return contactsUsed;
			}
			
		}
			
		return contactsUsed;
	}
	
	public int AABBAndHalfSpace(AABB aabb, Plane plane, CollisionData data)
	{
		//System.out.println("Collide");
		int axis = -1;
		float center = 0;
		float edge = 0;
		float planepos = plane.offset;
		Vector3f move = new Vector3f(0,0,0);
		
		if(plane.direction.x != 0)
		{
			axis = 0;
			center = aabb.body.getPosition().x;
			edge = aabb.halfSize.x;
			move.x = 1;
			//edge = center + aabb.halfSize.x * plane.direction.x * -1;
			//planepos *= plane.direction.x;
		}
		else if(plane.direction.y != 0)
		{
			axis = 1;
			center = aabb.body.getPosition().y;
			edge = aabb.halfSize.y;
			move.y = 1;
			//planepos *= plane.direction.y;
		}
		else if(plane.direction.z != 0)
		{
			axis = 2;
			center = aabb.body.getPosition().z;
			edge = aabb.halfSize.z;
			move.z = 1;
			//edge = center + aabb.halfSize.z * plane.direction.z * -1;
			//planepos *= plane.direction.z;
		}
		
		if(axis == -1) return 0;
		
		float penetration = 0;
		boolean right = false;
		
		if(center < planepos) right = true;
		
		if(right)
		{
			edge = center + edge;
			penetration = edge - planepos;
			if(penetration < 0) return 0;
			move.mul(penetration);
			aabb.body.getTransform().setPos(aabb.body.getPosition().sub(move));
		}
		else
		{
			edge = center - edge;
			penetration = planepos - edge;
			if(penetration < 0) return 0;
			move.mul(penetration);
			aabb.body.getTransform().setPos(aabb.body.getPosition().add(move));
		}
		
		aabb.body.setVelocity(new Vector3f(0,0,0));
		
		return 1;
	}
}

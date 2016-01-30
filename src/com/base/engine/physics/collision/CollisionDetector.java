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
		if(s1 == Shape.Box && s2 == Shape.Plane)
		{
			return boxAndHalfSpace((Box)one, (Plane)two, data);
		}
		if(s1 == Shape.Plane && s2 == Shape.Box)
		{
			return boxAndHalfSpace((Box)two, (Plane)one, data);
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
		}
		return null;
	}
	
	private enum Shape
	{
		Sphere,
		Plane,
		Box;
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
	
	int boxAndHalfSpace(Box box, Plane plane, CollisionData data)
	{
//		if(data.contactsLeft <= 0) return 0;
//		
//		float halfX = box.halfSize.getX();
//		float halfY = box.halfSize.getY();
//		float halfZ = box.halfSize.getZ();
//		Vector3f vertices[] =
//			{
//			new Vector3f(-halfX, -halfY, -halfZ),
//			new Vector3f(-halfX, -halfY, +halfZ),
//			new Vector3f(-halfX, +halfY, -halfZ),
//			new Vector3f(-halfX, +halfY, +halfZ),
//			new Vector3f(+halfX, -halfY, -halfZ),
//			new Vector3f(+halfX, -halfY, +halfZ),
//			new Vector3f(+halfX, +halfY, -halfZ),
//			new Vector3f(+halfX, +halfY, +halfZ)
//			};
//			for (int i = 0; i < 8; i++)
//			{
//				float vertexDistance = vertices[i].mul(plane.direction).magnitude();
//				
//				if(vertexDistance <= plane.offset + data.tolerance)
//				{
//					
//					Contact contact = data.nextContact();
//					contact.contactNormal = plane.direction;
//					contact.penetration = plane.offset - vertexDistance;
//					contact.contactPoint = plane.direction.mul(vertexDistance-plane.offset);
//					
//					contact.body[0] = box.body;
//					contact.body[1] = null;
////					contact.restitution = data.restitution;
////					contact.friction = data.friction;
//				}
//			}
		
		
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
		
		int bestSingleAxis = best;
		
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0).cross(two.getAxis(0))), toCentre, (6), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0).cross(two.getAxis(1))), toCentre, (7), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0).cross(two.getAxis(2))), toCentre, (8), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1).cross(two.getAxis(0))), toCentre, (9), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1).cross(two.getAxis(1))), toCentre, (10), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1).cross(two.getAxis(2))), toCentre, (11), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2).cross(two.getAxis(0))), toCentre, (12), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2).cross(two.getAxis(1))), toCentre, (13), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2).cross(two.getAxis(2))), toCentre, (14), penstuff)) return 0;
		
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
			
			ptOnOneEdge = one.transform.transform(ptOnOneEdge);
			ptOnTwoEdge = two.transform.transform(ptOnTwoEdge);
			
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
		Vector3f relPt = box.transform.transformInverse(point);
		
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
		Vector3f relCentre = box.transform.transformInverse(centre);
		
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
		
		Vector3f closestPtWorld = box.transform.transform(closestPt);
		
		Contact contact = data.nextContact();
		contact.contactNormal = (closestPtWorld.sub(centre));
		contact.contactNormal.normalize();
		contact.contactPoint = closestPtWorld;
		contact.penetration = (float) (sphere.radius - Math.sqrt(dist));
		contact.setBodyData(box.body, sphere.body, data.friction, data.restitution);
		
		return 1;
	}
}

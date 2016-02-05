package com.base.engine.physics.collision;

import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;

public class IntersectionTests 
{
	public static boolean sphereAndHalfSpace(Sphere sphere, Plane plane)
	{
		float ballDistance = plane.getDirection().dot(sphere.getAxis(3)) - sphere.radius;
		return ballDistance <= plane.getOffset();
	}
	
	public static boolean sphereAndSphere(Sphere one, Sphere two)
	{
		Vector3f midline = one.getAxis(3).sub(two.getAxis(3));
		return midline.magnitude()*midline.magnitude() < (one.radius + two.radius)*(one.radius + two.radius);
	}
	
	public static boolean boxAndBox(Box one, Box two)
	{
		Vector3f toCentre = two.getAxis(3).sub(one.getAxis(3));
		return 
				overlapOnAxis(one, two, (one.getAxis(0)) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(1)) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(2)) , toCentre) &&
				
				overlapOnAxis(one, two, (two.getAxis(0)) , toCentre) &&
				overlapOnAxis(one, two, (two.getAxis(1)) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(2)) , toCentre) &&
				
				overlapOnAxis(one, two, (one.getAxis(0).cross(two.getAxis(0))) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(0).cross(two.getAxis(1))) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(0).cross(two.getAxis(2))) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(1).cross(two.getAxis(0))) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(1).cross(two.getAxis(1))) , toCentre) && 
				overlapOnAxis(one, two, (one.getAxis(1).cross(two.getAxis(2))) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(2).cross(two.getAxis(0))) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(2).cross(two.getAxis(1))) , toCentre) &&
				overlapOnAxis(one, two, (one.getAxis(2).cross(two.getAxis(2))) , toCentre)
				;
	}
	
	public static boolean boxAndHalfSpace(Box box, Plane plane)
	{
		float projectedRadius = transformToAxis(box, plane.getDirection());
		
		float boxDistance = plane.getDirection().dot(box.getAxis(3)) - projectedRadius;
		return boxDistance <= plane.getOffset();
	}
	
	public static float transformToAxis(Box box, Vector3f axis)
	{
		return
				box.getHalfSize().x * Math.abs(axis.dot(box.getAxis(0))) +
				box.getHalfSize().y * Math.abs(axis.dot(box.getAxis(1))) +
				box.getHalfSize().z * Math.abs(axis.dot(box.getAxis(2)));
	}
	
	public static boolean overlapOnAxis(Box one, Box two, Vector3f axis, Vector3f toCentre)
	{
		float oneProject = transformToAxis(one, axis);
		float twoProject = transformToAxis(two, axis);
		
		float distance = Math.abs(toCentre.dot(axis));
		
		return (distance < oneProject + twoProject);
	}
	
	public static float penetrationOnAxis(Box one, Box two, Vector3f axis, Vector3f toCentre)
	{
		float oneProject = transformToAxis(one, axis);
		float twoProject = transformToAxis(two, axis);
		
		float distance = Math.abs(toCentre.dot(axis));
		
		return oneProject + twoProject - distance;
	}
	
	public static boolean tryAxis(Box one, Box two, Vector3f axis, Vector3f toCentre, int index, Vector2f pen)
	{
		if(axis.magnitude()*axis.magnitude() < 0.0001) return true;
		axis.normalize();
		
		float penetration = penetrationOnAxis(one, two, axis, toCentre);
		
		if(penetration < 0) return false;
		if(penetration < pen.x)
		{
			pen.x = penetration;
			pen.y = index;
		}
		return true;
	}
	
	public static Vector3f contactPoint(Vector3f pone, Vector3f done, float oneSize, Vector3f ptwo, Vector3f dtwo, float twoSize, boolean useOne)
	{
		Vector3f tost, cone, ctwo;
		
		float dpStaOne, dpStaTwo, dpOneTwo, smOne, smTwo;
		float denom, mua, mub;
		
		smOne = done.magnitude()*done.magnitude();
		smTwo = dtwo.magnitude()*dtwo.magnitude();
		dpOneTwo = dtwo.dot(done);
		
		tost = pone.sub(ptwo);
		dpStaOne = done.dot(tost);
		dpStaTwo = dtwo.dot(tost);
		
		denom = smOne * smTwo - dpStaOne * dpStaTwo;
		
		if(Math.abs(denom) < 0.0001f)
		{
			return useOne?pone:ptwo;
		}
		
		mua = (dpOneTwo * dpStaTwo - smTwo * dpStaOne) / denom;
		mub = (smOne * dpStaTwo - dpOneTwo * dpStaOne) / denom;
		
		if(mua > oneSize || mua < -oneSize || mub > twoSize || mub < -twoSize)
		{
			return useOne?pone:ptwo;
		}
		else
		{
			cone = pone.add(done.mul(mua));
			ctwo = ptwo.add(dtwo.mul(mub));
			
			return cone.mul(.5f).add(ctwo.mul(.5f));
		}
		
	}
}

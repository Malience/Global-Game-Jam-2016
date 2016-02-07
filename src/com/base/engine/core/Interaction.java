package com.base.engine.core;


import java.util.ArrayList;

import com.base.engine.components.Camera;
import com.base.engine.components.attachments.Interactable;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.collision.Collider;
import com.base.engine.physics.collision.Sphere;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.UI.UIText;

public class Interaction 
{
	private static final String interactAction = "Interact";
	
	private static Camera main;
	private static ArrayList<Interactable> interactables;
	private static Interactable interacting;
	
	public static void gather()
	{
		main = RenderingEngine.mainCamera;
		interactables = World.world.getInteractables();
	}
	
	private static Vector3f interactThreshold = new Vector3f(2,20,2);
	private static final float length = 5;
	private static float min;
	private static Interactable minI;
	public static void interact()
	{
		interacting = null;
		minI = null;
		min = length + 2;
		Vector3f direction = main.getTransform().getRot().getForward();
		Vector3f playerpos = World.world.focus.getPosition();
		Vector3f distance = null;
		Collider collider = null;
		direction.normalize();
		for(Interactable interact : interactables)
		{
			collider = interact.getCollider();
			if(collider == null)
			{
				distance = interact.getTransform().getPos().sub(playerpos);
				if((Math.abs(distance.x) - interactThreshold.x) < 0 && (Math.abs(distance.y) - interactThreshold.y) < 0 && (Math.abs(distance.z) - interactThreshold.z) < 0)
				{
					interacting = interact;
					break;
				}
			}
			else
			{
				switch(collider.getClass().getSimpleName())
				{
				case "Sphere":
					Sphere sphere = (Sphere)collider;
					Vector3f center = sphere.getTransform().getPos();
					Vector3f v = center.sub(playerpos);
					float dv = direction.dot(v);
					if(dv > 0)
					{
						Vector3f projection = center.projection(direction);
						if(projection.sub(center).length() <= sphere.radius)
						{
							float dist = projection.sub(playerpos).length();
							if(dist < length && dist < min)
							{
								min = dist;
								minI = interact;
							}
						}
					}
					break;
				}
			}
		}
		if(minI != null)
		{
			interacting = minI;
		}
		if(interacting != null)
		{
			interacting.interactable();
			if(Input.getBindingPressed(interactAction))
			{
				interacting.interact();
			}
		}
		else
		{
			standard();
		}
		
	}
	
	public static UIText interactIcon = new UIText(400, 100, "timesNewRoman.png", "E", 64);
	public static UIText interactText = new UIText(400, 50, "timesNewRoman.png", "E", 32);
	private static boolean first = true;
	/**
	 * What objects will do if they are interactable but don't do anything specific about it
	 */
	public static void standard() 
	{
		if(first)
		{
			RenderingEngine.UI.add(interactIcon);
			RenderingEngine.UI.add(interactText);
			first = false;
		}
		if(interacting != null)
		{
			interactIcon.active = true;
			interactText.text = "Interact";// + interacting.getParent().toString();
			interactText.generate();
			interactText.active = true;
		}
		else
		{
			interactIcon.active = false;
			interactText.active = false;
		}
	}
}

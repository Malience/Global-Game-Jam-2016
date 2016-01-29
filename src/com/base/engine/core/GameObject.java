package com.base.engine.core;

import com.base.engine.core.math.GameObjectContainer;
import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.ComponentAttachment;
import com.base.engine.core.math.Transform;
import com.base.engine.core.math.Vector3f;

import java.util.ArrayList;

public class GameObject implements GameObjectContainer
{
	
	private ArrayList<GameObject> children;
	private ComponentHash components;
	private Transform transform;

	
	public GameObject()
	{
		children = new ArrayList<GameObject>();
		components = new ComponentHash();
		transform = new Transform();
	}

	public void addChild(GameObject child)
	{
		children.add(child);
		//child.setEngine(engine);
		child.getTransform().setParent(transform);
	}

	public GameObject addComponent(GameComponent component)
	{
		components.add(component);
		component.attach(this);

		return this;
	}
	
	public void update()
	{
		transform.update();
	}
//	public void inputAll(float delta)
//	{
//		input(delta);
//
//		for(GameObject child : children)
//			child.inputAll(delta);
//	}
//
//	public void updateAll(float delta, PhysicsEngine physicsEngine)
//	{
//		update(delta, physicsEngine);
//
//		for(GameObject child : children)
//			child.updateAll(delta, physicsEngine);
//	}
//
//	public void renderAll(Shader shader, RenderingEngine renderingEngine)
//	{
//		render(shader, renderingEngine);
//
//		for(GameObject child : children)
//			child.renderAll(shader, renderingEngine);
//	}
//	
//	public void input(float delta)
//	{
//		transform.update();
//
//		for(GameComponent component : components)
//			component.input(delta);
//	}
//
//	public void update(float delta, PhysicsEngine physicsEngine)
//	{
//		for(GameComponent component : components)
//			component.update(delta, physicsEngine);
//	}
//
//	public void render(Shader shader, RenderingEngine renderingEngine)
//	{
//		for(GameComponent component : components)
//			component.render(shader, renderingEngine);
//	}
	
	
	public ArrayList<GameObject> getAllAttached()
	{
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		
		for(GameObject child : children)
		{
			result.addAll(child.getAllAttached());
		}
		result.add(this);
		return result;
	}

	public Transform getTransform()
	{
		return transform;
	}
	
	public Vector3f getPosition()
	{
		return transform.getPos();
	}
	
//	public void setEngine(CoreEngine engine)
//	{
//		if(this.engine != engine)
//		{
//			this.engine = engine;
//			
//			for(GameComponent component : components)
//				component.addToEngine(engine);
//
//			for(GameObject child : children)
//				child.setEngine(engine);
//		}
//	}
	
	public ArrayList<ComponentAttachment> getComponentsOf(ComponentAttachment ca)
	{
		return components.getBucket(ca);
	}
	
	public ArrayList<ComponentAttachment> getComponentsOf(String ca)
	{
		return components.getBucket(ca);
	}
	
	public ArrayList<ComponentAttachment> getAllComponentsOf(ComponentAttachment ca)
	{
		ArrayList<ComponentAttachment> out = new ArrayList<ComponentAttachment>();
		ArrayList<ComponentAttachment> check = getComponentsOf(ca);
		if(check != null)
		{
			out.addAll(check);
		}
		for(GameObject child : children)
		{
			check = child.getAllComponentsOf(ca);
			if(check != null)
			{
				out.addAll(check);
			}
		}
		return out;
	}
	
	public ArrayList<ComponentAttachment> getAllComponentsOf(String ca)
	{
		ArrayList<ComponentAttachment> out = new ArrayList<ComponentAttachment>();
		ArrayList<ComponentAttachment> check = getComponentsOf(ca);
		if(check != null)
		{
			out.addAll(check);
		}
		for(GameObject child : children)
		{
			check = child.getAllComponentsOf(ca);
			if(check != null)
			{
				out.addAll(check);
			}
		}
		return out;
	}
	
	@Override
	public ArrayList<Object> getContents() 
	{
		ArrayList<Object> out = new ArrayList<Object>();
		out.addAll(children);
		out.addAll(components.getAll());
		return out;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + "->" + components.toString();
	}
}

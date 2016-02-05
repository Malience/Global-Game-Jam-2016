package com.base.engine.physics.PremadeObjects;

import com.base.engine.components.MeshRenderer;
import com.base.engine.core.GameObject;
import com.base.engine.physics.RigidBody.RigidBody;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public abstract class PremadeObjects extends GameObject
{
	private Mesh mesh;
	private Material mat;
	private MeshRenderer renderer;
	private RigidBody body;
	
	public PremadeObjects(Mesh mesh, float mass, float ldamping, float adamping)
	{
		this.mesh = mesh;
		mat = new Material();
		mat.addTexture("diffuse", new Texture("test.png"));
		renderer = new MeshRenderer(mesh, mat);
		this.addComponent(renderer);
		
		body = new RigidBody(mass, ldamping, adamping);
		body.attach(this);
		this.addComponent(body);
	}
	
	public RigidBody getRigidBody()
	{
		return body;
	}
	
	public void setTexture(Texture texture)
	{
		mat = new Material();
		mat.addTexture("diffuse", texture);
		renderer.set(mesh, mat);
	}
}

package com.base.engine.core;

import java.util.ArrayList;
import java.util.Collection;

import com.base.engine.components.attachments.*;
import com.base.engine.core.math.Point;
import com.base.engine.core.math.SpatialHash;
import com.base.engine.core.math.Tuple;
import com.base.engine.core.math.Vector3f;
import com.base.game.TestGame;

public class World 
{
	public static World world = new World();
	private SpatialHash spatialHash;
	private ArrayList<GameObject> bucket;
	private ArrayList<Tuple> activeCells;
	public GameObject focus;
	private Game map;
	ArrayList<Updatable> updates;
	
	public World()
	{
		spatialHash = new SpatialHash(100);
		bucket = new ArrayList<GameObject>();
		map = new TestGame();
	}
	
	public void init()
	{
		map.init();
	}
	
	public void add(GameObject object)
	{
		spatialHash.insert(object, object.getPosition());
	}
	
	public void addToBucket(GameObject object)
	{
		bucket.add(object);
	}
	
	public void refreshActives()
	{
		activeCells = spatialHash.getCellsAdjacent(spatialHash.hash(focus.getPosition()));
	}
	
	//Convenience Methods!
	public void updateObjects()
	{
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			object.update();
		}
	}
	
	public void gather()
	{
		updates = this.getUpdatable();
	}
	
	public void update(float delta)
	{
		for(Updatable update : updates)
		{
			update.update(delta);
		}
	}
	
	public ArrayList<GameObject> getGameObjects()
	{
		ArrayList<GameObject> out = new ArrayList<GameObject>();
		ArrayList<Point> points = spatialHash.getAll();
		for(Point point : points)
		{
			out.addAll(point.getType("GameObject"));
		}
		out.addAll(bucket);
		return out;
	}
	
	public ArrayList<GameObject> getGameObjects(ArrayList<Tuple> cells)
	{
		ArrayList<GameObject> out = new ArrayList<GameObject>();
		ArrayList<Point> points = spatialHash.getAll();//get(cells);
		for(Point point : points)
		{
			out.addAll(point.getType("GameObject"));
		}
		out.addAll(bucket);
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Renderable> getRenderable()
	{
		ArrayList<Renderable> out = new ArrayList<Renderable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Renderable>) object.getAllComponentsOf("Renderable"));
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Controlable> getControlable()
	{
		ArrayList<Controlable> out = new ArrayList<Controlable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Controlable>) object.getAllComponentsOf("Controlable"));
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Physical> getPhysical()
	{
		ArrayList<Physical> out = new ArrayList<Physical>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Physical>) object.getAllComponentsOf("Physical"));
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Collidable> getCollidable()
	{
		ArrayList<Collidable> out = new ArrayList<Collidable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Collidable>) object.getAllComponentsOf("Collidable"));
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<LightAttachment> getLightAttachments() {
		ArrayList<LightAttachment> out = new ArrayList<LightAttachment>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends LightAttachment>) object.getAllComponentsOf("LightAttachment"));
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Updatable> getUpdatable() {
		ArrayList<Updatable> out = new ArrayList<Updatable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Updatable>) object.getAllComponentsOf("Updatable"));
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Interactable> getInteractables() {
		ArrayList<Interactable> out = new ArrayList<Interactable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Interactable>) object.getAllComponentsOf("Interactable"));
		}
		return out;
	}
}

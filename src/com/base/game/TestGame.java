package com.base.game;

import game.AccessItems;
import game.BallWaterfall;
import game.Battery;
import game.Listener;
import game.Map;
import game.NewBattery;
import game.Player;
import game.PickUpItem;
import game.PowerUp;
import game.Room;
import game.FlashLight;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.core.math.Quaternion;
import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.PremadeObjects.BoxObject;
import com.base.engine.physics.PremadeObjects.PlaneObject;
import com.base.engine.physics.PremadeObjects.SphereObject;
import com.base.engine.physics.RigidBody.RigidBody;
import com.base.engine.physics.collision.Box;
import com.base.engine.physics.collision.Plane;
import com.base.engine.physics.collision.Sphere;
import com.base.engine.rendering.*;

public class TestGame extends Game
{
	public void init()
	{
		world = World.world;
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

		int indices[] = { 0, 1, 2,
				2, 1, 3};

		Vertex[] vertices2 = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth/ 10, 0.0f, -fieldDepth/ 10), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-fieldWidth/ 10, 0.0f, fieldDepth/ 10 * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(fieldWidth/ 10 * 3, 0.0f, -fieldDepth/ 10), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(fieldWidth/ 10 * 3, 0.0f, fieldDepth/ 10 * 3), new Vector2f(1.0f, 1.0f))};

		int indices2[] = { 0, 1, 2,
				2, 1, 3};
		

		
		Mesh mesh2 = new Mesh(vertices2, indices2, true);

		Mesh mesh = new Mesh(vertices, indices, true);
		Material material = new Material();//new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		material.addTexture("diffuse", new Texture("test.png"));
		material.addFloat("specularIntensity", 1);
		material.addFloat("specularPower", 8);
		

		Mesh tempMesh = new Mesh("monkey3.obj");
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPos().set(0, -1, 5);

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(150f/255f,75f/255f,20f/255f), .5f);
//		RenderingEngine.dlight = directionalLight;

		directionalLightObject.addComponent(directionalLight);

		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new Vector3f(0,1,0), 0.4f, new Attenuation(0,0,1)));

		SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f,
				new Attenuation(0,0,0.1f), 0.7f);

		GameObject spotLightObject = new GameObject();
		spotLightObject.addComponent(spotLight);

		spotLightObject.getTransform().getPos().set(5, 0, 5);
		spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(90.0f)));

		//addObject(planeObject);
		addObject(directionalLightObject);
		//addObject(pointLightObject);
		//addObject(spotLightObject);

		//getRootObject().addChild(new GameObject().addComponent(new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f)));

		GameObject testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh3 = new GameObject().addComponent(new FreeLook(0.5f)).addComponent(new FreeMove(10)).addComponent(new LookAtComponent()).addComponent(new MeshRenderer(tempMesh, material));

		testMesh1.getTransform().getPos().set(0, 2, 0);
		testMesh1.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));

		testMesh2.getTransform().getPos().set(0, 0, 5);

		testMesh1.addChild(testMesh2);
		//getRootObject()
		//GameObject cameraObject = new GameObject();
		//Camera camera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f);
		//GameComponent freelook = new FreeLook(0.5f);
		//GameComponent freeMove = new FreeMove(10);
		
		
		//world.add(cameraObject);
		
		//RenderingEngine.mainCamera = camera;
		
		//cameraObject.addComponent(freelook).addComponent(freeMove).addComponent(camera);
		//world.focus = cameraObject;
		//world.add(cameraObject);
		//testMesh2.addChild(cameraObject);

		//addObject(testMesh2);
		
		//addObject(testMesh1);
		//addObject(testMesh3);

		testMesh3.getTransform().getPos().set(5, 5, 5);
		testMesh3.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(-70.0f)));
		
		//addObject(new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), material)));
		
		directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1,0,0), (float)Math.toRadians(-45)));
		
		BoxObject box1 = new BoxObject(5, .2f, 0);
		BoxObject box2 = new BoxObject(5, .5f, .1f);
		PlaneObject plane = new PlaneObject(new Vector3f(0,1,1), -11);
		SphereObject sphere = new SphereObject(20, 1, 1, 1);
		SphereObject sphere2 = new SphereObject(1, 1, 1, 1);
		BallWaterfall waterfall = new BallWaterfall(20, 1, 1, 1, .2f);
		
		box1.getTransform().setPos(new Vector3f(-40,25,1));
		box2.getTransform().setPos(new Vector3f(-25,18,0));
		plane.getTransform().setPos(new Vector3f(-25, 20, 0));
		sphere.getTransform().setPos(new Vector3f(-30, 20, 0));
		sphere2.getTransform().setPos(new Vector3f(-30, 20, 0));
		waterfall.getTransform().setPos(new Vector3f(-25, 20, 0));
		
		box1.getRigidBody().addVelocity(new Vector3f(0,0,15));
		box2.getRigidBody().addVelocity(new Vector3f(5,15,0));
		sphere.getRigidBody().addVelocity(new Vector3f(-20,0,0));
		sphere2.getRigidBody().addVelocity(new Vector3f(20,0,0));
		world.add(box1);
		world.add(box2);
		//world.add(plane);
		world.add(sphere);
		world.add(sphere2);
		//world.add(waterfall);
		
		
		GameObject object = new GameObject("Last Object");
		FlashLight fl = new FlashLight();
		//object.addChild(fl);
		world.add(object);
		
		GameComponent accessItems = new AccessItems();
		object.addComponent(accessItems);
		
		GameComponent newBattery = new NewBattery();
		object.addComponent(newBattery);
		
		GameComponent pickUp = new PickUpItem();
		object.addComponent(pickUp);
		
		
		Map map = new Map(5,5);
		map.showMap();
		
		Listener l = new Listener();
		//cameraObject.addComponent(l);
		//Room room = new Room("Our House");
		//room.addComponent(room.calculate());
		//room.getTransform().setPos(new Vector3f(0,10,0));	
		
		//world.add(room);
		
		Vector3f mainRoomTopCenterPos = map.getMainRoomPos().add(Room.roomSize.mul(2.0f));
		Player player = new Player();
		//player.getTransform().setPos(new Vector3f(mainRoomTopCenterPos.x, Room.roomSize.y * 2.0f, mainRoomTopCenterPos.z));
		
		player.addChild(fl);
		
		//player.getTransform().setPos(new Vector3f(mainRoomTopCenterPos.x, Room.roomSize.y * 1.5f, mainRoomTopCenterPos.z));
		world.addToBucket(player);
		
		
		GameObject gmonkey = new GameObject();
		Material m = new Material();
		m.addTexture("diffuse", new Texture("test.png"));
		
		MeshRenderer renderer = new MeshRenderer(new Mesh("monkey3.obj"), m);
		
		gmonkey.addComponent(renderer);
		gmonkey.addComponent(new LookAtComponent());
		
		//gmonkey.getTransform().setPos(new Vector3f(0,800,700));
		gmonkey.getTransform().setScale(10);
		
		
		MeshRenderer r1 = (MeshRenderer) sphere.getAllComponentsOf("Renderable").get(0);
		r1.set(new Mesh("monkey3.obj"), m);
		MeshRenderer r2 = (MeshRenderer) sphere2.getAllComponentsOf("Renderable").get(0);
		r2.set(new Mesh("monkey3.obj"), m);
		//sphere.addChild(gmonkey);
		sphere.getTransform().setScale(10);
		sphere.addComponent(new LookAtComponent(sphere2));
		sphere2.getTransform().setScale(10);
		sphere2.addComponent(new LookAtComponent(sphere));
		//world.add(gmonkey);
		
		//addObject(new TestObject(0,0,0).convert());
	}
}

package com.base.engine.core;


import static org.lwjgl.glfw.GLFW.*;
import static com.base.engine.core.Input.*;
import static com.base.engine.rendering.Window.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.opengl.GL;

import com.base.engine.components.attachments.Updatable;
import com.base.engine.physics.PhysicsEngine;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

public class CoreEngine {
	private boolean isRunning;
	private long MainWindow;
	private World world;
	private RenderingEngine renderingEngine;
	private PhysicsEngine physicsEngine;
	private int width;
	private int height;
	private String name;
	private double frameTime;
	private boolean editMode;
	
	public CoreEngine(int width, int height, String name, double framerate)
	{
		this(width, height, name, framerate, false);
	}
	
	public CoreEngine(int width, int height, String name, double framerate, boolean editMode)
	{
		this.world = World.world;
		this.width = width;
		this.height = height;
		this.name = name;
		this.frameTime = 1.0/framerate;
		this.editMode = editMode;
		
		start();
	}
	
	private void start()
	{
		glfwInit();
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		MainWindow = createMainWindow(width, height, name);
		
		glfwMakeContextCurrent(MainWindow);
		glfwSwapInterval(1);		
		
		glfwShowWindow(MainWindow);
		
		
		run();
	}
	
	private void stop()
	{
		isRunning = false;
	}
	
	private void run()
	{
		isRunning = true;
		
		GL.createCapabilities();
		
		Input.init(MainWindow);
		
		renderingEngine = new RenderingEngine();
		physicsEngine = new PhysicsEngine();
		world.init();
		
		
		int frames = 0;
		double frameCounter = 0;
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		
		while(isRunning && glfwWindowShouldClose(MainWindow) == GL_FALSE)
		{
			boolean render = false;
			
			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			world.remove();
			world.refreshActives();
			world.updateObjects();
			physicsEngine.startFrame();
			//Gather Resources
			Input.gather();
			renderingEngine.gather();
			physicsEngine.gather();
			world.gather();
			
			while(unprocessedTime > frameTime)
			{
				render = true;
				
				unprocessedTime -= frameTime;
				
				if(glfwWindowShouldClose(MainWindow) == GL_TRUE)
					stop();
				
				
				
				glfwPollEvents();
				
				Input.update();
				Input.input((float)frameTime);
				Input.interact();
				world.update((float)frameTime);
				
				//Physics Engine
				physicsEngine.integrate((float)frameTime);
				physicsEngine.simulate((float)frameTime);
				physicsEngine.generateContacts((float)frameTime);
				physicsEngine.handleCollisions((float)frameTime);
				
				if(frameCounter >= 1.0)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}	
			}
			
			if(render)
			{
				renderingEngine.render();
				Window.render(MainWindow);
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
		
			
		}
		
		cleanUp();
	}
	
	private void cleanUp()
	{
		dispose(MainWindow);
	}

	public RenderingEngine getRenderingEngine() {
		return renderingEngine;
	}
}

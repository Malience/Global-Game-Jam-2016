package com.base.engine.core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import com.base.engine.components.attachments.Controlable;
import com.base.engine.components.attachments.Interactable;
import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.UI.UIElement;
import com.base.engine.rendering.UI.UIText;

public class Input
{
	static World world = World.world;
	
	static ArrayList<Controlable> inputs;
	static ArrayList<Interactable> interacts;
	
	final static int NUM_KEYS = 338;
	final static int NUM_MOUSE = 8;
	
	static boolean[] currentKeys = new boolean[NUM_KEYS];
	static boolean[] currentMouse = new boolean[NUM_MOUSE];
	
	static boolean[] prevKeys = new boolean[NUM_KEYS];
	
	static long MainWindow;
	
	public static UIText interactIcon;
	public static UIText interactText;
	
	//private static GLFWKeyCallback keyCallback;
	
	private static DoubleBuffer mx;
	private static DoubleBuffer my;
	
	public static void init(long window)
	{
		MainWindow = window;
		
		mx = BufferUtils.createDoubleBuffer(1);
		my = BufferUtils.createDoubleBuffer(1);
		/*
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
        */
		interactIcon = new UIText(400, 100, "timesNewRoman.png", "E", 64);
		interactText = new UIText(400, 50, "timesNewRoman.png", "E", 32);
		
		interactIcon.active = false;
		interactText.active = false;
		
		interacts = new ArrayList<Interactable>();
	}
	
	public static void gather()
	{
		inputs = world.getControlable();
		interacts = world.getInteractables();
	}
	
	public static void input(float delta)
	{
		for(Controlable input : inputs)
		{
			input.input(delta);
		}
	}
	
	private static Vector3f interactThreshold = new Vector3f(1,5,1);
	private static boolean first = true;
	public static void interact()
	{
		if(first)
		{
			RenderingEngine.UI.add(interactIcon);
			RenderingEngine.UI.add(interactText);
			first = false;
		}
		
		Interactable interacting = null;
		Vector3f playerpos = World.world.focus.getPosition();
		
		int i = 0;
		int size = interacts.size();
		if(size <= 0) return;
		Interactable interact = interacts.get(i);
		while(interacting == null)
		{
			Vector3f distance = interact.getTransform().getPos().sub(playerpos);
			if((Math.abs(distance.x) - interactThreshold.x) < 0 && (Math.abs(distance.y) - interactThreshold.y) < 0 && (Math.abs(distance.z) - interactThreshold.z) < 0)
			{
				interacting = interact;
			}
			i++;
			if(i >= size) break;
			interact = interacts.get(i);
		}
		
		if(interacting != null)
		{
			interactIcon.active = true;
			interactText.text = "Interact";// + interacting.getParent().toString();
			interactText.generate();
			interactText.active = true;
			if(getKey(GLFW_KEY_E))
			{
				interacting.interact();
			}
		}
		else
		{
			interactIcon.active = false;
			interactText.active = false;
		}
	}
	
	public static void update()
	{
		for(int i = 0; i < NUM_KEYS; i++)
		{
			currentKeys[i] = glfwGetKey(MainWindow, i) == 1;
		}
		for(int i = 0; i < NUM_MOUSE; i++)
		{
			currentMouse[i] = glfwGetMouseButton(MainWindow, i) == 1;
		}
	}
	
	public static boolean getKey(int key)
	{
		return currentKeys[key];
	}
	
	public static boolean getKeyPressed(int key)
	{
		return !currentKeys[key] && prevKeys[key];
	}
	
	public static boolean getMouse(int mouse)
	{
		return currentMouse[mouse];
	}
	
	
	public static Vector2f getMousePosition()
	{
		glfwGetCursorPos(MainWindow, mx, my);
		return new Vector2f((float)mx.get(0), (float)my.get(0));
	}
	
	public static void setMousePosition(Vector2f pos)
	{
		glfwSetCursorPos(MainWindow, (double)pos.getX(), (double)pos.getY());
	}
	
	public static void setCursor(boolean enabled)
	{
		if(enabled)
			glfwSetInputMode(MainWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		else
			glfwSetInputMode(MainWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

}

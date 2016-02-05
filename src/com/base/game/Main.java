package com.base.game;


import com.base.engine.core.CoreEngine;
import com.base.engine.core.math.Vector2f;
import com.base.engine.rendering.Window;


public class Main
{
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		//Testing.testAll();
			
//		//
//		Noise sound = new Noise();
//		sound.play();
		//
	
	
		CoreEngine engine = new CoreEngine(800,600, "Monkey in the Middle", 60);
		//CoreEngine engine = new CoreEngine(MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT, MainGame.NAME, 60, new MainGame());
//		engine.createWindow("3D Game Engine");
//		engine.start();
		
	}
}
package com.base.engine.rendering.UI;

public class Button extends UIElement
{
	float width;
	float height;
	float pressedColorEffect = 2;
	boolean isPressed = false;
	
	Button(float x, float y, String texturePath, float width, float height)
	{
		super(x, y, texturePath, "UIButton");
		this.width = width;
		this.height = height;
	}
	
	
}

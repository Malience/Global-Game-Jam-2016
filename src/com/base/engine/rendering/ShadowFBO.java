package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

public class ShadowFBO 
{
	int fbo;
	int shadowMap;
	
	public ShadowFBO()
	{
		init((int)Window.getWidth(), (int)Window.getHeight());
	}
	
	public boolean init(int w, int h)
	{
		fbo = glGenFramebuffers();
		
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		
		shadowMap = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, shadowMap);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, w, h, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, shadowMap, 0);
		
		glDrawBuffer(GL_NONE);
		glReadBuffer(GL_NONE);
		
		
		return false;
	}
	
	public int getShadowMap()
	{
		return shadowMap;
	}
	
	public void write()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, shadowMap);
	}
	
	public void finalize()
	{
		cleanup();
	}
	
	public void cleanup()
	{
		glDeleteTextures(shadowMap);
		glDeleteBuffers(fbo);
	}
}

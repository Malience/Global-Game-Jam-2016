package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.attachments.LightAttachment;
import com.base.engine.components.attachments.Renderable;
import com.base.engine.core.GameObject;
import com.base.engine.core.World;
import com.base.engine.core.math.Matrix4f;
import com.base.engine.core.math.Transform;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.MeshLoading.ResourceManagement.MappedValues;
import com.base.engine.rendering.UI.UIElement;
import com.base.engine.rendering.UI.UIText;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

public class RenderingEngine extends MappedValues
{
	World world;
	
	public static Camera mainCamera;

	private ArrayList<LightAttachment> lights;
	private LightAttachment activeLight;

	private ArrayList<UIElement> UI;
	private Shader UIShader;
	
	private HashMap<String, Integer> samplerMap;
	private Shader forwardAmbient;
	
	private ArrayList<Renderable> renders;
	
	//private Shader shadowShader;
	//private ShadowFBO shadowMapFBO;
	
	
	public RenderingEngine()
	{
		super();
		world = World.world;
		
		samplerMap = new HashMap<String, Integer>();
		samplerMap.put("diffuse", 0);
		//samplerMap.put("shadowMap", 0);
		
		addVector3f("ambient", new Vector3f(0.1f,0.1f,0.1f));
		
		forwardAmbient = new Shader("forward-ambient");
		//shadowShader = new Shader("shadowShader");
		
		//shadowMapFBO = new ShadowFBO();
		
		UI = new ArrayList<UIElement>();
		UIShader = new Shader("UI");
		
		UI.add(new UIText(0,0,"timesNewRoman.png", "Stuff", 64));
				
		glClearColor(0.0f, 1.0f, 1.0f, 0.0f);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D);
	}

	public void updateUniformStruct(Transform transform, Material material, RenderingEngine renderingEngine, String uniformName, String uniformType)
	{
		throw new IllegalArgumentException(uniformName + " is not a supported type in Rendering Engine");
	}
	
	public void gather()
	{
		lights = world.getLightAttachments();
		renders = world.getRenderable();
	}
	
	public void render()
	{
		//Shadows
//		shadowMapFBO.write();
//		
//		glClear(GL_DEPTH_BUFFER_BIT);
//		DirectionalLight dlight = null;
//		for(BaseLight light : lights)
//		{
//			if(light instanceof DirectionalLight)
//			{
//				dlight = (DirectionalLight) light;
//				break;
//			}
//		}
//		if(dlight != null)
//		{
//			Vector3f lightInvDir = dlight.getDirection();
//			
//			Matrix4f depthProjectionMatrix = new Matrix4f().initOrthographic(-10, 10, -10, 10, -10, 20);
//			Matrix4f depthViewMatrix = new Transform().getLookAtRotation(lightInvDir, new Vector3f(0,1,0)).toRotationMatrix();
//			//Matrix4f depthModelMatrix = new Matrix4f().initIdentity(); //Doesn't matter
//			Matrix4f depthMVP = depthProjectionMatrix.mul(depthViewMatrix);
//			dlight.setMVP(depthMVP);
//			
//			samplerMap.put("shadowMap", shadowMapFBO.getShadowMap());
//			
//			activeLight = dlight;
//			object.renderAll(shadowShader, this);
//		}
		//Rendering
		Window.bindAsRenderTarget();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		renderAll(forwardAmbient);

		
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		for(LightAttachment light : lights)
		{
			activeLight = light;
			renderAll(light.getShader());
		}

		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
		
		
		
		
		
		//UI RENDERING
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glDepthMask(false);
		//glDepthFunc(GL_EQUAL);
		
		UIShader.bind();
		
		for(UIElement u : UI)
		{
			if(u.active) u.render();
		}
	
		//glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	public void renderAll(Shader shader)
	{
		for(Renderable render : renders)
		{
			render.render(shader, this);
		}
	}
	

//	private static void clearScreen()
//	{
//		//TODO: Stencil Buffer
//		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//	}
	
	public static String getOpenGLVersion()
	{
		return glGetString(GL_VERSION);
	}

	public void addLight(BaseLight light)
	{
		lights.add(light);
	}

	public void addCamera(Camera camera)
	{
		mainCamera = camera;
	}

	public void putSampler(String samplerName, int samplerSlot)
	{
		samplerMap.put(samplerName, samplerSlot);
	}
	
	public int getSamplerSlot(String samplerName)
	{
		return samplerMap.get(samplerName);
	}
	
	public LightAttachment getActiveLight()
	{
		return activeLight;
	}

	public Camera getMainCamera()
	{
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera)
	{
		this.mainCamera = mainCamera;
	}
}

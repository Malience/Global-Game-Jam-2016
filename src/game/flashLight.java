package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Updatable;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.UI.UIText;

public class flashLight extends GameComponent implements Updatable
{
	private int life = 100;
	private int counter = 0;
	private int nsec = 0;

	UIText power = new UIText(0,0,"timesNewRoman.png", life + "%", 50);
	
	public flashLight()
	{
		RenderingEngine.UI.add(power);
	}

	public int update(float delta)
	{
		nsec++; 
		
		//when one second is hit counter adds one
		if (nsec >= 20)
		{
			counter++;
			nsec = 0;
		}
		
		//after 10 seconds battery life dies. counter resets
		if (counter >= 10)
		{
			life--;
			counter = 0;
			draw();
			works();
		}
		
		return 1;
	}
	
	// 1,000,000,000 ns = 1 sec
	
	public boolean works()
	{
		if (life > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void newBattery()
	{
		//if new battery is put into the flashlight
		life = 100;
	}
	
	public void draw()
	{	
		power.text = life + "%";
		power.generate();
	}
}

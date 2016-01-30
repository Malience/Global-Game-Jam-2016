package game;

import com.base.engine.components.attachments.Updatable;

public class flashLight implements Updatable
{
	private int life = 100;
	private int counter = 0;
	private int nsec = 0;
	
	public flashLight()
	{
		
	}

	public int update(float delta)
	{
		nsec = (int) delta;
		
		//when one second is hit counter adds one
		if (nsec >= 1000000000)
		{
			counter++;
			nsec = 0;
		}
		
		//after 10 seconds battery life dies. counter resets
		if (counter >= 10)
		{
			life--;
			counter = 0;
		}
		works();
		return 1;
	}
	
	// 1,000,000,000 ns = 1 sec
	
	public boolean works()
	{
		System.out.println("Battery Life: " + life);
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
}

package game;

import com.base.engine.core.GameObject;

public class Inventory 
{
	//items in bag
	// 0 : batteries
	// 1 : artifact (monkey heads) (4)
	// 2 : power-up (only 1)
	// 3 : 
	// 4 : 
	private GameObject[] backpack = new GameObject[14];
	
	public Inventory()
	{
		
	}
	
	public void addItem(GameObject go)
	{
		try
		{
			for (int i = 0; i < backpack.length; i++)
			{
				if (backpack[i] == null)
				{
					backpack[i] = go;
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			System.out.println("To much Stuff");
		}
	}
	
	public void removeItem(GameObject go)
	{
		for (int i = 0; i < backpack.length; i++)
		{
			if (backpack[i] == go)
			{
				backpack[i] = null;
				break;
			}
		}
	}
}

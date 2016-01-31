package game;

import com.base.engine.core.GameObject;

public class Inventory 
{
	//items in bag
	private GameObject[] backpack = new GameObject[14];
	private String list = "Inventory:\n";
	
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
	
	public String getItems()
	{
		for (int i = 0; i < 14; i++)
		{
			if (backpack[i] != null)
			{
				list += "\n" + backpack[i];
			}
		}
		return list;
	}
	
	public int findItem(GameObject go)
	{
		int counter = 0;
		
		for (int i = 0; i < 14; i++)
		{
			if (backpack[i] != go)
			{
				counter++;
			}
		}
		
		return counter;
	}
}

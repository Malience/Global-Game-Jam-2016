package game;

public class Inventory 
{
	//items in bag
	// 0 : batteries
	// 1 : artifact (monkey heads) (4)
	// 2 : power-up (only 1)
	// 3 : 
	// 4 : 
	private int[] backpack = new int[4];
	
	public Inventory()
	{
		
	}
	
	public void addItem(int itemNum)
	{
		if (itemNum == 2)
		{
			System.out.println("Can not hold more than one power-up.");
		}
		else
		{
			backpack[itemNum]++;
		}
	}
	
	public void removeItem(int itemNum)
	{
		backpack[itemNum]--;
	}
}

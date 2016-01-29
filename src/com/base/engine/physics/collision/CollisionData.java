package com.base.engine.physics.collision;

public class CollisionData 
{
	Contact contacts[];
	int contactsLeft;
	int index;
	float tolerance;
	
	public CollisionData(int max)
	{
		contactsLeft = max;
		contacts = new Contact[max];
		index = 0;
		tolerance = .01f;
	}
	
	public Contact nextContact()
	{
		contactsLeft--;
		return contacts[index++];
	}
	
	public void flush()
	{
		contactsLeft = contacts.length;
		contacts = new Contact[contactsLeft];
	}
	
	public void detectContacts(Primitive firstPrimitive, Primitive secondPrimitive, CollisionData data)
	{
		
	}
}

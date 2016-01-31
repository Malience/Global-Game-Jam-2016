package com.base.engine.physics.collision;

public class CollisionData 
{
	Contact contacts[];
	int contactsLeft;
	int index;
	float tolerance;
	int contactCount;
	float friction;
	float restitution;
	
	public CollisionData(int max)
	{
		contactsLeft = max;
		contacts = new Contact[max];
		index = 0;
		tolerance = 0;
		contactCount = 0;
	}
	
	public boolean hasMoreContacts()
	{
		return contactsLeft > 0;
	}
	
	public void reset()
	{
		contactsLeft = contacts.length;
		contactCount = 0;
		contacts = new Contact[contactsLeft];
	}
	
	public Contact nextContact()
	{
		contactsLeft--;
		contactCount++;
		return contacts[index++];
	}
	
	public Contact[] getContacts()
	{
		Contact[] out = contacts;
		reset();
		return out;
	}
	
	public void detectContacts(Primitive firstPrimitive, Primitive secondPrimitive, CollisionData data)
	{
		
	}
}

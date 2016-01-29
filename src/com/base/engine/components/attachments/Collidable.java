package com.base.engine.components.attachments;

public interface Collidable extends ComponentAttachment
{
	public default int checkCollisions(){return 1;}
}

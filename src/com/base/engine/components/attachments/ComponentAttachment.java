package com.base.engine.components.attachments;

import com.base.engine.core.GameObject;

public interface ComponentAttachment 
{
	public default int attach(GameObject parent){return 1;}
	public default int universal(){return 1;}
}

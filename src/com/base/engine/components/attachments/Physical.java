package com.base.engine.components.attachments;

public interface Physical extends ComponentAttachment
{
	public default int integrate(float delta){return 1;};
	public default int simulate(float delta){return 1;};
}

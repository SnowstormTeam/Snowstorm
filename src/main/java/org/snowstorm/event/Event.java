package org.snowstorm.event;

import org.snowstorm.Snowstorm;

public abstract class Event
{
	private boolean cancelled;
	
	
	
	public void call()
	{
		Snowstorm.events().callEvent( this );
	}
	
	
	
	public final void setCancelled( boolean cancel )
	{
		this.cancelled = cancel;
	}
	
	public final boolean isCancelled()
	{
		return this.cancelled;
	}
}

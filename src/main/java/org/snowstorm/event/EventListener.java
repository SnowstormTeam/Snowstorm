package org.snowstorm.event;

import org.snowstorm.Snowstorm;
import org.snowstorm.plugin.Plugin;

public abstract class EventListener
{
	public final void registerListener( Plugin plugin )
	{
		Snowstorm.events().registerListener( this, plugin );
	}
}

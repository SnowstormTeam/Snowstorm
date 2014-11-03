package org.snowstorm.event;

import java.lang.reflect.Method;

import org.snowstorm.Snowstorm;
import org.snowstorm.plugin.Plugin;

public class EventExecutor
{
	private Plugin plugin;
	private EventListener listener;
	private EventHandler handler;
	private Method method;
	
	public EventExecutor( Plugin plugin, EventListener listener, EventHandler handler, Method method )
	{
		this.plugin = plugin;
		this.listener = listener;
		this.handler = handler;
		this.method = method;
	}
	
	
	
	public Plugin getPlugin()
	{
		return this.plugin;
	}
	
	public EventListener getListener()
	{
		return this.listener;
	}
	
	public Priority getPriority()
	{
		return handler.priority();
	}
	
	
	
	public void execute( Event e )
	{
		if( e.isCancelled() && !handler.ignoreCancelled() )
		{
			return;
		}
		
		try
		{
			this.method.invoke( listener, e );
		}
		catch( Exception ex )
		{
			Snowstorm.logging().console( "Error executing event" );
			Snowstorm.logging().console( ex.getMessage() );
		}
	}
}

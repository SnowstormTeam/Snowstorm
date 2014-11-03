package org.snowstorm.event;

import java.lang.reflect.Method;

import org.snowstorm.Snowstorm;

public class EventExecutor
{
	private Method method;
	private EventListener listener;
	
	public EventExecutor( Method method, EventListener listener )
	{
		this.method = method;
		this.listener = listener;
	}
	
	
	
	public EventListener getListener()
	{
		return this.listener;
	}
	
	public void execute( Event e )
	{
		try
		{
			this.method.invoke( listener, e );
		}
		catch( Exception ex )
		{
			Snowstorm.logging().console( "Error executing event" );
		}
	}
}

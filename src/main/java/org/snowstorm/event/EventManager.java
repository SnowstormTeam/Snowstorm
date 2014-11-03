package org.snowstorm.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snowstorm.Snowstorm;
import org.snowstorm.plugin.Plugin;

public class EventManager
{
	private static Map< Class< ? extends Event >, List< EventExecutor > > executors = new HashMap< Class< ? extends Event >, List< EventExecutor > >();
	
	
	
	
	@SuppressWarnings("unchecked")
	public void registerListener( EventListener listener, Plugin plugin )
	{
		List< Method > methods = new ArrayList< Method >();
		
		Collections.addAll( methods, listener.getClass().getMethods() );
		
		for( Method method : methods )
		{
			EventHandler handler = method.getAnnotation( EventHandler.class );
			
			if( handler == null )
			{
				continue;
			}
			
			Class< ? >[] parameters = method.getParameterTypes();
			
			if( parameters.length != 1 )
			{
				continue;
			}
			
			Class< ? extends Event> eventClass = ( Class<? extends Event> ) parameters[ 0 ];
			EventExecutor executor = new EventExecutor( method, listener );
			
			List< EventExecutor > ex = null;
			
			if( executors.containsKey( eventClass ) )
			{
				ex = executors.get( eventClass );
			}
			else
			{
				ex = new ArrayList< EventExecutor >();
			}
			
			ex.add( executor );
			executors.put( eventClass, ex );
			
			Snowstorm.logging().console( "Added" );
		}
	}
	
	public void callEvent( Event e )
	{
		if( e.isCancelled() )
		{
			return;
		}
		
		if( !executors.containsKey( e.getClass() ) )
		{
			return;
		}
		
		for( EventExecutor ex : executors.get( e.getClass() ) )
		{
			if( e.isCancelled() )
			{
				break;
			}
			
			ex.execute( e );
		}
	}
}

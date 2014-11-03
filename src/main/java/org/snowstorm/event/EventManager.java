package org.snowstorm.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			EventExecutor executor = new EventExecutor( plugin, listener, handler, method );
			
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
		}
	}
	
	public void unregisterListener( EventListener listener, Plugin plugin )
	{
		for( Class< ? extends Event > eventClass : executors.keySet())
		{
			List< EventExecutor > keep = new ArrayList< EventExecutor >();
			
			for( EventExecutor e : executors.get( eventClass ) )
			{
				if( e.getListener().getClass() == listener.getClass() && e.getPlugin().getClass() == plugin.getClass() )
				{
					continue;
				}
				
				keep.add( e );
			}
			
			if( keep.isEmpty() )
			{
				executors.remove( eventClass );
			}
			else
			{
				executors.put( eventClass, keep );
			}
		}
	}
	
	public void unregisterListeners( Plugin plugin )
	{
		for( Class< ? extends Event > eventClass : executors.keySet())
		{
			List< EventExecutor > keep = new ArrayList< EventExecutor >();
			
			for( EventExecutor e : executors.get( eventClass ) )
			{
				if( e.getPlugin().getClass() == plugin.getClass() )
				{
					continue;
				}
				
				keep.add( e );
			}
			
			if( keep.isEmpty() )
			{
				executors.remove( eventClass );
			}
			else
			{
				executors.put( eventClass, keep );
			}
		}
	}
	
	
	
	public void callEvent( Event e )
	{
		if( !executors.containsKey( e.getClass() ) )
		{
			return;
		}
		
		List< EventExecutor > executorList = executors.get( e.getClass() );
		Collections.sort( executorList, new PriorityComparator() );
		
		for( EventExecutor ex : executorList )
		{
			ex.execute( e );
		}
	}
	
	
	
	public class PriorityComparator implements Comparator< EventExecutor >
	{
		public int compare( EventExecutor ex1, EventExecutor ex2 )
		{
			return ex2.getPriority().getPriorityValue() - ex1.getPriority().getPriorityValue();
		}
	}
}

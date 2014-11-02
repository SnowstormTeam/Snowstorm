package org.snowstorm.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogManager
{
	private static List< Logger > logger = new ArrayList< Logger >();
	
	private static DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
	
	public Logger getLogger( String name )
	{
		for( Logger log : logger )
		{
			if( log.getName().equals( name ) )
			{
				return log;
			}
		}
		
		return createLogger( name );
	}
	
	public Logger createLogger( String name )
	{
		if( loggerExists( name ) )
		{
			return getLogger( name );
		}
		
		Logger log = new Logger( name );
		logger.add( log );
		
		return log;
	}
	
	public boolean loggerExists( String name )
	{
		for( Logger log : logger )
		{
			if( log.getName().equals( name ) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void console( String message )
	{
		System.out.print( "\r\r" );
		System.out.println( "[" + timeFormat.format( new Date() ) + "] " + message );
		System.out.print( "> " );
	}
}

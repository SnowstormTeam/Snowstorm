package org.snowstorm.logging;

import org.snowstorm.Snowstorm;

public class Logger
{
	private final String name;
	
	public Logger( String name )
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void console( LogLevel level, String message )
	{
		String logLevel = "[" + level.toString() + "] ";
		String logName = "[" + name.toUpperCase() + "] ";
		
		Snowstorm.logging().console( logName + logLevel + message);
	}
	
	public void log( LogLevel level, String message )
	{
		console( level, message );
	}
	
	public void debug( String message )
	{
		log( LogLevel.DEBUG, message );
	}
	
	public void info( String message )
	{
		log( LogLevel.INFO, message );
	}
	
	public void warning( String message )
	{
		log( LogLevel.WARNING, message );
	}
	
	public void error( String message )
	{
		log( LogLevel.ERROR, message );
	}
}

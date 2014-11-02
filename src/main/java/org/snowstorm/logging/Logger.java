package org.snowstorm.logging;

import java.util.ArrayList;
import java.util.List;

import org.snowstorm.Snowstorm;

public class Logger
{
	private final String name;
	
	private List< LogLevel > consoleLevel = new ArrayList< LogLevel >();
	private List< LogLevel > fileLevel = new ArrayList< LogLevel >();
	
	public Logger( String name )
	{
		this.name = name;
		
		consoleLevel.add( LogLevel.INFO );
		consoleLevel.add( LogLevel.ERROR );
		consoleLevel.add( LogLevel.WARNING );
		
		fileLevel.add( LogLevel.INFO );
		fileLevel.add( LogLevel.ERROR );
		fileLevel.add( LogLevel.WARNING );
	}
	
	public String getName()
	{
		return this.name;
	}
	
	
	
	public List< LogLevel > getConsoleLevels()
	{
		return this.consoleLevel;
	}
	
	public List< LogLevel > getFileLevels()
	{
		return this.fileLevel;
	}
	
	public void addConsoleLevel( LogLevel level )
	{
		if( consoleLevel.contains( level ) )
		{
			return;
		}
		
		consoleLevel.add( level );
	}
	
	public void addFileLevel( LogLevel level )
	{
		if( fileLevel.contains( level ) )
		{
			return;
		}
		
		fileLevel.add( level );
	}
	
	public void removeConsoleLevel( LogLevel level )
	{
		if( !consoleLevel.contains( level ) )
		{
			return;
		}
		
		consoleLevel.remove( level );
	}
	
	public void removeFileLevel( LogLevel level )
	{
		if( !fileLevel.contains( level ) )
		{
			return;
		}
		
		fileLevel.remove( level );
	}
	
	
	
	public void console( LogLevel level, String message )
	{
		if( !consoleLevel.contains( level ) )
		{
			return;
		}
		
		String logLevel = "[" + level.toString() + "] ";
		String logName = "[" + name.toUpperCase() + "] ";
		
		Snowstorm.logging().console( logName + logLevel + message );
	}
	
	public void file( LogLevel level, String message )
	{
		if( !fileLevel.contains( level ) )
		{
			return;
		}
		
		String logLevel = "[" + level.toString() + "] ";
		String logName = "[" + name.toUpperCase() + "] ";
		
		Snowstorm.logging().file( logName + logLevel + message );
	}
	
	
	
	public void log( LogLevel level, String message )
	{
		console( level, message );
		file( level, message );
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

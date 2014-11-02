package org.snowstorm;

import org.snowstorm.logging.LogManager;
import org.snowstorm.logging.Logger;

public class Snowstorm
{
	public static final String CORE_VERSION = "0.1-DEV";
	
	private static LogManager logManager;
	
	private static Logger coreLogger;
	
	public static void main( String[] args )
	{
		logManager = new LogManager();
		
		coreLogger = logManager.getLogger( "Core" );
		
		coreLogger.info( "Starting Snowstorm " + CORE_VERSION + "..." );
	}
	
	public static LogManager logging()
	{
		return logManager;
	}
}

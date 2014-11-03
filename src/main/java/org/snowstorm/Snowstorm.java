package org.snowstorm;

import org.snowstorm.event.EventManager;
import org.snowstorm.logging.LogManager;
import org.snowstorm.plugin.PluginManager;

public class Snowstorm
{
	public static final String CORE_VERSION = "0.1-DEV";
	
	public static final String PLUGIN_DIRECTORY = "plugins";
	public static final String LOGGING_DIRECTORY = "log";
	
	private static LogManager logManager;
	private static PluginManager pluginManager;
	private static EventManager eventManager;
	
	public static void main( String[] args )
	{
		logManager = new LogManager();
		pluginManager = new PluginManager();
		eventManager = new EventManager();
		
		logging().getLogger( "Core" ).info( "Starting Snowstorm " + CORE_VERSION + "..." );
		
		pluginManager.loadPlugins();
		pluginManager.enablePlugins();
	}
	
	public static void shutdown()
	{
		logging().getLogger( "Core" ).info( "Snowstorm shutting down..." );
		
		System.exit( 0 );
	}
	
	
	
	public static LogManager logging()
	{
		return logManager;
	}
	
	public static PluginManager plugins()
	{
		return pluginManager;
	}
	
	public static EventManager events()
	{
		return eventManager;
	}
}

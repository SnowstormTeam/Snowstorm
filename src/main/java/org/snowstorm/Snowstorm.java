package org.snowstorm;

import org.snowstorm.logging.LogManager;
import org.snowstorm.plugin.PluginManager;

public class Snowstorm
{
	public static final String CORE_VERSION = "0.1-DEV";
	
	public static final String PLUGIN_DIRECTORY = "plugins";
	public static final String LOGGING_DIRECTORY = "log";
	
	private static LogManager logManager;
	private static PluginManager pluginManager;
	
	public static void main( String[] args )
	{
		logManager = new LogManager();
		pluginManager = new PluginManager();
		
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
}

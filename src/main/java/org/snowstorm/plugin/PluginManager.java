package org.snowstorm.plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.snowstorm.Snowstorm;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

public class PluginManager
{
	private Map< String, PluginContainer > plugins = new HashMap< String, PluginContainer >();
	
	private final JarClassLoader classloader = new JarClassLoader();
	private final JclObjectFactory jarfactory = JclObjectFactory.getInstance();
	
	public PluginManager()
	{
		createPluginDirectory();
	}
	
	
	
	public void loadPlugins()
	{
		Snowstorm.logging().getLogger( "Core" ).info( "Loading plugins..." );
		
		File dir = new File( Snowstorm.PLUGIN_DIRECTORY );
		File[] fileList = dir.listFiles();
		
		for( File f : fileList )
		{
			if( f.isFile() && f.getPath().endsWith( ".jar" ) )
			{
				try
				{
					loadPlugin( f.getPath() );
				}
				catch( Exception e )
				{
					Snowstorm.logging().getLogger( "Core" ).error( "Error while loading plugin " + f.getName());
				}
			}
		}
	}
	
	public void loadPlugin( String path ) throws IOException
	{
		PluginContainer container = new PluginContainer( path );
		plugins.put( container.getName(), container );
		
		Snowstorm.logging().getLogger( "Core" ).info( "Loading plugin " + container.getName() + "..." );
		
		classloader.add( path );
		Plugin plugin = (Plugin) jarfactory.create( classloader, container.getDescription().getMainClass() );
		plugin.setName( container.getName() );
		
		container.setPlugin( plugin );
	}
	
	public void enablePlugins()
	{
		Snowstorm.logging().getLogger( "Core" ).info( "Enabling all plugins..." );
		
		for( PluginContainer cont : plugins.values() )
		{
			if( !cont.getPlugin().enablePlugin() )
			{
				Snowstorm.logging().getLogger( "Core" ).warning( "Can't enable plugin " + cont.getName() );
				continue;
			}
			
			cont.setState( PluginState.ENABLED );
		}
		
		for( PluginContainer cont : plugins.values() )
		{
			if( cont.getState() == PluginState.ENABLED )
			{
				cont.getPlugin().registerCommands();
			}
		}
		
		for( PluginContainer cont : plugins.values() )
		{
			if( cont.getState() == PluginState.ENABLED )
			{
				cont.getPlugin().registerHandlers();
			}
		}
	}
	
	
	
	public PluginContainer getContainer( String name )
	{
		return plugins.get( name );
	}
	
	public Plugin getPlugin( String name )
	{
		return getContainer( name ).getPlugin();
	}
	
	
	
	public void createPluginDirectory()
	{
		File dir = new File( Snowstorm.PLUGIN_DIRECTORY );
		
		if( !dir.exists() )
		{
			try
			{
				dir.mkdir();
			}
			catch( Exception e )
			{
				Snowstorm.logging().getLogger( "Core" ).error( "Cannot create plugin directory" );
				return;
			}
		}
	}
}

package org.snowstorm.plugin;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginDescription
{
	private String name;
	private String mainClass;
	
	private String author;
	private String version;
	private String description;
	private String website;

	private List< String > dependencies;
	
	public PluginDescription( String path ) throws IOException
	{
		JarFile pluginJar = new JarFile( path );
		JarEntry entry = pluginJar.getJarEntry( "plugin.properties" );
		Properties pluginProperties = new Properties();
		
		pluginProperties.load( pluginJar.getInputStream( entry ) );
		pluginJar.close();
		
		this.name = pluginProperties.getProperty( "name" );
		this.mainClass = pluginProperties.getProperty( "main-class" );
		
		this.author = pluginProperties.getProperty( "author" );
		this.version = pluginProperties.getProperty( "version" );
		this.description = pluginProperties.getProperty( "description" );
		this.website = pluginProperties.getProperty( "website" );
		
		if( !pluginProperties.containsKey( "dependencies" ) )
		{
			return;
		}
		
		String[] dp = pluginProperties.getProperty( "dependencies" ).split( "," );
		for( int i = 0; i < dp.length; i++ )
		{
			dependencies.add( dp[ i ] );
		}
	}
	
	
	
	public String getName()
	{
		return this.name;
	}
	
	public String getMainClass()
	{
		return this.mainClass;
	}
	
	
	
	public String getAuthor()
	{
		return this.author;
	}
	
	public String getVersion()
	{
		return this.version;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getWebsite()
	{
		return this.website;
	}
	
	
	
	public List< String > getDependencies()
	{
		return this.dependencies;
	}
}

package org.snowstorm.plugin;

import java.io.IOException;

public class PluginContainer
{
	private Plugin plugin;
	
	private String name;
	private String path;

	private PluginState pluginState;
	
	private PluginDescription pluginDescription;
	
	public PluginContainer( String path ) throws IOException
	{
		this.path = path;
		
		this.pluginDescription = new PluginDescription( this.path );
		this.name = pluginDescription.getName();
		
		this.pluginState = PluginState.KNOWN;
	}
	
	protected void setPlugin( Plugin plugin )
	{
		this.plugin = plugin;
	}
	
	protected void setState( PluginState state )
	{
		this.pluginState = state;
	}
	
	
	
	public Plugin getPlugin()
	{
		return this.plugin;
	}
	
	public PluginState getState()
	{
		return this.pluginState;
	}
	
	
	
	public String getName()
	{
		return this.name;
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	public String getMainClass()
	{
		return this.pluginDescription.getMainClass();
	}
	
	
	
	public PluginDescription getDescription()
	{
		return this.pluginDescription;
	}
}

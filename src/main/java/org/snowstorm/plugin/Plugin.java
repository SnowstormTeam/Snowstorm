package org.snowstorm.plugin;

import org.snowstorm.Snowstorm;

public abstract class Plugin
{
	private String name;
	
	public abstract boolean enablePlugin();
	
	public abstract void disablePlugin();
	
	public abstract void registerCommands();
	
	public abstract void registerHandlers();
	
	
	
	public String getName()
	{
		return this.name;
	}
	
	protected void setName( String name )
	{
		this.name = name;
	}
	
	
	
	public PluginContainer container()
	{
		return Snowstorm.plugins().getContainer( this.name );
	}
	
	public PluginState getState()
	{
		return container().getState();
	}
	
	
	
	public PluginDescription getDescription()
	{
		return container().getDescription();
	}
}

package org.snowstorm.event;

public enum Priority
{
	LOWEST( 100 ),
	LOW( 300 ),
	NORMAL( 500 ),
	HIGH( 700 ),
	CRITICAL( 900 );
	
	final int value;
	
	Priority( int priority )
	{
		this.value = priority;
	}
	
	public int getPriorityValue()
	{
		return this.value;
	}
}

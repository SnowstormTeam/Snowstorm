package org.snowstorm.logging;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogManager
{
	private static List< Logger > logger = new ArrayList< Logger >();
	
	private static DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
	
	private String loggingDirectory;
	private File loggingFile;
	
	private boolean fileLogging = true;
	
	public LogManager()
	{
		createLogDirectory();
		createLogFile();
	}
	
	
	
	public Logger getLogger( String name )
	{
		for( Logger log : logger )
		{
			if( log.getName().equals( name ) )
			{
				return log;
			}
		}
		
		return createLogger( name );
	}
	
	public Logger createLogger( String name )
	{
		if( loggerExists( name ) )
		{
			return getLogger( name );
		}
		
		Logger log = new Logger( name );
		logger.add( log );
		
		return log;
	}
	
	public boolean loggerExists( String name )
	{
		for( Logger log : logger )
		{
			if( log.getName().equals( name ) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	
	public void createLogDirectory()
	{
		File dir = new File( "log" );
		
		if( !dir.exists() )
		{
			try
			{
				dir.mkdir();
			}
			catch( Exception e )
			{
				console( "Cannot create logging directory" );
				disableFileLogging();
				return;
			}
		}
		
		loggingDirectory = Paths.get( "log" ).toAbsolutePath().toString();
	}
	
	public String getLogDirectory()
	{
		return this.loggingDirectory;
	}
	
	public void createLogFile()
	{
		int filenr = 0;
		
		do
		{
			filenr = filenr + 1;
			loggingFile = new File( loggingDirectory + "/serverlog" + filenr + ".log" );
		}
		while( loggingFile.exists() );
		
		try
		{
			loggingFile.createNewFile();
		}
		catch( Exception e )
		{
			console( "Cannot create logging File" );
			disableFileLogging();
		}
	}
	
	public void disableFileLogging()
	{
		console( "Disabling file logging..." );
		fileLogging = false;
	}
	
	
	
	public void console( String message )
	{
		System.out.print( "\r\r" );
		System.out.println( "[" + timeFormat.format( new Date() ) + "] " + message );
		System.out.print( "> " );
	}
	
	public void file( String message )
	{
		if( !fileLogging )
		{
			return;
		}
		
		try
		{
			FileWriter writer = new FileWriter( loggingFile, true );
			
			writer.write( "[" + timeFormat.format( new Date() ) + "] " + message );
			writer.append( System.lineSeparator() );
			
			writer.flush();
			writer.close();
		}
		catch( Exception e )
		{
			console( "Cannot write to logging file" );
			disableFileLogging();
		}
	}
}

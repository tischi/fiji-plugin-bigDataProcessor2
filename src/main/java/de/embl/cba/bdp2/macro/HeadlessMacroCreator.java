package de.embl.cba.bdp2.macro;

import de.embl.cba.bdp2.open.ui.AbstractOpenCommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HeadlessMacroCreator
{
	private final File macroFile;
	private ArrayList< String > commands;

	public HeadlessMacroCreator( File macroFile )
	{
		this.macroFile = macroFile;
		readCommands( macroFile );
	}

	private void readCommands( File macroFile )
	{
		try
		{
			BufferedReader bufferedReader = new BufferedReader( new FileReader( macroFile ) );

			commands = new ArrayList<>();
			String line;
			while ( ( line = bufferedReader.readLine() ) != null )
			{
				commands.add( line );
			}
			
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public String createHeadlessExecutableMacroString()
	{
		ArrayList< String > headlessCommands = new ArrayList<>();

		for ( String command : commands )
		{
			if ( command.contains( "BigDataProcessor2") ) continue;
			if ( command.equals( "" ) ) continue;

			command = command.replace( AbstractOpenCommand.SHOW_IN_NEW_VIEWER, AbstractOpenCommand.DO_NOT_SHOW );
			command = command.replace( AbstractOpenCommand.SHOW_IN_CURRENT_VIEWER, AbstractOpenCommand.DO_NOT_SHOW );
			headlessCommands.add( command );
		}

		String join = headlessCommands.stream().collect( Collectors.joining( ";" ) );

		return join;
	}

	public static void main( String[] args )
	{
		HeadlessMacroCreator macroCreator = new HeadlessMacroCreator( new File( "/Users/tischer/Desktop/tmp2/SaveBDP2.ijm" ) );
		String headlessString = macroCreator.createHeadlessExecutableMacroString();
		System.out.println(
				"/Users/tischer/Desktop/Fiji-bdp2.app/Contents/MacOS/ImageJ-macosx --headless --mem=10000M -eval " +
						"'" + headlessString + "'" );
		// /Users/tischer/Desktop/Fiji-imflow.app/Contents/MacOS/ImageJ-macosx --headless -eval
	}

}
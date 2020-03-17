package de.embl.cba.bdp2.scijava.command;

import de.embl.cba.bdp2.image.Image;
import de.embl.cba.bdp2.service.ImageService;
import de.embl.cba.bdp2.load.files.FileInfos;
import de.embl.cba.bdp2.ui.BigDataProcessor2;
import de.embl.cba.bdp2.ui.HelpDialog;
import loci.common.DebugTools;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.widget.Button;

import javax.swing.*;
import java.io.File;

@Plugin(type = Command.class, menuPath = "Plugins>BigDataProcessor2>Open...")
public class OpenCommand< R extends RealType< R > & NativeType< R > > implements Command {

    @Parameter(label = "Image data directory", style = "directory")
    File directory;

    @Parameter(label = "Subset files using regular expression")
    String filterPattern = ".*";

    @Parameter(label = "Regular expression help", callback = "showRegExpHelp", required = false)
    Button regExpHelpButton;

    @Parameter(label = "Image files scheme",
            choices = {
                    FileInfos.SINGLE_CHANNEL_TIMELAPSE,
                    FileInfos.LEICA_LIGHT_SHEET_TIFF,
                    FileInfos.LOAD_CHANNELS_FROM_FOLDERS,
                    FileInfos.TIFF_SLICES,
                    FileInfos.PATTERN_1,
                    FileInfos.PATTERN_2,
                    FileInfos.PATTERN_3,
                    FileInfos.PATTERN_4,
                    FileInfos.PATTERN_5,
                    FileInfos.PATTERN_6})
    String namingScheme = FileInfos.SINGLE_CHANNEL_TIMELAPSE;

    @Parameter(label = "Auto contrast")
    boolean autoContrast = false;

    public void run()
    {
        SwingUtilities.invokeLater( () ->  {
            DebugTools.setRootLevel( "OFF" ); // Bio-Formats

            final Image< R > image =
                    BigDataProcessor2.openImage(
                            directory.toString(),
                            namingScheme,
                            filterPattern );

            ImageService.nameToImage.put( image.getName(), image );

            BigDataProcessor2.setVoxelSpacingViaDialog( image );

            BigDataProcessor2.showImage( image, autoContrast );
        });
    }

    public void showRegExpHelp()
    {
        SwingUtilities.invokeLater( () -> {
            final HelpDialog helpDialog = new HelpDialog( null,
                    OpenCommand.class.getResource( "/RegExpHelp.html" ) );
            helpDialog.setVisible( true );
        } );
    }
}
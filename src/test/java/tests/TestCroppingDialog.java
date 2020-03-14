package tests;

import de.embl.cba.bdp2.Image;
import de.embl.cba.bdp2.loading.files.FileInfos;
import de.embl.cba.bdp2.track.*;
import de.embl.cba.bdp2.ui.BigDataProcessor2;
import de.embl.cba.bdp2.viewers.BdvImageViewer;
import de.embl.cba.bdv.utils.BdvUtils;
import ij.IJ;
import loci.common.DebugTools;
import net.imagej.ImageJ;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import org.junit.Test;
import org.ojalgo.matrix.transformation.Rotation;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

public class TestCroppingDialog
{
    @Test
    public < R extends RealType< R > & NativeType< R > > void run( )
    {
        DebugTools.setRootLevel("OFF"); // Bio-Formats

        String imageDirectory = "/Users/tischer/Documents/fiji-plugin-bigDataProcessor2/src/test/resources/test-data/microglia-tracking-nt123/volumes";

        final Image< R > image = BigDataProcessor2.openImage(
                imageDirectory,
                FileInfos.SINGLE_CHANNEL_TIMELAPSE,
                ".*" );

        image.setVoxelUnit( "pixel" );
        image.setVoxelSpacing( 1.0, 1.0, 1.0 );

        final BdvImageViewer viewer = BigDataProcessor2.showImage( image );
        viewer.get5DIntervalFromUser( true );
    }

    public static void main( String[] args )
    {
        final ImageJ imageJ = new ImageJ();
        imageJ.ui().showUI();
        new TestCroppingDialog().run();
    }

}
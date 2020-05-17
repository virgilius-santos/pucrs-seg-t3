
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTests
{

    @Test
    public void testConverterSha1() throws Exception {

        final String hn_1Expected = "37d88ff100aaf4c63bb828ff1a89f99af2123e143bd758d0eb1573a044e74c84".toUpperCase();
        final String hn0Expected = "302256b74111bcba1c04282a1e31da7e547d4a7098cdaec8330d48bd87569516".toUpperCase();

        String[] names = {"FuncoesResumo - SHA1.mp4"};
        App.main(names);
        Converter converter = App.converter;
        assertEquals(converter.numberOfBlocks,15575);
        assertEquals(converter.lastBlockSize,738);
        assertEquals(converter.numberOfBytes,15948514);
        assertEquals(converter.h0String,hn0Expected);
        assertEquals(converter.hn_1String,hn_1Expected);
    }
    
    @Test
    public void testConverterHashFunctions() throws Exception {

        final String hn_1Expected = "EA914F3A87C1914BB7EBF5CAF2005E53BDAF25A176E92721424A3276D6260EBA";
        final String hn0Expected = "45013B3A2D5BC5369B90125DA1DC55D2903C47C3852E13B04878DF9942F21B9D";

        String[] names = new String[0];
        App.main(names);
        Converter converter = App.converter;
        assertEquals(converter.numberOfBlocks,40544);
        assertEquals(converter.lastBlockSize,361);
        assertEquals(converter.numberOfBytes,41516393);
        assertEquals(converter.h0String,hn0Expected);
        assertEquals(converter.hn_1String,hn_1Expected);
    }
}

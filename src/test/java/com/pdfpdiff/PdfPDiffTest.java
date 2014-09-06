package com.pdfpdiff;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mcorey on 8/28/14.
 */
@RunWith(Parameterized.class)
public class PdfPDiffTest {
    private PdfPDiff comparer;

    private String pdf1;
    private String pdf2;
    private boolean match;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"pdf-sample-1.pdf", "pdf-sample-1a.pdf", true},
//                {"pdf1-a.pdf", "pdf1-a.pdf", true},
//                {"pdf1-a.pdf", "pdf2.pdf", false},
//                {"belk-00-packingslip-ds.pdf.generated.pdf", "belk-00-packingslip-ds.pdf", false},
//                {"qvc_generated_barcode.pdf", "qvc_sample_barcode.pdf", false}
        });
    }

    public PdfPDiffTest(String pdf1, String pdf2, boolean match) {
        this.pdf1=pdf1;
        this.pdf2=pdf2;
        this.match=match;
    }

    @Before
    public void makeIt() {
        comparer = new PdfPDiff();
    }

    @Test
    public void isTheBooleanRight() {
        InputStream pdfStream1 = loadStream(pdf1);
        InputStream pdfStream2 = loadStream(pdf2);

        if(match)
            assertTrue("Should have matched", comparer.compare(pdfStream1, pdfStream2).pdfsMatch());
        else
            assertFalse("Shouldn't have matched", comparer.compare(pdfStream1, pdfStream2).pdfsMatch());
    }

    @Test
    public void areTheDiffsForTheEqualPDFsReallyClean() {
        if(match) {
            ComparisonResults res = comparer.compare(loadStream(pdf1), loadStream(pdf2));

            for(BufferedImage img : res.getDiffs()) {
                for (int y = 0; y < img.getHeight(); ++y) {
                    for(int x = 0; x < img.getWidth(); ++x) {
                        //asserts that every pixes is white
                        assertEquals(0x000000, img.getRGB(x, y));
                    }
                }
            }
        }
    }

    private InputStream loadStream(String resource) {
        return this.getClass().getClassLoader().getResourceAsStream(resource);
    }
}

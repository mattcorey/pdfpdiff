package com.pdfpdiff.conversion;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;

/**
 * Created by mcorey on 9/4/14.
 */
public interface PDFToImgConverter {
    public List<BufferedImage> convertToImage(InputStream pdfStream);
}

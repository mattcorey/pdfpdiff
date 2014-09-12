package com.pdfpdiff.conversion

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/10/14.
 */
interface PDFToImgConverter {
    List<BufferedImage> convertToImage(InputStream pdfStream)
}

package com.pdfpdiff.conversion.impl

import com.pdfpdiff.conversion.PDFToImgConverter
import org.apache.pdfbox.io.RandomAccessBuffer
import org.apache.pdfbox.pdmodel.PDDocument

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/10/14.
 */
class PDFBoxConverter implements PDFToImgConverter {

    @Override
    @SuppressWarnings('EmptyCatchBlock')
    List<BufferedImage> convertToImage(InputStream pdfStream) {
        if (pdfStream == null) {
            throw new IllegalArgumentException('Null parameter was passed into converter.')
        }
        try {
            if (pdfStream.available() == 0) {
                throw new IllegalArgumentException('Empty stream was passed into converter.')
            }
        } catch (IOException e) {
            throw new IllegalStateException('Unable to read from input stream', e)
        }

        PDDocument doc1 = getDoc(pdfStream)

        def ret = []

        doc1.documentCatalog.allPages.each { page ->
            try {
                ret << page.convertToImage()
            } catch (IOException e) {
                throw new IllegalStateException('Unable to convert a page to an image', e)
            }
        }

        try {
            doc1.close()
        } catch (IOException e) {
            //Not gonna worry about this one.
        }

        ret
    }

    private PDDocument getDoc(InputStream pdfStream) {
        try {
            return PDDocument.load(pdfStream, new RandomAccessBuffer())
        } catch (IOException e) {
            throw new IllegalArgumentException(e)
        }
    }
}

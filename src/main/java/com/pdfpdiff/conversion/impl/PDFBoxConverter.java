package com.pdfpdiff.conversion.impl;

import com.pdfpdiff.conversion.PDFToImgConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of PDFToImgConverter that uses the PDFBox library from Apache.
 *
 * Created by mcorey on 9/4/14.
 */
public class PDFBoxConverter implements PDFToImgConverter {
    @Override
    public List<BufferedImage> convertToImage(InputStream pdfStream) {
        if (pdfStream == null) {
            throw new IllegalArgumentException("Null parameter was passed into converter.");
        }
        try {
            if (pdfStream.available() == 0) {
                throw new IllegalArgumentException("Empty stream was passed into converter.");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to read from input stream", e);
        }

        PDDocument doc1 = getDoc(pdfStream);

        List<BufferedImage> ret = new ArrayList<BufferedImage>();

        for (PDPage page: (List<PDPage>) doc1.getDocumentCatalog().getAllPages()) {
            try {
                ret.add(page.convertToImage());
            } catch (IOException e) {
                throw new IllegalStateException("Unable to convert a page to an image", e);
            }
        }

        try {
            doc1.close();
        } catch (IOException e) {
            throw new IllegalStateException("Error while closing PDF", e);
        }

        return ret;
    }

    private PDDocument getDoc(InputStream pdfStream) {
        try {
            return PDDocument.load(pdfStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

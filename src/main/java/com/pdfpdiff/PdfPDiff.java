package com.pdfpdiff;

import com.pdfpdiff.conversion.PDFToImgConverter;
import com.pdfpdiff.conversion.impl.PDFBoxConverter;
import com.pdfpdiff.diff.ImgDiff;
import com.pdfpdiff.diff.impl.BasicImgDiffImpl;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * Simple implementation of a PDF comparison -- this class first converts two PDF's to images, and then does a pixel
 * by pixel comparison of the images to tell whether they are equal.
 *
 * Created by mcorey on 8/28/14.
 */
public class PdfPDiff {
    private PDFToImgConverter converter = new PDFBoxConverter();
    private ImgDiff differ = new BasicImgDiffImpl();

    public ComparisonResults compare(InputStream pdfStream1, InputStream pdfStream2) {
        List<BufferedImage> imgList1 = converter.convertToImage(pdfStream1);
        List<BufferedImage> imgList2 = converter.convertToImage(pdfStream2);

        //If they have a different number of pages, bail
        if(imgList1.size() != imgList2.size()) {
            return new ComparisonResults(false);
        }

        //Compare each page
        int cnt = 0;
        ComparisonResults results = new ComparisonResults(true);

        for(BufferedImage img1: imgList1) {


            BufferedImage img2 = imgList2.get(cnt);

            if(!differ.doImagesMatch(img1, img2)) {
                if(results.pdfsMatch()) {
                    results = new ComparisonResults(false);
                }

                results.getDiffs().add(differ.createImageDiff(img1, img2));
            }


            cnt++;
        }

        return results;
    }

    public ComparisonResults compare(File pdfFile1, File pdfFile2) {
        ComparisonResults results = null;
        try {
            results = compare(new FileInputStream(pdfFile1), new FileInputStream(pdfFile2));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Error loading PDF from file", e);
        }

        return results;
    }

}

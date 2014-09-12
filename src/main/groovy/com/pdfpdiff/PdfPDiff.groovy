package com.pdfpdiff

import com.pdfpdiff.conversion.PDFToImgConverter
import com.pdfpdiff.conversion.impl.PDFBoxConverter
import com.pdfpdiff.diff.ImgDiff
import com.pdfpdiff.diff.impl.BasicImgDiffImpl

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/10/14.
 */
class PdfPDiff {
    PDFToImgConverter converter = new PDFBoxConverter()
    ImgDiff differ = new BasicImgDiffImpl()

    ComparisonResults compare(InputStream pdfStream1, InputStream pdfStream2) {
        List<BufferedImage> imgList1 = converter.convertToImage(pdfStream1)
        List<BufferedImage> imgList2 = converter.convertToImage(pdfStream2)

        //If they have a different number of pages, bail
        if (imgList1.size() != imgList2.size()) {
            return new ComparisonResults(false)
        }

        //Compare each page
        int cnt = 0
        ComparisonResults results = new ComparisonResults(true)

        for (BufferedImage img1: imgList1) {

            BufferedImage img2 = imgList2.get(cnt)

            if (!differ.compareImages(img1, img2)) {
                if (results.pdfsMatch()) {
                    results = new ComparisonResults(false)
                }

                results.diffs.add(differ.produceImageDiff(img1, img2))
            }

            cnt++
        }

        results
    }

    ComparisonResults compare(File pdfFile1, File pdfFile2) {
        ComparisonResults results = null
        try {
            results = compare(new FileInputStream(pdfFile1), new FileInputStream(pdfFile2))
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException('Error loading PDF from file', e)
        }

        results
    }
}

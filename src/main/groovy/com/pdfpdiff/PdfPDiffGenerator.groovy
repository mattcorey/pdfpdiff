package com.pdfpdiff

import com.pdfpdiff.conversion.PDFToImgConverter
import com.pdfpdiff.conversion.impl.PDFBoxConverter
import com.pdfpdiff.diff.ImageComparer
import com.pdfpdiff.diff.impl.BasicImageComparerImpl

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/10/14.
 */
class PdfPDiffGenerator {
    PDFToImgConverter converter = new PDFBoxConverter()
    ImageComparer differ = new BasicImageComparerImpl()

    PdfPDiff generatePdfPDiff(InputStream pdfStream1, InputStream pdfStream2) {
        if (!pdfStream1 || !pdfStream2) {
            throw new IllegalArgumentException('Input streams cannot be null.')
        }

        List<BufferedImage> imgList1 = converter.convertToImage(pdfStream1)
        List<BufferedImage> imgList2 = converter.convertToImage(pdfStream2)

        //If they have a different number of pages, bail
        if (imgList1.size() != imgList2.size()) {
            return new PdfPDiff(false)
        }

        //Compare each page
        int cnt = 0
        PdfPDiff results = new PdfPDiff(true)

        for (BufferedImage img1: imgList1) {

            BufferedImage img2 = imgList2.get(cnt)

            if (!differ.compareImages(img1, img2)) {
                if (results.pdfsMatch()) {
                    results = new PdfPDiff(false)
                }

                results.diffs.add(differ.produceImageDiff(img1, img2))
            }

            cnt++
        }

        results
    }

    PdfPDiff generatePdfPDiff(File pdfFile1, File pdfFile2) {
        if (!pdfFile1 || !pdfFile2) {
            throw new IllegalArgumentException('Input files cannot be null.')
        }

        PdfPDiff results = null
        try {
            results = generatePdfPDiff(new FileInputStream(pdfFile1), new FileInputStream(pdfFile2))
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException('Error loading PDF from file', e)
        }

        results
    }
}

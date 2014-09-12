package com.pdfpdiff.diff

import com.pdfpdiff.diff.impl.DiffGeneratorStrategy

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/11/14.
 */
class DiffGenerator {

    BufferedImage produceImageDiff(BufferedImage img1, BufferedImage img2, DiffType type) {
        Class strategy = type.strategy
        DiffGeneratorStrategy strategyInstance = strategy.newInstance()

        strategyInstance.produceDiff(img1, img2)
    }

}

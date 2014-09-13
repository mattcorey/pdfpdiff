package com.pdfpdiff.diff

import com.pdfpdiff.diff.impl.DiffGeneratorStrategy

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/11/14.
 */
class DiffGenerator {

    BufferedImage produceImageDiff(BufferedImage img1, BufferedImage img2, DiffType type) {
        if (img1.width != img2.width
                || img1.height != img2.height) {
            throw new IllegalArgumentException('Images are different sizes, cannot produce visual diff')
        }

        Class strategy = type.strategy
        DiffGeneratorStrategy strategyInstance = strategy.newInstance()

        BufferedImage img = new BufferedImage(img1.width,
                img1.height,
                img1.type)

        for (int y = 0; y < img1.height; ++y) {
            for (int x = 0; x < img1.width; ++x) {
                def pixelMod = strategyInstance.produceColor(img1.getRGB(x, y), img2.getRGB(x, y))

                img.setRGB(x, y, pixelMod)
            }
        }

        img
    }

}

package com.pdfpdiff.diff

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/11/14.
 */
class DiffGenerator {

    BufferedImage produceImageDiff(BufferedImage img1, BufferedImage img2, DiffType type) {
        if (!img1) {
            throw new IllegalArgumentException('Invalid or null \'img1\' provided.')
        }
        if (!img2) {
            throw new IllegalArgumentException('Invalid or null \'img2\' provided.')
        }
        if (!type) {
            throw new IllegalArgumentException('Invalid or null DiffType provided.')
        }

        if (img1.width != img2.width
                || img1.height != img2.height) {
            throw new IllegalArgumentException('Images are different sizes, cannot produce visual diff')
        }

        BufferedImage img = new BufferedImage(img1.width,
                img1.height,
                img1.type)

        for (int y = 0; y < img1.height; ++y) {
            for (int x = 0; x < img1.width; ++x) {
                def pixelMod = type.strategy.produceColor(img1.getRGB(x, y), img2.getRGB(x, y))

                img.setRGB(x, y, pixelMod)
            }
        }

        img
    }

}

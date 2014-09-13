package com.pdfpdiff.diff.impl

import com.pdfpdiff.diff.ImageComparer
import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/11/14.
 */
class BasicImageComparerImpl implements ImageComparer {
    @Override
    boolean compareImages(BufferedImage img1, BufferedImage img2) {
        if (img1.width == img2.width && img1.height == img2.height) {
            for (int x = 0; x < img1.width; x++) {
                for (int y = 0; y < img1.height; y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                        return false
                    }
                }
            }
        } else {
            return false
        }

        true
    }
}

package com.pdfpdiff.diff.impl

import com.pdfpdiff.diff.ImageComparer
import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/11/14.
 */
class BasicImageComparerImpl implements ImageComparer {
    private static final int BIT_MASK = 0x000000FF
    private static final int RED = 0xFF000000

    private static final double RED_WEIGHT = 0.299
    private static final double GREEN_WEIGHT = 0.587
    private static final double BLUE_WEIGHT = 0.114

    private static final int BIT_SHIFT_32 = 32
    private static final int BIT_SHIFT_16 = 16
    private static final int BIT_SHIFT_8 = 8
    private static final int ZERO = 0

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

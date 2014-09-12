package com.pdfpdiff.diff.impl

import com.pdfpdiff.diff.ImgDiff
import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/11/14.
 */
class BasicImgDiffImpl implements ImgDiff {
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
    boolean doImagesMatch(BufferedImage img1, BufferedImage img2) {
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

    @Override
    BufferedImage produceImageDiff(BufferedImage img1, BufferedImage img2) {
        if (img1.width != img2.width
                || img1.height != img2.height) {
            throw new IllegalArgumentException('Images are different sizes, cannot produce visual diff')
        }

        BufferedImage img = new BufferedImage(img1.width,
                img1.height,
                img1.type)

        for (int y = 0; y < img1.height; ++y) {
            for (int x = 0; x < img1.width; ++x) {
                int pixelA = img1.getRGB(x, y)
                int pixelB = img2.getRGB(x, y)

                int pixelMod = RED
                if (pixelA == pixelB) {
                    pixelMod = convertToGrayscale(pixelA)
                }

                img.setRGB(x, y, pixelMod)
            }
        }

        img
    }

    private int convertToGrayscale(int rgb) {
        //Based on the following weighed algorithm:  0.299r + 0.587g + 0.114b
        int red = (rgb >> BIT_SHIFT_16) & BIT_MASK
        int green = (rgb >> BIT_SHIFT_8) & BIT_MASK
        int blue = rgb & BIT_MASK

        int target = (int) ((red * RED_WEIGHT) + (green * GREEN_WEIGHT) + (blue * BLUE_WEIGHT))

        (ZERO << BIT_SHIFT_32) + (target << BIT_SHIFT_16) + (target << BIT_SHIFT_8) + target
    }
}

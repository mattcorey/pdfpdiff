package com.pdfpdiff.diff.impl

/**
 * Created by mattcorey on 9/11/14.
 */
class GreyScaleDiffGeneratorStrategy implements DiffGeneratorStrategy {
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
    int produceColor(int rgb1, int rgb2) {
        def ret = RED

        if (rgb1 == rgb2) {
            convertToGrayscale(rgb1)
        }

        ret
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

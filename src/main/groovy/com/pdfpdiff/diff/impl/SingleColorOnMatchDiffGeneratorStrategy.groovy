package com.pdfpdiff.diff.impl

/**
 * Created by mattcorey on 9/13/14.
 */
class SingleColorOnMatchDiffGeneratorStrategy implements DiffGeneratorStrategy {
    def colorOnMatch

    SingleColorOnMatchDiffGeneratorStrategy(int color) {
        colorOnMatch = color
    }

    @Override
    int produceColor(int rgb1, int rgb2) {
        def ret = 0x00FF0000

        if (rgb1 == rgb2) {
            ret = colorOnMatch
        }

        ret
    }
}

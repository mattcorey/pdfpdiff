package com.pdfpdiff.diff

import com.pdfpdiff.util.ImageLoadUtil
import spock.lang.Specification

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/13/14.
 */
class DiffGeneratorTest extends Specification {
    def 'Passing the same reference in, it matches' () {
        setup:
            def img = ImageLoadUtil.loadImage('diffs/okay.jpg')

        when: 'I pass the same image reference into the DiffGenerator'
            def output = new DiffGenerator().produceImageDiff(img, img, DiffType.RED_OVER_WHITE)

        then: 'it creates a diff that\'s all matches'
            allWhite(output)
    }

    private void allWhite(BufferedImage img) {
        for (int i; i < img.width; ++i) {
            for (int j; j < img.height; ++j) {
                assert (img.getRGB(i, j) & 0X00FFFFFF) == 0xFFFFFF
            }
        }
    }
}

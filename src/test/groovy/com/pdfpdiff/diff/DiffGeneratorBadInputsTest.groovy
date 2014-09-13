package com.pdfpdiff.diff

import spock.lang.Specification
import spock.lang.Unroll

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/13/14.
 */
class DiffGeneratorBadInputsTest extends Specification {
    def 'Images of different widths bomb' () {
        when: 'Images of different widths are passed into the generator'
            def img1 = loadImage('diffs/yay-big.jpg')
            def img2 = loadImage('diffs/yay-skinny.jpg')
            new DiffGenerator().produceImageDiff(img1, img2, DiffType.RED_OVER_GREY_SCALE)

        then: 'it blows up'
            thrown IllegalArgumentException
    }

    def 'Images of different heights bomb' () {
        when: 'Images of different heights are passed into the generator'
            def img1 = loadImage('diffs/yay-big.jpg')
            def img2 = loadImage('diffs/yay-short.jpg')
            new DiffGenerator().produceImageDiff(img1, img2, DiffType.RED_OVER_GREY_SCALE)

        then: 'it blows up'
            thrown IllegalArgumentException
    }

    @Unroll
    def 'Any combination of null images bomb' (String img1, String img2) {
        when: '#image1 is compared to #image2'
            def image1 = loadImage(img1)
            def image2 = loadImage(img2)
            new DiffGenerator().produceImageDiff(image1, image2, DiffType.RED_OVER_GREY_SCALE)

        then: 'it blows up'
            thrown IllegalArgumentException

        where:
            img1             | img2
            'diffs/okay.jpg' | null
            null             | 'diffs/okay.jpg'
            null             | null
    }

    def 'A null DiffType bombs, too' () {
        when: 'Valid images, but a null DiffType are passed in'
        def img = loadImage('diffs/yay-big.jpg')
        new DiffGenerator().produceImageDiff(img, img, null)

        then: 'it blows up'
            thrown IllegalArgumentException
    }

    private BufferedImage loadImage(String name) {
        if (!name) {
            return null
        }

        ImageIO.read(this.class.classLoader.getResourceAsStream(name))
    }
}

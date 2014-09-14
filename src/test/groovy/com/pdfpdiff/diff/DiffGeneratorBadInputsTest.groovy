package com.pdfpdiff.diff

import com.pdfpdiff.util.ImageLoadUtil
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by mattcorey on 9/13/14.
 */
class DiffGeneratorBadInputsTest extends Specification {
    def 'Images of different widths bomb' () {
        when: 'Images of different widths are passed into the generator'
            def img1 = ImageLoadUtil.loadImage('diffs/yay-big.jpg')
            def img2 = ImageLoadUtil.loadImage('diffs/yay-skinny.jpg')
            new DiffGenerator().produceImageDiff(img1, img2, DiffType.RED_OVER_GREY_SCALE)

        then: 'it blows up'
            thrown IllegalArgumentException
    }

    def 'Images of different heights bomb' () {
        when: 'Images of different heights are passed into the generator'
            def img1 = ImageLoadUtil.loadImage('diffs/yay-big.jpg')
            def img2 = ImageLoadUtil.loadImage('diffs/yay-short.jpg')
            new DiffGenerator().produceImageDiff(img1, img2, DiffType.RED_OVER_GREY_SCALE)

        then: 'it blows up'
            thrown IllegalArgumentException
    }

    @Unroll
    def 'Any combination of null images bomb' (String img1, String img2) {
        when: '#image1 is compared to #image2'
            def image1 = ImageLoadUtil.loadImage(img1)
            def image2 = ImageLoadUtil.loadImage(img2)
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
        def img = ImageLoadUtil.loadImage('diffs/yay-big.jpg')
        new DiffGenerator().produceImageDiff(img, img, null)

        then: 'it blows up'
            thrown IllegalArgumentException
    }
}

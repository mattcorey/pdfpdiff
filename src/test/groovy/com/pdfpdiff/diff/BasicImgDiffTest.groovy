package com.pdfpdiff.diff

import com.pdfpdiff.diff.impl.BasicImgDiffImpl
import spock.lang.Specification

import javax.imageio.ImageIO

/**
 * Created by mattcorey on 9/11/14.
 */
class BasicImgDiffTest extends Specification {
    def 'Passing in images of different sizes don\'t match' () {
        when: 'I pass in two images of different sizes'
            def img1 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/yay-big.jpg'))
            def img2 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/okay.jpg'))
            def results = new BasicImgDiffImpl().compareImages(img1, img2)

        then: 'they don\'t match'
            !results
    }

    def 'Comparing two different imges of the same size also don\'t match' () {
        when: 'I pass in two different images of the same size'
            def img1 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/yay-little.jpg'))
            def img2 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/okay.jpg'))
            def results = new BasicImgDiffImpl().compareImages(img1, img2)

        then: 'they don\'t match'
            !results
    }

    def 'Comparing two different images of the same width also don\'t match' () {
        when: 'I pass in two different images of the same size'
        def img1 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/yay-skinny.jpg'))
        def img2 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/okay.jpg'))
        def results = new BasicImgDiffImpl().compareImages(img1, img2)

        then: 'they don\'t match'
        !results
    }

    def 'Comparing two different imges of the same height also don\'t match' () {
        when: 'I pass in two different images of the same size'
        def img1 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/yay-short.jpg'))
        def img2 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/okay.jpg'))
        def results = new BasicImgDiffImpl().compareImages(img1, img2)

        then: 'they don\'t match'
        !results
    }

    def 'The same image matches' () {
        when: 'I pass in the same exact image'
            def img = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/okay.jpg'))
            def results = new BasicImgDiffImpl().compareImages(img, img)

        then: 'it matches - whoot!'
            results == true
    }

    def 'Copies of the same image matches' () {
        when: 'I pass in copies of a same image'
            def img1 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/okay.jpg'))
            def img2 = ImageIO.read(this.class.classLoader.getResourceAsStream('diffs/okay-2.jpg'))
            def results = new BasicImgDiffImpl().compareImages(img1, img2)

        then: 'it matches'
            results == true
    }
}

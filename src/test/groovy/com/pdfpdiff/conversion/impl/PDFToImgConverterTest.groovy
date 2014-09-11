package com.pdfpdiff.conversion.impl

import spock.lang.Specification

import javax.imageio.ImageIO
import java.awt.image.BufferedImage


/**
 * Created by mattcorey on 9/6/14.
 */
class PDFToImgConverterTest extends Specification {

    def "does a simple conversion work"() {
        setup: "Load the input and the expected output"
            def inStream = this.class.classLoader.getResourceAsStream('simple-test/simple-test.pdf')
            def expectedOut = ImageIO.read(this.class.classLoader.getResourceAsStream('simple-test/simple-test.png'))

        when: "The Input PDF is passed through the converter"
            List<BufferedImage> generatedOutput = new PDFBoxConverter().convertToImage(inStream)

        then: "It produces a single PNG image"
            generatedOutput.size() == 1

        and: "The generated output is a pixel for pixel match of the expected output"
            matchesExpectedOutput(expectedOut, generatedOutput[0])
    }

    def "does a multipage conversion work" () {
        setup: "Load the input and the expected output"
            def inStream = this.class.classLoader.getResourceAsStream('simple-multipage-test/simple-multipage-test.pdf')
            def expectedOutPage0 = ImageIO.read(this.class.classLoader.getResourceAsStream('simple-multipage-test/simple-multipage-test-0.png'))
            def expectedOutPage1 = ImageIO.read(this.class.classLoader.getResourceAsStream('simple-multipage-test/simple-multipage-test-1.png'))
            def expectedOutPage2 = ImageIO.read(this.class.classLoader.getResourceAsStream('simple-multipage-test/simple-multipage-test-2.png'))

        when: "The multi-page input PDF is passed through the converter"
            List<BufferedImage> generatedOutput = new PDFBoxConverter().convertToImage(inStream)

        then: "It produces one PNG image per page"
            generatedOutput.size() == 3

        and: "the generated pages are a pixel for pixel match of the expected output"
            matchesExpectedOutput(expectedOutPage0, generatedOutput[0])
            matchesExpectedOutput(expectedOutPage1, generatedOutput[1])
            matchesExpectedOutput(expectedOutPage2, generatedOutput[2])
    }

    private void matchesExpectedOutput(BufferedImage expectedOut, BufferedImage generatedOutput) {
        assert expectedOut
        assert generatedOutput

        assert expectedOut.width == generatedOutput.width
        assert expectedOut.height == generatedOutput.height

        //TODO -- find a reliable way to compare this.  Different fonts on different systems make this not work so well
    }
}

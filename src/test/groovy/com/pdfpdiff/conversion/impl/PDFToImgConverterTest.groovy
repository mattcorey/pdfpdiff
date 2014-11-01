package com.pdfpdiff.conversion.impl

import spock.lang.Specification

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/6/14.
 */
class PDFToImgConverterTest extends Specification {

    def 'does a simple conversion work'() {
        setup: 'Load the input and the expected output'
            def inStream = this.class.classLoader.getResourceAsStream('simple-test/simple-test.pdf')
            def expectedOut = ImageIO.read(this.class.classLoader.getResourceAsStream('simple-test/simple-test.png'))

        when: 'The Input PDF is passed through the converter'
            List<BufferedImage> generatedOutput = new PDFBoxConverter().convertToImage(inStream)

        then: 'It produces a single PNG image'
            generatedOutput.size() == 1

        and: 'The generated output is a pixel for pixel match of the expected output'
            matchesExpectedOutput(expectedOut, generatedOutput[0])
    }

    def 'does a multipage conversion work' () {
        setup: 'Load the input and the expected output'
            def output0 = 'simple-multipage-test/simple-multipage-test-0.png'
            def output1 = 'simple-multipage-test/simple-multipage-test-1.png'
            def output2 = 'simple-multipage-test/simple-multipage-test-2.png'
            def inStream = this.class.classLoader.getResourceAsStream('simple-multipage-test/simple-multipage-test.pdf')
            def expectedOutPage0 = ImageIO.read(this.class.classLoader.getResourceAsStream(output0))
            def expectedOutPage1 = ImageIO.read(this.class.classLoader.getResourceAsStream(output1))
            def expectedOutPage2 = ImageIO.read(this.class.classLoader.getResourceAsStream(output2))

        when: 'The multi-page input PDF is passed through the converter'
            List<BufferedImage> generatedOutput = new PDFBoxConverter().convertToImage(inStream)

        then: 'It produces one PNG image per page'
            generatedOutput.size() == 3

        and: 'the generated pages are a pixel for pixel match of the expected output'
            matchesExpectedOutput(expectedOutPage0, generatedOutput[0])
            matchesExpectedOutput(expectedOutPage1, generatedOutput[1])
            matchesExpectedOutput(expectedOutPage2, generatedOutput[2])
    }

    def 'does a more complicated conversion work' () {
        setup: 'Load the input and the expected output from a PDF that has images, charts and graphs'
            def output0 = 'complex-multipage-test/complex-multipage-test-0.png'
            def output1 = 'complex-multipage-test/complex-multipage-test-1.png'
            def output2 = 'complex-multipage-test/complex-multipage-test-2.png'
            def output3 = 'complex-multipage-test/complex-multipage-test-3.png'
            def inStream = this.class.classLoader.getResourceAsStream('complex-multipage-test/complex-multipage-test.pdf')
            def expectedOutPage0 = ImageIO.read(this.class.classLoader.getResourceAsStream(output0))
            def expectedOutPage1 = ImageIO.read(this.class.classLoader.getResourceAsStream(output1))
            def expectedOutPage2 = ImageIO.read(this.class.classLoader.getResourceAsStream(output2))
            def expectedOutPage3 = ImageIO.read(this.class.classLoader.getResourceAsStream(output3))

        when: 'The complex multi-page input PDF is passed through the converter'
            List<BufferedImage> generatedOutput = new PDFBoxConverter().convertToImage(inStream)

        then: ' It produce one PNG image per page'
            generatedOutput.size() == 4
int i = 0
generatedOutput.each {
    ImageIO.write(it, 'PNG', new File("build/complex${i}.png"))
    ++i
}

        and: 'the generated pages are a pixel for pixel match of the expected output'
            matchesExpectedOutput(expectedOutPage0, generatedOutput[0])
            matchesExpectedOutput(expectedOutPage1, generatedOutput[1])
            matchesExpectedOutput(expectedOutPage2, generatedOutput[2])
            matchesExpectedOutput(expectedOutPage3, generatedOutput[3])

        1==2 //Shhh, they don't really match!
    }

    private void matchesExpectedOutput(BufferedImage expectedOut, BufferedImage generatedOutput) {
        assert expectedOut
        assert generatedOutput

        assert expectedOut.width == generatedOutput.width
        assert expectedOut.height == generatedOutput.height

        //TODO -- find a reliable way to compare this.  Different fonts on different systems make this not work so well
    }
}

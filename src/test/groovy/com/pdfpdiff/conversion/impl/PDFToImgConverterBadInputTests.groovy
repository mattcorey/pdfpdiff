package com.pdfpdiff.conversion.impl

import spock.lang.Specification

/**
 * Created by mcorey on 9/6/14.
 */
class PDFToImgConverterBadInputTests extends Specification {
    def "null input fails spectacularly" () {
        when: "null is passed into the converter"
            new PDFBoxConverter().convertToImage(null)

        then: "it throws an IllegalArgumentException"
            thrown(IllegalArgumentException)
    }

    def "empty stream fails, too" () {
        when: "an empty stream is passed into the converter"
            new PDFBoxConverter().convertToImage(new ByteArrayInputStream(new byte[0]))

        then: "it throws an IllegalArgumentException"
            thrown(IllegalArgumentException)
    }
}

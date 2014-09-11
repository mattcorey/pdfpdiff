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

    def "not a pdf fails" () {
        when: "a stream is passed into the converter that ain't a pdf"
            new PDFBoxConverter().convertToImage(new ByteArrayInputStream("This ain't no pdf!".bytes))

        then: "it throws an IllegalArgumentException"
            thrown(IllegalArgumentException)
    }

    def "failing to read is bad when checking availability" () {
        when: "An IOException is thrown while the converter is reading the stream"
            RuntimeException exc
            try {
                new PDFBoxConverter().convertToImage(new BlowyUpyInputStream())
            } catch (RuntimeException e) {
                exc = e
            }

        then: "it throws a RuntimeException with the original exception Wrapped in it"
            exc != null
            exc.cause instanceof IOException
    }

    def "failing to read is bad when loading doc" () {
        when: "An IOException is thrown while the converter is reading the stream"
            RuntimeException exc
            try {
                new PDFBoxConverter().convertToImage(new BlowyUpyInputStream(false, true))
            } catch (RuntimeException e) {
                println e
                exc = e
            }

        then: "it throws a RuntimeException with the original exception Wrapped in it"
            exc != null
            exc.cause instanceof IOException
    }
}

package com.pdfpdiff.conversion.impl

import org.apache.pdfbox.pdmodel.PDPage
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by mcorey on 9/6/14.
 */
class PDFToImgConverterBadInputTests extends Specification {
    def 'null input fails spectacularly' () {
        when: 'null is passed into the converter'
            new PDFBoxConverter().convertToImage(null)

        then: 'it throws an IllegalArgumentException'
            thrown(IllegalArgumentException)
    }

    def 'empty stream fails, too' () {
        when: 'an empty stream is passed into the converter'
            new PDFBoxConverter().convertToImage(new ByteArrayInputStream(new byte[0]))

        then: 'it throws an IllegalArgumentException'
            thrown(IllegalArgumentException)
    }

    def 'not a pdf fails' () {
        when: 'a stream is passed into the converter that ain\'t a pdf'
            new PDFBoxConverter().convertToImage(new ByteArrayInputStream('This ain\'t no pdf!'.bytes))

        then: 'it throws an IllegalArgumentException'
            thrown(IllegalArgumentException)
    }

    def 'failing to read is bad when checking availability' () {
        when: 'An IOException is thrown while the converter is reading the stream'
            RuntimeException exc
            try {
                new PDFBoxConverter().convertToImage(new BlowyUpyInputStream())
            } catch (RuntimeException e) {
                exc = e
            }

        then: 'it throws a RuntimeException with the original exception Wrapped in it'
            exc != null
            exc.cause instanceof IOException
    }

    def 'failing to read is bad when loading doc' () {
        when: 'An IOException is thrown while the converter is reading the stream'
            RuntimeException exc
            try {
                new PDFBoxConverter().convertToImage(new BlowyUpyInputStream(false, true))
            } catch (RuntimeException e) {
                exc = e
            }

        then: 'it throws a RuntimeException with the original exception Wrapped in it'
            exc != null
            exc.cause instanceof IOException
    }

    @Ignore('Don\'t work, because PDPage isn\'t a Groovy class')
    def 'when conversion blows up' () {
        setup:
            def anyPage = GroovyMock(PDPage, [global : true])

        when: 'The PDFBox doc conversion throws an exception in the converter'
            RuntimeException exc
            try {
                def inputStream = this.class.classLoader.getResourceAsStream('simple-test/simple-test.pdf')
                new PDFBoxConverter().convertToImage(inputStream)
            } catch (RuntimeException e) {
                exc = e
            }

        then: 'it throws a RuntimeException with the original exception wrapped in it'
            1 * anyPage.convertToImage(_) >> { throw new IOException('Here it is, baby') }

            exc != null
            exc.cause instanceof IOException
    }
}

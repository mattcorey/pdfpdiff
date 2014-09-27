package com.pdfpdiff

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by mattcorey on 9/26/14.
 */
class PdfPDiffGeneratorBadInputsTest extends Specification {
    @Unroll
    def 'Null Files (#comparingFile, #file1Null, #file2Null) throw Exceptions' (boolean comparingFile,
                                                                                boolean file1Null,
                                                                                boolean file2Null) {
        when: 'A null file or stream is passed into the class'
            PdfPDiffGenerator gener = new PdfPDiffGenerator()
            Object param1
            Object param2

            if (comparingFile) {
                if (!file1Null) {
                    param1 = new File(this.class.classLoader.getResource('simple-test/simple-test.pdf').file)
                }
                if (!file2Null) {
                    param2 = new File(this.class.classLoader.getResource('simple-test/simple-test.pdf').file)
                }
            } else {
                if (!file1Null) {
                    param1 = this.class.classLoader.getResourceAsStream('simple-test/simple-test.pdf')
                }
                if (!file2Null) {
                    param2 = this.class.classLoader.getResourceAsStream('simple-test/simple-test.pdf')
                }
            }

            if (comparingFile) {
                gener.generatePdfPDiff((File)param1, (File)param2)
            } else {
                gener.generatePdfPDiff(param1, param2)
            }

        then: 'an IllegalArgumentException is thrown'
            thrown(IllegalArgumentException)

        where:
            comparingFile | file1Null | file2Null
            true          | false     | true
            true          | true      | false
            false         | false     | true
            false         | true      | false
    }

}

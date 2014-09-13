package com.pdfpdiff.diff.impl

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by mattcorey on 9/13/14.
 */
class GreyScaleTest extends Specification {

    @Unroll
    def "Colors #color1 and #color2 are passed through the strategy, and should return #expectedColor" (
            String color1, String color2, String expectedColor) {
        setup:
            def intColor1 = Integer.decode(color1)
            def intColor2 = Integer.decode(color2)
            def intExpected = Integer.decode(expectedColor)

        expect:
            new GreyScaleDiffGeneratorStrategy().produceColor(intColor1, intColor2) == intExpected

        where:
            color1     | color2     | expectedColor
            '0x00000000' | '0x00000000' | '0x00000000'
            '0x00FFFFFF' | '0x00FFFFFF' | '0x00FDFDFD'
            '0x00FFFFFF' | '0x00000000' | '0x00FF0000'
    }
}
